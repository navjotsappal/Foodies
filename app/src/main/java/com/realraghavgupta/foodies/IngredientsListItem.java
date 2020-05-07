package com.realraghavgupta.foodies;

public class IngredientsListItem {  //Defining the structure of the data that is used to receive and process data
    private String groceryNames;

    public IngredientsListItem() {
    }

    public IngredientsListItem(String groceryNames) {
        this.groceryNames = groceryNames;
    }

    public String getGroceryNames() {
        return groceryNames;
    }
}