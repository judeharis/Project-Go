package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class TwistedFourCorner {

	
	static ArrayList<Pattern> twistedFourCorner1Pattern = Pattern.sToPv2("xrdxzldxdxrdxr#");
	static ArrayList<Pattern> twistedFourCorner2Pattern = Pattern.sToPv2("xldxldxdxrrX#");


	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, twistedFourCorner1Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S0 = S1.side(side);
					Tuple D0 = S0.side(r);		
					Tuple D1 = D0.side(side);	
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple BL = S0.side2(side,l);
					counter++;
					

					if (e.isThere(S1) || e.isThere(D1))continue;
					if (e.isTheres(S0,TR) || e.isTheres(D0,BL))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S0) || e.isThere(D0))patval=0;
					
					patval +=650;
					float b1 = States.borderSafeRel2(e, 2, TL,TR);
					float b2 = States.borderSafeRel2(e, 2, S0,D0);
					float b3 = States.borderSafeRel2(e, 2, S0,BL);
					float ncap = States.minFinder(b1,b2,b3);
					if(States.oneCheck(b2,b3)) ncap = States.minFinder(ncap,0.5f);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;

					retval+=patval;
					if(patval>=100 && !e.isThere(S0) && !e.isThere(D0))e.addToEye(S0,S1,D0,D1);
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 2, TL,TR);
//					float b2 = States.borderSafe(e, 2, S0,D0);
//					float b3 = States.borderSafe(e, 2, S0,BL);
//					float ncap = States.minFinder(b1,b2,b3);
//					if(States.oneCheck(b2,b3)) ncap = States.minFinder(ncap,0.5f);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;
					


					

					
				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, twistedFourCorner2Pattern,e.kscolour);
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
//					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S0 = S1.side(side);
					Tuple D0 = S0.side(l);		
					Tuple D1 = D0.side(side);	
					Tuple TL = tlist.get(0).side(l);
					Tuple LT = TL.side2(l,side);
					counter++;


					
					if (e.isThere(S1) || e.isThere(D1))continue;
					if (e.isTheres(TL,LT,S0) || e.isTheres(D0))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S0) || e.isThere(D0))patval=0;
					
					patval +=650;
					float b1 = States.borderSafeRel2(e, 1, TL);
					float b2 = States.borderSafeRel2(e, 2, LT,D0);
					float b3 = States.borderSafeRel2(e, 1, S0);
					float ncap = States.minFinder(b1,b2,b3);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;

					retval+=patval;
					if(patval>=100 && !e.isThere(S0) && !e.isThere(D0))e.addToEye(S0,S1,D0,D1);
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 1, TL);
//					float b2 = States.borderSafe(e, 2, LT,D0);
//					float b3 = States.borderSafe(e, 1, S0);
//					float ncap = States.minFinder(b1,b2,b3);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;
					



					
				}
				
			}
			
		}
		
		


		
		return retval;

	}

	
	
}
