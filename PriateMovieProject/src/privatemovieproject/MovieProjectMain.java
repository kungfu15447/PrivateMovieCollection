/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemovieproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author bonde
 */
public class MovieProjectMain extends Application
{
    
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/View/MovieView.fxml"));
        Scene scene = new Scene(root);
        
        Image icon = new Image(getClass().getResourceAsStream("/GUI/View/Icon.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Movie collection");
        
        stage.setTitle("Movie collection");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
