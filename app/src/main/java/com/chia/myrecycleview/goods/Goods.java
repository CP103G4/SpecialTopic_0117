package com.chia.myrecycleview.goods;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Goods implements Serializable {

    private int id;
    private String name;
    private String description;
    private double prices;
    private int mainClass;
    private int subClass;
    private int evulatePoints;

    public Goods(int id, String name, String description, double prices, int mainClass, int subClass, int evulatePoints) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.prices = prices;
        this.mainClass = mainClass;
        this.subClass = subClass;
        this.evulatePoints = evulatePoints;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }

    public int getMainClass() {
        return mainClass;
    }

    public void setMainClass(int mainClass) {
        this.mainClass = mainClass;
    }

    public int getSubClass() {
        return subClass;
    }

    public void setSubClass(int subClass) {
        this.subClass = subClass;
    }

    public int getEvulatePoints() {
        return evulatePoints;
    }

    public void setEvulatePoints(int evulatePoints) {
        this.evulatePoints = evulatePoints;
    }

}
