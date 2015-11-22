
public enum SkiLevel {
    Beginer, Intermediate, Advanced, Expert;

    public boolean isValid(Edge e) {
        boolean res = false;
        RoadType type = e.getType();
        switch (this) {
            case Expert:
                res |= type == RoadType.N;
                res |= type == RoadType.KL;
                res |= type == RoadType.SURF;
            case Advanced:
                res |= type == RoadType.R;
            case Intermediate:
                res |= type == RoadType.B;
            case Beginer:
                res |= type == RoadType.V;
                res |= type == RoadType.TK;
                res |= type == RoadType.TS;
                res |= type == RoadType.TSD;
                res |= type == RoadType.TPH;
                res |= type == RoadType.TC;
                res |= type == RoadType.BUS;
        }
        return res;
    }

    public static SkiLevel toValue(String str) {
        if (str.compareTo("Beginer") == 0) {
            return Beginer;
        } else if (str.compareTo("Intermediate") == 0) {
            return Intermediate;
        } else if (str.compareTo("Advanced") == 0) {
            return Advanced;
        } else if (str.compareTo("Expert") == 0) {
            return Expert;
        }
        return null;
    }
}

