/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 /test/grocery --> gives all the grocery items in the database.
 /test/price --> gives all the prices of all the groceries in every store in the db
 /test/price/1 --> gives prices of grocery of ID=1 in all the stores in the db
 /test/category --> gives all the available categories
 /test/category/1 --> give all the items in the category with ID =1
 */
package JSONOrdering.FoodiesAPI;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author neha
 */
public class HTTPServer {

    public HTTPServer(int port) throws IOException{
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
