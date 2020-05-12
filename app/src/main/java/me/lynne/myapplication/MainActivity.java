package me.lynne.myapplication;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import me.lynne.myapplication.util.JsonCache;
import me.lynne.myapplication.util.OkHttpRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    final private String Tag = "tst";
    private boolean ischange = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_main);

        String url = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=8&mkt=zh-CN";

        String Tag = "test";

        String path = getDiskCacheDir();
        Log.d(Tag, "onCreate: "+ path);
        OkHttpRequest okHttpRequest = new OkHttpRequest(path);
        okHttpRequest.getResponse(url, okHttpRequest.callback(findViewById(R.id.image_view)));

//
//

    }

    private String getDiskCacheDir() {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = Objects.requireNonNull(getExternalCacheDir()).getPath();
        } else {
            cachePath = getCacheDir().getPath();
        }
        return cachePath;
    }

    public void onClick(View view) {
        Log.d("test", view.toString());

        TextView textView = findViewById(R.id.textView);

        if(ischange){
//            Log.d("t", "onClick: true");
            textView.setVisibility(View.GONE);
            }else{
//            Log.d("t", "onClick: false");
            textView.setVisibility(View.VISIBLE);
        }
        ischange = !ischange;
    }

}
