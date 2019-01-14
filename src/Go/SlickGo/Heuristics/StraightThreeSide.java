package Go.SlickGo.Heuristics;

import java.util.ArrayList;


import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightThreeSide  {
	Evaluator e;
	PatternSearcher ps;

	public StraightThreeSide (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		
		
		
//		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxdxurxrxdxzdxdS", e.kscolour);
//		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
//		
//		if(!pMatches.isEmpty()) for(ArrayList<Tuple> tlist: pMatches) if(!tlist.isEmpty()) retval+=1000;

		
		

		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxrxdxzdxdS", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;				
					UDLR r = side.diag(diagSide);
					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);
					Tuple S3 = S2.side(r);
					Tuple S4 = S3.side(r);

					if (e.isThere(S2)  || e.isThere(S4))continue;
					retval+=300;
					if (e.isThere(S3))retval += 700;
					if (e.isEnemy(S3))retval -= 300;			
				}
			}
		}
		
		
		pattern = Pattern.sToPv2("xrxrxdxurxrxddS", e.kscolour);
		pMatches =ps.allStringMatchv2(sstring, pattern);
		
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;			
					UDLR r = side.diag(diagSide);
					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);
					Tuple S3 = S2.side(r);
					Tuple S4 = S3.side(r);
					Tuple S5 = S4.side(r);

					if (e.isEnemy(S1) && e.isThere(S5))retval -= 100;
					if (e.isEnemy(S5) && e.isThere(S1))retval -= 100;
					
					if (e.isThere(S5))retval += 100;
					if (e.isThere(S1))retval += 100;
					
				}
			}
		}
		

		return retval;
	}


	
	
}
