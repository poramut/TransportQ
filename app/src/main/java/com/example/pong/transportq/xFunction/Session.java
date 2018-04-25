package com.example.pong.transportq.xFunction;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by User on 12/2/2561.
 */

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setLatti(String Latti) {
        prefs.edit().putString("Latti", Latti).commit();
    }

    public String getLatti() {
        String Latti = prefs.getString("Latti","");
        return Latti;
    }

    public void setLongti(String Longti) {
        prefs.edit().putString("Longti", Longti).commit();
    }

    public String getLongti() {
        String Longti = prefs.getString("Longti","");
        return Longti;
    }

    public void setLocation(String Location) {
        prefs.edit().putString("Location", Location).commit();
    }

    public String getLocation() {
        String Location = prefs.getString("Location","");
        return Location;
    }

}