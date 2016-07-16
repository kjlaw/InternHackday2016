package com.fantasticthree.funapp;

import com.fantasticthree.funapp.entity.UserProfileEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface LinkedInApi {
    @GET("v1/people/url={profile_url}")
    Observable<UserProfileEntity> getUserProfile(@Path("profile_url") String url);
}
