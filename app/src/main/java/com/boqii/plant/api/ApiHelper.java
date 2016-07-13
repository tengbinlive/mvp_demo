package com.boqii.plant.api;


import com.boqii.plant.api.helper.Result;
import com.boqii.plant.api.helper.ResultFunc;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * OpenAPI返回的简单结果类.
 *
 * @author bin.teng
 */
public class ApiHelper {

    public static <T> void wrap(Observable<Result<T>> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ResultFunc<T>())
                .subscribe(s);
    }

    public static <T> void wrap(Observable<Result<T>> o) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ResultFunc<T>());
    }
}