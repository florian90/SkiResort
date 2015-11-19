
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Arrow extends CubicCurve{
    public ArrowHead arrowHead;
    
    public Arrow()
    {
        createStartingCurve();
        double[] arrowShape = new double[]{0, 0, 10, 20, -10, 20};
        arrowHead = new ArrowHead(this, 1f, arrowShape);
    }
    /*
    public void start(final Stage stage) throws Exception {

        //Anchor start = new Anchor(Color.PALEGREEN, curve.startXProperty(), curve.startYProperty());
        //Anchor end = new Anchor(Color.TOMATO, curve.endXProperty(), curve.endYProperty());

        Group root = new Group();
        root.getChildren().addAll(curve);//, start, end);


        arrows.add(new ArrowHead(curve, 0f, arrowShape));
        arrows.add(new ArrowHead(curve, 1f, arrowShape));
        root.getChildren().addAll(arrows);

        stage.setTitle("Cubic Curve Manipulation Sample");
        stage.setScene(new Scene(root, 400, 400, Color.ALICEBLUE));
        stage.show();
    }*/

    private void createStartingCurve() {
        setStartX(200);
        setStartY(50);
        setControlX1(150);
        setControlY1(150);
        setControlX2(150);
        setControlY2(150);
        setEndX(200);
        setEndY(250);
        setStroke(Color.FORESTGREEN);
        setStrokeWidth(4);
        setStrokeLineCap(StrokeLineCap.ROUND);
        setFill(Color.TRANSPARENT);
    }
}
