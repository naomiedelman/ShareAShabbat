package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadingActivity extends AppCompatActivity {
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        setContentView(R.layout.loading);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent toActivity;
        if (currentUser == null) {
            toActivity = new Intent(this, SignUpActivity.class);
        } else {
            toActivity = new Intent(this, SignInActivity.class);
        }
        startActivity(toActivity);
        finish();
    }
}