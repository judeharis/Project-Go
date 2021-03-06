package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class PyramidFourCorner {
	static ArrayList<Pattern> pyramidFourCorner1Pattern = Pattern.sToPv2("xdlxdlxzdrxd#");
	static ArrayList<Pattern> pyramidFourCorner2Pattern = Pattern.sToPv2("xrxrxzdlxdrxrrX");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pyramidFourCorner1Pattern,e.kscolour);
		
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple D1 = S0.side(side);
					Tuple D0 = D1.side(l);
					Tuple D2 = D1.side(r);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple LT = TL.side2(side,l);
					counter++;


//					if(e.isThere(S0)||e.isThere(D2)||e.isThere(D0)) continue;
//					if(!e.isTheres(TL) && !e.isTheres(TR))continue;
//					if(!e.isTheres(D1,LT)) continue;
					
					
					if(e.isThere(S0)||e.isThere(D2)||e.isThere(D0)) continue;
					
					
					int patval =0;	
					patval +=100;
					if(e.isThere(D1))patval=0;

					patval +=650;
					float a = States.borderSafeRel2(e, 2, TL,TR);
					float b = States.borderSafeRel2(e, 1, LT);
					float c = States.borderSafeRel2(e, 1, D1);
					
					float ncap = States.minFinder(a,b,c);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(D1))e.addToEye(S0,D0,D1,D2);
					
					
					
//					retval +=700;
//					float a = States.borderSafe(e, 2, TL,TR);
//					float b = States.borderSafe(e, 1, LT);
//					float c = States.borderSafe(e, 1, D1);
//					
//					float ncap = States.minFinder(a,b,c);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;

				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, pyramidFourCorner2Pattern,e.kscolour);
		
		
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
					Tuple TL = tlist.get(0).side(l);
					Tuple BL = TL.side2(side,side);
					counter++;

					
					
//					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
//					if(!e.isTheres(TL) && !e.isTheres(BL))continue;
//					if(!e.isTheres(S1)) continue;
					


					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
					
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;

					patval +=650;
					float a = States.borderSafeRel2(e, 2, TL,BL);
					float b = States.borderSafeRel2(e, 1, S1);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2,D0);
					
					
//					retval +=700;
//					float a = States.borderSafe(e, 2, TL,BL);
//					float b = States.borderSafe(e, 1, S1);
//					
//					float ncap = States.minFinder(a,b);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;

					
					

				}
				
			}
			
		}
		

		

		
		return retval;

	}

	
	
}
