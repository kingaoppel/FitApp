package com.example.fitapp.Utils;

import java.util.HashMap;
import java.util.Map;

public class MealUtils {

    public static Map<String, String> getAutoCompleteQueryMap(String query){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("app_id", "9b16511d");
        queryMap.put("app_key", "e42f25ff071d6fabb074714838e8770a");
        queryMap.put("q", query);
        queryMap.put("limit", "10");

        return queryMap;
    }
}
