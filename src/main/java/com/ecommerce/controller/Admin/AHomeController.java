package com.ecommerce.controller.Admin;

import com.ecommerce.controller.AuthController;
import com.ecommerce.utils.AppException;
import com.ecommerce.utils.StringUtil;

import static com.ecommerce.utils.AppInput.enterInt;
import static com.ecommerce.utils.UserUtil.setLoggedInUser;
import static com.ecommerce.utils.Utils.println;

public class AHomeController {
    private final AUserController aUserController;
    private final ACategoryController aCategoryController;
    private final AProductController aProductController;

    public AHomeController(AuthController authController){
        aCategoryController=new ACategoryController(this);
        aProductController=new AProductController(this);
        aUserController=new AUserController(authController,this);
    }
    public void adminMenu(){
        println("1. User\n2.Category\n3.Product\n4. Log Out");
        try {
            int choice=enterInt(StringUtil.ENTER_CHOICE);
            if(choice==1){
                aUserController.showUserAction();

            } else if (choice==2) {
                aCategoryController.showCategoryAction();

            } else if (choice==3) {
                aProductController.showProductAction();

            } else if (choice==4) {
                setLoggedInUser(null);

            } else {
                invalisChoice(new AppException(StringUtil.INVALID_CHOICE));
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

    }

    private void invalisChoice(AppException e) {
        println(e.getMessage());
        adminMenu();
    }
}
