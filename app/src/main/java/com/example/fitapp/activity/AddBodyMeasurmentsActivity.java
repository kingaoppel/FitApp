package com.example.fitapp.activity;

import static java.lang.Double.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fitapp.Bodymeasurments;
import com.example.fitapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBodyMeasurmentsActivity extends AppCompatActivity {

    private TextView date;
    private TextInputLayout weightInputLayout, circumferenceArmInputLayout, circumferenceCalfInputLayout, circumferenceChestInputLayout, circumferenceHipInputLayout,circumferenceThighInputLayout, circumferenceWaistInputLayout;
    private TextInputEditText weight, circumferenceArm, circumferenceCalf, circumferenceChest, circumferenceHip,circumferenceThigh, circumferenceWaist;
    private List<Date> dateToFire = new ArrayList<>();
    private LinearLayout linearLayoutAddMeas;
    private List<Double> wei = new ArrayList<>();
    private List<Double> arm = new ArrayList<>();
    private List<Double> calf = new ArrayList<>();
    private List<Double> chest = new ArrayList<>();
    private List<Double> hip = new ArrayList<>();
    private List<Double> thigh = new ArrayList<>();
    private List<Double> waist = new ArrayList<>();

    Map<String, Object> dataMeas = new HashMap<>();

    FirebaseUser currentUser;
    FirebaseFirestore db;
    String uid;
    String addDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_body_measurments);

        date = findViewById(R.id.dateOfMeas);
        weightInputLayout = findViewById(R.id.weightField);
        circumferenceArmInputLayout = findViewById(R.id.circumferenceArmField);
        circumferenceCalfInputLayout = findViewById(R.id.circumferenceCalfField);
        circumferenceChestInputLayout = findViewById(R.id.circumferenceChestField);
        circumferenceHipInputLayout = findViewById(R.id.circumferenceHipField);
        circumferenceThighInputLayout = findViewById(R.id.circumferenceThighField);
        circumferenceWaistInputLayout = findViewById(R.id.circumferenceWaistField);

        weight = findViewById(R.id.circumferenceWeightInputField);
        circumferenceArm = findViewById(R.id.circumferenceArmInputField);
        circumferenceCalf = findViewById(R.id.circumferenceCalfInputField);
        circumferenceChest = findViewById(R.id.circumferenceChestInputField);
        circumferenceHip = findViewById(R.id.circumferenceHipInputField);
        circumferenceThigh = findViewById(R.id.circumferenceThighInputField);
        circumferenceWaist = findViewById(R.id.circumferenceWeightInputField);

        linearLayoutAddMeas = findViewById(R.id.linearLayoutAddNewBodyMeas);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null){
            uid = currentUser.getUid();
            Log.d("User",uid);
        }


        DocumentReference docRef = db.collection("body_measuremants").document("11" + uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        dataMeas = document.getData();
                        Log.d("User", "DocumentSnapshot data: " + dataMeas.get("name"));
                        wei = (List<Double>) dataMeas.get("weight");

                    } else {
                        Log.d("User", "No such document");
                    }
                } else {
                    Log.d("User", "get failed with ", task.getException());
                }
            }
        });


        linearLayoutAddMeas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whiteNewBodyMeas();
            }
        });

        weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceArm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceCalf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceChest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceHip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceThigh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        circumferenceWaist.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                dateToFire.add(c.getTime());
                addDate = (day + month + year + "");
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddBodyMeasurmentsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                addDate = (dayOfMonth + monthOfYear + year + "");
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void whiteNewBodyMeas() {
        wei.add(valueOf(this.weight.getText().toString()));
        arm.add(valueOf(this.circumferenceArm.getText().toString()));
        calf.add(valueOf(this.circumferenceCalf.getText().toString()));
        chest.add(valueOf(this.circumferenceChest.getText().toString()));
        hip.add(valueOf(this.circumferenceHip.getText().toString()));
        thigh.add(valueOf(this.circumferenceThigh.getText().toString()));
        waist.add(valueOf(this.circumferenceWaist.getText().toString()));
        Calendar cal = Calendar.getInstance();
        dateToFire.add(cal.getTime());

            Bodymeasurments bodymeasurments = new Bodymeasurments(uid,dateToFire,arm,calf,chest,hip,thigh,waist,wei);
            Map<String, Object> bodyValues = bodymeasurments.toMap();
            db.collection("body_measuremants").document("11" + uid)
                    .set(bodyValues, SetOptions.merge())
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
        }


    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}