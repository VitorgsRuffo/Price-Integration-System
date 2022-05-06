/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DAO;
import dao.DAOFactory;
import dao.PgIPhoneDAO;
import java.io.IOException;
import dao.PgIPhoneVersionDAO;
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
@WebServlet(name = "SearchController",
        urlPatterns = {
            "", "/index", "/search"
        }
)
public class SearchController extends HttpServlet {

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
            case "":
            case "/index": {

                //redirect to view(ip1, ip2) ...
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {

                    //reading cheapest and most expensive iphones from database...
                    PgIPhoneVersionDAO iphoneVersionDAO = (PgIPhoneVersionDAO) daoFactory.getIphoneVersionDAO();
                    List<IphoneVersion> iphoneVersions = iphoneVersionDAO.allOrderByCashPayment();
                    IphoneVersion cheapestIphone = iphoneVersions.get(0);
                    IphoneVersion mostExpensiveIphone = iphoneVersions.get(iphoneVersions.size() - 1);

                    //reading those iphones images links...
                    PgIPhoneDAO iphoneDAO = (PgIPhoneDAO) daoFactory.getIphoneDAO();

                    Iphone cIp = iphoneDAO.readByKey(cheapestIphone.getModelName(),
                            cheapestIphone.getSecondaryMemory(),
                            cheapestIphone.getColor());
                    String cheapestIphoneImgLink = cIp.getImageLink();

                    Iphone eIp = iphoneDAO.readByKey(mostExpensiveIphone.getModelName(),
                            mostExpensiveIphone.getSecondaryMemory(),
                            mostExpensiveIphone.getColor());
                    String mostExpensiveIphoneImgLink = eIp.getImageLink();

                    //reading those iphones store names...
                    DAO storeDAO = daoFactory.getStoreDAO();

                    Store cSt = (Store) storeDAO.read(cheapestIphone.getStoreId());
                    String cheapestIphoneStoreName = cSt.getName();
                    Store eSt = (Store) storeDAO.read(mostExpensiveIphone.getStoreId());
                    String mostExpensiveIphoneStoreName = eSt.getName();

                    request.setAttribute("cheapestIphone", cheapestIphone);
                    request.setAttribute("mostExpensiveIphone", mostExpensiveIphone);
                    request.setAttribute("cheapestIphoneImgLink", cheapestIphoneImgLink);
                    request.setAttribute("mostExpensiveIphoneImgLink", mostExpensiveIphoneImgLink);
                    request.setAttribute("cheapestIphoneStoreName", cheapestIphoneStoreName);
                    request.setAttribute("mostExpensiveIphoneStoreName", mostExpensiveIphoneStoreName);

                    dispatcher = request.getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    System.out.println(ex);
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/teste");
                }
                break;
            }

            case "/search": {
                
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
