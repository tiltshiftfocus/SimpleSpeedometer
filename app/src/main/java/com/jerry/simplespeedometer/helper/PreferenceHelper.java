package com.jerry.simplespeedometer.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jerry on 23/3/2016.
 */
public class PreferenceHelper {
    private Context context;
    private static SharedPreferences pref;
    private static PreferenceHelper instance;

    private PreferenceHelper(Context ctx){
        pref = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static PreferenceHelper getInstance(Context ctx){
        if(instance == null){
            instance = new PreferenceHelper(ctx);
        }
        return instance;
    }

    public int getSpeedometerColor(){
        return pref.getInt("speedometer_color",-1);
    }

    public void setSpeedoMeterColor(int color){
        pref.edit().putInt("speedometer_color", color).apply();
    }

}
