package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class StraightThree {


	static ArrayList<Pattern> straightThreePattern = Pattern.sToPv2("xrxrxrdxdlxlxlxlux");

	public static int evaluate(ArrayList<Tuple> sstring,Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightThreePattern,e.kscolour);
		
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
					Tuple TL = tlist.get(0).side(l);
					Tuple BL = TL.side2(side,side);
					Tuple TR = S2.side2(r,side.opp());
					Tuple BR = TR.side2(side,side);
					counter++;
					
					


					if (e.isThere(S0) || e.isThere(S2))continue;
					
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafe(e, 4, TL,BL,S0,S1);
					float z2 = States.borderSafe(e, 4, TR,BR,S1,S2);
					float z3 = States.borderSafe(e, 1,S0);
					float z4 = States.borderSafe(e, 1,S2);
					float z5 = States.borderSafe(e, 4, TL,BL,TR,BR) + States.minFinder(z3,z4);
					float zcap = States.minFinder(z1,z2,z5);
					if(States.oneCheck(z1,z2) || States.oneCheck(z1,z5)) zcap = States.minFinder(zcap,0.5f);
					else if(States.oneCheck(z2,z5)) zcap = States.minFinder(zcap,0.5f);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1))patval=0;
		
					
					
					
					patval +=450;
					float a = States.borderSafe(e, 2, TL,BL);
					float b = States.borderSafe(e, 2, TR,BR);
					float c = States.borderSafe(e, 1, S1);
					float ncap = States.minFinder(a,b,c);
					if(ncap>0.5) patval+=450;
					else if(ncap<0.5) patval-=450;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2);
					
					
					
//					retval +=500;
//					float a = States.borderSafe(e, 2, TL,BL);
//					float b = States.borderSafe(e, 2, TR,BR);
//					float c = States.borderSafe(e, 1, S1);
//					float ncap = States.minFinder(a,b,c);
//					if(ncap>0.5) retval+=500;
//					else if(ncap<0.5) retval-=500;
					

					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
