package com.fantasyapps.darmahealthcare.Services;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlError;
import com.rethinkdb.net.Connection;


public class ConnectivityReceiver extends BroadcastReceiver {
    public static final RethinkDB r = RethinkDB.r;
    public static Connection conn = null;
    private static final String TAG = "ConnectivityReceiver";



    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.e(TAG, "onReceive().........Connectivity..........." + intent.getAction());


        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            Log.e(TAG, "________________onConnectivityChanged");

            try {
                conn = r.connection().hostname("104.251.211.120").port(28015).user("nasser", "123456").db("Swarm").connect();
                Log.e(TAG, "Database connection_______________successful");
            }
            catch (NoClassDefFoundError error){
//                error.printStackTrace();
                Log.e(TAG, error.getLocalizedMessage());

            }
            catch (ReqlError error) {
//                error.printStackTrace();
                Log.e(TAG, "Database connection_______________failed");
            }
        }else {
            Log.e(TAG, "________________offline");
        }
    }
}