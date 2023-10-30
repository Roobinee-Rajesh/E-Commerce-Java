package com.ecommerce.utils;

import com.ecommerce.models.User;

public class UserUtil {
    private static User loggedInUser;
    public static void setLoggedInUser(User loggedInUser) {
        UserUtil.loggedInUser = loggedInUser;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }


}
