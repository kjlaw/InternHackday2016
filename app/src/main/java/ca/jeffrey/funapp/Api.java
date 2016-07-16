package ca.jeffrey.funapp;

import ca.jeffrey.funapp.entity.TestEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {
    //    @GET("test")
    //    Observable<TestEntity> testApi(@Query("fields") String fields);

    @GET("face")
    Observable<TestEntity> testApi();
}