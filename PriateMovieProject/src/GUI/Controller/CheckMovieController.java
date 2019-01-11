/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
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
    private TableView<Movie> tableTitle;
    @FXML
    private TableView<Movie> tableRating;
    @FXML
    private Button btnok;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    }
    
    public void checkMovies(ActionEvent event)
    {
        movieModel.checkMovies();
    }
    
    public void btnok(ActionEvent btnok)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
}
