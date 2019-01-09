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
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author bonde
 */
public class MovieViewController implements Initializable
{
    private MediaPlayer mediaPlayer;
    private String filePath;
    private final MovieModel movieModel;
    
    @FXML
    private Label label;
    private MediaView mediaView;
    private Slider durationSlider;
    private Slider volumeSlider;
    @FXML
    private TableView<Movie> tableView;
    @FXML
    private TableColumn<Movie, String> clmTitle;
    @FXML
    private TableColumn<Movie, Double> clmMyRating;
    @FXML
    private TableColumn<Movie, Double> clmImdbRating;
    @FXML
    private ListView<Category> lstCategories;
    @FXML
    private Button btnEditRating;
    @FXML
    private Button btnAddCate;
    @FXML
    private Button btnDeleteCate;
    
    
    public MovieViewController() throws MTBllException
    {
        movieModel = new MovieModel();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        clmTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmMyRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tableView.setItems(movieModel.getMovies());
        lstCategories.setItems(movieModel.getCategories());
    }    

    /*
    Plays the chosen movie.
    */
    private void play(ActionEvent event)
    {

        filePath = tableView.getSelectionModel().getSelectedItem().getFilepath();
            Media media = new Media (filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();

        
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                durationSlider.setValue(newValue.toSeconds());
                durationSlider.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));
            }
        });
        mediaPlayer.play();
    }
    
    /*
    Temporary movie chooser.
    */
    @FXML
    private void chooseFiles(ActionEvent event) throws IOException
    {
        Movie movie = tableView.getSelectionModel().getSelectedItem();
        String filePath = movie.getFilepath();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MoviePlayerView.fxml"));
        Parent root = (Parent) loader.load();
        
        MoviePlayerViewController mpvcontroller = loader.getController();
        mpvcontroller.initializeModel(movieModel);
        mpvcontroller.getFilePath(filePath);
        
        Stage stage = new Stage();
        stage.setTitle("Movie player");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /*
    Pauses the movie, pressing play will continue the movie.
    */
    private void pause(ActionEvent event)
    {
        mediaPlayer.pause();
    }

    /*
    Exits the program.
    */
    @FXML
    private void exit(ActionEvent event)
    {
        mediaPlayer.stop();
    }

    /*
    Stops and resets the movie.
    */
    private void stop(ActionEvent event)
    {
        mediaPlayer.stop();
    }

    /*
    By clicking the slider, you can change where the movie plays from.
    */
    private void setDuration(MouseEvent event)
    {
        mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));
    }

    /*
    Adds a movie
    */
    @FXML
    private void addMovie(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddMovieView.fxml"));
        Parent root = (Parent) loader.load();
        
        AddMovieViewController amvcontroller = loader.getController();
        amvcontroller.initializeModel(movieModel);
        
        Stage stage = new Stage();
        stage.setTitle("Movie collection");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void deleteMovie(ActionEvent event)
    {
        try
        {
            Movie movie = tableView.getSelectionModel().getSelectedItem();
            movieModel.deleteMovie(movie);
        } catch (MTBllException ex)
        {
            
        }
    }

    @FXML
    private void createCategory(ActionEvent event)
    {
    }

    @FXML
    private void deleteCategory(ActionEvent event)
    {
    }
    
    
     
    
    
}
