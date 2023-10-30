package com.ecommerce.view;

import com.ecommerce.utils.StringUtil;

import java.util.Map;

import static com.ecommerce.utils.Utils.println;

public class OrdersPage {
    public void printSuccess() {
        try {
            println("#---------------------#");
            println(StringUtil.PLACE_ORDER);
            println("#---------------------#");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void printOrder(Map<String, String> files) {
        println("#---------------------#");
        println(StringUtil.ORDERS);
        println("#---------------------#");
        int id = 1;
        for (Map.Entry<String, String> entry : files.entrySet()) {
            println(id + ". Date = " + entry.getKey() + " OrderId = " + entry.getValue());
            id++;
        }
        println(StringUtil.BACK);
    }

    public void printDesign() {
        println("#---------------------#");
    }

    public void printNoOrders() {
        try {
            println("#---------------------#");
            println(StringUtil.NO_ORDER);
            println("#---------------------#");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
