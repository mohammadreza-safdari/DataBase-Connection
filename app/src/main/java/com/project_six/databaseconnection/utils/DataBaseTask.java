package com.project_six.databaseconnection.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

public class DataBaseTask extends AsyncTask<UrlMaker, String, String> {
    UrlMaker urlMaker;
    @SuppressLint("WrongThread")
    @Override
    protected String doInBackground(UrlMaker... urlM) {
        urlMaker = urlM[0];
        HttpConnectionManager manager = new HttpConnectionManager(urlMaker);
        String s = manager.httpConnect();
        return s;
    }
    @Override
    protected void onPostExecute(String s) {
        Log.d("result : ", s);
        super.onPostExecute(s);
    }
}
