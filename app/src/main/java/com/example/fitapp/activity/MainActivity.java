package com.example.fitapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitapp.R;
import com.example.fitapp.adapters.BreakfastAdapter;
import com.example.fitapp.adapters.SearchProductAdapter;
import com.example.fitapp.viewModels.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView logoutButton;

    private Context context;
    private BreakfastAdapter breakfastAdapter;
    RecyclerView breakfast;
    private List<String> items = new ArrayList<>();
    private TextView tvbreakfast, dinner, lunch, snack, supper, calo,fat,carbo,protein;
    private ImageView addMealToBreakfast, userPage;

    FirebaseUser currentUser;
    FirebaseFirestore db;
    String uid;
    Map<String, Object> data = new HashMap<>();

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton = findViewById(R.id.logout);
        tvbreakfast = findViewById(R.id.breakfast);
        calo = findViewById(R.id.tvAmountOfCalories);
        protein = findViewById(R.id.tvAmountOfProteins);
        fat = findViewById(R.id.tvAmountOfFats);
        carbo = findViewById(R.id.tvAmountOfCarbo);
        addMealToBreakfast = findViewById(R.id.but_addBreakfastToMeal);

        userPage = findViewById(R.id.menu_user);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null){
            uid = currentUser.getUid();
            Log.d("User",uid);
        }
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        data = document.getData();
                        Log.d("User", "DocumentSnapshot data: " + data.get("amount_calories"));
                        calo.setText("Calories : " + data.get("amount_calories").toString());
                        protein.setText("Proteins : " + data.get("amount_proteins").toString());
                        fat.setText("Fats : " + data.get("amount_fats").toString());
                        carbo.setText("Carbohydrates: " + data.get("amount_carbs").toString());
                    } else {
                        Log.d("User", "No such document");
                    }
                } else {
                    Log.d("User", "get failed with ", task.getException());
                }
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
                Intent intent = new Intent(MainActivity.this, AddProducktActivity.class);
                startActivity(intent);
            }
        });

        tvbreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(breakfast.isShown()){
                    breakfast.setVisibility(View.GONE);
                }
                else{
                    breakfast.setVisibility(View.VISIBLE);
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
        breakfastAdapter = new BreakfastAdapter(MainActivity.this, items);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        breakfast.setLayoutManager(manager);
        breakfast.setAdapter(breakfastAdapter);

    }
}