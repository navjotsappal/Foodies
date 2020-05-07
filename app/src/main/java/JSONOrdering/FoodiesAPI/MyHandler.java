/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSONOrdering.FoodiesAPI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import com.google.gson.Gson;
import com.google.gson.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author neha
 */
public class MyHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        directRequest(t);
    }

    /**
     * Method to check the endpoint and respond accordingly.
     *
     * @param t
     * @throws IOException
     */
    public void directRequest(HttpExchange t) throws IOException {

        try {
            String uri[] = t.getRequestURI().toString().split("/");
            String response = "";
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            switch (uri[2]) {
                case "grocery":
                    ArrayList<Grocery> groceries = JSONOrdering.DAL.DALGrocery.getAllGroceries();
                    response = gson.toJson(groceries);
                    break;

                case "price":
                    if (uri.length <= 3) {
                        ArrayList<Price> prices = JSONOrdering.DAL.DALPrice.getAllPrices();
                        response = gson.toJson(prices);
                    }
                    if(uri.length > 3){
                        int itemId = Integer.parseInt(uri[3]);
                        Grocery prices = JSONOrdering.DAL.DALPrice.getPrice(itemId);
                        response = gson.toJson(prices);
                    }
                    break;

                case "category":
                    if(uri.length<=3){
                        ArrayList<Category> categories= JSONOrdering.DAL.DALCategory.getAllCategories();
                        response=gson.toJson(categories);
                    }
                    if(uri.length>3){
                        int cId= Integer.parseInt(uri[3]);
                        Category c1=JSONOrdering.DAL.DALCategory.getCategoryWithItems(cId);
                        response=gson.toJson(c1);
                    }
                    break;

                default:
                    response = "{}";
            }

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception ex) {
            Logger.getLogger(MyHandler.class.getName()).log(Level.SEVERE, null, ex);
            String response = "Some weird error";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        System.out.println("served " + t.getRequestURI().toString() +" from "+t.getRemoteAddress().getHostName());
    }
}
