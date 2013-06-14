package org.fancygiraffe.locations;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;
import java.util.Map;
import javax.sql.rowset.CachedRowSet;
import org.fancygiraffe.global.*;

/**
 *
 * @author Erik
 */
public class LocationModel {
	
	/**
	 * Adds a new location to the database 
	 * @param newLocation contains the values for the new location the Map will have the following keys
	 * <br/>location
	 * <br/>address
	 * <br/>city
	 * <br/>state
	 * <br/>zip
	 * <br/>phone
	 * <br/>district
	 * @return int with the locationID of the newly created location
	 */
	//Erik - 4/13/13
	public int addLocation(Map<String,String> newLocation) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int ret = -1;//return value - index of created row
		
		//Current DateTime for created and modified fields
		java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
		
		String insertSql = "INSERT INTO locations "
				+ "(created, modified, location_name, address, city, state, zip, phone, district, status) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";
				
		try {
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			
			//-------- Set Params ----------
			ps.setTimestamp(1, sqlDate);
			ps.setTimestamp(2, sqlDate);
			HelperMethods.setParam(newLocation, "location_name", ps, 3, Types.VARCHAR);
			HelperMethods.setParam(newLocation, "address", ps, 4, Types.VARCHAR);
			HelperMethods.setParam(newLocation, "city", ps, 5, Types.VARCHAR);
			HelperMethods.setParam(newLocation, "state", ps, 6, Types.VARCHAR);
			HelperMethods.setParam(newLocation, "zip", ps, 7, Types.VARCHAR);
			HelperMethods.setParam(newLocation, "phone", ps, 8, Types.VARCHAR);
			HelperMethods.setParam(newLocation, "district", ps, 9, Types.VARCHAR);
			
			//Run the statment
			ret = ps.executeUpdate();
			
			//Get last insert ID
			rs = ps.getGeneratedKeys();			
			if(rs.next()) {
				ret = (int)rs.getLong(1);
			}
			
			
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "LocationModel", "addLocation");			
		} finally {//Close all the things
			if(rs != null) {try {rs.close();} catch (SQLException ex) {	}}//So there
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return ret;
	}
	
	/** 
	 * Updates a location in the database 
	 * @param newLocationValues contains the new values for the location the Map will have the following keys
	 * <br/>location
	 * <br/>address
	 * <br/>city
	 * <br/>state
	 * <br/>zip
	 * <br/>phone
	 * <br/>district
	 * @return int with the number of rows affected
	 */
	//Erik - 4/13/13
	public int editLocation(Map<String,String> newLocationValues) {
		Connection con = null;
		PreparedStatement ps = null;
				
		int ret = 0;//return value - number of rows modified
		
		//Current DateTime for modified field
		java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
		
		String updateSql = "UPDATE locations "
				+ "SET modified=?, address=?, city=?, state=?, zip=?, phone=?, district=? "
				+ "WHERE location_name=?"
				+ "AND status=1";
		
		try {
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(updateSql);
			
			//-------- Set Params ----------
			ps.setTimestamp(1, sqlDate);
			HelperMethods.setParam(newLocationValues, "address", ps, 2, Types.VARCHAR);
			HelperMethods.setParam(newLocationValues, "city", ps, 3, Types.VARCHAR);
			HelperMethods.setParam(newLocationValues, "state", ps, 4, Types.VARCHAR);
			HelperMethods.setParam(newLocationValues, "zip", ps, 5, Types.VARCHAR);
			HelperMethods.setParam(newLocationValues, "phone", ps, 6, Types.VARCHAR);
			HelperMethods.setParam(newLocationValues, "district", ps, 7, Types.VARCHAR);
			HelperMethods.setParam(newLocationValues, "location_name", ps, 8, Types.VARCHAR);
			
			//Run statement - gets rows affected
			ret = ps.executeUpdate();
						
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "LocationModel", "editLocation");
		} finally {//Close everything
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return ret;		
	}
	
	/**
	 * Deletes a location from the database
	 * @param location the name of the location to be deleted
	 * @return number of rows affected
	 */
	//Erik - 4/14/13
	public int deleteLocation(String location) {
		int ret = 0;//return value number of rows affected
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String deleteSql = "{ CALL DELETE_LOCATION ( ? ) }";
		
		try {
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(deleteSql);
			
			//-------- Set Param ----------
			ps.setString(1, location);		
			
			//Run statement - gets rows affected
			ret = ps.executeUpdate();
						
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "LocationModel", "deleteLocation");
		} finally {//Close everything
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return ret;
	}
	
	/**
	 * Gets unique locations to populate dropdown lists
	 * @return CachedRowSet with unique locations as strings
	 */
	//Erik - 4/15/13
	public CachedRowSet getUniqueLocations() {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
				
		String sql = "SELECT location_name, id FROM locations WHERE status=1 GROUP BY location_name";
				
		try {
			crs = new CachedRowSetImpl();
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(sql);
			
			//Run statement
			rs = ps.executeQuery();			
			crs.populate(rs);
			
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "LocationModel", "getUniqueLocations");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
	
	/**
	 * Returns location(s) matching the given name
	 * @param name of location to look for
	 * @return CachedRowSet containing matching location(s)
	 */
	//Erik - 4/15/13
	public CachedRowSet getLocationByName(String name) {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
				
		String sql = "SELECT location_name, address, city, state, zip, phone, district "
				+ "FROM locations WHERE location_name = ? AND status = 1";
		
		try {
			crs = new CachedRowSetImpl();
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(sql);
			
			ps.setString(1, name);
			
			//Run statement
			rs = ps.executeQuery();
			crs.populate(rs);
		
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "LocationModel", "getLocationByName");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
}
