package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SquareFourCorner {
	Evaluator e;
	PatternSearcher ps;

	public SquareFourCorner (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxzdlxdxrr#", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple D0 = S0.side(side);
					Tuple D1 = D0.side(r);
					
					
					
					if(e.isThere(S0)||e.isThere(S1)||e.isThere(D0)||e.isThere(D1)) continue;
					
					retval-=50;
					if(e.isEnemy(S0)||e.isEnemy(S1)||e.isEnemy(D0)||e.isEnemy(D1)) retval-=50;
					if(e.isEnemies(S0,D1)||e.isEnemies(S1,D0)) retval+=50;
					
					
//					if(e.isThere(S0))continue;
//					
//					
//					if(e.isThere(S1) || e.isThere(D0) || e.isThere(D1)) retval+=500;
//
//
//					if(e.isThere(D1) && !e.isThere(D0)  && (e.isEnemy(D0) || e.isEnemy(S1)))retval -=250;
//
//					if(e.isEnemies(D0,S1))retval -=250;
//					
//					if(e.isThere(S0) && e.isEnemy(D1)) retval -=500;
//					if(e.isThere(S1) && e.isEnemy(D0)) retval -=500;
//					if(e.isThere(D0) && e.isEnemy(S1)) retval -=500;
//					if(e.isThere(D1) && e.isEnemy(S0)) retval -=500;
//					
					




				}
				counter++;
			}
			
		}

		
		return retval;

	}

	
	
}
