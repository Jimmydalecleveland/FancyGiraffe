/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fancygiraffe.relocate;

import org.fancygiraffe.global.Connections;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationDropdownModel {
    
	/**
	 * Returns all unique location names with their ids
	 * @return Map containing name, id pairs
	 */
    public Map getLocations() {
        
        Map<String,Integer> locations = new HashMap<String,Integer>();
        
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(Connections.connectionUrl(), 
                                               Connections.user(), 
                                               Connections.pwd());
            
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                                             ResultSet.CONCUR_UPDATABLE);
            
            String sql = "SELECT DISTINCT location_name, id FROM locations WHERE status=1 GROUP BY location_name";
            rs = statement.executeQuery(sql);
            
            rs.beforeFirst();
            while (rs.next()) {
                locations.put(rs.getString(1), rs.getInt(2));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(LocationDropdownModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                rs.close();
                statement.close();
                conn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(LocationDropdownModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return locations;
    }
}
