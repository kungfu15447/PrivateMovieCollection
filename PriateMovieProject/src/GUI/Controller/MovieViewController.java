
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
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
    private final MovieModel MOVIEMODEL;
    private final CategoryViewModel CVM;
    private MoviePlayerViewController mpvc;

    @FXML
    private TableView<Movie> tableView;
    @FXML
    private TableColumn<Movie, String> clmTitle;
    @FXML
    private TableColumn<Movie, String> clmMyRating;
    @FXML
    private TableColumn<Movie, String> clmImdbRating;
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
        MOVIEMODEL = new MovieModel();
        CVM = new CategoryViewModel();
    }

    /**
     * initializes the columns and tables.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        clmTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmMyRating.setCellValueFactory(new PropertyValueFactory<>("stringRating"));
        clmImdbRating.setCellValueFactory(new PropertyValueFactory<>("stringImdbRating"));
        clmCateTitle.setCellValueFactory(new PropertyValueFactory<>("category"));
        clmCateCheck.setCellValueFactory(new PropertyValueFactory<>("select"));
        tableView.setItems(MOVIEMODEL.getMovies());
        tblCategory.setItems(MOVIEMODEL.getCategories());
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        runPopup();
    }

    /**
     * Chooses a movie from the tableview which then sends the filepath to the
     * movieplayer. Also makes sure that sounds stops when closing the window.
     *
     * @param event
     * @throws IOException
     * @throws MTDalException
     */
    @FXML
    private void playMovie(ActionEvent event) throws IOException, MTDalException
    {
        Movie movie = tableView.getSelectionModel().getSelectedItem();
        if (movie != null)
        {
            String filePath = movie.getFilepath();
            MOVIEMODEL.updateLastView(movie);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MoviePlayerView.fxml"));
            Parent root = (Parent) loader.load();

            MoviePlayerViewController mpvcontroller = loader.getController();
            mpvcontroller.initializeModel(MOVIEMODEL);
            mpvcontroller.getFilePath(filePath);

            Stage stage = new Stage();
            Image icon = new Image(getClass().getResourceAsStream("/GUI/View/Icon.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Movie collection");

            stage.setTitle("Movie player");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setFullScreen(true);

            stage.setOnCloseRequest((WindowEvent we) ->
            {
                mpvcontroller.stopMovie();
            });
        } else
        {
            String header = "No movie has been selected";
            String content = "Please select a movie to play";
            getAlertBox(header, content);
        }
    }

    /**
     * Exits the program.
     *
     * @param event
     */
    @FXML
    private void exit(ActionEvent event)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Movie collection");
        alert.setHeaderText("  You are about to close the program");
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

    /**
     * Adds a movie.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void addMovie(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddMovieView.fxml"));
        Parent root = (Parent) loader.load();

        AddMovieViewController amvcontroller = loader.getController();
        amvcontroller.initializeModel(MOVIEMODEL);

        Stage stage = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("/GUI/View/Icon.png"));
        stage.getIcons().add(icon);

        stage.setTitle("Movie collection");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Deletes a movie.
     *
     * @param event
     */
    @FXML
    private void deleteMovie(ActionEvent event)
    {
        try
        {
            Movie movie = tableView.getSelectionModel().getSelectedItem();
            if (movie != null)
            {
                CVM.deleteMovieFromTable(movie);
                MOVIEMODEL.deleteMovie(movie);
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
     * Creates a category.
     *
     * @param event
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
                MOVIEMODEL.createCategory(result.get());
            } catch (MTBllException ex)
            {

            }
        }
    }

    /**
     * Deletes a category.
     *
     * @param event
     */
    @FXML
    private void deleteCategory(ActionEvent event)
    {
        try
        {
            Category category = tblCategory.getSelectionModel().getSelectedItem();
            if (category != null)
            {
                CVM.deleteCategoryFromTable(category);
                MOVIEMODEL.deleteCategory(category);
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

    /**
     * Gets the alertbox for when no movie has been chosen.
     *
     * @param header
     * @param content
     */
    private void getAlertBox(String header, String content)
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
     * Uses the searchbar for input and then searches for at movie.
     *
     * @param event
     */
    @FXML
    private void writeSearch(KeyEvent event)
    {
        try
        {
            MOVIEMODEL.searchMovies(searchbar.getText().toLowerCase());
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Filters the movie list.
     *
     * @param event
     */
    @FXML
    private void filterMovieList(ActionEvent event)
    {
        try
        {

            if (!MOVIEMODEL.getMovies().isEmpty())
            {
                MOVIEMODEL.fillCheckedCategoryList();
                MOVIEMODEL.contextOfMovieList();
            }
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sorts the movie list for "movietitle".
     *
     * @param event
     */
    @FXML
    private void handlerSortTitle(ActionEvent event)
    {
        try
        {
            MOVIEMODEL.sortMovieList("movietitle");
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sorts the movie list for "movierating".
     *
     * @param event
     */
    @FXML
    private void handlerSortRating(ActionEvent event)
    {
        try
        {
            MOVIEMODEL.sortMovieList("movierating");
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sorts the movie list for "id".
     *
     * @param event
     */
    @FXML
    private void handlerSortId(ActionEvent event)
    {
        try
        {
            MOVIEMODEL.sortMovieList("id");
        } catch (MTBllException ex)
        {
            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set the rating of a movie.
     *
     * @param event
     */
    @FXML
    private void editRating(ActionEvent event)
    {
        Movie movie = tableView.getSelectionModel().getSelectedItem();
        int index = tableView.getSelectionModel().getSelectedIndex();
        if (movie == null)
        {
            String header = "You have not chosen a movie";
            String content = "Please select a movie to edit rating";
            getAlertBox(header, content);
        } else
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/EditRatingView.fxml"));
                Parent root = (Parent) loader.load();

                EditRatingViewController ervcontroller = loader.getController();
                ervcontroller.initializeModel(MOVIEMODEL);
                ervcontroller.initializeMovie(movie, index);
                Stage stage = new Stage();

                stage.setTitle("Edit Movie");

                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex)
            {

            }
        }
    }

    /**
     * Some popup scheme.
     */
    public void runPopup()
    {
        MOVIEMODEL.checkMovies();
        if (!MOVIEMODEL.getCheckMovie().isEmpty())

        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CheckMovie.fxml"));
                Parent root = (Parent) loader.load();

                CheckMovieController cmcontroller = loader.getController();
                Stage stage = new Stage();

                stage.setTitle("Popup");
                stage.setScene(new Scene(root));
                stage.show();
                stage.setOnHiding(event2 ->
                {
                    if (cmcontroller.deleteMovies())
                    {
                        try
                        {
                            MOVIEMODEL.deleteCheckMoviesFromList();
                        } catch (MTBllException ex)
                        {
                            Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            } catch (IOException ex)
            {
                Logger.getLogger(MovieViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * A popup window that shows the user they havent selected a playlist
     */
    private void displayNoMovieWindow()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information dialog");
        alert.setHeaderText("You have not selected a movie");
        alert.setContentText("Please select a movie");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/privateMovieProject/GUI/Dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        alert.showAndWait();
    }
}
