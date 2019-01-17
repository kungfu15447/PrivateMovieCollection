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

    /**
     * returns the created movie with the designated parameters
     *
     * @param name the title of the movie
     * @param rating the personal rating of the movie
     * @param filepath the filepath of the movie
     * @param lastview the last time the movie was viewed
     * @param imdbrating the IMDB rating of the movie
     * @return the movie created
     * @throws MTBllException
     */
    public Movie createMovie(String name, double rating, String filepath, int lastview, double imdbrating) throws MTBllException
    {
        try
        {
            return MDAO.createMovie(name, rating, filepath, lastview, imdbrating);
        } catch (MTDalException ex)
        {
            throw new MTBllException("" + ex.getMessage());
        }
    }

    /**
     * Gets all movies from the database
     *
     * @return a list containing all movies
     * @throws MTBllException
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

    /**
     * Deletes the selected movie
     *
     * @param movie the movie getting deleted
     * @throws MTBllException
     */
    public void deleteMovie(Movie movie) throws MTBllException
    {
        try
        {
            MDAO.deleteMovie(movie);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete movie. " + ex.getMessage());
        }
    }

    /**
     * Updates the rating for the selected movie
     *
     * @param movie
     * @throws MTBllException
     */
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

    /**
     * gets all categories
     *
     * @return a list containing all categories
     * @throws MTBllException
     */
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

    /**
     * returns the created category with the chosen name
     *
     * @param name title of the category
     * @return the category getting created
     * @throws MTBllException
     */
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

    /**
     * deletes the selected category
     *
     * @param category
     * @throws MTBllException
     */
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

    /**
     * deletes the selected category from the CategoryMovie table
     *
     * @param category
     * @throws MTBllException
     */
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

    /**
     * updates the last time the movie was viewed
     *
     * @param movie
     * @throws MTDalException
     */
    public void updateLastView(Movie movie) throws MTDalException
    {
        MDAO.updateLastView(movie);
    }

    /**
     * gets all movies from the selected category
     *
     * @return cmdao.getMoviesFromCats()
     * @throws MTBllException
     */
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

    /**
     * adds the selected category to the chosen movie
     *
     * @param catlist
     * @param movie
     * @throws MTBllException
     */
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

    /**
     * deletes the selected movie from the table
     *
     * @param movie
     * @throws MTBllException
     */
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

    /**
     * returns the IMDB movies by comparing their title to a search word
     *
     * @param searchWord the word used to get the specific movie titles
     * @return a list containing the IMDB movies 
     */
    public List<IMDBMovie> getIMDBMovieTitles(String searchWord)
    {
        return IMDAO.getIMDBMovieTitles(searchWord);
    }

    /**
     * Gets the IMDB rating from a movie
     *
     * @param movieId the id of the movie
     * @return the IMDB rating from that movie
     */
    public double getIMDBMovieRating(String movieId)
    {
        return IMDAO.getIMDBMovieRating(movieId);
    }

    /**
     * downloads two zip files from the IMDB database and unzips them on the
     * computer
     *
     * @throws MTBllException
     */
    public void downloadIMDBDatabase() throws MTBllException
    {
        try
        {
            IMDAO.downloadIMDBDatabase();
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not get files from the IMDB website");
        }
    }

}
