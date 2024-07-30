package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class ProfileActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Bundle received = getIntent().getExtras();
        String userString = "";
        if (received != null) {
            userString = received.getString("guest");
        }
        TextView nameAge = findViewById(R.id.name_age);
        try {
            Guest g = Guest.deserialize(userString);
            nameAge.setText(g.getName() + ","+ g.getAge());
        } catch (JSONException e) {
            nameAge.setText("Error");
        }
    }
}
