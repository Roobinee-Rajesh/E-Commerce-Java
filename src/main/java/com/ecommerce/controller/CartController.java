package com.ecommerce.controller;

import com.ecommerce.controller.impl.ICartController;
import com.ecommerce.models.User;
import com.ecommerce.utils.AppException;
import com.ecommerce.utils.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.ecommerce.utils.AppInput.enterInt;
import static com.ecommerce.utils.FileUtil.*;
import static com.ecommerce.utils.UserUtil.getLoggedInUser;
import static com.ecommerce.utils.Utils.println;

public class CartController implements ICartController {
    private final HomeController homeController;
    private final OrderController orderController;

    public CartController(HomeController homeController) {
        this.homeController = homeController;
        orderController = new OrderController(homeController);
    }

    @Override
    public void addTocart(int validProduct, String[] product) {
        User loggedInUser = getLoggedInUser();
        int userId = loggedInUser.getId(), count = 1;
//        println(String.valueOf(validProduct));
//        println(String.valueOf(id));
//        println(Arrays.toString(product));

        boolean isProductExist = false;
        DecimalFormat df = new DecimalFormat("#.00");
        double Price = Double.parseDouble(product[3]);

        try {
            FileWriter csvWriter = new FileWriter(getCartFile(), true);
            Scanner scanner = new Scanner(getCartFile());
            while (scanner.hasNext()) {
//                println("in");
                String value = scanner.next().trim();
                String[] userCartArray = value.split(",");
                ArrayList<String> newValue=new ArrayList<>();
                int cartUserId = Integer.parseInt(userCartArray[0]);
                int cartProductId = Integer.parseInt(userCartArray[1]);
                int cartCount = Integer.parseInt(userCartArray[3]);
                if (userId == cartUserId && validProduct == cartProductId) {
//                    updateCount(userId, validProduct, product);

                    String cartPrice = df.format(Price * (cartCount + 1));
//                    println(cartPrice);
                    int indexToUpdate = 0;  // Replace with the actual index you want to update

                    String newElement = userId + "," + product[0] + "," + product[1] + "," + (cartCount + 1) + "," + cartPrice;
//                    System.out.println(newElement);
//                    System.out.println(value);
                    replaceCartFile(value,newElement);

                    isProductExist = true;
                    break;
                }
            }
            if (isProductExist == false) {
                csvWriter.append(userId + "," + Integer.parseInt(product[0]) + "," + product[1] + "," + count + "," + Price);
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
            showCartMenu();
//       println("Added to cart");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void replaceCartFile(String oldString, String newString) throws IOException {
        String filePath = String.valueOf(getCartFile());
        Scanner sc = new Scanner(new File(filePath));
        StringBuffer buffer = new StringBuffer();
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine()+System.lineSeparator());
        }
        String fileContents = buffer.toString();
        System.out.println("Contents of the file: "+fileContents);
        sc.close();
        String oldLine = oldString;
        String newLine = newString;
        fileContents = fileContents.replaceAll(oldLine, newLine);
        FileWriter writer = new FileWriter(filePath);
        System.out.println("");
        System.out.println("new data: "+fileContents);
        writer.append(fileContents);
        writer.flush();
    }

    @Override
    public void showCart() {
        Scanner scanner = null;
        double total = 0;
        DecimalFormat df = new DecimalFormat("#.00");
        try {
            scanner = new Scanner(getCartFile());
            while (scanner.hasNext()) {
                String value = scanner.next().trim();
//                    println(value);
                String[] cartProduct = value.split(",");
                total += Double.parseDouble(cartProduct[4]);
                println(cartProduct[2] + "*" + cartProduct[3] + "=" + cartProduct[4]);
            }
            println("Total: " + df.format(total));
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void showCartMenu() {
        println(StringUtil.CARTCHOICE);
        try {
            int choice = choice = enterInt(StringUtil.ENTER_CHOICE);
            if (choice == 99) {
                homeController.printMenu();
            } else if (choice == 66) {
                orderController.checkOut();
            } else {
                println(StringUtil.INVALID_CHOICE);
                showCartMenu();
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }


}
