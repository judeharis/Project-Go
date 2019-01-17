package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightTwoSidev2 {
	Evaluator e;
	PatternSearcher ps;


	public StraightTwoSidev2 (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrdxzdlxdS", e.kscolour);
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
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);

					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);


					
					if (e.isThere(S1) || e.isThere(S2))continue;
					
					retval += 50;	
					
					if (e.isEnemy(TL) || e.isEnemy(TR)) {
						retval-=50;
						if (e.isEnemies(TL,S1) || e.isEnemies(TR,S2) || e.isEnemies(TL,TR))retval-=0;
						else if (e.isEnemies(TL,S2) || e.isEnemies(TR,S1)) {
							retval+=50;
							if (e.isThere(TL) || e.isThere(TR)) retval+=50;
						}else if (e.isThere(TL) || e.isThere(TR)) retval+=50;
					}else {
						if (e.isThere(TL) || e.isThere(TR)) retval+=100;
						if (e.isThere(TL) && e.isThere(TR)) retval+=50;
					}
					
					
				}
			}
		}
		
		
		pattern = Pattern.sToPv2("xrdxdxzldxdxdS", e.kscolour);
		pMatches =ps.allStringMatchv2(sstring, pattern);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;				
					UDLR r = side.diag(diagSide);

					UDLR l = side.diag(!diagSide);
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);

					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(side);


					
					if (e.isThere(S1) || e.isThere(S2))continue;
					
					retval += 100;	
					
					if (e.isEnemy(TL) || e.isEnemy(TR)) {

						if (e.isEnemies(TL,TR,S1))retval-=100;
						else if (e.isEnemies(TL,TR,S2)) retval+=0;
						else if (e.isEnemies(TL,TR) || e.isEnemies(TL,S1) || e.isEnemies(TR,S1)) {
							retval-=50;
							if (e.isThere(TL) || e.isThere(TR)) retval+=50;
						}else if (e.isEnemies(TL,S2) || e.isEnemies(TR,S2)) retval+=0;
						
					} else {
						if (e.isThere(TL) || e.isThere(TR)) retval+=50;
						if (e.isTheres(TL,TR)) retval+=50;
					}
					
					
				}
			}
		}
		
		
		return retval;
	}


	
	
}
