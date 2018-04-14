package edu.cmu.eps.scams.logic;

import java.util.List;

import edu.cmu.eps.scams.logic.model.AppSettings;
import edu.cmu.eps.scams.logic.model.Association;
import edu.cmu.eps.scams.logic.model.ClassifierParameters;
import edu.cmu.eps.scams.logic.model.History;
import edu.cmu.eps.scams.logic.model.Telemetry;

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

