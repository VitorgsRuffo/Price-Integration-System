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
import model.Rating;

/**
 *
 * @author wellinton
 */
public class PgRatingDAO implements DAO {
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO rating(iphone_model_name, iphone_sec_mem, iphone_color, store_id, title, description, rater_name, rating, likes, deslikes, date) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    
    private static final String ALL_BY_KEY_QUERY =
                                "SELECT title, description, rater_name, date, rating, likes, deslikes" +
                                "FROM rating " +
                                "WHERE iphone_model_name = ? AND iphone_sec_mem = ? AND iphone_color = ? AND store_id = ? " +
                                "ORDER BY date DESC";

    public PgRatingDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Object t) throws SQLException {
         Rating rating = (Rating) t;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, rating.getModelName());
            statement.setString(2, rating.getSecondaryMemory());
            statement.setInt(3, rating.getStoreId());
            statement.setString(4, rating.getTitle());
            statement.setString(5, rating.getDescription());
            statement.setString(6, rating.getRaterName());
            statement.setDouble(7, rating.getRating());
            statement.setInt(8, rating.getLikes());
            statement.setInt(9, rating.getDeslikes());
            statement.setDate(10, rating.getDate());

            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PgRatingDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
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
    
    //"WHERE iphone_model_name = ? AND iphone_sec_mem = ? AND iphone_color = ? AND store_id = ? AND title = ? AND description = ?" +

    public List<Rating> allByKey(String iphoneModel, String secondaryMemory, String color, int storeId) throws SQLException {
        List<Rating> ratingList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_BY_KEY_QUERY)){
            statement.setString(1, iphoneModel);
            statement.setString(2, secondaryMemory);
            statement.setString(3, color);
            statement.setInt(4, storeId);

            try(ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Rating rating = new Rating();
                    rating.setTitle(result.getString("title"));
                    rating.setDescription(result.getString("description"));
                    rating.setRaterName(result.getString("rater_name"));
                    rating.setDate(result.getDate("date"));
                    rating.setRating(result.getDouble("rating"));
                    rating.setLikes(result.getInt("likes"));
                    rating.setDeslikes(result.getInt("deslikes"));
                    ratingList.add(rating);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgRatingDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw ex;
        }

        return ratingList;
    }   
}