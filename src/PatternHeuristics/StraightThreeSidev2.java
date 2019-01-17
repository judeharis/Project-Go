package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightThreeSidev2 {
	Evaluator e;
	PatternSearcher ps;

	public StraightThreeSidev2 (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrdxzldxdS", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					counter++;
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					
					
					
					Tuple TL = tlist.get(0).side(l);

					Tuple TR = S2.side2(r,side.opp());


					if (e.isThere(S0) || e.isThere(S2))continue;
					

					
					retval +=200;
					
					if(e.isThere(TL) || e.isThere(TR) ||  e.isThere(S1)) {
						if(e.isTheres(TL,TR,S1))retval+=600;
						else if(e.isTheres(TL,TR)) {
							retval+=350;
							if(e.isEnemy(S1))retval-=350;
						}else if(e.isTheres(TR,S1)  || e.isTheres(TL,S1)) {
							retval+=200;
							if(e.isEnemy(TL) || e.isEnemy(TR)) retval-=400;
						}else if(e.isThere(TL)) {
							if(e.isEnemies(TR,S2,S1)) retval-=200;
							else if(e.isEnemies(TR,S0,S2)) retval-=100;
							else if(e.isEnemies(TR,S1,S0)) retval+=0;
							else if(e.isEnemies(TR,S2)) retval-=150;
							else if(e.isEnemies(TR,S1)) retval-=100;
							else if(e.isEnemies(TR) || e.isEnemies(S2)) retval-=50;

						}else if(e.isThere(TR)) {
							if(e.isEnemies(TL,S0,S1)) retval-=200;
							else if(e.isEnemies(TL,S0,S2)) retval-=100;
							else if(e.isEnemies(TL,S1,S2)) retval+=0;
							else if(e.isEnemies(TL,S0)) retval-=150;
							else if(e.isEnemies(TL,S1)) retval-=100;
							else if(e.isEnemies(TL) || e.isEnemies(S0)) retval-=50;
		
						}else if(e.isThere(S1)) retval-=200;
						
						
					}else if(e.isEnemy(TL) || e.isEnemy(TR) ||  e.isEnemy(S1) || e.isEnemy(S0) ||  e.isEnemy(S2)) {
						if(e.isEnemies(TL,S0,S1) || e.isEnemies(TR,S2,S1) || e.isEnemies(TR,TL,S0) || e.isEnemies(TR,TL,S2)) retval-=200;
						else if(e.isEnemies(TL,TR,S1) || e.isEnemies(TL,S0,S2) || e.isEnemies(TR,S0,S2)) retval-=150;
						else if(e.isEnemies(TL,S1,S2) || e.isEnemies(TR,S0,S1)) retval-=100;
						else if(e.isEnemies(TL,S1,S2) || e.isEnemies(TR,S0,S1)) retval-=100;
						else if(e.isEnemies(TL,TR) || e.isEnemies(TL,S0) || e.isEnemies(TR,S2)) retval-=200;
						else if(e.isEnemies(TL,S1) || e.isEnemies(TR,S1) || e.isEnemies(TL,S2) || e.isEnemies(TR,S0)) retval-=150;
						else if(e.isEnemies(S0,S1) || e.isEnemies(S1,S2) || e.isEnemies(S0,S2)) retval-=100;
					}
					
			
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
