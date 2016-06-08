package com.example.okhttpprogress;

/**
 * Created by long on 2016/6/4.
 */
public interface ProgressResponseListener {

    public void onResponseProgress(long byteread,long contentLength,boolean done);
}
