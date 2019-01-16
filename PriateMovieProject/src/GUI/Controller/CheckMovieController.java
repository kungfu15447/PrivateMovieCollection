/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Schweizeren
 */
public class CheckMovieController implements Initializable
{
    private MovieModel movieModel;
    @FXML
    private TableView<Movie> tableCheck;
    @FXML
    private TableColumn<Movie, String> tableTitle;
    @FXML
    private TableColumn<Movie, Double> tableRating;
    @FXML
    private Button btnok;
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    }
    /**
     * calls the checkMovies method from movieModel
     */
    public void checkMovies(ActionEvent event)
    {
        movieModel.checkMovies();
    }
    
    /**
     * closes the window
     */
    public void btnok(ActionEvent btnok)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
}
