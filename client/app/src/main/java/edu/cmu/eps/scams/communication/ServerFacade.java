package edu.cmu.eps.scams.communication;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.eps.scams.logic.OutgoingMessage;
import edu.cmu.eps.scams.logic.model.ClassifierParameters;
import edu.cmu.eps.scams.logic.model.IncomingMessage;
import edu.cmu.eps.scams.logic.model.Telemetry;
import edu.cmu.eps.scams.utilities.TimestampUtility;

public class ServerFacade implements IServerFacade {

    private String identifier;
    private String secret;
    private String profile;
    private String recovery;
    private String token;
    private final Gson gson;
    private boolean initialized;

    public ServerFacade() {
        this.gson = new Gson();
        this.initialized = false;
    }

    public void init(boolean isRegistered, String identifier, String secret, String profile, String recovery) throws JSONException, CommunicationException {
        this.identifier = identifier;
        this.secret = secret;
        this.profile = profile;
        this.recovery = recovery;
        String result = null;
        if (isRegistered) {
            ServerResponse loginResponse = ServerApi.login(this.identifier, this.secret);
            JSONObject response = loginResponse.getBody();
            JSONObject resultItem = response.getJSONObject("result");
            result = resultItem.getString("access_token");
        } else {
            ServerResponse registerResponse = ServerApi.register(this.identifier, this.secret, this.profile, this.recovery);
            JSONObject registerObject = registerResponse.getBody();
            ServerResponse loginResponse = ServerApi.login(this.identifier, this.secret);
            JSONObject loginObject = loginResponse.getBody();
            JSONObject resultItem = loginObject.getJSONObject("result");
            result = resultItem.getString("access_token");
        }
        this.initialized = true;
        this.token = result;
    }

    @Override
    public void sendMessage(OutgoingMessage toSend) throws CommunicationException {
        try {
            ServerApi.createMessage(
                    this.token,
                    toSend.getRecipient(),
                    this.gson.toJson(toSend.getProperties()),
                    TimestampUtility.now());
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    @Override
    public void acknowledgeMessage(IncomingMessage toAcknowledge) throws CommunicationException {
        try {
            ServerResponse response = ServerApi.updateMessage(
                    this.token,
                    toAcknowledge.getIdentifier(),
                    TimestampUtility.now());
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    @Override
    public List<IncomingMessage> retrieveMessages() throws CommunicationException {
        try {
            List<IncomingMessage> output = new ArrayList<>();
            ServerResponse response = ServerApi.getMessages(this.token);
            JSONObject responseObject = response.getBody();
            JSONArray resultsArray = responseObject.getJSONArray("results");
            for (int resultsIndex = 0; resultsIndex < resultsArray.length(); resultsIndex++) {
                JSONObject resultObject = resultsArray.getJSONObject(resultsIndex);
                String identifier = resultObject.getString("identifier");
                String sender = resultObject.getString("sender");
                String recipient = resultObject.getString("recipient");
                int state = resultObject.getInt("state");
                Long created = resultObject.getLong("created");
                Long received = resultObject.getLong("received");
                Long recipientReceived = resultObject.getLong("recipientReceived");
                output.add(new IncomingMessage(
                        identifier,
                        sender,
                        recipient,
                        state,
                        created,
                        received,
                        recipientReceived
                ));
            }
            return output;
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    @Override
    public void sendTelemetry(Telemetry toSend) throws CommunicationException {
        try {
            ServerResponse response = ServerApi.createTelemetry(
                    this.token,
                    toSend.getDataType(),
                    toSend.getContent(),
                    toSend.getCreated());
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    @Override
    public ClassifierParameters retrieveClassifierParameters() throws CommunicationException {
        try {
            ServerResponse response = ServerApi.getClassifierParameters(this.token);
            JSONObject responseObject = response.getBody();
            JSONObject resultObject = responseObject.getJSONObject("results");
            String content = resultObject.getString("content");
            return new ClassifierParameters(content);
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    public boolean isInitialized() {
        return initialized;
    }
}
