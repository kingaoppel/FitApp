package com.example.fitapp.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitapp.remote.model.Search;
import com.example.fitapp.remote.modelProduct.Product;
import com.example.fitapp.repositories.MainRepository;
import com.example.fitapp.saveDataAboutMaeals.DayWithMeals;

import java.util.Date;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final MainRepository mainRepository;
    private final LiveData<Search> autocompleteData;
    private final LiveData<Product> productLiveData;
    private final LiveData<List<DayWithMeals>> dayWithMealsLiveData;
    private final LiveData<String> mealNameLiveData;
    private final LiveData<Date> dateLiveData;

    public MainViewModel() {
        this.mainRepository = MainRepository.getInstance();
        this.autocompleteData = mainRepository.getAutocompleteData();
        this.productLiveData = mainRepository.getProductInfo();
        this.dayWithMealsLiveData = mainRepository.getDayWithMealsLiveData();
        this.mealNameLiveData = mainRepository.getMealNameLiveData();
        this.dateLiveData = mainRepository.getDateLiveData();
    }

    public void fetchAutoCompleteMeals(String query) {
        this.mainRepository.getAutocompleteByQuery(query);
    }

    public LiveData<String> getMealNameLiveData() {
        return mealNameLiveData;
    }

    public LiveData<Date> getDateLiveData() {
        return dateLiveData;
    }

    public String getMealName() {
        return mealNameLiveData.getValue();
    }

    public Date getDate() {
        return dateLiveData.getValue();
    }

    public void setMealName(String mealName) {
        mainRepository.setMealName(mealName);
    }

    public void setDate(Date date) {
        mainRepository.setDate(date);
    }


    public LiveData<Search> getAutoCompleteMealsData() {
        return autocompleteData;
    }

    public void fetchProductInfobyId(String id) {
        this.mainRepository.fetchProductInfo(id);
    }

    public LiveData<Product> getProductLiveData() {
        return productLiveData;
    }

    public void clearData() {
        mainRepository.clearData();
    }

    public void setNameProduct(String name) {
        mainRepository.setNameProduct(name);
    }

    public LiveData<List<DayWithMeals>> getDayWithMealsLiveData() {
        return dayWithMealsLiveData;
    }

    public void setDayWithMeals(List<DayWithMeals> dayWithMeals) {
        mainRepository.setDayWithMeals(dayWithMeals);
    }

    public boolean setDayWithMealsAndSave(List<DayWithMeals> dayWithMeals, Context ctx){
        setDayWithMeals(dayWithMeals);
        return mainRepository.saveToSharedPrefs(ctx);
    }

    //add single item to list
    public boolean addDayWithMealsAndSave(DayWithMeals dayWithMeals, Context ctx){
        mainRepository.addDayWithMeals(dayWithMeals);
        return mainRepository.saveToSharedPrefs(ctx);
    }

    public boolean saveToSharedPrefs(Context context) {
        return mainRepository.saveToSharedPrefs(context);
    }

    public boolean loadFromSharedPrefs(Context context) {
        return mainRepository.loadFromSharedPrefs(context);
    }
}
