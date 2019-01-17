package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightTwo {
	Evaluator e;
	PatternSearcher ps;


	public StraightTwo (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrdxzldxdrxrx", e.kscolour);
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
					Tuple BL = TL.side2(side,side);
					Tuple BR = TR.side2(side,side);
					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);


					
					if (e.isThere(S1) || e.isThere(S2))continue;
					
					retval += 100;	
					
					if(e.isEnemy(TL) || e.isEnemy(TR) || e.isEnemy(BL) || e.isEnemy(BR)) {
						if(e.isEnemies(BL,BR,TL) || e.isEnemies(TR,BR,BL) || e.isEnemies(TL,BL,TR) || e.isEnemies(TL,TR,BR)) {
							retval-=100;
							if(e.isEnemies(TL,BL,S1) || e.isEnemies(TR,BR,S2))retval-=0;
							else if(e.isEnemies(TL,BL,S2) || e.isEnemies(TR,BR,S1))retval+=100; 
							else if(e.isThere(TL) || e.isThere(TR) || e.isThere(BL) || e.isThere(BR)) retval+=50;	
						}else if(e.isEnemies(TL,BL) || e.isEnemies(TR,BR)) {
							retval-=50;
							if(e.isEnemies(TL,BL,S1) || e.isEnemies(TR,BR,S2))retval-=50; 
							if(e.isEnemies(TL,BL,S2) || e.isEnemies(TR,BR,S1))retval+=50; 
						}else if(e.isEnemies(BL,BR) || e.isEnemies(TL,TR)) {
							retval-=50;
							if(e.isThere(TL) || e.isThere(TR) || e.isThere(BL) || e.isThere(BR)) retval+=50;	
						}else if(e.isEnemies(TL,BR) || e.isEnemies(TR,BL)) {
							retval-=50;
							if(e.isEnemies(TL,BR) && (e.isThere(TR) || e.isThere(BL))) retval+=75;
							
							if(e.isEnemies(TR,BL) && (e.isThere(TL) || e.isThere(BR))) retval+=75;
							
							
							if(e.isEnemies(TL,BR) && e.isTheres(TR,BL)) retval+=25;
							
							if(e.isEnemies(TR,BL) && e.isTheres(TL,BR)) retval+=25;
						}else {

							if(e.isEnemy(TL) && (e.isThere(TR) || e.isThere(BL))) retval+=25;
											
							if(e.isEnemy(TR) && (e.isThere(TL) || e.isThere(BR))) retval+=25;
							
							if(e.isEnemy(BL) && (e.isThere(TL) || e.isThere(BR))) retval+=25;
							
							if(e.isEnemy(BR) && (e.isThere(TR) || e.isThere(BL))) retval+=25;
							
							
							if(e.isEnemy(TL) && e.isTheres(TR,BL)) retval+=50;
							else if(e.isEnemy(TL) && (e.isTheres(BR,BL) || e.isTheres(BR,TR))) retval+=25;
											
							if(e.isEnemy(TR) && e.isTheres(TL,BR)) retval+=50;
							else if(e.isEnemy(TR) && (e.isTheres(BL,BR) || e.isTheres(BL,TL))) retval+=25;
							
							if(e.isEnemy(BL) && e.isTheres(TL,BR)) retval+=50;
							else if(e.isEnemy(BL) && (e.isTheres(TR,BR) || e.isTheres(TR,TL))) retval+=25;
							
							if(e.isEnemy(BR) && e.isTheres(TR,BL)) retval+=50;
							else if(e.isEnemy(BR) && (e.isTheres(TL,BL) || e.isTheres(TL,TR))) retval+=25;
							
							
							if(e.isEnemy(TL) && e.isTheres(TR,BL,BR)) retval+=25;
							
							if(e.isEnemy(TR) && e.isTheres(TL,BR,BL)) retval+=25;
							
							if(e.isEnemy(BL) && e.isTheres(TL,BR,TR)) retval+=25;
							
							if(e.isEnemy(BR) && e.isTheres(TR,BL,TL)) retval+=25;
							
						}			
					}else {
						if(e.isThere(TL) || e.isThere(TR) || e.isThere(BL) || e.isThere(BR)) retval+=50;
						
						if(e.isTheres(BL,BR) || e.isTheres(TL,TR) || e.isTheres(TL,BL)) retval+=50;
						else if(e.isTheres(TR,BR) || e.isTheres(TL,BR) || e.isTheres(BL,TR)) retval+=50;
						
						
					}
						
					
				}
			}
		}
		
		
		return retval;
	}


	
	
}
