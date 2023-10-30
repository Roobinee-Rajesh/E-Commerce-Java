package com.ecommerce.controller.Admin;

import com.ecommerce.utils.AppException;
import com.ecommerce.utils.StringUtil;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static com.ecommerce.utils.AppInput.enterInt;
import static com.ecommerce.utils.AppInput.enterString;
import static com.ecommerce.utils.FileUtil.*;
import static com.ecommerce.utils.Utils.println;

public class AProductController {
    private final AHomeController aHomeController;
    private final ACategoryController aCategoryController;
    public AProductController(AHomeController aHomeController){
        this.aHomeController=aHomeController;
        aCategoryController=new ACategoryController(aHomeController);
    }
    public void showProductAction() {
        println(StringUtil.ADMIN_CHOICE);
        try {
            int choice=enterInt(StringUtil.ENTER_CHOICE);
            if(choice==1) {
                addProduct();
            }
            else if (choice==2) {
                updateProduct();
            } else if (choice==3) {
                deleteProduct();
            }else if (choice==4) {
                viewProducts();
            } else if (choice==99) {
                aHomeController.adminMenu();
            }else{
                invalisChoice(new AppException(StringUtil.INVALID_CHOICE));
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }

    private void viewProducts() {
        Scanner scanner = null;
        try {
            scanner=new Scanner(getProductFile());
            println("List of Products: ");
            while (scanner.hasNext()) {
                String value = scanner.next().trim();
                String[] productArray = value.split(",");
                println("Product Id: "+productArray[0]+" Name: "+productArray[1]+" Description: "+productArray[2]+" Price: "+productArray[3]);
            }
            scanner.close();
            aHomeController.adminMenu();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void invalisChoice(AppException e) {
        println(e.getMessage());
        showProductAction();
    }

    private void deleteProduct() {
    }

    private void updateProduct() {
    }

    private void addProduct() {

        try {
            String productName,productDescription;
            double productPrice;
            int productAvailable,categoryBelongTo=0;
            productName = enterString(StringUtil.ENTER_PRODUCT_NAME);
            productDescription=enterString("Enter product description");
            productPrice= enterInt("Enter product price");

           viewCategories();

            int choice=enterInt(StringUtil.ENTER_CHOICE);
            Scanner scanner=new Scanner(getCategoryFile());
            while (scanner.hasNext()) {
                String value = scanner.next().trim();
                String[] categoryArray = value.split(",");
                if(Integer.parseInt(categoryArray[0])==choice){
                    categoryBelongTo=choice;
                }
            }
            if(categoryBelongTo==0)
            {
                println("Invalid choice");
                showProductAction();
            }
            productAvailable=enterInt("Enter Availability");

            FileWriter csvWriter = new FileWriter(getProductFile(), true);
            int id = (int) (Math.random() * 100);
            csvWriter.append("\n");
            csvWriter.append(id+","+productName+","+productDescription+","+productPrice+","+productAvailable+","+categoryBelongTo);
            csvWriter.flush();
            csvWriter.close();
            println("Product added successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }

    private void viewCategories() {
        Scanner scanner = null;
        try {
            scanner=new Scanner(getCategoryFile());
            println("List of Categories: ");
            while (scanner.hasNext()) {
                String value = scanner.next().trim();
                String[] categoryArray = value.split(",");
                println("Category Id: "+categoryArray[0]+" Name: "+categoryArray[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
