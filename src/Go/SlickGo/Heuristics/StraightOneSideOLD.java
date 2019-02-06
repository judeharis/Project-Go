package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightOneSideOLD  {
	Evaluator e;
	PatternSearcher ps;

	public StraightOneSideOLD (Evaluator e){
		this.e=e;
	}

	
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
//		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxdxzdxdS", e.kscolour);
//		ArrayList<Tuple> bar3 =ps.stringMatch(sstring, pattern);
//		
//		
//		if (!bar3.isEmpty()) {
//			boolean diagSide= ps.dirSideToBool();
//			UDLR side = ps.dirNumToDir();
//				Tuple S1 = bar3.get(0).side(side);
//				Tuple S2 = S1.side(side.diag(diagSide));
//				retval -= 200;
//				if (e.isEnemy(S2))retval -= 1000;
//		}
		

		
		
		
//		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxdxzdxdS", e.kscolour);
//		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
//
//		if(!pMatches.isEmpty()) {
//			int counter=0;
//			for(ArrayList<Tuple> tlist: pMatches) {
//				if(!tlist.isEmpty()) {
//					boolean diagSide= ps.dirSideToBool(counter);
//					UDLR side = ps.dirNumToDir(counter);
//					counter++;				
//					UDLR r = side.diag(diagSide);
//					
//					Tuple S1 = tlist.get(0).side(side);
//					Tuple S2 = S1.side(r);
//					retval += 100;
//					if (e.isThere(S2))retval -= 100;	
//				}
//			}
//		}
//		
		
		
//		ArrayList<Pattern> pattern = Pattern.sToPv2("xrdxzldxdS", e.kscolour);
//		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrdxzldxdS");
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern,e.kscolour);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;				
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					
					Tuple S1 = tlist.get(0).side(side);
					Tuple B1 = tlist.get(0).side(l);
					Tuple B3 = tlist.get(0).side(r);
					
					
					if (e.isThere(B1) || e.isThere(B3))retval += 100;	
					
					if (e.isEnemy(B1))retval -= 100;	
					if (e.isEnemy(B3))retval -= 100;	
					
					if (e.isThere(S1))retval -= 100;	
				}
			}
		}
		
		return retval;
	}

	
	
}
