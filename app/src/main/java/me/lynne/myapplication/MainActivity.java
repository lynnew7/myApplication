package me.lynne.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Objects;

import me.lynne.myapplication.util.OkHttpRequest;

public class MainActivity extends AppCompatActivity {

    final private String Tag = "tst";
    private boolean issuance = true;
    private boolean memuShow = false;
    private Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_main);

        findViewById(R.id.id_ic_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalMemu();
            }
        });
        findViewById(R.id.saveimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalMemu();
            }
        });
        findViewById(R.id.setlockscreen_and_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalMemu();
            }
        });
        findViewById(R.id.setlockscreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalMemu();
            }
        });
        findViewById(R.id.sethomescreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalMemu();
            }
        });


        String url = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=8&mkt=zh-CN";

        String Tag = "test";
        String path = getDiskCacheDir();
        Log.d(Tag, "onCreate: "+ path);
        OkHttpRequest okHttpRequest = new OkHttpRequest(path);
        okHttpRequest.getResponse(url, okHttpRequest.callback(findViewById(R.id.parent)));

    }



    public void totalMemu(){
        View fab3 = findViewById(R.id.float_btn);
        View fb = findViewById(R.id.setting_text);
        if(memuShow){
            fab3.setVisibility(View.INVISIBLE);
            fb.setVisibility(View.INVISIBLE);
        }else{
            fab3.setVisibility(View.VISIBLE);
            fb.setVisibility(View.VISIBLE);
        }
        memuShow = !memuShow;
    }

    public void startAct(){
        Intent intent = new Intent(this, ScrollingActivity.class);
        startActivity(intent);
    }

    private String getDiskCacheDir() {
        String cachePath;
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

        TextView titleView = view.findViewById(R.id.title);
        TextView copyRightView = view.findViewById(R.id.copyright);

        if(issuance){
//            Log.d("t", "onClick: true");
            titleView.setVisibility(View.GONE);
            copyRightView.setVisibility(View.INVISIBLE);
            }else{
//            Log.d("t", "onClick: false");
            titleView.setVisibility(View.VISIBLE);
            copyRightView.setVisibility(View.VISIBLE);
        }
        issuance = !issuance;
    }

}
