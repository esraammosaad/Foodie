package com.example.foodplannerapp.utilis;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Transformation {
    static public <T> ObservableTransformer<T,T> apply(){

        return upstream -> upstream.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());


    }

}
