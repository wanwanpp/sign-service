package com.site;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by 王萍 on 2017/4/27 0027.
 */
public class FastJsonUtil {

    public static Map<String, Object> jsonToMap(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String, Object> map = new HashMap<>();
        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {
            map.put(key, jsonObject.get(key));
        }
        return map;
    }

    public static List<Map<String, Object>> jsonToList(String jsonList) {
        List<Map<String, Object>> list = new ArrayList<>();
        JSONArray parseArray = JSONArray.parseArray(jsonList);
        for (Object object : parseArray) {
            String jsonString = JSON.toJSONString(object);
            Map<String, Object> map = jsonToMap(jsonString);
            list.add(map);
        }
        return list;
    }
}
