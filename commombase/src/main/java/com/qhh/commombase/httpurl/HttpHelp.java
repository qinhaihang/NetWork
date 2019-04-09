package com.qhh.commombase.httpurl;

import android.text.TextUtils;

import com.qhh.commombase.httpurl.interf.IRequest;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

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

    private int readTimeOut = 50000;
    private int connectTimeOut = 10000;

    private static class SingletonHolder{
         private static final HttpHelp INSTANCE = new HttpHelp();
    }

    public static HttpHelp getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void get(IRequest request){
        String urlStr = buildURL(request);

        HttpURLConnection httpURLConnection = openHttpConnect(urlStr);

        if(httpURLConnection == null){
            return;
        }

        try {
            httpURLConnection.setConnectTimeout(connectTimeOut);
            httpURLConnection.setReadTimeout(readTimeOut);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip");

            Map<String, String> headers = request.getHeaders();
            if(headers != null && headers.size() > 0){
                Set<String> keySet = headers.keySet();
                for( String key : keySet ){
                    httpURLConnection.setRequestProperty(key,headers.get(key));
                }
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        InputStream wrapStream = null;
        StringBuilder readerBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            wrapStream = wrapStream(httpURLConnection,inputStream);

            inputStreamReader = new InputStreamReader(wrapStream);
            reader = new BufferedReader(inputStreamReader, 512);
            String line;
            while ((line = reader.readLine()) != null){
                //疑惑在在这里换行是否正确？？
                readerBuilder.append(line + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeQuietly(inputStreamReader);
            closeQuietly(reader);
            closeQuietly(wrapStream);
        }

        String resultStr = readerBuilder.toString();
    }

    private void closeQuietly(Closeable io) {
        try {
            if (io != null) {
                io.close();
            }
        } catch (IOException e) {
        }
    }

    private InputStream wrapStream(HttpURLConnection httpURLConnection,InputStream inputStream) throws IOException {
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            inputStream = httpURLConnection.getInputStream();
            String contentEncoding = httpURLConnection.getContentEncoding();

            if (contentEncoding == null || "identity".equalsIgnoreCase(contentEncoding)) {
                return inputStream;
            }
            if ("gzip".equalsIgnoreCase(contentEncoding)) {
                return new GZIPInputStream(inputStream);
            }
            if ("deflate".equalsIgnoreCase(contentEncoding)) {
                return new InflaterInputStream(inputStream, new Inflater(false), 512);
            }

            throw new RuntimeException("unsupported content-encoding: " + contentEncoding);

        }
        return null;
    }

    private HttpURLConnection openHttpConnect(String urlStr) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlStr);
            String scheme = url.getProtocol();

            if("https".equals(scheme)){

            }else{

            }

            httpURLConnection = (HttpURLConnection) url.openConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpURLConnection;
    }

    private String buildURL(IRequest request) {
        String baseUrl = request.getBaseUrl();
        HashMap<String, Object> params = request.getParam();
        if(TextUtils.isEmpty(baseUrl) || params == null || params.size() == 0){
            return baseUrl;
        }

        if(baseUrl.endsWith("?")){
            baseUrl += "?";
        }

        StringBuilder sb = new StringBuilder();
        Set<String> keySets = params.keySet();
        for( String key : keySets){
            Object value = params.get(key);
            if (value == null)
                continue;
            sb = sb.append(key+"="+ URLEncoder.encode(value.toString())+"&");
        }

        String paramsStr = sb.substring(0, sb.length() - 1).toString();

        if(request.getEncrypt() != null){
            paramsStr = request.getEncrypt().encrypt(baseUrl,params);
        }

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append(paramsStr);
        return urlBuilder.toString();
    }

}
