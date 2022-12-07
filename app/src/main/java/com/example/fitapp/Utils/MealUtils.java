package com.example.fitapp.Utils;

import java.util.HashMap;
import java.util.Map;

public class MealUtils {

    public static Map<String, String> getAutoCompleteQueryMap(String query){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("apiKey", "51a116becc0945f5a9b834dd2caff3a9");
        queryMap.put("query", query);
        queryMap.put("number", "10");

        return queryMap;
    }

    public static Map<String, String> getAutoCompleteQueryMapToProduct(String query){
        Map<String, String> queryMap = new HashMap<>();

        return queryMap;
    }
}
