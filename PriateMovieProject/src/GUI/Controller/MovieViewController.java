/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Category;
import BE.Movie;
import BLL.Exception.MTBllException;
import DAL.Exception.MTDalException;
import GUI.Model.MovieModel;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
    private MoviePlayerViewController mpvc;
    
    @FXML
    private Label label;
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
    Temporary movie chooser.
     */
    @FXML
    private void chooseFiles(ActionEvent event) throws IOException, MTDalException
    {
        Movie movie = tableView.getSelectionModel().getSelectedItem();
        String filePath = movie.getFilepath();
        
        Date date = new Date();
        long miliTime = date.getTime();
        int days = (int) (miliTime /(60*60*24*1000));
        movie.setLastview(days);
        movieModel.updateLastView(movie);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MoviePlayerView.fxml"));
        Parent root = (Parent) loader.load();

        MoviePlayerViewController mpvcontroller = loader.getController();
        mpvcontroller.initializeModel(movieModel);
        mpvcontroller.getFilePath(filePath);

        Stage stage = new Stage();
        stage.setTitle("Movie player");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            mpvc.stopSound();
        }
});
    }

    /*
    Exits the program.
     */
    @FXML
    private void exit(ActionEvent event)
    {
        System.exit(0);
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
            if(movie != null)
            {
            movieModel.deleteMovie(movie);
            }
        } catch (MTBllException ex)
        {

        }
    }

    @FXML
    private void createCategory(ActionEvent event)
    {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Creating category");
        dialog.setContentText("Category title:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
        {
            try
            {
                movieModel.createCategory(result.get());
            } catch (MTBllException ex)
            {

            }
        }
    }

    @FXML
    private void deleteCategory(ActionEvent event)
    {
        try
        {
            Category category = lstCategories.getSelectionModel().getSelectedItem();
            if (category != null)
            {
                movieModel.deleteCategory(category);
                movieModel.deleteCategoryFromTable(category);
            } else {
                
            }
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
