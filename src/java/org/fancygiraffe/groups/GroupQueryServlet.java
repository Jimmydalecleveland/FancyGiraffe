/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fancygiraffe.groups;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;
import org.fancygiraffe.global.ErrorLog;

/**
 *
 * @author Jimmy
 */
public class GroupQueryServlet extends HttpServlet {

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


        //**********************************************************
        // Tells the JSP what tab was open last
        //**********************************************************
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("create")) {
                request.setAttribute("activeTab", 1);
            } else if (action.equals("dissolve")) {
                request.setAttribute("activeTab", 3);
            }
        }
        //**********************************************************

        RequestDispatcher view = request.getRequestDispatcher("groups.jsp");
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

        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("create")) {
                queryCreate(request);
            } else if (action.equals("dissolve")) {
                queryDissolve(request);
            }
        }

        processRequest(request, response);
    }

    private void queryCreate(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        String id = request.getParameter("assetTag");
        CachedRowSet crs = null;

        GroupQueryModel m = new GroupQueryModel();
        crs = m.rowset(id);

        
        if (crs.size() > 0)
        {
            CachedRowSet tempCrs = (CachedRowSet) session.getAttribute("sessionGroupCrs");

            if (tempCrs == null) {
                session.setAttribute("sessionGroupCrs", crs);
                tempCrs = crs;

            } else {
                try {
                    crs.beforeFirst();
                    while (crs.next()) {
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
                    new ErrorLog().LogError(ex.getMessage(), "GroupQueryServlet", "processRequest"); //log errors
                }
                session.setAttribute("sessionGroupCrs", tempCrs);
            }

            request.setAttribute("data", tempCrs);
        }
        else
        {
            request.setAttribute("createResultText", "Asset not found.");
        }
        


        //**********************************************************
        // Tells the JSP what tab was open last
        //**********************************************************
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("create")) {
                request.setAttribute("activeTab", 1);
            } else if (action.equals("dissolve")) {
                request.setAttribute("activeTab", 3);
            }
        }
        //**********************************************************
    }

    private void queryDissolve(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        String id = request.getParameter("assetTag");
        CachedRowSet crs = null;

        GroupQueryModel m = new GroupQueryModel();
        crs = m.rowset(id);

        if (crs.size() > 0)
        {

            CachedRowSet tempCrs = (CachedRowSet) session.getAttribute("sessionDissolveCrs");

            if (tempCrs == null) {
                session.setAttribute("sessionDissolveCrs", crs);
                tempCrs = crs;

            } else {
                try {
                    crs.beforeFirst();
                    while (crs.next()) {
                        tempCrs.moveToInsertRow();
                        tempCrs.updateString(1, crs.getString(1));
                        tempCrs.updateString(2, crs.getString(2));
                        tempCrs.updateString(3, crs.getString(3));
                        tempCrs.updateString(4, crs.getString(4));
                        tempCrs.updateString(5, crs.getString(5));
                        tempCrs.insertRow();
                        tempCrs.moveToCurrentRow();
                    }
                } catch (SQLException ex) {
                    new ErrorLog().LogError(ex.getMessage(), "GroupQueryServlet", "processRequest"); //log errors
                }
                session.setAttribute("sessionDissolveCrs", tempCrs);
            }

            request.setAttribute("data", tempCrs);           
        }
        else
        {
            request.setAttribute("dissolveResultText", "Asset not found.");
        }

        //**********************************************************
        // Tells the JSP what tab was open last
        //**********************************************************
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("create")) {
                request.setAttribute("activeTab", 1);
            } else if (action.equals("dissolve")) {
                request.setAttribute("activeTab", 3);
            }
        }
        //**********************************************************
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
