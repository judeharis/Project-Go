package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class TwistedFour {


	static ArrayList<Pattern> twistedFourPattern = Pattern.sToPv2("xrxrdxldxldxlxluxrux");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, twistedFourPattern,e.kscolour);
		
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
					Tuple BL = LT.side2(side,side);
					Tuple BR = RB.side2(side,l);
					counter++;
					
//					if (e.isThere(S1) || e.isThere(D1))continue;
//					
//					if (!e.isThere(S0) && !e.isThere(D0))continue;
//					
//					if (!e.isTheres(S0,TR,BR,BL) && !e.isTheres(S0,TR,BR,LT) 
//							&& !e.isTheres(S0,RB,BR,BL) && !e.isTheres(S0,RB,BR,LT) 
//							&& !e.isTheres(D0,BL,TL,TR) && !e.isTheres(D0,BL,TL,RB) 
//							&& !e.isTheres(D0,LT,TL,TR) && !e.isTheres(D0,LT,TL,RB) 
//							&& !e.isTheres(D0,S0,BL,RB) && !e.isTheres(D0,S0,BL,TR)
//							&& !e.isTheres(D0,S0,LT,RB) && !e.isTheres(D0,S0,LT,TR))continue;
					
					
//					if (e.isThere(S1) || e.isThere(D1))continue;
//
//					if (e.isTheres(S0,D0)){
//						retval+=1400;
//						if(e.isTheres(BL,RB) || e.isTheres(BL,TR)
//								|| e.isTheres(LT,RB) || e.isTheres(LT,TR))retval+=600;
//					}else if (e.isThere(S0)){
//						retval+=1100;
//						if (e.isTheres(TR,BR,BL) || e.isTheres(TR,BR,LT) 
//								|| e.isTheres(RB,BR,BL) || e.isTheres(RB,BR,LT))retval+=900;
//					}else if (e.isThere(D0)){
//						retval+=1100;
//						if (e.isTheres(BL,TL,TR) || e.isTheres(BL,TL,RB) 
//								|| e.isTheres(LT,TL,TR) || e.isTheres(LT,TL,RB)) retval+=900;
//					}

					
					
					if (e.isThere(S1) || e.isThere(D1))continue;
					if (e.isTheres(S0,BR,RB) || e.isTheres(D0,TL,LT) )continue;
					retval +=600;
					float b1 = States.borderSafe(e, 2, LT,BL);
					float b2 = States.borderSafe(e, 2, TR,RB);
					float b3 = States.borderSafe(e, 2, S0,D0);
					float b4 = States.borderSafe(e, 3, D0,BR,LT);
					float b5 = States.borderSafe(e, 3, D0,BR,BL);
					float b6 = States.borderSafe(e, 3, S0,TL,TR);
					float b7 = States.borderSafe(e, 3, S0,TL,RB);

					float ncap = States.minFinder(b1,b2,b3,b4,b5,b6,b7);


					if(States.oneCheck(b1,b3)  && States.numCheck(1.5, b4,b5)) ncap = States.minFinder(ncap,0.5f);
					else if(States.oneCheck(b2,b3) && States.numCheck(1.5, b6,b7)) ncap = States.minFinder(ncap,0.5f);
					else if(States.oneCheck(b3,b5) || States.oneCheck(b3,b6)) ncap = States.minFinder(ncap,0.5f);
					else if(States.oneCheck(b3,b7) || States.oneCheck(b3,b4)) ncap = States.minFinder(ncap,0.5f);
					else if(States.oneCheck(b1,b4) || States.oneCheck(b1,b5)) ncap = States.minFinder(ncap,0.5f);
					else if(States.oneCheck(b2,b6) || States.oneCheck(b2,b7)) ncap = States.minFinder(ncap,0.5f);
					else if(States.oneCheck(b4,b5) || States.oneCheck(b6,b7)) ncap = States.minFinder(ncap,0.5f);
					if(States.oneCheck(b4,b5,b6,b7)) ncap = States.minFinder(ncap,0.5f);
					
					if(ncap>0.5) retval+=600;
					else if(ncap<0.5) retval-=600;



				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
