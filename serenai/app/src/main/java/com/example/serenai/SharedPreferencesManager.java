package com.example.serenai;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;


public class SharedPreferencesManager {
    private static final String KEY_IS_LOGGED_IN = "false";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_Password = "userPassword";
    private static final String KEY_USER_ID = "userID";
    private static final String KEY_IS_Youke = "isyouke";
    private static final String KEY_Session_ID = "";
    private static final String KEY_USER_Signature="signature";
    private static final String KEY_USER_RegisterTime = "Register_time";
    private static final String KEY_USER_DaysDifference = "daysDifference"; //账号注册天数
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isYouke() {
        return sharedPreferences.getBoolean(KEY_IS_Youke, false);
    }
    public void setIsYouke(boolean isyouke) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_Youke, isyouke);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public void setLoggedIn(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }
    public String getKEY_Session_ID() {
        return sharedPreferences.getString(KEY_Session_ID, "");
    }
    public void setKEY_Session_ID(String Session_ID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Session_ID, Session_ID);
        editor.apply();
    }
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }
    public void setUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUserPassword() {
        return sharedPreferences.getString(KEY_USER_Password, "");
    }
    public void setUserPassword(String userPassword) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_Password, userPassword);
        editor.apply();
    }

    public String getUserID() {
        return sharedPreferences.getString(KEY_USER_ID, "");
    }

    public void setUserID(String userID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, userID);
        editor.apply();
    }
    public String getUSERSignature(){return sharedPreferences.getString(KEY_USER_Signature,"");}
    public void setUserSignature(String signature){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_USER_Signature,signature);
        editor.apply();
    }

    public String getUser_RegisterTime(){
        return sharedPreferences.getString(KEY_USER_RegisterTime,"");
    }

    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public int getUser_daysDifference(){
        return sharedPreferences.getInt(KEY_USER_DaysDifference, 0);
    }

    public void setUser_daysDifference(int daysDifference){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY_USER_DaysDifference,daysDifference);
        editor.apply();
    }
}
