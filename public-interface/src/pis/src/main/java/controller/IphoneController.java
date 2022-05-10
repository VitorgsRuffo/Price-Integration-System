/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DAO;
import dao.DAOFactory;
import java.io.IOException;
import dao.PgIPhoneDAO;
import dao.PgIPhoneVersionDAO;
import dao.PgRatingDAO;
import dao.PgStoreDAO;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Date;
import java.sql.Time;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Iphone;
import model.IphoneVersion;
import model.Rating;
import model.Store;


/**
 *
 * @author vitor and wellinton
 */
@WebServlet(name = "IphoneController", 
                    urlPatterns = {
                        "/iphone"
                    }
)
public class IphoneController extends HttpServlet {
    
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
            case "/iphone": {
                
                //getting query parameters...
                String modelName = request.getParameter("modelName");
                String color = request.getParameter("color");
                String secMem = request.getParameter("secMem");
                System.out.println(modelName);
                System.out.println(color);
                System.out.println(secMem);
                String query = request.getParameter("q");
                if(query == null)
                    query = "";
                
                 try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    //get necessary DAO(s)...
                    PgIPhoneDAO iphoneDAO = (PgIPhoneDAO) daoFactory.getIphoneDAO();
                    PgRatingDAO ratingDAO = (PgRatingDAO) daoFactory.getRatingDAO();
                    PgIPhoneVersionDAO iphoneVersionDAO = (PgIPhoneVersionDAO) daoFactory.getIphoneVersionDAO();
                    
                    //query the database...
                    
                    //2, 1:
                    //List<IphoneVersion> lastVersions = iphoneVersionDAO.lastVersionOnEachStoreByKey(modelName, secMem, color);
                    
                    //3:
                    List<IphoneVersion> allVersions = iphoneVersionDAO.allByKey(modelName, secMem, color);
                    for(int i = 0; i<allVersions.size(); i++)
                        System.out.println(allVersions.get(i).getDate());

                    //5, 1:
                    //Iphone iphone = iphoneDAO.readByKey(modelName, secMem, color);
                    
                    //6:
                    //List<Rating> ratings = ratingDAO.allByIphoneKeyWithStoreName(modelName, secMem, color); 
                    
                    
                    //append result to request
                    //request.setAttribute("lastVersions", lastVersions);
                    request.setAttribute("allVersions", allVersions);
                    //request.setAttribute("iphoneInfo", iphone);
                    //request.setAttribute("ratings", ratings);

                    //call view
                    dispatcher = request.getRequestDispatcher("/pages/iphone.jsp");
                    dispatcher.forward(request, response);
               

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/search?q=" + query + "&read=failure");
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