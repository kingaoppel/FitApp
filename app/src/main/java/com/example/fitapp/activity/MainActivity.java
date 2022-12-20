package com.example.fitapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fitapp.MyProduct;
import com.example.fitapp.R;
import com.example.fitapp.adapters.BreakfastAdapter;
import com.example.fitapp.remote.modelProduct.Product;
import com.example.fitapp.repositories.MainRepository;
import com.example.fitapp.saveDataAboutMaeals.DayWithMeals;
import com.example.fitapp.viewModels.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView logoutButton;
    private TextView date;

    private Context context;
    private BreakfastAdapter breakfastAdapter;
    RecyclerView breakfast;
    private List<String> items = new ArrayList<>();
    private TextView tvbreakfast, dinner, lunch, snack, supper, calo, fat, carbo, protein;
    private ImageView addMealToBreakfast, bodyMeasPage, userPage;
    private static final DecimalFormat df = new DecimalFormat("0.0");
    private Double caloriesSumPerDay = 0.0;
    private Double fatsSumPerDay = 0.0;
    private Double carbohydratesSumPerDay = 0.0;
    private Double proteinsSumPerDay = 0.0;

    FirebaseUser currentUser;
    FirebaseFirestore db;
    String uid;
    Map<String, Object> data = new HashMap<>();
    Date theDate;
    Calendar newC;

    private MainViewModel viewModel;
    private List<DayWithMeals> itemsDay = new ArrayList<>();
    private List<MyProduct> itemsBreakfast = new ArrayList<>();
    private DayWithMeals dayWithMeals;

    private ProgressBar progressBar;
    private int currentProgress = 0;
    private int maxProgress = 100;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        currentProgress = 90;
        progressBar.setProgress(currentProgress);
        progressBar.setMax(100);


        logoutButton = findViewById(R.id.logout);
        tvbreakfast = findViewById(R.id.breakfast);
        calo = findViewById(R.id.tvAmountOfCalories);
        protein = findViewById(R.id.tvAmountOfProteins);
        fat = findViewById(R.id.tvAmountOfFats);
        carbo = findViewById(R.id.tvAmountOfCarbo);
        addMealToBreakfast = findViewById(R.id.but_addBreakfastToMeal);

        bodyMeasPage = findViewById(R.id.menu);
        userPage = findViewById(R.id.menu_person);
        date = findViewById(R.id.tvDateOfDay);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (currentUser != null) {
            uid = currentUser.getUid();
            Log.d("User", uid);
            viewModel.loadFromSharedPrefs(this);
        }

        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        data = document.getData();
                        Log.d("User", "DocumentSnapshot data: " + data.get("amount_calories"));
                        //Integer temp = (Integer) data.get("amount_calories");
                        caloriesSumPerDay = (Double) data.get("amount_calories");
                        fatsSumPerDay = (Double) data.get("amount_fats");
                        carbohydratesSumPerDay = (Double) data.get("amount_carbs");
                        proteinsSumPerDay = (Double) data.get("amount_proteins");
                        calo.setText("Calories : " + df.format(caloriesSumPerDay).toString());
                        protein.setText("Proteins : " + df.format(proteinsSumPerDay).toString());
                        fat.setText("Fats : " + df.format(fatsSumPerDay).toString());
                        carbo.setText("Carbohydrates: " + df.format(carbohydratesSumPerDay).toString());
                    } else {
                        Log.d("User", "No such document");
                    }
                } else {
                    Log.d("User", "get failed with ", task.getException());
                }
            }
        });

        bodyMeasPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBodyMeasurmentsActivity.class);
                startActivity(intent);
            }
        });

        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserPageActivity.class);
                startActivity(intent);
            }
        });

        addMealToBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setMealName("breakfast");
                viewModel.setDate(theDate);
                Intent intent = new Intent(MainActivity.this, AddProducktActivity.class);
                intent.putExtra("MEAL_NAME", "breakfast");
                intent.putExtra("DATE", theDate);
                startActivity(intent);
            }
        });

        tvbreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (breakfast.isShown()) {
                    breakfast.setVisibility(View.VISIBLE);
                    if(dayWithMeals != null && dayWithMeals.getBreakfast() != null) {
                        itemsBreakfast.clear();
                        itemsBreakfast.addAll(dayWithMeals.getBreakfast().getItems());
                        breakfastAdapter.notifyDataSetChanged();
                    }
                    //na gone
                } else {
                    breakfast.setVisibility(View.VISIBLE);
                    if(dayWithMeals != null && dayWithMeals.getBreakfast() != null) {
                        itemsBreakfast.clear();
                        itemsBreakfast.addAll(dayWithMeals.getBreakfast().getItems());
                        breakfastAdapter.notifyDataSetChanged();
                    }

                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this, AddProducktActivity.class));
            }
        });

        items.clear();
        items.add("1");
        items.add("2");
        items.add("3");

        breakfast = findViewById(R.id.rv_breakfast);
        breakfastAdapter = new BreakfastAdapter(MainActivity.this, itemsBreakfast);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        breakfast.setLayoutManager(manager);
        breakfast.setAdapter(breakfastAdapter);

        Calendar myCal = Calendar.getInstance();
        newC = (Calendar) myCal.clone();
        date.setText(myCal.get(Calendar.DAY_OF_MONTH) + "." + (myCal.get(Calendar.MONTH) + 1) + "." + myCal.get(Calendar.YEAR));
        newC.set(Calendar.YEAR, myCal.get(Calendar.YEAR));
        newC.set(Calendar.MONTH, myCal.get(Calendar.MONTH) + 1);
        newC.set(Calendar.DAY_OF_MONTH, myCal.get(Calendar.DAY_OF_MONTH));
        newC.set(Calendar.HOUR, 0);
        newC.set(Calendar.MINUTE, 0);
        newC.set(Calendar.SECOND, 0);

        theDate = newC.getTime();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                newC = (Calendar) c.clone();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                newC.set(Calendar.YEAR, year);
                                newC.set(Calendar.MONTH, monthOfYear);
                                newC.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                newC.set(Calendar.HOUR, 0);
                                newC.set(Calendar.MINUTE, 0);
                                newC.set(Calendar.SECOND, 0);

                                theDate = newC.getTime();
                                long time_val = newC.getTimeInMillis();
                                String formatted_date = (DateFormat.format("EEEE", time_val))
                                        .toString();
                                date.setText(formatted_date + ", " + dayOfMonth + "." + (monthOfYear + 1) + "." + year);

                                newDayWithMeals(theDate);

                                itemsDay = viewModel.getDayWithMealsLiveData().getValue();
                                if (itemsDay == null) {
                                    itemsDay = new ArrayList<>();
                                }
                                for (DayWithMeals day : itemsDay) {
                                    if ((day.getDate().toString()).equals(theDate.toString())) {
                                        dayWithMeals = day;
                                        if (dayWithMeals != null) {
                                            calo.setText("Calories : " + df.format(dayWithMeals.getSumCallories()) + " / " + df.format(caloriesSumPerDay) + "");
                                            protein.setText("Proteins : " + df.format(dayWithMeals.getSumProteins()) + " / " + df.format(proteinsSumPerDay).toString());
                                            fat.setText("Fats : " + df.format(dayWithMeals.getSumFats()) + " / " + df.format(fatsSumPerDay).toString());
                                            carbo.setText("Carbohydrates: " + df.format(dayWithMeals.getSumCarbo()) + " / " + df.format(carbohydratesSumPerDay).toString());
                                            itemsBreakfast.clear();
                                            if(dayWithMeals.getBreakfast() != null){
                                                itemsBreakfast = dayWithMeals.getBreakfast().getItems();
                                                breakfastAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        viewModel.getDayWithMealsLiveData().observe(this, new Observer<List<DayWithMeals>>() {
            @Override
            public void onChanged(List<DayWithMeals> dayWithMealsList) {
                if (dayWithMealsList == null) {
                    dayWithMealsList = new ArrayList<>();
                }

                for (DayWithMeals day : dayWithMealsList) {
                    if ((day.getDate().toString()).equals(theDate.toString())) {
                        dayWithMeals = day;
                        if (dayWithMeals != null) {
                            calo.setText("Calories : " + df.format(dayWithMeals.getSumCallories()) + " / " + df.format(caloriesSumPerDay) + "");
                            protein.setText("Proteins : " + df.format(dayWithMeals.getSumProteins()) + " / " + df.format(proteinsSumPerDay).toString());
                            fat.setText("Fats : " + df.format(dayWithMeals.getSumFats()) + " / " + df.format(fatsSumPerDay).toString());
                            carbo.setText("Carbohydrates: " + df.format(dayWithMeals.getSumCarbo()) + " / " + df.format(carbohydratesSumPerDay).toString());
                            itemsBreakfast.clear();
                            if(dayWithMeals.getBreakfast() != null){
                                itemsBreakfast = dayWithMeals.getBreakfast().getItems();
                                breakfastAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

            }
        });

    }

    boolean newDayWithMeals(Date date) {
        itemsDay = viewModel.getDayWithMealsLiveData().getValue();
        if (itemsDay == null) {
            itemsDay = new ArrayList<>();
        }
        for (DayWithMeals dayWithMeals : itemsDay) {
            if ((dayWithMeals.getDate().toString()).equals(date.toString())) {
                return false;
            }
        }
        DayWithMeals dayWithMeals = new DayWithMeals();
        dayWithMeals.setDate(date);
        itemsDay.add(dayWithMeals);
        viewModel.setDayWithMealsAndSave(itemsDay, this);
        return true;
    }
}