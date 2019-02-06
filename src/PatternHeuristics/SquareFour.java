package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SquareFour {
	
	static ArrayList<Pattern> squareFourPattern = Pattern.sToPv2("xrxrdxdxdlxlxluxux");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, squareFourPattern,e.kscolour);

		
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
					Tuple D0 = S0.side(side);
					Tuple D1 = D0.side(r);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = S1.side2(side.opp(),r);
					Tuple BL = D0.side2(side,l);
					Tuple BR = D1.side2(side,r);
					counter++;



					if(e.isThere(S0)||e.isThere(S1)||e.isThere(D0)||e.isThere(D1)) continue;
					retval+=125;
					float b1 = States.borderSafe(e, 4, TL,TR,S0,S1);
					float b2 = States.borderSafe(e, 4, BL,BR,D0,D1);
					float b3 = States.borderSafe(e, 4, TL,BL,S0,D0);
					float b4 = States.borderSafe(e, 4, TR,BR,S1,D1);
					float b5 = States.borderSafe(e, 4, TL,TR,BL,BR);

					float ncap = States.minFinder(b1,b2,b3,b4,b5);
					if(ncap>0.5) retval+=25;
					else if(ncap<0.5) retval-=25;
				
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
