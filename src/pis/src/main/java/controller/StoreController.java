/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DAO;
import dao.DAOFactory;
import dao.PgScriptDAO;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.PgIPhoneDAO;
import dao.PgScriptExecutionDAO;
import dao.PgStoreDAO;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import static sun.font.CreatedFontTracker.MAX_FILE_SIZE;
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
import model.Script;
import model.ScriptExecution;
import model.crawling.CrawledData;
import model.crawling.CrawledIphone;
import model.crawling.CrawledRating;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author vitor and wellinton
 */
@WebServlet(name = "StoreController", 
                    urlPatterns = {
                        "", "/index",
                        "/store/create",
                        "/store/read",
                        "/store/update",
                        "/store/delete",
                        "/store/read/script/executions",
                        "/store/crawling"
                    }
)
public class StoreController extends HttpServlet {
    /**
     * Pasta para salvar os arquivos que foram 'upados'. Os arquivos vão ser
     * salvos na pasta de build do servidor. Ao limpar o projeto (clean),
     * pode-se perder estes arquivos. Façam backup antes de limpar.
     */
    private static final String SAVE_DIR = "crawling";
    
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
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    DAO storeDao = daoFactory.getStoreDAO();                      
                    List<Store> storeList = storeDao.all(); 
                    request.setAttribute("storeList", storeList);
                    
                    dispatcher = request.getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/index");
                }
                break;
            }
        
            case "/store/create": {
                    
                dispatcher = request.getRequestDispatcher("/store/create.jsp");
                dispatcher.forward(request, response);
                break;
            }

            case "/store/update": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    int storeId = Integer.parseInt(request.getParameter("id"));

                    DAO storeDao = daoFactory.getStoreDAO();
                    PgScriptDAO scriptDao = (PgScriptDAO) daoFactory.getScriptDAO();
                       
                    Store store = (Store) storeDao.read(storeId); 
                    Script script = scriptDao.readLastVersion(storeId);
                    
                    request.setAttribute("store", store);
                    request.setAttribute("script", script);
                    
                    dispatcher = request.getRequestDispatcher("/store/update.jsp");
                    dispatcher.forward(request, response);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/index?update=failure");
                }
                break;
            }
            
            case "/store/read": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    int storeId = Integer.parseInt(request.getParameter("id"));

                    PgStoreDAO storeDao = (PgStoreDAO) daoFactory.getStoreDAO();
                       
                    Store store = (Store) storeDao.readStoreWithScriptData(storeId); 

                    request.setAttribute("store", store);
                    
                    dispatcher = request.getRequestDispatcher("/store/read.jsp");
                    dispatcher.forward(request, response);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/index?read=failure");
                }
                break;
            }
            
            case "/store/read/script/executions": {
                int storeId = Integer.parseInt(request.getParameter("storeId"));
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    int scriptVersionNum = Integer.parseInt(request.getParameter("scriptVersionNum"));

                    PgScriptExecutionDAO dao = (PgScriptExecutionDAO) daoFactory.getScriptExecutionDAO();
                    List<ScriptExecution> exes = dao.allByKey(storeId, scriptVersionNum); 
                            
                    Gson gson = new GsonBuilder().create();
                    String json = gson.toJson(exes);

                    response.getOutputStream().print(json);
                    
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/store/read?id=" + String.valueOf(storeId)+ "?read_history=failure");
                }
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
        
        String servletPath = request.getServletPath();

        switch (servletPath) {
            
            case "/store/create": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    Store store = new Store();
                    
                    store.setName(request.getParameter("name"));
                    store.setAddress(request.getParameter("address"));
                    store.setPhone(request.getParameter("phone"));
                    
                    PgStoreDAO storeDao = (PgStoreDAO) daoFactory.getStoreDAO();
                    int storeId = storeDao.createReturningId(store);
                    
                    Script script = new Script();
                    script.setText(request.getParameter("scriptText"));
                    script.setStoreId(storeId);
                    
                    long millis = System.currentTimeMillis();  
                    script.setDate(new Date(millis));  
                    script.setTime(new Time(millis));
                    
                    DAO scriptDao = daoFactory.getScriptDAO();
                    scriptDao.create(script);
                    
                    response.sendRedirect(request.getContextPath() + "/index?create=success");
                    
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    response.sendRedirect(request.getContextPath() + servletPath + "?create=failure");
                } catch (Exception ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    response.sendRedirect(request.getContextPath() + servletPath + "?create=failure");
                }
                break;
            }
            
            case "/store/update": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    /* store */
                    Store store = new Store();
                    
                    store.setId(Integer.parseInt(request.getParameter("id")));
                    store.setName(request.getParameter("name"));
                    store.setAddress(request.getParameter("address"));
                    store.setPhone(request.getParameter("phone"));
                    
                    DAO storeDao = daoFactory.getStoreDAO();
                    storeDao.update(store);
                    
                    /* script */
                    PgScriptDAO scriptDao = (PgScriptDAO) daoFactory.getScriptDAO();
                    Script lastScript = scriptDao.readLastVersion(store.getId());
                    
                    if(lastScript.getText() == null || !(lastScript.getText().equals(request.getParameter("scriptText")))) {
                        Script script = new Script();
                        
                        script.setStoreId(store.getId());
                        script.setText(request.getParameter("scriptText"));

                        long millis = System.currentTimeMillis();  
                        script.setDate(new Date(millis));  
                        script.setTime(new Time(millis));
                    
                        scriptDao.create(script);
                    }
                    
                    response.sendRedirect(request.getContextPath() + "/index?update=success");
                    
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller1", ex);
                    response.sendRedirect(request.getContextPath() + servletPath + "?id="+request.getParameter("id")+"&update=failure");
                } catch (Exception ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller2", ex);
                    response.sendRedirect(request.getContextPath() + servletPath + "?id="+request.getParameter("id")+"&update=failure");
                }
                break;
            }
            
            case "/store/delete": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    DAO storeDao = daoFactory.getStoreDAO();
                    int storeId = Integer.parseInt(request.getParameter("id"));
                    storeDao.delete(storeId);

                    response.sendRedirect(request.getContextPath() + "/index?delete=success");
                    
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    response.sendRedirect(request.getContextPath() + "/index?delete=failure");
                } catch (Exception ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    response.sendRedirect(request.getContextPath() + "/index?delete=failure");
                }
                break;
            }
            
            case "/store/crawling":{                
                //receiving .json file...
                int storeId = -1;
                String savePath = "";
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(MAX_FILE_SIZE);
                factory.setRepository(new File("/tmp"));
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(MAX_FILE_SIZE);

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {

                    List<FileItem> items = upload.parseRequest(request);

                    Iterator<FileItem> iter = items.iterator();
                    while (iter.hasNext()) {
                        FileItem item = iter.next();

                        if (item.isFormField()) {
                            storeId = Integer.parseInt(item.getString());
                            
                        } else {
                            String fieldName = item.getFieldName();
                            String fileName = item.getName();
                            if (fieldName.equals("crawling_file") && !fileName.isBlank()) {
                                
                                // Pega o caminho absoluto da aplicação
                                String appPath = request.getServletContext().getRealPath("");
                                // Grava novo arquivo na pasta img no caminho absoluto
                                savePath = appPath + File.separator + SAVE_DIR + File.separator + fileName;
                                File uploadedFile = new File(savePath);
                                item.write(uploadedFile);
                            }else{
                                throw new Exception("error: invalid file.");
                            }
                        }
                    }
                    
                    //parsing .json crawled data...
                    Gson gson = new GsonBuilder().create();
                    Reader reader = new FileReader(savePath);
                    CrawledData[] data = gson.fromJson(reader, CrawledData[].class);
                    
                    
                    //inserting crawled data on db...
                    PgIPhoneDAO iphoneDao = (PgIPhoneDAO) daoFactory.getIphoneDAO();
                    DAO iphoneVersionDao = daoFactory.getIphoneVersionDAO();
                    DAO ratingDao = daoFactory.getRatingDAO();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = data[0].getData();
                    java.util.Date date = format.parse(dateString);
                    Date sqlDate = new Date(date.getTime());
                    
                    String source = data[0].getLoja();
                    
                    for(int i = 1; i<data.length; i++){
                        
                        CrawledIphone cIphone = data[i].getIphone();
                        
                        Iphone iphone = new Iphone();
                        iphone.setModelName(cIphone.getModelo_nome());
                        iphone.setColor(cIphone.getCor());
                        iphone.setSecondaryMemory(cIphone.getMem_int()); 
                        iphone.setVoltage(cIphone.getVoltagem()); 
                        iphone.setModelCod(cIphone.getModelo_cod());
                        iphone.setIphoneLink(cIphone.getLink_iphone());
                        iphone.setImageLink(cIphone.getLink_imagem());
                        iphone.setDisplaySize(cIphone.getTam_tela());
                        iphone.setFrontCam(cIphone.getResolucao_cam_front());
                        iphone.setBackCam(cIphone.getResolucao_cam_tras());
                        iphone.setRamMemory(cIphone.getMem_ram()); 
                        iphone.setTitle(cIphone.getTitulo());
                        iphone.setMainSource(source);
                        iphoneDao.integrate(iphone);
                        
                        IphoneVersion iphoneVersion = new IphoneVersion();
                        iphoneVersion.setStoreId(storeId);
                        iphoneVersion.setModelName(iphone.getModelName());
                        iphoneVersion.setColor(iphone.getColor());
                        iphoneVersion.setSecondaryMemory(iphone.getSecondaryMemory());
                        iphoneVersion.setDate(sqlDate);
                        iphoneVersion.setCashPayment(cIphone.getPreco_avista());
                        iphoneVersion.setInstallmentPayment(cIphone.getPreco_aprazo());
                        iphoneVersion.setRatingAverage(Double.parseDouble(cIphone.getMedia_nota()));
                        iphoneVersion.setRatingAmount(Integer.parseInt(cIphone.getQuantidade_avaliacoes()));
                        iphoneVersionDao.create(iphoneVersion);

                        List<CrawledRating> cRatings = data[i].getAvaliacoes();
                        for(int j = 0; j<cRatings.size(); j++){
                            CrawledRating cRating = cRatings.get(j); 
                            Rating rating = new Rating();
                            rating.setStoreId(storeId);
                            rating.setModelName(iphone.getModelName());
                            rating.setColor(iphone.getColor());
                            rating.setSecondaryMemory(iphone.getSecondaryMemory());
                            rating.setTitle(cRating.getTitulo());
                            rating.setDescription(cRating.getDescricao());
                            rating.setDate(sqlDate);
                            rating.setRaterName(cRating.getAvaliador_nome());
                            rating.setLikes(Integer.parseInt(cRating.getLikes()));
                            rating.setDeslikes(Integer.parseInt(cRating.getDeslikes()));
                            rating.setRating(Double.parseDouble(cRating.getNota()));
                            ratingDao.create(rating);                       
                        }
                    }
                    
                    //saving crawling script execution on database... (we consider that the inputed json was resulted from the execution of the last script version.)
                    PgScriptDAO scriptDao = (PgScriptDAO) daoFactory.getScriptDAO();
                    Script lastScript = scriptDao.readLastVersion(storeId);
                    
                    ScriptExecution scriptExecution = new ScriptExecution();
                    scriptExecution.setStoreId(storeId);
                    scriptExecution.setScriptVersionNum(lastScript.getVersionNum());
                    long millis = System.currentTimeMillis();  
                    scriptExecution.setDate(new Date(millis));
                    scriptExecution.setTime(new Time(millis));
                    
                    PgScriptExecutionDAO scriptExeDao = (PgScriptExecutionDAO) daoFactory.getScriptExecutionDAO();
                    scriptExeDao.create(scriptExecution);

                    
                    //redirecting user...
                    response.sendRedirect(request.getContextPath() + "/store/read?id=" + String.valueOf(storeId) + "&crawling=success");

                } catch (FileUploadException ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    response.sendRedirect(request.getContextPath() + "/store/read?id=" + String.valueOf(storeId) + "&crawling=failure");
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    response.sendRedirect(request.getContextPath() + "/store/read?id=" + String.valueOf(storeId) + "&crawling=failure");
                } catch (Exception ex) {
                    Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    response.sendRedirect(request.getContextPath() + "/store/read?id=" + String.valueOf(storeId) + "&crawling=failure");
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