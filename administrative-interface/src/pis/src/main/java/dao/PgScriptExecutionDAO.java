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
import model.ScriptExecution;

/**
 *
 * @author vitor
 */
public class PgScriptExecutionDAO implements DAO {
 
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO pis.scriptexecutions(store_id, script_version_num, date, time) " +
                                "VALUES(?, ?, ?, ?);";
    
    private static final String ALL_BY_KEY_QUERY =
                                "SELECT date, time " +
                                "FROM pis.scriptexecutions " +
                                "WHERE store_id = ? AND script_version_num = ? " +
                                "ORDER BY date ASC, time ASC;";   
    
    public PgScriptExecutionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Object t) throws SQLException {
        ScriptExecution scriptExecution = (ScriptExecution) t;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setInt(1, scriptExecution.getStoreId());
            statement.setInt(2, scriptExecution.getScriptVersionNum());
            statement.setDate(3, scriptExecution.getDate());
            statement.setTime(4, scriptExecution.getTime());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgScriptExecutionDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
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
    
    public List<ScriptExecution> allByKey(int storeId, int scriptVersionNum) throws SQLException {
        List<ScriptExecution> scriptExecutions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_BY_KEY_QUERY)){
            statement.setInt(1, storeId);
            statement.setInt(2, scriptVersionNum);
            try(ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    ScriptExecution scriptExecution = new ScriptExecution();
                    scriptExecution.setDate(result.getDate("date"));
                    scriptExecution.setTime(result.getTime("time"));
                    scriptExecutions.add(scriptExecution);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgScriptExecutionDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

        return scriptExecutions;
    }
}