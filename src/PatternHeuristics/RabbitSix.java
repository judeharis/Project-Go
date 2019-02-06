package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class RabbitSix {


	static ArrayList<Pattern> rabbitSixPattern = Pattern.sToPv2("xrdxrdxldxldxlxzldxldxdx");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, rabbitSixPattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple A1 = tlist.get(0).side(side);
					Tuple S1 = A1.side(side);
					Tuple S0 = S1.side(l);		
					Tuple S2 = S1.side(r);	
					Tuple B1 = S1.side(side);	
					Tuple B0 = B1.side(l);	
					
					
					Tuple TL = A1.side2(u,l);
					Tuple TR = A1.side2(u,r);
					Tuple LT = A1.side2(l,l);		
					Tuple RT = A1.side2(r,r);		
					Tuple RB = S2.side2(r,side);
					Tuple BL = B0.side2(side,l);
					Tuple BR = S2.side2(side,side);	
					
					
					counter++;
					
					
//					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
//					if(e.isTheres(S1)) retval+=1000;
//					if(e.countThere(TL,TR,LT,RT,RB,BL,BR) > 5) retval+=3000;
					
					
					
					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
//					retval+=500;
//					if(e.isEnemy(S1)) retval-=500;
//					if(e.isThere(S1)) {
//						
//						int ncap=0;
//						boolean a = (States.borderSafe(e, 2, TL,BL) == 0);
//						boolean b = (States.borderSafe(e, 2, TR,BR) == 0);
//						boolean dead = a || b ;
//						ncap +=(States.borderSafe(e, 2, TL,BL) == 1)?1:0;
//						ncap += (States.borderSafe(e, 2, TR,BR) ==1)?1:0 ;
//		
//
//						if(ncap<1 && !dead) retval+=500;
//						else if(ncap>1 && !dead) retval-=500;
//						else if(dead) retval-=500;
//						
//					}

				}
				
			}
			
		}
		


		
		return retval;

	}

	
	
}
