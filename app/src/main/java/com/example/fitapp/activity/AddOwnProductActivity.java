package com.example.fitapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddOwnProductActivity extends AppCompatActivity {

    private TextInputLayout nameInputField, caloriesInputField, proteinInputField, fatInputField, carboInputField;
    private TextInputEditText name,calories, protein, fat, carbo;
    private TextView sumCal,sumProtein,sumFat,sumCarbo;
    private LinearLayout linearLayout;

    @SuppressLint("MissingInflatedId")
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

        sumCal = findViewById(R.id.sumCalories);
        sumProtein = findViewById(R.id.sumProtrins);
        sumFat = findViewById(R.id.sumFat);
        sumCarbo = findViewById(R.id.sumCarbo);

        linearLayout = findViewById(R.id.linearLayoutAddNewPro);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddOwnProductActivity.this,name.getText() + "zostało dodane", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddOwnProductActivity.this, AddProducktActivity.class);
                startActivity(intent);
                finish();
            }
        });

        calories.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                caloriesInputField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                caloriesInputField.setErrorEnabled(false);
                sumCal.setText("Calories: " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        protein.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                proteinInputField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                proteinInputField.setErrorEnabled(false);
                sumProtein.setText("Protein: " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                fatInputField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fatInputField.setErrorEnabled(false);
                sumFat.setText("Fat: " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        carbo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                carboInputField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                carboInputField.setErrorEnabled(false);
                sumCarbo.setText("Carbohydrates: " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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