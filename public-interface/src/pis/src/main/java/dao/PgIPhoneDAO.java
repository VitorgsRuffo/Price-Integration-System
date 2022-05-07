/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Iphone;
import model.IphoneVersion;

/**
 *
 * @author wellinton
 */
public class PgIPhoneDAO implements DAO {
    
    private final Connection connection;
       
    private static final String READ_BY_KEY_QUERY = "SELECT * FROM pis.Iphones WHERE model_name = ? AND sec_mem = ? AND color = ?";
    
    private static final String CREATE_QUERY =
                                "INSERT INTO pis.iphone(model_name, sec_mem, color, title, iphone_link, image_link, model_cod, display_size, front_cam, back_cam, ram_mem, voltage, main_source) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    
    private static final String MASTER_UPDATE_QUERY = 
                                "UPDATE pis.Iphones SET voltage = ?, iphone_link = ?, image_link = ?, display_size = ?, front_cam = ?, back_cam = ?, ram_mem = ?, title = ?, main_source = ? " +
                                "WHERE model_name = ? AND sec_mem = ? AND color = ?;";
    
    private static final String NOT_MASTER_UPDATE_QUERY = 
                                "UPDATE pis.Iphones SET model_cod = ? WHERE model_name = ? AND sec_mem = ? AND color = ?;";
    
    private static final String MAIN_SOURCE_QUERY = 
                                "SELECT main_source FROM pis.Iphones WHERE model_name = ? AND sec_mem = ? AND color = ?;";
    
    private static String ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = "SELECT * FROM pis.Iphones AS ip JOIN ( " +
                                                                    "SELECT ip.model_name, ip.color, ip.sec_mem, MIN(ipv.cash_payment) AS cheapest_price " +
                                                                    "FROM pis.Iphones AS ip JOIN IphoneVersions AS ipv " +
                                                                    "ON ip.model_name = ipv.model_name AND ip.color = ipv.color AND ip.sec_mem = ipv.sec_mem " +
                                                                    "WHERE ";

    private static String ALL_BY_FILTERS_QUERY = "SELECT * FROM pis.Iphones WHERE LOWER(title) LIKE '%?%";

    
    public PgIPhoneDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Object t) throws SQLException {
        Iphone iphone = (Iphone) t;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, iphone.getModelName());
            statement.setString(2, iphone.getSecondaryMemory());
            statement.setString(3, iphone.getColor());
            statement.setString(4, iphone.getTitle());
            statement.setString(5, iphone.getIphoneLink());
            statement.setString(6, iphone.getImageLink());
            statement.setString(7, iphone.getModelCod());
            statement.setString(8, iphone.getDisplaySize());
            statement.setString(9, iphone.getFrontCam());
            statement.setString(10, iphone.getBackCam());
            statement.setString(11, iphone.getRamMemory());
            statement.setString(12, iphone.getVoltage());
            statement.setString(13, iphone.getMainSource());

            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
           throw ex;
        }
    }
    
    public void integrate(Object t) throws SQLException {
        
        String master = "magalu";
        Iphone iphone = (Iphone) t;
        String source = iphone.getMainSource(); 

        try (PreparedStatement statement = connection.prepareStatement(MAIN_SOURCE_QUERY)) {
            statement.setString(1, iphone.getModelName());
            statement.setString(2, iphone.getSecondaryMemory());
            statement.setString(3, iphone.getColor());
            
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    this.create(t);
                } else {
                    if(source.equals(master)) { // Sobrescreve o que tem no banco, independente se for master ou nao
                        this.masterUpdate(iphone);
                        return;
                    }
                    String bd_source = result.getString("main_source");

                    if (bd_source.equals(master)) {
                        this.notMasterUpdate(iphone);
                        return;
                    }
                }   
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
    }
    
    public void masterUpdate(Iphone iphone) throws SQLException {
        
        try (PreparedStatement statement = connection.prepareStatement(MASTER_UPDATE_QUERY)) {
            statement.setString(1, iphone.getVoltage());
            statement.setString(2, iphone.getIphoneLink());
            statement.setString(3, iphone.getImageLink());
            statement.setString(4, iphone.getDisplaySize());
            statement.setString(5, iphone.getFrontCam());
            statement.setString(6, iphone.getBackCam());
            statement.setString(7, iphone.getRamMemory());
            statement.setString(8, iphone.getTitle());
            statement.setString(9, iphone.getMainSource());
            statement.setString(10, iphone.getModelName());
            statement.setString(11, iphone.getSecondaryMemory());
            statement.setString(12, iphone.getColor());
            
            statement.executeUpdate();
           
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
    }
    
    public void notMasterUpdate(Iphone iphone) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(NOT_MASTER_UPDATE_QUERY)) {
            statement.setString(1, iphone.getModelCod());
            statement.setString(2, iphone.getModelName());
            statement.setString(3, iphone.getSecondaryMemory());
            statement.setString(4, iphone.getColor());
            
            statement.executeUpdate();
           
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
    }
    
    public Iphone readByKey(String modelName, String secMem, String color) throws SQLException {
        
        Iphone iphone = new Iphone();

        try (PreparedStatement statement = connection.prepareStatement(READ_BY_KEY_QUERY)) {
            statement.setString(1, modelName);
            statement.setString(2, secMem);
            statement.setString(3, color);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    iphone.setImageLink(result.getString("image_link"));
                    //set remaining attributes...
                } else {
                    throw new SQLException("read error: iphone not found.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgStoreDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

        return iphone;

    }
    
    /*
    SELECT code, MIN(price) as cheapestPrice
    FROM ball JOIN ballVersions
      ON code = ballCode
    WHERE length(color) > 3
    GROUP BY code
    HAVING MIN(price) > 50.0 AND MIN(price) < 100.00
    ORDER BY cheapestPrice ASC
    OFFSET (1-1)*2
    LIMIT 2
    */
//    "SELECT ip.model_name, ip.color, ip.sec_mem, MIN(ipv.cash_payment) AS cheapest_price " +
//    "FROM pis.Iphones AS ip JOIN IphoneVersions AS ipv " +
//    "ON ip.model_name = ipv.model_name AND ip.color = ipv.color AND ip.sec_mem = ipv.sec_mem "
    
//    "WHERE LOWER(title) LIKE '%?%";

        
    public List<Iphone> allAlongWithCheapestVersion(String query, Double minPrice, Double maxPrice, String color, String secMem, String orderBy, int page) throws SQLException {
        List<Iphone> iphones = new ArrayList<Iphone>();
        
        try (PreparedStatement statement = connection.prepareStatement(ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY)){ 
            
            int i = 1;
            
            //dynamically building the query to meet the filtering requeriments...
            //query filtering...
            String[] queryParts = query.split(" ");
            
            for(int j = 0; j < queryParts.length; j++){
                if(j == 0)
                    this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY.concat(" LOWER(ip.title) LIKE '%?%'");
                else
                    this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY.concat(" AND LOWER(ip.title) LIKE '%?%'");
                
                statement.setString(i, queryParts[j]);
                i++;
            }

            //color filtering...
            if(color != null){
                if(color.equals("outro")){
                    this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY.concat(" AND color NOT IN ('vermelho', 'azul', 'amarelo', 'branco', 'cinza', 'preto')");
                }else{
                    this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY.concat(" AND color = ?");
                    statement.setString(i, color);
                    i++;
                }
            }
            
            //secondary memory filtering...
            if(secMem != null){
                this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY.concat(" AND sec_mem = ?");
                statement.setString(i, secMem);
                i++;
            }
            
            
            //price filtering...
            this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY.concat(" GROUP BY ip.model_name, ip.color, ip.sec_mem HAVING MIN(cash_payment) >= ? AND MIN(cash_payment) <= ?");
            statement.setDouble(i, minPrice);
            i++;
            statement.setDouble(i, maxPrice);
            i++;
            
            //sorting...
            if(orderBy != null){
                if(orderBy.equals("asc"))
                    this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY.concat(" ORDER BY cheapest_price ASC");

                else if (orderBy.equals("desc"))
                    this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY.concat(" ORDER BY cheapest_price DESC");
            }
            
            //paging...
            this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY.concat(" OFFSET (?-1)*10 LIMIT 10");
            statement.setInt(i, page);
            
            
            //finishing the outer SELECT...
            this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY = this.ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY.concat(" ) AS ipipv ON ip.model_name = ipipv.model_name AND ip.color = ipipv.color AND ip.sec_mem = ipipv.sec_mem;");

            
            //saving results...
            try(ResultSet result = statement.executeQuery(ALL_ALONG_WITH_CHEAPEST_VERSION_QUERY)) {
                while (result.next()) {
                    Iphone iphone = new Iphone();
                    
                    iphone.setModelName(result.getString("ip.model_name"));
                    iphone.setColor(result.getString("ip.color"));
                    iphone.setSecondaryMemory(result.getString("ip.sec_mem"));
                    iphone.setTitle(result.getString("ip.title"));
                    iphone.setImageLink(result.getString("ip.image_link"));
                    
                    IphoneVersion version = new IphoneVersion();
                    version.setCashPayment(result.getDouble("cheapest_price"));
                    List<IphoneVersion> versions = new ArrayList<IphoneVersion>();
                    versions.add(version);
                    iphone.setVersions(versions);
                    
                    iphones.add(iphone);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneVersionDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }   
        
        return iphones;
    }
    
    
    public List<Iphone> allByFilters(String query, String color, String secMem) throws SQLException {
        List<Iphone> iphones = new ArrayList<Iphone>();
        
        try (PreparedStatement statement = connection.prepareStatement(ALL_BY_FILTERS_QUERY)){ 
            statement.setString(1, query);
            
            int i = 2;

            if(color != null){
                if(color.equals("outro")){
                    this.ALL_BY_FILTERS_QUERY = this.ALL_BY_FILTERS_QUERY.concat(" AND color NOT IN ('vermelho', 'azul', 'amarelo', 'branco', 'cinza', 'preto')");
                }else{
                    this.ALL_BY_FILTERS_QUERY = this.ALL_BY_FILTERS_QUERY.concat(" AND color = ?");
                    statement.setString(i, color);
                    i++;
                }
            }
            
            if(secMem != null){
                this.ALL_BY_FILTERS_QUERY = this.ALL_BY_FILTERS_QUERY.concat(" AND sec_mem = ?");
                statement.setString(i, secMem);
                i++;
            }
            
            this.ALL_BY_FILTERS_QUERY = this.ALL_BY_FILTERS_QUERY.concat(";");
            
            try(ResultSet result = statement.executeQuery(ALL_BY_FILTERS_QUERY)) {
                while (result.next()) {
                    Iphone iphone = new Iphone();
                    
                    iphone.setModelName(result.getString("model_name"));
                    iphone.setColor(result.getString("color"));
                    iphone.setSecondaryMemory(result.getString("sec_mem"));
                    iphone.setTitle(result.getString("title"));
                    iphone.setImageLink(result.getString("image_link"));
                    
                    iphones.add(iphone);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneVersionDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }   
        
        return iphones;
    }

    @Override
    public Object read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Object t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
