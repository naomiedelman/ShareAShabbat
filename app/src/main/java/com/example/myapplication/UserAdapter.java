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
    public List<User> userStore;

    public void setUsers(ArrayList<User> users) {
        this.userStore = new ArrayList<User>();
        this.userStore.addAll(users);
        notifyItemRangeInserted(0, this.userStore.size());
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView name;
        public TextView age;

        public UserViewHolder(@NotNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.user_image);
            name = itemView.findViewById(R.id.user_name);
            age = itemView.findViewById(R.id.user_age);
        }
    }

    public UserAdapter(ArrayList<User> userList) {
        this.userStore = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = this.userStore.get(position);
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
        holder.avatar.setImageResource(avatar);
    }

    @Override
    public int getItemCount() {
        return this.userStore.size();
    }

}
