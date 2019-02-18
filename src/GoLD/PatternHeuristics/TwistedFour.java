package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

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
					

					
					
					if (e.isThere(S1) || e.isThere(D1))continue;
					if (e.isTheres(S0,BR,RB) || e.isTheres(D0,TL,LT) )continue;
					
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafe(e, 6,RB,TR,LT,BL,S1,D1);
					float zcap = States.minFinder(z1);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S0) || e.isThere(D0))patval=0;
					
					patval +=650;
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
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S0) && !e.isThere(D0))e.addToEye(S0,S1,D0,D1);
					
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 2, LT,BL);
//					float b2 = States.borderSafe(e, 2, TR,RB);
//					float b3 = States.borderSafe(e, 2, S0,D0);
//					float b4 = States.borderSafe(e, 3, D0,BR,LT);
//					float b5 = States.borderSafe(e, 3, D0,BR,BL);
//					float b6 = States.borderSafe(e, 3, S0,TL,TR);
//					float b7 = States.borderSafe(e, 3, S0,TL,RB);
//					float ncap = States.minFinder(b1,b2,b3,b4,b5,b6,b7);
//					if(States.oneCheck(b1,b3)  && States.numCheck(1.5, b4,b5)) ncap = States.minFinder(ncap,0.5f);
//					else if(States.oneCheck(b2,b3) && States.numCheck(1.5, b6,b7)) ncap = States.minFinder(ncap,0.5f);
//					else if(States.oneCheck(b3,b5) || States.oneCheck(b3,b6)) ncap = States.minFinder(ncap,0.5f);
//					else if(States.oneCheck(b3,b7) || States.oneCheck(b3,b4)) ncap = States.minFinder(ncap,0.5f);
//					else if(States.oneCheck(b1,b4) || States.oneCheck(b1,b5)) ncap = States.minFinder(ncap,0.5f);
//					else if(States.oneCheck(b2,b6) || States.oneCheck(b2,b7)) ncap = States.minFinder(ncap,0.5f);
//					else if(States.oneCheck(b4,b5) || States.oneCheck(b6,b7)) ncap = States.minFinder(ncap,0.5f);
//					if(States.oneCheck(b4,b5,b6,b7)) ncap = States.minFinder(ncap,0.5f);
//					
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;



				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
