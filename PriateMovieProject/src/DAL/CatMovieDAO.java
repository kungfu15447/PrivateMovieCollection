/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Category;
import BE.Movie;
import java.sql.Connection;
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
    private final ConnectionDAO cb;
    
    public CatMovieDAO() {
        cb = new ConnectionDAO();
    }
    
    public List<Movie> getMoviesFromCats(List<Category> catlist) throws SQLException {
        List<Movie> categoryMovies = new ArrayList<>();
        try (Connection con = cb.getConnection()) {
            String sql = "SELECT * FROM CategoryMovie INNER JOIN Movie ON MovieId = Movie.id WHERE ";
            for (int i = 0; i < catlist.size(); i++) {
                if (i == catlist.size() - 1) {
                    sql = sql + "CategoryMovie.CategoryId = " + catlist.get(i).getId() + ";";
                } else {
                    sql = sql + "CategoryMovie.CategoryId = " + catlist.get(i).getId() + " AND ";
                }
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double rating = rs.getDouble("rating");
                String filepath = rs.getString("filePath");
                int lastview = rs.getInt("lastview");
                Movie movie = new Movie(id, name, rating, filepath, lastview);
                categoryMovies.add(movie);
            }
        }
        return categoryMovies;
    }
    
    public void addCategoryToMovie(List<Category> catlist, Movie movie) {
        
    }
}
