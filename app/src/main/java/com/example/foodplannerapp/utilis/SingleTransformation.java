package com.example.foodplannerapp.utilis;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SingleTransformation {

    static public <T> SingleTransformer<T,T> apply(){

        return upstream -> {

            return upstream.
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread());
        };


    }

}