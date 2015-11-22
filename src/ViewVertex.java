
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ViewVertex extends Group {
    Vertex vertex;
    Circle c;
    Text text;
    float R = 20;

    ViewVertex(Vertex v) {
        Point pt;
        Positions pos = new Positions();
        vertex = v;
        vertex.setView(this);

        pt = pos.get(v.getId());
        c = new Circle(pt.x, pt.y, R);
        c.setFill(Color.YELLOW);
        c.setStroke(Color.BLACK);

        text = new Text(pt.x - (v.getId() > 10 ? 5 : 3), pt.y + 5, "" + v.getId());
        text.setTextAlignment(TextAlignment.CENTER);

        this.getChildren().addAll(c, text);
    }

    public void use() {
        c.setFill(Color.ORANGE);
    }

    public void resetColor() {
        c.setFill(Color.YELLOW);
    }
}
