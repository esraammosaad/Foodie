package com.example.foodplannerapp.utilis;

import android.content.Intent;

public class ShareApp {

    public static Intent shareApp(){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Foodie");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage += "https://drive.google.com/drive/folders/1EQSISR97_Ym3OGLvyP8iv8KwizbRMxht?usp=drive_link";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            return shareIntent;
        } catch (Exception e) {
        }
        return null;
    }
}
