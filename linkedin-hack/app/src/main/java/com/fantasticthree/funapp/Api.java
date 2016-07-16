package com.fantasticthree.funapp;

import com.fantasticthree.funapp.entity.TestEntity;
import retrofit2.http.GET;
import rx.Observable;

public interface Api {
    //    @GET("test")
    //    Observable<TestEntity> testApi(@Query("fields") String fields);

    @GET("face")
    Observable<TestEntity> testApi();
}