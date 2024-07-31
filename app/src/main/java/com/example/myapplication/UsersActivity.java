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
    public UserAdapter userAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);

        db = FirebaseFirestore.getInstance();
        fetchUsers();

        recyclerView = findViewById(R.id.rvUsers);

        ConstraintLayout inner = findViewById(R.id.menu);
        userAdapter = new UserAdapter(new ArrayList<>());
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private void fetchUsers() {
        db.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> userList = new ArrayList<User>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    User user = document.toObject(User.class);
                    userList.add(user);
                }
                userAdapter.setUsers(userList);
            } else {
                Log.e("MainActivity", "Error fetching guests: ", task.getException());
            }
        });
    }
}
