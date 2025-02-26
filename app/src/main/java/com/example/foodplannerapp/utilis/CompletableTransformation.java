package com.example.foodplannerapp.utilis;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableConverter;
import io.reactivex.rxjava3.core.CompletableTransformer;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CompletableTransformation {



    static public CompletableTransformer apply(){

        return upstream -> {

            return upstream.
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread());
        };


    }

}
