/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fancygiraffe.relocate;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;
import org.fancygiraffe.global.ErrorLog;
import org.fancygiraffe.locations.*;

/**
 *
 * @author Jimmy
 */
public class QueryServlet extends HttpServlet {

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

        HttpSession session = request.getSession(true);
        
        LocationModel lm = new LocationModel();
        CachedRowSet locations = lm.getUniqueLocations();
        request.setAttribute("locations", locations);

        String id = request.getParameter("assetTag");
        CachedRowSet crs = null;
        QueryModel m = new QueryModel();
        crs = m.rowset(id);

        if (crs.size() > 0)
        {
            CachedRowSet tempCrs = (CachedRowSet) session.getAttribute("sessionRelocateCrs");

            if (tempCrs == null) {
                session.setAttribute("sessionRelocateCrs", crs);
                tempCrs = crs;
            } else {
                try {
                    crs.beforeFirst();
                    while ( crs.next() ) {
                        tempCrs.moveToInsertRow();
                        tempCrs.updateString(1, crs.getString(1));
                        tempCrs.updateString(2, crs.getString(2));
                        tempCrs.updateString(3, crs.getString(3));
                        tempCrs.updateString(4, crs.getString(4));
                        tempCrs.updateString(5, crs.getString(5));
                        tempCrs.updateString(6, crs.getString(6));
                        tempCrs.insertRow();
                        tempCrs.moveToCurrentRow();
                    }
                } catch (SQLException ex) {
                    new ErrorLog().LogError(ex.getMessage(), "QueryServlet", "rowset");
                }
                session.setAttribute("sessionRelocateCrs", tempCrs);
            }

            request.setAttribute("data", tempCrs);
        }
        else
        {
            request.setAttribute("resultText", "Asset not found.");
        }

        RequestDispatcher view = request.getRequestDispatcher("relocate.jsp");
        view.forward(request, response);
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
