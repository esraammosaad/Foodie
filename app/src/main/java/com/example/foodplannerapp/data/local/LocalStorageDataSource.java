package com.example.foodplannerapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplannerapp.utilis.Strings;

public class LocalStorageDataSource {

    private static LocalStorageDataSource instance;
    SharedPreferences prefs;
    public SharedPreferences.Editor editor;

    private LocalStorageDataSource(Context context) {
        prefs = context.getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor=prefs.edit();
    }

    public static LocalStorageDataSource getInstance(Context context){

        if(instance==null){
            instance=new LocalStorageDataSource(context);
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
