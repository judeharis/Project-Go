package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BentThree {
	Evaluator e;
	PatternSearcher ps;

	public BentThree (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxdxdxdxlxlxlxuxuxrx", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatch(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					Tuple S0 = tlist.get(0).side2(side,side);
					Tuple S1 = S0.side(r);
					retval+=500;
					if(e.isThere(S1)) retval+=500;
					if(e.isEnemy(S1)) retval-=500;
				}
				counter++;
			}
			
		}

		
		return retval;

	}

	
	
}
