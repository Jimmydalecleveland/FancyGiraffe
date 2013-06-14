/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fancygiraffe.relocate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import org.fancygiraffe.global.Connections;
import org.fancygiraffe.global.ErrorLog;

public class ReassignModel {
    
	/**
	 * Changes asset locations in the database
	 * @param loc location to move assets to
	 * @param crs CachedRowSet containing all assets to move
	 * @param locMap Map of locations containing location_name, id pairs
	 * @return rows affected
	 */
    public int edit(int loc, CachedRowSet crs) {
        
        int ret = 0;
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            crs.last();
            int rowCount = crs.getRow();
        
            String sql = "UPDATE assets SET location_id = ? WHERE tag IN ('";
            
            crs.beforeFirst();
            for (int i = 1; i < rowCount; i++) {
                crs.next();
                String id = crs.getString(1);
                sql += id;
                sql += "', '";
            }
            crs.next();
            sql += crs.getString(1);
            sql += "')";
            
            conn = DriverManager.getConnection(Connections.connectionUrl(),
                                               Connections.user(),
                                               Connections.pwd());
            ps = conn.prepareStatement(sql);
            ps.setInt(1, loc);
            ret = ps.executeUpdate();
        } 
        catch (SQLException ex) {
            new ErrorLog().LogError(ex.getMessage(), "ReassignModel", "update location");
        }
        finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                new ErrorLog().LogError(ex.getMessage(), "ReassignModel", "ps close");
            }
            try {
                conn.close();
            } catch (SQLException ex) {
                new ErrorLog().LogError(ex.getMessage(), "ReassignModel", "conn close");
            }
        }
        
        return ret;
    }   
}
