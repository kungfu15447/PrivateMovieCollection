/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Category;
import BLL.Exception.MTBllException;
import BLL.MovieManager;
import GUI.Model.MovieModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class CategoryViewController implements Initializable
{
    private MovieModel movieModel;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Category> tblCategory;
    @FXML
    private TableColumn<Category, String> clmCateTitle;
    @FXML
    private TableColumn<Category, String> clmCateSelect;
    
    /**
     * Initializes the controller class.
     */
    
    public CategoryViewController() {
        try
        {
            movieModel = new MovieModel();
        } catch (MTBllException ex)
        {
            Logger.getLogger(CategoryViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        clmCateTitle.setCellValueFactory(new PropertyValueFactory<>("category"));
        clmCateSelect.setCellValueFactory(new PropertyValueFactory<>("select"));
        tblCategory.setItems(movieModel.getCategories());
    }    

    @FXML
    private void handleCancelBtn(ActionEvent event)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleOkBtn(ActionEvent event)
    {
        movieModel.setCheckedCategory(movieModel.getCategories());
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    public void initializeModel(MovieModel mm) {
        this.movieModel = mm;
    }
    
    
    
}