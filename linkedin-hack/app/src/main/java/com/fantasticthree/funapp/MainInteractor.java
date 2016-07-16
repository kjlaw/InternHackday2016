package com.fantasticthree.funapp;

import android.content.Context;

import com.fantasticthree.funapp.data.ImageResponse;
import com.fantasticthree.funapp.data.UserProfile;
import com.fantasticthree.funapp.entity.ImageResponseEntity;
import com.fantasticthree.funapp.entity.TestEntity;
import com.fantasticthree.funapp.entity.UserProfileEntity;
import com.fantasticthree.funapp.utils.LinkedInAuthInterceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainInteractor {

    private static final HttpUrl API_BASE_URL = HttpUrl.parse("http://www.blakelockbrown.com/");
    private static final HttpUrl LINKEDIN_BASE_URL = HttpUrl.parse("https://api.linkedin.com/");

    private Api mApi;
    private LinkedInApi mLinkedInApi;
    private Context mContext;
    public MainInteractor(Context context) {
        mContext = context;
        setupLinkedInApi();
        setupApi();
    }

    private void setupLinkedInApi() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new LinkedInAuthInterceptor(mContext))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(LINKEDIN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mLinkedInApi = retrofit.create(LinkedInApi.class);
    }

    private void setupApi() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApi = retrofit.create(Api.class);
    }

    public Observable<String> getTestApiResult() {
        return mApi.testApi()
                .map(this::getString);
    }

    public Observable<ImageResponse> uploadImage(String encodedImage) {
        Map<String, Object> params = new HashMap<>();
        params.put(Api.BODY_IMAGE_PARAM, encodedImage);
        return mApi.uploadPicture(params)
                .map(this::createImageResponse);
    }

    public Observable<UserProfile> getUserProfile(String userId) {
        String url = "";
        try {
            url = URLEncoder.encode("https://www.linkedin.com/in/" + userId, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mLinkedInApi.getUserProfile(url)
                .map(this::createUserProfile);
    }

    private String getString(TestEntity testEntity) {
        return testEntity.getHello();
    }

    private UserProfile createUserProfile(UserProfileEntity userProfileEntity) {
        return new UserProfile();
    }

    private ImageResponse createImageResponse(ImageResponseEntity imageResponseEntity){
        if(!imageResponseEntity.isSuccessful()) {
            return null;
        }
        return new ImageResponse.Builder()
                .withCompany(imageResponseEntity.getCompany())
                .withEmail(imageResponseEntity.getEmail())
                .withUserid(imageResponseEntity.getUserId())
                .withFullname(imageResponseEntity.getName())
                .build();
    }
}
