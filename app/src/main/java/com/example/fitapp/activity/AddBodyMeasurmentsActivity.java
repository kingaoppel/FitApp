package com.example.fitapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.fitapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.Calendar;

public class AddBodyMeasurmentsActivity extends AppCompatActivity {

    private TextView date;
    private TextInputLayout weightInputLayout, circumferenceArmInputLayout, circumferenceCalfInputLayout, circumferenceChestInputLayout, circumferenceHipInputLayout,circumferenceThighInputLayout, circumferenceWaistInputLayout;
    private TextInputEditText weight, circumferenceArm, circumferenceCalf, circumferenceChest, circumferenceHip,circumferenceThigh, circumferenceWaist;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_body_measurments);

        date = findViewById(R.id.dateOfMeas);
        weightInputLayout = findViewById(R.id.weightField);
        circumferenceArmInputLayout = findViewById(R.id.circumferenceArmField);
        circumferenceCalfInputLayout = findViewById(R.id.circumferenceCalfField);
        circumferenceChestInputLayout = findViewById(R.id.circumferenceChestField);
        circumferenceHipInputLayout = findViewById(R.id.circumferenceHipField);
        circumferenceThighInputLayout = findViewById(R.id.circumferenceThighField);
        circumferenceWaistInputLayout = findViewById(R.id.circumferenceWaistField);

        weight = findViewById(R.id.circumferenceWeightInputField);
        circumferenceArm = findViewById(R.id.circumferenceArmInputField);
        circumferenceCalf = findViewById(R.id.circumferenceCalfInputField);
        circumferenceChest = findViewById(R.id.circumferenceChestInputField);
        circumferenceHip = findViewById(R.id.circumferenceHipInputField);
        circumferenceThigh = findViewById(R.id.circumferenceThighInputField);
        circumferenceWaist = findViewById(R.id.circumferenceWeightInputField);

        weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceArm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceCalf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceChest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceHip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceThigh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceWaist.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddBodyMeasurmentsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}