package com.example.fitapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.fitapp.R;
import com.example.fitapp.remote.modelProduct.Product;
import com.example.fitapp.viewModels.MainViewModel;

import java.security.acl.Owner;
import java.util.Locale;

public class AddProductToMeal extends AppCompatActivity {

    String sName = "maslo";
    private TextView name;
    private MainViewModel mainViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_to_meal);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        name = findViewById(R.id.addProToMealProductName);
        sName = mainViewModel.getProductLiveData().getValue().getName().toUpperCase(Locale.ROOT);
        name.setText(sName);

    }
}