package edu.cmu.eps.scams.communication;

import java.util.List;

import edu.cmu.eps.scams.logic.OutgoingMessage;
import edu.cmu.eps.scams.logic.model.ClassifierParameters;
import edu.cmu.eps.scams.logic.model.ClientIdentity;
import edu.cmu.eps.scams.logic.model.IncomingMessage;
import edu.cmu.eps.scams.logic.model.Telemetry;

public class ServerApi implements IServerApi {


    private final String identifier;
    private final String secret;
    private final String profile;
    private final String recovery;

    public ServerApi(String identifier, String secret, String profile, String recovery) {
        this.identifier = identifier;
        this.secret = secret;
        this.profile = profile;
        this.recovery = recovery;
    }

    @Override
    public void sendMessage(OutgoingMessage toSend) throws CommunicationException {

    }

    @Override
    public void acknowledgeMessage(IncomingMessage toAcknowledge) throws CommunicationException {

    }

    @Override
    public List<IncomingMessage> retrieveMessages() throws CommunicationException {
        return null;
    }

    @Override
    public void sendTelemetry(Telemetry toSend) throws CommunicationException {

    }

    @Override
    public ClassifierParameters retrieveClassifierParameters() throws CommunicationException {
        return null;
    }
}
