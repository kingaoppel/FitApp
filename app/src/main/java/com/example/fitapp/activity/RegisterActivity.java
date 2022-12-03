package com.example.fitapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.R;
import com.example.fitapp.Utils.ValidateCredentials;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout, passwordInputLayout, confirmPasswordInputLayout;
    private TextInputEditText email, password, confirmPassword;
    private TextView errorText, registerButton, loginSwitch;
    private SignInButton googleSignInButton;

    private boolean isEmailValid = false;
    private boolean isPasswordValid = false;
    private boolean isConfirmPasswordValid = false;

    private static final int REQ_ONE_TAP = 367;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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

    @Override
    protected void onStart() {
        super.onStart();
        //check if user is signed in (non-null) and update UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
//            change na Main
            Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
            startActivity(intent);
            finish();
        }else{
            Log.d("RegisterActivity", "updateUI: User is not signed in");
        }
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQ_ONE_TAP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(requestCode == REQ_ONE_TAP){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }catch(ApiException e){
                Log.w("RegisterActivity", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Sign in success, update UI with the signed-in user's information
                            Log.d("RegisterActivity", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else{
                            //If sign in fails, display a message to the user
                            Log.w("RegisterActivity", "signInWithCredential:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void register() {
        if(isConfirmPasswordValid && isPasswordValid && isEmailValid){
            String s_email = email.getText().toString();
            String s_password = password.getText().toString();
            String s_confirmPassword = confirmPassword.getText().toString();

            if(s_password.equals(s_confirmPassword)){
                mAuth.createUserWithEmailAndPassword(s_email, s_password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //Sign in success, update UI with the signed-in user's information
                                    Log.d("RegisterActivity", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                }else{
                                    //If sign in fails, display a message to the user
                                    Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }else {
                confirmPasswordInputLayout.setErrorEnabled(true);
                confirmPasswordInputLayout.setError("Passwords do not match");
                confirmPasswordInputLayout.requestFocus();
            }
        }

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