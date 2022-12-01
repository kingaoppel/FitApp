package com.example.fitapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitapp.Utils.MealUtils;
import com.example.fitapp.remote.ApiClient;
import com.example.fitapp.remote.MealApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private static MainRepository instance;
    private final MealApiService apiService;
    private MutableLiveData<List<String>> autocompleteData = new MutableLiveData<>();

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
        autocompleteData.setValue(new ArrayList<>());
        Map<String, String> params = MealUtils.getAutoCompleteQueryMap(query);
        Call<List<String>> call = apiService.getAutocompleteByQuery(params);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()){
                    autocompleteData.setValue(response.body());
                }else{
                    autocompleteData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                autocompleteData.setValue(null);
            }
        });
    }

    public LiveData<List<String>> getAutocompleteData(){
        return autocompleteData;
    }

}

