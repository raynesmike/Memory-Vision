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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.IntFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.reactfx.Subscription;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXToggleNode;

import application.controller.components.CodeArrowFactory;
import application.controller.components.PanPane;
import application.model.C;
import application.model.Code;

import application.model.Variable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.jfoenix.controls.events.JFXDialogEvent;

/**
 * Responsible for MemoryMap.fxml
 * @author Algorado
 * 
 */

public class MemoryMapController implements Initializable {

	@FXML
	private TableView<Variable> table;
	@FXML
	private TableColumn<Variable, String> variableCol;
	@FXML
	private TableColumn<Variable, Integer> addressCol;
	@FXML
	private TableColumn<Variable, String> valueCol;
	@FXML
	private JFXButton generateB;
	@FXML
	private TextArea codeTextArea;
	@FXML
	private Label lineLabel;
	@FXML
	private VBox nbox;
	@FXML
	private AnchorPane anchor_root;
	@FXML
	private StackPane stackPane;
	@FXML
	private PanPane panPane;
	@FXML
	private Pane editor;
	@FXML
	private Stage stage;
	@FXML
	private HBox hbSettings;
	@FXML
	private Label statusL;
	@FXML
	JFXToggleNode btnHelp;
	@FXML
	JFXButton nextB;
	@FXML
	JFXButton anotherB;
	@FXML
	CodeArea codeArea;
	@FXML
	Pane pane;
	@FXML
	Circle circleStart;
	@FXML
	Circle circleEnd;
	@FXML
	FontAwesomeIcon helpIcon;

	private C syntaxH;
	private Code code;
	private ObservableList<Variable> variableList, emptyTable;
	private int index = 0;
	private String[] line;
	int lineNumber;

	String defaultCode;

	/**
	 * Generate button
	 */
	public void generate() {

		nextB.setDisable(false);
		generateB.setDisable(true);

		clear();
		variableList = FXCollections.observableArrayList();

		Variable empty = new Variable("Starting ...", 0, "Empty", 0, 0);
		emptyTable = FXCollections.observableArrayList();
		emptyTable.add(empty);

		String codeString = codeArea.getText();
		line = codeString.split("\\n");
		table.setItems(emptyTable);

	}
	
	/**
	 * button with "Choose another program" is clicked, switch to signin scene
	 * @param e ActionEvent
	 */
	public void anotherClicked(ActionEvent e) {
		SignInController.filePath = "data/";
		Parent pot;
		try {
			pot = FXMLLoader.load(getClass().getResource("../view/Signin.fxml"));
			Scene sceneW = new Scene(pot);
			
			Stage window = (Stage) (((Node) e.getSource()).getScene().getWindow());
		
			window.setScene(sceneW);
			window.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * save button
	 * @param event ActionEvent
	 */
	public void handle_save(ActionEvent event) {

		save(SignInController.filePath, codeArea.getText());

	}

	/** 
	 * Handles loading
	 */
	public void handle_load() {
		generateB.setDisable(false);
		nextB.setDisable(true);
		String codeText = "";

		codeArea.clear();

		try (BufferedReader br = new BufferedReader(new FileReader(SignInController.filePath))) {
			while (br.ready()) {
				codeText += br.readLine() + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		codeArea.replaceText(codeText.trim());
		clear();

		}
	
	/**
	 * Dialog Button
	 * @param event ActionEvent
	 */
	public void loadDialog(ActionEvent event) {

		if (btnHelp.isDisable() == false) {

			BoxBlur blur = new BoxBlur(3, 3, 3);

			JFXDialogLayout content = new JFXDialogLayout();
			content.setHeading(new Text("User Rules"));
			content.setBody(new Text("- Only initialization and modification statements\n"
					+ "- Pointers can only be initialized, no Modifying\n"
					+ "- No functions, printf statements, File IO, etc\n" + "- NO COMMENTS!\n"
					+ "- Single Space between words\n"
					+ "- \"x++\" and \"x--\" must be one string (NO SPACES between them)\n" + "- NO +=, -=, *=, /=\n"
					+ "- Please enter correct C code, while we do check for some errors, we aren't garbage collectors\n"
					+ "- Only Addition and Subtraction!\n"));

			StackPane dialogStackPane = new StackPane();

			JFXDialog dialog = new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER, true);

			JFXButton btnDone = new JFXButton("Done");

			btnDone.addEventHandler(ActionEvent.ACTION, (e) -> {
				dialog.close();
			});

			anchor_root.getChildren().add(dialogStackPane);
			
			content.setActions(btnDone);

			AnchorPane.setTopAnchor(dialogStackPane, (editor.getHeight() - content.getPrefHeight()) / 3);
			AnchorPane.setLeftAnchor(dialogStackPane, (editor.getWidth() - content.getPrefWidth()) / 3);
			dialog.show();
			dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
				editor.setEffect(null);
				pane.setEffect(null);
				btnHelp.setDisable(false);
				btnHelp.setSelected(false);
			});

			// blurs background
			editor.setEffect(blur);
			pane.setEffect(blur);

			btnHelp.setDisable(true);

		}

	}
	/**
	 * next button
	 */
	public void next() {
		// disable user input
		generateB.setDisable(true);
		codeArea.setDisable(true);

		// creates arrow indicator to left of code line
		IntFunction<Node> numberFactory = LineNumberFactory.get(codeArea);
		IntFunction<Node> arrowFactory = new CodeArrowFactory(codeArea.currentParagraphProperty());
		IntFunction<Node> graphicFactory = codeLine -> {
			HBox hbox = new HBox(numberFactory.apply(codeLine), arrowFactory.apply(codeLine));
			hbox.setAlignment(Pos.CENTER_LEFT);

			return hbox;
		};

		codeArea.setParagraphGraphicFactory(graphicFactory);

		// step through lines of code until end;
		if (index < codeArea.getParagraphs().size()) {

			code.readCode(line[index]);
			index++;

			variableList.removeAll(code.getTable().getVariableList());

			variableList.addAll(code.getTable().getVariableList());
			codeArea.moveTo(index, 0);
			lineNumber++;

			table.setItems(variableList);

		}
		if (index == codeArea.getParagraphs().size()) {
			generateB.setDisable(false);
			nextB.setDisable(true);
			codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		}

	}
	
	/**
	 * clear button
	 */

	public void clear() {
		// codeArea.clear();
		index = 0;

		codeArea.setDisable(false);
		generateB.setDisable(false);

		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		code = new Code();

		table.getItems().clear();

	}

	/**
	 * Initialize method
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		nextB.setDisable(true);

		// Creates and positions help button
		btnHelp = new JFXToggleNode();
		btnHelp.setGraphic(helpIcon);
		btnHelp.setLayoutX(helpIcon.getLayoutX() + 35);
		btnHelp.setLayoutY(helpIcon.getLayoutY() - 15);
		hbSettings.getChildren().add(btnHelp);
		//anchor_root.getChildren().add(btnHelp);
		btnHelp.setOnAction(this::loadDialog);

		// removes the table's default message
		table.setPlaceholder(new Label(""));

		// removes horizonal table scroll bar
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// links table.css to the table
		table.getStylesheets().add(MemoryMapController.class.getResource("../view/table.css").toExternalForm());

		// default/demonstration code
		defaultCode = "int v[] = {20, 30, 40};\n" + "int x = 20;\n" + "int y = 30;\n" + "char a[] = {'a', 'b', 'c'};\n"
				+ "x = 20 + 30;\n" + "y = x + 50;\n" + "v[1] = 20 + 30;\n" + "v[2] = v[1] + 50;\n"
				+ "char name = 'r';\n" + "char last = 'z';\n" + "name = last;\n" + "v[0]++;\n" + "int *p = &x;";
		
		
		
		PanPane panPane = new PanPane(pane);
		anchor_root.getChildren().add(panPane);

		// send panPane to back
		anchor_root.getChildren().get(anchor_root.getChildren().size() - 1).toBack();

		panPane.setStyle("-fx-background-color: #ffffff");

		codeArea = new CodeArea();

		codeArea.setId("codeArea");

		syntaxH = new C();

		codeArea.setLayoutX(0);
		codeArea.setLayoutY(0);
		codeArea.setPrefSize(325, 425);
		// adds line numbers to left of area
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		codeArea.setStyle("-fx-font-size:16px");
		codeArea.setEffect(new DropShadow(3, 0, 1, Color.BLACK));

		// recomputes syntax highlighting 500ms after user enters text
		@SuppressWarnings("unused")
		Subscription cleanupWhenNoLongerNeedIt = codeArea.multiPlainChanges().successionEnds(Duration.ofMillis(500))
		.subscribe(ignore -> codeArea.setStyleSpans(0, syntaxH.computeHighlighting(codeArea.getText())));

		codeArea.replaceText(0, 0, defaultCode);

		code = new Code();
		variableCol.setCellValueFactory(new PropertyValueFactory<>("variable"));
		addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
		valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

		codeArea.getStylesheets().add(MemoryMapController.class.getResource("../view/c.css").toExternalForm());
		editor.getChildren().add(codeArea);

		System.out.println(codeArea.getText().trim());
		handle_load();
	}

	/**
	 * saves the code
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
	 * writes the code
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
