package application.controller;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.Code;
import application.model.Variable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Code code; 
    
	public void generate() {
		code  = new Code();
		//String codeString = codeTextArea.getText();
		
    	table.setItems(getVariable());
		//code.readCode(codeString);		
    }
    
    public ObservableList<Variable> getVariable(){
    	ObservableList<Variable> table1 = FXCollections.observableArrayList();
    	table1.add( new Variable( "v1[0]",1004,"value") );
    	table1.add( new Variable( "v1[1]",1008,"value") );
    	table1.add( new Variable( "v1[2]",1012,"value") );
    	table1.add( new Variable( "ptr"  ,1016,"value") );
    	table1.add( new Variable( "  "   ,1020,"value") );
    	table1.add( new Variable( "p1"   ,1024,"value") );
    	table1.add( new Variable( "p2"   ,1028,"value") );
    	
    	
    	return table1;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	variableCol.setCellValueFactory(new PropertyValueFactory<>("variable"));
    	addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    	valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
	}
}
