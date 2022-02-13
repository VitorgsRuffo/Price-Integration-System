/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
                                "INSERT INTO iphone(model_name, sec_mem, color, title, iphone_link, image_link, model_code, display_size, front_cam, back_cam, ram_mem, voltagem) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

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
