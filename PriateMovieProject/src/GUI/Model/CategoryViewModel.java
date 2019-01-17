/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Category;
import BE.Movie;
import BLL.Exception.MTBllException;
import BLL.MovieManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Frederik Jensen
 */
public class CategoryViewModel
{

    MovieManager moma;
    ObservableList<Category> categoryList;

    /**
     * Constructor of the CategoryViewModel class.
     */
    public CategoryViewModel()
    {
        try
        {
            moma = new MovieManager();
            categoryList = FXCollections.observableArrayList();
            categoryList.addAll(moma.getAllCategories());
        } catch (MTBllException ex)
        {
            Logger.getLogger(CategoryViewModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Adds a category to a movie.
     * @param catlist the list of categories getting added to the movie
     * @param movie the movie getting categories added to it
     * @throws MTBllException 
     */
    public void addCategoryToMovie(List<Category> catlist, Movie movie) throws MTBllException
    {
        moma.addCategoryToMovie(catlist, movie);
    }

    /**
     * Checks the list of categories through and adds the selected categories
     * to a new list. It then returns that list
     * @return the list of selected categories
     */
    public List<Category> getCheckedCategory()
    {
        ArrayList<Category> checkedCategoryList = new ArrayList<>();
        for (Category cate : categoryList)
        {
            if (cate.getSelect().isSelected())
            {
                checkedCategoryList.add(cate);
            }
        }
        return checkedCategoryList;
    }

    /**
     * Returns the categoryList.
     * @return 
     */
    public ObservableList<Category> getCategories()
    {
        return categoryList;
    }

    /**
     * Deletes a category from the CategoryMovie table if it has been deleted
     * in the Category table
     * @param category the category getting deleted
     * @throws MTBllException 
     */
    public void deleteCategoryFromTable(Category category) throws MTBllException
    {
        moma.deleteCategoryFromTable(category);

    }
    
    /**
     * Deletes a movie from the CategoryMovie table
     * @param movie the movie getting deleted
     * @throws MTBllException 
     */
    public void deleteMovieFromTable(Movie movie) throws MTBllException
    {
        moma.deleteMovieFromTable(movie);
    }
    
}
