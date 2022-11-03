package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.fitapp.Utils.ValidateCredentials;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout, passwordInputLayout;
    private TextInputEditText email, password;
    private TextView loginButton, registerSwitch, errorText;
    private SignInButton googleSignInButton;

    private boolean isEmailValid = false;
    private boolean isPasswordValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInputLayout = (TextInputLayout) findViewById(R.id.emailField);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.passwordField);
        email = (TextInputEditText) findViewById(R.id.emailInputField);
        password = (TextInputEditText) findViewById(R.id.passwordInputField);
        loginButton = (TextView) findViewById(R.id.loginButton);
        registerSwitch = (TextView) findViewById(R.id.createAccountButton);
        googleSignInButton = (SignInButton) findViewById(R.id.googleSignInButton);
        errorText = (TextView) findViewById(R.id.errorText);

        initialize();

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                emailInputLayout.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInputLayout.setErrorEnabled(false);
                isEmailValid = count > 0 && ValidateCredentials.matchesEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                passwordInputLayout.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordInputLayout.setErrorEnabled(false);
                isPasswordValid = count > 0 && ValidateCredentials.matchesPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid && isPasswordValid) {
                    login();
                }else{
                    if (!isEmailValid) {
                        emailInputLayout.setError("Invalid email format");
                        emailInputLayout.setErrorEnabled(true);
                    }
                    if (!isPasswordValid) {
                        passwordInputLayout.setErrorEnabled(true);
                        passwordInputLayout.setError("Password must be at least 8 characters and contain at least one number and capital letter");
                        if(isEmailValid){
                            passwordInputLayout.requestFocus();
                        }else{
                            emailInputLayout.requestFocus();
                        }
                    }

                }
            }
        });

        registerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void initialize(){
        email.setText("");
        password.setText("");
        errorText.setVisibility(View.GONE);
    }

    private void login(){
        System.out.println("Login");
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


}