package com.example.studentspecificproductivityapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private final String SHARED_PREFERENCES_NAME = "UserSession";
    private final String IS_LOGGED_IN = "IsLoggedIn";

    public SessionManagement(Context context)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();;
    }

    public boolean isLoggedIn()
    {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void createLoginSession()
    {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();
    }

    public void logOut()
    {
        editor.clear();
        editor.apply();
    }
}
