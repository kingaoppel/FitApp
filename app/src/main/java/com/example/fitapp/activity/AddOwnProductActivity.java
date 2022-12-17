package com.example.fitapp.activity;

import static java.lang.Double.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.Product;
import com.example.fitapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOwnProductActivity extends AppCompatActivity {

    private TextInputLayout nameInputField, caloriesInputField, proteinInputField, fatInputField, carboInputField;
    private TextInputEditText name, calories, protein, fat, carbo;
    private TextView sumCal, sumProtein, sumFat, sumCarbo;
    private LinearLayout linearLayout;
    Map<String, Object> dataRef = new HashMap<>();
    List<String> lista = new ArrayList<>();

    FirebaseFirestore db;
    FirebaseUser currentUser;
    String uid;
    String s_name;
    Double s_cal;
    Double s_pro;
    Double s_fat;
    Double s_carbo;
    Double s_qua = 100.0;

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

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();
            Log.d("User", uid);
        }

        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        dataRef = document.getData();
                        Log.d("User", "DocumentSnapshot data: " + dataRef.get("my_products"));
                        lista = (List<String>) dataRef.get("my_products");
                    } else {
                        Log.d("User", "No such document");
                    }
                } else {
                    Log.d("User", "get failed with ", task.getException());
                }
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whiteNewProduct();
                Toast.makeText(AddOwnProductActivity.this, name.getText() + "zostało dodane", Toast.LENGTH_LONG).show();
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
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        calories.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        protein.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        fat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        carbo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void whiteNewProduct() {
        s_name = this.name.getText().toString();
        s_cal = valueOf(this.calories.getText().toString());
        s_pro = valueOf(this.protein.getText().toString());
        s_fat = valueOf(this.fat.getText().toString());
        s_carbo = valueOf(this.carbo.getText().toString());

        if (s_cal >= 0 && s_pro >= 0 && s_fat >= 0 && s_carbo >= 0 && s_name.length() > 2) {
            Product product = new Product(s_name, uid, s_cal, s_pro, s_fat, s_carbo, s_qua);
            Map<String, Object> productValues = product.toMap();
            db.collection("products").document(uid + s_name)
                    .set(productValues)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("AddData", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("AddData", "Error writing document", e);
                        }
                    });

            lista.add(s_name);
            HashMap<String, Object> result = new HashMap<>();
            result.put("my_products", lista);
            db.collection("users").document(uid)
                    .set(result, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("AddData", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("AddData", "Error writing document", e);
                        }
                    });
        }
    }
}