package com.realraghavgupta.foodies;

public class UserOrders {
    String emailId;
    String groceryNames, storeNames, prices;
    int quantity;

    public UserOrders() {
    }

    public UserOrders(String emailId, String groceryNames, String storeNames, String prices, int quantity) {
        this.emailId = emailId;
        this.groceryNames = groceryNames;
        this.storeNames = storeNames;
        this.prices = prices;
        this.quantity = quantity;
    }
}
