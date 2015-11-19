
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class NewFXMain extends Application {

    List<Arrow> arrows = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        Group root = new Group();
        root.getChildren().addAll(new Arrow());

        stage.setScene(new Scene( root, 400, 400, Color.ALICEBLUE));
        stage.show();
    }
    
    /*
    // a draggable anchor displayed around a point.
    class Anchor extends Circle {
        Anchor(Color color, DoubleProperty x, DoubleProperty y) {
            super(x.get(), y.get(), 10);
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);

            x.bind(centerXProperty());
            y.bind(centerYProperty());
            enableDrag();
        }

        // make a node movable by dragging it around with the mouse.
        private void enableDrag() {
            final Delta dragDelta = new Delta();
            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // record a delta distance for the drag and drop operation.
                    dragDelta.x = getCenterX() - mouseEvent.getX();
                    dragDelta.y = getCenterY() - mouseEvent.getY();
                    getScene().setCursor(Cursor.MOVE);
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getScene().setCursor(Cursor.HAND);
                }
            });
            setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    double newX = mouseEvent.getX() + dragDelta.x;
                    if (newX > 0 && newX < getScene().getWidth()) {
                        setCenterX(newX);
                    }
                    double newY = mouseEvent.getY() + dragDelta.y;
                    if (newY > 0 && newY < getScene().getHeight()) {
                        setCenterY(newY);
                    }

                    // update arrow positions
                    for (ArrowHead arrow : arrows) {
                        arrow.update();
                    }
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        getScene().setCursor(Cursor.HAND);
                    }
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        getScene().setCursor(Cursor.DEFAULT);
                    }
                }
            });
        }

        // records relative x and y co-ordinates.
        private class Delta {

            double x, y;
        }
    }*/
}
