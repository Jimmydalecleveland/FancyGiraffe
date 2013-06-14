package org.fancygiraffe.assets;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;
import java.util.Map;
import javax.sql.rowset.CachedRowSet;
import org.fancygiraffe.global.*;
/**
*
* @author Alexis
*/
public class AssetModel {

    public AssetModel()
    {
        
    }
    
    /**
    * Adds a new asset to the database
    * @param Map<String,String> newAsset contains the values for the new asset
    * Map will have the following keys
    * <br/>String    description
    * <br/>String    model
    * <br/>String    serialnumber
    * <br/>String    assettype
    * <br/>String    value
    * <br/>String    location
    * <br/>String    condition
    * <br/>String    assettag
    * <br/>String    notes
    * 
    * @return int with the assetID of the newly created asset
    * 
    * Alexis F. 4-17-13
    */
    public int addAsset( Map<String,String> newAsset )
    {

        int newAssetID = 0;
        Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;	    	

        try
        {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());

            //prepared stored procedure
            String sSql = "{CALL INSERT_ASSET( ?, ?, ?, ?, ?, ?, ?, ?, ? )} ";
            ps = con.prepareStatement(sSql);                    


            HelperMethods.setParam(newAsset, "locationName", 	ps, 1, Types.VARCHAR);
            HelperMethods.setParam(newAsset, "inputAssetTag",	ps, 2, Types.VARCHAR);
            HelperMethods.setParam(newAsset, "description",	ps, 3, Types.VARCHAR);
            HelperMethods.setParam(newAsset, "model", 		ps, 4, Types.VARCHAR);
            HelperMethods.setParam(newAsset, "sn", 		ps, 5, Types.VARCHAR);
            HelperMethods.setParam(newAsset, "value", 		ps, 6, Types.VARCHAR);
            HelperMethods.setParam(newAsset, "condition", 	ps, 7, Types.VARCHAR);
            HelperMethods.setParam(newAsset, "type", 		ps, 8, Types.VARCHAR);
            HelperMethods.setParam(newAsset, "notes", 		ps, 9, Types.VARCHAR);


            rs = ps.executeQuery();									//execute query

            while(rs.next()) {
              newAssetID = Integer.parseInt(rs.getString(1));		//get new asset ID from executed query
            }

            ps.clearParameters();
        }
        catch (Exception e)
        {
            new ErrorLog().LogError(e.getMessage(), "AssetModel", "addAsset");	//log errors
            newAssetID = 0;
        }
        finally
        {
            if(rs != null) {try {rs.close();} catch (SQLException ex) { }}	//close everything
            if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
            if(con != null) {try {con.close();} catch (SQLException ex) { }}
        }

            return newAssetID;
    }
	
	
    
    /**
     * Updates the values of an asset in the database
     * @param Map<String,String> newAssetValues contains the edited values
     * Map will have the following keys
     * <br/>String    tag
     * <br/>String    locationid
     * <br/>String    name
     * <br/>String    model
     * <br/>String    serial
     * <br/>String    value
     * <br/>String    condition
     * <br/>String    type
     * 
     * @returns int with the number of rows affected
     * 
     * Alexis F. 4-17-13
     */
    public int editAsset( Map<String,String> newAssetValues )
    {
        int iRowsAffected = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try
        {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());

            //prepared stored procedure
            String sSql = "{CALL EDIT_ASSET( ?, ?, ?, ?, ?, ?, ?, ?, ? )} ";
            ps = con.prepareStatement(sSql);


            HelperMethods.setParam(newAssetValues, "locationName", 	ps, 1, Types.VARCHAR);
            HelperMethods.setParam(newAssetValues, "inputAssetTag",	ps, 2, Types.VARCHAR);
            HelperMethods.setParam(newAssetValues, "description",	ps, 3, Types.VARCHAR);
            HelperMethods.setParam(newAssetValues, "model", 		ps, 4, Types.VARCHAR);
            HelperMethods.setParam(newAssetValues, "sn", 		ps, 5, Types.VARCHAR);
            HelperMethods.setParam(newAssetValues, "value", 		ps, 6, Types.VARCHAR);
            HelperMethods.setParam(newAssetValues, "condition", 	ps, 7, Types.VARCHAR);
            HelperMethods.setParam(newAssetValues, "type", 		ps, 8, Types.VARCHAR);
            HelperMethods.setParam(newAssetValues, "notes", 		ps, 9, Types.VARCHAR);


             iRowsAffected = ps.executeUpdate();									//execute query


             ps.clearParameters();
             }
             catch (Exception e)
             {
                new ErrorLog().LogError(e.getMessage(), "AssetModel", "editAsset");	//log errors
                iRowsAffected = 0;
             }
             finally
             {
             	if(rs != null) {try {rs.close();} catch (SQLException ex) {	}}	//close everything
                if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
                if(con != null) {try {con.close();} catch (SQLException ex) { }}
             }
 	        
            return iRowsAffected;
     }
    
     
     
    /**
      * Deletes an asset from the database
      * @param String assetTag contains the assetTag of the asset to be deleted
      * 
      * @returns int with the number of rows affected
      * 
      * Alexis F. 4-17-13
      */
    public int deleteAsset( String assetTag )
    {
      		int iRowsAffected = 0;
  	    	Connection con = null;
                PreparedStatement ps = null;
  		ResultSet rs = null;
  	    	
  	    	
  	        try
  	        {
  	                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
  	                con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
                     
  	                //prepared stored procedure
                      String sSql = "{CALL DELETE_ASSET( ? )} ";
                      ps = con.prepareStatement(sSql);
                                          
                      ps.setString(1, assetTag);								//add assetTag to the query

                      iRowsAffected = ps.executeUpdate();						//execute query                      

                      ps.clearParameters();
              }
              catch (Exception e)
              {
                  	new ErrorLog().LogError(e.getMessage(), "AssetModel", "deleteAsset");	//log errors
                  	iRowsAffected = 0;
              }
              finally
              {
              	if(rs != null) {try {rs.close();} catch (SQLException ex) {	}}	//close everything
      			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
      			if(con != null) {try {con.close();} catch (SQLException ex) { }}
              }
  	        
  	        return iRowsAffected;
      }
           
      
      
    /**
    * Gets unique asset_types to populate drop-down list
    * 
    * @returns CachedRowSet with unique asset_types as strings
    * 
    * Alexis F. 4-21-13
    */
    public CachedRowSet getUniqueTypes() {
  		CachedRowSet crs = null;
  		ResultSet rs = null;
  		Connection con = null;
  		PreparedStatement ps = null;
  		
  		String deleteSql = "{ CALL DISTINCT_TYPES () }";
  		
  		try {
  			crs = new CachedRowSetImpl();
  			
  			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
  			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
  			ps = con.prepareStatement(deleteSql);
  			
  			//Run statement
  			rs = ps.executeQuery();
  			crs.populate(rs);
  						
  		} catch (SQLException ex) {
  			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getUniqueTypes");
  		} finally {//Close everything
  			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
  			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
  			if(con != null) {try {con.close();} catch (SQLException ex) { }}
  		}
  		
  		return crs;
  	}
  	
  	
    
    /**
   * Gets unique locations to populate drop-down list
   * 
   * @returns CachedRowSet with unique asset_types as strings
   * 
   * Alexis F. 4-21-13
   */
    public CachedRowSet getUniqueLocations() {
 		CachedRowSet crs = null;
 		ResultSet rs = null;
 		Connection con = null;
 		PreparedStatement ps = null;
 		
 		String deleteSql = "{ CALL DISTINCT_LOCATIONS () }";
 		
 		try {
 			crs = new CachedRowSetImpl();
 			
 			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
 			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
 			ps = con.prepareStatement(deleteSql);
 			
 			//Run statement
 			rs = ps.executeQuery();
 			crs.populate(rs);
 						
 		} catch (SQLException ex) {
 			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getUniqueLocations");
 		} finally {//Close everything
 			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
 			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
 			if(con != null) {try {con.close();} catch (SQLException ex) { }}
 		}
 		
 		return crs;
 	}
        
    public CachedRowSet getUniqueDistricts() {
  		CachedRowSet crs = null;
  		ResultSet rs = null;
  		Connection con = null;
  		PreparedStatement ps = null;
  		
  		String sql = "SELECT DISTINCT district FROM locations";
  		
  		try {
  			crs = new CachedRowSetImpl();
  			
  			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
  			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
  			ps = con.prepareStatement(sql);
  			
  			//Run statement
  			rs = ps.executeQuery();
  			crs.populate(rs);
  						
  		} catch (SQLException ex) {
  			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getUniqueDistricts");
  		} finally {//Close everything
  			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
  			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
  			if(con != null) {try {con.close();} catch (SQLException ex) { }}
  		}
  		
  		return crs;
  	}
        
    /**
	 * Gets all active assets
	 * @return CachedRowSet with assets rows
	 */
    //Nathan - 4/20/13
    public CachedRowSet getAllAssets() {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "SELECT assets.id, assets.tag, assets.asset_name, assets.model, assets.ser_no, assets.value, " +
                                "assets.asset_condition, assets.asset_type, " +
                                "locations.location_name, locations.address, locations.city, " +
                                "locations.state, locations.zip, locations.phone, locations.district " +
                "FROM  `assets` " +
                "LEFT JOIN locations ON ( assets.location_id = locations.id ) " +
                "WHERE assets.status =1 " +
                " LIMIT 0, 10 ";
                
		try {
			crs = new CachedRowSetImpl();
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(sql);
			
			//Run statement
			rs = ps.executeQuery();
			crs.populate(rs);
						
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getAllAssets");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
        
    /**
	 * Gets all active assets by their assigned location
	 * @return CachedRowSet
	 */
    //Nathan - 4/20/13
    public CachedRowSet getAssetsByLocation(String location_name) {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		
                String sql = "SELECT assets.tag, assets.asset_name, assets.asset_type, " + 
                                "assets.ser_no, assets.value, assets.asset_condition, " +
                                "locations.location_name, locations.address, locations.city, " +
                                "locations.state, locations.zip, locations.phone, assets.modified " +
                "FROM  `assets` " +
                "LEFT JOIN locations ON ( assets.location_id = locations.id ) " +
                "WHERE assets.status =1 " +
                "AND locations.status =1 " + 
                "AND location_name = ?";
                
		try {
			crs = new CachedRowSetImpl();
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(sql);
			
                        ps.setString(1, location_name);
                        
			//Run statement
			rs = ps.executeQuery();
			crs.populate(rs);
						
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getAssetsByLocation");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
        
    /**
	 * Gets all active assets by their type
	 * @param asset_type String containing the name of the asset type to search for
	 * @return CachedRowSet
	 */
    //Nathan - 4/20/13
    public CachedRowSet getAssetsByType(String asset_type) {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		
                String sql = "SELECT assets.tag, assets.asset_name, assets.model, " +
                                "assets.ser_no, assets.value, " +
                                "locations.location_name, assets.modified " +
                "FROM  `assets` " +
                "LEFT JOIN locations ON ( assets.location_id = locations.id ) " +
                "WHERE assets.status =1 " + 
                "AND assets.asset_type = ?";
                
		try {
			crs = new CachedRowSetImpl();
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(sql);
			
                        ps.setString(1, asset_type);
                        
			//Run statement
			rs = ps.executeQuery();
			crs.populate(rs);
						
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getAssetsByType");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
        
    /**
	 * Gets all active assets by their id
	 * @param asset_id asset id to search for
	 * @return CachedRowSet with assets rows
	 */
    //Nathan - 4/20/13
    public CachedRowSet getAssetsByTag(String asset_tag) {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		
                String sql = "SELECT assets.id, assets.tag, assets.asset_name, assets.model, assets.ser_no, assets.value, " +
                                "assets.asset_condition, assets.asset_type, " +
                                "locations.location_name, locations.address, locations.city, " +
                                "locations.state, locations.zip, locations.phone, locations.district " +
                "FROM  `assets` " +
                "LEFT JOIN locations ON ( assets.location_id = locations.id ) " +
                "WHERE assets.status =1 " +
                "AND assets.tag = ?";
                
                System.out.print(sql);
                
		try {
			crs = new CachedRowSetImpl();
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(sql);
			
                        ps.setString(1, asset_tag);
                        
			//Run statement
			rs = ps.executeQuery();
			crs.populate(rs);
						
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getAssetsByTag");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
        
    public CachedRowSet getAssetsByCondition(String asset_condition) {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		
                String sql = "SELECT assets.tag, assets.asset_name, assets.value, " +
                                    "locations.location_name " +
                "FROM  `assets` " +
                "LEFT JOIN locations ON ( assets.location_id = locations.id ) " +
                "WHERE assets.status =1 " +
                "AND assets.asset_condition = ?";
                
                System.out.print(sql);
                
		try {
			crs = new CachedRowSetImpl();
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(sql);
			
                        ps.setString(1, asset_condition);
                        
			//Run statement
			rs = ps.executeQuery();
			crs.populate(rs);
						
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getAssetsByCondition");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
        
    public CachedRowSet getAssetsByDistrict(String district) {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		
                String sql = "SELECT assets.tag, assets.asset_name, locations.district, " +
                                    "locations.location_name " +
                "FROM  `assets` " +
                "LEFT JOIN locations ON ( assets.location_id = locations.id ) " +
                "WHERE assets.status =1 " +
                "AND locations.district = ?";
                
                System.out.print(sql);
                
		try {
			crs = new CachedRowSetImpl();
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(sql);
			
                        ps.setString(1, district);
                        
			//Run statement
			rs = ps.executeQuery();
			crs.populate(rs);
						
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getAssetsByDistrict");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
        
    /**
	 * Gets all active assets by their location and type
	 * @param location name of location to search for
	 * @param  assetType name of aassetType to search for
	 * @return CachedRowSet
	 */
    //Nathan - 4/22/13
    public CachedRowSet getAssetsByLocationAndType(String location, String assetType) {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		
                String sql = "SELECT assets.id, assets.tag, assets.asset_name, assets.model, assets.ser_no, assets.value, " +
                                "assets.asset_condition, assets.asset_type, " +
                                "locations.location_name, locations.address, locations.city, " +
                                "locations.state, locations.zip, locations.phone, locations.district " +
                "FROM  `assets` " +
                "LEFT JOIN locations ON ( assets.location_id = locations.id ) " +
                "WHERE assets.status =1 " +
                "AND locations.status =1 " +
                "AND assets.location_id = ? " +
                "AND assets.asset_type = ?";
                
                System.out.print(sql);
                
		try {
			crs = new CachedRowSetImpl();
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(sql);
			
                        ps.setString(1, location);
                        ps.setString(2, assetType);
                        
			//Run statement
			rs = ps.executeQuery();
			crs.populate(rs);
						
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getAssetsByLocationAndType");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
        
    /**
	 * Get distinct assets for dropdown menus
	 * @return CachedRowSet
	 */
    //Nathan - 4/22/13
    public CachedRowSet getAssetsTypes() {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
                
		String sql = "SELECT DISTINCT asset_type FROM assets WHERE status = 1 AND asset_type IS NOT NULL ";
		
		try {
			crs = new CachedRowSetImpl();
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
			ps = con.prepareStatement(sql);
			
			//Run statement
			rs = ps.executeQuery();
			crs.populate(rs);
						
		} catch (SQLException ex) {
			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getAssetsTypes");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
        
            
    /**
   * Gets unique conditions to populate drop-down list
   * 
   * @returns CachedRowSet with unique asset_condition as strings
   * 
   * Alexis F. 4-21-13
   */
    public CachedRowSet getUniqueConditions() {
 		CachedRowSet crs = null;
 		ResultSet rs = null;
 		Connection con = null;
 		PreparedStatement ps = null;
 		
 		String deleteSql = "{ CALL DISTINCT_CONDITIONS () }";
 		
 		try {
 			crs = new CachedRowSetImpl();
 			
 			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		
 			con = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
 			ps = con.prepareStatement(deleteSql);
 			
 			//Run statement
 			rs = ps.executeQuery();
 			crs.populate(rs);
 						
 		} catch (SQLException ex) {
 			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getUniqueConditions");
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
    //Alexis - 4/27/13
    public CachedRowSet getAssetsByName(String name) {
		CachedRowSet crs = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
				
		String sql = "SELECT l.location_name, a.tag, a.asset_name, a.model, a.ser_no, a.value, a.asset_condition, a.asset_type, n.text  "
				+ " FROM assets as a "
                                + " LEFT JOIN locations as l ON a.location_id = l.id " 
                                + " LEFT JOIN notes as n ON a.id = n.asset_id "
                                + " WHERE a.tag = ? AND a.status = 1 ";
		
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
			new ErrorLog().LogError(ex.getMessage(), "AssetModel", "getAssetByName");
		} finally {//Close everything
			if(rs != null) {try {rs.close();} catch (SQLException ex) { }}
			if(ps != null) {try {ps.close();} catch (SQLException ex) { }}
			if(con != null) {try {con.close();} catch (SQLException ex) { }}
		}
		
		return crs;
	}
        
        
       /* public static void main(String[] args)
        {
            
            
            AssetModel am = new AssetModel();
	    Map<String,String> map = new HashMap<String,String>() {};
            
            
            map.put("inputAssetTag", "test2");
            map.put("locationName", "office");
            map.put("model", "model");
            map.put("sn", "sn");
            map.put("type", "typu");
            map.put("value", "2.00");
            map.put("description", "descuriptuon");
            map.put("condition", "condurishion");
            map.put("notes", null );
            
            
            System.out.println( am.editAsset(map) );*/
            
            
            /*AssetModel am = new AssetModel();
	    Map<String,String> map = new HashMap<String,String>() {};
            
            
            map.put("inputAssetTag", "tag6");
            map.put("locationName", "office");
            map.put("model", "model");
            map.put("sn", "sn");
            map.put("type", "typu");
            map.put("value", "2.00");
            map.put("description", "descuriptuon");
            map.put("condition", "condurishion");
            map.put("notes", "noto");
            
            
            System.out.println( am.addAsset(map) );
        }*/
}
