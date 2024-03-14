package com.project_six.databaseconnection.utils;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpConnectionManager {
    private UrlMaker urlMaker;
    private String content = null;
    public HttpConnectionManager(UrlMaker urlMaker){
        this.urlMaker = urlMaker;
    }
    public HttpConnectionManager(){}
    public String httpConnect(){
        OkHttpClient okHttpClient;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        URL url = null;
        String charset = "UTF-8";
        int response_code;
        String uri = urlMaker.getUri();
        if (urlMaker.getMethod().equals("GET") && !urlMaker.getMethod().equals("read_data")) {
            //in read data we do not need parameters
            uri += "?" + urlMaker.getEncodedParams();
        }
        try {
            url = new URL(uri);
            okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try (Response response = okHttpClient.newCall(request).execute()){
                content = response.body().string();
            } catch (Exception exception){
                Log.d("okHttpException", exception.toString());
            }
            /*
                if you want use HttpURLConnection
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(false);
                httpURLConnection.setRequestMethod(urlMaker.getMethod());
                httpURLConnection.setRequestProperty("Accept-Charset", charset);
                httpURLConnection.connect();
                response_code = httpURLConnection.getResponseCode();
                Log.d("RESPONSE_CODE_", "response_code : \n" + response_code);
                if (response_code >= 100 && response_code <= 399){
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    content = Helper.inputStreamToString(inputStream);
                    return content;
                } else {
                    Log.d("_ERROR_", "ERROR CODE : " + response_code);
                }
             */
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("_ERROR_", e.toString());
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return content;
    }
}
