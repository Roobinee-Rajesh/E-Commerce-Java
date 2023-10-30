package com.ecommerce.controller;

import com.ecommerce.controller.impl.IAppController;
import com.ecommerce.view.WelcomePage;

public class AppController implements IAppController {

    private final WelcomePage welcomePage;
    private final AuthController authController;

    public AppController() {
        welcomePage = new WelcomePage();
        authController = new AuthController();
    }

    @Override
    public void init() {
        welcomePage.welcome();
        authController.authMenu();
    }

    public void printAuthMenu() {
        welcomePage.printAuthMenu();
    }
}
