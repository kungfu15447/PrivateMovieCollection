/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Category;
import BE.Movie;
import BLL.Exception.MTBllException;
import DAL.CatMovieDAO;
import DAL.Exception.MTDalException;
import DAL.MovieDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Frederik Jensen
 */
public class MovieSearcher
{ 
    public List<Movie> searchMovies(List<Movie> searchBase, List<Category> categoryList ,String query) throws MTBllException {
        try
        {
            MovieDAO modao = new MovieDAO();
            CatMovieDAO catdao = new CatMovieDAO();
            List<Movie> searchList = new ArrayList<>();
            
            if (query.isEmpty())
            {
                if (categoryList.isEmpty()) {
                    searchList = modao.getAllMovies();
                }else {
                    searchList = catdao.getMoviesFromCats();
                }
                
            } else
            {
                for (Movie movie : searchBase)
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
