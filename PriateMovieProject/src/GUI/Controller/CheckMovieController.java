/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Movie;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private AnchorPane rootPane;
    private boolean deleteMovies;

    public CheckMovieController() {
        try
        {
            movieModel = new MovieModel();
        } catch (MTBllException ex)
        {
            Logger.getLogger(CheckMovieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tableTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableRating.setCellValueFactory(new PropertyValueFactory<>("stringRating"));
        movieModel.checkMovies();
        tableCheck.setItems(movieModel.getCheckMovie());
    }

    @FXML
    private void handlerYesButton(ActionEvent event)
    {
        deleteMovies = true;
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handlerNoButton(ActionEvent event)
    {
        deleteMovies = false;
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    public boolean deleteMovies() {
        return deleteMovies;
    }
    
}
