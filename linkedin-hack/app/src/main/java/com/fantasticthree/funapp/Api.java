package com.fantasticthree.funapp;

import com.fantasticthree.funapp.entity.ImageResponseEntity;
import com.fantasticthree.funapp.entity.TestEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface Api {

    String BODY_IMAGE_PARAM = "photo";
    //    @GET("test")
    //    Observable<TestEntity> testApi(@Query("fields") String fields);

    @GET("face")
    Observable<TestEntity> testApi();

    @POST("recognize")
    Observable<ImageResponseEntity> uploadPicture(@Body Map<String, Object> params);
}