
import javafx.scene.Group;

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
        for (Edge e : graph.m_edges) {
            edges.getChildren().add(new Arrow(e));
        }

        getChildren().addAll(edges, vertices);
    }
}
