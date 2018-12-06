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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.model.PopulateTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * 
 * @author Algorado
 * Responsible for Sign In
 *
 */
public class SignInController implements Initializable {

	
	@FXML 
	private TableColumn<PopulateTable, String> colName; 

	@FXML // fx:id="Table1"
	private TableView<PopulateTable> fileTable; // Value injected by FXMLLoader
	
	@FXML
	private JFXButton btnOK, btnCancel, btnNewProject, btnLaunch;
	
	@FXML
	private JFXTextField txtName;

	String fileName;
	String newProjectName;
	public static String filePath = "data/";


	public ObservableList<PopulateTable> fileList = FXCollections.observableArrayList();

	/**
	 * Helps with signIn 
	 * @param event ActionEvent
	 */
	public void SignIn(ActionEvent event) {
		
		PopulateTable selectedFile;
		selectedFile = fileTable.getSelectionModel().getSelectedItem();
		filePath += selectedFile.getName() + ".txt";
		
		try {
			Parent memoryMapParent = FXMLLoader.load(getClass().getResource("../view/MemoryMap.fxml"));
			Scene memoryMapScene = new Scene(memoryMapParent);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(memoryMapScene);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Initialize method
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnLaunch.setVisible(false);
		fileTable.getStylesheets().add(MemoryMapController.class.getResource("../view/filetable.css").toExternalForm());
		txtName.setVisible(false);
		btnCancel.setVisible(false);
		btnOK.setVisible(false);
		loadFiles();
		colName.setCellValueFactory(new PropertyValueFactory<PopulateTable, String>("name"));
		fileTable.setItems(fileList);
		
		// reveal option to launch if a project is selected
		fileTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        
		        btnLaunch.setVisible(true);
		    } else {
		    	btnLaunch.setVisible(false);
		    }
		});

	}
	
	/**
	 * loads files
	 */
	public void loadFiles() {
		File folder = new File("data");
		File[] fList = folder.listFiles();

		// loads each .txt file in 'data' folder to the table
		for (File file : fList) {
			if (file.isFile()) {
				if (file.getName().endsWith(".txt")) {
					fileList.add(new PopulateTable(file.getName().replaceFirst("[.][^.]+$", "")));
				}

			}
		}
	}
	/**
	 * aids with choosing different files
	 * @param event ActionEvent
	 */
	public void chooseFile(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		

		fileChooser.setInitialDirectory(new File("data"));
		fileChooser.setTitle("Choose a file");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		if (selectedFile != null) {
			String fileName = selectedFile.getName();
			fileTable.getItems().add(new PopulateTable(fileName));
		}

	}
	
	/**
	 * button settings
	 * @param event ActionEvent
	 */
	public void NewProject(ActionEvent event) {
		btnOK.setVisible(true);
		btnCancel.setVisible(true);
		txtName.setVisible(true);
		fileTable.getSelectionModel().clearSelection();
		btnLaunch.setVisible(false);
		btnNewProject.setVisible(false);
		
	}
	/**
	 * confirm
	 * @param event ActionEvent
	 */
	public void Confirm(ActionEvent event) {
		newProjectName = txtName.getText();
		
		List<String> lines = Arrays.asList("int newProject = 1;", "int *p = &newProject;");
		Path file = Paths.get("data/"+txtName.getText()+".txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		 
		fileList.add(new PopulateTable(newProjectName));
		fileTable.setItems(fileList);
		Cancel(event);
		
		//SignIn(event);
	}
	/**
	 * Cancel
	 * @param event ActionEvent
	 */
	public void Cancel(ActionEvent event) {
		btnOK.setVisible(false);
		btnCancel.setVisible(false);
		txtName.setVisible(false);
		btnNewProject.setVisible(true);
		
		
	}
	/**
	 * Helps with saving files
	 * @param fileName String
	 */
	public void saveAs(String fileName) {
        File newFile = new File("data/"+fileName+".txt");
    }
	
	/**
	 * Helps with save
	 * @param filePath String
	 * @param text String
	 */
	public void save(String filePath, String text) {

		try {
			write(new File(filePath), text);
		} catch (IOException ex) {
			
		}

	}

	/**
	 * helps with writing code
	 * @param file File
	 * @param text String
	 * @throws IOException IOException
	 */
	private void write(File file, String text) throws IOException {
		file.getParentFile().mkdirs();
		try (FileWriter fw = new FileWriter(file); PrintWriter pw = new PrintWriter(fw)) {
			pw.print(text.trim());
			pw.close();
			fw.close();
		}

	}
}
