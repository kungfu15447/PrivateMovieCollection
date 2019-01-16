/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Category;
import BE.IMDBMovie;
import BE.Movie;
import BLL.Exception.MTBllException;
import DAL.CatMovieDAO;
import DAL.CategoryDAO;
import DAL.Exception.MTDalException;
import DAL.ImdbDAO;
import DAL.MovieDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Schweizeren
 */
public class MovieManager
{

    private final MovieDAO MDAO;
    private final CategoryDAO CDAO;
    private final CatMovieDAO CMDAO;
    private final ImdbDAO IMDAO;

    public MovieManager()
    {
        MDAO = new MovieDAO();
        CDAO = new CategoryDAO();
        CMDAO = new CatMovieDAO();
        IMDAO = new ImdbDAO();
    }

    public Movie createMovie(String name, double rating, String filepath, int lastview) throws MTBllException
    {
        try
        {
            return MDAO.createMovie(name, rating, filepath, lastview);
        } catch (MTDalException ex)
        {
            throw new MTBllException("" + ex.getMessage());
        }
    }

    /*
    Gets all movies.
     */
    public List<Movie> getAllMovies() throws MTBllException
    {
        try
        {
            return MDAO.getAllMovies();
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not read all songs. " + ex.getMessage());
        }
    }

    public void deleteMovie(Movie movie) throws MTBllException
    {
        try
        {
            MDAO.deleteMovie(movie);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete song. " + ex.getMessage());
        }
    }

    public void updateRating(Movie movie) throws MTBllException
    {
        try
        {
            MDAO.updateRating(movie);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not update movie. " + ex.getMessage());
        }
    }

    public List<Category> getAllCategories() throws MTBllException
    {
        try
        {
            return CDAO.getAllCategories();
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not get a ll categories");
        }
    }

    public Category createCategory(String name) throws MTBllException
    {
        try
        {
            return CDAO.createCategory(name);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not create category");
        }
    }

    public void deleteCategory(Category category) throws MTBllException
    {
        try
        {
            CDAO.deleteCategory(category);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete category");
        }
    }

    public void deleteCategoryFromTable(Category category) throws MTBllException
    {
        try
        {
            CMDAO.deleteCategoryFromTable(category);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete category from the CategoryMovie table");
        }
    }

    public void updateLastView(Movie movie) throws MTDalException
    {
        MDAO.updateLastView(movie);
    }

    public List<Movie> getMoviesFromCats() throws MTBllException
    {
        try
        {
            return CMDAO.getMoviesFromCats();
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not get movies from selected categories");
        }
    }

    public void addCategoryToMovie(List<Category> catlist, Movie movie) throws MTBllException
    {
        try
        {
            CMDAO.addCategoryToMovie(catlist, movie);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not add categories to movie");
        }
    }

    public void deleteMovieFromTable(Movie movie) throws MTBllException
    {
        try
        {
            CMDAO.deleteMovieFromTable(movie);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete movie from CategoryMovie table");
        }
    }
    
    public List<IMDBMovie> getIMDBMovieTitles(String searchWord) {
        return IMDAO.getIMDBMovieTitles(searchWord);
    }
    
    public double getIMDBMovieRating(String movieId) {
        return IMDAO.getIMDBMovieRating(movieId);
    }
    
    public void downloadIMDBDatabase() throws MTBllException {
        try {
            IMDAO.downloadIMDBDatabase();
        } catch (MTDalException ex) {
            throw new MTBllException("Could not get files from the IMDB website");
        }
    }
    
}