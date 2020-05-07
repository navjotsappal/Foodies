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
public class Category {

    public int id;
    public String name;
    public String description;
    public Collection<Grocery> items;
}
