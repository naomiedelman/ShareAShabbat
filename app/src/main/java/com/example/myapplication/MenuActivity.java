package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Button viewUsersButton = findViewById(R.id.view_users_button);
        viewUsersButton.setOnClickListener(listener -> {
            Intent viewUsersActivity = new Intent(this, UsersActivity.class);
            startActivity(viewUsersActivity);
            finish();
        });
        Button profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(listener -> {
            Intent profileActivity = new Intent(this, ProfileActivity.class);
            Bundle receivedBundle = getIntent().getExtras();
            if (receivedBundle != null) {
                if (receivedBundle.containsKey("user")) {
                    Bundle b = receivedBundle.getBundle("user");
                    if (b.containsKey("user")) {
                        String user = b.getString("user");
                        profileActivity.putExtra("user", user);
                    }
                }
            }
            startActivity(profileActivity);
            finish();
        });
    }
}