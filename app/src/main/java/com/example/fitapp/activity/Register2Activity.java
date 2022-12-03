package com.example.fitapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitapp.R;

public class Register2Activity extends AppCompatActivity {

    private Button butWeightLoss;
    private Button butPuttingOnWeight;
    private Button butMaintainingWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        butWeightLoss = findViewById(R.id.buttonWeightLoss);
        butPuttingOnWeight = findViewById(R.id.buttonPuttingOnWeight);
        butMaintainingWeight = findViewById(R.id.buttonMaintainingWeight);

        butWeightLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
                startActivity(intent);
                finish();
            }
        });

        butPuttingOnWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
                startActivity(intent);
                finish();
            }
        });

        butMaintainingWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}