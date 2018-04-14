package edu.cmu.eps.scams.logic;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.eps.scams.logic.model.AppSettings;
import edu.cmu.eps.scams.logic.model.Association;
import edu.cmu.eps.scams.logic.model.ClassifierParameters;
import edu.cmu.eps.scams.logic.model.History;
import edu.cmu.eps.scams.logic.model.Telemetry;

/**
 * Created by fanmichaelyang on 4/14/18.
 */

public class HistoryRecords implements IApplicationLogic {

    @Override
    public Association createAssociation(String qrValue) {
        return null;
    }

    @Override
    public List<Association> getAssociations() {
        return null;
    }

    @Override
    public boolean removeAssociation(Association association) {
        return false;
    }

    @Override
        public List<History> getHistory() {

            List<History> historyList = new ArrayList<History>();

            historyList.add(new History("123-412-1231", "2010-10-2"));
            historyList.add(new History("412-231-2131", "2018-4-17"));

            return historyList;
        }

    @Override
    public boolean removeHistory(History history) {
        return false;
    }

    @Override
    public AppSettings getAppSettings() {
        return null;
    }

    @Override
    public boolean updateAppSettings(AppSettings appSettings) {
        return false;
    }

    @Override
    public void sendTelemetry(Telemetry telemetry) {

    }

    @Override
    public void sendMessage(OutgoingMessage outgoingMessage) {

    }

    @Override
    public List<OutgoingMessage> receiveMessages() {
        return null;
    }

    @Override
    public ClassifierParameters getClassifierParameters() {
        return null;
    }

}
