package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;


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

					int patval =0;	
					patval +=50;
					float z1 = States.borderSafeRel1(e, 3, TL,BL,S1);
					float z2 = States.borderSafeRel1(e, 3, TR,BR,S2);
					float z3 = States.borderSafeRel1(e, 4, TL,BL,TR,BR);
					float zcap = States.minFinder(z1,z2,z3);
					if(States.oneCheck(z1,z3) || States.oneCheck(z2,z3)) zcap = States.minFinder(zcap,0.5f);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					

					
					patval +=25;
					float a = States.borderSafe(e, 2, TL,TR,BL,BR);
					float ncap = States.minFinder(a);
					if(ncap>0.5) patval+=25;
					else if(ncap<0.5) patval-=25;
					
//					retval +=75;
//					float a = States.borderSafe(e, 2, TL,TR,BL,BR);
//					float ncap = States.minFinder(a);
//					if(ncap>0.5) retval+=75;
//					else if(ncap<0.5) retval-=75;
					
					
					retval+=patval;
					if(patval>=100)e.addToEye(S1,S2);
					

					


					
	
						
					
				}
			}
		}
		
		
		return retval;
	}


	
	
}
