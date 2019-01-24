package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;
import PatternHeuristics.States;

public class PyramidFour {
	Evaluator e;
	PatternSearcher ps;

	public PyramidFour (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrdxdlxdlxluxlux", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;
					
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple D0 = S1.side(side);
					
					
					Tuple A1 = tlist.get(0).side(l);
					Tuple A2 = A1.side2(side,side);
					Tuple B1 = A2.side2(side,r);
					Tuple B2 = B1.side2(r,r);
					Tuple C1 = B2.side2(r,side.opp());
					Tuple C2 = C1.side2(side.opp(),side.opp());
					
					

					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
					

//					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0) || e.isEnemies(C1,C2) ||e.isEnemies(B1,B2) ||e.isEnemies(A1,A2)) continue;
					
					String s = States.arrayToString(e,A1,A2,B1,B2,C1,C2,S0,S1,S2,D0);
					
				}
				
			}
			
		}
		
//		pattern = Pattern.sToPv2("xrxrxrxrxdxdxlxdxlxlxuxlxux", e.kscolour);
//		pMatches =ps.allStringMatch(sstring, pattern);
//		
//		if(!pMatches.isEmpty()) {
//			int counter=0;
//			for(ArrayList<Tuple> tlist: pMatches) {
//				if(!tlist.isEmpty()) {
//					boolean diagSide= ps.dirSideToBool(counter);
//					UDLR side = ps.dirNumToDir(counter);
//					counter++;
//					
//					UDLR r = side.diag(diagSide);
//					Tuple S0 = tlist.get(0).side2(side,r);
//					Tuple S1 = S0.side(r);
//					Tuple S2 = S1.side(r);
//					Tuple D0 = S1.side(side);
//
//
//					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
//					
//					retval+=500;
//					if(e.isEnemy(S1)) retval-=500;
//					if(e.isThere(S1)) retval+=5000;
//				}
//				
//			}
//			
//		}

		
		return retval;

	}

	
	
}
