/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Category;
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
public class CatMovieDAO
{

    private final ConnectionDAO CB;

    /**
     * The constructor of CatMovieDAO, gets the connection.
     */
    public CatMovieDAO()
    {
        CB = new ConnectionDAO();
    }
    
    /**
     * Get movies from categories table.
     * @return categoryMovies
     * @throws MTDalException 
     */
    public List<Movie> getMoviesFromCats() throws MTDalException
    {
        List<Movie> categoryMovies = new ArrayList<>();
        try (Connection con = CB.getConnection())
        {
            String sql = "SELECT * FROM CategoryMovie INNER JOIN Movie ON MovieId = Movie.id";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double rating = rs.getDouble("rating");
                double imdbRating = rs.getDouble("imdbrating");
                String filepath = rs.getString("filepath");
                int lastview = rs.getInt("lastview");
                int categoryId = rs.getInt("CategoryId");
                Movie movie = new Movie(id, name, rating, imdbRating, filepath, lastview);
                movie.getList().add(categoryId);
                categoryMovies.add(movie);
            }
            return categoryMovies;
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not get movies from the selected categories.", ex);
        }
        
    }

    /**
     * Adds one or more categories to a movie.
     * @param catlist
     * @param movie
     * @throws MTDalException 
     */
    public void addCategoryToMovie(List<Category> catlist, Movie movie) throws MTDalException
    {
        try (Connection con = CB.getConnection())
        {
            String sql = "INSERT INTO CategoryMovie (CategoryId, MovieId) VALUES (?,?)";
            PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (Category cate : catlist)
            {
                pst.setInt(1, cate.getId());
                pst.setInt(2, movie.getId());
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not add categories to the movie.", ex);
        }
    }

    /**
     * Deletes a movie in CategoryMovie Table if deleted in Movie Table.
     * @param movie
     * @throws MTDalException 
     */
    public void deleteMovieFromTable(Movie movie) throws MTDalException
    {
        try (Connection con = CB.getConnection())
        {
            String sql = "DELETE FROM CategoryMovie WHERE MovieId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, movie.getId());
            pst.execute();
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not delete movies from the CategoryMovie table.", ex);
        }
    }

    /**
     * Deletes a category from CategoryMovie Table if deleted in the 
     * Category table.
     * @param category
     * @throws MTDalException 
     */
    public void deleteCategoryFromTable(Category category) throws MTDalException
    {
        try (Connection con = CB.getConnection())
        {
            String sql = "DELETE FROM CategoryMovie WHERE CategoryId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, category.getId());
            pst.execute();
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not delete categories from the CategoryMovie table.", ex);
        }
    }
}
