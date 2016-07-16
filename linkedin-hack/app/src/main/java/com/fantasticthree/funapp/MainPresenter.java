package com.fantasticthree.funapp;

import android.util.Log;

import com.fantasticthree.funapp.data.ImageResponse;
import com.fantasticthree.funapp.utils.RxUtils;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private Subscription mTestApiSubscription;
    private Subscription mUploadSubscription;
    private final MainInteractor mMainInteractor;

    private FaceTrackerActivity mActivity;

    public MainPresenter(FaceTrackerActivity activity) {
        mMainInteractor = new MainInteractor();
        mActivity = activity;
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

    public void upload(String encodedImage) {
        if (mMainInteractor.isSending()) {
            return;
        }
        mUploadSubscription = mMainInteractor.uploadImage(encodedImage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageResponse -> {
                    if (imageResponse == null) {
                        Log.d(TAG, "imageResponse is null");
                        return;
                    }
                    mActivity.getUserProfile(FaceTrackerActivity.LINKEDIN_BASE_URL + imageResponse.getUserId());
                    mActivity.setCurrentUuid(imageResponse.getUserId());
                    Log.d(TAG, "imageResponse: " + imageResponse.getUserId());
                }, throwable -> {
                    Log.d(TAG, "Got an error: ", throwable);
                });
    }

    public void onDestroy() {
        RxUtils.unsubscribe(mTestApiSubscription);
        RxUtils.unsubscribe(mUploadSubscription);
    }

    public boolean shouldTakePhoto() {
        return !mMainInteractor.isSending();
    }


}
