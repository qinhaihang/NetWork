package com.qhh.commombase.network.api;

import com.qhh.commombase.network.manager.SchedulerManager;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * @author qinhaihang
 * @version $Rev$
 * @time 19-3-27 下午11:05
 * @des
 * @packgename com.qhh.commombase.network.api
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class SubscribeHelper {

    private static class SingletonHolder{
        private static final SubscribeHelper INSTANCE = new SubscribeHelper();
    }

    public static SubscribeHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public <T> void subscribeOb(Observable<T> observable, Observer<T> observer){
        observable.compose(SchedulerManager.<T>schedulerObIOMain())
                .subscribe(observer);
    }

}
