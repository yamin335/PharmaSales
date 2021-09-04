/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fantasyapps.darmahealthcare.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.location.LocationResult;
import com.rethinkdb.gen.exc.ReqlError;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;

import static com.fantasyapps.darmahealthcare.Services.ConnectivityReceiver.conn;
import static com.fantasyapps.darmahealthcare.Services.ConnectivityReceiver.r;

/**
 * Receiver for handling location updates.
 * <p>
 * For apps targeting API level O
 * {@link android.app.PendingIntent#getBroadcast(Context, int, Intent, int)} should be used when
 * requesting location updates. Due to limits on background services,
 * {@link android.app.PendingIntent#getService(Context, int, Intent, int)} should not be used.
 * <p>
 * Note: Apps running on "O" devices (regardless of targetSdkVersion) may receive updates
 * less frequently than the interval specified in the
 * {@link com.google.android.gms.location.LocationRequest} when the app is no longer in the
 * foreground.
 */
public class LocationUpdatesBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATES =
            "bd.com.rtchubs.taskapp.Services.action" +
                    ".PROCESS_UPDATES";
    private static final String TAG = "LUBroadcastReceiver";

    public static String getAddress(Context context, double LATITUDE, double LONGITUDE) {

        String fulladdress = "";
        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {


                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

//                Log.d(TAG, "getAddress:  address" + address);
//                Log.d(TAG, "getAddress:  city" + city);
//                Log.d(TAG, "getAddress:  state" + state);
//                Log.d(TAG, "getAddress:  postalCode" + postalCode);
//                Log.d(TAG, "getAddress:  knownName" + knownName);

//                Log.e("BroadCast Address____", address + "," + city + "," + state + "," + postalCode);
                fulladdress = address + "," + city + " ," + postalCode;

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return fulladdress;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    List<Location> locations = result.getLocations();
                    LocationResultHelper locationResultHelper = new LocationResultHelper(context, locations);
                    // Save the location data to SharedPreferences.
                    locationResultHelper.saveResults();
                    // Show notification with the location data.
                    locationResultHelper.showNotification();
//                    Log.e(TAG, LocationResultHelper.getSavedLocationResult(context));



                    SharedPreferences preferences = context.getSharedPreferences("loggedIn",Context.MODE_PRIVATE );
                    String empName=  preferences.getString("emplyoee_name", "");
                    String empId = preferences.getString("emplyoee_id", "0f0f0f");

                    Location location = result.getLastLocation();
                    if (location != null) {
                        String adress = getAddress(context, location.getLatitude(), location.getLongitude());
                            try {
                                if (conn == null){

                                    Thread thread = new Thread(new Runnable(){
                                        public void run() {
                                            try {
                                                conn = r.connection().hostname("104.251.211.120").port(28015).user("nasser", "123456").db("Swarm").connect();
                                                r.table("DarmaHealth").insert(
                                                        r.hashMap("id", ZonedDateTime.now().toInstant().toEpochMilli())
                                                                .with("employee_id", empId)
                                                                .with("employee_name", empName)
                                                                .with("address", adress)
                                                                .with("lat", location.getLatitude())
                                                                .with("long", location.getLongitude())
                                                                .with("lastUpdated_time", getCurrentTimeStamp())
                                                ).run(conn);

//                                                Log.e(TAG, "Data inserted_________HandlerMode_________yes!");
                                            } catch (Exception e) {
//                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();

                                }else {
                                    r.table("DarmaHealth").insert(
                                            r.hashMap("id", ZonedDateTime.now().toInstant().toEpochMilli())
                                                    .with("employee_id", empId)
                                                    .with("employee_name", empName)
                                                    .with("address", adress)
                                                    .with("lat", location.getLatitude())
                                                    .with("long", location.getLongitude())
                                                    .with("lastUpdated_time", getCurrentTimeStamp())
                                    ).run(conn);

//                                    Log.e(TAG, "Data inserted__________________yes!");
                                }
                                                                                                 //.optArg("conflict", "replace")
                            } catch (ReqlError error) {
//                                error.printStackTrace();
//                                Log.e(TAG, "Data inserted__________________no!");
                            }
                        }
                    }
                }
            }
        }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * Check if network available or not
     *
     * @param context
     * */
    public boolean isOnline(Context context)
    {
        boolean isOnline = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo.isConnected()){
                Log.e(TAG, "isOnline_______NetInfo_____________Connected");
            }else {
                Log.e(TAG, "isOnline_______NetInfo_____________NotConnected");
            }
            //should check null because in airplane mode it will be null
            isOnline = (netInfo != null && netInfo.isConnected());


        }
        catch (Exception ex)
        {
            Log.e(TAG, "isOnline____________________exception");
        }
        Log.e(TAG, "isOnline____________________"+ String.valueOf(isOnline));
        return isOnline;

    }

    public boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnectedOrConnecting())  //isConnectingOrConnected did the trick
            {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
