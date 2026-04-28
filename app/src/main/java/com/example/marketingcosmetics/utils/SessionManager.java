package com.example.marketingcosmetics.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(int userId, String name) {
        editor.putBoolean("IsLoggedIn", true);
        editor.putInt("UserId", userId);
        editor.putString("UserName", name);
        editor.commit();
    }

    public boolean isLoggedIn() { return pref.getBoolean("IsLoggedIn", false); }

    public int getUserId() { return pref.getInt("UserId", -1); }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}