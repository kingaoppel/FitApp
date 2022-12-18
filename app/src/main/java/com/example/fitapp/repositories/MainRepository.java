package com.example.fitapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitapp.MyProduct;
import com.example.fitapp.Utils.MealUtils;
import com.example.fitapp.remote.ApiClient;
import com.example.fitapp.remote.MealApiService;
import com.example.fitapp.remote.model.Search;
import com.example.fitapp.remote.modelProduct.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private static MainRepository instance;
    private final MealApiService apiService;
    private MutableLiveData<Search> autocompleteData = new MutableLiveData<>();
    private MutableLiveData<Product> productInfo = new MutableLiveData<>();

    public static MainRepository getInstance() {
        if (instance == null) {
            instance = new MainRepository();
        }
        return instance;
    }

    private MainRepository() {
        apiService = ApiClient.getRetrofit().create(MealApiService.class);
    }


    //TODO: Add methods to get data from API

    public void getAutocompleteByQuery(String query) {
        autocompleteData.setValue(new Search());
        Map<String, String> params = MealUtils.getAutoCompleteQueryMap(query);
        Call<Search> call = apiService.getAutocompleteByQuery(params);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if(response.isSuccessful()){
                    autocompleteData.setValue(response.body());
                }else{
                    autocompleteData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                autocompleteData.setValue(null);
            }
        });
    }

    public LiveData<Search> getAutocompleteData(){
        return autocompleteData;
    }


    public void fetchProductInfo(String id){
        productInfo.setValue(new Product());
        Map<String, String> params = MealUtils.getProductInfo();
        Call<Product> call = apiService.getProductInfo(id, params);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    productInfo.setValue(response.body());
                }
                else{
                    productInfo.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                productInfo.setValue(null);
            }
        });
    }

    public LiveData<Product> getProductInfo(){ return productInfo; }

    public void clearData(){
        productInfo.setValue(null);
    }

    public void setNameProduct(String name){
        Product temp = productInfo.getValue();
        if(temp == null){
            temp = new Product();
        }
        temp.setName(name);
        productInfo.setValue(temp);
    }

}

