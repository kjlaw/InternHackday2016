package com.fantasticthree.funapp;

import com.fantasticthree.funapp.data.ImageResponse;
import com.fantasticthree.funapp.entity.ImageResponseEntity;
import com.fantasticthree.funapp.entity.TestEntity;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class MainInteractor {

    private static final HttpUrl API_BASE_URL = HttpUrl.parse("http://www.blakelockbrown.com/");

    private Api mApi;

    public MainInteractor() {
        setupApi();
    }

    private void setupApi() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
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

    private String getString(TestEntity testEntity) {
        return testEntity.getHello();
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
