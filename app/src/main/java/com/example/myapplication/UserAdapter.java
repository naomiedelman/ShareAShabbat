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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users;

    public void setGuests(List<User> users) {
        this.users = users;
        notifyDataSetChanged();  // Notify the adapter that the data has changed
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public ImageView guestImage;
        public TextView name;
        public TextView age;
        public TextView location;

        public UserViewHolder(@NotNull View itemView) {
            super(itemView);
            guestImage = itemView.findViewById(R.id.user_image);
            name = itemView.findViewById(R.id.user_name);
            age = itemView.findViewById(R.id.user_age);
            location = itemView.findViewById(R.id.user_location);
        }
    }

    public UserAdapter() {
        users = new ArrayList<>();
         }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        //Glide.with(holder.itemView.getContext()).load(guest.getGuestImage()).into(holder.guestImage);
        holder.name.setText(user.getName());
        holder.age.setText(String.valueOf(user.getAge()));
        int avatar;
        switch (user.getAvatarIndex()) {
            case 1:
                avatar = R.drawable.avatar1;
                break;
            case 2:
                avatar = R.drawable.avatar2;
                break;
            case 3:
                avatar = R.drawable.avatar3;
                break;
            case 4:
                avatar = R.drawable.avatar4;
                break;
            case 5:
                avatar = R.drawable.avatar5;
                break;
            case 6:
                avatar = R.drawable.avatar6;
                break;
            case 7:
                avatar = R.drawable.avatar7;
                break;
            case 8:
                avatar = R.drawable.avatar8;
                break;
            default:
                avatar = R.drawable.avatar1;
        }
        holder.guestImage.setImageResource(avatar);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
