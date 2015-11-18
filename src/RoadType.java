public enum RoadType {
	TK, TS, TSD, TPH, TC, BUS, V, B, R, N, KL, SURF;

	public static RoadType parse(String str) {
		if (str.equals("TK")) {
			return TK;
		} else if (str.equals("TS")) {
			return TS;
		} else if (str.equals("TSD")) {
			return TSD;
		} else if (str.equals("TPH")) {
			return TPH;
		} else if (str.equals("TC")) {
			return TC;
		} else if (str.equals("BUS")) {
			return BUS;
		} else if (str.equals("V")) {
			return V;
		} else if (str.equals("B")) {
			return B;
		} else if (str.equals("R")) {
			return R;
		} else if (str.equals("N")) {
			return N;
		} else if (str.equals("KL")) {
			return KL;
		} else if (str.equals("SURF")) {
			return SURF;
		} else {
			System.err.println("Error while converting string to RoadType : invalid input");
			return null;
		}
	}
}
