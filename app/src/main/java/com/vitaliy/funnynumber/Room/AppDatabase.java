package com.vitaliy.funnynumber.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {History.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoryDAO historyDao();
}