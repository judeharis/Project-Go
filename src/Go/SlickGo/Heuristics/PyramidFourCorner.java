package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class PyramidFourCorner {
	Evaluator e;
	PatternSearcher ps;

	public PyramidFourCorner (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xdlxdlxzdrxd#", e.kscolour);
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
					Tuple D1 = S0.side(side);
					Tuple D0 = D1.side(l);
					Tuple D2 = D1.side(r);


					if(e.isThere(D0)||e.isThere(D2)||e.isThere(S0)) continue;
					
					retval+=600;
					if(e.isThere(D1)) retval+=500;
					if(e.isEnemy(D1)) retval-=500;

				}
				
			}
			
		}
		

		

		
		return retval;

	}

	
	
}
