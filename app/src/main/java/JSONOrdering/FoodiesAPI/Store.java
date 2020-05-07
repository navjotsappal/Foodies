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

public class Store  {

    public Collection<Price> priceCollection;
    public Integer id;
    public String name;
    public Collection<Price> getPriceCollection() {
        return priceCollection;
    }
    public void setPriceCollection(Collection<Price> priceCollection) {
        this.priceCollection = priceCollection;
    }
}
