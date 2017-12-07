package com.example.ioana.budgetapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Ioana on 08/11/2017.
 */

@Entity(foreignKeys = @ForeignKey(entity = Supermarket.class,
        parentColumns = "id",
        childColumns = "supermarketId",
        onDelete = CASCADE))
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String name;
    private Double price;
    private int supermarketId;
    private String brand;
    private int imagePath;

    public Product(int id, @NonNull String name, Double price, int supermarketId, String brand, int imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.supermarketId = supermarketId;
        this.brand = brand;
        this.imagePath = imagePath;
    }

    @Ignore
    public Product(String name, Double price, int supermarketId, String brand, int imagePath) {
        this.name = name;
        this.price = price;
        this.supermarketId = supermarketId;
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

    public int getSupermarketId() {
        return supermarketId;
    }

    public void setSupermarketId(int supermarketId) {
        this.supermarketId = supermarketId;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", supermarketId=" + supermarketId +
                ", brand='" + brand + '\'' +
                ", imagePath=" + imagePath +
                '}';
    }
}
