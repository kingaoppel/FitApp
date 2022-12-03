package com.example.fitapp.remote;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MealApiService {

    @GET("auto-complete")
    Call<List<String>> getAutocompleteByQuery(@QueryMap(encoded = false) Map<String, String> query);


}
