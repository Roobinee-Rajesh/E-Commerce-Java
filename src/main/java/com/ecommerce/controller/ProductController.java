package com.ecommerce.controller;

import com.ecommerce.controller.impl.IProductController;
import com.ecommerce.models.Category;
import com.ecommerce.models.Product;
import com.ecommerce.utils.AppException;
import com.ecommerce.utils.StringUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PrimitiveIterator;
import java.util.Scanner;


import static com.ecommerce.utils.AppInput.enterInt;
import static com.ecommerce.utils.FileUtil.getCategoryFile;
import static com.ecommerce.utils.FileUtil.getProductFile;
import static com.ecommerce.utils.Utils.println;

public class ProductController implements IProductController {
    private final HomeController homeController;
    private final CartController cartController;
    public ProductController(HomeController homeController){
        this.homeController=homeController;
        cartController=new CartController(homeController);
    }

    public int categoryId = 0;

    @Override
    public void showProducts(int categoryId) {

        this.categoryId = categoryId;
        if (categoryId == 0) {
            try {
                Scanner scanner = new Scanner(getProductFile());
                while (scanner.hasNext()) {
                    String value = scanner.next().trim();
//                println(value);
                    String[] Products = value.split(",");
                    println(Products[0] + ". " + "Product: " + Products[1] + " Price: " + Products[3] + " Stock: " + Products[4]);
                }
                println(StringUtil.BACK);
                scanner.close();
                productChoice();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                Scanner scanner = new Scanner(getProductFile());
//                println(scanner.nextLine());
                while (scanner.hasNext()) {
                    String value = scanner.next().trim();
//                    println(value);
                    String[] categoryProducts = value.split(",");

                    int productCategoryId = Integer.parseInt(categoryProducts[5]);

                    if (productCategoryId == this.categoryId) {
                        println(categoryProducts[0] + ". " + "Product: " + categoryProducts[1]+ " Price: " + categoryProducts[3] + " Stock: " + categoryProducts[4]);
                    }
                }
                println(StringUtil.BACK);
                scanner.close();
                productChoice();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void productChoice() {
        try {
           int choice = enterInt(StringUtil.ENTER_CHOICE);
           if(choice==99)
           {
               homeController.printMenu();
           }
           else{
               int validProduct=0;
               Scanner scanner=new Scanner(getProductFile());
               String[] product={};
               String[] productFound={};
               while (scanner.hasNext()) {
                   String value = scanner.next().trim();
//                println(value);
                   product = value.split(",");
                   if(choice==Integer.parseInt(product[0])){
                       validProduct=choice;
                       break;
                   }

               }
                 if(validProduct==0){
                   println(StringUtil.INVALID_CHOICE);
                   showProducts(categoryId);
                   productChoice();
               }
               scanner.close();
               cartController.addTocart(validProduct,product);
           }
        } catch (AppException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
