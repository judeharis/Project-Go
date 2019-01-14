package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class PyramidFourSide {
	Evaluator e;
	PatternSearcher ps;

	public PyramidFourSide (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xdrxdrxruxruxuS", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;
					
					UDLR r = side.diag(diagSide);
					Tuple S0 = tlist.get(0).side(r);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple D0 = S1.side(side);


					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
					
					retval+=600;
					if(e.isThere(S1)) retval+=500;
					if(e.isEnemy(S1)) retval-=500;

				}
				
			}
			
		}
		
		
		pattern = Pattern.sToPv2("xrxrxrdxdlxdlSluxlux", e.kscolour);
		pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;
					
					UDLR r = side.diag(diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple D0 = S1.side(side);


					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
					
					retval+=600;
					if(e.isThere(S1)) retval+=500;
					if(e.isEnemy(S1)) retval-=500;

				}
				
			}
			
		}
		
		pattern = Pattern.sToPv2("xrxrxrdSdlxdlxluxlux", e.kscolour);
		pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;
					
					UDLR r = side.diag(diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple D0 = S1.side(side);


					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
					
					retval+=600;
					if(e.isThere(S1)) retval+=500;
					if(e.isEnemy(S1)) retval-=500;

				}
				
			}
			
		}
		

		
		return retval;

	}

	
	
}
