package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class PyramidFour {
	static ArrayList<Pattern> pyramidFourPattern = Pattern.sToPv2("xrxrxrdxdlxdlxluxlux");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pyramidFourPattern,e.kscolour);
		
		
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
					Tuple D0 = S1.side(side);		
					
					Tuple A1 = tlist.get(0).side(l);
					Tuple A2 = A1.side2(side,side);
					Tuple B1 = A2.side2(side,r);
					Tuple B2 = B1.side2(r,r);
					Tuple C1 = B2.side2(r,side.opp());
					Tuple C2 = C1.side2(side.opp(),side.opp());
					counter++;
					
					
					


//					if(!e.isTheres(A1,B1,C1) && !e.isTheres(A1,B1,C2) &&
//							!e.isTheres(A1,B2,C1) && !e.isTheres(A1,B2,C2) &&
//							!e.isTheres(A2,B1,C1) && !e.isTheres(A2,B1,C2) &&
//							!e.isTheres(A2,B2,C1) && !e.isTheres(A2,B2,C2))continue;
//					if(!e.isTheres(S1)) continue;
//					
//					String s = States.arrayToString(e,A1,A2,B1,B2,C1,C2,S0,S1,S2,D0);
					
					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
					retval +=700;
					float a = States.borderSafe(e, 2, A1,A2);
					float b = States.borderSafe(e, 2, B1,B2);
					float c = States.borderSafe(e, 2, C1,C2);
					float d = States.borderSafe(e, 1, S1);
					
					float ncap = States.minFinder(a,b,c,d);
					if(ncap>0.5) retval+=700;
					else if(ncap<0.5) retval-=700;

				}
				
			}
			
		}
		

		
		return retval;

	}

	
	
}
