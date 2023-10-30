package com.ecommerce.controller.Admin;

import com.ecommerce.controller.HomeController;
import com.ecommerce.utils.AppException;
import com.ecommerce.utils.StringUtil;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static com.ecommerce.utils.AppInput.enterInt;
import static com.ecommerce.utils.AppInput.enterString;
import static com.ecommerce.utils.FileUtil.getCategoryFile;
import static com.ecommerce.utils.FileUtil.getCredentialsFile;
import static com.ecommerce.utils.Utils.println;

public class ACategoryController {
    private final AHomeController aHomeController;
    public ACategoryController(AHomeController aHomeController){
        this.aHomeController=aHomeController;

    }
    public void showCategoryAction() {
        println(StringUtil.ADMIN_CHOICE);
        try {
            int choice=enterInt(StringUtil.ENTER_CHOICE);
            if(choice==1) {
                addCategory();
            }
            else if (choice==2) {
                updateCategory();
            } else if (choice==3) {
                deleteCategory();
            }else if (choice==4) {
                viewCategories();
            } else if (choice==99) {
                aHomeController.adminMenu();
            }else{
                invalisChoice(new AppException(StringUtil.INVALID_CHOICE));
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewCategories() {
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
            aHomeController.adminMenu();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void invalisChoice(AppException e) {
        println(e.getMessage());
        showCategoryAction();
    }

    private void deleteCategory() {

    }

    private void updateCategory() {

    }

    private void addCategory() {
       String categoryName = enterString(StringUtil.ENTER_CATEGORY_NAME);
        try {
            FileWriter csvWriter = new FileWriter(getCategoryFile(), true);
            int id = (int) (Math.random() * 100);
            csvWriter.append("\n");
            csvWriter.append(id+","+categoryName);
            csvWriter.flush();
            csvWriter.close();
            println("Category added successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
