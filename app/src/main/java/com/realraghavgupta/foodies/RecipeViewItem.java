package com.realraghavgupta.foodies;

import java.util.ArrayList;

public class RecipeViewItem {
    private String title, imageUrl, totalTime, sourceRecipeUrl;
    private int numberofServings;
    private double rating;
    private ArrayList<String> ingredientLines;

    public RecipeViewItem(String title, String imageUrl, String totalTime, String sourceRecipeUrl, int numberofServings, double rating, ArrayList<String> ingredientLines) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.totalTime = totalTime;
        this.sourceRecipeUrl = sourceRecipeUrl;
        this.numberofServings = numberofServings;
        this.rating = rating;
        this.ingredientLines = ingredientLines;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getSourceRecipeUrl() {
        return sourceRecipeUrl;
    }

    public void setSourceRecipeUrl(String sourceRecipeUrl) {
        this.sourceRecipeUrl = sourceRecipeUrl;
    }

    public int getNumberofServings() {
        return numberofServings;
    }

    public void setNumberofServings(int numberofServings) {
        this.numberofServings = numberofServings;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(ArrayList<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }
}