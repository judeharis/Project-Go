package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

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
					


					
					
					if (e.isThere(S0) || e.isThere(S1) || e.isThere(S3) || e.isThere(S4))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S2))patval=0;
					
					patval +=850;
					float b1 = States.borderSafeRel2(e, 2, A1,A2);
					float b2 = States.borderSafeRel2(e, 2, D1,D2);
					float b3 = States.borderSafeRel2(e, 1, S2);
					float ncap = States.minFinder(b1,b2,b3);
					if(ncap>0.5) patval+=850;
					else if(ncap<0.5) patval-=850;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S2))e.addToEye(S0,S1,S2,S3,S4);
					
					
//					retval +=900;
//					float b1 = States.borderSafe(e, 2, A1,A2);
//					float b2 = States.borderSafe(e, 2, D1,D2);
//					float b3 = States.borderSafe(e, 1, S2);
//					float ncap = States.minFinder(b1,b2,b3);
//					if(ncap>0.5) retval+=900;
//					else if(ncap<0.5) retval-=900;
					
					
				
					
					


				}

			}
			
		
		
		}


		
		return retval;

	}

	
	
}
