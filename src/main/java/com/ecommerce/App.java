package com.ecommerce;

import com.ecommerce.controller.AppController;

public class App {
    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.init();
    }
}