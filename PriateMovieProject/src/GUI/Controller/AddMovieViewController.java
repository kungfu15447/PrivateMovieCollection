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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bonde
 */
public class AddMovieViewController implements Initializable
{
    
    private final MovieManager moma;

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtFilepath;
    @FXML
    private AnchorPane rootPane;

    
    private MovieManager mm;
    private String trueTrueFilePath;
    
    public AddMovieViewController()
    {
        moma = new MovieManager();
    }
    

    @FXML
    private TextField txtRating;

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
        txtFilepath.setText(trueTrueFilePath);
    }

    @FXML
    private void saveMovie(ActionEvent event)
    {
        String title;
        String category;
        double rating;
        String filepath;
        try
        {
            title = txtTitle.getText();
            rating = Double.parseDouble(txtRating.getText());
            filepath = txtFilepath.getText();
            
            mm.createMovie(title, 0, filepath, 0);
        } catch (MTBllException ex)
        {
            displayError(ex);
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
        
        Stage stage = new Stage();
        stage.setTitle("Movie collection");
        stage.setScene(new Scene(root));
        stage.show();
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
    
    public Movie createMovie(String name, double rating, String filepath, int lastview) throws MTBllException
    {
        return moma.createMovie(name, rating, filepath, lastview);
    }
    
    public void updateRating(Movie movie) throws MTBllException
    {
        moma.updateRating(movie);
    }
    
    public Category createCategory(String name) throws SQLException
    {
        return moma.createCategory(name);
    }
    
    public void deleteCategory(Category category) throws SQLException
    {
        moma.deleteCategory(category);
    }
}
