package com.example.foodplannerapp.onboarding.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplannerapp.utilis.Strings;

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


}
