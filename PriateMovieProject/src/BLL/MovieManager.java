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
import DAL.CategoryDAO;
import DAL.Exception.MTDalException;
import DAL.MovieDAO;
import java.util.List;

/**
 *
 * @author Schweizeren
 */
public class MovieManager
{
    private final MovieDAO mdao;
    private final CategoryDAO cdao;
    private final CatMovieDAO cmdao;

    public MovieManager()
    {
        mdao = new MovieDAO();
        cdao = new CategoryDAO();
        cmdao = new CatMovieDAO();
    }
    
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
    
    /*
    Gets all movies.
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
    
    public void deleteCategoryFromTable(Category category) throws MTBllException {
        try
        {
            cmdao.deleteCategoryFromTable(category);
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not delete category from the CategoryMovie table");
        }
    }
    
}
