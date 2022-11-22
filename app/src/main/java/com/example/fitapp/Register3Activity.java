package com.example.fitapp;

import static java.lang.Short.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class Register3Activity extends AppCompatActivity {

    private TextView dateOfBirth;
    private TextView weightInputField;
    private TextView heightInputField;
    private TextView targetInputField;
    private TextView additionalInfo;
    private Spinner spinner;
    private LinearLayout linearLayoutAdditionalInfo;
    private TextView createAnAccount;
    private TextView incorrectTarget;
    private TextView incorrectWeight;
    private TextView incorrectHeight;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        dateOfBirth = findViewById(R.id.dateOfBirth);
        weightInputField = findViewById(R.id.weightInputField);
        heightInputField = findViewById(R.id.heightInputField);
        targetInputField = findViewById(R.id.targetInputField);
        additionalInfo = findViewById(R.id.additionalInfo);
        spinner = findViewById(R.id.spinner);
        linearLayoutAdditionalInfo = findViewById(R.id.linLayAdditionalInfo);
        createAnAccount = findViewById(R.id.tvCreateAccount);
        incorrectWeight = findViewById(R.id.incorrectWeight);
        incorrectHeight = findViewById(R.id.incorrectHeight);
        incorrectTarget = findViewById(R.id.incorrectTarget);

        initialize();

        createAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incorrectWeight.setVisibility(View.GONE);
                incorrectHeight.setVisibility(View.GONE);
                incorrectTarget.setVisibility(View.GONE);
                if(checkDataHei() && checkDataWei() && checkDataTarg()){
                    Intent intent = new Intent(Register3Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        weightInputField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        heightInputField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        targetInputField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        additionalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linearLayoutAdditionalInfo.isShown()){
                    linearLayoutAdditionalInfo.setVisibility(View.GONE);
                }
                else{
                    linearLayoutAdditionalInfo.setVisibility(View.VISIBLE);
                }
            }
        });


        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Register3Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateOfBirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void initialize() {
        weightInputField.setText("");
        heightInputField.setText("");
        targetInputField.setText("");
        incorrectHeight.setVisibility(View.GONE);
        incorrectWeight.setVisibility(View.GONE);
        incorrectTarget.setVisibility(View.GONE);
    }

    private boolean checkDataWei(){
        String weight = weightInputField.getText().toString();
        if(weight.length()>2){
            int intWeight = valueOf(weight);
            if(intWeight>30 && intWeight<300){
                return true;
            }
            incorrectWeight.setVisibility(View.VISIBLE);
        }
        incorrectWeight.setVisibility(View.VISIBLE);
        return false;
    }

    private boolean checkDataTarg(){
        String target = targetInputField.getText().toString();
        if(target.length()>2){
            int intWeight = valueOf(target);
            if(intWeight>30 && intWeight<300){
                return true;
            }
            incorrectTarget.setVisibility(View.VISIBLE);
        }
        incorrectTarget.setVisibility(View.VISIBLE);
        return false;
    }

    private boolean checkDataHei(){
        String height = heightInputField.getText().toString();
        if(height.length()>2){
            int intWeight = valueOf(height);
            if(intWeight>100 && intWeight<220){
                return true;
            }
            incorrectHeight.setVisibility(View.VISIBLE);
        }
        incorrectHeight.setVisibility(View.VISIBLE);
        return false;
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}