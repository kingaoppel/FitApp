package com.example.fitapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fitapp.R;
import com.example.fitapp.adapters.BreakfastAdapter;
import com.example.fitapp.adapters.SearchProductAdapter;
import com.example.fitapp.viewModels.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView logoutButton;

    private Context context;
    private BreakfastAdapter breakfastAdapter;
    RecyclerView breakfast;
    private List<String> items = new ArrayList<>();
    private TextView tvbreakfast, dinner, lunch, snack, supper;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton = findViewById(R.id.logout);
        tvbreakfast = findViewById(R.id.breakfast);

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
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("1");
        items.add("2");
        items.add("3");
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