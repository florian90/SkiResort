
public class Edge {
    private int m_id;
    private String m_name;
    private float m_time;
    private RoadType m_type;
    private Vertex m_from;
    private Vertex m_to;

    public Arrow view;

    public Edge(int id, String name, RoadType type, Vertex from, Vertex to) {
        m_id = id;
        m_name = name;
        m_type = type;
        m_from = from;
        m_to = to;
        from.addOutEdge(this);
        to.addInEdge(this);
        compTime();
    }

    @Override
    public String toString() {
        return m_name + ", " + m_name + ", " + m_type + ",t=" + m_time + " (" + m_from.getId() + "," + m_to.getId() + ")";
    }

    private void compTime() {
        int height = m_from.getAltitude() - m_to.getAltitude();
        if (height < 0) {
            height = -height;
        }
        switch (m_type) {
            case V:
                m_time = 1f * 5 / 100 * height;
                break;
            case B:
                m_time = 1f * 4 / 100 * height;
                break;
            case R:
                m_time = 1f * 3 / 100 * height;
                break;
            case N:
                m_time = 1f * 2 / 100 * height;
                break;
            case KL:
                m_time = 1f * 10 / 60 / 100 * height;
                break;
            case SURF:
                m_time = 1f * 10 / 100 * height;
                break;
            case TPH:
                m_time = 4 + 1f * 2 / 100 * height;
                break;
            case TC:
                m_time = 2 + 1f * 3 / 100 * height;
                break;
            case TSD:
                m_time = 1 + 1f * 3 / 100 * height;
                break;
            case TS:
            case TK:
                m_time = 1 + 1f * 4 / 100 * height;
                break;
            case BUS: {
                if (m_name.contains("2000"))
                {// arc 2000 <--> arc 1600 
                    m_time = 40f;
                } else
                {// arc 1800 <--> arc 1600
                    m_time = 30f;
                }
            }
        }
    }

    public void setView(Arrow a) {
        view = a;
    }

    public float getId() {
        return m_id;
    }

    public float getTime() {
        return m_time;
    }

    public Vertex getArrival() {
        return m_to;
    }

    public Vertex getDeparture() {
        return m_from;
    }

    public RoadType getType() {
        return m_type;
    }

    public void updateNext() {
        if (m_to.getDist() == -1 || m_to.getDist() > m_from.getDist() + m_time) {
            m_to.setDist(m_from.getDist() + m_time);
            m_to.setPrev(m_from);
        }
    }
}
