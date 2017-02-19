package com.gloco.exercise.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by "Tuan Nguyen" on 11/10/2016.
 */

public class NetworkUtils {


    /**
     * Use this method to check the network's availability
     *
     * @param context The context where checking is executed
     * @return true if network is available, false if it's not
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    /**
     * Check if Wi-fi is connected
     *
     * @param context The context where wifi check is executed
     * @return true if wifi is connected, otherwise false
     */
    public static boolean isConnectedWifi(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected()
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isConnectedCellular(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected()
                && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

}
