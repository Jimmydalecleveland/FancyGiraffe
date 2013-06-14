package org.fancygiraffe.global;

/**
 * Class returns connection info
 * @author Erik
 */
public class Connections {
    	/**
	 * returns url to connect to the database
	 * @return database url
	 */
	public static String connectionUrl() {
		return "jdbc:mysql://localhost:3306/db_name";
	}
	/**
	 * returns user name for database
	 * @return database user name
	 */
	public static String user() {
		return "user";
	}
	/**
	 * returns password for database
	 * @return database password
	 */
	public static String pwd() {
		return "pw";
	}
}
