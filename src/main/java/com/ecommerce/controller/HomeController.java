package com.ecommerce.controller;

import com.ecommerce.controller.Admin.AHomeController;
import com.ecommerce.controller.Admin.AUserController;
import com.ecommerce.controller.impl.IHomeController;
import com.ecommerce.models.Cart;
import com.ecommerce.utils.AppException;
import com.ecommerce.utils.StringUtil;
import com.ecommerce.view.HomePage;

import static com.ecommerce.utils.AppInput.enterInt;
import static com.ecommerce.utils.UserUtil.setLoggedInUser;
import static com.ecommerce.utils.Utils.println;

public class HomeController implements IHomeController {
    private final HomePage homePage;
    private final AuthController authController;
    private final CategoryController categoryController;
    private final ProductController productController;
    private final CartController cartController;
    private final OrderController orderController;
    private final AHomeController aHomeController;

    private int categoryId=0;

    public HomeController(AuthController authController){
        homePage=new HomePage();
        this.authController=authController;
        categoryController=new CategoryController(this);
        productController=new ProductController(this);
        cartController=new CartController(this);
        orderController=new OrderController(this);
        aHomeController=new AHomeController(authController);

    }

    @Override
    public void printMenu() {
        homePage.printMenu();
        try {
            int choice=enterInt(StringUtil.ENTER_CHOICE);
            if(choice==1){
                categoryController.printCategory();
            } else if (choice==2) {
                productController.showProducts(categoryId);
                
            } else if (choice==3) {
                cartController.showCart();
                
            } else if (choice==4) {
                orderController.printOrder();
                
            } else if (choice==5) {
                setLoggedInUser(null);
                authController.authMenu();
            }else {
                invalisChoice(new AppException(StringUtil.INVALID_CHOICE));
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

    }

    private void invalisChoice(AppException appException) {
        println(appException.getMessage());
        printMenu();
    }
}
