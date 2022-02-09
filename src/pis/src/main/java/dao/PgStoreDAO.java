/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author vitor
 */
public class PgStoreDAO implements DAO {
//    private static final String CREATE_QUERY =
//                                "INSERT INTO j2ee.user(login, senha, nome, nascimento, avatar) " +
//                                "VALUES(?, md5(?), ?, ?, ?);";
//    
//    private static final String READ_QUERY =
//                                "SELECT login, nome, nascimento, avatar " +
//                                "FROM j2ee.user " +
//                                "WHERE id = ?;";      
//    
//    private static final String UPDATE_QUERY =
//                                "UPDATE j2ee.user " +
//                                "SET login = ?, nome = ?, nascimento = ? " +
//                                "WHERE id = ?;";
//    
//    private static final String DELETE_QUERY =
//                                "DELETE FROM j2ee.user " +
//                                "WHERE id = ?;";
//    
//    private static final String ALL_QUERY =
//                                "SELECT id, login " +
//                                "FROM j2ee.user " +
//                                "ORDER BY id;";
    
    public PgStoreDAO(Connection connection) {
    }

    @Override
    public void create(Object t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
