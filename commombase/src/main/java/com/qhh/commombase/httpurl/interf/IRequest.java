package com.qhh.commombase.httpurl.interf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qinhaihang
 * @version $Rev$
 * @time 19-3-30 下午6:09
 * @des
 * @packgename com.qhh.commombase.httpurl
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public interface IRequest {
    String getBaseUrl();
    String getMethod();
    IEncrypt getEncrypt();
    HashMap<String, Object> getParam();
//    Map<String, FilePair> getFilePair();
    Map<String, String> getHeaders();
}
