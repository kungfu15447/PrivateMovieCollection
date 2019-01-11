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

    public void addCategoryToMovie(List<Category> catlist, Movie movie) throws MTBllException
    {
        moma.addCategoryToMovie(catlist, movie);
    }

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

    public ObservableList<Category> getCategories()
    {
        return categoryList;
    }

    public void deleteCategoryFromTable(Category category) throws MTBllException
    {
        moma.deleteCategoryFromTable(category);

    }

    public void deleteMovieFromTable(Movie movie) throws MTBllException
    {
        moma.deleteMovieFromTable(movie);
    }
    
}
