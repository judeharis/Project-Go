package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.HeuristicI;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SideSixGap implements HeuristicI {
	Evaluator e;
	PatternSearcher ps;

	public SideSixGap (Evaluator e){
		this.e=e;
	}


	
	@Override
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxrxrxrxrxdxzdxdS", Stone.BLACK);
		ArrayList<Tuple> pString =ps.stringMatch(sstring, pattern);
		

		if (!pString.isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			Tuple S1 = pString.get(0).side(side);
			Tuple S2 = S1.side(side.diag(diagSide));
			Tuple S3 = S2.side(side.diag(diagSide));
			Tuple S4 = S3.side(side.diag(diagSide));
			Tuple S5 = S4.side(side.diag(diagSide));
			Tuple S6 = S5.side(side.diag(diagSide));
			Tuple S7 = S6.side(side.diag(diagSide));






			retval += 1500;
			if(e.isThere(S2) || e.isThere(S7)) return 0;

			if(e.isThere(S3))retval += 500;
			if(e.isThere(S4))retval += 1000;
			if(e.isThere(S5))retval += 1000;
			if(e.isThere(S6))retval += 500;
			
			return retval;
		}
		
		return retval;

	}

	
	
}
