package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class MenuActivity extends AppCompatActivity {

    private Bundle receivedBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Button viewUsersButton = findViewById(R.id.view_users_button);
        viewUsersButton.setOnClickListener(listener -> {
            Intent viewUsersActivity = new Intent(this, UsersActivity.class);
            startActivity(viewUsersActivity);
        });
        Button profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(listener -> {
            Intent profileActivity = new Intent(this, ProfileActivity.class);
            Bundle receivedBundle = getIntent().getExtras();
            if (receivedBundle != null) {
                Bundle b = receivedBundle.getBundle("guest");
                String guest = b.getString("guest");
                profileActivity.putExtra("guest", guest);
                startActivity(profileActivity);
            }
        });
    }
}