/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Category;
import BE.Movie;
import java.sql.Connection;
import java.sql.SQLException;
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
        List<Movie> catmovies = new ArrayList<>();
        try (Connection con = cb.getConnection()) {
            String sql = "SELECT * FROM ";
        }
        return catmovies;
    }
}
