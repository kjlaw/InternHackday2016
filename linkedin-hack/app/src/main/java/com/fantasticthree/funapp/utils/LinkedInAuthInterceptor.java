package com.fantasticthree.funapp.utils;

import android.content.Context;

import com.linkedin.platform.LISessionManager;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LinkedInAuthInterceptor implements Interceptor {

    Context mContext;

    public LinkedInAuthInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String access_token = LISessionManager.getInstance(mContext).getSession().getAccessToken().getValue();
        access_token = URLEncoder.encode(access_token, "UTF-8");
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("format","json")
                .addQueryParameter("oauth2_access_token", access_token)
                .build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
