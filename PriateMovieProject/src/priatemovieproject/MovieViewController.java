/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package priatemovieproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaView;

/**
 *
 * @author bonde
 */
public class MovieViewController implements Initializable
{
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider durationSlider;
    @FXML
    private Slider volumeSlider;
    
    private void handleButtonAction(ActionEvent event)
    {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void play(ActionEvent event)
    {
    }

    @FXML
    private void chooseFiles(ActionEvent event)
    {
    }

    @FXML
    private void pause(ActionEvent event)
    {
    }

    @FXML
    private void exit(ActionEvent event)
    {
    }

    @FXML
    private void stop(ActionEvent event)
    {
    }

    @FXML
    private void setDuration(MouseEvent event)
    {
    }

    @FXML
    private void addMovie(ActionEvent event)
    {
    }
    
}
