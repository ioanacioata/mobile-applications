package com.example.ioana.budgetapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Ioana on 06/12/2017.
 */

@Entity
public class Supermarket implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String address;

    @Ignore
    public Supermarket(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Supermarket(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Supermarket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
