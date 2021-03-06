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
import model.IphoneVersion;

/**
 *
 * @author wellinton
 */
public class PgIPhoneVersionDAO implements DAO {
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO pis.IphoneVersions(iphone_model_name, iphone_sec_mem, iphone_color, store_id, date, cash_payment, installment_payment, rating_amount, rating_average) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
    
    private static final String ALL_BY_KEY_QUERY =
                                "SELECT * " +
                                "FROM pis.IphoneVersions " +
                                "WHERE iphone_model_name = ? AND iphone_sec_mem = ? AND iphone_color = ? " +
                                "ORDER BY date ASC;";
    
    private static final String ALL_ORDER_BY_CASH_PAYMENT_QUERY = "SELECT * " + 
                                                                  "FROM pis.IphoneVersions " +
                                                                  "ORDER BY cash_payment ASC;";

    private static final String LAST_VERSION_ON_EACH_STORE_BY_KEY_QUERY = "SELECT v1.*, s.name " +
                                                                          "FROM ( " +
                                                                                  "pis.IphoneVersions AS v1 " +
                                                                                  "JOIN ( " +
                                                                                          "SELECT iphone_model_name, iphone_sec_mem, iphone_color, store_id, MAX(date) AS date " +
                                                                                          "FROM pis.IphoneVersions "+ 
                                                                                          "WHERE iphone_model_name = ? AND iphone_sec_mem = ? AND iphone_color = ? " +
                                                                                          "GROUP BY iphone_model_name, iphone_sec_mem, iphone_color, store_id " +
                                                                                       ") AS v2 " +
                                                                                  "ON v1.iphone_model_name = v2.iphone_model_name AND v1.iphone_sec_mem = v2.iphone_sec_mem AND v1.iphone_color = v2.iphone_color " +
                                                                                  "AND v1.store_id = v2.store_id AND v1.date = v2.date " +
                                                                               " ) " +
                                                                          "JOIN pis.stores AS s " +
                                                                          "ON v1.store_id = s.id " +
                                                                          "ORDER BY v1.cash_payment ASC;";
    
    public PgIPhoneVersionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Object t) throws SQLException {
        IphoneVersion iphoneVersion = (IphoneVersion) t;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, iphoneVersion.getModelName());
            statement.setString(2, iphoneVersion.getSecondaryMemory());
            statement.setString(3, iphoneVersion.getColor());
            statement.setInt(4, iphoneVersion.getStoreId());
            statement.setDate(5, iphoneVersion.getDate());
            statement.setDouble(6, iphoneVersion.getCashPayment());
            statement.setString(7, iphoneVersion.getInstallmentPayment());
            statement.setInt(8, iphoneVersion.getRatingAmount());
            statement.setDouble(9, iphoneVersion.getRatingAverage());

            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneVersionDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
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
    
    public List<IphoneVersion> allOrderByCashPayment() throws SQLException { 
        List<IphoneVersion> iphoneVersions = new ArrayList<>();
        
        try (PreparedStatement statement = connection.prepareStatement(ALL_ORDER_BY_CASH_PAYMENT_QUERY)){
            
            try(ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    IphoneVersion iphoneVersion = new IphoneVersion();
                    iphoneVersion.setStoreId(result.getInt("store_id"));
                    iphoneVersion.setModelName(result.getString("iphone_model_name"));
                    iphoneVersion.setColor(result.getString("iphone_color"));
                    iphoneVersion.setSecondaryMemory(result.getString("iphone_sec_mem"));
                    iphoneVersion.setDate(result.getDate("date"));
                    iphoneVersion.setCashPayment(result.getDouble("cash_payment"));
                    iphoneVersion.setInstallmentPayment(result.getString("installment_payment"));
                    iphoneVersion.setRatingAmount(result.getInt("rating_amount"));
                    iphoneVersion.setRatingAverage(result.getDouble("rating_average"));
                    iphoneVersions.add(iphoneVersion);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneVersionDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

        return iphoneVersions;
    }
    
    public List<IphoneVersion> allByKey(String iphoneModel, String secondaryMemory, String color) throws SQLException {
        List<IphoneVersion> iphoneVersions = new ArrayList<>();
        
        try (PreparedStatement statement = connection.prepareStatement(ALL_BY_KEY_QUERY)){
            statement.setString(1, iphoneModel);
            statement.setString(2, secondaryMemory);
            statement.setString(3, color); 
            
            try(ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    IphoneVersion iphoneVersion = new IphoneVersion();
                    iphoneVersion.setDate(result.getDate("date"));
                    iphoneVersion.setCashPayment(result.getDouble("cash_payment"));
                    iphoneVersion.setInstallmentPayment(result.getString("installment_payment"));
                    iphoneVersion.setRatingAmount(result.getInt("rating_amount"));
                    iphoneVersion.setRatingAverage(result.getDouble("rating_average"));
                    iphoneVersions.add(iphoneVersion);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneVersionDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

        return iphoneVersions;
    }
    
    public List<IphoneVersion> lastVersionOnEachStoreByKey(String modelName, String secMem, String color) throws SQLException {
        List<IphoneVersion> iphoneVersions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(LAST_VERSION_ON_EACH_STORE_BY_KEY_QUERY)){
            statement.setString(1, modelName);
            statement.setString(2, secMem);
            statement.setString(3, color);
            
            try(ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    IphoneVersion iphoneVersion = new IphoneVersion();
                    iphoneVersion.setDate(result.getDate("date"));
                    iphoneVersion.setCashPayment(result.getDouble("cash_payment"));
                    iphoneVersion.setInstallmentPayment(result.getString("installment_payment"));
                    iphoneVersion.setRatingAmount(result.getInt("rating_amount"));
                    iphoneVersion.setRatingAverage(result.getDouble("rating_average"));
                    iphoneVersion.setIphoneLink(result.getString("iphone_link"));
                    iphoneVersion.setStoreName(result.getString("name"));
                    iphoneVersions.add(iphoneVersion);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneVersionDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

        return iphoneVersions;
    }   
}