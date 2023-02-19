package com.vitaliy.funnynumber.Util;

import android.util.Log;

import com.vitaliy.funnynumber.App;
import com.vitaliy.funnynumber.Room.History;

public class HistorySaver {
    private static final String LOG_TAG = "HistorySaver:";

    public static void saveResultToHistory(String result){
        String[] tokens = result.split(" ");
        String number = tokens[0];
        String fact = "";
        for(int i = 1; i < tokens.length; i++){
            fact = fact + tokens[i] + " ";
        }
        History history = new History(number, fact);
        App.getInstance().getHistoryDAO().insert(history);
        Log.d(LOG_TAG, result + "saved");
    }
}
