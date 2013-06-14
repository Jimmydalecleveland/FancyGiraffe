package org.fancygiraffe.groups;

import org.fancygiraffe.global.Connections;
import java.sql.*;
import javax.sql.rowset.CachedRowSet;
import org.fancygiraffe.global.ErrorLog;

public class GroupQueryModel {

	/**
	 * Returns a CachedRowSet containing the asset with the matching asset_tag
	 * @param id the asset_tag to search for
	 * @return CachedRowSet with the matching asset
	 */
    public CachedRowSet rowset(String id) {

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        CachedRowSet crs = null;

        int groupId = 0;

        try {
            String sql1 = "SELECT group_id FROM assets WHERE tag = '" + id + "' ";
            String sql2 = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(Connections.connectionUrl(),
                    Connections.user(),
                    Connections.pwd());

            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            
            crs = new com.sun.rowset.CachedRowSetImpl();
            rs = statement.executeQuery(sql1);            
            
            if (rs != null) {
                rs.next();
                if (rs.getString(1) != null) {
                    groupId = Integer.parseInt(rs.getString(1));

                    sql2 = "SELECT a.tag, a.asset_name, a.asset_type, a.asset_condition, l.location_name, a.group_id "
                            + "FROM assets as a "
                            + "LEFT JOIN locations as l "
                            + "ON a.location_id = l.id "
                            + "WHERE a.group_id = " + groupId;
                }
                else {
                    sql2 = "SELECT a.tag, a.asset_name, a.asset_type, a.asset_condition, l.location_name, a.group_id "
                            + "FROM assets as a "
                            + "LEFT JOIN locations as l "
                            + "ON a.location_id = l.id "
                            + "WHERE a.tag = '" + id + "' ";
                } 
                rs = statement.executeQuery(sql2);   
                crs.populate(rs);
            }
            else
            {            
            crs.populate(rs);
            }
            
        } catch (SQLException ex) {
            new ErrorLog().LogError(ex.getMessage(), "GroupQueryModel", "rowset");
        } finally {
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (SQLException ex) {
                new ErrorLog().LogError(ex.getMessage(), "GroupQueryModel", "close connection");
            }
        }

        return crs;
    }
}
