package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fitapp.fragments.NoteFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AddProducktActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private TextView search;
    private TextView yourMeals;
    private TextView favourite;
    private TextView notes;
    private TextView addNewProduct;
    private TextInputLayout searchInputLayout;
    private LinearLayout linearLayout;

    private NoteFragment noteFragment;

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
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        String str;

        noteFragment = new NoteFragment();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colors();
                search.setTextColor(getResources().getColor(R.color.black,null));
            }
        });

        yourMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colors();
                yourMeals.setTextColor(getResources().getColor(R.color.black,null));
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colors();
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
                linearLayout.setVisibility(View.GONE);
            }
        });

        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
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
        linearLayout.setVisibility(View.VISIBLE);
    }
}