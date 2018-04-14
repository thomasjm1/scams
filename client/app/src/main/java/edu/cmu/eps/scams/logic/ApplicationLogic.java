package edu.cmu.eps.scams.logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.eps.scams.storage.ILocalStorage;
import edu.cmu.eps.scams.storage.StorageException;

public class ApplicationLogic implements IApplicationLogic {

    private static final String TAG = "ApplicationLogic";

    private final ILocalStorage storage;

    public ApplicationLogic(ILocalStorage storage) {
        this.storage = storage;
    }

    @Override
    public Association createAssociation(String qrValue) {
        Association toCreate = new Association(qrValue);
        try {
            storage.insert(toCreate);
        } catch (StorageException e) {
            Log.d(TAG, String.format("Failed to create Association due to %s", e.getMessage()));
        }
        return toCreate;
    }

    @Override
    public List<Association> getAssociations() {
        List<Association> results = new ArrayList<>();
        try {
            results = storage.retrieveAssociations();
        } catch (StorageException e) {
            Log.d(TAG, String.format("Failed to retrieve Associations due to %s", e.getMessage()));
        }
        return results;
    }

    @Override
    public boolean removeAssociation(Association association) {
        boolean result = true;
        try {
            storage.deleteAssociation(association);
        } catch (StorageException e) {
            Log.d(TAG, String.format("Failed to delete Association due to %s", e.getMessage()));
            result = false;
        }
        return result;
    }

    @Override
    public List<History> getHistory() {
        List<History> results = new ArrayList<>();
        try {
            results = storage.retrieveHistory();
        } catch (StorageException e) {
            Log.d(TAG, String.format("Failed to retrieve History due to %s", e.getMessage()));
        }
        return results;
    }

    @Override
    public boolean removeHistory(History history) {
        boolean result = true;
        try {
            storage.deleteHistory(history);
        } catch (StorageException e) {
            Log.d(TAG, String.format("Failed to delete History due to %s", e.getMessage()));
            result = false;
        }
        return result;
    }

    @Override
    public AppSettings getAppSettings() {
        try {
            return storage.retrieveSettings();
        } catch (StorageException e) {
            Log.d(TAG, String.format("Failed to retrieve Settings due to %s", e.getMessage()));
            AppSettings settings = AppSettings.defaults();
            try {
                storage.insert(settings);
            } catch (StorageException e1) {
                Log.d(TAG, String.format("Failed to insert default Settings due to %s", e1.getMessage()));
            }
            return settings;
        }
    }

    @Override
    public boolean updateAppSettings(AppSettings appSettings) {
        boolean result = true;
        try {
            storage.insert(appSettings);
        } catch (StorageException e) {
            Log.d(TAG, String.format("Failed to insert Settings due to %s", e.getMessage()));
            result = false;
        }
        return result;
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
