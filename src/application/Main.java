/** 
 * Application Programming Project 
 * @author * 
 * -Tyler Mitchell
 * -Jamal Dabas
 * -Michael Raynes
 * UTSA CS 3443 Application Programming
 * Fall 2018 
 **/
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author 
 * * - Tyler Mitchell
 * - Jamal Dabas
 * - Michael Raynes
 * UTSA CS 3443 Application Programming
 * Fall 2018 
 * Main function starts the program
 *
 */
public class Main extends Application {
    
	/**
	 * start method used for starting the Main fxml
	 */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));        
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);     
        stage.show();
    }
   
    /**
     * main function
     * @param args String []
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}