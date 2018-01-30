package com.example.ioana.gamestore.domain;

/**
 * Created by Ioana on 30/01/2018.
 */

public class Game {
    private int id;
    private String name;
    private Type type;
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
