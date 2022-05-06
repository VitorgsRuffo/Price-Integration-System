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
import java.util.ArrayList;
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
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    //getting query parameters...
                    String parameter;
                    
                    parameter = request.getParameter("page");
                    int page = (parameter==null) ? (1) : (Integer.parseInt(parameter));
                    
                    parameter = request.getParameter("q");
                    String query = (parameter==null) ? ("") : (parameter);
                    query = query.toLowerCase();
                    
                    parameter = request.getParameter("minPrice");
                    Double minPrice = (parameter==null) ? (0.0) : (Double.parseDouble(parameter));
                    
                    parameter = request.getParameter("maxPrice");
                    Double maxPrice = (parameter==null) ? (1000000.00) : (Double.parseDouble(parameter));
                    
                    String color = request.getParameter("color");
                    
                    String secMem = request.getParameter("secMem");
                    
                    String orderBy = request.getParameter("orderBy");
                    
                    
                    //get necessary DAO(s)...
                    PgIPhoneDAO iphoneDAO = (PgIPhoneDAO) daoFactory.getIphoneDAO();
                    PgIPhoneVersionDAO iphoneVersionDAO = (PgIPhoneVersionDAO) daoFactory.getIphoneVersionDAO();

                    
                    //query the database...
                    List<Iphone> selectedIphones = new ArrayList<Iphone>();
                    
                    //List<Iphone> iphones = iphoneDAO.allByFilters(query, color, secMem);

//                    int iphonesLen = iphones.size();
//                    for (int i = 0; i<iphonesLen; i++){
//                        Iphone iphone = iphones.get(i);
//                        List<IphoneVersion> versions = iphoneVersionDAO.allByKey(iphone.getModelName(), iphone.getSecondaryMemory(), iphone.getColor());
//                        if(versions.get(0).getCashPayment() >= minPrice && versions.get(0).getCashPayment() <= maxPrice){ //applying price filter...
//                            iphone.setVersions(versions);
//                            selectedIphones.add(iphone);
//                        }
//                    }
//                    
//                    //applying order by:
//                    if(orderBy != null){
//                        if(orderBy.equals("asc")){
//                            
//                        }else{
//                        
//                        }
//                    }
                    
                    //apxpend result to request
                    request.setAttribute("iphones", selectedIphones);
                    
                    //call view
                    dispatcher = request.getRequestDispatcher("/search.jsp");
                    dispatcher.forward(request, response);
               

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/index?search=failure");
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
