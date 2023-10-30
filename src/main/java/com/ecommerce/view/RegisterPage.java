package com.ecommerce.view;

import com.ecommerce.utils.StringUtil;

import static com.ecommerce.utils.Utils.println;

public class RegisterPage {

    public void printRegistrationSuccessful() {
        try {
            println(StringUtil.REGISTRATION_SUCCESSFUL);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void passwordMisMatch() {
        try {
            println(StringUtil.PASSWORD_MISMATCH);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
