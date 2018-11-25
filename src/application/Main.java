package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/MemoryMap.fxml"));        
        Scene scene = new Scene(root);
        //scene.setFill(Color.TRANSPARENT);
       // stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        
        stage.show();
    }
 
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
