package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightTwoSide {
	Evaluator e;
	PatternSearcher ps;


	public StraightTwoSide (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
//		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxdxzdxdS", e.kscolour);
//		ArrayList<Tuple> bar3 =ps.stringMatch(sstring, pattern);
//		
//
//		if (!bar3.isEmpty()) {
//			boolean diagSide= ps.dirSideToBool();
//			UDLR side = ps.dirNumToDir();
//				Tuple S1 = bar3.get(0).side(side);
//				Tuple S2 = S1.side(side.diag(diagSide));
//				Tuple S3 = S2.side(side.diag(diagSide));
//
//				retval -= 200;
//				if (e.isThere(S2) && e.isThere(S3))retval -= 200;
//				else if (e.isThere(S2) || e.isThere(S3))retval -= 100;
//		}
//		
//
//		return retval;
		
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxdxzdxdS", e.kscolour);
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
					
					if (e.isThere(S2) || e.isThere(S3))continue;
					retval += 100;	
				}
			}
		}
		
		
		return retval;
	}


	
	
}
