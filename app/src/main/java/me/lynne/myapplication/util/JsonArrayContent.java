package me.lynne.myapplication.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonArrayContent {

    private JsonCache jsonCache;

    public JsonArrayContent(String path) {
        jsonCache = new JsonCache(path);
    }

    public JsonCache getJsonCache() {
        return jsonCache;
    }

    public JSONArray getJsonArray(){
        String content = jsonCache.getCacheContent();
        return JSONObject.parseArray(content);
    }
}
