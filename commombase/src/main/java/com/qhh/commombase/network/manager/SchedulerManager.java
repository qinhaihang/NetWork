package com.qhh.commombase.network.manager;

import android.app.ProgressDialog;
import android.content.Context;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/3/27 20:03
 * @des 线程转化封装类
 * @packgename com.qhh.commombase.network.manager
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class SchedulerManager {

    /**
     * 不支持背压
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> schedulerObIOMain() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {

                Observable<T> observable = upstream
                        .subscribeOn(Schedulers.io())
                        //.doOnSubscribe(new Consumer<Disposable>() {
                        //    @Override
                        //    public void accept(Disposable disposable) throws Exception {
                        //        //检测是否断网，断网则取消发送
                        //    }
                        //})
                        .observeOn(AndroidSchedulers.mainThread());

                return observable;
            }
        };
    }

    /**
     * 支持背压
     */
    public static <T> FlowableTransformer<T, T> schedulerFbIOMain() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {

                return upstream
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(@NonNull Subscription subscription) throws Exception {
                                //如果无网络连接，则直接取消了
                                //if (!NetUtil.isConnected(context)) {
                                //    subscription.cancel();
                                //    MsgEvent msgEvent = new MsgEvent(Constants.NET_CODE_CONNECT, Constants.CONNECT_EXCEPTION);
                                //    EventBus.getDefault().post(msgEvent);
                                //}
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 带进度条的请求
     */
    public static <T> FlowableTransformer<T, T> io_main(final Context context, final ProgressDialog dialog) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream //为了让进度条保持一会儿做了个延时
                        .delay(1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(@NonNull final Subscription subscription) throws Exception {
                                //if (!NetUtil.isConnected(context)) {
                                //    subscription.cancel();
                                //    MsgEvent msgEvent = new MsgEvent(Constants.NET_CODE_CONNECT, Constants.CONNECT_EXCEPTION);
                                //    EventBus.getDefault().post(msgEvent);
                                //} else {
                                //    //启动进度显示，取消进度时会取消请求
                                //    if (dialog != null) {
                                //        dialog.setCanceledOnTouchOutside(false);
                                //        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                //            @Override
                                //            public void onCancel(DialogInterface dialog) {
                                //                subscription.cancel();
                                //            }
                                //        });
                                //        dialog.show();
                                //    }
                                //}
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
