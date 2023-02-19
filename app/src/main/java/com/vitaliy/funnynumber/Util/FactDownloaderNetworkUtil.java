package com.vitaliy.funnynumber.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FactDownloaderNetworkUtil {
    public static final String LOG_TAG = "HTTP_Request";

    private static final FactDownloaderNetworkUtil holder = new FactDownloaderNetworkUtil();
    public static FactDownloaderNetworkUtil getInstance(){return holder;}

    public static boolean isOpenNetwork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null){
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    public String getFact(int number){
        try{
            URL url = new URL(Data.stringUrlNumber + number);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String fact = reader.readLine();

            if (fact == null || fact.isEmpty()){
                Log.d(LOG_TAG, "Fact with number, unable to read stream");
                return "Fact not found try again";
            }
            Log.d(LOG_TAG, "success fact loading");
            HistorySaver.saveResultToHistory(fact);
            return fact;
        }
        catch (IOException e) {
            Log.v(LOG_TAG, "Fact loading failed: ", e);
            return "ERROR";
        }
    }
    public String getRandFact(){
        try {
            URL url = new URL(Data.stringUrlRandom);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String fact = reader.readLine();

            httpURLConnection.disconnect();

            if (fact == null || fact.isEmpty()){
                Log.d(LOG_TAG, "RandFact, unable to read stream");
                return "Fact not found try again";
            }
            Log.d(LOG_TAG, "success fact loading");
            HistorySaver.saveResultToHistory(fact);
            return fact;
        }
        catch (IOException e) {
            Log.v(LOG_TAG, "Fact loading failed: ", e);
            return "ERROR";
        }
    }
}
