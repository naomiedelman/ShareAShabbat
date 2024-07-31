package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;

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
                            String userEmail = auth.getCurrentUser().getEmail();
                            if (userEmail == null) {
                                userEmail = "";
                            }
                            DocumentReference docRef = db.collection("users").document(userEmail);
                            docRef.get().addOnSuccessListener(documentSnapshot -> {
                                        User user = documentSnapshot.toObject(User.class);
                                        Intent toMenu = new Intent(this, MenuActivity.class);
                                        Bundle bundle = new Bundle();
                                        try {
                                            if (user != null) {
                                                bundle.putString("user", user.serialize().toString());
                                                toMenu.putExtra("user", bundle);
                                                startActivity(toMenu);
                                                finish();
                                            } else {
                                                FirebaseAuth.getInstance().signOut();
                                                Intent signUp = new Intent(this, SignUpActivity.class);
                                                startActivity(signUp);
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                            );
                        } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
