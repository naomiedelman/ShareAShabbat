package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GuestAdapter guestAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvGuest);
        guestAdapter = new GuestAdapter();
        recyclerView.setAdapter(guestAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        fetchGuests();
    }

    private void fetchGuests() {
        db.collection("guests").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Guest> guestList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    guestList.add(document.toObject(Guest.class));
                }
                guestAdapter.setGuests(guestList);
            } else {
                Log.e("MainActivity", "Error fetching guests: ", task.getException());
            }
        });
    }
}
