package com.example.studentspecificproductivityapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private final String SHARED_PREFERENCES_NAME = "UserSession";
    private final String IS_LOGGED_IN = "IsLoggedIn";
    private final String USER_ID = "UserId";
    private final String USER_EMAIL = "UserEmail";

    public SessionManagement(Context context)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();;
    }

    public boolean isLoggedIn()
    {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void createLoginSession(int userId, String email)
    {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putInt(USER_ID,userId);
        editor.putString(USER_EMAIL,email);
        editor.apply();
    }

    public int getUserId(){
        return sharedPreferences.getInt(USER_ID,-1);
    }

    public void logOut()
    {
        editor.clear();
        editor.apply();
    }
}
