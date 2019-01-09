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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frederik Jensen
 */
public class CatMovieDAO
{

    private final ConnectionDAO cb;

    public CatMovieDAO()
    {
        cb = new ConnectionDAO();
    }

    public List<Movie> getMoviesFromCats(List<Category> catlist) throws MTDalException
    {
        List<Movie> categoryMovies = new ArrayList<>();
        try (Connection con = cb.getConnection())
        {
            String sql = "SELECT * FROM CategoryMovie INNER JOIN Movie ON MovieId = Movie.id WHERE ";
            for (int i = 0; i < catlist.size(); i++)
            {
                if (i == catlist.size() - 1)
                {
                    sql = sql + "CategoryMovie.CategoryId = " + catlist.get(i).getId() + ";";
                } else
                {
                    sql = sql + "CategoryMovie.CategoryId = " + catlist.get(i).getId() + " AND ";
                }
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double rating = rs.getDouble("rating");
                String filepath = rs.getString("filePath");
                int lastview = rs.getInt("lastview");
                Movie movie = new Movie(id, name, rating, filepath, lastview);
                categoryMovies.add(movie);
            }
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not get movies from the selected categories.", ex);
        }
        return categoryMovies;
    }

    public void addCategoryToMovie(List<Category> catlist, Movie movie) throws MTDalException
    {
        try (Connection con = cb.getConnection())
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

    public void deleteMovieFromTable(Movie movie) throws MTDalException
    {
        try (Connection con = cb.getConnection())
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
    
    public void deleteCategoryFromTable(Category category) throws MTDalException {
        try (Connection con = cb.getConnection()) {
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
