package org.fancygiraffe.assets;

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
import org.fancygiraffe.global.ErrorLog;

/**
 *
 * @author Alexis
 */
@WebServlet("/assets")
public class AssetServlet extends HttpServlet {

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
		
		//Get list of assets by name
		AssetModel am = new AssetModel();
		CachedRowSet crsT = am.getUniqueTypes();
                CachedRowSet crsL = am.getUniqueLocations();
                CachedRowSet crsC = am.getUniqueConditions();
                
                
		request.setAttribute("types", crsT);
		request.setAttribute("locations", crsL);
                request.setAttribute("conditions", crsC);
                
		RequestDispatcher view = request.getRequestDispatcher("assets.jsp");
		
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
                                addAsset(request);
			else if(action.equals("edit"))
				editAsset(request);
			else if(action.equals("delete"))
				deleteAsset(request);		
		}
		
		processRequest(request, response);
	}

	/**
	 * asks the model to add an asset to the database add results to the request object
	 * @param request
	 */
	private void addAsset(HttpServletRequest request) {
		
		AssetModel am = new AssetModel();
		Map<String,String> map = new HashMap<String,String>() {};
		Map<String,String[]> requestMap = request.getParameterMap();
		
		int i = -1;
		
		if(requestMap.containsKey("assetTag")) {
			
                        if(requestMap.containsKey("description"))
                                map.put("description", requestMap.get("description")[0]);
                        if(requestMap.containsKey("model"))
                                map.put("model", requestMap.get("model")[0]);
                        if(requestMap.containsKey("sn"))
                                map.put("sn", requestMap.get("sn")[0]);
                        if(requestMap.containsKey("typeL") && requestMap.get("typeL").length > 0 )
                                map.put("type", requestMap.get("typeL")[0]);
                        else if(requestMap.containsKey("typeDD"))
                                map.put("type", requestMap.get("typeDD")[0]);
                        if(requestMap.containsKey("value"))
                                map.put("value", requestMap.get("value")[0]);
                        if(requestMap.containsKey("location"))
                                map.put("locationName", requestMap.get("location")[0]);
                        if(requestMap.containsKey("conditionL") && requestMap.get("conditionL").length > 0)
                                map.put("condition", requestMap.get("conditionL")[0]);
                        else if(requestMap.containsKey("conditionDD"))
                                map.put("condition", requestMap.get("conditionDD")[0]);
                        if(requestMap.containsKey("assetTag"))
                                map.put("inputAssetTag", requestMap.get("assetTag")[0]);
                        if(requestMap.containsKey("notes"))
                                map.put("notes", requestMap.get("notes")[0]);
                        
			i = am.addAsset(map);                        
                }		
                
		if(i > 0)
			request.setAttribute("result", "Asset added successfully.");
		else
			request.setAttribute("result", "Couldn't add asset.");
		
		request.setAttribute("id", i);		
	}
	
	/**
	 * asks the model to update an asset in the database add results to the request object
	 * @param request 
	 */
	private void editAsset(HttpServletRequest request) {
		
		AssetModel lm = new AssetModel();
		Map<String,String> map = new HashMap<String,String>() {};
		Map<String,String[]> requestMap = request.getParameterMap();
		
		if(requestMap.containsKey("description"))
                        map.put("description", requestMap.get("description")[0]);
                if(requestMap.containsKey("model"))
                        map.put("model", requestMap.get("model")[0]);
                if(requestMap.containsKey("sn"))
                        map.put("sn", requestMap.get("sn")[0]);
                if(requestMap.containsKey("type"))
                        map.put("type", requestMap.get("type")[0]);
                if(requestMap.containsKey("value"))
                        map.put("value", requestMap.get("value")[0]);
                if(requestMap.containsKey("location"))
                        map.put("locationName", requestMap.get("location")[0]);
                if(requestMap.containsKey("condition"))
                        map.put("condition", requestMap.get("condition")[0]);
                if(requestMap.containsKey("assetTag"))
                        map.put("inputAssetTag", requestMap.get("assetTag")[0]);
                if(requestMap.containsKey("notes"))
                        map.put("notes", requestMap.get("notes")[0]);
			
                int i = lm.editAsset(map);
                request.setAttribute("editRowsAffected", i);
                request.setAttribute("result", "Asset edited successfully.");
		
		
		request.setAttribute("activeTab", 2);
		
	}
		
	
	/**
	 * asks the model to delete an asset from the database add results to the request object
	 * @param request 
	 */
	private void deleteAsset(HttpServletRequest request) {
		
		AssetModel am = new AssetModel();
		String assetTag = request.getParameter("assetTag");//tag of asset to be deleted
		
		if(assetTag != null && assetTag.length() > 0) {
			int i = am.deleteAsset(assetTag);
			request.setAttribute("deleteRowsAffected", i);
			request.setAttribute("result", "Asset deleted successfully.");
		}
		
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