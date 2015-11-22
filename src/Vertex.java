
import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {

    private int m_id;
    private String m_name;
    private int m_altitude;

    private List<Edge> m_inEdges;
    private List<Edge> m_outEdges;

    private float m_distance;
    private Vertex m_prevVertex;
    private boolean m_mark;

    public ViewVertex view;

    public Vertex(int id, String name, int altitude) {
        m_id = id;
        m_name = name;
        m_altitude = altitude;

        m_inEdges = new ArrayList<Edge>();
        m_outEdges = new ArrayList<Edge>();
        m_distance = 0f;
        m_prevVertex = null;
        m_mark = false;
    }

    public String toString() {
        return "Vertex " + m_id + ", " + m_name;
    }

    public void setView(ViewVertex v) {
        view = v;
    }

    public void addInEdge(Edge e) {
        m_inEdges.add(e);
    }

    public void addOutEdge(Edge e) {
        m_outEdges.add(e);
    }

    public int getId() {
        return m_id;
    }

    public int getAltitude() {
        return m_altitude;
    }

    public void setPrev(Vertex v) {
        m_prevVertex = v;
    }

    public Vertex getPrev() {
        return m_prevVertex;
    }

    public void setDist(float d) {
        m_distance = d;
    }

    public float getDist() {
        return m_distance;
    }

    public List<Edge> getOutList() {
        return m_outEdges;
    }

    public boolean getMark() {
        return m_mark;
    }

    public void setMark(boolean b) {
        m_mark = b;
    }

    @Override
    public int compareTo(Vertex v) {
        // TODO Auto-generated method stub
        if (m_distance < v.m_distance) {
            return -1;
        } else if (m_distance > v.m_distance) {
            return 1;
        }
        return 0;
    }
}
