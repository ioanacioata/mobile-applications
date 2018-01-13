package com.example.ioana.budgetapplication.repository;

import android.util.Log;

import com.example.ioana.budgetapplication.config.MyDatabase;
import com.example.ioana.budgetapplication.model.Role;
import com.example.ioana.budgetapplication.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ioana on 12/01/2018.
 */

public class UserRepository {
    private DatabaseReference reference;

    public UserRepository() {
        reference = MyDatabase.getDatabase().getReference("users");
        reference.keepSynced(true);
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public void addUser(String id, String email) {
        User user = new User(id, email, Role.USER);
        reference.child(id).setValue(user);
    }

    public void addAdmin(String id, String email) {
        User user = new User(id, email, Role.ADMIN);
        reference.child(id).setValue(user);
    }

    public boolean isAdmin(String id) {
        Log.d("is admin reff ", String.valueOf(reference.child(id).getRef()));
        return reference.child(id).child("role").getRef().equals(Role.ADMIN.toString());
    }


    public void makeAdmin(User user) {
        reference.child(user.getId()).child("role").setValue(Role.ADMIN);
    }

}
