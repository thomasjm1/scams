package edu.cmu.eps.scams.communication;

import java.util.List;

import edu.cmu.eps.scams.logic.OutgoingMessage;
import edu.cmu.eps.scams.logic.model.ClassifierParameters;
import edu.cmu.eps.scams.logic.model.ClientIdentity;
import edu.cmu.eps.scams.logic.model.IncomingMessage;
import edu.cmu.eps.scams.logic.model.Telemetry;

public interface IServerApi {

    void sendMessage(OutgoingMessage toSend) throws CommunicationException;

    void acknowledgeMessage(IncomingMessage toAcknowledge) throws CommunicationException;

    List<IncomingMessage> retrieveMessages() throws CommunicationException;

    void sendTelemetry(Telemetry toSend) throws CommunicationException;

    ClassifierParameters retrieveClassifierParameters() throws CommunicationException;
}
