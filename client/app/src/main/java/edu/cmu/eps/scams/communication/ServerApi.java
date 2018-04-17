package edu.cmu.eps.scams.communication;

import org.json.JSONObject;

import edu.cmu.eps.scams.utilities.HTTPAction;

public class ServerApi {

    private static final String CODE = "yRK3LxunjCFrovCXKyG32nB3pyST7ddE40T8FlxK8CCn75EyGr5jTanGyMqJ";
    private static final String PARAMETERS_KEY = "p4ZfOJxhXJOU5VE9mdPX8Mo5V8dveda1bCUQaQ4QzHo06nrklJxRvdNpUZSE4WnG";
    private static final String HOST = "eps-scams.appspot.com";

    private static final String REGISTER_URL = String.format("https://%s/api/authentication/register", HOST);
    private static final String LOGIN_URL = String.format("https://%s/api/authentication/login", HOST);
    private static final String MESSAGES_URL = String.format("https://%s/api/messages/", HOST);
    private static final String CLASSIFIER_PARAMETERS_URL = String.format("https://%s/api/parameters/", HOST);
    private static final String TELEMETRY_URL = String.format("https://%s/api/telemetry/", HOST);

    public static ServerResponse register(String identifier, String secret, String profile, String recovery) throws CommunicationException {
        try {
            HTTPAction action = new HTTPAction(REGISTER_URL, "application/json");
            JSONObject toSend = new JSONObject();
            toSend.put("identifier", identifier);
            toSend.put("secret", secret);
            toSend.put("code", CODE);
            toSend.put("profile", profile);
            toSend.put("recovery", recovery);
            JSONObject response = action.postRequest(toSend);
            return new ServerResponse(response);
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    public static ServerResponse login(String identifier, String secret) throws CommunicationException {
        try {
            HTTPAction action = new HTTPAction(LOGIN_URL, "application/json");
            JSONObject toSend = new JSONObject();
            toSend.put("identifier", identifier);
            toSend.put("secret", secret);
            JSONObject response = action.postRequest(toSend);
            return new ServerResponse(response);
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    public static ServerResponse createMessage(String token, String recipient, String content, long created) throws CommunicationException {
        try {
            HTTPAction action = new HTTPAction(MESSAGES_URL, "application/json");
            action.setToken(token);
            JSONObject toSend = new JSONObject();
            toSend.put("recipient", recipient);
            toSend.put("content", content);
            toSend.put("created", created);
            JSONObject response = action.postRequest(toSend);
            return new ServerResponse(response);
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    public static ServerResponse getMessages(String token) throws CommunicationException {
        try {
            HTTPAction action = new HTTPAction(MESSAGES_URL, "application/json");
            action.setToken(token);
            JSONObject response = action.getRequest();
            return new ServerResponse(response);
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    public static ServerResponse updateMessage(String token, String identifier, long received) throws CommunicationException {
        try {
            HTTPAction action = new HTTPAction(MESSAGES_URL, "application/json");
            action.setToken(token);
            JSONObject toSend = new JSONObject();
            toSend.put("identifier", identifier);
            toSend.put("received", received);
            JSONObject response = action.putRequest(toSend);
            return new ServerResponse(response);
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    public static ServerResponse getClassifierParameters(String token) throws CommunicationException {
        try {
            HTTPAction action = new HTTPAction(CLASSIFIER_PARAMETERS_URL, "application/json");
            action.setToken(token);
            JSONObject response = action.getRequest();
            return new ServerResponse(response);
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

    public static ServerResponse createTelemetry(String token, String dataType, String content, long created) throws CommunicationException {
        try {
            HTTPAction action = new HTTPAction(TELEMETRY_URL, "application/json");
            action.setToken(token);
            JSONObject toSend = new JSONObject();
            toSend.put("data_type", dataType);
            toSend.put("content", content);
            toSend.put("created", created);
            JSONObject response = action.postRequest(toSend);
            return new ServerResponse(response);
        } catch (Exception e) {
            throw new CommunicationException(e);
        }
    }

}
