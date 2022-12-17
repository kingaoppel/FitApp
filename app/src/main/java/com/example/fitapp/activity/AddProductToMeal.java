package com.example.fitapp.activity;

import static java.lang.Short.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.R;
import com.example.fitapp.remote.modelProduct.NutrientsItem;
import com.example.fitapp.viewModels.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class AddProductToMeal extends AppCompatActivity {

    String sName = "maslo";
    Double dAmount = 100.0, dFat, dProtein, dCarbo, dCalories;
    private TextView name,calories,protein,fat,carbo;
    private MainViewModel mainViewModel;
    private TextInputEditText amount;
    private List<NutrientsItem> items = new ArrayList<>();
    private NutrientsItem nutrientsItemCal, nutrientsItemPro, nutrientsItemCarbo, nutrientsItemFat;
    private LinearLayout linearLayout;

    Map<String, Object> dataRef = new HashMap<>();
    List<String> lista = new ArrayList<>();

    FirebaseFirestore db;
    FirebaseUser currentUser;
    String uid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_to_meal);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        name = findViewById(R.id.addProToMealProductName);
        amount = findViewById(R.id.caloriesInputField);
        calories = findViewById(R.id.sumCalories);
        protein = findViewById(R.id.sumProtrins);
        fat = findViewById(R.id.sumFat);
        carbo = findViewById(R.id.sumCarbo);

        sName = getIntent().getStringExtra("NAME");

        //sName = mainViewModel.getProductLiveData().getValue().getName().toUpperCase(Locale.ROOT);
        name.setText(sName);


        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();
            Log.d("User", uid);
        }

        DocumentReference docRef = db.collection("products").document(sName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        dataRef = document.getData();
                        Log.d("UserMeal", "DocumentSnapshot data: ");
                        dCalories = (Double) dataRef.get("calories");
                        calories.setText("Dupa");

                        // ustawić defaultowe wartości i dopiero zmienic potem, program potrzebuje chwili na ogarniecie się
                        // dodać interface żeby mozna było wchodzić w nasze produkty
                    } else {
                        Log.d("UserMeal", "No such document");
                    }
                } else {
                    Log.d("UserMeal", "get failed with ", task.getException());
                }
            }
        });

        dAmount = mainViewModel.getProductLiveData().getValue().getAmount();
        amount.setText(dAmount.toString());
        //calories.setText("Calories: " + dataRef.get("carbs").toString());

        items = mainViewModel.getProductLiveData().getValue().getNutrition().getNutrients();
        nutrientsItemCal = items.stream().filter(s -> s.getName().equals("Calories")).findFirst().orElse(null);
        if(nutrientsItemCal != null){
            dCalories = nutrientsItemCal.getAmount();
            calories.setText("Calories: " + dCalories + "");
        }

        nutrientsItemPro = items.stream().filter(s -> s.getName().equals("Protein")).findFirst().orElse(null);
        if(nutrientsItemPro != null){
            dProtein = nutrientsItemPro.getAmount();
            protein.setText("Proteins: " + dProtein + "");
        }

        nutrientsItemCarbo = items.stream().filter(s -> s.getName().equals("Carbohydrates")).findFirst().orElse(null);
        if(nutrientsItemCarbo != null){
            dCarbo = nutrientsItemCarbo.getAmount();
            carbo.setText("Carbohydrates: " + dCarbo + "");
        }

        nutrientsItemFat = items.stream().filter(s -> s.getName().equals("Fat")).findFirst().orElse(null);
        if(nutrientsItemFat != null){
            dFat = nutrientsItemFat.getAmount();
            fat.setText("Carbohydrates: " + dFat + "");
        }

        amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    double a = valueOf(amount.getText().toString());
                    dCalories = nutrientsItemCal.getAmount() * a / 100;
                    calories.setText("Calories: " + dCalories + "");

                    dProtein = nutrientsItemPro.getAmount() * a / 100;
                    protein.setText("Proteins: " + dProtein + "");

                    dCarbo = nutrientsItemCarbo.getAmount() * a / 100;
                    carbo.setText("Carbohydrates: " + dCarbo + "");

                    dFat = nutrientsItemFat.getAmount() * a/100;
                    fat.setText("Carbohydrates: " + dFat + "");
                }
            }
        });

        linearLayout = findViewById(R.id.linearLayoutAddNewPro);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.clearData();
                Toast.makeText(AddProductToMeal.this,name.getText() + " zostało dodane do posiłku", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddProductToMeal.this, AddProducktActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}