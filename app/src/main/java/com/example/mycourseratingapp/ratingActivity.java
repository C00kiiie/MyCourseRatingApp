package com.example.mycourseratingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycourseratingapp.model.Rating;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ratingActivity extends AppCompatActivity {
    private static String TAG = "ratingActivity";


    public String pressedButtonNumber;
    public TextView titleText;
    public RatingBar q1, q2, q3, q4, q5;
    public TextView q1Txt, q2Txt, q3Txt, q4Txt, q5Txt;
    public String id;
    public String uId;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        init();

        pressedButtonNumber = getIntent().getExtras().getString("courseButton");
        titleText.setText(pressedButtonNumber);
        id = titleText.getText().toString();


    }

    public void onClick(View v) {

        Float q1Val = q1.getRating() * 20;
        Float q2Val = q2.getRating() * 20;
        Float q3Val = q3.getRating() * 20;
        Float q4Val = q4.getRating() * 20;
        Float q5Val = q5.getRating() * 20;

        Rating r = new Rating(id, q1Val, q2Val, q3Val, q4Val, q5Val);

        Float avgRating = (q1Val + q2Val + q3Val + q4Val + q5Val) / 5;
        String totaltResult = null;

        if (avgRating < 50) {
            totaltResult = "Failed";
        } else if (avgRating < 60) {
            totaltResult = "E";
        } else if (avgRating < 70) {
            totaltResult = "D";
        } else if (avgRating < 80) {
            totaltResult = "C";
        } else if (avgRating < 90) {
            totaltResult = "B";
        } else if (avgRating > 90) {
            totaltResult = "A";
        }

        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("Ratings").document(user.getEmail() + id).set(r).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "Updated");
                //goBack();
            }
        });

        Intent intent = getIntent();
        String[] TO = {"Vic.e.simonsen@gmail.com"};
        Intent email = new Intent(Intent.ACTION_SEND);

        email.setData(Uri.parse("mailto: "));
        email.setType("text/plain");

        email.putExtra(Intent.EXTRA_EMAIL, TO);
        email.putExtra(Intent.EXTRA_SUBJECT, "Rating of course: " + intent.getStringExtra("courseButton"));
        email.putExtra(Intent.EXTRA_TEXT, "Subject relevance: " + q1Val + "\n Teacher overall: " + q2Val +
                "\n Teacher prep: " + q3Val + "\n Amount of feedback: " + q4Val + "\n Job oppetunities: " + q5Val
                + "\n \n Average Rating: " + avgRating + " - TOTAL: " + totaltResult);

        try {
            startActivityForResult(Intent.createChooser(email, "Send email..."),0);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(ratingActivity.this, "No client avalible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        goBack();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        titleText = findViewById(R.id.ratingTitleText);
        q1Txt = findViewById(R.id.q1_txt);
        q2Txt = findViewById(R.id.q2_txt);
        q3Txt = findViewById(R.id.q3_txt);
        q4Txt = findViewById(R.id.q4_txt);
        q5Txt = findViewById(R.id.q5_txt);
        q1 = findViewById(R.id.ratingBar);
        q2 = findViewById(R.id.ratingBar2);
        q3 = findViewById(R.id.ratingBar3);
        q4 = findViewById(R.id.ratingBar4);
        q5 = findViewById(R.id.ratingBar5);

        uId = mAuth.getUid();
        id = titleText.getText().toString();


    }

    public void goBack(){
        Toast.makeText(ratingActivity.this, "Email sent",
                Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, overviewActivity.class);
        startActivity(i);
    }
}
