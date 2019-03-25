package com.example.mycourseratingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    public EditText emailText, passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        emailText.setText("Vic.e.simonsen@gmail.com");
        passText.setText("123456");


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }

    private void updateUI(FirebaseUser user) {
        Intent i = new Intent(this, overviewActivity.class);
        if(user != null) {
            startActivity(i);
        } else {
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

        private void createAccount (String email, String password){
                Log.d(TAG, "createAccount:" + email);
                if (!validateForm()) {
                    return;
                }

            //showProgressDialog();

            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // [START_EXCLUDE]
                            //hideProgressDialog();
                            // [END_EXCLUDE]
                        }
                    });
            // [END create_user_with_email]
        }

        private boolean validateForm () {
            boolean valid = true;

            String email = emailText.getText().toString();
            if (TextUtils.isEmpty(email)) {
                emailText.setError("Required.");
                valid = false;
            } else {
                emailText.setError(null);
            }

            String password = passText.getText().toString();
            if (TextUtils.isEmpty(password)) {
                passText.setError("Required.");
                valid = false;
            } else {
                passText.setError(null);
            }

            return valid;
        }



    private void init(){
        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailText);
        passText = findViewById(R.id.passwordText);

    }


    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.registerButton) {
            createAccount(emailText.getText().toString(), passText.getText().toString());
        } else if (i == R.id.loginButton) {
            signIn(emailText.getText().toString(), passText.getText().toString());
        }
    }
}
