package com.chia.myrecycleview.ShoppingCar;

import com.chia.myrecycleview.ShoppingCar.OrderProduct;
@SuppressWarnings("serial")
public class OrderProduct extends Product {
    private int quantity;

    public OrderProduct(int id, String name, double price, String description, int quantity) {
        super(id, name, price, description);
        this.quantity = quantity;
    }

    public OrderProduct(Product product, int quantity) {
        this(product.getId(), product.getName(), product.getPrice(), product
                .getDescription(), quantity);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
