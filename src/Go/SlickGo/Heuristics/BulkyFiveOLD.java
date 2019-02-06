package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BulkyFiveOLD {
	Evaluator e;
	PatternSearcher ps;

	public BulkyFiveOLD (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
//		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrdxrdxdlxlxlxulxux", e.kscolour);
//		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrdxrdxdlxlxlxulxux");
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;
					
					UDLR r = side.diag(diagSide);
//					UDLR l = side.diag(!diagSide);
					
					Tuple B0 = tlist.get(0).side(side);
					Tuple B1 = B0.side(r);
					Tuple S0 = B0.side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);

					
					
					if(e.isThere(B0) || e.isThere(S2)) continue;
					retval+=200;
					if(e.isThere(S1)) retval+=1400;
					if(e.isThere(B1)) retval+=600;
					if(e.isThere(S0)) retval+=600;
					
					if(e.isEnemy(S1)) retval-=800;
					if(e.isEnemy(B1)) retval-=500;
					if(e.isEnemy(S0)) retval-=500;
					
					
					if(e.isTheres(S1,B1) || e.isTheres(S1,S0))retval+=100;
					else if(e.isTheres(B1,S0))retval+=500;
					
					if(e.isEnemies(S1,B1) || e.isEnemies(S1,S0))retval-=500;
					else if(e.isEnemies(B1,S0))retval-=500;

					
					

					
					
					
					


				}
				
			}
			
		}
		

		

		
		return retval;

	}

	
	
}
