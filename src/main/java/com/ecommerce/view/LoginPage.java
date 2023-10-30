package com.ecommerce.view;

import static com.ecommerce.utils.Utils.println;

public class LoginPage {
    public void printInvalidCredentials() {
        try {
            println("Invalid Credentials!!!");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
