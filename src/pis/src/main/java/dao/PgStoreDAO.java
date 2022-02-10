/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import model.Store;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Script;

/**
 *
 * @author vitor
 */
public class PgStoreDAO implements DAO {
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO store(name, logo_path, address, phone) " +
                                "VALUES(?, ?, ?, ?);";
    
    private static final String UPDATE_QUERY =
                                "UPDATE store " +
                                "SET name = ?, logo_path = ?, address = ?, phone = ? " +
                                "WHERE id = ?;";
    
    private static final String READ_WITH_SCRIPT_QUERY = 
                                "SELECT * " +
                                "FROM store st JOIN scripts sc ON (st.id = sc.id) " +
                                "WHERE st.id = ? " +
                                "ORDER BY sc.version_num DESC;";

    
    private static final String DELETE_QUERY =
                                "DELETE FROM store " +
                                "WHERE id = ?;";
    
    private static final String ALL_QUERY =
                                "SELECT id " +
                                "FROM store " +
                                "ORDER BY id;";    
        
    public PgStoreDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Object t) throws SQLException {
        Store store = (Store) t;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, store.getName());
            statement.setString(2, store.getLogoPath());
            statement.setString(3, store.getAddress());
            statement.setString(4, store.getPhone());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgStoreDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
    }

    @Override
    public Object read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Store readStoreWithScriptData(Integer id) throws SQLException{
        Store store = new Store();
        List<Script> scripts = new ArrayList<Script>();

        try (PreparedStatement statement = connection.prepareStatement(READ_WITH_SCRIPT_QUERY)){
            statement.setInt(1, id); 
            try (ResultSet result = statement.executeQuery()) {
                if(result.next()){
                    store.setId(id);
                    store.setName(result.getString("st.name"));
                    store.setName(result.getString("st.address"));
                    store.setName(result.getString("st.phone"));
                    store.setName(result.getString("st.logo_path"));

                    Script script = new Script();
                    script.setVersionNum(result.getInt("sc.version_num"));
                    script.setDate(result.getDate("sc.date"));
                    script.setTime(result.getTime("sc.time"));
                    script.setText(result.getString("sc.text"));
                    scripts.add(script);

                }else{
                    throw new SQLException("read (with script) error: store not found.");
                }

                while (result.next()) {
                    Script script = new Script();
                    script.setVersionNum(result.getInt("sc.version_num"));
                    script.setDate(result.getDate("sc.date"));
                    script.setTime(result.getTime("sc.time"));
                    script.setText(result.getString("sc.text"));
                    scripts.add(script);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PgStoreDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
        
        store.setScripts(scripts);
        return store;
    }

    @Override
    public void update(Object t) throws SQLException {
        Store store = (Store) t;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, store.getName());
            statement.setString(2, store.getLogoPath());
            statement.setString(3, store.getAddress());
            statement.setString(4, store.getPhone());
            statement.setInt(5, store.getId());
            
            if (statement.executeUpdate() < 1) {
                throw new SQLException("update error: store not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgStoreDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("delete error: store not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgStoreDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
    }

    @Override
    public List<Store> all() throws SQLException {
        List<Store> stores = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Store store = new Store();
                store.setId(result.getInt("id"));
                stores.add(store);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgStoreDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

        return stores;
    }
}