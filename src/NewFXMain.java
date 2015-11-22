
import java.util.Stack;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NewFXMain extends Application {

    private  Group root;
    private Graph graph;
    private ViewGraph viewGraph;

    boolean isColored = false;

    final Label lb_res = new Label("Result :\n");

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        root = new Group();
        graph = new Graph("dataski.txt");
        viewGraph = new ViewGraph(graph);
        root.getChildren().addAll(viewGraph);

        final Label lb_Title = new Label("Select a level\n and a path : ");
        lb_Title.setLayoutX(Positions.WIDTH + 80);
        lb_Title.setLayoutY(10);
        lb_Title.setFont(Font.font(20));

        final HBox hud_Level = new HBox();
        final Label lb_Level = new Label("Ski level :   ");
        ObservableList<String> levelOptions = FXCollections.observableArrayList("Beginer", "Intermediate", "Advanced", "Expert");
        final ComboBox cb_Level = new ComboBox(levelOptions);
        cb_Level.setValue("Beginer");
        hud_Level.setSpacing(10);
        hud_Level.setLayoutX(Positions.WIDTH + 50);
        hud_Level.setLayoutY(80);
        hud_Level.getChildren().addAll(lb_Level, cb_Level);

        final HBox hud_start = new HBox();
        final Label lb_start = new Label("Departure :");
        final TextField txt_start = new TextField();
        hud_start.setSpacing(10);
        hud_start.setLayoutX(Positions.WIDTH + 50);
        hud_start.setLayoutY(120);
        hud_start.getChildren().addAll(lb_start, txt_start);

        final HBox hud_end = new HBox();
        final Label lb_end = new Label("Arrival :      ");
        final TextField txt_end = new TextField();
        hud_end.setSpacing(10);
        hud_end.setLayoutX(Positions.WIDTH + 50);
        hud_end.setLayoutY(160);
        hud_end.getChildren().addAll(lb_end, txt_end);

        final HBox hud_but = new HBox();
        hud_but.setSpacing(10);
        hud_but.setLayoutX(Positions.WIDTH + 60);
        hud_but.setLayoutY(200);
        final Button bt_validate = new Button("Compute !");
        bt_validate.setPrefWidth(100);
        bt_validate.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                int start, end;
                try {
                    start = Integer.parseInt(txt_start.getText());
                    end = Integer.parseInt(txt_end.getText());
                    findPath(start, end, cb_Level.getValue().toString());
                } catch (Exception exep) {
                }

            }
        }
        );
        final Button bt_reset = new Button("reset");
        bt_reset.setPrefWidth(100);
        bt_reset.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                resetColor();
            }
        }
        );
        hud_but.getChildren().addAll(bt_validate, bt_reset);

        lb_res.setFont(Font.font(16));
        lb_res.setLayoutX(Positions.WIDTH + 50);
        lb_res.setLayoutY(250);

        final VBox hud_help = new VBox();
        hud_help.setSpacing(5);
        hud_help.setLayoutX(Positions.WIDTH + 40);
        hud_help.setLayoutY(Positions.HEIGHT - 300);

        final Label[] lb_help = new Label[7];
        lb_help[0] = new Label("Legend :\n");
        lb_help[0].setTextFill(Color.BLACK);
        lb_help[1] = new Label(" - Green slope\n");
        lb_help[1].setTextFill(Color.GREEN);
        lb_help[2] = new Label(" - Blue slope");
        lb_help[2].setTextFill(Color.BLUE);
        lb_help[3] = new Label(" - Red slope\n");
        lb_help[3].setTextFill(Color.RED);
        lb_help[4] = new Label(" - Black slope / Surf / KL\n");
        lb_help[4].setTextFill(Color.BLACK);
        lb_help[5] = new Label(" - Every ski lift\n");
        lb_help[5].setTextFill(Color.GREY);
        lb_help[6] = new Label(" - Slope used in the path\n");
        lb_help[6].setTextFill(Color.ORANGE);

        for (int i = 0; i < lb_help.length; i++) {
            lb_help[i].setFont(Font.font(18));
            hud_help.getChildren().add(lb_help[i]);
        }

        root.getChildren().addAll(lb_Title, hud_Level, hud_start, hud_end, hud_but, lb_res, hud_help);
        stage.setScene(new Scene(root, Positions.WIDTH + 300, Positions.HEIGHT, Color.ALICEBLUE));
        stage.show();
    }

    public void findPath(int id_start, int id_end, String skiLevel) {
        if (isColored) {
            resetColor();
        }
        Vertex last = null;
        String srt_res = "No such path";
        SkiLevel level = SkiLevel.toValue(skiLevel);
        if (level == null) {
            return;
        }
        Stack<Vertex> res = graph.shortestPath(id_start, id_end, level);
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
        if (!res.isEmpty()) {
            srt_res = "Result :\n";
            while (!res.isEmpty()) {
                last = res.pop();
                srt_res += " - " + last + "\n";
            }
            srt_res += "Time needed : " + Math.round(last.getDist()) + " mins.";
            isColored = true;
        }
        lb_res.setText(srt_res);
    }

    public void resetColor() {
        isColored = false;
        for (Edge e : graph.m_edges) {
            e.view.resetColor();
        }
        for (Vertex v : graph.m_vertices) {
            v.view.resetColor();
        }
        lb_res.setText("Result :\n");
    }
}
