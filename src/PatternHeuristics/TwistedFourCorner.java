package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

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
					
//					if (e.isThere(S1) || e.isThere(D1))continue;
//					if (!e.isTheres(S0,D0,TL) && !e.isTheres(S0,D0,TR))continue;


//					if (e.isThere(S1) || e.isThere(D1))continue;
//					if (e.isTheres(S0,D0)){
//						retval+=1500;
//						if(e.isTheres(TL) || e.isTheres(TR)) retval+=500;
//					}else if (e.isThere(S0)){
//						retval+=1400;
//
//						if(e.isTheres(TL,TR)) retval+=700;
//
//					}else if (e.isThere(D0)){
//						retval+=800;
//						if(e.isTheres(BL)) retval+=800;
//						if(e.isTheres(TL) || e.isThere(TR)) retval+=400;
//
//					}
					
					
					if (e.isThere(S1) || e.isThere(D1))continue;
					if (e.isTheres(S0,TR) || e.isTheres(D0,BL))continue;
					retval +=700;
					float b1 = States.borderSafe(e, 2, TL,TR);
					float b2 = States.borderSafe(e, 2, S0,D0);
					float b3 = States.borderSafe(e, 2, S0,BL);


					float ncap = States.minFinder(b1,b2,b3);

					
					if(States.oneCheck(b2,b3)) ncap = States.minFinder(ncap,0.5f);
					if(ncap>0.5) retval+=700;
					else if(ncap<0.5) retval-=700;
					


					

					
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

					
//					if (e.isThere(S1) || e.isThere(D1))continue;
//					if (e.isTheres(S0,D0)){
//						retval+=1700;
//						if(e.isTheres(TL)) retval+=300;
//					}else if (e.isThere(D0)){
//						retval+=1400;
//						if(e.isTheres(TL)) retval+=700;
//					}else if (e.isThere(S0)){
//						retval+=1400;
//						if(e.isTheres(TL,LT)) retval+=700;
//					}
					
					if (e.isThere(S1) || e.isThere(D1))continue;
					if (e.isTheres(TL,LT,S0) || e.isTheres(D0))continue;
					
					retval +=700;
					float b1 = States.borderSafe(e, 1, TL);
					float b2 = States.borderSafe(e, 2, LT,D0);
					float b3 = States.borderSafe(e, 1, S0);


					float ncap = States.minFinder(b1,b2,b3);

					if(ncap>0.5) retval+=700;
					else if(ncap<0.5) retval-=700;
					



					
				}
				
			}
			
		}
		
		


		
		return retval;

	}

	
	
}
