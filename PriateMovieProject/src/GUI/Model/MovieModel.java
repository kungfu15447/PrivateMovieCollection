/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Movie;
import BLL.Exception.MTBllException;
import BLL.MovieManager;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

/**
 *
 * @author bonde
 */
public class MovieModel
{
    private ObservableList<Movie> movieList;
    private MovieManager mm;
    private String trueTrueFilePath;
    
    public MovieModel() throws MTBllException
    {
        mm = new MovieManager();
        movieList = FXCollections.observableArrayList();
        movieList.addAll(mm.getAllMovies());
    }
    
    public void createMovie (String title, double rating, String filepath, int lastView) throws MTBllException
    {
        Movie movie = mm.createMovie(title, rating, filepath, lastView);
        movieList.add(movie);
    }
    
    public void initializeFile()
    {
        String filePath;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null)
        {
            filePath = file.toURI().toString();
            String trueFilePath = filePath.replaceFirst("file:/", "");
            trueTrueFilePath = trueFilePath.replace("%20", " ");
        }
    }
}
