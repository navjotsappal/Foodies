/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSONOrdering.DAL;

import JSONOrdering.FoodiesAPI.*;
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
public class DALPrice {

    static String host = DALSettings.host;
    static String uName = DALSettings.uName;
    static String pass = DALSettings.pass;

    public static ArrayList<Price> getAllPrices() throws SQLException {
        ArrayList<Price> prices= new ArrayList<>();
        Connection con;
        con = DriverManager.getConnection(DALPrice.host, DALPrice.uName, DALPrice.pass);

        try {
            String insertStoredproc = "{call getAllprices()}";
            CallableStatement callableStatement = con.prepareCall(insertStoredproc);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                Price p = new Price();
                p.itemId= new Grocery();
                p.itemId.setId(resultSet.getInt(1));
                p.itemId.setName(resultSet.getString(2));
                p.itemId.setPicture(resultSet.getString(3));
                p.price = resultSet.getBigDecimal(4);

                p.storeId= new Store();
                p.storeId.id = resultSet.getInt(5);
                p.storeId.name= resultSet.getString(6);
                p.note=resultSet.getString(7);
                prices.add(p);
            }

        } catch (Exception ex) {

        }
        return prices;
    }

    public static Grocery getPrice(int itemId) throws SQLException {
        Grocery g= JSONOrdering.DAL.DALGrocery.getGrocery(itemId);
        Connection con;
        con = DriverManager.getConnection(DALPrice.host, DALPrice.uName, DALPrice.pass);

        try {
            String insertStoredproc = "{call getprices(?)}";
            CallableStatement callableStatement = con.prepareCall(insertStoredproc);
            callableStatement.setInt(1, itemId);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();
            g.priceCollection=new ArrayList<Price>();

            while (resultSet.next()) {
                Price p = new Price();
                p.price = resultSet.getBigDecimal(4);
                p.storeId= new Store();
                p.storeId.id = resultSet.getInt(5);
                p.storeId.name= resultSet.getString(6);
                p.note=resultSet.getString(7);
                g.priceCollection.add(p);
            }

        } catch (Exception ex) {

        }
        return g;
    }


}
