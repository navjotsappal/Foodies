/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JSONOrdering.DAL;

import java.util.ArrayList;
import JSONOrdering.FoodiesAPI.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author neha
 */

public class DALGrocery {

    static String host = DALSettings.host;
    static String uName = DALSettings.uName;
    static String pass = DALSettings.pass;

    public static ArrayList<Grocery> getAllGroceries() throws SQLException {
        ArrayList<Grocery> groceries = new ArrayList<Grocery>();
        Connection con;
        con = DriverManager.getConnection(DALGrocery.host, DALGrocery.uName, DALGrocery.pass);

        try {
            String insertStoredproc = "{call getGroceries()}";
            CallableStatement callableStatement = con.prepareCall(insertStoredproc);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                Grocery g = new Grocery();
                g.setId(resultSet.getInt(1));
                g.setName(resultSet.getString(2));
                g.setPicture(resultSet.getString(3));

                groceries.add(g);
            }
        } catch (Exception ex) {

        }

        return groceries;
    }

    /**
     * Getting grocery item by Id.
     * @param gId
     * @return
     * @throws SQLException
     */
    public static Grocery getGrocery(int gId) throws SQLException {
        Grocery g=new Grocery();
        Connection con;
        con = DriverManager.getConnection(DALGrocery.host, DALGrocery.uName, DALGrocery.pass);

        try {
            String insertStoredproc = "{call getgrocery(?)}";
            CallableStatement callableStatement = con.prepareCall(insertStoredproc);
            callableStatement.setInt(1, gId);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                g.setId(resultSet.getInt(1));
                g.setName(resultSet.getString(2));
                g.setPicture(resultSet.getString(3));

            }
        } catch (Exception ex) {

        }
        return g;
    }
}
