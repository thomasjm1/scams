package edu.cmu.eps.scams.storage.access;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.cmu.eps.scams.storage.model.HistoryRecord;

@Dao
public interface HistoryRecordAccess {
    @Query("SELECT * FROM historyrecord")
    List<HistoryRecord> getAll();

    @Query("SELECT * FROM historyrecord WHERE id IN (:historyRecordIds)")
    List<HistoryRecord> loadAllByIds(int[] historyRecordIds);

    @Insert
    void insertAll(HistoryRecord... historyRecords);

    @Delete
    void delete(HistoryRecord historyRecord);
}