package com.fantasyapps.darmahealthcare;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.fantasyapps.darmahealthcare.Services.ConnectivityReceiver;
import androidx.annotation.RequiresApi;

import io.fabric.sdk.android.Fabric;

import static com.fantasyapps.darmahealthcare.Services.ConnectivityReceiver.conn;

public class App extends Application {
    private static final String TAG = "App";
    private ConnectivityReceiver connectivityReceiver;


    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        registerConnectivityReceiver();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        conn.reconnect();
        Log.e("App", "Terminated__________________RethinkDB________reconnect");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private ConnectivityReceiver getConnectivityReceiver() {
        if (connectivityReceiver == null)
            connectivityReceiver = new ConnectivityReceiver();

        return connectivityReceiver;
    }

    // register here your filtters
    private void registerConnectivityReceiver(){
        try {
//            if (android.os.Build.VERSION.SDK_INT >= 26) {
                IntentFilter filter = new IntentFilter();
                filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                //filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
                //filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
                //filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                registerReceiver(getConnectivityReceiver(), filter);
//            }
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
