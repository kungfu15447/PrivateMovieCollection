/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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
        }
        return movies;
    }

    public Movie createMovie(String name, double rating, String filepath, int lastview) throws SQLException
    {
        

        try (Connection con = cb.getConnection())
        {
            String sql = "INSERT INTO Movie (name, rating, filepath, lastview) VALUE(?,?,?,?);";
            
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

    public void updateRating(Movie movie) throws SQLException
    {
        try (Connection con = cb.getConnection())
        {
            String sql = "UPDATE Movie SET rating = ? WHERE id = ?;";
            
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setDouble(1, movie.getRating());
            st.setInt(2, movie.getId());
            
            st.executeUpdate();
        }
    }
}
