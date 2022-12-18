package com.example.fitapp.remote;

import com.example.fitapp.remote.model.ResultsItem;
import com.example.fitapp.remote.model.Search;
import com.example.fitapp.remote.modelProduct.Product;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface MealApiService {

    @GET("ingredients/search")
    Call<Search> getAutocompleteByQuery(@QueryMap(encoded = false) Map<String, String> query);

    @GET("ingredients/{id}/information")
    Call<Product> getProductInfo(@Path(value = "id", encoded = false) String id, @QueryMap(encoded = false) Map<String, String> query);

}
