package ca.jeffrey.funapp;

import ca.jeffrey.funapp.entity.TestEntity;
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

    private String getString(TestEntity testEntity) {
        return testEntity.getHello();
    }
}
