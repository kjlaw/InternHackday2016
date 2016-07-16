package com.fantasticthree.funapp;

import android.content.Context;
import android.util.Log;

import com.fantasticthree.funapp.utils.RxUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private Subscription mTestApiSubscription;
    private Subscription mGetUserProfileSubscription;
    private final MainInteractor mMainInteractor;

    public MainPresenter(Context context) {
        mMainInteractor = new MainInteractor(context);
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

    public void getUserProfile(String userId) {
        mGetUserProfileSubscription = mMainInteractor.getUserProfile(userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> {
                    Log.d(TAG, "Received User Profile");
                }, throwable -> {
                    Log.e(TAG, "Received Error while getting user profile", throwable);
                });
    }

    public void onDestroy() {
        RxUtils.unsubscribe(mTestApiSubscription);
    }

}
