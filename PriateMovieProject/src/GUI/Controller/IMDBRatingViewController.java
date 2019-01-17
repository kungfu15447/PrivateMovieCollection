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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
     * selects the selected movie and gets its rating and then closes the window
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
     * sets instance movie object to null and closes the window
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
     * @param title the movie title
     */
    public void initializeTitle(String title) {
        this.movieTitle = title;
    }

    /**
     * Gets the imdb Movies based on their movie title and inserts them into
     * the listview
     * @param event 
     */
    @FXML
    private void handlerGetMovies(ActionEvent event) {
        lstImdbMovies.setItems(momo.getIMDBMovieTitles(movieTitle));
    }

    /**
     * downloads two zip files from the IMDB website and then unzips them on
     * the computer
     * @param event 
     */
    @FXML
    private void handlerDownloadIMDBDatabase(ActionEvent event) {
        try {
            momo.downloadIMDBDatabase();
        } catch (MTBllException ex) {
            displayError(ex);
        }
    }
    
    /**
     * Checks whether the instance movie object is null. If it is it sets the
     * instance variable movieRating to -1. Else it sets the variable to the
     * selected movies rating. It then returns movieRating
     * @return the movies rating getting returned
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
     * Sets the instance movie object to the selected movie in the listview
     * @param event 
     */
    @FXML
    private void handlerClickedMovie(MouseEvent event) {
        movie = lstImdbMovies.getSelectionModel().getSelectedItem();
    }
    
    /**
     * displays the exception that was caught
     * @param ex the exception getting showed
     */
    private void displayError(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }
    
}
