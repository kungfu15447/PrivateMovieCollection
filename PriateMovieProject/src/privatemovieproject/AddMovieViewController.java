/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemovieproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

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
    private TextField txtDuration;
    @FXML
    private TextField txtFilepath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void chooseFile(ActionEvent event)
    {
    }

    @FXML
    private void saveMovie(ActionEvent event)
    {
    }

    @FXML
    private void cancelMovie(ActionEvent event)
    {
    }
    
}
