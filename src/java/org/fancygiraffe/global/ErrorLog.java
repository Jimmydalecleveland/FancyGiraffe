package org.fancygiraffe.global;

import java.sql.*;

/**
 * 
 * @author Alexis
 */
public class ErrorLog {
    
    //Main constructor
    //this doesn't need any code
    public ErrorLog()
    {
        
    }
    
    //Inserts into the Errors table
    //Alexis F. 4-14-13
	/**
	 * Logs an error in the ErrorLog table
	 * @param err error message
	 * @param className name of class that caused the error
	 * @param method name of method that caused the error
	 */
    public void LogError(String err, String className, String method)
    {
        Connection c = null;
            try
            {
                    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                    c = DriverManager.getConnection(Connections.connectionUrl(), Connections.user(), Connections.pwd());
                    		
                    //prepared stored procedure
                    String sql = "{CALL INSERT_ERROR  ( ? , ? , ? )}";
                    PreparedStatement ps = c.prepareStatement(sql);
                    ps.setString(1, err);
                    ps.setString(2, className);
                    ps.setString(3,  method);
                    ps.execute();
                    ps.clearParameters();		
            }
            catch (Exception e) {
                    //we can't call the LogError method here
                    //because it will create a recursive infinite loop
                    System.out.println("Caught Exception: " + e);
            }
            finally
            {
	            	try{
	            		c.close();
	            	}
	            	catch (Exception e) {
	            		//we can't call the LogError method here
	                    //because it will create a recursive infinite loop
	                    System.out.println("Caught Exception: " + e);
	            	}
            }
    }
    
    
    //quick and dirty testing
    public static void main(String[] args)
	{    	
		new ErrorLog().LogError("testError","testClass","testMethod");
	}
}
