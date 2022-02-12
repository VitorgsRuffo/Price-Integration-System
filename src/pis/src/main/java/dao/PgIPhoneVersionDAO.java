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
                                "INSERT INTO iphoneVersion(iphone_model_name, iphone_sec_mem, store_id, date, cash_payment, installment_payment, rating_amount, rating_average) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
    
    private static final String ALL_BY_KEY_QUERY =
                                "SELECT date, cash_payment, installment_payment, rating_amout, rating_average " +
                                "FROM iphoneVersion " +
                                "WHERE iphone_model_name = ? AND iphone_sec_mem = ? AND store_id = ? " +
                                "ORDER BY date DESC";

    public PgIPhoneVersionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Object t) throws SQLException {
        IphoneVersion iphoneVersion = (IphoneVersion) t;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, iphoneVersion.getModelName());
            statement.setInt(2, iphoneVersion.getSecondaryMemory());
            statement.setInt(3, iphoneVersion.getStoreId());
            statement.setDate(4, iphoneVersion.getDate());
            statement.setString(5, iphoneVersion.getCashPayment());
            statement.setString(6, iphoneVersion.getInstallmentPayment());
            statement.setInt(7, iphoneVersion.getRatingAmount());
            statement.setString(8, iphoneVersion.getRatingAverage());

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
    
    public List<IphoneVersion> allByKey(String iphoneModel, int secondaryMemory, int storeId, int id) throws SQLException {
        List<IphoneVersion> iphoneVersionsExec = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_BY_KEY_QUERY)){
            statement.setString(1, iphoneModel);
            statement.setInt(2, secondaryMemory);
            statement.setInt(3, storeId);
            
            try(ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    IphoneVersion iphoneVersion = new IphoneVersion();
                    iphoneVersion.setDate(result.getDate("date"));
                    iphoneVersion.setCashPayment(result.getString("cash_payment"));
                    iphoneVersion.setInstallmentPayment(result.getString("installment_payment"));
                    iphoneVersion.setRatingAmount(result.getInt("rating_amount"));
                    iphoneVersion.setRatingAverage(result.getString("rating_average"));
                    iphoneVersionsExec.add(iphoneVersion);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgIPhoneVersionDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

        return iphoneVersionsExec;
    }
}
