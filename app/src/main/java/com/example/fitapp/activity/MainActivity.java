package com.example.fitapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.MyProduct;
import com.example.fitapp.R;
import com.example.fitapp.adapters.MealAdapter;
import com.example.fitapp.interfaces.OnMealAdapterItemClickInterface;
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView logoutButton;
    private TextView date;
    private TextView tvprogressBar, tvProgressBarFats, tvProgressBarCarbs, tvProgressBarProteins;
    private TextView breakfastCalories, snackCalories, lunchCalories, dinnerCalories, supperCalories;

    private MealAdapter breakfastAdapter, lunchAdapter, dinnerAdapter, snackAdapter, supperAdapter;
    private OnMealAdapterItemClickInterface onMealAdapterItemClickInterface;
    RecyclerView rvBreakfast, rvLunch, rvDinner, rvSnack, rvSupper;
    private List<String> items = new ArrayList<>();
    private TextView tvbreakfast, dinner, lunch, snack, supper, calo, fat, carbo, protein;
    private ImageView addMealToBreakfast, addMealToSnack, addMealToDinner, addMealToSupper, addMealToLunch, bodyMeasPage, userPage;
    private static final DecimalFormat df = new DecimalFormat("0");
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
    private List<MyProduct> itemsLunch = new ArrayList<>();
    private List<MyProduct> itemsDinner = new ArrayList<>();
    private List<MyProduct> itemsSnack = new ArrayList<>();
    private List<MyProduct> itemsSupper = new ArrayList<>();

    private DayWithMeals dayWithMeals;

    private ProgressBar progressBar;
    private int currentProgress = 0;
    private int maxProgress = 100;

    private ProgressBar progressBarCarbs;
    private int currentProgressCarbs = 0;
    private int maxProgressCarbs = 100;

    private ProgressBar progressBarFats;
    private int currentProgressFats = 0;
    private int maxProgressFats = 100;

    private ProgressBar progressBarPro;
    private int currentProgressPro = 0;
    private int maxProgressPro = 100;

    String mealName;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBarPro = findViewById(R.id.progressBarProtein);
        progressBarFats = findViewById(R.id.progressBarFats);
        progressBarCarbs = findViewById(R.id.progressBarCarbs);

//        currentProgress = 90;
//        progressBar.setProgress(currentProgress);
//        progressBar.setMax(100);

        logoutButton = findViewById(R.id.logout);
        tvbreakfast = findViewById(R.id.breakfast);
        rvLunch = findViewById(R.id.rv_lunch);
        rvSnack = findViewById(R.id.rv_snack);
        rvSupper = findViewById(R.id.rv_supper);
        rvDinner = findViewById(R.id.rv_dinner);

        dinner = findViewById(R.id.dinner);
        lunch = findViewById(R.id.lunch);
        snack = findViewById(R.id.snack);
        supper = findViewById(R.id.supper);

        calo = findViewById(R.id.tvAmountOfCalories);
        protein = findViewById(R.id.tvAmountOfProteins);
        fat = findViewById(R.id.tvAmountOfFats);
        carbo = findViewById(R.id.tvAmountOfCarbo);
        addMealToBreakfast = findViewById(R.id.but_addBreakfastToMeal);
        addMealToSnack = findViewById(R.id.but_addSnackToMeal);
        addMealToDinner = findViewById(R.id.but_addDinnerToMeal);
        addMealToSupper = findViewById(R.id.but_addSupperToMeal);
        addMealToLunch = findViewById(R.id.but_addLunchToMeal);

        bodyMeasPage = findViewById(R.id.menu);
        userPage = findViewById(R.id.menu_person);
        date = findViewById(R.id.tvDateOfDay);

        tvprogressBar = findViewById(R.id.tvProgressBar);
        tvProgressBarCarbs = findViewById(R.id.tvProgressBarCarbs);
        tvProgressBarFats = findViewById(R.id.tvProgressBarFats);
        tvProgressBarProteins = findViewById(R.id.tvProgressBarProtein);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        breakfastCalories = findViewById(R.id.breakfastKcal);
        snackCalories = findViewById(R.id.snackKcal);
        lunchCalories = findViewById(R.id.lunchKcal);
        dinnerCalories = findViewById(R.id.dinnerKcal);
        supperCalories = findViewById(R.id.supperKcal);

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

                        tvprogressBar.setText(df.format(caloriesSumPerDay).toString());
                        tvProgressBarCarbs.setText(df.format(carbohydratesSumPerDay).toString());
                        tvProgressBarFats.setText(df.format(fatsSumPerDay).toString());
                        tvProgressBarProteins.setText(df.format(proteinsSumPerDay).toString());

                        currentProgress = 0;
                        progressBar.setProgress(currentProgress);
                        progressBar.setMax(caloriesSumPerDay.intValue());

                        currentProgressPro = 0;
                        progressBarPro.setProgress(currentProgressPro);
                        progressBarPro.setMax(proteinsSumPerDay.intValue());

                        currentProgressFats = 0;
                        progressBarFats.setProgress(currentProgressFats);
                        progressBarFats.setMax(fatsSumPerDay.intValue());

                        currentProgressCarbs = 0;
                        progressBarCarbs.setProgress(currentProgressCarbs);
                        progressBarCarbs.setMax(carbohydratesSumPerDay.intValue());

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
                mealName = "breakfast";
                viewModel.setMealName(mealName);
                viewModel.setDate(theDate);
                Intent intent = new Intent(MainActivity.this, AddProducktActivity.class);
                intent.putExtra("MEAL_NAME", mealName);
                intent.putExtra("DATE", theDate);
                startActivity(intent);
            }
        });

        addMealToDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealName = "dinner";
                viewModel.setMealName(mealName);
                viewModel.setDate(theDate);
                Intent intent = new Intent(MainActivity.this, AddProducktActivity.class);
                intent.putExtra("MEAL_NAME", mealName);
                intent.putExtra("DATE", theDate);
                startActivity(intent);
            }
        });

        addMealToLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealName = "lunch";
                viewModel.setMealName(mealName);
                viewModel.setDate(theDate);
                Intent intent = new Intent(MainActivity.this, AddProducktActivity.class);
                intent.putExtra("MEAL_NAME", mealName);
                intent.putExtra("DATE", theDate);
                startActivity(intent);
            }
        });

        addMealToSupper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealName = "supper";
                viewModel.setMealName(mealName);
                viewModel.setDate(theDate);
                Intent intent = new Intent(MainActivity.this, AddProducktActivity.class);
                intent.putExtra("MEAL_NAME", mealName);
                intent.putExtra("DATE", theDate);
                startActivity(intent);
            }
        });

        addMealToSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealName = "snack";
                viewModel.setMealName(mealName);
                viewModel.setDate(theDate);
                Intent intent = new Intent(MainActivity.this, AddProducktActivity.class);
                intent.putExtra("MEAL_NAME", mealName);
                intent.putExtra("DATE", theDate);
                startActivity(intent);
            }
        });

        tvbreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rvBreakfast.isShown()) {
                    rvBreakfast.setVisibility(View.GONE);
                    mealName = "breakfast";
                } else {
                    rvBreakfast.setVisibility(View.VISIBLE);
                    if (dayWithMeals != null && dayWithMeals.getBreakfast() != null) {
                        itemsBreakfast.clear();
                        itemsBreakfast.addAll(dayWithMeals.getBreakfast().getItems());
                        breakfastAdapter.notifyDataSetChanged();
                        mealName = "breakfast";
                    }
                    rvDinner.setVisibility(View.GONE);
                    rvLunch.setVisibility(View.GONE);
                    rvSupper.setVisibility(View.GONE);
                    rvSnack.setVisibility(View.GONE);
                }
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rvLunch.isShown()) {
                    rvLunch.setVisibility(View.GONE);
                    mealName = "lunch";
                } else {
                    rvLunch.setVisibility(View.VISIBLE);
                    if (dayWithMeals != null && dayWithMeals.getLunch() != null) {
                        itemsLunch.clear();
                        itemsLunch.addAll(dayWithMeals.getLunch().getItems());
                        lunchAdapter.notifyDataSetChanged();
                        mealName = "lunch";
                    }
                    rvDinner.setVisibility(View.GONE);
                    rvBreakfast.setVisibility(View.GONE);
                    rvSupper.setVisibility(View.GONE);
                    rvSnack.setVisibility(View.GONE);
                }
            }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rvDinner.isShown()) {
                    rvDinner.setVisibility(View.GONE);
                    mealName = "dinner";
                } else {
                    rvDinner.setVisibility(View.VISIBLE);
                    if (dayWithMeals != null && dayWithMeals.getDinner() != null) {
                        itemsDinner.clear();
                        itemsDinner.addAll(dayWithMeals.getDinner().getItems());
                        lunchAdapter.notifyDataSetChanged();
                        mealName = "dinner";
                    }
                    rvLunch.setVisibility(View.GONE);
                    rvBreakfast.setVisibility(View.GONE);
                    rvSupper.setVisibility(View.GONE);
                    rvSnack.setVisibility(View.GONE);
                }
            }
        });

        supper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rvSupper.isShown()) {
                    rvSupper.setVisibility(View.GONE);
                    mealName = "supper";
                } else {
                    rvSupper.setVisibility(View.VISIBLE);
                    if (dayWithMeals != null && dayWithMeals.getSupper() != null) {
                        itemsSupper.clear();
                        itemsSupper.addAll(dayWithMeals.getSupper().getItems());
                        lunchAdapter.notifyDataSetChanged();
                        mealName = "supper";
                    }
                    rvLunch.setVisibility(View.GONE);
                    rvBreakfast.setVisibility(View.GONE);
                    rvDinner.setVisibility(View.GONE);
                    rvSnack.setVisibility(View.GONE);
                }
            }
        });

        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rvSnack.isShown()) {
                    rvSnack.setVisibility(View.GONE);
                    mealName = "snack";
                } else {
                    rvSnack.setVisibility(View.VISIBLE);
                    if (dayWithMeals != null && dayWithMeals.getSnack() != null) {
                        itemsSnack.clear();
                        itemsSnack.addAll(dayWithMeals.getSnack().getItems());
                        lunchAdapter.notifyDataSetChanged();
                        mealName = "snack";
                    }
                    rvLunch.setVisibility(View.GONE);
                    rvBreakfast.setVisibility(View.GONE);
                    rvDinner.setVisibility(View.GONE);
                    rvSupper.setVisibility(View.GONE);
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


        onMealAdapterItemClickInterface = new OnMealAdapterItemClickInterface() {
            @Override
            public void onMealItemClick(MyProduct product) {
                //TODO IMPLEMENT
                Toast.makeText(MainActivity.this, product.getName() + "", Toast.LENGTH_SHORT).show();
                viewModel.setNameProduct(product.getName());
                viewModel.setMealName(mealName);
                viewModel.setDate(theDate);
                Intent intent = new Intent(MainActivity.this, AddProductToMeal.class);
                startActivity(intent);
            }
        };

        rvBreakfast = findViewById(R.id.rv_breakfast);
        breakfastAdapter = new MealAdapter(MainActivity.this, itemsBreakfast, onMealAdapterItemClickInterface);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        rvBreakfast.setLayoutManager(manager);
        rvBreakfast.setAdapter(breakfastAdapter);

        rvSnack = findViewById(R.id.rv_snack);
        snackAdapter = new MealAdapter(MainActivity.this, itemsSnack, onMealAdapterItemClickInterface);
        LinearLayoutManager managerSnack = new LinearLayoutManager(MainActivity.this);
        rvSnack.setLayoutManager(managerSnack);
        rvSnack.setAdapter(snackAdapter);

        rvDinner = findViewById(R.id.rv_dinner);
        dinnerAdapter = new MealAdapter(MainActivity.this, itemsDinner, onMealAdapterItemClickInterface);
        LinearLayoutManager managerDinner = new LinearLayoutManager(MainActivity.this);
        rvDinner.setLayoutManager(managerDinner);
        rvDinner.setAdapter(dinnerAdapter);

        rvLunch = findViewById(R.id.rv_lunch);
        lunchAdapter = new MealAdapter(MainActivity.this, itemsLunch, onMealAdapterItemClickInterface);
        LinearLayoutManager managerLunch = new LinearLayoutManager(MainActivity.this);
        rvLunch.setLayoutManager(managerLunch);
        rvLunch.setAdapter(lunchAdapter);

        rvSupper = findViewById(R.id.rv_supper);
        supperAdapter = new MealAdapter(MainActivity.this, itemsSupper, onMealAdapterItemClickInterface);
        LinearLayoutManager managerSupper = new LinearLayoutManager(MainActivity.this);
        rvSupper.setLayoutManager(managerSupper);
        rvSupper.setAdapter(supperAdapter);

        Calendar myCal = Calendar.getInstance();
        newC = (Calendar) myCal.clone();
        date.setText(myCal.get(Calendar.DAY_OF_MONTH) + "." + (myCal.get(Calendar.MONTH) + 1) + "." + myCal.get(Calendar.YEAR));
        GregorianCalendar cal = new GregorianCalendar(myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH), myCal.get(Calendar.DAY_OF_MONTH));

        theDate = cal.getTime();

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

                    tvprogressBar.setText(df.format(dayWithMeals.getSumCallories()) + "/" + df.format(caloriesSumPerDay).toString());
                    tvProgressBarCarbs.setText(df.format(dayWithMeals.getSumCarbo()) + "/" + df.format(carbohydratesSumPerDay).toString());
                    tvProgressBarFats.setText(df.format(dayWithMeals.getSumFats()) + "/" + df.format(fatsSumPerDay).toString());
                    tvProgressBarProteins.setText(df.format(dayWithMeals.getSumProteins()) + "/" + df.format(proteinsSumPerDay).toString());

                    if (dayWithMeals.getBreakfast() != null)
                        breakfastCalories.setText(df.format(dayWithMeals.getBreakfast().getCalories()));
                    else
                        breakfastCalories.setText("0");

                    if (dayWithMeals.getSnack() != null)
                        snackCalories.setText(df.format(dayWithMeals.getSnack().getCalories()));
                    else
                        snackCalories.setText("0");

                    if (dayWithMeals.getLunch() != null)
                        lunchCalories.setText(df.format(dayWithMeals.getLunch().getCalories()));
                    else
                        lunchCalories.setText("0");

                    if (dayWithMeals.getDinner() != null)
                        dinnerCalories.setText(df.format(dayWithMeals.getDinner().getCalories()));
                    else
                        dinnerCalories.setText("0");

                    if (dayWithMeals.getSupper() != null)
                        supperCalories.setText(df.format(dayWithMeals.getSupper().getCalories()));
                    else
                        supperCalories.setText("0");

                    currentProgress = dayWithMeals.getSumCallories().intValue();
                    progressBar.setProgress(currentProgress);
                    progressBar.setMax(caloriesSumPerDay.intValue());

                    currentProgressPro = dayWithMeals.getSumProteins().intValue();
                    progressBarPro.setProgress(currentProgressPro);
                    progressBarPro.setMax(proteinsSumPerDay.intValue());

                    currentProgressFats = dayWithMeals.getSumFats().intValue();
                    progressBarFats.setProgress(currentProgressFats);
                    progressBarFats.setMax(fatsSumPerDay.intValue());

                    currentProgressCarbs = dayWithMeals.getSumCarbo().intValue();
                    progressBarCarbs.setProgress(currentProgressCarbs);
                    progressBarCarbs.setMax(carbohydratesSumPerDay.intValue());

                    itemsBreakfast.clear();
                    if (dayWithMeals.getBreakfast() != null) {
                        itemsBreakfast.addAll(dayWithMeals.getBreakfast().getItems());
                        breakfastAdapter.notifyDataSetChanged();
                    }
                }
            }
        }


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
                                newC.set(Calendar.HOUR_OF_DAY, 0);
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

                                            tvprogressBar.setText(df.format(dayWithMeals.getSumCallories()) + "/" + df.format(caloriesSumPerDay).toString());
                                            tvProgressBarCarbs.setText(df.format(dayWithMeals.getSumCarbo()) + "/" + df.format(carbohydratesSumPerDay).toString());
                                            tvProgressBarFats.setText(df.format(dayWithMeals.getSumFats()) + "/" + df.format(fatsSumPerDay).toString());
                                            tvProgressBarProteins.setText(df.format(dayWithMeals.getSumProteins()) + "/" + df.format(proteinsSumPerDay).toString());

                                            if (dayWithMeals.getBreakfast() != null)
                                                breakfastCalories.setText("Cal: " + df.format(dayWithMeals.getBreakfast().getCalories()) + " P: " + df.format(dayWithMeals.getBreakfast().getProteins()) + " F: " + df.format(dayWithMeals.getBreakfast().getFats()) + " C: " + df.format(dayWithMeals.getBreakfast().getCarbo()));
                                            else
                                                breakfastCalories.setText("0");

                                            if (dayWithMeals.getSnack() != null)
                                                snackCalories.setText("Cal: " + df.format(dayWithMeals.getSnack().getCalories()) + " P: " + df.format(dayWithMeals.getSnack().getProteins()) + " F: " + df.format(dayWithMeals.getSnack().getFats()) + " C: " + df.format(dayWithMeals.getSnack().getCarbo()));
                                            else
                                                snackCalories.setText("0");

                                            if (dayWithMeals.getLunch() != null)
                                                lunchCalories.setText("Cal: " + df.format(dayWithMeals.getLunch().getCalories()) + " P: " + df.format(dayWithMeals.getLunch().getProteins()) + " F: " + df.format(dayWithMeals.getLunch().getFats()) + " C: " + df.format(dayWithMeals.getLunch().getCarbo()));
                                            else
                                                lunchCalories.setText("0");

                                            if (dayWithMeals.getDinner() != null)
                                                dinnerCalories.setText("Cal: " + df.format(dayWithMeals.getDinner().getCalories()) + " P: " + df.format(dayWithMeals.getDinner().getProteins()) + " F: " + df.format(dayWithMeals.getDinner().getFats()) + " C: " + df.format(dayWithMeals.getDinner().getCarbo()));
                                            else
                                                dinnerCalories.setText("0");

                                            if (dayWithMeals.getSupper() != null)
                                                supperCalories.setText("Cal: " + df.format(dayWithMeals.getSupper().getCalories()) + " P: " + df.format(dayWithMeals.getSupper().getProteins()) + " F: " + df.format(dayWithMeals.getSupper().getFats()) + " C: " + df.format(dayWithMeals.getSupper().getCarbo()));
                                            else
                                                supperCalories.setText("0");

                                            currentProgress = dayWithMeals.getSumCallories().intValue();
                                            progressBar.setProgress(currentProgress);
                                            progressBar.setMax(caloriesSumPerDay.intValue());
                                            if (currentProgress > caloriesSumPerDay.intValue())
                                                progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                                            else
                                                progressBar.setProgress(currentProgress);

                                            currentProgressPro = dayWithMeals.getSumProteins().intValue();
                                            progressBarPro.setProgress(currentProgressPro);
                                            progressBarPro.setMax(proteinsSumPerDay.intValue());
                                            if (currentProgressPro > proteinsSumPerDay.intValue())
                                                progressBarPro.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                                            else
                                                progressBar.setProgress(currentProgress);

                                            currentProgressFats = dayWithMeals.getSumFats().intValue();
                                            progressBarFats.setProgress(currentProgressFats);
                                            progressBarFats.setMax(fatsSumPerDay.intValue());
                                            if (currentProgressFats > fatsSumPerDay.intValue())
                                                progressBarFats.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                                            else
                                                progressBar.setProgress(currentProgress);

                                            currentProgressCarbs = dayWithMeals.getSumCarbo().intValue();
                                            progressBarCarbs.setProgress(currentProgressCarbs);
                                            progressBarCarbs.setMax(carbohydratesSumPerDay.intValue());
                                            if (currentProgressCarbs > carbohydratesSumPerDay.intValue())
                                                progressBarCarbs.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                                            else
                                                progressBar.setProgress(currentProgress);

                                            itemsBreakfast.clear();
                                            if (dayWithMeals.getBreakfast() != null) {
                                                itemsBreakfast.addAll(dayWithMeals.getBreakfast().getItems());
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
                            if (dayWithMeals.getBreakfast() != null) {
                                itemsBreakfast.addAll(dayWithMeals.getBreakfast().getItems());
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