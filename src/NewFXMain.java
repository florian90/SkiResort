
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class NewFXMain extends Application {

    Group root;
    Graph graph;
    ViewGraph viewGraph;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        root = new Group();
        graph = new Graph("dataski.txt");
        viewGraph = new ViewGraph(graph);
        root.getChildren().addAll(viewGraph);
        
        stage.setScene(new Scene(root, Positions.WIDTH, Positions.HEIGHT, Color.ALICEBLUE));
        stage.show();
    }

    public class ViewGraph extends Group {

        Graph graph;
        Group vertices;
        Group edges;

        ViewGraph(Graph gr) {
            graph = gr;
            init();
        }

        private void init() {
            vertices = new Group();
            edges = new Group();
            Positions pos = new Positions();
            Point pt;
            // Add vertices
            for (Vertex v : graph.m_vertices) {
                vertices.getChildren().add(new ViewVertex(v));
            }
            // Add edges
            for(Edge e : graph.m_edges){
                edges.getChildren().add(new Arrow(e));
            }
            
            getChildren().addAll(edges, vertices);
        }
    }

    public class ViewVertex extends Group {
        Vertex vertex;
        Circle c;
        Text text;
        float R = 20;

        ViewVertex(Vertex v) {
            Point pt;
            Positions pos = new Positions();
            vertex = v;

            pt = pos.get(v.getId());
            c = new Circle(pt.x, pt.y, R);
            c.setFill(Color.YELLOW);
            c.setStroke(Color.BLACK);
            
            text = new Text(pt.x-(v.getId() > 10 ? 5 : 3), pt.y+5, ""+v.getId());
            text.setTextAlignment(TextAlignment.CENTER);
            
            this.getChildren().addAll(c, text);
        }
    }
}
