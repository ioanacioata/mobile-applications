package com.example.ioana.budgetapplication.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ioana.budgetapplication.R;
import com.example.ioana.budgetapplication.model.Role;
import com.example.ioana.budgetapplication.model.User;
import com.example.ioana.budgetapplication.repository.UserRepository;

import java.util.List;

/**
 * Created by Ioana on 12/01/2018.
 */

public class UserListAdapter extends BaseAdapter {
    List<User> users;
    LayoutInflater layoutInflater;

    public UserListAdapter(List<User> users, LayoutInflater layoutInflater) {
        this.users = users;
        this.layoutInflater = layoutInflater;
    }

    public UserListAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.user_layout_list_item, null);
        TextView textView = convertView.findViewById(R.id.emailTextView);
        final String email = users.get(position).getEmail();
        textView.setText(email);
        final Button btn = convertView.findViewById(R.id.buttonMakeAdmin);
        if(users.get(position).getRole().equals(Role.ADMIN)){
            btn.setBackgroundColor(Color.DKGRAY);
        }
        final View finalConvertView = convertView;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepository userRepository = new UserRepository();
                if(users.get(position).getRole().equals(Role.ADMIN)){
                    Toast.makeText(finalConvertView.getContext(), "User " + users.get(position).getEmail() + " is already ADMIN!", Toast.LENGTH_SHORT).show();
                }
                else {
                    userRepository.makeAdmin(users.get(position));
                    Toast.makeText(finalConvertView.getContext(), "User " + users.get(position).getEmail() + " is now ADMIN!", Toast.LENGTH_SHORT).show();
                    btn.setBackgroundColor(Color.DKGRAY);
                }

            }
        });
        return convertView;
    }
}
