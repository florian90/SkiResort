
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
    Arrow a1;
    Group g;

    public static void main(String[] args) throws Exception {launch(args);}

    @Override
    public void start(final Stage stage) throws Exception {
        g = new Group();
        a1 = new Arrow(360, 101, 30, 100, 1);
        //Arrow a2 = new Arrow(30, 100, 360, 101, -1);
        g.getChildren().addAll(a1);
        //g.getChildren().addAll(a2);

        stage.setScene(new Scene( g, 1600, 900, Color.ALICEBLUE));
        stage.show();
    }
}
