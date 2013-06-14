/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fancygiraffe.locations; 
 
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.CachedRowSet;
import org.fancygiraffe.global.ErrorLog;

/**
 *
 * @author Erik
 */
@WebServlet("/locationJSONServlet")
public class locationJSONServlet extends HttpServlet {

	/**
	 * Processes requests for both HTTP
	 * Returns JSON for AJAX calls
	 * <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		LocationModel lm = new LocationModel();
		
		String name = request.getParameter("name");
		
		//default output
		String json = "{" + 
					"\"location_name\":\"null\"," +
					"\"address\":\"null\"," +
					"\"city\":\"null\"," +
					"\"state\":\"null\"," +
					"\"zip\":\"null\"," +
					"\"phone\":\"null\"," +
					"\"district\":\"null\"}";
		
		if(name != null && name.length() > 0) {
			CachedRowSet cs = lm.getLocationByName(name);
				
			try {
				if(cs.size() > 0) {
					cs.next();	
					json = "{" +
						"\"location_name\":\"" + cs.getString("location_name") +"\"," +
						"\"address\":\"" + cs.getString("address") +"\"," +
						"\"city\":\"" + cs.getString("city") + "\"," +
						"\"state\":\"" + cs.getString("state") + "\"," +
						"\"zip\":\"" + cs.getString("zip") + "\"," +
						"\"phone\":\"" + cs.getString("phone") + "\"," +
						"\"district\":\"" + cs.getString("district") + "\"}";
				}
			} catch(SQLException ex) {
				new ErrorLog().LogError(ex.getMessage(), "locationJSONServlet", "processRequest");
			}
		}
				
		out.println(json);
		out.close();
		
		
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP
	 * <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
