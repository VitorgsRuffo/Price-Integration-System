/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DAO;
import dao.DAOFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Store;
import model.Script;


/**
 *
 * @author vitor
 */
@WebServlet(name = "StoreController", 
                    urlPatterns = {
                        "/store/create",
                        "/store/read",
                        "/store/update",
                        "/store/delete"
                    }
)
public class StoreController extends HttpServlet {
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        
        switch (request.getServletPath()) {
            case "/store/update": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    int storeId = Integer.parseInt(request.getParameter("id"));

                    DAO storeDao = daoFactory.getStoreDAO();
                    DAO scriptDao = daoFactory.getScriptDAO();
                       
                    Store store = (Store) storeDao.read(storeId);
                    Script script = scriptDao.readLastVersion(storeId); //create this method...

                    request.setAttribute("store", store);
                    request.setAttribute("script", script);
                    
                    dispatcher = request.getRequestDispatcher("/view/store/update.jsp");
                    dispatcher.forward(request, response);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }
                break;
            }
            case "/store/read": {
                
                break;
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String servletPath = request.getServletPath();        
        
        switch (servletPath) {
            
            case "/store/create":
            case "/store/update": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    Store store = new Store();
                    Script script = new Script();
                    
                    if(request.getParameter("id") != null)
                        store.setId(Integer.parseInt(request.getParameter("id")));
                        script.setStoreId(store.getId());
                    store.setName(request.getParameter("name"));
                    store.setAddress(request.getParameter("address"));
                    store.setPhone(request.getParameter("phone"));
                    script.setText(request.getParameter("scriptText"));
                   
                    
                    long millis = System.currentTimeMillis();  
                    script.setDate(new Date(millis));  
                    script.setTime(new Time(millis));
                    
                    DAO storeDao = daoFactory.getStoreDAO();
                    DAO scriptDao = daoFactory.getScriptDAO();
                    
                    if(servletPath.equals("/user/create")){
                       int storeId = storeDao.create(store); //return just created store id...
                       script.setStoreId(storeId);
                    }else{
                       storeDao.update(store);
                    }
                    scriptDao.create(script);
                    
                    response.sendRedirect(request.getContextPath() + "/index");
                    
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    response.sendRedirect(request.getContextPath() + servletPath);
                } catch (Exception ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    response.sendRedirect(request.getContextPath() + servletPath);
                }
                break;
            }
        }
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