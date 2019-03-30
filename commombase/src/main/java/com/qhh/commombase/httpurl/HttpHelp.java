package com.qhh.commombase.httpurl;

import com.qhh.commombase.httpurl.interf.IRequest;

/**
 * @author qinhaihang
 * @version $Rev$
 * @time 19-3-30 下午6:27
 * @des
 * @packgename com.qhh.commombase.httpurl
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class HttpHelp {

    private static class SingletonHolder{
         private static final HttpHelp INSTANCE = new HttpHelp();
    }

    public static HttpHelp getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void get(IRequest request){

    }

}
