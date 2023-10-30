package com.ecommerce.controller;

import com.ecommerce.models.Category;
import com.ecommerce.utils.AppException;
import com.ecommerce.utils.StringUtil;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.ecommerce.utils.AppInput.enterInt;
import static com.ecommerce.utils.FileUtil.getCategoryFile;
import static com.ecommerce.utils.Utils.print;
import static com.ecommerce.utils.Utils.println;

public class CategoryController {
    private final HomeController homeController;
    private final ProductController productController;

    public CategoryController(HomeController homeController) {
        this.homeController = homeController;
        productController = new ProductController(homeController);
    }

    public void printCategory() {
        String[] categoryArray = new String[0];
        try {
            Scanner scanner = new Scanner(getCategoryFile());
            while (scanner.hasNext()) {
                String value = scanner.next().trim();
                categoryArray = value.split(",");
                println(categoryArray[0] + ". " + categoryArray[1]);
            }
            println(StringUtil.BACK);
            scanner.close();
            showCategoryProducts();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void showCategoryProducts() {
        int choice;
        try {
            choice = enterInt(StringUtil.ENTER_CHOICE);
            int categoryId=0;
            if (choice == 99) {
                homeController.printMenu();
            } else {

                Scanner scanner = new Scanner(getCategoryFile());
                while (scanner.hasNext()) {
                    String value = scanner.next().trim();
                    String[] categoryArray = value.split(",");
                    if (Integer.parseInt(categoryArray[0]) == choice) {
                        categoryId = choice;
                        break;
                    }
                }
                if(categoryId==0){
                    println("Please enter a valid choice");
                    printCategory();
                    showCategoryProducts();
                }
                scanner.close();
                productController.showProducts(categoryId);
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
