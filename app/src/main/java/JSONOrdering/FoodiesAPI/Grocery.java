/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSONOrdering.FoodiesAPI;

import java.util.Collection;


/**
 *
 * @author neha
 */

public class Grocery {

    private Integer id;
    private String name;
    private String picture;
    public Collection<Price> priceCollection;

    public Grocery() {
    }

    public Grocery(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public Collection<Price> getPriceCollection() {
        return priceCollection;
    }

    public void setPriceCollection(Collection<Price> priceCollection) {
        this.priceCollection = priceCollection;
    }

}
