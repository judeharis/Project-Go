package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightSixSideOLD  {
	Evaluator e;
	PatternSearcher ps;

	public StraightSixSideOLD (Evaluator e){
		this.e=e;
	}


	
	
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
//		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxrxrxrxrxdxzdxdS", e.kscolour);
//		ArrayList<Tuple> pString =ps.stringMatch(sstring, pattern);
//		
//
//		if (!pString.isEmpty()) {
//			boolean diagSide= ps.dirSideToBool();
//			UDLR side = ps.dirNumToDir();
//			Tuple S1 = pString.get(0).side(side);
//			Tuple S2 = S1.side(side.diag(diagSide));
//			Tuple S3 = S2.side(side.diag(diagSide));
//			Tuple S4 = S3.side(side.diag(diagSide));
//			Tuple S5 = S4.side(side.diag(diagSide));
//			Tuple S6 = S5.side(side.diag(diagSide));
//			Tuple S7 = S6.side(side.diag(diagSide));
//
//
//
//
//
//
//			retval += 1500;
//			if(e.isThere(S2) || e.isThere(S7)) return 0;
//
//			if(e.isThere(S3))retval += 500;
//			if(e.isThere(S4))retval += 1000;
//			if(e.isThere(S5))retval += 1000;
//			if(e.isThere(S6))retval += 500;
//			
//			return retval;
//		}

		
		
//		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxrxrxrxrxdxzdxdS", e.kscolour);
//		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxrxrxrxrxdxzdxdS");
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern,e.kscolour);

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
					Tuple S6 = S5.side(r);
					Tuple S7 = S6.side(r);

					retval += 1500;
					if(e.isThere(S2) || e.isThere(S7)) continue;

					if(e.isThere(S3))retval += 500;
					if(e.isThere(S4))retval += 1000;
					if(e.isThere(S5))retval += 1000;
					if(e.isThere(S6))retval += 500;
				}
			}
		}
		
		
		return retval;

	}

	
	
}
