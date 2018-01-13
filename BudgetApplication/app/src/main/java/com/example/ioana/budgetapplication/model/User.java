package com.example.ioana.budgetapplication.model;


import java.io.Serializable;

/**
 * Created by Ioana on 05/12/2017.
 */

public class User implements Serializable {

    private String id;
    private String email;
    private Role role;

    public User(String id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
