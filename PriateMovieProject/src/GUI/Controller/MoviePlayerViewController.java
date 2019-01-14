/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import GUI.Model.MovieModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author bonde
 */
public class MoviePlayerViewController implements Initializable
{

    private MediaPlayer mediaPlayer;
    private String filePath;
    private MovieModel movieModel;
    private boolean playing = false;
    private boolean paused = false;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider durationSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lblVolume;
    @FXML
    private Label lblTimer;
    @FXML
    private ImageView playButton;
    @FXML
    private ImageView pauseButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       
    }
    



    @FXML
    private void exit(ActionEvent event)
    {
        if (playing == true)
        {
            mediaPlayer.stop();
        }
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent we)
            {
                System.out.println("Stage is closing");
            }
        });
    }

    public void windowsExit()
    {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent close)
            {

            }
        });
    }

    @FXML
    private void setDuration(MouseEvent event)
    {
        mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));
    }

    /*
    Sets the volume of the video to 100, but also allows changes to the volume by clicking it.
     */
    private void controlSound()
    {
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        volumeSlider.valueProperty().addListener((Observable observable) ->
        {
            mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            lblVolume.setText(Math.round(volumeSlider.getValue()) + "");
        });
        lblVolume.setText(Math.round(volumeSlider.getValue()) + "");
    }

    /**
     * Initializes this class' moviemodel object
     *
     * @param movieModel the movieModel this class' movieModel is getting
     * initialized with
     */
    public void initializeModel(MovieModel movieModel)
    {
        this.movieModel = movieModel;
    }

    public void getFilePath(String filepath)
    {
        this.filePath = filepath;
    }

    private void timer()
    {
        int seconds = (int) durationSlider.getValue() % 60;
        int minutes = (int) (durationSlider.getValue() / 60) % 60;
        int hours = (int) (durationSlider.getValue() / 3600);

        lblTimer.setText(hours + ":" + minutes + ":" + seconds + "");
    }

    public void stopMovie()
    {
        mediaPlayer.stop();
    }

    @FXML
    private void handlePlayButton(MouseEvent event) 
    {
        playButton.setVisible(true);
        pauseButton.setVisible(false);
        if (!paused && !playing)
        {
            
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>()
            {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
                {
                    durationSlider.setValue(newValue.toSeconds());
                    durationSlider.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));

                    timer();
                }

            });
            
            mediaPlayer.play();
            playing = true;
            paused = false;
            pauseButton.setVisible(true);
            playButton.setVisible(false);
            
        } else if (paused)
        {
            mediaPlayer.play();
            paused = false;
            playing = true;
            pauseButton.setVisible(true);
            playButton.setVisible(false);
        } 
        controlSound();
    }

    @FXML
    private void handlePauseButton(MouseEvent event) 
    {
            if (playing)
            {
            mediaPlayer.pause();
            paused = true;
            playing = false;
            pauseButton.setVisible(false);
            playButton.setVisible(true);
            }
    }

}
