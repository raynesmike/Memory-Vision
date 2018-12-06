package application.controller.components;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

import com.jfoenix.controls.JFXButton;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Point2D;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/**
 * visualizes how memory is connected
 * @author Algorado
 * 
 *
 */
public class VNode extends StackPane {
		
		@FXML private StackPane root_pane;
		@FXML private Circle port;
		@FXML private JFXButton btnVariable;
		@FXML private JFXButton btnValue;
		@FXML private JFXButton btnAddress;
		@FXML private JFXButton btnType;
		
		
		
		private Point2D mDragOffset = new Point2D (0.0, 0.0);
		
		private final VNode self;
		
		
		private AnchorPane right_pane = null;

		private final List <String> mLinkIds = new ArrayList <String> ();

		/**
		 * Constructor
		 */
		public VNode() {
			
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("../view/VNode.fxml")
					);
			
			fxmlLoader.setRoot(this); 
			fxmlLoader.setController(this);
			
			self = this;
			
			try { 
				fxmlLoader.load();
	        
			} catch (IOException exception) {
			    throw new RuntimeException(exception);
			}
			//provide a universally unique identifier for this object
			setId(UUID.randomUUID().toString());

		}
		
		/**
		 * set-up
		 * @return port.localToScene(Point2D.ZERO)
		 */
		public Point2D portCoordinates() {
			//port.localToScene(port.layoutBounds.minX, port.layoutBounds.minY);
			return port.localToScene(Point2D.ZERO);
			//Point2D localCoords = getParent().sceneToLocal(p);
		}
		
		// GETTERS & SETTERS
		
		/**
		 * set-up for cubic curve
		 * @param source VNode
		 * @param target target
		 * @return curve
		 */
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
		 * @return arrowEnd
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

		/**
		 * getter method
		 * @return btnVariable String
		 */
		public JFXButton getVariable() {
			return btnVariable;
		}

		/**
		 * setter method
		 * @param variableName String
		 */
		public void setVariable(String variableName) {
			this.btnVariable.setText(variableName);
		}

		/**
		 * getter method
		 * @return btnValue String
		 */
		public JFXButton getValue() {
			return btnValue;
		}

		/**
		 * setter method
		 * @param btnValue String
		 */
		public void setValue(String value) {
			btnValue.setText(value);
		}

		/**
		 * getter method
		 * @return btnAddress String
		 */
		public JFXButton getAddress() {
			return btnAddress;
		}
 
		/**
		 * setter method
		 * @param address String
		 */
		public void setAddress(String address) {
			btnAddress.setText(address);
		}

		/**
		 * getter method
		 * @return btnType String
		 */
		public JFXButton getType() {
			return btnType;
		}

		/**
		 * setter method
		 * @param type String
		 */
		public void setType(String type) {
			btnType.setText(type);;
		}
		
		
		
		

		
		
}