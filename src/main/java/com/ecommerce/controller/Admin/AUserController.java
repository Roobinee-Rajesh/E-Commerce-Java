package com.ecommerce.controller.Admin;

import com.ecommerce.controller.AuthController;
import com.ecommerce.utils.AppException;
import com.ecommerce.utils.StringUtil;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.ecommerce.utils.AppInput.enterInt;
import static com.ecommerce.utils.FileUtil.getCredentialsFile;
import static com.ecommerce.utils.FileUtil.getProductFile;
import static com.ecommerce.utils.Utils.println;

public class AUserController {
    private final AuthController authController;
    private final AHomeController aHomeController;
    public AUserController(AuthController authController,AHomeController aHomeController)
    {
        this.authController=authController;
        this.aHomeController=aHomeController;
    }
    public void showUserAction() {
        println(StringUtil.ADMIN_CHOICE);
        try {
            int choice=enterInt(StringUtil.ENTER_CHOICE);
            if(choice==1) {
                authController.register();
            }
            else if (choice==2) {
                updateUser();
            } else if (choice==3) {
                deleteUser();
            } else if (choice==4) {
                viewUsers();
            } else if (choice==99) {
                aHomeController.adminMenu();
            } else{
                invalisChoice(new AppException(StringUtil.INVALID_CHOICE));
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }

    private void viewUsers() {
        Scanner scanner = null;
        try {
            scanner=new Scanner(getCredentialsFile());
            println("List of users: ");
            while (scanner.hasNext()) {
                String value = scanner.next().trim();
                String[] usersArray = value.split(",");
                println("User Id: "+usersArray[0]+" Name: "+usersArray[1]+" Email: "+usersArray[2]);
            }
            scanner.close();
            aHomeController.adminMenu();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void invalisChoice(AppException e) {
        println(e.getMessage());
        showUserAction();
    }

    private void deleteUser() {

    }

    private void updateUser() {

    }


}
