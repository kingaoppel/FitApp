package com.example.fitapp.activity;

import static java.lang.Short.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register3Activity extends AppCompatActivity {

    private TextView dateOfBirth;
    private TextInputLayout weightInputLayout, heightInputLayout, targetInputLayout;
    private TextInputEditText weight, height, target;
    private TextView additionalInfo;
    private Spinner spinner;
    private LinearLayout linearLayoutAdditionalInfo;
    private TextView createAnAccount;
    private TextView incorrectTarget;
    private TextView incorrectWeight;
    private TextView incorrectHeight;
    private RadioButton woman,man;
    private int age;
    private Date dateToSave;

    FirebaseUser currentUser;
    FirebaseFirestore db;
    String uid;

    Map<String, Object> data = new HashMap<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        dateOfBirth = findViewById(R.id.dateOfBirth);
        weight = (TextInputEditText) findViewById(R.id.weightInputField);
        height = (TextInputEditText) findViewById(R.id.heightInputField);
        target = (TextInputEditText) findViewById(R.id.targetInputField);
        weightInputLayout = (TextInputLayout) findViewById(R.id.weightField);
        heightInputLayout = (TextInputLayout) findViewById(R.id.heightField);
        targetInputLayout = (TextInputLayout) findViewById(R.id.targetField);
        additionalInfo = (TextView)  findViewById(R.id.additionalInfo);
        spinner = (Spinner) findViewById(R.id.spinner);
        linearLayoutAdditionalInfo = (LinearLayout) findViewById(R.id.linLayAdditionalInfo);
        createAnAccount = (TextView) findViewById(R.id.tvCreateAccount);
        incorrectWeight = (TextView) findViewById(R.id.incorrectWeight);
        incorrectHeight = (TextView) findViewById(R.id.incorrectHeight);
        incorrectTarget = (TextView) findViewById(R.id.incorrectTarget);
        woman = (RadioButton) findViewById(R.id.radioWoman);
        man = (RadioButton) findViewById(R.id.radioMan);

        initialize();

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null){
            uid = currentUser.getUid();
            Log.d("User",uid);
        }

        createAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incorrectWeight.setVisibility(View.GONE);
                incorrectHeight.setVisibility(View.GONE);
                incorrectTarget.setVisibility(View.GONE);
                if(checkDataHei() && checkDataWei() && checkDataTarg()){
                    saveData();
                    Intent intent = new Intent(Register3Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        height.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        target.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                weightInputLayout.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weightInputLayout.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

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
                Calendar c = Calendar.getInstance();
                Calendar d = Calendar.getInstance();
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
                                d.set(Calendar.YEAR,year);
                                d.set(Calendar.MONTH,monthOfYear);
                                d.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                Log.d("DATKA",d.toString());
                                age = LocalDate.now().getYear() - year;
                                dateToSave = d.getTime();
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void saveData(){
        Log.d("Data",weight.getText().toString());
        double hei = valueOf(height.getText().toString());
        double wei = valueOf(weight.getText().toString());
        double weiTarg = valueOf(weight.getText().toString());
        String sex;

        double calories = 2000;

        if(woman.isChecked()){
            sex = woman.getText().toString();
            calories = 655.1 + (9.563 * wei) + (1.85 * hei) - (4.676 * age);
            //PPM u kobiet = 655,1 + (9,563 x masa ciała [kg]) + (1,85 x wzrost [cm]) - (4,676 x [wiek]);
        }

        else{
            sex = man.getText().toString();
            calories = 65.5 + (13.75 * wei) + (5.003 * hei) - (6.775 * age);
            //PPM u mężczyzn = 66,5 + (13,75 x masa ciała [kg]) + (5,003 x wzrost [cm]) - (6,775 x [wiek]).
        }

        double fat = wei * 1.2;
        double protein = wei * 1.8;
        double carbs = wei * 3.3;
//        data.put("height", hei);

        List<String> fav = new ArrayList<>();
        List<String> mypro = new ArrayList<>();

        db.collection("users").document(uid)
                .update("height",hei,
                        "current_weight",wei,
                        "target_weight",weiTarg,
                        "amount_calories",calories,
                        "amount_fats",fat,
                        "amount_proteins",protein,
                        "amount_carbs",carbs,
                        "sex",sex,
                        "date_of_bitrth",dateToSave,
                        "age",age,
                        "favourite",fav,
                        "my_products",mypro
                        )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("AddData2", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("AddData2", "Error writing document", e);
                    }
                });
    }

    private void initialize() {
        weight.setText("");
        height.setText("");
        target.setText("");
        incorrectHeight.setVisibility(View.GONE);
        incorrectWeight.setVisibility(View.GONE);
        incorrectTarget.setVisibility(View.GONE);
    }

    private boolean checkDataWei(){
        String weight = this.weight.getText().toString();
        if(weight.length()>=2){
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
        String target = this.target.getText().toString();
        if(target.length()>=2){
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
        String height = this.height.getText().toString();
        if(height.length()>=2){
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