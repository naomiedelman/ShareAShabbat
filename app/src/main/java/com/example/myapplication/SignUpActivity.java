package com.example.myapplication;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText, nameEditText, ageEditText, sexEditText;
    private RadioGroup userMode;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private FusedLocationProviderClient fusedLocationClient;
    private Address location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);  // Initialize Firebase

        setContentView(R.layout.sign_up);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        userMode = findViewById(R.id.modeRadioGroup);
        Button signUpButton = findViewById(R.id.signUpButton);
        Button signInButton = findViewById(R.id.signInButton);

        signUpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            int age = Integer.parseInt(ageEditText.getText().toString().trim());
            int userModeId = userMode.getCheckedRadioButtonId();
            RadioButton userModeSelected = findViewById(userModeId);
            UserType userType = UserType.getUserType(userModeSelected.getText().toString().toLowerCase());


            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
                return;
            }

            register(username, password, name, age, userType);

        });

        signInButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
        });
    }

    private void register(String username, String password, String name, int age, UserType userType) {
        // Assume validation passes
        auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("SignUpActivity", "User creation successful");

                        User newUser = new User(name, age, username, userType, 0);

                        db.collection("users").document(username).set(newUser)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(SignUpActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                                    // Create the Intent to start MainActivity
                                    Intent menu = new Intent(SignUpActivity.this, MenuActivity.class);

                                    // Start MainActivity
                                    startActivity(menu);

                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Failed to register user.", Toast.LENGTH_SHORT).show());

                    } else {
                        Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, task -> {
                    Toast.makeText(SignUpActivity.this, "Signup Failed " + task.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

}
