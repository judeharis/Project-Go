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
					
					
					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
					
					
					if(e.isTheres(S1)) retval+=1000;
					if(e.countThere(TL,TR,LT,RT,RB,BL,BR) > 5) retval+=3000;
				}
				
			}
			
		}
		


		
		return retval;

	}

	
	
}
