package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

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
					
					
					

					
					
					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
					
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafe(e, 1,S0);
					float z2 = States.borderSafe(e, 1,S2);
					float z3 = States.borderSafe(e, 1,D0);
					float z4 = States.borderSafe(e, 6,A1,A2,B1,B2,C1,C2) +States.minFinder(z1,z2,z3);
					float zcap = States.minFinder(z4);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1))patval=0;
					
					patval +=650;
					float a = States.borderSafe(e, 2, A1,A2);
					float b = States.borderSafe(e, 2, B1,B2);
					float c = States.borderSafe(e, 2, C1,C2);
					float d = States.borderSafe(e, 1, S1);
					float ncap = States.minFinder(a,b,c,d);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2,D0);
					
					
					
					
//					retval +=700;
//					float a = States.borderSafe(e, 2, A1,A2);
//					float b = States.borderSafe(e, 2, B1,B2);
//					float c = States.borderSafe(e, 2, C1,C2);
//					float d = States.borderSafe(e, 1, S1);
//					
//					float ncap = States.minFinder(a,b,c,d);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;

				}
				
			}
			
		}
		

		
		return retval;

	}

	
	
}
