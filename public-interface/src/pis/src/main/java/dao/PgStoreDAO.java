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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vitor
 */
public class PgStoreDAO implements DAO {
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO pis.stores(name, logo_path, address, phone) " +
                                "VALUES(?, ?, ?, ?);";
    
    private static final String UPDATE_QUERY =
                                "UPDATE pis.stores " +
                                "SET name = ?, logo_path = ?, address = ?, phone = ? " +
                                "WHERE id = ?;";
    
    private static final String READ_QUERY =
                                "SELECT name, logo_path, address, phone " +
                                "FROM pis.stores " +
                                "WHERE id = ?;";  

    
    private static final String DELETE_QUERY =
                                "DELETE FROM pis.stores " +
                                "WHERE id = ?;";
    
    private static final String ALL_QUERY =
                                "SELECT id, name " +
                                "FROM pis.stores " +
                                "ORDER BY id ASC;";    
        
    public PgStoreDAO(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void create(Object t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int createReturningId(Object t) throws SQLException {
        Store store = (Store) t;
        int storeId;
        
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, store.getName());
            statement.setString(2, store.getLogoPath());
            statement.setString(3, store.getAddress());
            statement.setString(4, store.getPhone());

            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if(keys.next()){
                    storeId = keys.getInt("id");

                }else{
                    throw new SQLException("create (returning id) error: store could not be created.");
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PgStoreDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }
        
        return storeId;
    }

    @Override
    public Object read(Integer id) throws SQLException {
        Store store = new Store();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    store.setId(id);
                    store.setName(result.getString("name"));
                    store.setAddress(result.getString("address"));
                    store.setPhone(result.getString("phone"));
                    store.setLogoPath(result.getString("logo_path"));
                } else {
                    throw new SQLException("read error: store not found.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgStoreDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

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
                store.setName(result.getString("name"));
                stores.add(store);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgStoreDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

        return stores;
    }
}