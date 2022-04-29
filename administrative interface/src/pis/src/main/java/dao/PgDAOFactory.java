/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;

/**
 *
 * @author vitor
 */
public class PgDAOFactory extends DAOFactory {

    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public DAO getIphoneDAO() {
        return new PgIPhoneDAO(this.connection);
    }

    @Override
    public DAO getIphoneVersionDAO() {
        return new PgIPhoneVersionDAO(this.connection);
    }

    @Override
    public DAO getRatingDAO() {
        return new PgRatingDAO(this.connection);
    }

    @Override
    public DAO getScriptDAO() {
        return new PgScriptDAO(this.connection);
    }

    @Override
    public DAO getScriptExecutionDAO() {
        return new PgScriptExecutionDAO(this.connection);
    }

    @Override
    public DAO getStoreDAO() {
        return new PgStoreDAO(this.connection);
    }
}