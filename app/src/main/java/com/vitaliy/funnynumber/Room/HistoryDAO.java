package com.vitaliy.funnynumber.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Query("SELECT * FROM History")
    List<History> getAll();

    @Query("SELECT * FROM History")
    LiveData<List<History>> getAllLiveData();

    @Query("SELECT * FROM History WHERE uid = :uid LIMIT 1")
    History findById(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History history);

    @Insert
    void insertAll(History... histories);

    @Delete
    void delete(History history);
}
