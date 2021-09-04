package com.fantasyapps.darmahealthcare.Services.NetworkReciver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import static com.fantasyapps.darmahealthcare.extensions.LogKt.error;

public class NetworkChangeReciever extends Activity {
    private onRecieveCallback mOnRecieveCallback;

    public BroadcastReceiver br;
    Context context;



    public static interface onRecieveCallback {
        void onMethodCallback(Boolean isConnected);
    }

    public void build() {
        if (br == null) {
            br = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();
                    NetworkInfo info = (NetworkInfo) extras.getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();
                    Log.d("Test Internet", info.toString() + " " + state.toString());
                    if (state == NetworkInfo.State.CONNECTED) {
                        //Toast.makeText(context, "Back to online", Toast.LENGTH_SHORT).show();
                        mOnRecieveCallback.onMethodCallback(true);


                    } else {
                        //Toast.makeText(context, "No connection", Toast.LENGTH_LONG).show();
                        mOnRecieveCallback.onMethodCallback(false);

                    }

                }
            };
/*
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReciever((BroadcastReceiver) br, intentFilter);
*/
            registerNetworkBroadcastForNougat(context);
        }

    }


    private void registerNetworkBroadcastForNougat(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.registerReceiver(br, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.registerReceiver(br, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            context.registerReceiver(br, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges(Context context) {
        try {
            context.unregisterReceiver(br);
        } catch (IllegalArgumentException e) {
//            error(context, e.getLocalizedMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges(context);
    }

}