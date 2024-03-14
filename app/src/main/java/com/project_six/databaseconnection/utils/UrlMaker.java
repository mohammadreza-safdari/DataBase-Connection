package com.project_six.databaseconnection.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UrlMaker {
    private String uri;
    private String method;
    private String operation;
    //Object can be string or int or double or .... so we can use Object to send any data type to server
    private Map<String, Object> params;
    //initialization
    public UrlMaker(){
        this.uri = "";
        this.method = "GET";
        this.params = new HashMap<String, Object>();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void setParameter(String key, Object value){
        params.put(key, value);
    }

    public String getEncodedParams(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : params.keySet()){
            try {
                String value = URLEncoder.encode(String.valueOf(params.get(key)), "UTF-8");
                if (stringBuilder.length() > 0){
                    stringBuilder.append('&');
                }
                stringBuilder.append(key + "=" + value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
