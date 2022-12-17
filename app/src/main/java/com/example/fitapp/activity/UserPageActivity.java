package com.example.fitapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.fitapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserPageActivity extends AppCompatActivity {

    private static final DecimalFormat df = new DecimalFormat("0.0");

    FirebaseUser currentUser;
    FirebaseFirestore db;
    String uid;
    Map<String, Object> data = new HashMap<>();
    Map<String, Object> dataMeas = new HashMap<>();

    TextView calories;
    TextView bmi;
    TextView yourWeightNowString;
    TextView yourWeightNow;
    TextView yourWeightTarget;
    TextView progressWeight;

    Double temp;
    Long tempToProgressWeight;

    private List<Long> wei = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        calories = findViewById(R.id.userPage_caloriesTarget);
        bmi = findViewById(R.id.userPage_bmi);
        yourWeightNowString = findViewById(R.id.userPage_yourWeightNow);
        yourWeightNow = findViewById(R.id.userPage_nowWeight);
        yourWeightTarget = findViewById(R.id.userPage_targetWeight);
        progressWeight = findViewById(R.id.userPage_progressInWeight);

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
                        Log.d("User", "DocumentSnapshot data: " + data.get("name"));
                        calories.setText(df.format(data.get("amount_calories")) + "");
                        temp = bmiCal((Double) data.get("current_weight"),(Double) data.get("height"));
                        bmi.setText(df.format(temp) + "");
                        yourWeightTarget.setText(data.get("target_weight").toString());
                        yourWeightNowString.setText("Your weight now " + data.get("current_weight").toString());
                        yourWeightNow.setText(data.get("current_weight").toString());

                    } else {
                        Log.d("User", "No such document");
                    }
                } else {
                    Log.d("User", "get failed with ", task.getException());
                }
            }
        });



        DocumentReference docRefMeas = db.collection("body_measuremants").document("11" + uid);
        docRefMeas.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        dataMeas = document.getData();
                        Log.d("User", "DocumentSnapshot data: ");
                        wei = (List<Long>) dataMeas.get("weight");

                        if(wei.size() < 2){
                            //setvisability na gone
                        }
                        else{
                            yourWeightNow.setText(wei.get(0) + "");
                            tempToProgressWeight = progressWeight(wei.get(0), wei.get(wei.size()-1));
                            progressWeight.setText(tempToProgressWeight.toString() + " kg from the beginning of training" );
                        }

                    } else {
                        Log.d("User", "No such document");
                    }
                } else {
                    Log.d("User", "get failed with ", task.getException());
                }
            }
        });

    }

    Double bmiCal(Double weight, Double height){
        Double h = height/100;
        Double bmiVal = weight/(h*h);
        Log.d("BMI", weight + "    " + height + "    " + h + "     ");

        if(bmiVal < 18.5){
            bmi.setTextColor(0xFF375EC1);
        }
        else if(bmiVal > 18.5 && bmiVal < 24.9){
            bmi.setTextColor(0xFF5AE343);
        }
        else if(bmiVal > 24.9){
            bmi.setTextColor(0xFFF61B1B);
        }

        return bmiVal;
    }

    Long progressWeight(Long weightNow, Long weightTarget){
        Long temp ;
        temp = weightTarget - weightNow;
        temp = weightNow - weightTarget;
        return temp;
    }

    void setVisa(String s){
        return;
        //ustawić na poczatek visability na gone w wszystkich layoutach i potem jak pobierzemy dane z firestore to ustawić na visable
    }
}