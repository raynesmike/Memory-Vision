/**
 *
 * @author Michael Raynes
 * UTSA CS 3443 Application Programming
 * FALL 2018
 */
package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.ConfirmBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainController implements Initializable {
	
	@FXML 
	boolean answer;
	@FXML
	private Label labelStatus;
	@FXML
	private Button loginButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Button registerButton;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private AnchorPane rootPane;
	
	public void login (ActionEvent event) throws IOException {

		if(username.getText().equals( "user" ) && password.getText().equals ("pass")){
			labelStatus.setText( "Login Succes" );
			
			Parent mainView = FXMLLoader.load( getClass().getResource( "/application/view/Main.fxml" ) );
			Scene mainScene = new Scene(mainView);
			
			Stage window = (Stage) ( (Node) event.getSource() ) .getScene() .getWindow();
			
			window.setScene(mainScene);
			window.show();
			
		}else{
			labelStatus.setText( "Login Failed" );
		}
		

		
	}
	public void register (ActionEvent event) throws IOException {

	
			labelStatus.setText( "Register" );
			
			Parent mainView = FXMLLoader.load( getClass().getResource( "/application/view/register.fxml" ) );
			Scene mainScene = new Scene(mainView);
			
			Stage window = (Stage) ( (Node) event.getSource() ) .getScene() .getWindow();
			
			window.setScene(mainScene);
			window.show();
	
	}
	
	
	
	private void mainPage() throws IOException  {
//		Stage primaryStage = new Stage();
//		Parent root = FXMLLoader.load( getClass().getResource( "fxml/Main.fxml" ) );
//		Scene scene = new Scene ( root );
//		scene.getStylesheets().add( getClass(). getResource( "application.css").toExternalForm()) ;
//		primaryStage.setTitle( "Application Programming Project" );
//		primaryStage.setScene( scene );
//		primaryStage.show();
//		

		
		
	}
	public void logout (ActionEvent event) throws Exception {
		
		boolean result = ConfirmBox.display("Logging out", "Are you sure you want to logout?");
		System.out.println(result);
		
	}
	private void closeProgram() {

		boolean result = ConfirmBox.display("Closing Program", "Are you sure you want to close?");
		if (result == true) {
			System.out.println("File is saved");
			
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}
