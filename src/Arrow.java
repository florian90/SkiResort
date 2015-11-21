
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;

public class Arrow extends Group {

    private final float DISTANCE = 25;

    private int nb;
    public ArrowHead arrowHead;
    public CubicCurve curve;
    private float x1, y1, x2, y2;

    private Color col;

    public boolean used = false;

    private Edge edge;

    private final double[] arrowShape = new double[]{0, 0, 10, 20, -10, 20};

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
        initColor();
        getChildren().addAll(curve, arrowHead);
    }

    public void initColor() {
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
        applyColor(col);
    }

    public void use() {
        applyColor(Color.ORANGE);
    }

    public void applyColor(Color color) {
        curve.setStroke(color);
        arrowHead.setColor(color);
    }

    public void applyColor() {
        curve.setStroke(col);
        arrowHead.setColor(col);
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
        curve.setStroke(Color.GREEN);
        curve.setStrokeWidth(3);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        curve.setFill(Color.TRANSPARENT);
    }

    // TODO : cas avec Y1 == Y2
    public void update() {
        if (nb == 0) {
            curve.setControlX1((x1 + x2) / 2);    //Calculated
            curve.setControlY1((y1 + y2) / 2);    //Calculated
            curve.setControlX2((x1 + x2) / 2);    //Calculated
            curve.setControlY2((y1 + y2) / 2);    //Calculated
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
            curve.setControlX1(x);   //Calculated
            curve.setControlY1(y);   //Calculated
            curve.setControlX2(x);   //Calculated
            curve.setControlY2(y);   //Calculated
        }
    }

    public class ArrowHead extends Polygon {

        public double rotate;
        public float t;
        CubicCurve curve;
        Rotate rz;

        public ArrowHead(CubicCurve curve, float t) {
            super();
            this.curve = curve;
            this.t = t;
            init();
        }

        public ArrowHead(CubicCurve curve, float t, double... arg0) {
            super(arg0);
            this.curve = curve;
            this.t = t;
            setStroke(Color.GREEN);
            setFill(Color.GREEN);
            init();
        }

        public void setColor(Color col) {
            setStroke(col);
            setFill(col);
        }

        private void init() {

            rz = new Rotate();
            {
                rz.setAxis(Rotate.Z_AXIS);
            }
            getTransforms().addAll(rz);

            update();
        }

        public void update() {
            double size = Math.max(curve.getBoundsInLocal().getWidth(), curve.getBoundsInLocal().getHeight());
            double scale = size / 4d;

            Point2D ori = eval(curve, t);
            Point2D tan = evalDt(curve, t).normalize().multiply(scale);

            setTranslateX(ori.getX());
            setTranslateY(ori.getY());

            double angle = Math.atan2(tan.getY(), tan.getX());

            angle = Math.toDegrees(angle);

            // arrow origin is top => apply offset
            double offset = -90;
            if (t > 0.5) {
                offset = +90;
            }
            rz.setAngle(angle + offset);
        }

        private Point2D eval(CubicCurve c, float t) {
            Point2D p = new Point2D(Math.pow(1 - t, 3) * c.getStartX()
                    + 3 * t * Math.pow(1 - t, 2) * c.getControlX1()
                    + 3 * (1 - t) * t * t * c.getControlX2()
                    + Math.pow(t, 3) * c.getEndX(),
                    Math.pow(1 - t, 3) * c.getStartY()
                    + 3 * t * Math.pow(1 - t, 2) * c.getControlY1()
                    + 3 * (1 - t) * t * t * c.getControlY2()
                    + Math.pow(t, 3) * c.getEndY());
            return p;
        }

        private Point2D evalDt(CubicCurve c, float t) {
            Point2D p = new Point2D(-3 * Math.pow(1 - t, 2) * c.getStartX()
                    + 3 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) * c.getControlX1()
                    + 3 * ((1 - t) * 2 * t - t * t) * c.getControlX2()
                    + 3 * Math.pow(t, 2) * c.getEndX(),
                    -3 * Math.pow(1 - t, 2) * c.getStartY()
                    + 3 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) * c.getControlY1()
                    + 3 * ((1 - t) * 2 * t - t * t) * c.getControlY2()
                    + 3 * Math.pow(t, 2) * c.getEndY());
            return p;
        }
    }
}
