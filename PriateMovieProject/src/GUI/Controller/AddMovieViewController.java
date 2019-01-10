/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Category;
import BE.Movie;
import BLL.Exception.MTBllException;
import BLL.MovieManager;
import GUI.Model.MovieModel;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bonde
 */
public class AddMovieViewController implements Initializable
{

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtFilepath;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Slider ratingSlider;
    @FXML
    private Label lblRating;

    private MovieModel movieModel;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        txtFilepath.setDisable(true);
    }
    

    @FXML
    private void chooseFile(ActionEvent event)
    {
        movieModel.initializeFile();
        txtFilepath.setText(movieModel.getFilePath());
    }

    @FXML
    private void saveMovie(ActionEvent event)
    {
        boolean emptyField = getEmptyFieldInfo();
        if(!emptyField)
        {
        try
        {
            String title = txtTitle.getText();
            double rating = new BigDecimal(ratingSlider.getValue()).setScale(1, RoundingMode.HALF_UP).doubleValue();
            String filepath = txtFilepath.getText();
            Movie movie = movieModel.createMovie(title, rating, filepath, 0);
            movieModel.addCategoryToMovie(movie);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
            
        } catch (MTBllException ex)
        {
            displayError(ex);
        }
        }
        else{
            getAlertBox();
        }
    }

    @FXML
    private void cancelMovie(ActionEvent event)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleCategoryChooseBtn(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CategoryView.fxml"));
        Parent root = (Parent) loader.load();
        CategoryViewController cwcontroller = loader.getController();
        cwcontroller.initializeModel(movieModel);
        
        Stage stage = new Stage();
        stage.setTitle("Movie collection");
        stage.setScene(new Scene(root));
        stage.show();
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
    
    /**
     * A popup window that displays the error that occured
     * @param ex the exception getting showened to the user
     */
    private void displayError(Exception ex)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());

        alert.showAndWait();
    }

    /**
     *
     * @return
     */
    public String getErrorInfo()
    {
        String errorInfo;
        if(txtTitle.getText().isEmpty())
        {
            errorInfo = "title";
        }
        else if (txtFilepath.getText().isEmpty())
        {
            errorInfo = "filepath";
        }
        else 
        {
            errorInfo = null;
        }
        return errorInfo;
    }
    
    public boolean getEmptyFieldInfo()
    {
        
        boolean emptyField = false;
        if(txtTitle.getText().isEmpty() || txtFilepath.getText().isEmpty())
        {
            emptyField = true;
        }
        return emptyField;
    }
    

    /**
     * Initializes this class' moviemodel object
     * @param movieModel the movieModel this class' movieModel is getting
     * initialized with
     */
    public void initializeModel(MovieModel movieModel)
    {
        this.movieModel = movieModel;
    }
    
    public void getAlertBox()
    {
        String errorInfo = getErrorInfo();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Dialog");
        alert.setHeaderText("You have not chosen a " + errorInfo + " for the movie");
        alert.setContentText("Please select a " + errorInfo + " for the movie");
        
        alert.showAndWait();
    }
}
