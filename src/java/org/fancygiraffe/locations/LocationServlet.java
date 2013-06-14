/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package org.fancygiraffe.locations;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Erik
 */
@WebServlet("/locations")
public class LocationServlet extends HttpServlet {

	/**
	 * Processes requests for both HTTP
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
		response.setContentType("text/html;charset=UTF-8");
		
		//Get list of locations by name
		LocationModel lm = new LocationModel();
		CachedRowSet crs = lm.getUniqueLocations();
		request.setAttribute("locations", crs);
		
		RequestDispatcher view = request.getRequestDispatcher("locations.jsp");
		
		view.forward(request, response);
		
	}

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * 
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
		
		String action = request.getParameter("action");
		
		if(action != null) {
			if(action.equals("add"))
				addLocation(request);
			else if(action.equals("edit"))
				editLocation(request);
			else if(action.equals("delete"))
				deleteLocation(request);		
		}
		
		processRequest(request, response);
	}

	/**
	 * asks the model to add a location to the database add results to the request object
	 * @param request
	 */
	private void addLocation(HttpServletRequest request) {
		
		LocationModel lm = new LocationModel();
		Map<String,String> map = new HashMap<String,String>() {};
		Map<String,String[]> requestMap = request.getParameterMap();
		
		int i = -1;
		
		if(requestMap.containsKey("location")) {
			
			if(lm.getLocationByName(requestMap.get("location")[0]).size() == 0) {

				map.put("location_name", requestMap.get("location")[0]);
				if(requestMap.containsKey("address"))
					map.put("address", requestMap.get("address")[0]);
				if(requestMap.containsKey("city"))
					map.put("city", requestMap.get("city")[0]);
				if(requestMap.containsKey("state"))
					map.put("state", requestMap.get("state")[0]);
				if(requestMap.containsKey("zip"))
					map.put("zip", requestMap.get("zip")[0]);
				if(requestMap.containsKey("phone"))
					map.put("phone", requestMap.get("phone")[0]);
				if(requestMap.containsKey("district"))
					map.put("district", requestMap.get("district")[0]);
				
				i = lm.addLocation(map);
			}
		}		
		
		request.setAttribute("newLocationId", i);		
	}
	
	/**
	 * asks the model to update a location in the database add results to the request object
	 * @param request 
	 */
	private void editLocation(HttpServletRequest request) {
		
		LocationModel lm = new LocationModel();
		Map<String,String> map = new HashMap<String,String>() {};
		Map<String,String[]> requestMap = request.getParameterMap();

		int i = -1;
		
		if(requestMap.containsKey("address"))
			map.put("address", requestMap.get("address")[0]);
		if(requestMap.containsKey("city"))
			map.put("city", requestMap.get("city")[0]);
		if(requestMap.containsKey("state"))
			map.put("state", requestMap.get("state")[0]);
		if(requestMap.containsKey("zip"))
			map.put("zip", requestMap.get("zip")[0]);
		if(requestMap.containsKey("phone"))
			map.put("phone", requestMap.get("phone")[0]);
		if(requestMap.containsKey("district"))
			map.put("district", requestMap.get("district")[0]);
		if(requestMap.containsKey("location")) {
			map.put("location_name", requestMap.get("location")[0]);
			
			i = lm.editLocation(map);			
		}
		
		request.setAttribute("editRowsAffected", i);
		request.setAttribute("activeTab", 2);
		
	}
	
	/**
	 * asks the model to delete a location from the database add results to the request object
	 * @param request 
	 */
	private void deleteLocation(HttpServletRequest request) {
		
		LocationModel lm = new LocationModel();
		String location = request.getParameter("location");//name of location to be deleted
		
		int i = -1;
		
		if(location != null && location.length() > 0) {
			i = lm.deleteLocation(location);
		}
		
		request.setAttribute("deleteRowsAffected", i);
		request.setAttribute("activeTab", 3);
	}
	
	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}
}