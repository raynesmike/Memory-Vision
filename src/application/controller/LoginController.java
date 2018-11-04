package application.controller;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class LoginController {

	@FXML
	private Button loginButton;
	@FXML
	private Button registerButton;
	
	public void login(ActionEvent event ) {
		changeView("/application/view/MemoryMap.fxml");
	}
	
	
	public void changeView(String fxmlname) {

		try {
			Parent shopView = FXMLLoader.load( getClass().getResource( fxmlname ) );
			Scene shopScene = new Scene(shopView);
			Main.stage.setScene(shopScene);
			Main.stage.show();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
