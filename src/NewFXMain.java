
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class NewFXMain extends Application {

    Group root;
    Graph graph;
    ViewGraph viewGraph;

    boolean isColored = false;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        root = new Group();
        graph = new Graph("dataski.txt");
        viewGraph = new ViewGraph(graph);
        root.getChildren().addAll(viewGraph);

        final HBox hud_start = new HBox();
        final Label lb_start = new Label("Departure :");
        final TextField txt_start = new TextField();
        hud_start.setSpacing(10);
        hud_start.setLayoutX(Positions.WIDTH + 50);
        hud_start.setLayoutY(100);

        final HBox hud_end = new HBox();
        final Label lb_end = new Label("Arrival :      ");
        final TextField txt_end = new TextField();
        hud_end.setSpacing(10);
        hud_end.setLayoutX(Positions.WIDTH + 50);
        hud_end.setLayoutY(130);

        final Button bt_validate = new Button("Compute !");
        bt_validate.setLayoutX(Positions.WIDTH + 100);
        bt_validate.setLayoutY(170);
        bt_validate.setPrefWidth(100);
        bt_validate.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (!isColored) {
                    int start, end;

                    try {
                        start = Integer.parseInt(txt_start.getText());
                        end = Integer.parseInt(txt_end.getText());
                        bt_validate.setText("Erase path");
                        findPath(start, end);
                    } catch (Exception exep) {
                    }
                    txt_start.setText("");
                    txt_end.setText("");
                } else {
                    bt_validate.setText("Compute !");
                    resetColor();
                    txt_start.setText("");
                    txt_end.setText("");
                }
            }
        }
        );

        hud_start.getChildren().addAll(lb_start, txt_start);
        hud_end.getChildren().addAll(lb_end, txt_end);

        root.getChildren().addAll(bt_validate, hud_start, hud_end);
        stage.setScene(new Scene(root, Positions.WIDTH + 300, Positions.HEIGHT, Color.ALICEBLUE));
        stage.show();
    }

    public void findPath(int id_start, int id_end) {
        Vertex last = null;
        Stack<Vertex> res = graph.shortestPath(id_start, id_end, SkiLevel.Expert);
        for (Vertex v : res) {
            v.view.use();
            Vertex prev = v.getPrev();
            if (prev != null) {
                for (Edge e : prev.getOutList()) {
                    if (prev.getDist() + e.getTime() == v.getDist()) {
                        e.view.use();
                    }
                }
            }
        }
        if (res.size() == 0) {
            System.out.println("No such path ");
        } else {
            isColored = true;
        }
    }

    public void resetColor() {
        isColored = false;
        for (Edge e : graph.m_edges) {
            e.view.initColor();
        }
        for (Vertex v : graph.m_vertices) {
            v.view.resetColor();
        }
    }
}
