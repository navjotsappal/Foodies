/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSONOrdering.FoodiesAPI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author neha
 */
public class FoodiesApi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            @SuppressWarnings("unused")
            HTTPServer s1= new HTTPServer(9000);

        } catch (IOException ex) {
            Logger.getLogger(FoodiesApi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
