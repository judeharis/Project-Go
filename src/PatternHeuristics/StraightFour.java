package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightFour {


	static ArrayList<Pattern> straightFourPattern = Pattern.sToPv2("xrxrxrxrdxdlxlxlxlxlux");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightFourPattern,e.kscolour);
		
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
					Tuple S3 = S2.side(r);	
					Tuple TL = tlist.get(0).side(l);
					Tuple BL = TL.side2(side,side);
					Tuple TR = S3.side2(r,side.opp());
					Tuple BR = TR.side2(side,side);
					counter++;
					
					
					if (e.isThere(S0) || e.isThere(S3))continue;
					retval +=600;
					float a = States.borderSafe(e, 2, TL,BL);
					float b = States.borderSafe(e, 2, TR,BR);
					float c = States.borderSafe(e, 2, S1,S2);
					float ncap = States.minFinder(a,b,c);
					if(ncap>0.5) retval+=600;
					else if(ncap<0.5) retval-=600;
					


			
				

					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
