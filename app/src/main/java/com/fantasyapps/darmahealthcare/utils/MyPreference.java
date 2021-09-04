package com.fantasyapps.darmahealthcare.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MyPreference {

    Context context;

    public MyPreference(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(String id, String name, String email, String password, String image, String phone, String address, String serviceID) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("image", image);
        editor.putString("phone", phone);
        editor.putString("address", address);
        editor.putString("id", id);
        editor.putString("serviceAreaID", serviceID);
        editor.commit();
    }


    public void savePunchState(boolean isPunchIn, int position, long shiftID) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PunchState", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isPunchIn", isPunchIn);
        editor.putInt("position", position);
        editor.putLong("shiftID", shiftID);
        editor.commit();

    }

    public Boolean getPunchState() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PunchState", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isPunchIn", false);
    }


    public int getSelectedPosition() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PunchState", MODE_PRIVATE);
        return sharedPreferences.getInt("position", -1);
    }





}
