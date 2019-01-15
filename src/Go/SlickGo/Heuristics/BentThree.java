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
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrdxdxdlxlxluxurx", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(side);
					Tuple S0 = S1.side(l);

					Tuple A1 = tlist.get(0).side(l);
					Tuple A2 = tlist.get(0).side(r);
					Tuple B1 = S0.side2(side.opp(),l);
					Tuple B2 = B1.side2(side,side);

					
					

					if(e.isEnemies(A1,A2))continue;
					if(e.isEnemies(B1,B2))continue;

					
					
					
					
					
					if(e.isThere(S1)) retval+=500;
					if(e.isEnemies(S0,S2)) retval+=500;
					
					
					if(e.isEnemy(S1)) retval-=500;
					if(e.isTheres(S0) || e.isTheres(S2)) retval-=500;


				}
				counter++;
			}
			
		}
		


		
		return retval;

	}

	
	
}
