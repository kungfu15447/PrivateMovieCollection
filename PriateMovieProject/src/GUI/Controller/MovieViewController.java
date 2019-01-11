/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import BE.Category;
import BE.Movie;
import BLL.Exception.MTBllException;
import DAL.Exception.MTDalException;
import GUI.Model.CategoryMovieModel;
import GUI.Model.MovieModel;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author bonde
 */
public class MovieViewController implements Initializable
{

    private MediaPlayer mediaPlayer;
    private String filePath;
    private final MovieModel movieModel;
    private final CategoryMovieModel cmm;
    private MoviePlayerViewController mpvc;

    @FXML
    private TableView<Movie> tableView;
    @FXML
    private TableColumn<Movie, String> clmTitle;
    @FXML
    private TableColumn<Movie, Double> clmMyRating;
    @FXML
    private TableColumn<Movie, Double> clmImdbRating;
    @FXML
    private Button btnEditRating;
    @FXML
    private Button btnAddCate;
    @FXML
    private Button btnDeleteCate;
    @FXML
    private TableView<Category> tblCategory;
    @FXML
    private TableColumn<Category, String> clmCateTitle;
    @FXML
    private TableColumn<Category, String> clmCateCheck;
    @FXML
    private TextField searchbar;

    public MovieViewController() throws MTBllException
    {
        movieModel = new MovieModel();
        cmm = new CategoryMovieModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        clmTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmMyRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        clmCateTitle.setCellValueFactory(new PropertyValueFactory<>("category"));
        clmCateCheck.setCellValueFactory(new PropertyValueFactory<>("select"));
        tableView.setItems(movieModel.getMovies());
        tblCategory.setItems(movieModel.getCategories());
        tableView.getSelectionModel().setCellSelectionEnabled(true);
    }

    /*
    Temporary movie chooser.
     */
    @FXML
    private void playMovie(ActionEvent event) throws IOException, MTDalException
    {
        Movie movie = tableView.getSelectionModel().getSelectedItem();
        String filePath = movie.getFilepath();
        movieModel.updateLastView(movie);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MoviePlayerView.fxml"));
        Parent root = (Parent) loader.load();

        MoviePlayerViewController mpvcontroller = loader.getController();
        mpvcontroller.initializeModel(movieModel);
        mpvcontroller.getFilePath(filePath);

        Stage stage = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("/GUI/View/Icon.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Movie collection");
        
        stage.setTitle("Movie player");
        stage.setScene(new Scene(root));
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              System.out.println("Stage lort");
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
        Image icon = new Image(getClass().getResourceAsStream("/GUI/View/Icon.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Movie collection");
        
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
            if (movie != null)
            {
                cmm.deleteMovieFromTable(movie);
                movieModel.deleteMovie(movie);
            } else
            {
                getAlertBox();
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
            Category category = tblCategory.getSelectionModel().getSelectedItem();
            if (category != null)
            {
                cmm.deleteCategoryFromTable(category);
                movieModel.deleteCategory(category);
            } else
            {

            }
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getAlertBox()
    {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Dialog");
        alert.setHeaderText("You have not chosen a movie");
        alert.setContentText("Please select a movie");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/GUI/View/Dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("dialogPane");
        dialogPane.setGraphic(new ImageView(this.getClass().getResource("/GUI/View/Mouse.png").toString()));

        alert.showAndWait();
    }

    @FXML
    private void writeSearch(KeyEvent event)
    {
        try
        {
            tableView.setItems(movieModel.searchMovies(movieModel.getMovies(), searchbar.getText().toLowerCase()));
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void filterMovieList(ActionEvent event)
    {
        try
        {
            movieModel.fillCheckedCategoryList();
            tableView.setItems(movieModel.contextOfMovieList());
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void popup() throws IOException
    {
        Popup popup = new Popup();
        CheckMovieController controller = new CheckMovieController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CheckMovie.fxml"));
        loader.setController(controller);
        popup.getContent().add((Parent)loader.load());
    }

}
