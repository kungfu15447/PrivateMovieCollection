/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Movie;
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
    private final ConnectionDAO cb;

    public MovieDAO()
    {
        cb = new ConnectionDAO();
    }
    
    public List<Movie> getAllMovies() throws SQLException
    {
        List<Movie> movies = new ArrayList<>();
        
        try (Connection con = cb.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Movie;");
            while(rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                int year = rs.getInt("year");
                String filePath = rs.getString("filePath");
                Movie movie = new Movie(id, title, duration, year, filePath);
                movies.add(movie);
            }
        }
        return movies;
    }
    
    public Movie createMovie(String title, int duration, int year, String filePath) throws SQLException
    {
        String sql = "INSERT INTO Movie (title, duration, year, filePath) VALUE(?,?,?,?);";
        
        try (Connection con = cb.getConnection())
        {
            PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, title);
            st.setInt(2, duration);
            st.setInt(3, year);
            st.setString(4, filePath);
            
            int rowsAffected = st.executeUpdate();
            
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if(rs.next())
            {
                id = rs.getInt(1);
            }
            Movie movie = new Movie(id, title, duration, year, filePath);
            return movie;
        } 
    }
    
    
    public void deleteMovie(Movie movie) throws SQLException
    {
        try (Connection con = cb.getConnection())
        {
            Statement statement = con.createStatement();
            String sql = "DELETE FROM Movie WHERE id = " + movie.getId() + ";";
            statement.executeUpdate(sql);
        }
    }
}
