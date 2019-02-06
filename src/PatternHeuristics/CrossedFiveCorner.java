package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class CrossedFiveCorner {
	
	static ArrayList<Pattern> crossedFiveCornerPattern = Pattern.sToPv2("xrdxddXzdlxdlxrdx");

	
	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, crossedFiveCornerPattern,e.kscolour);
		

		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S2 = S0.side(side);
					Tuple S1 = S2.side(l);		
					Tuple S3 = S2.side(r);	
					Tuple S4 = S2.side(side);	
					Tuple A1 = tlist.get(0).side(l);
					Tuple A2 = A1.side2(r,r);
					Tuple D1 = S1.side2(l,side);
					Tuple D2 = D1.side2(side.opp(),side.opp());
					counter++;
					

					
//					if (e.isThere(S0) || e.isThere(S1) || e.isThere(S3) || e.isThere(S4))continue;
//					if(e.isTheres(S2)) retval+=1500;
//					if(e.isTheres(A1) || e.isTheres(A2)) retval+=750;
//					if(e.isTheres(D1) || e.isTheres(D2)) retval+=750;
//					
//					if(e.isEnemies(A1) && e.isEnemies(A2)) retval-=900;
//					if(e.isEnemies(D1) && e.isEnemies(D2)) retval-=900;

					
					
					if (e.isThere(S0) || e.isThere(S1) || e.isThere(S3) || e.isThere(S4))continue;
					retval +=900;
					float b1 = States.borderSafe(e, 2, A1,A2);
					float b2 = States.borderSafe(e, 2, D1,D2);
					float b3 = States.borderSafe(e, 1, S2);
					

					float ncap = States.minFinder(b1,b2,b3);
					if(ncap>0.5) retval+=900;
					else if(ncap<0.5) retval-=900;
					
					
				
					
					


				}

			}
			
		
		
		}


		
		return retval;

	}

	
	
}
