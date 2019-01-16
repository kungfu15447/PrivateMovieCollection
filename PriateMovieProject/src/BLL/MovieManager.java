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

    private final MovieDAO mdao;
    private final CategoryDAO cdao;
    private final CatMovieDAO cmdao;
    private final ImdbDAO imdao;

    public MovieManager()
    {
        mdao = new MovieDAO();
        cdao = new CategoryDAO();
        cmdao = new CatMovieDAO();
        imdao = new ImdbDAO();
    }

    /**
     * returns the created movie with the designated parameters
     * @param name
     * @param rating
     * @param filepath
     * @param lastview
     * @return mdao.createMovie(name, rating, filepath, lastview)
     * @throws MTBllException 
     */
    public Movie createMovie(String name, double rating, String filepath, int lastview) throws MTBllException
    {
        try
        {
            return mdao.createMovie(name, rating, filepath, lastview);
        } catch (MTDalException ex)
        {
            throw new MTBllException("" + ex.getMessage());
        }
    }

    /**
     * Gets all movies
     * @return mdao.getAllMovies()
     * @throws MTBllException 
     */
    public List<Movie> getAllMovies() throws MTBllException
    {
        try
        {
            return mdao.getAllMovies();
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not read all songs. " + ex.getMessage());
        }
    }

    /**
     * Deletes the selected movie
     * @param movie
     * @throws MTBllException 
     */
    public void deleteMovie(Movie movie) throws MTBllException
    {
        try
        {
            mdao.deleteMovie(movie);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete song. " + ex.getMessage());
        }
    }

    /**
     * Updates the rating for the selected movie
     * @param movie
     * @throws MTBllException 
     */
    public void updateRating(Movie movie) throws MTBllException
    {
        try
        {
            mdao.updateRating(movie);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not update movie. " + ex.getMessage());
        }
    }

    /**
     * gets all categories
     * @return cdao.getAllCategories
     * @throws MTBllException 
     */
    public List<Category> getAllCategories() throws MTBllException
    {
        try
        {
            return cdao.getAllCategories();
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not get a ll categories");
        }
    }
    
    /**
     * returns the created category with the chosen name
     * @param name
     * @returncdao.createCategory(name)
     * @throws MTBllException 
     */
    public Category createCategory(String name) throws MTBllException
    {
        try
        {
            return cdao.createCategory(name);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not create category");
        }
    }

    /**
     * deletes the selected category
     * @param category
     * @throws MTBllException 
     */
    public void deleteCategory(Category category) throws MTBllException
    {
        try
        {
            cdao.deleteCategory(category);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete category");
        }
    }

    /**
     * deletes the selected category from from the table
     * @param category
     * @throws MTBllException 
     */
    public void deleteCategoryFromTable(Category category) throws MTBllException
    {
        try
        {
            cmdao.deleteCategoryFromTable(category);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete category from the CategoryMovie table");
        }
    }

    /**
     * updates the last time the movie was viewed
     * @param movie
     * @throws MTDalException 
     */
    public void updateLastView(Movie movie) throws MTDalException
    {
        mdao.updateLastView(movie);
    }

    /**
     * gets all movies from the selected category
     * @return cmdao.getMoviesFromCats()
     * @throws MTBllException 
     */
    public List<Movie> getMoviesFromCats() throws MTBllException
    {
        try
        {
            return cmdao.getMoviesFromCats();
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not get movies from selected categories");
        }
    }

    /**
     * adds the selected category to the chosen movie
     * @param catlist
     * @param movie
     * @throws MTBllException 
     */
    public void addCategoryToMovie(List<Category> catlist, Movie movie) throws MTBllException
    {
        try
        {
            cmdao.addCategoryToMovie(catlist, movie);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not add categories to movie");
        }
    }

    /**
     * deletes the selected movie from the table
     * @param movie
     * @throws MTBllException 
     */
    public void deleteMovieFromTable(Movie movie) throws MTBllException
    {
        try
        {
            cmdao.deleteMovieFromTable(movie);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete movie from CategoryMovie table");
        }
    }
    
    /**
     * returns the IMDB movie titles
     * @param searchWord
     * @return return imdao.getIMDBMovieTitles(searchWord)
     */
    public List<IMDBMovie> getIMDBMovieTitles(String searchWord) {
        return imdao.getIMDBMovieTitles(searchWord);
    }
    
    /**
     * retirns the IMDB movie rating
     * @param movieId
     * @return return imdao.getIMDBMovieRating(movieId)
     */
    public double getIMDBMovieRating(String movieId) {
        return imdao.getIMDBMovieRating(movieId);
    }
    
    /**
     * downloads the IMDB database
     * @throws MTBllException 
     */
    public void downloadIMDBDatabase() throws MTBllException {
        try {
            imdao.downloadIMDBDatabase();
        } catch (MTDalException ex) {
            throw new MTBllException("Could not get files from the IMDB website");
        }
    }
    
}