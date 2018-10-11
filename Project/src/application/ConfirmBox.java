/**
 *
/**
 *
 * @author Michael Raynes
 * UTSA CS 3443 Application Programming
 * FALL 2018
 */
package application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * @author michael
 *
 */
public class ConfirmBox {
	
	static boolean answer; 
	
	public static boolean display ( String title, String message) {
		Stage window = new Stage();
		
		//stage
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		//labels
		Label label = new Label();
		label.setText(message);
		
		//button
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		
		yesButton.setOnAction(e -> {
			answer = true;
			window.close();
		});
		
		noButton.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		
		//layout
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, yesButton, noButton);
		layout.setAlignment(Pos.CENTER);
		
		//scenes
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait(); 
		
		
		
		return answer;		
	}
	


}






