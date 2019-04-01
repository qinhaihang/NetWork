package com.qhh.commombase.httpurl;

import android.text.TextUtils;

import com.qhh.commombase.httpurl.interf.IEncrypt;
import com.qhh.commombase.httpurl.interf.IRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

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

    private static final int READ_TIMEOUT = 50000;
    private static final int CONNECT_TIMEOUT = 50000;
    public static final String GET = "GET";
    public static final String POST = "POST";

    private static class SingletonHolder{
         private static final HttpHelp INSTANCE = new HttpHelp();
    }

    public static HttpHelp getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public Object get(IRequest request){

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = openUrlConnect(request);

            if(httpURLConnection == null){
                return null;
            }

            defaultSetting(request, httpURLConnection);

            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void defaultSetting(IRequest request, HttpURLConnection httpURLConnection) throws ProtocolException {
        //config setting
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
        httpURLConnection.setReadTimeout(READ_TIMEOUT);
        httpURLConnection.setRequestMethod(request.getMethod());
        //add header
        Map<String, String> headers = request.getHeaders();

        if (request.equals(GET)) {
            if (headers != null && headers.size() != 0) {
                httpURLConnection.setRequestProperty("Accept-Encoding", "gzip");
                Set<String> keySet = headers.keySet();
                for (String key : keySet) {
                    httpURLConnection.setRequestProperty(key, headers.get(key));
                }
            }
        } else {
            httpURLConnection.setRequestProperty("Conection", "Keep-Alive");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
        }
    }

    private HttpURLConnection openUrlConnect(IRequest request) throws IOException {

        URL url = buildUrl(request.getBaseUrl(), request.getParam(), request.getEncrypt());

        return (HttpURLConnection)url.openConnection();
    }

    private URL buildUrl(String urlPath, Map<String,Object> params, IEncrypt encrypt) throws MalformedURLException {
        if(TextUtils.isEmpty(urlPath) || params == null ||params.size() == 0)
            return null;

        if(!urlPath.endsWith("?")){
            urlPath += "?";
        }

        String paramsStr = "";
        if(encrypt != null){
            paramsStr = encrypt.encrypt(urlPath, params);
        } else {
            paramsStr = buildGetParams(params);
        }

        StringBuilder builder = new StringBuilder(urlPath);
        builder.append(paramsStr);

        return new URL(builder.toString());
    }

    private String buildGetParams(Map<String, Object> params) {

        StringBuilder stringBuilder = new StringBuilder();

        Set<String> keySet = params.keySet();
        for( String key : keySet ){
            Object value = params.get(key);
            if(value == null)
                continue;

            stringBuilder.append(key+"="+ URLEncoder.encode(value.toString())+"&");
        }

        return stringBuilder.substring(0,stringBuilder.length()-1);
    }

}
