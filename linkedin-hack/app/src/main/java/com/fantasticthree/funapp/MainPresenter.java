package com.fantasticthree.funapp;

import android.util.Log;

import com.fantasticthree.funapp.utils.RxUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private Subscription mTestApiSubscription;
    private final MainInteractor mMainInteractor;

    public MainPresenter() {
        mMainInteractor = new MainInteractor();
        getTestApi();
    }

    public void getTestApi() {
        mTestApiSubscription = mMainInteractor.getTestApiResult()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.d(TAG, s);
                }, throwable -> {
                    Log.d(TAG, "Got an error: ", throwable);
                });
    }

    public void onDestroy() {
        RxUtils.unsubscribe(mTestApiSubscription);
    }

}
