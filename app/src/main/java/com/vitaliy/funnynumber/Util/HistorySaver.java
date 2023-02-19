package com.vitaliy.funnynumber.Util;

import android.util.Log;

import com.vitaliy.funnynumber.App;
import com.vitaliy.funnynumber.Room.History;

public class HistorySaver {
    private static final String LOG_TAG = "HistorySaver:";

    public static void saveResultToHistory(String result){
        String[] tokens = result.split(" ");
        if (tokens.length < 2){
            Log.w(LOG_TAG, "Invalid result: " + result);
            return;
        }
        String number = tokens[0];

        StringBuilder factBuilder = new StringBuilder();
        for (int i = 1; i < tokens.length; i++){
            factBuilder.append(tokens[i]).append(" ");
        }
        String fact = factBuilder.toString().trim();

        History history = new History(number, fact);
        App.getInstance().getHistoryDAO().insert(history);
        Log.d(LOG_TAG, result + " saved;");
    }
}
