/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Category;
import BE.IMDBMovie;
import BE.Movie;
import BLL.Exception.MTBllException;
import BLL.MovieFilter;
import BLL.MovieManager;
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
    private final ObservableList<IMDBMovie> imdbMovieList;
    private final MovieManager moma;
    private final MovieSorter moso;
    private final MovieFilter mofi;
    private String filePath;

    public MovieModel() throws MTBllException
    {
        moma = new MovieManager();
        moso = new MovieSorter();
        mofi = new MovieFilter();
        movieList = FXCollections.observableArrayList();
        categoryList = FXCollections.observableArrayList();
        movieCheck = FXCollections.observableArrayList();
        checkedCategoryList = FXCollections.observableArrayList();
        imdbMovieList = FXCollections.observableArrayList();
        movieList.addAll(moma.getAllMovies());
        categoryList.addAll(moma.getAllCategories());
    }

    /**
     * Creates movie and adds it to the movieList. returns Movie.
     *
     * @param title
     * @param rating
     * @param filepath
     * @param lastView
     * @param imdbrating
     * @return
     * @throws BLL.Exception.MTBllException
     */
    public Movie createMovie(String title, double rating, String filepath, int lastView, double imdbrating) throws MTBllException
    {
        Movie movie = moma.createMovie(title, rating, filepath, lastView, imdbrating);
        movieList.add(movie);
        return movie;
    }

    /**
     * initializes the Filechooser
     */
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

    /**
     * Deletes a movie
     *
     * @param movie
     * @throws MTBllException
     */
    public void deleteMovie(Movie movie) throws MTBllException
    {
        moma.deleteMovie(movie);
        moma.deleteMovieFromTable(movie);
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

    /**
     * returns the category list
     *
     * @return the categoryList
     */
    public ObservableList<Category> getCategories()
    {
        return categoryList;
    }

    /**
     * Creates a category
     *
     * @param title
     * @throws BLL.Exception.MTBllException
     */
    public void createCategory(String title) throws MTBllException
    {
        Category cate = moma.createCategory(title);
        categoryList.add(cate);
    }

    /**
     * Deletes a category
     *
     * @param category
     * @throws MTBllException
     */
    public void deleteCategory(Category category) throws MTBllException
    {

        categoryList.remove(category);
        moma.deleteCategory(category);
        moma.deleteCategoryFromTable(category);

    }

    /**
     * updates the movie rating
     *
     * @param movie
     * @param index
     * @throws MTBllException
     */
    public void updateRating(Movie movie, int index) throws MTBllException
    {
        moma.updateRating(movie);
        movieList.set(index, movie);
    }

    /**
     * Updates the lastview date
     *
     * @param movie
     * @throws MTDalException
     */
    public void updateLastView(Movie movie) throws MTDalException
    {
        Date date = new Date();
        long miliTime = date.getTime();
        int days = (int) (miliTime / (60 * 60 * 24 * 1000));
        movie.setLastview(days);
        moma.updateLastView(movie);
    }

    /**
     * Compares the lastview date from the database and the current local time.
     * Checks if the difference between the dates are greater or lower than 2
     * years.
     */
    public void checkMovies()
    {
        Date Date = new Date();
        long MiliTime = Date.getTime();
        int days = (int) (MiliTime / (60 * 60 * 24 * 1000));
        int twoYearsInDays = 730;
        for (Movie movie : movieList)
        {
            int days2 = movie.getLastview();
            int inBetween = (days - days2);
            if (inBetween > twoYearsInDays)
            {
                movieCheck.add(movie);
            }
        }
    }

    /**
     * returns the Observable list "movieCheck".
     *
     * @return movieCheck
     */
    public ObservableList<Movie> getCheckMovie()
    {
        return movieCheck;
    }

    /**
     * Adds all movies from movieList to searchBase and then clears the
     * movieList. Then we add all searchfiltered movies to the movieList.
     *
     * @param query
     * @throws BLL.Exception.MTBllException
     */
    public void searchMovies(String query) throws MTBllException
    {
        ObservableList<Movie> searchBase = FXCollections.observableArrayList();
        searchBase.addAll(movieList);
        movieList.clear();
        movieList.addAll(mofi.searchFilter(searchBase, checkedCategoryList, query));
    }

    /**
     * Clears the checkedCategoryList and adds the new checked categories.
     */
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

    /**
     * gets the movies from the checked categories.
     *
     * @throws MTBllException
     */
    private void getMoviesFromCats() throws MTBllException
    {
        movieList.clear();
        List<Movie> moviesFromCatsList = mofi.categoriFilter(checkedCategoryList);
        for (Movie movie : moviesFromCatsList)
        {
            movieList.add(movie);
        }
    }

    /**
     * if there are no checked categories, then clears the movieList and then
     * adds all movies again. if there are checked categories, then gets movies
     * from the categories.
     *
     * @throws MTBllException
     */
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

    /**
     * Sorts the movies into lists using a switch statement.
     *
     * @param sortingchoice
     * @throws MTBllException
     */
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

    /**
     * returns a boolean to whether a title exists or not.
     *
     * @param movieTitle
     * @return
     * @throws BLL.Exception.MTBllException
     */
    public boolean checkMovieTitles(String movieTitle) throws MTBllException
    {
        List<Movie> allmovies = moma.getAllMovies();
        boolean existingTitle = false;
        for (Movie movie : allmovies)
        {
            if (movieTitle.toLowerCase().equals(movie.getName().toLowerCase()))
            {
                existingTitle = true;
            }
        }
        return existingTitle;
    }

    /**
     * clears the imdb movie list and adds adds all movies with the correct
     * search word
     *
     * @param searchWord
     * @return imdbMovieList
     */
    public ObservableList<IMDBMovie> getIMDBMovieTitles(String searchWord)
    {
        imdbMovieList.clear();
        imdbMovieList.addAll(moma.getIMDBMovieTitles(searchWord));
        return imdbMovieList;
    }

    /**
     * reutns the imdb movie rating based on the movieId
     *
     * @param movieId
     * @return moma.getIMDBMovieRating(movieId)
     */
    public double getIMDBMovieRating(String movieId)
    {
        return moma.getIMDBMovieRating(movieId);
    }

    /**
     * downloads the IMDB movie database
     *
     * @throws MTBllException
     */
    public void downloadIMDBDatabase() throws MTBllException
    {
        moma.downloadIMDBDatabase();
    }

    public void deleteCheckMoviesFromList() throws MTBllException
    {
        for (Movie movie : movieCheck)
        {
            moma.deleteMovieFromTable(movie);
            moma.deleteMovie(movie);
            movieList.remove(movie);
        }
    }
}
