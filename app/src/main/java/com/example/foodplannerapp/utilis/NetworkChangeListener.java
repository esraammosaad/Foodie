package com.example.foodplannerapp.utilis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeListener extends BroadcastReceiver {

    NetworkListener networkListener;
    public NetworkChangeListener(NetworkListener networkListener){
        this.networkListener=networkListener;


    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!NetworkAvailability.isNetworkAvailable(context)){

            networkListener.onLostConnection();



        }else {

            networkListener.onConnectionReturned();
        }
    }
}
