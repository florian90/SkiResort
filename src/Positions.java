
import java.util.ArrayList;

public class Positions extends ArrayList<Point> {
    public static float WIDTH = 800;
    public static float HEIGHT = 1000;

    private float initX = 1218;
    private float initY = 1342;

    public Positions() {
        add(0, 0);
        add(477, 1298); // 1
        add(551, 1158);
        add(715, 1096);
        add(782, 948);
        add(976, 936); // 5
        add(839, 803);
        add(559, 709);
        add(580, 839);
        add(1057, 577);
        add(628, 634); // 10
        add(823, 550);
        add(1009, 731);
        add(1173, 128);
        add(1046, 189);
        add(547, 522); // 15
        add(979, 358);
        add(447, 783);
        add(363, 988);
        add(334, 832);
        add(175, 878); // 20
        add(282, 743);
        add(652, 420);
        add(651, 323);
        add(491, 262);
        add(811, 68); // 25
        add(808, 212);
        add(81, 282);
        add(94, 684);
        add(43, 571);
        add(206, 601); // 30
        add(372, 471);
        add(642, 39);
        add(384, 68);
        add(220, 168);
        add(44, 435); // 35
        add(323, 223);
        add(526, 145);

        for (Point pt : this) {
            pt.x = pt.x * WIDTH / initX;
            pt.y = pt.y * HEIGHT / initY;
        }
    }

    private void add(float x, float y) {
        super.add(new Point(x, y));
    }
}
