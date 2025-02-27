package com.example.foodplannerapp.utilis;

import android.view.View;

import com.example.foodplannerapp.R;
import com.google.android.material.snackbar.Snackbar;

public class NoInternetSnackBar {
    public static void showSnackBar(View view){
        Snackbar snackbar = Snackbar
                .make(view, R.string.no_internet_connection, Snackbar.LENGTH_SHORT)
                ;
        snackbar.show();
    }
}
