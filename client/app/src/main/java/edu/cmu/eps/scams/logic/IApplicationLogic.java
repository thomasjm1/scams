package edu.cmu.eps.scams.logic;

import java.util.List;

import edu.cmu.eps.scams.HistoryActivity;

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

}

