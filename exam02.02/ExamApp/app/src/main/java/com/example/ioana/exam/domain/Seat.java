package com.example.ioana.exam.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;

@Entity
public class Seat implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @TypeConverters(TypeConv.class)
    private Type type;

    @TypeConverters(StatusConverter.class)
    private Status status;


    @Ignore
    public Seat() {
        id = 0;
        status = Status.AVAILABLE; //first enum
        type = Type.STALL;
        name = "";
    }

    public Seat(int id, String name, Type type, Status status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
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

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
