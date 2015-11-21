
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
    
    private Edge e;
    
    private final double[] arrowShape = new double[]{0, 0, 10, 20, -10, 20};

    public Arrow(int x1, int y1, int x2, int y2, int offset) {
        nb = offset;
        curve = new CubicCurve();
        init((float) x1, (float) y1, (float) x2, (float) y2);
        update();
        arrowHead = new ArrowHead(curve, 1f, arrowShape);
        getChildren().addAll(curve, arrowHead);
    }
    
    public Arrow(Edge e) {
        
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
        curve.setStroke(Color.FORESTGREEN);
        curve.setStrokeWidth(4);
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
            System.out.println("p = " + p);
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
            init();
        }

        private void init() {

            setFill(Color.GREEN);

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
