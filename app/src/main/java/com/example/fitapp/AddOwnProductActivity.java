package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddOwnProductActivity extends AppCompatActivity {

    private TextInputLayout nameInputField, caloriesInputField, proteinInputField, fatInputField, carboInputField;

    private TextInputEditText name,calories, protein, fat, carbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_own_product);

        nameInputField = (TextInputLayout) findViewById(R.id.nameField);
        caloriesInputField = (TextInputLayout) findViewById(R.id.caloriesField);
        fatInputField = (TextInputLayout) findViewById(R.id.fatField);
        proteinInputField = (TextInputLayout) findViewById(R.id.proteinField);
        carboInputField = (TextInputLayout) findViewById(R.id.carboField);

        name = (TextInputEditText) findViewById(R.id.nameInputField);
        calories = (TextInputEditText) findViewById(R.id.caloriesInputField);
        fat = (TextInputEditText) findViewById(R.id.fatInputField);
        protein = (TextInputEditText) findViewById(R.id.proteinInputField);
        carbo = (TextInputEditText) findViewById(R.id.carboInputField);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        calories.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        protein.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        fat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        carbo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}