/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSONOrdering.DAL;

import JSONOrdering.FoodiesAPI.Category;
import JSONOrdering.FoodiesAPI.Grocery;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author neha
 */
public class DALCategory {

    static String host = DALSettings.host;
    static String uName = DALSettings.uName;
    static String pass = DALSettings.pass;

    public static ArrayList<Category> getAllCategories() throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();

        Connection con;
        con = DriverManager.getConnection(DALCategory.host, DALCategory.uName, DALCategory.pass);

        try {
            String insertStoredproc = "{call getAllCategories()}";
            CallableStatement callableStatement = con.prepareCall(insertStoredproc);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                Category c = new Category();
                c.id = resultSet.getInt(1);
                c.name = resultSet.getString(2);
                c.description = resultSet.getString(3);

                categories.add(c);
            }

        } catch (Exception ex) {

        }

        return categories;
    }

    public static Category getCategoryWithItems(int cId) throws SQLException {
        Category c1 = new Category();

        Connection con;
        con = DriverManager.getConnection(DALCategory.host, DALCategory.uName, DALCategory.pass);

        try {
            String insertStoredproc = "{call getCategoryWithItems(?)}";
            CallableStatement callableStatement = con.prepareCall(insertStoredproc);
            callableStatement.setInt(1, cId);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();
            c1 = getCategory(cId);
            c1.items = new ArrayList<>();

            while (resultSet.next()) {
                Grocery g = new Grocery();
                g.setId(resultSet.getInt(1));
                g.setName(resultSet.getString(2));
                g.setPicture(resultSet.getString(3));
                c1.items.add(g);
            }
        } catch (Exception ex) {

        }

        return c1;
    }

    public static Category getCategory(int cId) throws SQLException {
        Category c1 = new Category();

        Connection con;
        con = DriverManager.getConnection(DALCategory.host, DALCategory.uName, DALCategory.pass);

        try {
            String insertStoredproc = "{call getCategory(?)}";
            CallableStatement callableStatement = con.prepareCall(insertStoredproc);
            callableStatement.setInt(1, cId);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                c1.id = resultSet.getInt(1);
                c1.name = resultSet.getString(2);
                c1.description = resultSet.getString(3);
            }
        } catch (Exception ex) {

        }

        return c1;
    }
}
