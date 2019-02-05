package com.chia.myrecycleview.goods;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Goods implements Serializable {

    private int id;
    private String name;
    private String description;
    private double prices;
    private int mainclass;
    private int subclass;
    private int evulation;

    public Goods(int id, String name, String description, double prices, int mainclass, int subclass, int evulation) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.prices = prices;
        this.mainclass = mainclass;
        this.subclass = subclass;
        this.evulation = evulation;
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

    public int getMainclass() {
        return mainclass;
    }

    public void setMainclass(int mainclass) {
        this.mainclass = mainclass;
    }

    public int getSubclass() {
        return subclass;
    }

    public void setSubclass(int subclass) {
        this.subclass = subclass;
    }

    public int getEvulation() {
        return evulation;
    }

    public void setEvulation(int evulation) {
        this.evulation = evulation;
    }

}
