package com.qhh.myapplication;

import android.app.Application;

import com.qhh.commombase.network.HttpHelper;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/3/27 18:06
 * @des
 * @packgename com.qhh.myapplication
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpHelper.getInstance()
                .addBaseUrl(Constants.BURL)
                .init();
    }
}
