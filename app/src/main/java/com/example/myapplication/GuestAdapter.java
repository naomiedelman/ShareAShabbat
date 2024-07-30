package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> {
    private List<Guest> guests;

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
        notifyDataSetChanged();  // Notify the adapter that the data has changed
    }


    public static class GuestViewHolder extends RecyclerView.ViewHolder {
        public ImageView guestImage;
        public TextView name;
        public TextView age;
        public TextView sex;

        public GuestViewHolder(@NotNull View itemView) {
            super(itemView);
            guestImage = itemView.findViewById(R.id.g_Image);
            name = itemView.findViewById(R.id.g_Name);
            age = itemView.findViewById(R.id.g_Age);
            sex = itemView.findViewById(R.id.g_Sex);
        }
    }

    public GuestAdapter() {
        guests = new ArrayList<>();
         }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        Guest guest = guests.get(position);
        //Glide.with(holder.itemView.getContext()).load(guest.getGuestImage()).into(holder.guestImage);
        holder.name.setText(guest.getName());
        holder.age.setText(String.valueOf(guest.getAge()));
    }

    @Override
    public int getItemCount() {
        return guests.size();
    }
}
