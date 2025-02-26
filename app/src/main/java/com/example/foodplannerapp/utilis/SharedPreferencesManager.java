package com.example.foodplannerapp.utilis;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static SharedPreferencesManager instance;
    SharedPreferences prefs;
    public SharedPreferences.Editor editor;

    private SharedPreferencesManager(Context context) {
        prefs = context.getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor=prefs.edit();
    }

    public static SharedPreferencesManager getInstance(Context context){

        if(instance==null){
            instance=new SharedPreferencesManager(context);
        }

        return instance;

    }

   public void saveOnBoardingState(){
       editor.putBoolean(Strings.SEEN_ONBOARDING,true);
       editor.apply();

    }

    public boolean getOnBoardingState(){

       return prefs.getBoolean(Strings.SEEN_ONBOARDING,false);


    }
    public  void saveThemeState(boolean state){

        editor.putBoolean(Strings.DARK_MODE,state);
        editor.apply();


    }
    public boolean getThemeState(){

        return prefs.getBoolean(Strings.DARK_MODE,false);


    }


}
