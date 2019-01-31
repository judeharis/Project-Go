package PatternHeuristics;

import java.util.ArrayList;
import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightThreeSide {
	
	static ArrayList<Pattern> straightThreeSide1Pattern = Pattern.sToPv2("xrxrxrdxzldxdS");
	static ArrayList<Pattern> straightThreeSide2Pattern = Pattern.sToPv2("xrdxdxdxzdlxdxdxdS");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightThreeSide1Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = S2.side2(r,side.opp());
					counter++;


					if (e.isThere(S0) || e.isThere(S2))continue;
					if (e.isTheres(S1))retval+=700;
					if (e.isTheres(TL,TR))retval+=300;

					
			
				}
			}
			
		}
		
		
		
		pMatches =ps.allStringMatchv2(sstring, straightThreeSide2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					counter++;

					if (e.isThere(S0) || e.isThere(S2))continue;
					if (e.isThere(S1))retval+=700;
					if (e.isTheres(TL) || e.isThere(TR))retval+=300;

					

					
			
				}
				
			}
			
		}


		
		return retval;

	}

	
	
}
