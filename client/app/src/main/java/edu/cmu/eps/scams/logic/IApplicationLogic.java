package edu.cmu.eps.scams.logic;

import java.util.List;

/**
 * Created by jeremy on 4/13/2018.
 */

public interface IApplicationLogic {

    Association createAssociation(String qrValue);

    List<Association> getAssociations();

    boolean removeAssociation(Association association);

    List<History> getHistory();

    boolean removeHistory(History history);

    AppSettings getAppSettings();

    boolean updateAppSettings(AppSettings appSettings);

    void sendTelemetry(Telemetry telemetry);

    void sendMessage(OutgoingMessage outgoingMessage);

    List<OutgoingMessage> receiveMessages();

    ClassifierParameters getClassifierParameters();
}

