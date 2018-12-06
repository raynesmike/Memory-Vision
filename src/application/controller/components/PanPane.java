
package application.controller.components;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;


public class PanPane extends Pane {

    private static Point2D mouseInitialPosition;

    public PanPane(Pane view) {
        this.getChildren().add(view);
        this.setOnScroll(this::onMouseScroll);
        this.setOnMousePressed(this::onMousePressed);
        this.setOnMouseDragged(this::onMouseDragged);
        
        
        
    }

    public void onMousePressed(MouseEvent event) {
        mouseInitialPosition = new Point2D(event.getX(), event.getY());
    }

    public void onMouseDragged(MouseEvent event) {
        pan(event.getX() - mouseInitialPosition.getX(), event.getY() - mouseInitialPosition.getY());
        mouseInitialPosition = new Point2D(event.getX(), event.getY());
    }

    public void onMouseScroll(ScrollEvent event) {
        zoom(event.getX(), event.getY(), event.getDeltaY() / 1000d);
    }

    private void zoom(double x, double y, double scaleFactor) {

        this.getChildren().forEach(child -> {
            Point2D stationaryPoint = new Point2D(x, y);
            Point2D childCentre = new Point2D(
                    child.getLayoutX() + child.getLayoutBounds().getWidth() / 2,
                    child.getLayoutY() + child.getLayoutBounds().getHeight() / 2
            );
            Point2D magnitudeChange = new Point2D(
                    (childCentre.getX() - stationaryPoint.getX()) * (scaleFactor / child.getScaleX()),
                    (childCentre.getY() - stationaryPoint.getY()) * (scaleFactor / child.getScaleY())
            );
            child.setScaleX(child.getScaleX() + scaleFactor);
            child.setScaleY(child.getScaleY() + scaleFactor);
            child.setLayoutX(child.getLayoutX() + magnitudeChange.getX());
            child.setLayoutY(child.getLayoutY() + magnitudeChange.getY());
        });

    }

    private void pan(double x, double y) {
        this.getChildren().forEach(child -> {
            child.setLayoutX(child.getLayoutX() + x);
            child.setLayoutY(child.getLayoutY() + y);
        });
    }

    public void centrePartOfNode(Node node, Bounds bounds) {
        //Zooming to scale is correct.
        double widthFactor = this.getWidth() / bounds.getWidth();
        //double heightFactor = this.getHeight()/bounds.getHeight();
        //Use the width for both as we want to scale consistently.
        Point2D boundsCentre = new Point2D(
                this.localToParent(bounds).getMinX() + this.localToParent(bounds).getWidth() / 2,
                this.localToParent(bounds).getMinY() + this.localToParent(bounds).getHeight() / 2
        );

        //Reset the scale so that the transform works, then zoom in again.
        node.setScaleX(1.0);
        node.setScaleY(1.0);
        //Shift the view to center the component.
        node.setLayoutX(this.getWidth() / 2 - boundsCentre.getX());
        node.setLayoutY(this.getHeight() / 2 - boundsCentre.getY());
        //zoom in to the correct level
        zoom(this.getWidth() / 2, this.getHeight() / 2, widthFactor - node.getScaleX());

    }

    
}
