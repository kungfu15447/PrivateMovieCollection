/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Movie;
import BLL.Exception.MTBllException;
import DAL.Exception.MTDalException;
import DAL.MovieDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frederik Jensen
 */
public class MovieSearcher
{
    private final MovieManager movieManager;
    public MovieSearcher() {
        movieManager = new MovieManager();
    }
    
    public List<Movie> searchMovies(List<Movie> searchBase, String query) throws MTBllException {
        try
        {
            MovieDAO modao = new MovieDAO();
            List<Movie> searchList = new ArrayList<>();
            List<Movie> movieList = modao.getAllMovies();
            if (query.isEmpty())
            {
                searchList = modao.getAllMovies();
            } else
            {
                for (Movie movie : movieList)
                {
                    if (movie.getName().toLowerCase().contains(query))
                    {
                        searchList.add(movie);
                    }
                }
            }

            return searchList;
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not connect to the DAL layer");
        }
    }
}
