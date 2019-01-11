/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Category;
import GUI.Model.CategoryMovieModel;
import GUI.Model.MovieModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private CategoryMovieModel cmm;
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
    public CategoryViewController()
    {
        cmm = new CategoryMovieModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        clmCateTitle.setCellValueFactory(new PropertyValueFactory<>("category"));
        clmCateSelect.setCellValueFactory(new PropertyValueFactory<>("select"));
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

        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void initializeModel(CategoryMovieModel cmm)
    {
        this.cmm = cmm;
        setCategoryTable();
    }

    private void setCategoryTable()
    {
        tblCategory.setItems(cmm.getCategories());
    }

}
