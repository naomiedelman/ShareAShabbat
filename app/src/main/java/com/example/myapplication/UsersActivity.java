package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);

        recyclerView = findViewById(R.id.rvUser);
        userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ConstraintLayout inner = findViewById(R.id.menu);
        FloatingActionButton homeButton = inner.findViewById(R.id.home_button);
        homeButton.setOnClickListener(listener -> {
            Intent toMenu = new Intent(this, MenuActivity.class);
            startActivity(toMenu);
        });
        FloatingActionButton profileButton = inner.findViewById(R.id.profile_button);
        profileButton.setOnClickListener(listener -> {
            Intent toMenu = new Intent(this, ProfileActivity.class);
            startActivity(toMenu);
        });


        db = FirebaseFirestore.getInstance();
        fetchGuests();
    }

    private void fetchGuests() {
        db.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<User> userList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    userList.add(document.toObject(User.class));
                }
                userAdapter.setGuests(userList);
            } else {
                Log.e("MainActivity", "Error fetching guests: ", task.getException());
            }
        });
    }
}
