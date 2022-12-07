package com.example.fitapp.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitapp.remote.model.Search;
import com.example.fitapp.remote.modelProduct.Product;
import com.example.fitapp.repositories.MainRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private final MainRepository mainRepository;
    private final LiveData<Search> autocompleteData;
    private final LiveData<Product> productLiveData;

    public MainViewModel(){
        this.mainRepository = MainRepository.getInstance();
        this.autocompleteData = mainRepository.getAutocompleteData();
        this.productLiveData = mainRepository.getProductInfo();
    }

    public void fetchAutoCompleteMeals(String query){
        this.mainRepository.getAutocompleteByQuery(query);
    }

    public LiveData<Search> getAutoCompleteMealsData(){
        return autocompleteData;
    }

    public void fetchProductInfobyId(String id){
        this.mainRepository.fetchProductInfo(id);
    }

    public LiveData<Product> getProductLiveData() {
        return productLiveData;
    }
}
