package application.controller;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.IntFunction;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.reactfx.Subscription;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXToggleNode;

import application.model.Code;
import application.model.Variable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.jfoenix.controls.events.JFXDialogEvent;




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
    JFXToggleNode btnHelp;
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
    private Code code; 
    private ObservableList<Variable> variableList,emptyTable;
    private int index = 0;
    private String [] line;
    int lineNumber;
    
    private final double NODE_MARGIN = 10;
    //private final double NODE_
    private static final int PAN_PANE_WIDTH = 20_000;
	private static final int PAN_PANE_HEIGHT = 10_000;
	private final int NODE_LAYOUT_X = 400;
	
	String defaultCode;
    
    
    
	public void generate() {
		clear();
		variableList = FXCollections.observableArrayList();

		Variable empty = new Variable("Starting",0 , "Empty", 0, 0);
		emptyTable = FXCollections.observableArrayList();
		emptyTable.add(empty);
		
		String codeString = codeArea.getText();
		line = codeString.split("\\n");
		table.setItems(emptyTable);
		
		
//		ObservableList<Variable> table1 = FXCollections.observableArrayList();
//		code  = new Code();
//		String codeString = codeTextArea.getText();
//		code.readCode(codeString);		
//	    
//    	//table.setItems(getVariable());
//    	for(Variable y: code.getTable().getTable()) {
//    		//System.out.println(y.toString());
//
//        	table1.add(y);
//        }
//    	
//    	table.setItems(table1);
    	
	}
	
	public void loadDialog(ActionEvent event) {
		
		if(btnHelp.isDisable()==false) {
			
		BoxBlur blur = new BoxBlur(3, 3, 3);
		
		JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text("User Rules"));
		content.setBody(new Text(
				" - NO COMMENTS!\n" + 
				"- \"x++\" and \"x--\" must be one string (NO SPACES)\n" + 
				"- NO +=, -=, *=, /=\n" + 
				"- Please enter correct C code, while we do check for some errors, we aren't garbage collectors\n"));
		 
		
		StackPane dialogStackPane = new StackPane();
		//dialogStackPane.autosize();
		JFXDialog dialog = new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER, true);
		
		
		JFXButton btnDone = new JFXButton("Done");
		
		btnDone.addEventHandler(ActionEvent.ACTION,(e) -> {dialog.close();});
		
		/*btnDone.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				dialog.close();
				
			}
		});*/
		
		
		anchor_root.getChildren().add(dialogStackPane);
		
		content.setActions(btnDone);
		
		AnchorPane.setTopAnchor(dialogStackPane, (editor.getHeight() - content.getPrefHeight()) / 2);
		AnchorPane.setLeftAnchor(dialogStackPane, (editor.getWidth() - content.getPrefWidth()) / 2);
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
    
	public void next() {
		// disable user input 
		codeArea.setDisable(true);
		
		// creates arrow indicator to left of code line 
		IntFunction<Node> numberFactory = LineNumberFactory.get(codeArea);
        IntFunction<Node> arrowFactory = new CodeArrowFactory(codeArea.currentParagraphProperty());
        IntFunction<Node> graphicFactory = codeLine -> {
            HBox hbox = new HBox(
                    numberFactory.apply(codeLine),
                    arrowFactory.apply(codeLine));
            hbox.setAlignment(Pos.CENTER_LEFT);
            
            return hbox;
        };
        
        codeArea.setParagraphGraphicFactory(graphicFactory);
        
        // step through lines of code until end;
		if (index < codeArea.getParagraphs().size()) {
			
			
	    	//table.setItems(emptyTable);
			code.readCode(line[index]);		
	    	
	    	//System.out.println(y.toString());
	    	variableList.removeAll(code.getTable().getVariableList());
	  
	        variableList.addAll(code.getTable().getVariableList());
	        
	    	
	    	
			codeArea.moveTo(index, 0);
			lineNumber++;
		
	    	table.setItems(variableList);
	    	index++;
	    	
	    	//lineLabel.setText("Line Number: " + String.valueOf(index));
		}else {
			//lineLabel.setText("DONE READING");
		}
	}
	
	public void clear() {
		index = 0;

		codeArea.setDisable(false);
		
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		code  = new Code();

    	table.getItems().clear();

    	///lineLabel.setText("Line Number: " + String.valueOf(index));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// help button
		btnHelp = new JFXToggleNode();
		
		btnHelp.setGraphic(helpIcon);
		
		btnHelp.setLayoutX(helpIcon.getLayoutX() + 35);
		btnHelp.setLayoutY(helpIcon.getLayoutY() - 15);
		anchor_root.getChildren().add(btnHelp);
		
		btnHelp.setOnAction(this::loadDialog);
		// makes the table's default message empty
		table.setPlaceholder(new Label(""));
		
		// demonstration code
		defaultCode = "int v[] = {20, 30, 40};\n" + 
				"int x = 20;\n" + 
				"int y = 30;\n" + 
				"char a[] = {'a', 'b', 'c'};\n" + 
				"x = 20 + 30;\n" + 
				"y = x + 50;\n" + 
				"v[1] = 20 + 30;\n" + 
				"v[2] = v[1] + 50;\n" + 
				"char name = 'r';\n" + 
				"char last = 'z';\n" + 
				"name = last;\n" + 
				"v[0]++;\n" + 
				"int *p = &x;";
		 
		// hides table focus border
	//	table.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color: transparent; ");
	//	table.setStyle("-fx-faint-focus-color: transparent;");
		
		lineNumber = 0;
		//Font.loadFont(getClass().getResourceAsStream("../view/DroidSansMono.ttf"), 30);
		
		PanPane panPane = new PanPane(pane);
		anchor_root.getChildren().add(panPane);
		// send panPane to back
		anchor_root.getChildren().get(anchor_root.getChildren().size() - 1).toBack();
		
		// links table.css to the table
		table.getStylesheets().add(MemoryMapController.class.getResource("../view/table.css").toExternalForm());
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		
		
		panPane.setStyle("-fx-background-color: #ffffff");
        
        
		codeArea = new CodeArea();
		
		codeArea.setId("codeArea");
		
		C c = new C();
		
		codeArea.setLayoutX(0);
		codeArea.setLayoutY(0);
		codeArea.setPrefSize(325, 425);
		
		 

		
		
		// adds line numbers to left of area
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		codeArea.setStyle("-fx-font-size:16px");
		codeArea.setEffect(new DropShadow(3, 0, 1, Color.BLACK));
		
		
		// recomputes syntax highlighting 500ms after user enters text
		Subscription cleanupWhenNoLongerNeedIt = codeArea

                // plain changes = ignore style changes that are emitted when syntax highlighting is reapplied
                // multi plain changes = save computation by not rerunning the code multiple times
                //   when making multiple changes (e.g. renaming a method at multiple parts in file)
                .multiPlainChanges()

                // do not emit an event until 500 ms have passed since the last emission of previous stream
                .successionEnds(Duration.ofMillis(500))

                // run the following code block when previous stream emits an event
                .subscribe(ignore -> codeArea.setStyleSpans(0, c.computeHighlighting(codeArea.getText())));

        // when no longer need syntax highlighting and wish to clean up memory leaks
        // run: `cleanupWhenNoLongerNeedIt.unsubscribe();`

        codeArea.replaceText(0, 0, defaultCode);
        
		
		code  = new Code();
    	variableCol.setCellValueFactory(new PropertyValueFactory<>("variable"));
    	addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    	valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
    	
    	
    	VNode varV0 = new VNode();
    	VNode varV1 = new VNode();
    	VNode varV2 = new VNode();
    	
    	VNode varX = new VNode();
    	VNode varY = new VNode();
    	
    	VNode varA0 = new VNode();
    	VNode varA1 = new VNode();
    	
    	VNode varA2 = new VNode();
    	
    	VNode varName = new VNode();
    	
    	VNode varLast = new VNode();
    	VNode varPtr = new VNode();
    	
    	
    	
    	
    	
    	//pane.getChildren().addAll(varV0, varV1, varV2, varX, varY, varA0, varA1, varA2, varName, varLast, varPtr);
    	
    	varV0.setLayoutX(400);
    	varV1.setLayoutX(400);
    	varV2.setLayoutX(400);
    	varX.setLayoutX(400);
    	varY.setLayoutX(400);
    	varA0.setLayoutX(400);
    	varA1.setLayoutX(400);
    	varA2.setLayoutX(400);
    	varName.setLayoutX(400);
    	varLast.setLayoutX(400);
    	varPtr.setLayoutX(400);
    	
    	
        int yy = 40;
    	varV0.setLayoutY(yy);
    	varV1.setLayoutY(yy*3);
    	varV2.setLayoutY(yy*5);
    	
    	varX.setLayoutY(yy*7);
    	varY.setLayoutY(yy*9);
    	
    	varA0.setLayoutY(yy*11);
    	varA1.setLayoutY(yy*13);
    	varA2.setLayoutY(yy*15);
    	
    	varName.setLayoutY(yy*17);
    	varLast.setLayoutY(yy*19);
    	varPtr.setLayoutY(yy*21);
    	
    	
        varV0.setAddress("1000");
        varV0.setValue("20");
        
        varV1.setAddress("1004");
        varV1.setValue("30");
        
        varV2.setAddress("1008");
        varV1.setValue("40");
        
        varX.setAddress("1012");
        varY.setAddress("1016");
        
        varA0.setAddress("1017");
        varA1.setAddress("1018");
        varA2.setAddress("1019");
        
        varName.setAddress("1020");
        varLast.setAddress("1021");
        varPtr.setAddress("1022");
        
        
    	
    	
    	
    	
    	
    	
        CubicCurve curve = createPointerLink(varV0, varV1);
        Path arrow = createArrowHead(curve);
        
      
        
        pane.getChildren().addAll();
         
        varV0.setVariable("ptr");
        varV0.setAddress("1064");
        varV0.setValue("&1024");
        varV0.setType("int *");
       
		

    	codeArea.getStylesheets().add(MemoryMapController.class.getResource("../view/c.css").toExternalForm());
    	
    	
    	 
    	//TODO for arrow indicator when generate/next is run 
    	
    	if(true) {
    	
       // codeArea.setParagraphGraphicFactory(graphicFactory);
    	} else {
    		//codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
    	}
    	
    	
       
		
		editor.getChildren().add(codeArea);
		
	}
	
	
	
	private CubicCurve createPointerLink(VNode source, VNode target) {
		CubicCurve curve = new CubicCurve();
		
		curve.setStartX(source.getLayoutX());
	    curve.setStartY(source.getLayoutY()+10);
	    curve.setControlX1(source.getLayoutX() - 40);
	    curve.setControlY1(source.getLayoutY()+10);
	    
	    curve.setControlX2(target.getLayoutX() - 40);
	    curve.setControlY2(target.getLayoutY()+10);
	    curve.setEndX(target.getLayoutX());
	    curve.setEndY(target.getLayoutY()+10);
	    
	    curve.setSmooth(true);
	    curve.setStroke(Color.BLACK);
	    curve.setStrokeWidth(3);
	    curve.setFill(null);
	    curve.setStrokeLineCap(StrokeLineCap.ROUND);
	    
		return curve;
		
	}
	
	/**
	 * Creates an arrow pointing to the pointer link's target
	 * @param pointerLink
	 * @return
	 */
	private Path createArrowHead(CubicCurve pointerLink) {
		double size=Math.max(pointerLink.getBoundsInLocal().getWidth(),
				pointerLink.getBoundsInLocal().getHeight());
		double scale=size/4d;
		
	    
	    Point2D ori=eval(pointerLink, 1);
	    Point2D tan=evalDt(pointerLink, 1).normalize().multiply(scale);
	    Path arrowEnd=new Path();
	    arrowEnd.getElements().add(new MoveTo(ori.getX()-0.2*tan.getX()-0.2*tan.getY(),
	                                        ori.getY()-0.2*tan.getY()+0.2*tan.getX()));
	    arrowEnd.getElements().add(new LineTo(ori.getX(), ori.getY()));
	    arrowEnd.getElements().add(new LineTo(ori.getX()-0.2*tan.getX()+0.2*tan.getY(),
	                                        ori.getY()-0.2*tan.getY()-0.2*tan.getX()));
	    
	    
	    arrowEnd.setStrokeLineCap(StrokeLineCap.ROUND);
	    arrowEnd.setStrokeWidth(3);
	    arrowEnd.setStrokeLineJoin(StrokeLineJoin.ROUND);
	    
	    return arrowEnd;
	}
	/**
	 * This is a utility method for arrow generation
	 * Evaluate the cubic curve at a parameter 0<=t<=1, returns a Point2D
	 * @param c the CubicCurve 
	 * @param t param between 0 and 1
	 * @return a Point2D 
	 */
	private Point2D eval(CubicCurve c, float t){
	    Point2D p=new Point2D(Math.pow(1-t,3)*c.getStartX()+
	            3*t*Math.pow(1-t,2)*c.getControlX1()+
	            3*(1-t)*t*t*c.getControlX2()+
	            Math.pow(t, 3)*c.getEndX(),
	            Math.pow(1-t,3)*c.getStartY()+
	            3*t*Math.pow(1-t, 2)*c.getControlY1()+
	            3*(1-t)*t*t*c.getControlY2()+
	            Math.pow(t, 3)*c.getEndY());
	    return p;
	}
 
	/**
	 * This is a utility method for arrow generation
	 * Evaluate the tangent of the cubic curve at a parameter 0<=t<=1, returns a Point2D
	 * @param c the CubicCurve 
	 * @param t param between 0 and 1
	 * @return a Point2D 
	 */
	private Point2D evalDt(CubicCurve c, float t){
	    Point2D p=new Point2D(-3*Math.pow(1-t,2)*c.getStartX()+
	            3*(Math.pow(1-t, 2)-2*t*(1-t))*c.getControlX1()+
	            3*((1-t)*2*t-t*t)*c.getControlX2()+
	            3*Math.pow(t, 2)*c.getEndX(),
	            -3*Math.pow(1-t,2)*c.getStartY()+
	            3*(Math.pow(1-t, 2)-2*t*(1-t))*c.getControlY1()+
	            3*((1-t)*2*t-t*t)*c.getControlY2()+
	            3*Math.pow(t, 2)*c.getEndY());
	    return p;
	}
	
	
	

}
