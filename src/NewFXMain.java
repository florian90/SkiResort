
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

        stage.setScene(new Scene(root, Positions.WIDTH + 300, Positions.HEIGHT, Color.ALICEBLUE));
        stage.show();

        findPath(1, 9);

        /*Vertex last = null;
        Stack<Vertex> res = graph.shortestPath(1, 2, SkiLevel.Expert);
        System.out.println("Shortest path : ");
        while (!res.isEmpty()) {
            System.out.println(last = res.pop());
        }
        if (last != null) {
            System.out.println("Time needed : " + Math.round(last.getDist()) + " mins.");
        }
        Vertex v = graph.m_vertices.get(7-1);
        System.out.println("Time : " + v.getDist());*/
        
        //resetColor();
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
        if(res.size() == 0) 
        {
            System.out.println("No such path ");
        }
    }

    public void resetColor() {
        for (Edge e : graph.m_edges) {
            e.view.initColor();
        }
        for(Vertex v : graph.m_vertices)
        {
            v.view.resetColor();
        }
    }
}
