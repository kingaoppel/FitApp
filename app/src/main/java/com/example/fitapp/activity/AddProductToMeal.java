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

import com.example.fitapp.MyProduct;
import com.example.fitapp.R;
import com.example.fitapp.remote.modelProduct.NutrientsItem;
import com.example.fitapp.remote.modelProduct.Product;
import com.example.fitapp.saveDataAboutMaeals.DayWithMeals;
import com.example.fitapp.saveDataAboutMaeals.Meal;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class AddProductToMeal extends AppCompatActivity {

    String sName = "maslo";
    private String mealName;
    Double dAmount = 100.0, dFat = 100.0, dProtein = 100.0, dCarbo = 100.0, dCalories = 100.0;
    Double dAmount2 = 100.0, dFat2 = 100.0, dProtein2 = 100.0, dCarbo2 = 100.0, dCalories2 = 100.0;
    private TextView name, calories, protein, fat, carbo;
    private MainViewModel mainViewModel;
    private TextInputEditText amount;
    private List<NutrientsItem> items = new ArrayList<>();
    private NutrientsItem nutrientsItemCal, nutrientsItemPro, nutrientsItemCarbo, nutrientsItemFat;
    private LinearLayout linearLayout;
    private Date date;
    private MyProduct myProduct = new MyProduct();

    private TextView sumCaloriesPerDay, sumProteinPerDay, sumFatPerDay, sumCarboPerDay;

    Map<String, Object> dataRef = new HashMap<>();
    List<String> lista = new ArrayList<>();

    FirebaseFirestore db;
    FirebaseUser currentUser;
    String uid;

    List<DayWithMeals> itemsDay = new ArrayList<>();

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

        mealName = mainViewModel.getMealName();
        date = mainViewModel.getDate();

        sName = mainViewModel.getProductLiveData().getValue().getName();
        name.setText(sName);
        myProduct.setName(sName);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        sumCaloriesPerDay = findViewById(R.id.sumCaloriesInAddProductToMeal);
        sumProteinPerDay = findViewById(R.id.sumProtrinsInAddProductToMeal);
        sumFatPerDay = findViewById(R.id.sumFatInAddProductToMeal);
        sumCarboPerDay = findViewById(R.id.sumCarboInAddProductToMeal);

        calories.setText("Calories: " + dCalories.toString());
        protein.setText("Proteins: " + dProtein.toString());
        fat.setText("Fats: " + dFat.toString());
        carbo.setText("Carbohydrates: " + dCarbo);
        amount.setText(dAmount.toString());

        myProduct.setCalories(dCalories);
        myProduct.setProtein(dProtein);
        myProduct.setFats(dFat);
        myProduct.setCarbs(dCarbo);
        myProduct.setAmount(dAmount);

        if (currentUser != null) {
            uid = currentUser.getUid();
            Log.d("User", uid);
        }

        itemsDay = mainViewModel.getDayWithMealsLiveData().getValue();

        if (mealName != null) {
            if (itemsDay != null) {
                for (DayWithMeals dayWithMeals : itemsDay) {
                    if ((dayWithMeals.getDate().toString()).equals(date.toString())) {
                        sumCaloriesPerDay.setText("Calories: " + dayWithMeals.getSumCallories().toString());
                        sumCarboPerDay.setText("Carbohydrates: " + dayWithMeals.getSumCarbo().toString());
                        sumFatPerDay.setText("Fats: " + dayWithMeals.getSumFats().toString());
                        sumProteinPerDay.setText("Proteins: " + dayWithMeals.getSumProteins().toString());
                    }
                }
            }
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
                        calories.setText("Calories: " + dCalories.toString());
                        myProduct.setCalories(dCalories);

                        dProtein = (Double) dataRef.get("protein");
                        protein.setText("Proteins: " + dProtein.toString());
                        myProduct.setProtein(dProtein);

                        dFat = (Double) dataRef.get("fats");
                        fat.setText("Fats: " + dFat.toString());
                        myProduct.setFats(dFat);

                        dCarbo = (Double) dataRef.get("carbs");
                        carbo.setText("Carbohydrates: " + dCarbo);
                        myProduct.setCarbs(dCarbo);
                    } else {
                        DocumentReference docRef = db.collection("products").document(uid+sName);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        dataRef = document.getData();
                                        Log.d("UserMeal", "DocumentSnapshot data: ");
                                        dCalories = (Double) dataRef.get("calories");
                                        calories.setText("Calories: " + dCalories.toString());
                                        myProduct.setCalories(dCalories);

                                        dProtein = (Double) dataRef.get("protein");
                                        protein.setText("Proteins: " + dProtein.toString());
                                        myProduct.setProtein(dProtein);

                                        dFat = (Double) dataRef.get("fats");
                                        fat.setText("Fats: " + dFat.toString());
                                        myProduct.setFats(dFat);

                                        dCarbo = (Double) dataRef.get("carbs");
                                        carbo.setText("Carbohydrates: " + dCarbo);
                                        myProduct.setCarbs(dCarbo);
                                    } else {
                                        Log.d("UserMeal", "No such document");
                                    }
                                } else {
                                    Log.d("UserMeal", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                } else {
                    Log.d("UserMeal", "get failed with ", task.getException());
                }
            }
        });

        dAmount = mainViewModel.getProductLiveData().getValue().getAmount();
        amount.setText(dAmount.toString());

        amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    double a = valueOf(amount.getText().toString());
                    double temp;
                    temp = dCalories * a / 100;
                    calories.setText("Calories: " + temp + "");
                    dCalories2 = temp;

                    temp = dProtein * a / 100;
                    protein.setText("Proteins: " + temp + "");
                    dProtein2 = temp;

                    temp = dCarbo * a / 100;
                    carbo.setText("Fats: " + temp + "");
                    dCarbo2 = temp;

                    temp = dFat * a / 100;
                    fat.setText("Carbohydrates: " + temp + "");
                    dFat2 = temp;

                    dAmount = a;

                    myProduct.setAmount((Double) a);
                }
            }
        });

        linearLayout = findViewById(R.id.linearLayoutAddNewPro);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.clearData();
                if (saveProductToMeal()) {
                    Toast.makeText(AddProductToMeal.this, name.getText() + " zostało dodane do posiłku", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddProductToMeal.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    boolean saveProductToMeal() {
        if (mealName != null) {
            if (itemsDay != null) {
                for (DayWithMeals dayWithMeals : itemsDay) {
                    if ((dayWithMeals.getDate().toString()).equals(date.toString())) {
                        Meal meal = new Meal();

                        if (mealName.equals("breakfast")) {
                            meal = dayWithMeals.getBreakfast();
                        } else if (mealName.equals("lunch")) {
                            meal = dayWithMeals.getLunch();
                        } else if (mealName.equals("dinner")) {
                            meal = dayWithMeals.getDinner();
                        } else if (mealName.equals("snack")) {
                            meal = dayWithMeals.getSnack();
                        } else if (mealName.equals("supper")) {
                            meal = dayWithMeals.getSupper();
                        }

                        if (meal == null) {
                            meal = new Meal();
                        }

                        if (meal != null) {
                            List<MyProduct> myProductList = meal.getItems();
                            if (myProductList == null) {
                                myProductList = new ArrayList<>();
                            } else {
                                for (MyProduct myProduct : myProductList) {
                                    if (myProduct.getName().equals(sName)) {
                                        myProduct.setAmount(dAmount);
                                        myProduct.setAmount(dAmount2);
                                        myProduct.setCalories(dCalories2);
                                        myProduct.setCarbs(dCarbo2);
                                        myProduct.setFats(dFat2);
                                        myProduct.setProtein(dProtein2);
                                        meal.setItems(myProductList);
                                        if (mealName.equals("breakfast")) {
                                            dayWithMeals.setBreakfast(meal);
                                        } else if (mealName.equals("lunch")) {
                                            dayWithMeals.setLunch(meal);
                                        } else if (mealName.equals("dinner")) {
                                            dayWithMeals.setDinner(meal);
                                        } else if (mealName.equals("snack")) {
                                            dayWithMeals.setSnack(meal);
                                        } else if (mealName.equals("supper")) {
                                            dayWithMeals.setSupper(meal);
                                        }
                                        mainViewModel.setDayWithMealsAndSave(itemsDay, this);
                                        return true;
                                    }
                                }
                            }
                            MyProduct myProduct = new MyProduct();
                            myProduct.setName(sName);
                            myProduct.setAmount(dAmount2);
                            myProduct.setCalories(dCalories2);
                            myProduct.setCarbs(dCarbo2);
                            myProduct.setFats(dFat2);
                            myProduct.setProtein(dProtein2);
                            myProductList.add(myProduct);
                            meal.setItems(myProductList);
                            if (mealName.equals("breakfast")) {
                                dayWithMeals.setBreakfast(meal);
                            } else if (mealName.equals("lunch")) {
                                dayWithMeals.setLunch(meal);
                            } else if (mealName.equals("dinner")) {
                                dayWithMeals.setDinner(meal);
                            } else if (mealName.equals("snack")) {
                                dayWithMeals.setSnack(meal);
                            } else if (mealName.equals("supper")) {
                                dayWithMeals.setSupper(meal);
                            }

                            mainViewModel.setDayWithMealsAndSave(itemsDay, this);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainViewModel.clearData();
    }
}