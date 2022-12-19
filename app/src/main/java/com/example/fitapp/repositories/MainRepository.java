package com.example.fitapp.repositories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitapp.MyProduct;
import com.example.fitapp.Utils.MealUtils;
import com.example.fitapp.remote.ApiClient;
import com.example.fitapp.remote.MealApiService;
import com.example.fitapp.remote.model.Search;
import com.example.fitapp.remote.modelProduct.Product;
import com.example.fitapp.saveDataAboutMaeals.DayWithMeals;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private static MainRepository instance;
    private final MealApiService apiService;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "sharedPrefs";
    private MutableLiveData<Search> autocompleteData = new MutableLiveData<>();
    private MutableLiveData<Product> productInfo = new MutableLiveData<>();
    private MutableLiveData<List<DayWithMeals>> dayWithMealsMutableLiveData = new MutableLiveData<>(new ArrayList<>());

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
                if (response.isSuccessful()) {
                    autocompleteData.setValue(response.body());
                } else {
                    autocompleteData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                autocompleteData.setValue(null);
            }
        });
    }

    public LiveData<Search> getAutocompleteData() {
        return autocompleteData;
    }


    public void fetchProductInfo(String id) {
        productInfo.setValue(new Product());
        Map<String, String> params = MealUtils.getProductInfo();
        Call<Product> call = apiService.getProductInfo(id, params);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    productInfo.setValue(response.body());
                } else {
                    productInfo.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                productInfo.setValue(null);
            }
        });
    }

    public LiveData<Product> getProductInfo() {
        return productInfo;
    }

    public void clearData() {
        productInfo.setValue(null);
    }

    public void setNameProduct(String name) {
        Product temp = productInfo.getValue();
        if (temp == null) {
            temp = new Product();
        }
        temp.setName(name);
        productInfo.setValue(temp);
    }

    public LiveData<List<DayWithMeals>> getDayWithMealsLiveData() {
        return dayWithMealsMutableLiveData;
    }

    public void setDayWithMeals(List<DayWithMeals> dayWithMeals) {
        dayWithMealsMutableLiveData.setValue(dayWithMeals);
    }

    //save to shared prefs
    public boolean saveToSharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        List<DayWithMeals> dayWithMeals = dayWithMealsMutableLiveData.getValue();
        Gson gson = new Gson();
        String json = dayWithMeals != null ? gson.toJson(dayWithMeals) : null;

        if (json != null) {
            editor.putString("dayWithMeals", json);
            editor.apply();
            return true;
        } else
            return false;
    }

    //load from shared prefs
    public boolean loadFromSharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dayWithMeals", null);
        Type listOfDayMeals = new TypeToken<List<DayWithMeals>>() {
        }.getType();
        List<DayWithMeals> dayWithMeals = gson.fromJson(json, listOfDayMeals);
        if (dayWithMeals != null) {
            dayWithMealsMutableLiveData.setValue(dayWithMeals);
            return true;
        }
        return false;
    }

    public void addDayWithMeals(DayWithMeals dayWithMeals) {
        //add another item to the list
        List<DayWithMeals> dayWithMealsList = dayWithMealsMutableLiveData.getValue();
        if (dayWithMealsList == null) {
            dayWithMealsList = new ArrayList<>();
        }
        dayWithMealsList.add(dayWithMeals);
        dayWithMealsMutableLiveData.setValue(dayWithMealsList);
    }
}

