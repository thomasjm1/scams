package edu.cmu.eps.scams.storage;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.eps.scams.logic.AppSettings;
import edu.cmu.eps.scams.logic.Association;
import edu.cmu.eps.scams.logic.History;
import edu.cmu.eps.scams.storage.model.AssociationRecord;
import edu.cmu.eps.scams.storage.model.HistoryRecord;
import edu.cmu.eps.scams.storage.model.SettingsRecord;
import edu.cmu.eps.scams.utilities.TimestampUtility;

public class RoomStorage implements ILocalStorage {

    private final AppDatabase appDatabase;

    public RoomStorage(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void insert(Association toCreate) throws StorageException {
        try {
            AssociationRecord record = new AssociationRecord();
            record.setIdentifier(toCreate.getIdentifier());
            this.appDatabase.associationRecordAccess().insertAll(record);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Association> retrieveAssociations() throws StorageException {
        List<Association> results = new ArrayList<>();
        try {
            List<AssociationRecord> records = this.appDatabase.associationRecordAccess().getAll();
            for (AssociationRecord record : records) {
                results.add(new Association(record.getIdentifier()));
            }
            return results;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void deleteAssociation(Association association) throws StorageException {
        try {
            AssociationRecord record = this.appDatabase.associationRecordAccess().getByIdentifier(association.getIdentifier());
            this.appDatabase.associationRecordAccess().delete(record);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<History> retrieveHistory() throws StorageException {
        List<History> results = new ArrayList<>();
        try {
            List<HistoryRecord> records = this.appDatabase.historyRecordAccess().getAll();
            for (HistoryRecord record : records) {
                History result = new History(record.getNumber(), TimestampUtility.format(record.getTimestamp()));
                result.setId(record.getId());
                results.add(result);
            }
            return results;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void insertHistory(String description, long timestamp, String number) throws StorageException {
        try {
            HistoryRecord record = new HistoryRecord();
            record.setDescription(description);
            record.setNumber(number);
            record.setTimestamp(timestamp);
            this.appDatabase.historyRecordAccess().insertAll(record);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void deleteHistory(History history) throws StorageException {
        try {
            List<HistoryRecord> records = this.appDatabase.historyRecordAccess().loadAllByIds(new int[]{history.getId()});
            if (records.size() > 0) {
                this.appDatabase.historyRecordAccess().delete(records.get(0));
            }
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public AppSettings retrieveSettings() throws StorageException {
        List<AppSettings> results = new ArrayList<>();
        try {
            List<SettingsRecord> records = this.appDatabase.settingsRecordAccess().getAll();
            for (SettingsRecord record : records) {
                results.add(new AppSettings(record.getIdentifier()));
            }
            return results.get(0);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void insert(AppSettings settings) throws StorageException {
        try {
            SettingsRecord record = new SettingsRecord();
            record.setIdentifier(settings.getIdentifier());
            this.appDatabase.settingsRecordAccess().insertAll(record);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }
}
