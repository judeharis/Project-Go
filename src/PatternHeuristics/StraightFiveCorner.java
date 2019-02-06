package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightFiveCorner {


	static ArrayList<Pattern> straightFiveCornerPattern = Pattern.sToPv2("xdlxdxdxdxdxr#");

	
	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightFiveCornerPattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);		
					Tuple S3 = S2.side(side);	
					Tuple S4 = S3.side(side);	
					Tuple TL = tlist.get(0).side(l);
					counter++;
					
					

					
					
//					if (e.isThere(S0) || e.isThere(S4))continue;
//					if (e.isTheres(S1) || e.isTheres(S2) || e.isThere(S3))retval +=1000;
//					if (e.isTheres(TL))retval +=2000;
					
					
					if (e.isThere(S0) || e.isThere(S4))continue;
					retval +=700;
					float a = States.borderSafe(e, 1, TL);
					float b = States.borderSafe(e, 3, S1,S2,S3);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) retval+=700;
					else if(ncap<0.5) retval-=700;

				

					
				}
				
			}
			
		}
		

		
		return retval;

	}

	
	
}
