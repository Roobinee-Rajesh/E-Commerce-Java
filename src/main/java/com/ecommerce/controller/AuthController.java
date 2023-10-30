package com.ecommerce.controller;

import com.ecommerce.controller.Admin.AHomeController;
import com.ecommerce.controller.impl.IAuthController;
import com.ecommerce.models.Role;
import com.ecommerce.models.User;
import com.ecommerce.utils.AppException;
import com.ecommerce.utils.StringUtil;
import com.ecommerce.utils.UserUtil;
import com.ecommerce.view.AuthPage;
import com.ecommerce.view.LoginPage;
import com.ecommerce.view.RegisterPage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static com.ecommerce.models.Role.USER;
import static com.ecommerce.utils.AppInput.enterInt;
import static com.ecommerce.utils.AppInput.enterString;
import static com.ecommerce.utils.FileUtil.getCredentialsFile;
import static com.ecommerce.utils.Utils.println;

public class AuthController implements IAuthController {

    private final HomeController homeController;

    private final LoginPage loginPage;
    private final RegisterPage registerPage;
    private final AuthPage authPage;
    private final AHomeController aHomeController;

    public AuthController() {
        authPage=new AuthPage();
        homeController = new HomeController(this);
        loginPage = new LoginPage();
        registerPage = new RegisterPage();
        aHomeController=new AHomeController(this);
    }

    @Override
    public void authMenu() {
        authPage.printAuthMenu();
        int choice;
        try {
            choice = enterInt(StringUtil.ENTER_CHOICE);
            if (choice == 1) {
                login();
            } else if (choice == 2) {
                register();
            } else {
                invalidChoice(new AppException(StringUtil.INVALID_CHOICE));
            }
        } catch (AppException appException) {
            invalidChoice(appException);
        }
    }

    @Override
    public void login() {
        String email, password;
        email = enterString(StringUtil.ENTER_EMAIL);
        password = enterString(StringUtil.ENTER_PASSWORD);

        User user = validateUser(email, password);
        if (user != null) {
            if(user.getRole()==USER) {
                UserUtil.setLoggedInUser(user);
                homeController.printMenu();
            }
            else {
                aHomeController.adminMenu();
            }
        } else {
            loginPage.printInvalidCredentials();
            authMenu();
        }
    }


    @Override
    public void register() {
        String name, email, password, c_password;
        name = enterString(StringUtil.ENTER_NAME);
        email = enterString(StringUtil.ENTER_EMAIL);
        password = enterString(StringUtil.ENTER_PASSWORD);
        c_password = enterString(StringUtil.ENTER_PASSWORD_AGAIN);

        if (password.equals(c_password)) {
            try {
                FileWriter csvWriter = new FileWriter(getCredentialsFile(), true);
                int id = (int) (Math.random() * 100);
                csvWriter.append("\n");
                csvWriter.append(id + "," + name + "," + email + "," + password);
                csvWriter.flush();
                csvWriter.close();
                registerPage.printRegistrationSuccessful();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            registerPage.passwordMisMatch();
        }
        authMenu();
    }


    private User validateUser(String email, String password) {
        try {
            Scanner scanner = new Scanner(getCredentialsFile());
            while (scanner.hasNext()) {
                String value = scanner.next().trim();

                    String[] userArray = value.split(",");
                    if (userArray[2].equals(email) && userArray[3].equals(password)) {
                        User user = new User();
                        user.setId(Integer.parseInt(userArray[0]));
                        user.setName(userArray[1]);
                        user.setEmail(userArray[2]);
                        user.setPassword(userArray[3]);
                        if (user.getEmail().equals("admin@admin.com"))
                            user.setRole(Role.ADMIN);
                        else
                            user.setRole(USER);
                        return user;
                    }
                }

            scanner.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void invalidChoice(AppException e) {
        println(e.getMessage());
        authMenu();
    }

    @Override
    public void logout() {

    }
}
