package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignInController {
	
	
	public void SignIn(ActionEvent event) {
		try {
			Parent memoryMapParent = FXMLLoader.load(getClass().getResource("../view/MemoryMap.fxml"));
			Scene memoryMapScene = new Scene(memoryMapParent);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(memoryMapScene);
			window.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		 
	}

}
