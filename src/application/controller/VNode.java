package application.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import com.jfoenix.controls.JFXButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class VNode extends StackPane {
		
		@FXML private StackPane root_pane;
		@FXML private Circle port;
		@FXML private JFXButton btnVariable;
		@FXML private JFXButton btnValue;
		@FXML private JFXButton btnAddress;
		@FXML private JFXButton btnType;
		
		
		
		private Point2D mDragOffset = new Point2D (0.0, 0.0);
		
		private final VNode self;
		
		private NodeLink mDragLink = null;
		private AnchorPane right_pane = null;

		private final List <String> mLinkIds = new ArrayList <String> ();

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
		
		public Point2D portCoordinates() {
			//port.localToScene(port.layoutBounds.minX, port.layoutBounds.minY);
			return port.localToScene(Point2D.ZERO);
			//Point2D localCoords = getParent().sceneToLocal(p);
		}

		public JFXButton getVariable() {
			return btnVariable;
		}

		public void setVariable(String variableName) {
			this.btnVariable.setText(variableName);
		}

		public JFXButton getValue() {
			return btnValue;
		}

		public void setValue(String value) {
			btnValue.setText(value);
		}

		public JFXButton getAddress() {
			return btnAddress;
		}
 
		public void setAddress(String address) {
			btnAddress.setText(address);
		}

		public JFXButton getType() {
			return btnType;
		}

		public void setType(String type) {
			btnType.setText(type);;
		}
		
		

		
		
}