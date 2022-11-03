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

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout, passwordInputLayout, confirmPasswordInputLayout;
    private TextInputEditText email, password, confirmPassword;
    private TextView errorText, registerButton, loginSwitch;
    private SignInButton googleSignInButton;

    private boolean isEmailValid = false;
    private boolean isPasswordValid = false;
    private boolean isConfirmPasswordValid = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInputLayout = (TextInputLayout) findViewById(R.id.emailField);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.passwordField);
        confirmPasswordInputLayout = (TextInputLayout) findViewById(R.id.retypePasswordField);
        email = (TextInputEditText) findViewById(R.id.emailInputField);
        password = (TextInputEditText) findViewById(R.id.passwordInputField);
        confirmPassword = (TextInputEditText) findViewById(R.id.retypePasswordInputField);
        errorText = (TextView) findViewById(R.id.errorText);
        registerButton = (TextView) findViewById(R.id.registerButton);
        loginSwitch = (TextView) findViewById(R.id.loginAccountButton);
        googleSignInButton = (SignInButton) findViewById(R.id.googleSignInButton);

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

        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                confirmPasswordInputLayout.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmPasswordInputLayout.setErrorEnabled(false);
                isConfirmPasswordValid = count > 0 && ValidateCredentials.matchesPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid && isPasswordValid && isConfirmPasswordValid) {
                    register();
                }else{
                    if(!isConfirmPasswordValid){
                        confirmPasswordInputLayout.setErrorEnabled(true);
                        confirmPasswordInputLayout.setError("Passwords do not match");
                        confirmPasswordInputLayout.requestFocus();
                    }
                    if(!isPasswordValid){
                        passwordInputLayout.setErrorEnabled(true);
                        passwordInputLayout.setError("Password must be at least 8 characters and contain at " +
                                "least one number and capital letter");
                        passwordInputLayout.requestFocus();
                    }
                    if(!isEmailValid){
                        emailInputLayout.setErrorEnabled(true);
                        emailInputLayout.setError("Invalid email");
                        emailInputLayout.requestFocus();
                    }
                }
            }
        });

        loginSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

    }

    private void googleSignIn() {
        //Intent signInIntent = mGoogleSignInClient.getSignInIntent();

    }

    private void register() {
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initialize() {
        email.setText("");
        password.setText("");
        confirmPassword.setText("");
        errorText.setVisibility(View.GONE);
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}