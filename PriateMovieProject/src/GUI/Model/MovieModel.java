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

    private final ObservableList<Movie> MOVIECHECK;
    private final ObservableList<Movie> MOVIELIST;
    private final ObservableList<Category> CATEGORYLIST;
    private final ObservableList<Category> CHECKEDCATEGORYLIST;
    private final ObservableList<IMDBMovie> IMDBMOVIELIST;
    private final MovieManager MOMA;
    private final MovieSorter MOSO;
    private final MovieFilter MOFI;
    private String filePath;

    /**
     * The constructor of the MovieModel class
     * @throws MTBllException 
     */
    public MovieModel() throws MTBllException
    {
        MOMA = new MovieManager();
        MOSO = new MovieSorter();
        MOFI = new MovieFilter();
        MOVIELIST = FXCollections.observableArrayList();
        CATEGORYLIST = FXCollections.observableArrayList();
        MOVIECHECK = FXCollections.observableArrayList();
        CHECKEDCATEGORYLIST = FXCollections.observableArrayList();
        IMDBMOVIELIST = FXCollections.observableArrayList();
        MOVIELIST.addAll(MOMA.getAllMovies());
        CATEGORYLIST.addAll(MOMA.getAllCategories());
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
        Movie movie = MOMA.createMovie(title, rating, filepath, lastView, imdbrating);
        MOVIELIST.add(movie);
        return movie;
    }

    /**
     * initializes a file by open a filechooser. only mp4 files can be chosen.
     * When a file is selected the filechooser then returns that files filepath
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
        MOMA.deleteMovie(movie);
        MOMA.deleteMovieFromTable(movie);
        MOVIELIST.remove(movie);
    }

    /**
     * Returns the list containing all movies from the database
     *
     * @return the ObservableList containing all movies
     */
    public ObservableList<Movie> getMovies()
    {
        return MOVIELIST;
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
        return CATEGORYLIST;
    }

    /**
     * Creates a category
     *
     * @param title
     * @throws BLL.Exception.MTBllException
     */
    public void createCategory(String title) throws MTBllException
    {
        Category cate = MOMA.createCategory(title);
        CATEGORYLIST.add(cate);
    }

    /**
     * Deletes a category
     *
     * @param category
     * @throws MTBllException
     */
    public void deleteCategory(Category category) throws MTBllException
    {

        CATEGORYLIST.remove(category);
        MOMA.deleteCategory(category);
        MOMA.deleteCategoryFromTable(category);

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
        MOMA.updateRating(movie);
        MOVIELIST.set(index, movie);
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
        MOMA.updateLastView(movie);
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
        for (Movie movie : MOVIELIST)
        {
            int days2 = movie.getLastview();
            int inBetween = (days - days2);
            if (inBetween > twoYearsInDays)
            {
                MOVIECHECK.add(movie);
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
        return MOVIECHECK;
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
        searchBase.addAll(MOVIELIST);
        MOVIELIST.clear();
        MOVIELIST.addAll(MOFI.searchFilter(searchBase, CHECKEDCATEGORYLIST, query));
    }

    /**
     * Clears the checkedCategoryList and adds the new selected categories.
     */
    public void fillCheckedCategoryList()
    {
        CHECKEDCATEGORYLIST.clear();
        for (Category cate : CATEGORYLIST)
        {
            if (cate.getSelect().isSelected())
            {
                CHECKEDCATEGORYLIST.add(cate);
            }
        }
    }

    /**
     * gets the movies that has the same categories as the categories been
     * selected.
     *
     * @throws MTBllException
     */
    private void getMoviesFromCats() throws MTBllException
    {
        MOVIELIST.clear();
        List<Movie> moviesFromCatsList = MOFI.categoriFilter(CHECKEDCATEGORYLIST);
        for (Movie movie : moviesFromCatsList)
        {
            MOVIELIST.add(movie);
        }
    }

    /**
     * if there are no selected categories it returns all movies. 
     * If there are selected categories it then gets the movies which has the
     * same categories as the categories selected
     * the movies 
     *
     * @throws MTBllException
     */
    public void contextOfMovieList() throws MTBllException
    {
        if (CHECKEDCATEGORYLIST.isEmpty())
        {
            MOVIELIST.clear();
            MOVIELIST.addAll(MOMA.getAllMovies());
        } else
        {
            getMoviesFromCats();
        }
    }

    /**
     * Sorts the movie list different ways using a switch statement
     *
     * @param sortingchoice the choice on how the list is sorted
     * @throws MTBllException
     */
    public void sortMovieList(String sortingchoice) throws MTBllException
    {
        switch (sortingchoice)
        {
            case "movietitle":
                MOSO.sortMovieListTitle(MOVIELIST);
                break;
            case "movierating":
                MOSO.sortMovieListRating(MOVIELIST);
                break;
            case "id":
                MOSO.sortMovieListId(MOVIELIST);
                break;
        }
    }

    /**
     * Checks if a title to a movie already exist in the database.
     *
     * @param movieTitle the title getting checked
     * @return true or false based on if the title already exist or not
     * @throws BLL.Exception.MTBllException
     */
    public boolean checkMovieTitles(String movieTitle) throws MTBllException
    {
        List<Movie> allmovies = MOMA.getAllMovies();
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
     * Adds IMDB movies to a list based on if their title contains the search
     * word
     *
     * @param searchWord the search word thats compared to all the IMDB movie
     * titles
     * @return imdbMovieList
     */
    public ObservableList<IMDBMovie> getIMDBMovieTitles(String searchWord)
    {
        IMDBMOVIELIST.clear();
        IMDBMOVIELIST.addAll(MOMA.getIMDBMovieTitles(searchWord));
        return IMDBMOVIELIST;
    }

    /**
     * returns the imdb movie rating based on the movieId
     *
     * @param movieId
     * @return the rating of the IMDB movie
     */
    public double getIMDBMovieRating(String movieId)
    {
        return MOMA.getIMDBMovieRating(movieId);
    }

    /**
     * downloads two zip files from the IMDB website and then unzips them on
     * the computer
     *
     * @throws MTBllException
     */
    public void downloadIMDBDatabase() throws MTBllException
    {
        MOMA.downloadIMDBDatabase();
    }

    /**
     * Deletes movies which havent been seen in 2 years or more
     * 
     * @throws MTBllException 
     */
    public void deleteCheckMoviesFromList() throws MTBllException
    {
        for (Movie movie : MOVIECHECK) 
        {
            MOMA.deleteMovieFromTable(movie);
            MOMA.deleteMovie(movie);
            MOVIELIST.remove(movie);
        }
    }
}
