package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
                            DocumentReference docRef = db.collection("guests").document(userEmail);
                            docRef.get().addOnSuccessListener(documentSnapshot -> {
                                        Guest guest = documentSnapshot.toObject(Guest.class);
                                        Intent toMenu = new Intent(this, MenuActivity.class);
                                        Bundle bundle = new Bundle();
                                        try {
                                            if (guest != null) {
                                                bundle.putString("guest", guest.serialize().toString());
                                                toMenu.putExtra("guest", bundle);
                                                startActivity(toMenu);
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
