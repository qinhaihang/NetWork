package com.qhh.commombase.network.manager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/3/27 14:45
 * @des 用于管理一些常用的 OkHttpClient 和 retrofit
 * @packgename com.qhh.commombase.network
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class NetWorkManager {

    private static final int DEFAULT_TIME_CONNECT = 60;//链接超时时间

    public static OkHttpClient defaultOkHttpClient(){
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIME_CONNECT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return mOkHttpClient;
    }

    public static Retrofit defaultRetrofit(OkHttpClient okHttpClient, String baseUrl, Converter.Factory factory){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient);
        builder.baseUrl(baseUrl);
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        if(factory != null)
            builder.addConverterFactory(factory);
        return builder.build();
    }

}
