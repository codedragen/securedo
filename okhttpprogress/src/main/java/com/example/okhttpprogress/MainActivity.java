package com.example.okhttpprogress;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("YYYYYYYYY","YYYYYYYYYY");
        setContentView(R.layout.activity_main);
        iv= (ImageView) findViewById(R.id.iv);
        Request request = new Request.Builder()
                .url("http://192.168.1.23/test.jpg")
                .build();

        final   ProgressResponseListener listener=new ProgressResponseListener() {
              @Override
              public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
                  //System.out.println(bytesRead);
                  Log.i("bytesRead",bytesRead+"");
                  //System.out.println(contentLength);
                  Log.i("contentLength",contentLength+"");


                  System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);
              }
          };
        OkHttpClient client=new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                   Log.i("intercept","intercept");
                return response.newBuilder().body(new ProgressResponseBody(response.body(), listener)).build();
            }
        }).build();


           client.newCall(request).enqueue(new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                    Log.i("TTTTTTT",e.toString());
              }

              @Override
              public void onResponse(Call call, Response response) throws IOException {
                          final Bitmap bitmap= BitmapFactory.decodeStream(response.body().byteStream()) ;
                      iv.post(new Runnable() {
                          @Override
                          public void run() {
                              iv.setImageBitmap(bitmap);
                          }
                      });

              }
          });

        DownloadManager dm= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request1=new DownloadManager.Request(Uri.parse("http://192.168.1.23/test.jpg"));
       request1.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE| DownloadManager.Request.NETWORK_WIFI);
        request1.setDestinationInExternalFilesDir(this, Environment.getExternalStorageDirectory().getAbsolutePath(),"download");
        
    }
}
