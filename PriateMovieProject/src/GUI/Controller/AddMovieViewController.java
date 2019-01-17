/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Movie;
import BLL.Exception.MTBllException;
import GUI.Model.CategoryViewModel;
import GUI.Model.MovieModel;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private TextField txtFilepath;
    @FXML
    private AnchorPane rootPane;
    private Slider ratingSlider;
    @FXML
    private Label lblRating;

    private MovieModel movieModel;
    private final CategoryViewModel CVM;
    @FXML
    private TextField txtRating;

    public AddMovieViewController() {
        CVM = new CategoryViewModel();

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        txtFilepath.setDisable(true);
        txtRating.setDisable(true);
    }

    /**
     * chooses the file
     *
     * @param event
     */
    @FXML
    private void chooseFile(ActionEvent event)
    {
        movieModel.initializeFile();
        txtFilepath.setText(movieModel.getFilePath());
    }

    /**
     * Creates a movie object and saves it with the given specifics
     *
     * @param event
     */
    @FXML
    private void saveMovie(ActionEvent event)
    {

        boolean emptyField = getEmptyFieldInfo();
        if (!emptyField)
        {
            try
            {
                String title = txtTitle.getText();
                double imdbrating;
                if (txtRating.getText().equals("No rating given") || txtRating.getText().isEmpty())
                {
                    imdbrating = -1;
                } else
                {
                    imdbrating = Double.parseDouble(txtRating.getText());
                }
                Date Date = new Date();
                long MiliTime = Date.getTime();
                int days = (int) (MiliTime / (60 * 60 * 24 * 1000));
                int lastview = days;
                double personalRating = -1;
                String filepath = txtFilepath.getText();
                if (!movieModel.checkMovieTitles(title))
                {
                    Movie movie = movieModel.createMovie(title, personalRating, filepath, lastview, imdbrating);
                    CVM.addCategoryToMovie(CVM.getCheckedCategory(), movie);
                    Stage stage = (Stage) rootPane.getScene().getWindow();
                    stage.close();
                } else
                {
                    changeTitleAlertBox();
                }

            } catch (MTBllException ex)
            {
                displayError(ex);
            }
        } else
        {
            getAlertBox();
        }
    }

    /**
     * Closes the window
     *
     * @param event
     */
    @FXML
    private void cancelMovie(ActionEvent event)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Chooses the category for the movie
     *
     * @param event
     */
    @FXML
    private void handleCategoryChooseBtn(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CategoryView.fxml"));
            Parent root = (Parent) loader.load();
            CategoryViewController cwcontroller = loader.getController();
            cwcontroller.initializeModel(CVM);

            Stage stage = new Stage();
            Image icon = new Image(getClass().getResourceAsStream("/GUI/View/Icon.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Movie collection");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            displayError(ex);
        }
    }

    /**
     * Dragging the slider will adjust the users rating.
     *
     * @param event
     */
    private void handleRating(MouseEvent event)
    {
        ratingSlider.valueProperty().addListener((Observable observable)
                ->
        {
            lblRating.setText(new BigDecimal(ratingSlider.getValue()).setScale(1, RoundingMode.HALF_UP).toString());
        });
    }

    /**
     * A popup window that displays the error that occured
     *
     * @param ex the exception getting showened to the user
     */
    private void displayError(Exception ex)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/GUI/View/Dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("dialogPane");
        dialogPane.setGraphic(new ImageView(this.getClass().getResource("/GUI/View/Keyboard.png").toString()));

        alert.showAndWait();
    }

    /**
     * returns the error info
     *
     * @return errorInfo
     */
    public String getErrorInfo()
    {
        String errorInfo;
        if (txtTitle.getText().isEmpty())
        {
            errorInfo = "title";
        } else if (txtFilepath.getText().isEmpty())
        {
            errorInfo = "file";
        } else
        {
            errorInfo = null;
        }
        return errorInfo;
    }

    /**
     * returns the info from the empty field
     *
     * @return emptyField
     */
    public boolean getEmptyFieldInfo()
    {

        boolean emptyField = false;
        if (txtTitle.getText().isEmpty() || txtFilepath.getText().isEmpty())
        {
            emptyField = true;
        }
        return emptyField;
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
     * Retrieves the Alert Box to show the user, if the title has not been
     * filled out
     */
    public void getAlertBox()
    {
        String errorInfo = getErrorInfo();
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Dialog");
        alert.setHeaderText("You have not chosen a " + errorInfo + " for the movie");
        alert.setContentText("Please select a " + errorInfo + " for the movie");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/GUI/View/Dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("dialogPane");
        dialogPane.setGraphic(new ImageView(this.getClass().getResource("/GUI/View/Keyboard.png").toString()));

        Image icon = new Image(this.getClass().getResourceAsStream("/GUI/View/Icon.png"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        alert.showAndWait();
    }

    /**
     * Retrieves the Alert Box to show the user, if the user tries to change the
     * title to a title that has already been chosen
     */
    public void changeTitleAlertBox()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Movie title " + txtTitle.getText() + " already taken");
        alert.setContentText("Please find another title for this movie");

        alert.showAndWait();
    }

    /**
     *
     * @param event
     */
    @FXML
    private void handlerChooseRating(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/IMDBRatingView.fxml"));
            Parent root = (Parent) loader.load();
            IMDBRatingViewController rvcontroller = loader.getController();
            rvcontroller.initializeModel(movieModel);
            rvcontroller.initializeTitle(txtTitle.getText());

            Stage stage = new Stage();
            stage.setTitle("IMDB ratings");

            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(event2 ->
            {
                if (rvcontroller.getRating() == -1)
                {
                    txtRating.setText("No rating given");
                } else
                {
                    txtRating.setText(Double.toString(rvcontroller.getRating()));
                }
            });
        } catch (IOException ex) {
            displayError(ex);
        }
    }

    /**
     * updates the local imdb database
     * @param event
     */
    private void updateIMDBDatabase(ActionEvent event)
    {
        try
        {
            movieModel.downloadIMDBDatabase();
        } catch (MTBllException ex) {
            displayError(ex);
        }
    }
}
