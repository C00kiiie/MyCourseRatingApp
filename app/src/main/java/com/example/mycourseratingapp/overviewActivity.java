package com.example.mycourseratingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class overviewActivity extends AppCompatActivity {

    private Button signOut, javaButton, iosButton, pythonButton, androidButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        init();
    }

    private void init(){
        signOut = findViewById(R.id.signOutButton);
        javaButton = findViewById(R.id.javaButton);
        iosButton = findViewById(R.id.iosButton);
        pythonButton = findViewById(R.id.pythonButton);
        androidButton = findViewById(R.id.androidButton);
    }

    public void signOut(){
        Intent intent = new Intent(this, MainActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(intent);
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signOutButton) {
            signOut();
        } else if (i == R.id.javaButton) {
            Intent intent = new Intent(this, ratingActivity.class);
            intent.putExtra("courseButton", javaButton.getText());
            startActivity(intent);
        } else if (i == R.id.iosButton) {
            Intent intent = new Intent(this, ratingActivity.class);
            intent.putExtra("courseButton", iosButton.getText());
            startActivity(intent);
        } else if (i == R.id.pythonButton) {
            Intent intent = new Intent(this, ratingActivity.class);
            intent.putExtra("courseButton", pythonButton.getText());
            startActivity(intent);
        } else if (i == R.id.androidButton) {
            Intent intent = new Intent(this, ratingActivity.class);
            intent.putExtra("courseButton", androidButton.getText());
            startActivity(intent);
        }
    }
}
