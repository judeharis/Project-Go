package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BentFour {


	static ArrayList<Pattern> straightFourPattern = Pattern.sToPv2("xrxrxdrxdxdlxluxlxlux");

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
					Tuple S3 = S2.side(side);	
					Tuple TL = tlist.get(0).side(l);
					Tuple LB = TL.side2(side,side);
					Tuple TR = S2.side2(r,side.opp());
					Tuple BL = S3.side2(l,side);
					Tuple BR = S3.side2(r,side);
					counter++;
					

					

				
					if (e.isThere(S0) || e.isThere(S3))continue;
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafe(e, 6,TL,BL,BR,LB,S0,S3);
					float zcap = States.minFinder(z1);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					patval +=650;
					float b1 = States.borderSafe(e, 2, TL,LB);
					float b2 = States.borderSafe(e, 2, BL,BR);
					float b3 = States.borderSafe(e, 2, S1,S2);
					float b4 = States.borderSafe(e, 3, S2,TR,BL);
					float b5 = States.borderSafe(e, 3, S2,TR,BR);
					float ncap = States.minFinder(b1,b2,b3,b4,b5);
					if(States.oneCheck(b2,b4) || States.oneCheck(b2,b5) || States.oneCheck(b2,b3)) ncap = States.minFinder(ncap,0.5f);
					if(States.oneCheck(b3,b4) || States.oneCheck(b3,b5)) ncap = States.minFinder(ncap,0.5f);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 2, TL,LB);
//					float b2 = States.borderSafe(e, 2, BL,BR);
//					float b3 = States.borderSafe(e, 2, S1,S2);
//					float b4 = States.borderSafe(e, 3, S2,TR,BL);
//					float b5 = States.borderSafe(e, 3, S2,TR,BR);
//
//
//	
//					float ncap = States.minFinder(b1,b2,b3,b4,b5);
//					if(States.oneCheck(b2,b4) || States.oneCheck(b2,b5) || States.oneCheck(b2,b3)) ncap = States.minFinder(ncap,0.5f);
//					if(States.oneCheck(b3,b4) || States.oneCheck(b3,b5)) ncap = States.minFinder(ncap,0.5f);
//
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;



				}
				
			}
			
		}

		
		return retval;

	}

	
	
}