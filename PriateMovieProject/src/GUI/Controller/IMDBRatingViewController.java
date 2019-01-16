/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.IMDBMovie;
import BLL.Exception.MTBllException;
import GUI.Model.MovieModel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Frederik
 */
public class IMDBRatingViewController implements Initializable {

    @FXML
    private ListView<IMDBMovie> lstImdbMovies;
    private String movieTitle;
    private double movieRating;
    MovieModel momo;
    IMDBMovie movie;
    @FXML
    private AnchorPane rootPane;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    /**
     * selects the selected item, gets the rating and closes the window
     * @param event 
     */
    @FXML
    private void saveRatingHandler(ActionEvent event) {
        lstImdbMovies.getSelectionModel().getSelectedItem();
        getRating();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    /**
     * sets movie to null, gets rating and closes teh window
     * @param event 
     */
    @FXML
    private void cancelHandler(ActionEvent event) {
        movie = null;
        getRating();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    /**
     * initializes the model
     * @param model 
     */
    public void initializeModel(MovieModel model) {
        this.momo = model;
    }
    
    /**
     * initializes the title
     * @param title 
     */
    public void initializeTitle(String title) {
        this.movieTitle = title;
    }

    /**
     * gets the movie titles from imdb
     * @param event 
     */
    @FXML
    private void handlerGetMovies(ActionEvent event) {
        lstImdbMovies.setItems(momo.getIMDBMovieTitles(movieTitle));
    }

    /**
     * downloads the imdb movie database
     * @param event 
     */
    @FXML
    private void handlerDownloadIMDBDatabase(ActionEvent event) {
        try {
            momo.downloadIMDBDatabase();
        } catch (MTBllException ex) {
            Logger.getLogger(IMDBRatingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Downloads the movie data from the imdb database
     * sets the return output to -, if there is no movie title for the movie.
     * @return movieRating
     */
    public double getRating() {
        if (movie == null) {
            movieRating = -1;
        }else {
            movieRating = momo.getIMDBMovieRating(movie.getId());
        }
        return movieRating;
    }

    /**
     * Selects the selected movie from the user mouse input
     * @param event 
     */
    @FXML
    private void handlerClickedMovie(MouseEvent event) {
        movie = lstImdbMovies.getSelectionModel().getSelectedItem();
    }
    
}
