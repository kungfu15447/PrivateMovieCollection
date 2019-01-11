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
import BLL.MovieSearcher;
import BLL.MovieSorter;
import DAL.Exception.MTDalException;
import java.io.File;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

/**
 *
 * @author bonde
 */
public class MovieModel
{

    private final ObservableList<Movie> movieCheck;
    private final ObservableList<Movie> movieList;
    private final ObservableList<Category> categoryList;
    private final ObservableList<Category> checkedCategoryList;
    private final MovieManager moma;
    private final MovieSearcher mose;
    private final MovieSorter moso;
    private String filePath;

    public MovieModel() throws MTBllException
    {
        moma = new MovieManager();
        mose = new MovieSearcher();
        moso = new MovieSorter();
        movieList = FXCollections.observableArrayList();
        categoryList = FXCollections.observableArrayList();
        movieCheck = FXCollections.observableArrayList();
        checkedCategoryList = FXCollections.observableArrayList();
        movieList.addAll(moma.getAllMovies());
        categoryList.addAll(moma.getAllCategories());
    }

    public Movie createMovie(String title, double rating, String filepath, int lastView) throws MTBllException
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
        moma.deleteMovie(movie);
        movieList.remove(movie);
    }

    /**
     * Returns the list containing all movies from the database
     *
     * @return the ObservableList containing all movies
     */
    public ObservableList<Movie> getMovies()
    {
        return movieList;
    }

    /**
     * Returns the filepath of the movie
     *
     * @return the movies filepath
     */
    public String getFilePath()
    {
        return filePath;
    }

    public ObservableList<Category> getCategories()
    {
        return categoryList;
    }

    public void createCategory(String title) throws MTBllException
    {
        Category cate = moma.createCategory(title);
        categoryList.add(cate);
    }

    public void deleteCategory(Category category) throws MTBllException
    {

        categoryList.remove(category);
        moma.deleteCategory(category);

    }

    public void updateLastView(Movie movie) throws MTDalException
    {
        Date date = new Date();
        long miliTime = date.getTime();
        int days = (int) (miliTime / (60 * 60 * 24 * 1000));
        movie.setLastview(days);
        moma.updateLastView(movie);
    }

    public void checkMovies()
    {
        Date Date = new Date();
        long MiliTime = Date.getTime();
        int days = (int) (MiliTime / (60 * 60 * 24 * 1000));
        for (Movie movie : movieList)
        {
            int days2 = movie.getLastview();
            int inBetween = (days - days2);
            if (inBetween > 0)
            {
                movieCheck.add(movie);
            }
        }
        
    }
    
    public ObservableList<Movie> getCheckMovie()
    {
        return movieCheck;
    }

    public void searchMovies(String query) throws MTBllException
    {
        ObservableList<Movie> searchBase = FXCollections.observableArrayList();
        searchBase.addAll(movieList);
        movieList.clear();
        movieList.addAll(mose.searchMovies(searchBase, checkedCategoryList, query));
    }

    public void fillCheckedCategoryList()
    {
        checkedCategoryList.clear();
        for (Category cate : categoryList)
        {
            if (cate.getSelect().isSelected())
            {
                checkedCategoryList.add(cate);
            }
        }
    }

    private void getMoviesFromCats() throws MTBllException
    {
        movieList.clear();
        List<Movie> moviesFromCatsList = moma.getMoviesFromCats(checkedCategoryList);
        for (Movie movie : moviesFromCatsList)
        {
            movieList.add(movie);
        }
    }

    public void contextOfMovieList() throws MTBllException
    {
        if (checkedCategoryList.isEmpty())
        {
            movieList.clear();
            movieList.addAll(moma.getAllMovies());
        } else
        {
            getMoviesFromCats();
        }
    }

    public void sortMovieList(String sortingchoice) throws MTBllException
    {
        switch (sortingchoice)
        {
            case "movietitle":
                moso.sortMovieListTitle(movieList);
                break;
            case "movierating":
                moso.sortMovieListRating(movieList);
                break;
            case "id":
                moso.sortMovieListId(movieList);
                break;
        }
    }

}
