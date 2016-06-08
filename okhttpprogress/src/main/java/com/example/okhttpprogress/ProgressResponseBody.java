package com.example.okhttpprogress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by long on 2016/6/4.
 */
public class ProgressResponseBody extends ResponseBody {
    public ProgressResponseListener listener;
    public ResponseBody body;
    BufferedSource source;
    public  ProgressResponseBody(ResponseBody body,ProgressResponseListener listener){
        this.body=body;
        this.listener=listener;
    }
    @Override
    public MediaType contentType() {
        return body.contentType();
    }

    @Override
    public long contentLength() {
        return body.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (source==null){
          source=Okio.buffer(source(body.source()));
         }

        return source;
    }

    private Source source(BufferedSource source) {
        return new ForwardingSource(source) {
            long totalBytesRead=0l;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                 long bytesread=super.read(sink, byteCount);
                totalBytesRead+=bytesread!=-1?bytesread:0;
                listener.onResponseProgress(totalBytesRead,body.contentLength(),bytesread==-1);
                return bytesread;
            }
        };
    }

}
