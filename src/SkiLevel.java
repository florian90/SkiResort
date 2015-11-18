
public enum SkiLevel {
	Beginer, Intermediate, Advanced, Expert;
	
	public boolean isValid(Edge e)
	{
		boolean res = false;
		RoadType type = e.getType();
		switch (this)
		{
		case Expert :
			res |= type==RoadType.N;
			res |= type==RoadType.KL;
			res |= type==RoadType.SURF;
		case Advanced :	
			res |= type==RoadType.R;
		case Intermediate :
			res |= type==RoadType.B;
		case Beginer :
			res |= type==RoadType.V;
			res |= type==RoadType.TK;
			res |= type==RoadType.TS;
			res |= type==RoadType.TSD;
			res |= type==RoadType.TPH;
			res |= type==RoadType.TC;
			res |= type==RoadType.BUS;
		}
		return res;
	}
}

//TK, TS, TSD, TPH, TC, BUS, V, B, R, N, KL, SURF;