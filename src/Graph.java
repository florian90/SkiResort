import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Graph {
	public List<Vertex> m_vertices;
	public List<Edge> m_edges;

	public Graph(String fileName) {
		m_vertices = new ArrayList<Vertex>();
		m_edges = new ArrayList<Edge>();
		if (readFile(fileName) != 0)
			System.err.println("Error while loading the file");
	}

	private int readFile(String fileName) {
		int nb;
		String[] vertexLine = new String[3];
		String[] edgeLine = new String[5];
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			// Read the vertices
			nb = Integer.parseInt(reader.readLine());
			for (int i = 0; i < nb; i++) {
				vertexLine = reader.readLine().split("\t");
				m_vertices.add(new Vertex(Integer.parseInt(vertexLine[0]),
						vertexLine[1], Integer.parseInt(vertexLine[2])));
			}
			// Read the edges
			nb = Integer.parseInt(reader.readLine());
			for (int i = 0; i < nb; i++) {
				edgeLine = reader.readLine().split("\t");
				m_edges.add(new Edge(Integer.parseInt(edgeLine[0]),
						edgeLine[1], RoadType.parse(edgeLine[2]), m_vertices
								.get(Integer.parseInt(edgeLine[3]) - 1),
						m_vertices.get(Integer.parseInt(edgeLine[4]) - 1)));
			}
			reader.close();
		} catch (Exception e) {
			System.err.println(e.toString());
			return 1;
		}
		return 0;
	}

	public String toString() {
		String str = new String(m_vertices.size() + " vertices :\n");
		for (Vertex v : m_vertices)
			str += "  - " + v.toString() + "\n";
		str += m_edges.size() + " edges :\n";
		for (Edge e : m_edges)
			str += "  - " + e.toString() + "\n";
		return str;
	}

	public Vertex getVertexWithId(int id) {
		Vertex v = m_vertices.get(id - 1);
		if (v != null && v.getId() == id)
			return v;
		for (Vertex v2 : m_vertices)
			if (v2.getId() == id)
				return v2;
		return null;
	}

	private void resetMarks() {
		for (Vertex v : m_vertices) {
			v.setPrev(null);
			v.setDist(-1f);
			v.setMark(false);
		}
	}

	public Stack<Vertex> shortestPath(Vertex start, Vertex end, SkiLevel lvl) {
		Vertex act;
		Stack<Vertex> path = new Stack<Vertex>();
		List<Vertex> elems = new LinkedList<Vertex>();
		resetMarks();
		start.setDist(0);
		elems.add(start);
		while (!elems.isEmpty()) 
		{
			act = elems.get(0);
			for(Vertex v : elems)
			{
				if(act.compareTo(v) > 0 )
				{
					act = v;
				}
			}
			act.setMark(true);
			for (Edge neighbour : act.getOutList()) 
			{
				if(!neighbour.getArrival().getMark() && lvl.isValid(neighbour))
				{
					neighbour.updateNext();
					if (elems.contains(neighbour.getArrival()))
						elems.remove(neighbour.getArrival());
					elems.add(neighbour.getArrival());
				}
			}
			elems.remove(act);
		}
		act = end;
		while (act != null) {
			path.push(act);
			act = act.getPrev();
		}
		if(path.peek()!=start)
			return new Stack<Vertex>();
		return path;
	}

	public Stack<Vertex> shortestPath(int startId, int endId, SkiLevel lvl) {
		Vertex start = getVertexWithId(startId);
		Vertex end = getVertexWithId(endId);
		return shortestPath(start, end, lvl);
	}

	public Stack<Vertex> shortestPath(int startId, int endId) {
		return shortestPath(startId, endId, SkiLevel.Expert);
	}
}
