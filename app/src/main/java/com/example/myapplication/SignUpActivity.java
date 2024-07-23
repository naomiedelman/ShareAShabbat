package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText, nameEditText, ageEditText, sexEditText;
    private CheckBox termsCheckBox;
    private Button signUpButton, signInButton, uploadImageButton;
    private ImageView logoImageView;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private Uri imageUri;  // URI for picked image


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);  // Initialize Firebase

        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        logoImageView = findViewById(R.id.logoImageView);
        Glide.with(this).load(R.drawable.shareashabatlogo).into(logoImageView);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        sexEditText = findViewById(R.id.sexEditText);
        termsCheckBox = findViewById(R.id.termsCheckBox);
        signUpButton = findViewById(R.id.signUpButton);
        signInButton = findViewById(R.id.signInButton);
        uploadImageButton = findViewById(R.id.uploadImageButton);

        uploadImageButton.setOnClickListener(v -> pickImage());

        signUpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            int age = Integer.parseInt(ageEditText.getText().toString().trim());
            String sex = sexEditText.getText().toString().trim();

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
                return;
            }
            if (!termsCheckBox.isChecked()) {
                Toast.makeText(this, "You must agree to the terms!", Toast.LENGTH_LONG).show();
                return;
            }
            if (imageUri != null) {
                uploadImageAndRegister(username, password, name, age, sex);
            } else {
                Toast.makeText(this, "Please select an image.", Toast.LENGTH_SHORT).show();
            }

        });

        signInButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(logoImageView);
        }
    }

    private void uploadImageAndRegister(String username, String password, String name, int age, String sex) {
        StorageReference fileRef = storage.getReference().child("images/" + System.currentTimeMillis() + ".jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String imageUrl = uri.toString();
            registerNewGuest(username, password, name, age, sex, imageUrl);
        })).addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Failed to upload image.", Toast.LENGTH_SHORT).show());
    }

    private void registerNewGuest(String username, String password, String name, int age, String sex, String guestImage) {
        // Assume validation passes
        auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("SignUpActivity", "User creation successful");

                        Guest newGuest = new Guest(guestImage, name, age, username, password, sex);

                        db.collection("guests").document(username).set(newGuest)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(SignUpActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                                    // Create the Intent to start MainActivity
                                    Intent mainActivityIntent = new Intent(SignUpActivity.this, MainActivity.class);

                                    // Put guest details as extras
                                    mainActivityIntent.putExtra("guestImage", guestImage);
                                    mainActivityIntent.putExtra("name", name);
                                    mainActivityIntent.putExtra("age", age);
                                    mainActivityIntent.putExtra("sex", sex);

                                    // Start MainActivity
                                    startActivity(mainActivityIntent);

                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Failed to register user.", Toast.LENGTH_SHORT).show());

                    } else {
                        Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
