/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BLL.Exception.MTBllException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtYear;
    @FXML
    private TextField txtFilepath;
    @FXML
    private AnchorPane rootPane;

    
    private String trueTrueFilePath;
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
        double rating;
        String filepath;
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
    
}
