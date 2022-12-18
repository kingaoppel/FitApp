package com.example.fitapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fitapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Register2Activity extends AppCompatActivity {

    private Button butWeightLoss;
    private Button butPuttingOnWeight;
    private Button butMaintainingWeight;

    FirebaseUser currentUser;
    FirebaseFirestore db;
    String uid;

    Map<String, Object> data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        butWeightLoss = findViewById(R.id.buttonWeightLoss);
        butPuttingOnWeight = findViewById(R.id.buttonPuttingOnWeight);
        butMaintainingWeight = findViewById(R.id.buttonMaintainingWeight);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();
            Log.d("User", uid);
        }

        butWeightLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.put("target", butWeightLoss.getText().toString());
                db.collection("users").document(uid)
                        .set(data)
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
                Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
                startActivity(intent);
                finish();
            }
        });

        butPuttingOnWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.put("target", butPuttingOnWeight.getText().toString());
                db.collection("users").document(uid)
                        .set(data)
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
                Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
                startActivity(intent);
                finish();
            }
        });

        butMaintainingWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.put("target", butMaintainingWeight.getText().toString());
                db.collection("users").document(uid)
                        .set(data)
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
                Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}