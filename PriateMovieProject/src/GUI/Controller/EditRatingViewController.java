/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Movie;
import BLL.Exception.MTBllException;
import GUI.Model.MovieModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bonde
 */
public class EditRatingViewController implements Initializable
{
    private MovieModel movieModel;
    private Movie oldMovie;
    private int index;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Slider ratingSlider;
    @FXML
    private Label lblRating;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
    /**
     * Initalizes the selected movie and the index of it in the list
     * @param movie the selected song gettting updated
     * @param index the index of the song in the song list
     */
    public void initializeMovie(Movie movie, int index)
    {
        ratingSlider.setValue(new BigDecimal(ratingSlider.getValue()).setScale(1, RoundingMode.HALF_UP).doubleValue());
        oldMovie = new Movie(movie.getId(), movie.getName(), movie.getRating(), movie.getFilepath(), movie.getLastview());
        this.index = index;
    }
    
    /**
     * Initializes this class' movieModel object
     * @param movieModel the movieModel this class' movieModel is getting
     * initialized with
     */
    public void initializeModel(MovieModel movieModel)
    {
        this.movieModel = movieModel;
    }

    @FXML
    private void saveMovie(ActionEvent event) throws MTBllException
    {
        oldMovie.setRating(new BigDecimal(ratingSlider.getValue()).setScale(1, RoundingMode.HALF_UP).doubleValue());
        movieModel.updateRating(oldMovie, index);
    }

    @FXML
    private void cancelMovie(ActionEvent event)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    /*
    *Dragging the slider will adjust the users rating.
     */
    @FXML
    private void handleRating(MouseEvent event)
    {
        ratingSlider.valueProperty().addListener((Observable observable) ->
        {
            lblRating.setText(new BigDecimal(ratingSlider.getValue()).setScale(1, RoundingMode.HALF_UP).toString());
        });
    }
}
