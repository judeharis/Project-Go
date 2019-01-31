package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BulkyFive {


	static ArrayList<Pattern> bulkyFivePattern = Pattern.sToPv2("xrxrdxrdxdlxlxlxulxux");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, bulkyFivePattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple D0 = tlist.get(0).side(side);
					Tuple D1 = D0.side(r);
					Tuple S0 = D0.side(side);		
					Tuple S1 = S0.side(r);	
					Tuple S2 = S1.side(r);	
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = D1.side2(r,side.opp());
					Tuple RT = TR.side2(r,side);
					Tuple BL = S0.side2(l,side);
					Tuple BR = tlist.get(0).side(l);


					counter++;
					
					
//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					
//					
//					if(!e.isTheres(S1)) continue;
//					
//					retval+=3000;
					
					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					if(e.isTheres(S1)) retval+=2000;
					if(e.countThere(TL,TR,RT,BL,BR)>3) retval+= 1000;
					if(e.countEnemy(TL,TR,RT,BL,BR)>1) retval-= 2000;
					
				}
				
			}
			
		}
		


		
		return retval;

	}

	
	
}
