package com.ecommerce.controller;

import com.ecommerce.controller.impl.IOrderController;
import com.ecommerce.utils.AppException;
import com.ecommerce.utils.StringUtil;
import com.ecommerce.view.OrdersPage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ecommerce.utils.AppInput.enterInt;
import static com.ecommerce.utils.FileUtil.*;
import static com.ecommerce.utils.UserUtil.getLoggedInUser;
import static com.ecommerce.utils.Utils.println;

public class OrderController implements IOrderController {
    private final HomeController homeController;
    private final OrdersPage ordersPage;
    public OrderController(HomeController homeController){
        this.homeController=homeController;
        ordersPage=new OrdersPage();
    }
    @Override
    public void checkOut() {
        int loggedInUser = getLoggedInUser().getId();
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(getFilePath() + loggedInUser + "-" + System.currentTimeMillis() + ".txt", true);
            fileWriter.append("Your Order are:");
            fileWriter.append("\n");

            double total = 0;

            Scanner scanner = new Scanner(getCartFile());
            while (scanner.hasNext()) {
                String value = scanner.next().trim();
//                println(String.valueOf(loggedInUser));
                String[] cartProduct = value.split(",");
                if (loggedInUser == Integer.parseInt(cartProduct[0])) {
                    total += Double.parseDouble(cartProduct[4]);
                    fileWriter.append(cartProduct[2]+ "*" + Integer.parseInt(cartProduct[3]) +"="+Double.parseDouble(cartProduct[4]));
                    fileWriter.append("\n");
                }
            }

            // Write the total to the file
            fileWriter.append("Total: " + total);
            fileWriter.close();
            homeController.printMenu();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printOrder() {
        Map<String, String> files = listFilesForFolder(new File(getFilePath()));
        if (files.isEmpty()) {
            ordersPage.printNoOrders();
            homeController.printMenu();
        } else {
            ordersPage.printOrder(files);
            try {
                int orderId = enterInt(StringUtil.ENTER_CHOICE);
                if (orderId == 99) {
                    homeController.printMenu();
                } else {
                    if (orderId > files.size()) {
                        println(StringUtil.INVALID_CHOICE);
                        printOrder();
                    } else {
                        int id = getLoggedInUser().getId();
                        String path = "";
                        for (final String key : files.keySet()) {
                            if (id == orderId) {
                                path = files.get(key);
                            }
                        }
                        BufferedReader r = new BufferedReader(new FileReader(getFilePath() + path));
                        String line;
                        ordersPage.printDesign();
                        while ((line = r.readLine()) != null) {
                            println(line);
                        }
                        printOrder();
                    }
                }

            } catch (AppException | IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private Map<String, String> listFilesForFolder(final File folder) throws RuntimeException {
        Map<String, String> files = new HashMap<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            Path path = new File(getFilePath() + fileEntry.getName()).toPath();
            BasicFileAttributes file_att;
            try {
                file_att = Files.readAttributes(
                        path, BasicFileAttributes.class);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy - ");

                Date d = sdf.parse(file_att.creationTime().toString());

                if (fileEntry.getName().startsWith(String.valueOf(getLoggedInUser().getId())))
                    files.put(dateFormat.format(d), fileEntry.getName());
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }

        }
        return files;
    }
}


