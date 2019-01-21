package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightThreeCornerv2 {
	Evaluator e;
	PatternSearcher ps;


	public StraightThreeCornerv2 (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xldxdxdxr#", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;				

					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);


					
					if (e.isThere(S0) || e.isThere(S2))continue;
					
					retval += 200;	
					
					
					if(e.isEnemies(TL)) {
						retval-=50;
						if(e.isEnemies(S0)) {
							retval-=100;
							if(e.isEnemies(S1)) retval-=50;
							else if(e.isEnemies(S2)) retval+=50;
						}else if(e.isEnemies(S1)) {
							retval-=50;
							if(e.isEnemies(S2)) retval+=100;
						}else if(e.isEnemies(S2)) retval-=0;
						else if(e.isThere(S1))retval-=150;
					}else if(e.isEnemies(S0)) {
						retval-=100;
						if(e.isEnemies(S1)) {
							if(e.isThere(TL))retval+=100;
						}else if(e.isEnemies(S2)) {
							retval+=500;
							if(e.isThere(TL))retval+=400;
						}else if(e.isThere(TL))retval+=500;
					}else if(e.isEnemies(S1)) {
						if(e.isEnemies(S2)) retval-=0;	
					}else if(e.isEnemies(S2))retval-=0;
					else if(e.isThere(S1)) {
						retval+=200;
						if(e.isThere(TL))retval+=400;
					}else if(e.isThere(TL))retval+=400;
					
					
					
//					if(e.isEnemy(TL) || e.isEnemy(S0) || e.isEnemy(S1) || e.isEnemy(S2)) {
//						if(e.isEnemies(TL)) {
//							retval-=50;
//							if(e.isEnemies(TL,S0)) {
//								retval-=100;
//								if(e.isEnemies(TL,S0,S1)) retval-=50;
//								else if(e.isEnemies(TL,S0,S2)) retval+=50;
//							}else if(e.isEnemies(TL,S1)) {
//								retval-=50;
//								if(e.isEnemies(TL,S1,S2)) retval+=100;
//							}else if(e.isEnemies(TL,S2)) retval-=0;
//							else if(e.isThere(S1))retval-=150;
//						}else if(e.isEnemies(S0)) {
//							retval-=100;
//							if(e.isEnemies(S0,S1)) {
//								if(e.isThere(TL))retval+=100;
//							}else if(e.isEnemies(S0,S2)) {
//								retval+=500;
//								if(e.isThere(TL))retval+=400;
//							}else if(e.isThere(TL))retval+=500;
//						}else if(e.isEnemies(S1)) {
//							if(e.isEnemies(S1,S2)) retval-=0;	
//						}else if(e.isEnemies(S2))retval-=0;
//					}else if(e.isThere(S1) || e.isThere(TL)) {
//						if(e.isThere(S1)) {
//							retval+=200;
//							if(e.isThere(TL))retval+=400;
//						}else if(e.isThere(TL))retval+=400;
//					}
					
					
				}
			}
		}
		
		

		
		
		return retval;
	}


	
	
}
