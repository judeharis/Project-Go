package PatternHeuristics;

import java.util.ArrayList;
import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BentThree {
	
	static ArrayList<Pattern> bentThreePattern = Pattern.sToPv2("xrdxdxdlxlxluxurx");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, bentThreePattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(side);
					Tuple S0 = S1.side(l);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple LT = S0.side2(side.opp(),l);
					Tuple LB = LT.side2(side,side);
//					Tuple BR = S1.side2(side,r);
					counter++;

					
					

					if(e.isThere(S0) || e.isThere(S2))continue;
					if(e.isThere(S1)) retval+=700;
					if (e.isTheres(TL,LT) || e.isTheres(TL,LB)  || e.isTheres(TR,LT)  || e.isTheres(TR,LB)) retval+=300;

					

				}

			}
			
		}
		


		return (int) retval;

	}

	
	
}
