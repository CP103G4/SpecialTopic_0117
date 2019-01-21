package com.chia.myrecycleview.ShoppingCar;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Product implements Serializable {

    private int id;
    private String name;
    private double price;
    private String description;

    public Product(int id, String name, double price, String description) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    // 要比對欲加入商品與購物車內商品的id是否相同，是則值相同
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof OrderProduct)) {
            return false;
        }

        return this.getId() == ((Product) obj).getId();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

}