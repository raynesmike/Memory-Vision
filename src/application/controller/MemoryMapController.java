package application.controller;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.Table;
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
    private TableView<Table> table;
    @FXML
    private TableColumn<Table, String> variableCol;
    @FXML
    private TableColumn<Table, String> addressCol;
    @FXML
    private TableColumn<Table, String> valueCol;
    @FXML
    private Button generateB;
    @FXML
    private TextArea codeTextArea;
    
	public void generate() {
    	table.setItems(getTable());
    }
    
    public ObservableList<Table> getTable(){
    	ObservableList<Table> table1 = FXCollections.observableArrayList();
    	table1.add( new Table( "v1[0]","1004","value") );
    	table1.add( new Table( "v1[1]","1008","value") );
    	table1.add( new Table( "v1[2]","1012","value") );
    	table1.add( new Table( "ptr","1016","value") );
    	table1.add( new Table( "  ","1020","value") );
    	table1.add( new Table( "p1","1024","value") );
    	table1.add( new Table( "p2","1028","value") );
    	
    	
    	return table1;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	variableCol.setCellValueFactory(new PropertyValueFactory<>("variable"));
    	addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    	valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

	}

}
