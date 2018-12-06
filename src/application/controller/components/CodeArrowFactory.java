package application.controller.components;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.reactfx.value.Val;

import java.util.function.IntFunction;

/**
 * Given the line number, return a node (graphic) to display to the left of a line.
 */
public class CodeArrowFactory implements IntFunction<Node> {
    private final ObservableValue<Integer> shownLine;

    /**
     * Constructor
     * @param shownLine shownLine
     */
    public CodeArrowFactory(ObservableValue<Integer> shownLine) {
        this.shownLine = shownLine;
    }

    /**
     * This shows the line incrementer polygon
     * @param lineNumber int
     * @return triangle Node
     * 
     */
    @Override
    public Node apply(int lineNumber) {
        Polygon triangle = new Polygon(0.0, 0.0, 10.0, 5.0, 0.0, 10.0);
        triangle.setFill(Color.GREEN);

        ObservableValue<Boolean> visible = Val.map(shownLine, sl -> sl == lineNumber);

        triangle.visibleProperty().bind(
            Val.flatMap(triangle.sceneProperty(), scene -> {
                return scene != null ? visible : Val.constant(false);
        }));

        return triangle;
    }
}