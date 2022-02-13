/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author vitor
 */
public class PgConnectionFactory extends ConnectionFactory {

    private String dbHost;
    private String dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;    
    
    public PgConnectionFactory() {
    }

    public void readProperties() throws IOException {
        Properties properties = new Properties();

        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream(propertiesPath);
            properties.load(input);

            dbHost = properties.getProperty("host");
            dbPort = properties.getProperty("port");
            dbName = properties.getProperty("name");
            dbUser = properties.getProperty("user");
            dbPassword = properties.getProperty("password");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());

            throw new IOException("Erro ao obter informações do banco de dados.");
        }
    }    
    
    @Override
    public Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");

            readProperties();

            String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;

            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());

            throw new ClassNotFoundException("Erro de conexão ao banco de dados.");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

            throw new SQLException("Erro de conexão ao banco de dados.");
        }

        return connection;

    }
    
}