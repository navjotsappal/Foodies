package com.realraghavgupta.foodies;

import java.util.ArrayList;

public class RecipeItem {
    private String title, thumbnailUrl;
    private int timeinsec;
    private String id;
    private double rating;
    private java.util.ArrayList<String> ingredients;

    public RecipeItem(String title, String thumbnailUrl, int timeinsec, double rating, String id,
                      ArrayList<String> ingredients) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.timeinsec = timeinsec;
        this.rating = rating;
        this.ingredients = ingredients;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int gettimeinsec() {
        return timeinsec;
    }

    public String getid() {
        return id;
    }

    public void settimeinsec(int timeinsec) {
        this.timeinsec = timeinsec;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getingredients() {
        return ingredients;
    }

    public void setingredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}



