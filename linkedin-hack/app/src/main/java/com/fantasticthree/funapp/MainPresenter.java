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

    private ArrayList<String> mData;

    public MainPresenter() {
        mMainInteractor = new MainInteractor();
        mData = new ArrayList<>();
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
                    setData(imageResponse);
                    Log.d(TAG, "imageResponse: " + imageResponse.getFullName());
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

    public ArrayList<String> getData() {
        return mData;
    }

    public void setData(ImageResponse imageResponse) {
        mData.clear();
        mData.add(imageResponse.getFullName());
        mData.add(imageResponse.getCompany());
        mData.add(imageResponse.getEmail());
    }

}
