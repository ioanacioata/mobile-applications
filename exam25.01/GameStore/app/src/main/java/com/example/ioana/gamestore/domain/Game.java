package com.example.ioana.gamestore.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;

@Entity(tableName = "games")
public class Game implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @TypeConverters(TypeConv.class)
    private Type type;

    @TypeConverters(StatusConverter.class)
    private Status status;

    private int quantity;

    public Game(int id, String name, Type type, Status status, int quantity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.quantity = quantity;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", quantity=" + quantity +
                '}';
    }
}
