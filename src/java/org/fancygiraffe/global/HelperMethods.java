package org.fancygiraffe.global;
import java.sql.*;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Erik
 */
public class HelperMethods {
	
	/**
	 * If map contains the key, sets sql parameter at index to the value, else sets parameter to null
	 * @param map to check in
	 * @param key to check the map for
	 * @param ps PreparedStatement to set params for
	 * @param index index of the param for the PreaparedStatement
	 * @param type sql.Types i.e Types.VARCHAR
	 * @throws SQLException 
	 */
	public static void setParam(Map<String,String> map, String key, PreparedStatement ps, int index, int type) throws SQLException {
		if(!map.containsKey(key) || map.get(key).length() == 0) {
			ps.setNull(index, type);
		}
		else {
			switch (type) {
				case Types.VARCHAR:
					ps.setString(index, map.get(key));
					break;
				case Types.TINYINT:
					ps.setInt(index, Integer.parseInt(map.get(key)));
					break;
			}
		}		
	}
}
