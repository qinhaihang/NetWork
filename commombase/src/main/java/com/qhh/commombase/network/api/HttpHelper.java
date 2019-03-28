package com.qhh.commombase.network.api;

import com.qhh.commombase.network.exception.HttpHelperException;
import com.qhh.commombase.network.manager.NetWorkManager;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/3/27 16:36
 * @des  对外暴露的操作辅助类,在业务层调用的时候配合业务层的 Loader使用
 * @packgename com.qhh.commombase.network
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class HttpHelper {

    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private String mBaseUrl;
    private Converter.Factory mFactory;

    private static class SingletonHolder{
        private static final HttpHelper INSTANCE = new HttpHelper();
    }

    public static HttpHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 建议在 Application中初始化
     */
    public void init(){
        if(mOkHttpClient == null){
            mOkHttpClient = NetWorkManager.defaultOkHttpClient();
        }

        if(mRetrofit == null){
            mRetrofit = NetWorkManager.defaultRetrofit(mOkHttpClient,mBaseUrl,mFactory);
        }

    }

    public HttpHelper addBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
        return this;
    }

    public HttpHelper addOkHttpClient(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
        return this;
    }

    public HttpHelper addRetrofit(Retrofit retrofit) {
        mRetrofit = retrofit;
        return this;
    }

    public HttpHelper addConverterFactory(Converter.Factory factory){
        mFactory = factory;
        return this;
    }

    public <T> T create(Class<T> apiServer) throws HttpHelperException {
        if(mRetrofit == null){
            throw new HttpHelperException("Retrofit is null!!!");
        }
        return mRetrofit.create(apiServer);
    }

}
