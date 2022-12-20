package com.example.fitapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    TextView armFirst, armNow;
    TextView chestFirst, chestNow;
    TextView waistFirst, waistNow;
    TextView hipFirst, hipNow;
    TextView thighFirst, thighNow;
    TextView calfFirst, calfNow;
    TextView addBodyMeas;
    TextView logout;

    LinearLayout linearLayoutMeasuremants;

    Double temp;
    Double tempToProgressWeight;

    private List<Double> weightList = new ArrayList<>();
    private List<Double> circumferenceArmList = new ArrayList<>();
    private List<Double> circumferenceCalfList = new ArrayList<>();
    private List<Double> circumferenceChestList = new ArrayList<>();
    private List<Double> circumferenceHipList = new ArrayList<>();
    private List<Double> circumferenceThighList = new ArrayList<>();
    private List<Double> circumferenceWaistList = new ArrayList<>();

    private ProgressBar progressBarWeight;
    private int currentProgressWeight = 0;
    private int maxProgressWeight = 100;


    @SuppressLint("MissingInflatedId")
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

        armFirst = findViewById(R.id.userPage_armFirst);
        armNow = findViewById(R.id.userPage_armNow);
        chestFirst = findViewById(R.id.userPage_chestFirst);
        chestNow = findViewById(R.id.userPage_chestNow);
        calfFirst = findViewById(R.id.userPage_calfFirst);
        calfNow = findViewById(R.id.userPage_calfNow);
        waistFirst = findViewById(R.id.userPage_waistFirst);
        waistNow = findViewById(R.id.userPage_waistNow);
        hipFirst = findViewById(R.id.userPage_hipFirst);
        hipNow = findViewById(R.id.userPage_hipNow);
        thighFirst = findViewById(R.id.userPage_thighFirst);
        thighNow = findViewById(R.id.userPage_calfNow);

        addBodyMeas = findViewById(R.id.tVaddBodyMeas);

        progressBarWeight = findViewById(R.id.progressBarWeight);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        addBodyMeas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPageActivity.this, AddBodyMeasurmentsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        linearLayoutMeasuremants = findViewById(R.id.bodyMeasuremantsLayout);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();
            Log.d("User", uid);
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
                        temp = bmiCal((Double) data.get("current_weight"), (Double) data.get("height"));
                        bmi.setText(df.format(temp) + "");
                        yourWeightTarget.setText(data.get("target_weight").toString());
                        yourWeightNowString.setText("Your weight now " + data.get("current_weight").toString());
                        yourWeightNow.setText(data.get("current_weight").toString());

                        currentProgressWeight = ((Double)data.get("current_weight")).intValue();
                        progressBarWeight.setProgress(currentProgressWeight);
                        progressBarWeight.setMax(((Double)data.get("target_weight")).intValue());

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
                        weightList = (List<Double>) dataMeas.get("weight");
                        circumferenceArmList = (List<Double>) dataMeas.get("circumference_arm");
                        circumferenceCalfList = (List<Double>) dataMeas.get("circumference_calf");
                        circumferenceChestList = (List<Double>) dataMeas.get("circumference_chest");
                        circumferenceHipList = (List<Double>) dataMeas.get("circumference_hip");
                        circumferenceThighList = (List<Double>) dataMeas.get("circumference_thigh");
                        circumferenceWaistList = (List<Double>) dataMeas.get("circumference_waist");


                        if (weightList != null) {
                            if (weightList.size() < 2) {
                                linearLayoutMeasuremants.setVisibility(View.GONE);
                                yourWeightNow.setText(weightList.get(weightList.size() - 1).toString());
                                yourWeightNowString.setText("Your weight now " + weightList.get(weightList.size() - 1).toString());


                            } else {
                                yourWeightNow.setText(weightList.get(weightList.size() - 1).toString());
                                yourWeightNowString.setText("Your weight now " + weightList.get(weightList.size() - 1).toString());

                                linearLayoutMeasuremants.setVisibility(View.VISIBLE);
//                            yourWeightNow.setText(weightList.get(0) + "");
                                String t1 = weightList.get(0).toString();
                                String t2 = weightList.get(weightList.size() - 1).toString();
                                String t3 = "";

                                currentProgressWeight = (weightList.get(0).intValue());
                                progressBarWeight.setProgress(currentProgressWeight);
                                progressBarWeight.setMax(weightList.get(weightList.size() - 1).intValue());

                                tempToProgressWeight = progressWeightFun(Double.valueOf(t1), Double.valueOf(t2));
                                if (tempToProgressWeight > 0) {
                                    t3 = " more ";
                                }

                                progressWeight.setText(tempToProgressWeight.toString() + " kg" + t3 + "from the beginning of training (" + weightList.get(0).toString() + " kg)");

                                armFirst.setText(circumferenceArmList.get(0).toString());
                                armNow.setText(circumferenceArmList.get(circumferenceArmList.size() - 1).toString() + " cm");

                                calfFirst.setText(circumferenceCalfList.get(0).toString());
                                calfNow.setText(circumferenceCalfList.get(circumferenceCalfList.size() - 1).toString() + " cm");

                                chestFirst.setText(circumferenceChestList.get(0).toString());
                                chestNow.setText(circumferenceChestList.get(circumferenceChestList.size() - 1).toString() + " cm");

                                hipFirst.setText(circumferenceHipList.get(0).toString());
                                hipNow.setText(circumferenceHipList.get(circumferenceHipList.size() - 1).toString() + " cm");

                                waistFirst.setText(circumferenceWaistList.get(0).toString());
                                waistNow.setText(circumferenceWaistList.get(circumferenceWaistList.size() - 1).toString() + " cm");

                                thighFirst.setText(circumferenceThighList.get(0).toString());
                                thighNow.setText(circumferenceThighList.get(circumferenceThighList.size() - 1).toString() + " cm");

                            }
                        } else {
                            linearLayoutMeasuremants.setVisibility(View.GONE);
                        }

                    } else {
                        Log.d("User", "No such document");
                        linearLayoutMeasuremants.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d("User", "get failed with ", task.getException());
                    linearLayoutMeasuremants.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    Double bmiCal(Double weight, Double height) {
        Double h = height / 100;
        Double bmiVal = weight / (h * h);
        Log.d("BMI", weight + "    " + height + "    " + h + "     ");

        if (bmiVal < 18.5) {
            bmi.setTextColor(0xFF375EC1);
        } else if (bmiVal > 18.5 && bmiVal < 24.9) {
            bmi.setTextColor(0xFF5AE343);
        } else if (bmiVal > 24.9) {
            bmi.setTextColor(0xFFF61B1B);
        }

        return bmiVal;
    }

    Double progressWeightFun(Double weightNow, Double weightTarget) {
        Double temp;
        temp = weightTarget - weightNow;
        return temp;
    }

    void setVisa(String s) {
        return;
        //ustawić na poczatek visability na gone w wszystkich layoutach i potem jak pobierzemy dane z firestore to ustawić na visable
    }
}