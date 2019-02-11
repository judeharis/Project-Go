package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SquareFourSide {
	
	static ArrayList<Pattern> squareFourSidePattern = Pattern.sToPv2("xrxrdxdxzdlxdxdS");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, squareFourSidePattern,e.kscolour);
		
		
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
					counter++;


					
					if(e.isThere(S0)||e.isThere(S1)||e.isThere(D0)||e.isThere(D1)) continue;
					int patval =0;	
					patval+=125;
					float b1 = States.borderSafe(e, 4, TL,TR,S0,S1);
					float ncap = States.minFinder(b1);
					if(ncap>0.5) patval+=25;
					else if(ncap<0.5) patval-=25;
					retval+=patval;
					if(patval>=100)e.addToEye(S0,S1,D0,D1);
					
					
//					retval+=125;
//					float b1 = States.borderSafe(e, 4, TL,TR,S0,S1);
//					float ncap = States.minFinder(b1);
//					if(ncap>0.5) retval+=25;
//					else if(ncap<0.5) retval-=25;

					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
