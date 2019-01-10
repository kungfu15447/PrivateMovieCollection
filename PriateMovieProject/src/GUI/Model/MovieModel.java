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
import DAL.Exception.MTDalException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

/**
 *
 * @author bonde
 */
public class MovieModel
{
    private final ObservableList<Movie> movieList;
    private final ObservableList<Category> categoryList;
    private ObservableList<Category> selectedCategoryList;
    private final MovieManager moma;
    private String filePath;
    
    public MovieModel() throws MTBllException
    {
        moma = new MovieManager();
        movieList = FXCollections.observableArrayList();
        categoryList = FXCollections.observableArrayList();
        selectedCategoryList = FXCollections.observableArrayList();
        movieList.addAll(moma.getAllMovies());
        categoryList.addAll(moma.getAllCategories());
    }
    
    public Movie createMovie (String title, double rating, String filepath, int lastView) throws MTBllException
    {
        Movie movie = moma.createMovie(title, rating, filepath, lastView);
        movieList.add(movie);
        return movie;
    }
    
    public void initializeFile()
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null)
        {
            filePath = file.toURI().toString();
        }
    }
    
    
    public void deleteMovie(Movie movie) throws MTBllException
    {
        try
        {
            moma.deleteMovie(movie);
            movieList.remove(movie);
        } catch (MTBllException ex)
        {
            throw new MTBllException("Could not delete movie");
        }
    }
    
    /**
     * Returns the list containing all movies from the database
     * @return the ObservableList containing all movies
     */
    public ObservableList<Movie> getMovies()
    {
        return movieList;
    }
    
    /*
    * returns the filepath for the movie.
    */
    public String getFilePath()
    {
        return filePath;
    }
    
    public ObservableList<Category> getCategories() {
        return categoryList;
    }
    
    public void createCategory(String title) throws MTBllException {
        try
        {
            Category cate = moma.createCategory(title);
            categoryList.add(cate);
        } catch (MTBllException ex)
        {
            throw new MTBllException("Could not create category");
        }
    }
    
    public void deleteCategory(Category category) throws MTBllException {
        try
        {
            moma.deleteCategory(category);
            categoryList.remove(category);
        } catch (MTBllException ex)
        {
            throw new MTBllException("Could not delete category");
        }
    }
    
    public void deleteCategoryFromTable(Category category) throws MTBllException {
        try
        {
            moma.deleteCategoryFromTable(category);
        } catch (MTBllException ex)
        {
            throw new MTBllException("Could not delete category from CategoryMovie table");
        }
    }

    public void setCheckedCategory(ObservableList<Category> catlist) {
        this.selectedCategoryList = catlist;
    }
    private List<Category> getCheckedCategory() {
        List<Category> checkedCategories = new ArrayList<>();
        for (Category cat : selectedCategoryList) {
            if (cat.getSelect().isSelected()) {
                checkedCategories.add(cat);
            }
        }
        return checkedCategories;
    }
    
    public void addCategoryToMovie(Movie movie) throws MTBllException {
        try
        {
            moma.addCategoryToMovie(getCheckedCategory(), movie);
        } catch (MTBllException ex)
        {
            throw new MTBllException("Could not add categories to movie");
        }
    }

    public void updateLastView(Movie movie) throws MTDalException
    {
        moma.updateLastView(movie);
    }

    
}
