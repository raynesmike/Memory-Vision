/** 
 * Application Programming Project 
 * @author * 
 * -Tyler Mitchell
 * -Jamal Dabas
 * -Michael Raynes
 * UTSA CS 3443 Application Programming
 * Fall 2018 
 **/
package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * 
 * @author Algorado
 * 
 *
 */
public class MainController implements Initializable {

	@FXML
	private VBox vbox;
	private Parent fxml;

	/**
	 * initialize method
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
		// t.setToX(vbox.getLayoutX() * 32);
		try {
			fxml = FXMLLoader.load(getClass().getResource("../view/SignIn.fxml"));
			vbox.getChildren().removeAll();
			vbox.getChildren().setAll(fxml);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
 
	/**
	 * Opens sign up
	 * @param event ActionEvent
	 */
	@FXML
	private void open_signup(ActionEvent event) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
		t.setToX(-472);
		t.play();
		t.setOnFinished((e) -> {
			try {
				fxml = FXMLLoader.load(getClass().getResource("../view/Tutorial.fxml"));
				vbox.getChildren().removeAll();
				vbox.getChildren().setAll(fxml);
			} catch (IOException ex) {
				Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
			}

		});
	}

	/**
	 * open sign in
	 * @param event ActionEvent
	 */
	@FXML
	private void open_signin(ActionEvent event) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
		t.setToX(0);
		t.play();
		t.setOnFinished((e) -> {
			try {
				fxml = FXMLLoader.load(getClass().getResource("../view/SignIn.fxml"));
				vbox.getChildren().removeAll();
				vbox.getChildren().setAll(fxml);
			} catch (IOException ex) {
				Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
			}

		});

	}

}
