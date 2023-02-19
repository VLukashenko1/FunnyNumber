package com.vitaliy.funnynumber.Util;

import android.content.Context;
import android.net.ConnectivityManager;

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

    public void getFact(int number){}
    public void getRandFact(){}
}
