
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

public class Arrow extends Group {
    private final float DISTANCE = 25;

    private int nb;
    private float x1, y1, x2, y2;
    private Color col;
    private Edge edge;
    private final double[] arrowShape = new double[]{0, 0, 10, 20, -10, 20};

    public boolean used = false;
    public ArrowHead arrowHead;
    public CubicCurve curve;
    
    public Arrow(Edge e) {
        Positions pos = new Positions();
        edge = e;
        e.setView(this);

        x1 = pos.get(e.getDeparture().getId()).x;
        y1 = pos.get(e.getDeparture().getId()).y;
        x2 = pos.get(e.getArrival().getId()).x;
        y2 = pos.get(e.getArrival().getId()).y;

        nb = 0;
        for (Edge edge : e.getDeparture().getOutList()) {
            if (edge == e) {
                break;
            }
            if (edge.getArrival() == e.getArrival()) {
                nb++;
            }
        }
        if (e.getArrival().getId() > e.getDeparture().getId()) {
            nb -= nb + 1;
        }
        curve = new CubicCurve();
        init(x1, y1, x2, y2);
        update();
        float dist, pc;
        dist = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        pc = (dist - 15) / dist;
        arrowHead = new ArrowHead(curve, pc, arrowShape);
        resetColor();
        getChildren().addAll(curve, arrowHead);
    }

    public void resetColor() {
        if (edge.getType() == RoadType.V) {
            col = Color.GREEN;
        } else if (edge.getType() == RoadType.B) {
            col = Color.BLUE;
        } else if (edge.getType() == RoadType.R) {
            col = Color.RED;
        } else if (edge.getType() == RoadType.N || edge.getType() == RoadType.KL || edge.getType() == RoadType.SURF) {
            col = Color.BLACK;
        } else {
            col = Color.GRAY;
        }
        setColor(col);
    }

    public void use() {
        setColor(Color.ORANGE);
    }

    public void setColor(Color color) {
        curve.setStroke(color);
        arrowHead.setColor(color); // col
    }

    private void init(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        curve.setStartX(x1);
        curve.setStartY(y1);
        curve.setEndX(x2);
        curve.setEndY(y2);
        curve.setStrokeWidth(3);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        curve.setFill(Color.TRANSPARENT);
    }

    // TODO : cas avec Y1 == Y2
    public void update() {
        if (nb == 0) {
            curve.setControlX1((x1 + x2) / 2);
            curve.setControlY1((y1 + y2) / 2);
            curve.setControlX2((x1 + x2) / 2);
            curve.setControlY2((y1 + y2) / 2);
        } else {
            float x = 0, y = 0;
            float mX = (1f * x1 + x2) / 2;
            float mY = (1f * y1 + y2) / 2;
            float p = (x2 - x1) / (y2 - y1);
            float b = mY - p * mX;
            float r = Math.abs(nb) * DISTANCE;
            float A = 1 + p * p;
            float B = 2 * (p * (b - mY) - mX);
            float C = mX * mX + mY * mY + b * b - 2 * b * mY - r * r;
            float delta = B * B - 4 * A * C;
            if (delta > 0) {
                if (nb > 0) {
                    x = (-B - (float) Math.sqrt(delta)) / (2 * A);
                    y = p * x + b;
                } else {
                    x = (-B + (float) Math.sqrt(delta)) / (2 * A);
                    y = p * x + b;
                }
            } else if (delta == 0) {
                System.err.println("Error : delta = 0");
                x = -B / (2 * A);
                y = p * x + b;
            } else {
                System.err.println("Error : delta < 0");
            }
            curve.setControlX1(x);
            curve.setControlY1(y);
            curve.setControlX2(x);
            curve.setControlY2(y);
        }
    }
}
