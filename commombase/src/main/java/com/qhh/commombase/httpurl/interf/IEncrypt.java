package com.qhh.commombase.httpurl.interf;

import java.util.Map;

/**
 * @author qinhaihang
 * @version $Rev$
 * @time 19-3-30 下午6:30
 * @des
 * @packgename com.qhh.commombase.httpurl.interf
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public interface IEncrypt {
    String encrypt(String urlPath, Map<String, Object> params);
    String dencrypt();
}
