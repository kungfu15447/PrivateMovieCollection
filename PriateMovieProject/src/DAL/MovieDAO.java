/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Movie;
import DAL.Exception.MTDalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Frederik Jensen
 */
public class MovieDAO
{

    private final ConnectionDAO CB;

    public MovieDAO()
    {
        CB = new ConnectionDAO();
    }

    public List<Movie> getAllMovies() throws MTDalException
    {
        List<Movie> movies = new ArrayList<>();

        try (Connection con = CB.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Movie;");
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double rating = rs.getDouble("rating");
                String filepath = rs.getString("filePath");
                int lastview = rs.getInt("lastview");
                Movie movie = new Movie(id, name, rating, filepath, lastview);
                movies.add(movie);
            }
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not read all movies. " + ex.getMessage());
        }
        return movies;
    }

    public Movie createMovie(String name, double rating, String filepath, int lastview) throws MTDalException
    {
        try (Connection con = CB.getConnection())
        {
            String sql = "INSERT INTO Movie (name, rating, filepath, lastview) VALUES(?,?,?,?);";
            
            PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, name);
            st.setDouble(2, rating);
            st.setString(3, filepath);
            st.setInt(4, lastview);

            int rowsAffected = st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next())
            {
                id = rs.getInt(1);
            }
            Movie movie = new Movie(id, name, rating, filepath, lastview);
            return movie;
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not create movie. " + ex.getMessage());
        }
    }

    public void deleteMovie(Movie movie) throws MTDalException
    {
        try (Connection con = CB.getConnection())
        {
            Statement statement = con.createStatement();
            String sql = "DELETE FROM Movie WHERE id = " + movie.getId() + ";";
            statement.executeUpdate(sql);
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not delete movie. " + ex.getMessage());
        }
    }

    public void updateRating(Movie movie) throws MTDalException
    {
        try (Connection con = CB.getConnection())
        {
            String sql = "UPDATE Movie SET rating = ? WHERE id = ?;";
            
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setDouble(1, movie.getRating());
            st.setInt(2, movie.getId());
            
            st.executeUpdate();
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not update movie. " + ex.getMessage());
        }
    }
    

    public void updateLastView(Movie movie) throws MTDalException
    {
        try (Connection con = CB.getConnection())
        {
            String sql = "UPDATE Movie SET lastview = ? WHERE id = ?;";
            
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setInt(1, movie.getLastview());
            st.setInt(2, movie.getId());
            
            st.executeUpdate();
            
            
        }
        catch (SQLException ex)
        {
            throw new MTDalException("Could not update LastView. " + ex.getMessage());
        }
    }
}
