/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Iphone;

/**
 *
 * @author wellinton
 */
public class PgIPhoneDAO implements DAO {
    
    private final Connection connection;
    
    private static final String CREATE_QUERY =
                                "INSERT INTO pis.iphone(model_name, sec_mem, color, title, iphone_link, image_link, model_cod, display_size, front_cam, back_cam, ram_mem, voltage, main_source) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    
    private static final String MASTER_UPDATE_QUERY = 
                                "UPDATE pis.iphone SET voltage = ?, iphone_link = ?, image_link = ?, display_size = ?, front_cam = ?, back_cam = ?, ram_mem = ?, title = ?, main_source = ? " +
                                "WHERE model_name = ? AND sec_mem = ? AND color = ?;";
    
    private static final String NOT_MASTER_UPDATE_QUERY = 
                                "UPDATE pis.iphone SET model_cod = ? WHERE model_name = ? AND sec_mem = ? AND color = ?;";
    
    private static final String MAIN_SOURCE_QUERY = 
                                "SELECT main_source FROM pis.iphone WHERE model_name = ? AND sec_mem = ? AND color = ?;";

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
