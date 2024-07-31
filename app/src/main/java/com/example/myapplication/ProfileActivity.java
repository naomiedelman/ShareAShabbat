package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;

public class ProfileActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        TextView nameAge = findViewById(R.id.name_age);
        Bundle received = getIntent().getExtras();
        String userString = "";
        if (received != null) {
            if (received.containsKey("user")) {
                userString = received.getString("user");
            }
        } else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userEmail = currentUser.getEmail();
            if (userEmail == null) {
                userEmail = "";
            }
            DocumentReference docRef = db.collection("users").document(userEmail);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                User user = documentSnapshot.toObject(User.class);
                nameAge.setText(user.getName() + ","+ user.getAge());
            });

        }

        try {
            User g = User.deserialize(userString);
            nameAge.setText(g.getName() + ","+ g.getAge());
        } catch (JSONException e) {
            nameAge.setText("");
        }

        ConstraintLayout inner = findViewById(R.id.menu);
        FloatingActionButton homeButton = inner.findViewById(R.id.home_button);
        homeButton.setOnClickListener(listener -> {
            Intent toMenu = new Intent(this, MenuActivity.class);
            startActivity(toMenu);
            finish();
        });
        FloatingActionButton profileButton = inner.findViewById(R.id.profile_button);
        profileButton.setOnClickListener(listener -> {
            Intent toMenu = new Intent(this, ProfileActivity.class);
            startActivity(toMenu);
            finish();
        });
    }
}
