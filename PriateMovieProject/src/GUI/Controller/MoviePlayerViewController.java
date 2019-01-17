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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
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
     * Initializes the controller class
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }

    /**
     * Closes the window. If the mediaplayer is playing then the method will
     * stop it from playing
     *
     * @param event
     */
    @FXML
    private void exit(ActionEvent event)
    {
        if (playing == true)
        {
            mediaPlayer.stop();
        }
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Skips to a chosen point in the movie, based on user interaction
     *
     * @param event
     */
    @FXML
    private void setDuration(MouseEvent event)
    {
        mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));
    }

    /**
     * Sets the volume of the video to 100, but also allows changes to the
     * volume by clicking it.
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

    /**
     * Gets the filepath
     *
     * @param filepath
     */
    public void getFilePath(String filepath)
    {
        this.filePath = filepath;
    }

    /**
     * Shows the duration in seconds, minutes and hours, and displays them in a
     * certain way.
     */
    private void timer()
    {
        int seconds = (int) durationSlider.getValue() % 60;
        int minutes = (int) (durationSlider.getValue() / 60) % 60;
        int hours = (int) (durationSlider.getValue() / 3600);

        lblTimer.setText(hours + ":" + minutes + ":" + seconds + "");
    }

    /**
     * Stops the mediaplayer
     */
    public void stopMovie()
    {
        if (playing)
        {
            mediaPlayer.stop();
        }
    }

    /**
     * Sets the playbutton to be visible and the pausebutton to be invisible. If
     * the player is not paused or playing, then the player is open to a
     * filepath. If the movie is playing the pause button is invisible and if
     * the movie is paused, then the play button is invisible.
     *
     * @param event
     */
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

    /**
     * Uses user interaction to pause the movie, if the movie is playing.
     *
     * @param event
     */
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

    /**
     * on mouseclick the movie will play or pause depending on what it is
     * already doing. if you doubleclick the mediaView the stage will be
     * fullscreen.
     *
     * @param doubleClicked
     */
    public void mouseClick(MouseEvent doubleClicked)
    {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        if (paused)
        {
            mediaPlayer.play();
            paused = false;
            playing = true;
            pauseButton.setVisible(true);
            playButton.setVisible(false);
        } else if (playing)
        {
            mediaPlayer.pause();
            paused = true;
            playing = false;
            pauseButton.setVisible(false);
            playButton.setVisible(true);
        }

        if (doubleClicked.getClickCount() == 2)
        {
            stage.setFullScreen(true);
        }
    }

}
