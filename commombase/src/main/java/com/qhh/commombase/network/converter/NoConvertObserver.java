package com.qhh.commombase.network.converter;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

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

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
