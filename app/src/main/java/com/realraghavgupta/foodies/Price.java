package com.realraghavgupta.foodies;

import java.math.BigDecimal;

//Model class for the price of each item at each store
public class Price {
    public String note;
    public int id;
    public BigDecimal price;
    public Grocery itemId;
    public Store storeId;
}
