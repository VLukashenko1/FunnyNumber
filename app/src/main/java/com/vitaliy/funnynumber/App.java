package com.vitaliy.funnynumber;

import android.app.Application;

import androidx.room.Room;

import com.vitaliy.funnynumber.Room.AppDatabase;
import com.vitaliy.funnynumber.Room.HistoryDAO;


public class App extends Application {
    private static App holder;
    public static App getInstance(){
        if (holder == null) return new App();
        return holder;
    }

    private AppDatabase database;
    private HistoryDAO historyDAO;
    public AppDatabase getDatabase() {
        return database;
    }
    public HistoryDAO getHistoryDAO() {
        return historyDAO;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        holder = this;

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "request-history")
                .allowMainThreadQueries()
                .build();
        historyDAO = database.historyDao();
    }


}
