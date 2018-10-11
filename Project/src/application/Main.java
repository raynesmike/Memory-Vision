package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;



public class Main extends Application  {
	
	Stage window;
	Scene scene1, scene2, scene3;
  
	public static void main(String[] args) {
		launch(args);
		System.out.println("Hello, World");
	}

	
	@Override

	public void start(Stage primaryStage) throws Exception   {
		
		try {
			Parent root = FXMLLoader.load( getClass().getResource( "/application/view/login.fxml" ) );
			Scene scene = new Scene (root);
			scene.getStylesheets().add( getClass(). getResource( "application.css").toExternalForm()) ;
			primaryStage.setTitle( "Application Programming Project" );
			primaryStage.setScene( scene );
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
		
		/*
		window = primaryStage;
		window.setTitle("Aldorado");
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
			
		});
		
		// Layout 2
		Label label1 = new Label("Scene 1");
		Label label2 = new Label("Scene 2");
		
		// buttons
		Button bLogin = new Button("Login");
		Button bLogout= new Button("Logout");
		Button bCloseProgram = new Button("Close Program");
		
		// button actions
		bLogin.setOnAction( e ->  window.setScene(scene2));
		bLogout.setOnAction( e -> { 
			boolean result = ConfirmBox.display("Logging out", "Are you sure you want to logout?");
			System.out.println(result);
			if (result == true ) {
				window.setScene(scene1);
			}
		});
		bCloseProgram.setOnAction( e -> { 

			closeProgram();
		});
	
		//Layout1 - children are layed out vertical
		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(label1, bLogin, bCloseProgram);
		scene1 = new Scene(layout1, 300, 400);
	
		VBox layout2 = new VBox();
		layout2.getChildren().addAll(label2, bLogout, bCloseProgram);
		scene2 = new Scene(layout2, 400, 300);
		
		
		
		window.setScene(scene1);
		window.show(); 

	

	}

	private void closeProgram() {

		boolean result = ConfirmBox.display("Closing Program", "Are you sure you want to close?");
		if (result == true) {
			System.out.println("File is saved");
			window.close(); 
		}
	}
}
	*/	


