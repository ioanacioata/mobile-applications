package com.example.ioana.budgetapplication.model;

import java.io.Serializable;

/**
 * Created by Ioana on 08/11/2017.
 */

public class Product implements Serializable {
    private String id;
    private String name;
    private Double price;
    private Shop shop;
    private String brand;
    private int imagePath;


    public Product() {
    }

    public Product(String name, Double price, Shop shop, String brand, int imagePath) {
        this.name = name;
        this.price = price;
        this.shop = shop;
        this.brand = brand;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
                ", shop=" + shop.toString() +
                ", brand='" + brand + '\'' +
                ", imagePath=" + imagePath +
                '}';
    }
}
