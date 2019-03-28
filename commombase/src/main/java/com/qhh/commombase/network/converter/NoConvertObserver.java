package com.qhh.commombase.network.converter;

import java.io.IOException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/3/27 19:43
 * @des 直接获取 responseBody 的 订阅者
 * @packgename com.qhh.commombase.network.converter
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public abstract class NoConvertObserver extends DisposableObserver<ResponseBody> {

    @Override
    public void onNext(ResponseBody responseBody) {
        BufferedSource bufferedSource = Okio.buffer(responseBody.source());
        String tempStr = "";
        try {
            tempStr = bufferedSource.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        onSuccess(200,tempStr);

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(int code, String msg);

    public abstract void onFailure(int code, String msg);

    public abstract void onSysError(Throwable e);
}
