package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;


public class StraightTwo {
	static ArrayList<Pattern> straightTwoPattern = Pattern.sToPv2("xrxrdxzldxdrxrx");

	public static int evaluate(ArrayList<Tuple> sstring,Evaluator e) {
		
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightTwoPattern,e.kscolour);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);
					Tuple BL = TL.side2(side,side);
					Tuple BR = TR.side2(side,side);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);
					counter++;		


					

					
					if (e.isThere(S1) || e.isThere(S2))continue;
					retval =75;
					float a = States.borderSafe(e, 2, TL,TR,BL,BR);
					float ncap = States.minFinder(a);

					if(ncap>0.5) retval+=75;
					else if(ncap<0.5) retval-=75;
					

					


					
	
						
					
				}
			}
		}
		
		
		return retval;
	}


	
	
}
