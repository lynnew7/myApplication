package me.lynne.myapplication.util;


import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;

import me.lynne.myapplication.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpRequest {

    private JsonCache jsonCache;

    public OkHttpRequest(String path) {
        jsonCache = new JsonCache(path);
    }

    public MyCallBack callback(View view) {
        return new MyCallBack(view);
    }

    public void getResponse(String url, MyCallBack callback) {
        if( jsonCache.checkCache() ) {
            String res = jsonCache.getCacheContent();
            JSONArray jsonArray = JSON.parseArray(res);
            callback.setView(jsonArray);
            return;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public class MyCallBack implements Callback {

        private View view;

        MyCallBack(View view) {
            this.view = view;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("test", "failure");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String res = response.body().string();
            JSONArray jsonArray = JSON.parseObject(res).getJSONArray("images");
            jsonCache.updateJsonCache(jsonArray.toString());
            setView(jsonArray);
        }

        private void setView(JSONArray jsonArray){
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String url = jsonObject.getString("url");
            String title = jsonObject.getString("title");
            String copyright = jsonObject.getString("copyright");
            if(title.equals("")){
                title = copyright;
            }
            Uri uri =  Uri.parse("https://www.bing.com" + url);
            SimpleDraweeView draweeView = view.findViewById(R.id.image_view);
            draweeView.setImageURI(uri);
            TextView titleView = view.findViewById(R.id.title);
            titleView.setText(title);
            TextView copyRightView = view.findViewById(R.id.copyright);
            copyRightView.setText(copyright);

        }
    }
}






