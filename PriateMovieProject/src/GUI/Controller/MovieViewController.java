
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
import GUI.Model.CategoryViewModel;
import GUI.Model.MovieModel;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

/**
 *
 * @author bonde
 */
public class MovieViewController implements Initializable
{

    private MediaPlayer mediaPlayer;
    private String filePath;
    private final MovieModel movieModel;
    private final CategoryViewModel cvm;
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
    @FXML
    private Button btnSortTitle;
    @FXML
    private Button btnSortRating;
    @FXML
    private Button btnSortId;

    public MovieViewController() throws MTBllException
    {
        movieModel = new MovieModel();
        cvm = new CategoryViewModel();
    }

    /**
     * initializes the columns and tables.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        {
            try
            {
                clmTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
                clmMyRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
                clmCateTitle.setCellValueFactory(new PropertyValueFactory<>("category"));
                clmCateCheck.setCellValueFactory(new PropertyValueFactory<>("select"));
                tableView.setItems(movieModel.getMovies());
                tblCategory.setItems(movieModel.getCategories());
                tableView.getSelectionModel().setCellSelectionEnabled(true);
                runPopup();
            } catch (IOException ex)
            {
                Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /*
    Temporary movie chooser.
     */
    @FXML
    private void playMovie(ActionEvent event) throws IOException, MTDalException
    {
        Movie movie = tableView.getSelectionModel().getSelectedItem();
        if (movie != null)
        {
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
            stage.setFullScreen(true);

            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                public void handle(WindowEvent we)
                {
                    mpvcontroller.stopMovie();
                }
            });
        } else
        {
            String header = "No movie has been selected";
            String content = "Please select a movie to play";
            getAlertBox(header, content);
        }
    }

    /*
    Exits the program.
     */
    @FXML
    private void exit(ActionEvent event)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Movie collection");
        alert.setHeaderText("You are about to close the program");
        alert.setContentText("Are you sure you want to exit?");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/GUI/View/Dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("dialogPane");
        dialogPane.setGraphic(new ImageView(this.getClass().getResource("/GUI/View/Mouse.png").toString()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            System.exit(0);
        }
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
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * deletes a movie
     */
    @FXML
    private void deleteMovie(ActionEvent event)
    {
        try
        {
            Movie movie = tableView.getSelectionModel().getSelectedItem();
            if (movie != null)
            {
                cvm.deleteMovieFromTable(movie);
                movieModel.deleteMovie(movie);
            } else
            {
                String header = "No movie has been selected";
                String content = "Please select a movie to be deleted";
                getAlertBox(header, content);
            }
        } catch (MTBllException ex)
        {
        }
    }

    /**
     * Creates a category
     */
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

    /**
     * Deletes a category
     */
    @FXML
    private void deleteCategory(ActionEvent event)
    {
        try
        {
            Category category = tblCategory.getSelectionModel().getSelectedItem();
            if (category != null)
            {
                cvm.deleteCategoryFromTable(category);
                movieModel.deleteCategory(category);
            } else
            {
                String header = "You have not chosen a category";
                String content = "Please select a category to delete";
                getAlertBox(header, content);
            }
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getAlertBox(String header, String content)
    {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Dialog");
        alert.setHeaderText(header);
        alert.setContentText(content);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/GUI/View/Dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("dialogPane");
        dialogPane.setGraphic(new ImageView(this.getClass().getResource("/GUI/View/Mouse.png").toString()));

        alert.showAndWait();
    }

    /**
     * uses the searchbar for input and then searches for at movie.
     */
    @FXML
    private void writeSearch(KeyEvent event)
    {
        try
        {
            movieModel.searchMovies(searchbar.getText().toLowerCase());
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Filters the movie list
     */
    @FXML
    private void filterMovieList(ActionEvent event)
    {
        try
        {
            movieModel.fillCheckedCategoryList();
            movieModel.contextOfMovieList();
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sorts the movie list for "movietitle".
     */
    @FXML
    private void handlerSortTitle(ActionEvent event)
    {
        try
        {
            movieModel.sortMovieList("movietitle");
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sorts the movie list for "movierating".
     */
    @FXML
    private void handlerSortRating(ActionEvent event)
    {
        try
        {
            movieModel.sortMovieList("movierating");
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sorts the movie list for "id".
     */
    @FXML
    private void handlerSortId(ActionEvent event)
    {
        try
        {
            movieModel.sortMovieList("id");
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Some popup scheme.
     */
    public void runPopup() throws IOException
    {

        if (!movieModel.getCheckMovie().isEmpty())
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CheckMovie.fxml"));
            Parent root = (Parent) loader.load();

            CheckMovieController cmcontroller = loader.getController();

            Stage stage = new Stage();

            stage.setTitle("Popup");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
