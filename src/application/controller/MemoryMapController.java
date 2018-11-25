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

import application.model.Code;
import application.model.Variable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
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
import javafx.scene.effect.DropShadow;
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
import javafx.stage.Screen;
import javafx.stage.Stage;






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
    CodeArea codeArea;
    @FXML
    Pane pane;
    @FXML
    Circle circleStart;
    @FXML
    Circle circleEnd;
    private Code code; 
    private ObservableList<Variable> table1,emptyTable;
    private int index = 0;
    private String [] line;
    int lineNumber;
    
    private final double NODE_MARGIN = 10;
    //private final double NODE_
    private static final int PAN_PANE_WIDTH = 20_000;
	private static final int PAN_PANE_HEIGHT = 10_000;
	private final int NODE_LAYOUT_X = 400;
    
    
    
	public void generate() {

		table1 = FXCollections.observableArrayList();

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


	    	table.setItems(emptyTable);
			code.readCode(line[index]);		
	    	
	    	//System.out.println(y.toString());
	    	table1.removeAll(code.getTable().getTable());
	  
	        table1.addAll(code.getTable().getTable());
	        
	    	index++;
	    	
			codeArea.moveTo(index, 0);
			lineNumber++;
		
	    	table.setItems(table1);
	    	lineLabel.setText("Line Number: " + String.valueOf(index));
		}else {
			lineLabel.setText("DONE READING");
		}
	}
	
	public void clear() {
		index = 0;

		codeArea.setDisable(false);
		
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		code  = new Code();

    	table1.removeAll(code.getTable().getTable());

    	lineLabel.setText("Line Number: " + String.valueOf(index));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lineNumber = 0;
		//Font.loadFont(getClass().getResourceAsStream("../view/DroidSansMono.ttf"), 30);
		
		PanPane panPane = new PanPane(pane);
		anchor_root.getChildren().add(panPane);
		// send panPane to back
		anchor_root.getChildren().get(anchor_root.getChildren().size() - 1).toBack();
		
		
		
		
		
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

        codeArea.replaceText(0, 0, codeTextArea.getText());
        
		
		code  = new Code();
    	variableCol.setCellValueFactory(new PropertyValueFactory<>("variable"));
    	addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    	valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
    	
//    	
//    	VNode node1 = new VNode();
//    	VNode node2 = new VNode();
//    	
//    	
//    	pane.getChildren().addAll(node1, node2);
//    	
//    	node1.setLayoutX(400);
//    	node1.setLayoutY(40);
//    	
//    	
//    	
//    	node2.setLayoutX(400);
//    	node2.setLayoutY(160);
//        
//        
//        CubicCurve curve = createPointerLink(node1, node2);
//        Path arrow = createArrowHead(curve);
//        
//      
//        
//        pane.getChildren().addAll(curve, arrow);
//         
//        node1.setVariable("ptr");
//        node1.setAddress("1064");
//        node1.setValue("&1024");
//        node1.setType("int *");
       
		

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
