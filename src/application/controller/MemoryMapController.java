package application.controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.model.Code;
import application.model.Variable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;


public class MemoryMapController implements Initializable{
    @FXML
    private TableView<Variable> table;
    @FXML
    private TableColumn<Variable, String> variableCol;
    @FXML
    private TableColumn<Variable, Integer>addressCol;
    @FXML
    private TableColumn<Variable, String> valueCol;
    @FXML
    private Button generateB;
    @FXML
    private TextArea codeTextArea;
    @FXML
    private Label lineLabel;
    private Code code; 
    private ObservableList<Variable> table1,emptyTable;
    private int index = 0;
    private String [] line;
    
	public void generate() {

//		table1 = FXCollections.observableArrayList();
//
//		Variable empty = new Variable("Starting",0 , "Empty", 0, 0);
//		emptyTable = FXCollections.observableArrayList();
//		emptyTable.add(empty);
//		
//		String codeString = codeTextArea.getText();
//		line = codeString.split("\\n");
//		table.setItems(emptyTable);
		
		ObservableList<Variable> table1 = FXCollections.observableArrayList();
		code  = new Code();
		String codeString = codeTextArea.getText();
		code.readCode(codeString);		
	    
    	//table.setItems(getVariable());
    	for(Variable y: code.getTable().getTable()) {
    		//System.out.println(y.toString());

        	table1.add(y);
        }
    	
    	table.setItems(table1);
	}
    
	public void next() {
		if(index < line.length) {
	    	table.setItems(emptyTable);
			code.readCode(line[index]);		
	    	
	    	//System.out.println(y.toString());
	    	table1.removeAll(code.getTable().getTable());
	  
	        table1.addAll(code.getTable().getTable());
	        
	    	index++;
	    	
	    	table.setItems(table1);
	    	lineLabel.setText("Line Number: " + String.valueOf(index));
		}else {
			lineLabel.setText("DONE READING");
		}
	}
	
	public void clear() {
		code  = new Code();
		index = 0;

    	table1.removeAll(code.getTable().getTable());

    	lineLabel.setText("Line Number: " + String.valueOf(index));
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		code  = new Code();
    	variableCol.setCellValueFactory(new PropertyValueFactory<>("variable"));
    	addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    	valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
	}
}
