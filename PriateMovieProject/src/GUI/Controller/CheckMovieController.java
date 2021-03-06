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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    /**
     * The constructor of the CheckMovieController
     */
    public CheckMovieController() {
        try
        {
            movieModel = new MovieModel();
        } catch (MTBllException ex)
        {
            displayError(ex);
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

    /**
     * Sets the instance variable deleteMovies equals true and then closes the
     * view
     * @param event 
     */
    @FXML
    private void handlerYesButton(ActionEvent event)
    {
        deleteMovies = true;
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets the instance variable deleteMovies equals false and then closes the
     * view
     * @param event 
     */
    @FXML
    private void handlerNoButton(ActionEvent event)
    {
        deleteMovies = false;
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    /**
     * returns the instance variable deleteMovies 
     * @return deleteMovies 
     */
    public boolean deleteMovies() {
        return deleteMovies;
    }
    
    /**
     * displays an error message
     * @param ex the exception getting displayed
     */
    private void displayError(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }
}
