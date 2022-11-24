package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fitapp.adapters.SearchProductAdapter;
import com.example.fitapp.fragments.NoteFragment;
import com.example.fitapp.fragments.ProductListFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddProducktActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private TextView search;
    private TextView yourMeals;
    private TextView favourite;
    private TextView notes;
    private TextView addNewProduct;
    private TextView date;
    private TextInputLayout searchInputLayout;
    private TextInputEditText searchItem;
    private LinearLayout addOwnProductLayout;

    private NoteFragment noteFragment;
    private ProductListFragment productListFragment;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produckt);

        search = findViewById(R.id.tvSearch);
        yourMeals = findViewById(R.id.tvYourMeals);
        favourite = findViewById(R.id.tvFavourite);
        notes = findViewById(R.id.tvNotes);
        addNewProduct = findViewById(R.id.addOwnProduct);
        searchInputLayout = (TextInputLayout) findViewById(R.id.searchField2);
        searchItem = (TextInputEditText) findViewById(R.id.searchInputField2);
        addOwnProductLayout = (LinearLayout) findViewById(R.id.linearLayout);
        date = (TextView) findViewById(R.id.tvDateOfDay);

        String str;

        noteFragment = new NoteFragment();
        productListFragment = new ProductListFragment();



        addOwnProductLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProducktActivity.this, AddOwnProductActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colors();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, productListFragment).commit();
                search.setTextColor(getResources().getColor(R.color.black,null));
            }
        });

        yourMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colors();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, productListFragment).commit();
                yourMeals.setTextColor(getResources().getColor(R.color.black,null));
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colors();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, productListFragment).commit();
                favourite.setTextColor(getResources().getColor(R.color.black,null));
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, noteFragment).commit();
                colors();
                notes.setTextColor(getResources().getColor(R.color.black,null));
                searchInputLayout.setVisibility(View.GONE);
                addOwnProductLayout.setVisibility(View.GONE);
            }
        });

        searchItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                int dayS = c.get(Calendar.DAY_OF_WEEK);
//                JAK WYŚWIETLIĆ MON, 21.11.2022

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddProducktActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
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

    @Override
    public void onBackStackChanged() {

    }

    void colors(){
        search.setTextColor(getResources().getColor(R.color.primary,null));
        yourMeals.setTextColor(getResources().getColor(R.color.primary,null));
        favourite.setTextColor(getResources().getColor(R.color.primary,null));
        notes.setTextColor(getResources().getColor(R.color.primary,null));
        searchInputLayout.setVisibility(View.VISIBLE);
        addOwnProductLayout.setVisibility(View.VISIBLE);
    }

}