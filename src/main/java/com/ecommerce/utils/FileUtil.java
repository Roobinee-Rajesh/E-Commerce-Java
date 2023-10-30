package com.ecommerce.utils;

import java.io.File;

public class FileUtil {

    private static File credentailsFile;
    private static File categoryFile;
    private static File productFile;
    private static File cartFile;

    public static File getCredentialsFile() {
        if (credentailsFile == null)
            credentailsFile = new File("src/main/java/com/ecommerce/assests/credentials.csv");
        return credentailsFile;
    }

    public static File getCategoryFile() {
        if (categoryFile == null)
            categoryFile = new File("src/main/java/com/ecommerce/assests/category.csv");
        return categoryFile;
    }

    public static File getProductFile() {
        if (productFile == null)
            productFile = new File("src/main/java/com/ecommerce/assests/product.csv");
        return productFile;
    }

    public static File getCartFile() {
        if (cartFile == null)
            cartFile = new File("src/main/java/com/ecommerce/assests/cart.csv");
        return cartFile;
    }

    public static String getFilePath() {
        return "src/main/java/com/ecommerce/assests/";
    }
}
