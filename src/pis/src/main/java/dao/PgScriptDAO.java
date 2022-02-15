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
import model.Script;

/**
 *
 * @author vitor
 */
public class PgScriptDAO implements DAO {
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO pis.script(store_id, date, time, text) " +
                                "VALUES(?, ?, ?, ?);";
    
    private static final String READ_LAST_VERSION_QUERY =
                                "SELECT * " +
                                "FROM pis.script " +
                                "WHERE store_id = ? " +
                                "order by version_num DESC " +
                                "LIMIT 1;";
    
    public PgScriptDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Object t) throws SQLException {
        Script script = (Script) t;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setInt(1, script.getStoreId());
            statement.setDate(2, script.getDate());
            statement.setTime(3, script.getTime());
            statement.setString(4, script.getText());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
    }

    @Override
    public Object read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Script readLastVersion(int storeId) throws SQLException {
         Script script = new Script();

        try (PreparedStatement statement = connection.prepareStatement(READ_LAST_VERSION_QUERY)) {
            statement.setInt(1, storeId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    script.setStoreId(storeId);
                    script.setVersionNum(result.getInt("version_num"));
                    script.setDate(result.getDate("date"));
                    script.setTime(result.getTime("time"));
                    script.setText(result.getString("text"));
                } else {
                    throw new SQLException("read last version error: script not found.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgStoreDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

        return script;
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