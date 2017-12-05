package com.example.ioana.budgetapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Ioana on 08/11/2017.
 */

@Entity
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String name;
    private Double price;
    private String supermarketName;
    private String brand;
    private int imagePath;

    public Product(int id, @NonNull String name, Double price, String supermarketName, String brand, int imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.supermarketName = supermarketName;
        this.brand = brand;
        this.imagePath = imagePath;
    }

    @Ignore
    public Product(String name, Double price, String supermarketName, String brand, int imagePath) {
        this.name = name;
        this.price = price;
        this.supermarketName = supermarketName;
        this.brand = brand;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSupermarketName() {
        return supermarketName;
    }

    public void setSupermarketName(String supermarketName) {
        this.supermarketName = supermarketName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Ignore
    public Product(int imagePath) {
        this.imagePath = imagePath;
    }

    public int getImagePath() {
        return imagePath;
    }
}
