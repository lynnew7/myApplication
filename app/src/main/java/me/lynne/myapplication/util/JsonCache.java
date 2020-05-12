package me.lynne.myapplication.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class JsonCache {


    public String date;
    private File jsonCacheFile;
    public String cachePath;

    public JsonCache(String path) {
        cachePath = path;
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        date = sf.format(new Date());
        Log.d("data", date);
        Log.d("path1", path);
        Log.d("getReuestCacheFileName", getReuestCacheFileName());
        jsonCacheFile = new File(getReuestCacheFileName());
    }

    public String getDiskCacheDir() {
        Log.d("path", cachePath);
        return cachePath;
    }

    public String getReuestCacheFileName(){
        return getDiskCacheDir() + "/" + date + ".json";
    }

    public Boolean checkCache() {
        return jsonCacheFile.exists();
    }

    public void deleteOldJson(){
        File cacheDir = new File(getDiskCacheDir());
        if (cacheDir.isDirectory()){
            File[] files = cacheDir.listFiles();
            if (files == null || files.length == 0) return;
            for (File file: files) {
                if(file.getName().endsWith(".json")) {
                    Log.i("deleteOldjson", file.getName());
                    file.delete();
                }
            }
        }
    }

    public String getCacheContent(){
        try {
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(jsonCacheFile);
            int len = 0;
            StringBuffer sb = new StringBuffer("");
            while((len=fis.read(buffer))>0) {
                sb.append(new String(buffer,0, len));
            }
            fis.close();
            Log.i("getCacheContent", "read json file:  "+jsonCacheFile.getName());
            return sb.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    public void updateJsonCache(String content){
        Log.d("upd", String.valueOf(checkCache()));
        if(checkCache()) return;
        deleteOldJson();
        setReuestCacheFile(content);
    }

    public void setReuestCacheFile(String content){
        if(checkCache()){
            return;
        }
        Log.d("setr", content);
        try {
            FileOutputStream out = new FileOutputStream(jsonCacheFile,false);
            out.write(content.getBytes(StandardCharsets.UTF_8));
            Log.i("TAG:","将数据写入到文件中："+ content);
            out.close();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

}
