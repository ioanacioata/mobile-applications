package com.example.ioana.exam.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;

@Entity
public class Project implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @TypeConverters(TypeConv.class)
    private Type type;

    @TypeConverters(StatusConverter.class)
    private Status status;

    private int budget;

    @Ignore
    public Project() {
        id = 0;
        status = Status.IDEA; //first enum
        type = Type.SMALL;
        name = "";
        budget = 0;
    }

    public Project(int id, String name, Type type, Status status, int budget) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.budget = budget;
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

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", budget=" + budget +
                '}';
    }
}
