package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class SignInActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String email = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Fetch user details from Firestore
                            db.collection("guests").whereEqualTo("Username", email).get()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful() && !task1.getResult().isEmpty()) {
                                            for (QueryDocumentSnapshot document : task1.getResult()) {
                                                Guest guest = document.toObject(Guest.class);
                                                Intent mainActivityIntent = new Intent(SignInActivity.this, MainActivity.class);
                                                mainActivityIntent.putExtra("guestImage", guest.getGuestImage());
                                                mainActivityIntent.putExtra("name", guest.getName());
                                                mainActivityIntent.putExtra("age", guest.getAge());
                                                mainActivityIntent.putExtra("sex", guest.getSex());
                                                startActivity(mainActivityIntent);
                                                finish();
                                            }
                                        } else {
                                            Toast.makeText(SignInActivity.this, "Failed to retrieve user details.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
