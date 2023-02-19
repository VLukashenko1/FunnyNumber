package com.vitaliy.funnynumber;

import android.app.Application;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.room.Room;

import com.vitaliy.funnynumber.Room.AppDatabase;
import com.vitaliy.funnynumber.Room.HistoryDAO;


public class App extends Application {
    private static App holder;
    public static App getInstance(){
        if (holder == null) return new App();
        return holder;
    }

    private ClipboardManager clipboardManager;

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
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "request-history")
                .allowMainThreadQueries()
                .build();
        historyDAO = database.historyDao();
    }

    public  boolean isOpenNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null){
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
    public ClipboardManager getClipboardManager(){
        return clipboardManager;
    }

}
