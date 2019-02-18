package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class TwistedFourSide {

	
	static ArrayList<Pattern> twistedFourSide1Pattern = Pattern.sToPv2("xrdxdxldxdSzdlxdlxdx");
	static ArrayList<Pattern> twistedFourSide2Pattern = Pattern.sToPv2("xrxrdxdlxdSzdlxldx");
	
	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, twistedFourSide1Pattern,e.kscolour);
		
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
					Tuple D0 = S0.side(l);		
					Tuple D1 = D0.side(side);	
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple RB = S0.side2(side,r);
					Tuple LT = TL.side2(side,l);
					counter++;
					


					
					if (e.isThere(S1) || e.isThere(D1))continue;
					if (e.isTheres(D0,RB) || e.isTheres(S0,TL,LT))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S0) || e.isThere(D0))patval=0;
					
					patval +=650;
					float b1 = States.borderSafe(e, 2, TL,TR);
					float b2 = States.borderSafe(e, 2, LT,D0);
					float b3 = States.borderSafe(e, 2, RB,S0);
					float b4 = States.borderSafe(e, 2, S0,D0);
					float ncap = States.minFinder(b1,b2,b3,b4);
					if(States.oneCheck(b2,b4) || States.oneCheck(b3,b4)) ncap = States.minFinder(ncap,0.5f);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S0) && !e.isThere(D0))e.addToEye(S0,S1,D0,D1);
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 2, TL,TR);
//					float b2 = States.borderSafe(e, 2, LT,D0);
//					float b3 = States.borderSafe(e, 2, RB,S0);
//					float b4 = States.borderSafe(e, 2, S0,D0);
//					float ncap = States.minFinder(b1,b2,b3,b4);
//					if(States.oneCheck(b2,b4) || States.oneCheck(b3,b4)) ncap = States.minFinder(ncap,0.5f);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;
					
				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, twistedFourSide2Pattern,e.kscolour);
		
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
					Tuple D0 = S0.side(side);		
					Tuple D1 = D0.side(l);	
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);
					Tuple RB = TR.side2(side,side);
					Tuple LT = TL.side2(side,l);
					counter++;
					
					
					
					if (e.isThere(S1) || e.isThere(D1))continue;
					if (e.isTheres(LT,TL,D0) || e.isTheres(RB,S0))continue;
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafe(e, 5,LT,TR,RB,S1,D1);
					float zcap = States.minFinder(z1);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S0) || e.isThere(D0))patval=0;
					
					patval +=650;
					float b1 = States.borderSafe(e, 1, LT);
					float b2 = States.borderSafe(e, 2, S0,D0);
					float b3 = States.borderSafe(e, 2, TL,S0);
					float b4 = States.borderSafe(e, 2, D0,TR,RB);
					float ncap = States.minFinder(b1,b2,b3,b4);
					if(States.oneCheck(b2,b3) || States.oneCheck(b2,b4)) ncap = States.minFinder(ncap,0.5f);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S0) && !e.isThere(D0))e.addToEye(S0,S1,D0,D1);
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 1, LT);
//					float b2 = States.borderSafe(e, 2, S0,D0);
//					float b3 = States.borderSafe(e, 2, TL,S0);
//					float b4 = States.borderSafe(e, 2, D0,TR,RB);
//					float ncap = States.minFinder(b1,b2,b3,b4);
//					if(States.oneCheck(b2,b3) || States.oneCheck(b2,b4)) ncap = States.minFinder(ncap,0.5f);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;
					
					
				}
				
			}
			
		}
		


				

		
		return retval;

	}

	
	
}
