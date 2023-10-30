package com.ecommerce.models;

import java.util.ArrayList;

public class Cart {
    private int userId;

    private int productId;
    private String productTitle;
    private double productPrice;
    private int count;

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getCount() {
        return count;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
