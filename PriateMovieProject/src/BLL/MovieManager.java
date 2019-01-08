/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Movie;
import DAL.MovieDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Schweizeren
 */
public class MovieManager
{
    private final MovieDAO md;

    public MovieManager()
    {
        md = new MovieDAO();
    }
    
    public List<Movie> getAllMovies() throws SQLException
    {
        return md.getAllMovies();
    }
    
    public Movie createMovie(String name, double rating, String filepath, int lastview) throws SQLException
    {
        return md.createMovie(name, rating, filepath, lastview);
    }
            
    public void deleteMovie(Movie movie) throws SQLException
    {
        md.deleteMovie(movie);
    }
            
    public void updateRating(Movie movie) throws SQLException
    {
        md.updateRating(movie);
    }
    
}
