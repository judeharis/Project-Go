package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class StraightFive {


	static ArrayList<Pattern> straightFivePattern = Pattern.sToPv2("xrxrxrxrxrdxdlxlxlxlxlxlux");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightFivePattern,e.kscolour);
		
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
					Tuple S4 = S3.side(r);		
					Tuple TL = tlist.get(0).side(l);
					Tuple BL = TL.side2(side,side);
					Tuple TR = S4.side2(r,side.opp());
					Tuple BR = TR.side2(side,side);
					counter++;
				
					
					if (e.isThere(S0) || e.isThere(S4))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1) || e.isThere(S2) || e.isThere(S3))patval=0;
					
					patval +=850;
					float a = States.borderSafeRel2(e, 2, TL,BL);
					float b = States.borderSafeRel2(e, 2, TR,BR);
					float c = States.borderSafeRel2(e, 4, S1,S2,S3);
					float ncap = States.minFinder(a,b,c);
					if(ncap>0.5) patval+=850;
					else if(ncap<0.5) patval-=850;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2) && !e.isThere(S3))e.addToEye(S0,S1,S2,S3,S4);
					
					
					
//					retval +=900;
//					float a = States.borderSafe(e, 2, TL,BL);
//					float b = States.borderSafe(e, 2, TR,BR);
//					float c = States.borderSafe(e, 3, S1,S2,S3);
//					float ncap = States.minFinder(a,b,c);
//					if(ncap>0.5) retval+=900;
//					else if(ncap<0.5) retval-=900;
					
					

					

					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
