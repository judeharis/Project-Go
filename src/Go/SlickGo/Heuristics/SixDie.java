package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SixDie {
	Evaluator e;
	PatternSearcher ps;

	public SixDie (Evaluator e){
		this.e=e;
	}


	

	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern6 = Pattern.sToPv2("xrxrxrxrxrxrozloddS",e.kscolour);	
		ArrayList<ArrayList<Tuple>>  pMatches =ps.allStringMatchv2(sstring, pattern6);
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if (!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
							
					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S0 = S1.side(l);
					Tuple Sn1 = S0.side(l);

					Tuple S2 = S1.side(r);
					Tuple S3 = S2.side(r);
					Tuple S6 = tlist.get((tlist.size() - 1)).side(side);
					Tuple S7 = S6.side(r);
					Tuple S8 = S7.side(r);
					Tuple S5 = S6.side(l);
					Tuple S4 = S5.side(l);
					
				
					
					if(e.isTheres(S1,S6))continue;
					
					//positive
					if (e.isThere(S1) || e.isThere(S6)) retval+=100;
					if (e.isThere(S1)) retval+=100;
					if (e.isThere(S6)) retval+=100;
					
//					if (e.isEnemy(S1) && e.isThere(S6)) retval+=100;
//					if (e.isEnemy(S6) && e.isThere(S1)) retval+=100;

					if (e.isTheres(S6,S2) && !e.isEnemies(S4)) retval+=100;
					if (e.isTheres(S1,S5) && !e.isEnemies(S3)) retval+=100;
					
					//negative
					if (e.isEnemy(S1) || e.isEnemy(S6)) retval-=100;
			
					if (e.isEnemies(S1,S7,S0)) retval-=100;
					if (e.isEnemies(S6,S0,S1))retval-=100;
			
					if (e.isEnemies(S1,S7) && (e.isThere(S6) || e.isThere(S8))  && !e.isThere(S0)) retval-=200;
					if (e.isEnemies(S6,S0) && (e.isThere(S1) || e.isThere(Sn1)) && !e.isThere(S7)) retval-=200;
						
						
				}
		
			}
		


		}
		return retval;
		
	}

	
}
