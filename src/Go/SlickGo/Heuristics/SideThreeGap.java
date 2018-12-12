package Go.SlickGo.Heuristics;

import java.util.ArrayList;


import Go.SlickGo.Evaluator;
import Go.SlickGo.HeuristicI;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SideThreeGap implements HeuristicI {
	Evaluator e;
	PatternSearcher ps;

	public SideThreeGap (Evaluator e){
		this.e=e;
	}

	@Override
//	public int evaluate(ArrayList<Tuple> sstring) {
//		int retval = 0;
//
//		
//		ArrayList<Tuple> bar5 = e.checkStringForBar(sstring, 5,1);
//		if (!bar5.isEmpty()) {
//			Collections.sort(bar5);
//			UDLR side =e.distFromSide(bar5, 1);
//			if (!side.isEmpty()) {
//				Tuple S1 = bar5.get(0).side(side);
//				Tuple S5= bar5.get((bar5.size() - 1)).side(side);
//				Tuple S2 = S1.side(side.diag(false));
//				Tuple S3 = S2.side(side.diag(false));
//
//
//				if (e.isThere(S1) && e.isThere(S5)) {
//					retval -= 500;
//					if (e.isThere(S3))retval += 10000;
//					if (e.isEnemy(S3))retval -= 10000;
//				}}}
//		
//
//		return retval;
//	}
	
	
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxrxdxzdxdS", Stone.BLACK);
		ArrayList<Tuple> bar5 =ps.stringMatch(sstring, pattern);


		if (!bar5.isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			Tuple S1 = bar5.get(0).side(side);
			Tuple S2 = S1.side(side.diag(diagSide));
			Tuple S3 = S2.side(side.diag(diagSide));

			if (e.isThere(S3))retval += 10000;
			if (e.isEnemy(S3))retval -= 200;
			return retval;
		}
		
		pattern = Pattern.sToPv2("xrxrxdxurxrxddS", e.kscolour);
		bar5 =ps.stringMatch(sstring, pattern);
		
		if (!bar5.isEmpty()) {
			UDLR side = ps.dirNumToDir();
			boolean diagSide= ps.dirSideToBool();
			Tuple S1 = bar5.get(0).side(side);
			Tuple S2 = S1.side(side.diag(diagSide));
			Tuple S3 = S2.side(side.diag(diagSide));
			Tuple S4 = S3.side(side.diag(diagSide));
			Tuple S5 = S4.side(side.diag(diagSide));

			if (e.isTheres(S1,S5))retval += 10000;
			if (e.isEnemy(S1) && e.isThere(S5))retval -= 200;
			if (e.isEnemy(S5) && e.isThere(S1))retval -= 200;
			return retval;
		}
		
//		pattern = Pattern.sToPv2("xdxurxrxrxrxddS", e.kscolour);
//		bar5 =ps.stringMatch(sstring, pattern);
//		
//		if (!bar5.isEmpty()) {
//			UDLR side = ps.dirNumToDir();
//			boolean diagSide= ps.dirSideToBool();
//			Tuple S1 = bar5.get(0).side(side);
//			Tuple S2 = S1.side(side.diag(diagSide));
//			Tuple S3 = S2.side(side.diag(diagSide));
//			Tuple S4 = S3.side(side.diag(diagSide));
//			Tuple S5 = S4.side(side.diag(diagSide));
//
//			if (e.isTheres(S1,S5))retval += 10000;
//			if (e.isEnemy(S1) && e.isThere(S5))retval -= 200;
//			if (e.isEnemy(S5) && e.isThere(S1))retval -= 200;
//			return retval;
//		}

		
		

		return retval;
	}


	
	
}
