package com.example.foodplannerapp.utilis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.airbnb.lottie.animation.content.Content;
import com.example.foodplannerapp.R;

public class NoInternetDialog {
    public static void showNoInternetDialog(Context context, String text){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(text);
            builder.setTitle(R.string.alert);
            builder.setCancelable(false);
            builder.setNegativeButton(R.string.ok, (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


    }
}
