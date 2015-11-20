
import java.util.ArrayList;
import java.util.List;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;

public class Arrow extends Group{
    private static final float DISTANCE = 25;
    
    private int nb;
    
    public ArrowHead arrowHead;
    public CubicCurve curve;
    private float x1, y1, x2, y2;
    
    public Arrow(int x1, int y1, int x2, int y2, int offset) {
        nb = offset;
        curve = new CubicCurve();
        double[] arrowShape = new double[]{0, 0, 10, 20, -10, 20};
        init((float) x1, (float) y1, (float) x2, (float) y2);
        update();
        arrowHead = new ArrowHead(curve, 1f, arrowShape);
        getChildren().addAll(curve, arrowHead);
    }
    
    private void init(float x1, float y1, float x2, float y2)
    {
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
        if(nb == 0)
        {
            curve.setControlX1((x1+x2)/2);    //Calculated
            curve.setControlY1((y1+y2)/2);    //Calculated
            curve.setControlX2((x1+x2)/2);    //Calculated
            curve.setControlY2((y1+y2)/2);    //Calculated
        }
        else
        {
            float x=0, y=0;
            float mX = (1f*x1 + x2)/2;
            float mY = (1f*y1 + y2)/2;
            float p = (x2-x1)/(y2-y1);
            float b = mY -p*mX;
            float r = Math.abs(nb)*DISTANCE;
            float A = 1 + p*p;
            float B = 2 * (p * (b - mY )  - mX);
            float C = mX * mX + mY * mY + b * b - 2 * b * mY - r * r;
            float delta = B * B - 4 * A * C;
            System.out.println("p = " + p);
            if (delta > 0)
            {
                if(nb > 0)
                {
                    x = (-B - (float)Math.sqrt(delta)) / (2 * A);
                    y = p * x + b;
                }
                else
                {
                    x = (-B + (float)Math.sqrt(delta)) / (2 * A);
                    y = p * x + b;
                }
            }
            else if (delta == 0)
            {
                System.err.println("Error : delta = 0");
                x = -B / (2 * A);
                y = p * x + b;
            }
            else
                System.err.println("Error : delta < 0");
            curve.setControlX1(x);   //Calculated
            curve.setControlY1(y);   //Calculated
            curve.setControlX2(x);   //Calculated
            curve.setControlY2(y);   //Calculated
        }
    }
}
