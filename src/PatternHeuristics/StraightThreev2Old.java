package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightThreev2Old {
	Evaluator e;
	PatternSearcher ps;

	public StraightThreev2Old (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrdxdlxlxlxlux", e.kscolour);
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
					Tuple BL = TL.side2(side,side);
					Tuple TR = S2.side2(r,side.opp());
					Tuple BR = TR.side2(side,side);

					
					


					if (e.isThere(S0) || e.isThere(S2))continue;
					

					if(e.isTheres(S1,TL,TR) || e.isTheres(S1,TL,BR)) retval+=800;
					else if(e.isTheres(S1,TR,BL)) retval+=800;
					else if(e.isTheres(S1,BL,BR)) retval+=800;
					
//					if(!e.isThere(TL) && !e.isThere(TR) && !e.isThere(BL) && !e.isThere(BR) && !e.isThere(S1)) retval+=600;

					
//					if(e.isThere(S1)) {
//						
//						retval+=200;
//						if(e.isTheres(TL,TR,BL,BR))retval+=0;
//						else if(e.isTheres(TL,TR,BL))retval+=0;
//						else if(e.isTheres(TL,TR,BR))retval+=0;
//						else if(e.isTheres(TL,BL,BR))retval+=0;
//						else if(e.isTheres(TR,BL,BR))retval+=0;
//						else if(e.isTheres(TL,TR)) {
//							retval+=0;
//						}else if(e.isTheres(TL,BL)) {
//							retval+=0;
//							if(e.isEnemies(TR,BR))retval-=400;
//							else if(e.isEnemies(TR) ||e.isEnemies(BR))retval-=400;
//						}else if(e.isTheres(TL,BR)) {
//							retval+=0;
//							if(e.isEnemies(TR,BL,S1))retval-=0;
//							else if(e.isEnemies(TR,BL,S2))retval-=0;
//							else if(e.isEnemies(TR,BL))retval-=0;
//							else if(e.isEnemies(TR,S1))retval-=0;
//							else if(e.isEnemies(TR,S2))retval-=0;
//							else if(e.isEnemies(BL,S1))retval-=0;
//							else if(e.isEnemies(BL,S2))retval-=0;
//							else if(e.isEnemies(TR) ||e.isEnemies(BL))retval-=0;
//						}else if(e.isTheres(TR,BR)) {
//							retval+=0;
//							if(e.isEnemies(TL,BL,S1))retval-=0;
//							else if(e.isEnemies(TL,BL,S2))retval-=0;
//							else if(e.isEnemies(TL,BL))retval-=0;
//							else if(e.isEnemies(TL,S1))retval-=0;	
//							else if(e.isEnemies(TL,S2))retval-=0;
//							else if(e.isEnemies(BL,S1))retval-=0;
//							else if(e.isEnemies(BL,S2))retval-=0;
//							else if(e.isEnemies(TL) ||e.isEnemies(BL))retval-=0;
//						}else if(e.isTheres(TR,BL)) {
//							retval+=0;
//							if(e.isEnemies(TL,BR,S2))retval-=0;
//							else if(e.isEnemies(TL,BR,S1))retval-=0;
//							else if(e.isEnemies(TL,BR))retval-=0;
//							else if(e.isEnemies(TL,S2))retval-=0;
//							else if(e.isEnemies(TL,S1))retval-=0;
//							else if(e.isEnemies(BR,S2))retval-=0;
//							else if(e.isEnemies(BR,S1))retval-=0;
//							else if(e.isEnemies(TL) ||e.isEnemies(BR))retval-=0;
//						}else if(e.isTheres(BL,BR)) {
//							retval+=0;
//							if(e.isEnemies(TL,TR))retval-=0;
//							else if(e.isEnemy(TL) || e.isEnemy(TR)) retval-=0;
//						}else if(e.isTheres(TL)) {
//							retval+=0;
//							if(e.isEnemies(TR,BL,BR,S1))retval-=0;
//							else if(e.isEnemies(TR,BL,BR,S2))retval-=0;
//							else if(e.isEnemies(TR,S1,BL))retval-=0;
//							else if(e.isEnemies(TR,S1,BR))retval-=0;
//							else if(e.isEnemies(TR,S2,BL))retval-=0;
//							else if(e.isEnemies(TR,S2,BR))retval-=0;
//							else if(e.isEnemies(TR,BL,BR))retval-=0;
//							else if(e.isEnemies(S1,BL,BR))retval-=0;
//							else if(e.isEnemies(S2,BL,BR))retval-=0;
//							else if(e.isEnemies(TR,S1))retval-=0;
//							else if(e.isEnemies(TR,S2))retval-=0;
//							else if(e.isEnemies(TR,BL))retval-=0;
//							else if(e.isEnemies(TR,BR))retval-=0;
//							else if(e.isEnemies(S1,BL))retval-=0;
//							else if(e.isEnemies(S1,BR))retval-=0;	
//							else if(e.isEnemies(S2,BL))retval-=0;
//							else if(e.isEnemies(S2,BR))retval-=0;
//							else if(e.isEnemies(BL,BR))retval-=0;							
//							else if(e.isEnemies(TR))retval-=0;
//							else if(e.isEnemies(S1))retval+=0;
//							else if(e.isEnemies(S2))retval+=0;
//							else if(e.isEnemies(BL))retval-=0;
//							else if(e.isEnemies(BR))retval-=0;
//						}
//						else if(e.isTheres(TR)) {
//							retval+=0;
//							if(e.isEnemies(TL,BR,BL,S2))retval-=0;
//							else if(e.isEnemies(TL,BR,BL,S1))retval-=0;
//							else if(e.isEnemies(TL,S2,BR))retval-=0;
//							else if(e.isEnemies(TL,S2,BL))retval-=0;
//							else if(e.isEnemies(TL,S1,BR))retval-=0;
//							else if(e.isEnemies(TL,S1,BL))retval-=0;
//							else if(e.isEnemies(TL,BR,BL))retval-=0;
//							else if(e.isEnemies(S2,BR,BL))retval-=0;
//							else if(e.isEnemies(S1,BR,BL))retval-=0;
//							else if(e.isEnemies(TL,S2))retval-=0;
//							else if(e.isEnemies(TL,S1))retval-=0;
//							else if(e.isEnemies(TL,BR))retval-=0;
//							else if(e.isEnemies(TL,BL))retval-=0;
//							else if(e.isEnemies(S2,BR))retval-=0;
//							else if(e.isEnemies(S2,BL))retval-=0;	
//							else if(e.isEnemies(S1,BR))retval-=0;
//							else if(e.isEnemies(S1,BL))retval-=0;
//							else if(e.isEnemies(BR,BL))retval-=0;							
//							else if(e.isEnemies(TL))retval-=0;
//							else if(e.isEnemies(S2))retval+=0;
//							else if(e.isEnemies(S1))retval+=0;
//							else if(e.isEnemies(BR))retval-=0;
//							else if(e.isEnemies(BL))retval-=0;
//						}
//						else if(e.isTheres(BL)) {
//							retval+=0;
//							if(e.isEnemies(TR,TL,BR,S1))retval-=0;
//							else if(e.isEnemies(TR,TL,BR,S2))retval-=0;
//							else if(e.isEnemies(TR,S1,TL))retval-=0;
//							else if(e.isEnemies(TR,S1,BR))retval-=0;
//							else if(e.isEnemies(TR,S2,TL))retval-=0;
//							else if(e.isEnemies(TR,S2,BR))retval-=0;
//							else if(e.isEnemies(TR,TL,BR))retval-=0;
//							else if(e.isEnemies(S1,TL,BR))retval-=0;
//							else if(e.isEnemies(S2,TL,BR))retval-=0;
//							else if(e.isEnemies(TR,S1))retval-=0;
//							else if(e.isEnemies(TR,S2))retval-=0;
//							else if(e.isEnemies(TR,TL))retval-=0;
//							else if(e.isEnemies(TR,BR))retval-=0;
//							else if(e.isEnemies(S1,TL))retval-=0;
//							else if(e.isEnemies(S1,BR))retval-=0;	
//							else if(e.isEnemies(S2,TL))retval-=0;
//							else if(e.isEnemies(S2,BR))retval-=0;
//							else if(e.isEnemies(TL,BR))retval-=0;							
//							else if(e.isEnemies(TR))retval-=0;
//							else if(e.isEnemies(S1))retval+=0;
//							else if(e.isEnemies(S2))retval+=0;
//							else if(e.isEnemies(TL))retval-=0;
//							else if(e.isEnemies(BR))retval-=0;
//
//						}
//						else if(e.isTheres(BR)) {
//							retval+=0;
//							if(e.isEnemies(TL,TR,BL,S2))retval-=0;
//							else if(e.isEnemies(TL,TR,BL,S1))retval-=0;
//							else if(e.isEnemies(TL,S2,TR))retval-=0;
//							else if(e.isEnemies(TL,S2,BL))retval-=0;
//							else if(e.isEnemies(TL,S1,TR))retval-=0;
//							else if(e.isEnemies(TL,S1,BL))retval-=0;
//							else if(e.isEnemies(TL,TR,BL))retval-=0;
//							else if(e.isEnemies(S2,TR,BL))retval-=0;
//							else if(e.isEnemies(S1,TR,BL))retval-=0;
//							else if(e.isEnemies(TL,S2))retval-=0;
//							else if(e.isEnemies(TL,S1))retval-=0;
//							else if(e.isEnemies(TL,TR))retval-=0;
//							else if(e.isEnemies(TL,BL))retval-=0;
//							else if(e.isEnemies(S2,TR))retval-=0;
//							else if(e.isEnemies(S2,BL))retval-=0;	
//							else if(e.isEnemies(S1,TR))retval-=0;
//							else if(e.isEnemies(S1,BL))retval-=0;
//							else if(e.isEnemies(TR,BL))retval-=0;							
//							else if(e.isEnemies(TL))retval-=0;
//							else if(e.isEnemies(S2))retval+=0;
//							else if(e.isEnemies(S1))retval+=0;
//							else if(e.isEnemies(TR))retval-=0;
//							else if(e.isEnemies(BL))retval-=0;
//						}
//
//						
//
//
//					}else if(e.isEnemy(S1)) {
//						
//					}else if(e.isEnemy(TL))retval-=0;
//					else if(e.isEnemy(TR))retval-=0;
//					else if(e.isEnemy(BL))retval-=0;
//					else if(e.isEnemy(BR))retval-=0;
//					else if(e.isEnemy(S0))retval-=0;
//					else if(e.isEnemy(S1))retval-=0;
//					else if(e.isEnemy(S2))retval-=0;
					
					
					
//					if(e.isThere(S1)) {
//						
//						
//						if(e.isEnemy(TL) || e.isEnemy(TR) || e.isEnemy(BL) || e.isEnemy(BR)) {
//							retval-=200;
//							if(e.isEnemies(TL,BL)||e.isEnemies(TR,BR)) retval-=400;
//							else if(e.isEnemies(TL,BR) || e.isEnemies(TL,TR) || e.isEnemies(BL,BR)||e.isEnemies(BL,TR)) retval-=400;
//							if(e.isEnemy(TL) && e.isThere(BL)) retval+=400;
//							if(e.isEnemy(TR) && e.isThere(BR)) retval+=400;
//							if(e.isEnemy(BL) && e.isThere(TL)) retval+=400;
//							if(e.isEnemy(BR) && e.isThere(TR)) retval+=400;
//							
//							
////							if(e.isEnemy(TL) && e.isThere(TR)) retval+=100;
////							if(e.isEnemy(TR) && e.isThere(TL)) retval+=100;
////							if(e.isEnemy(BL) && e.isThere(BR)) retval+=100;
////							if(e.isEnemy(BR) && e.isThere(BL)) retval+=100;
//							
//							
//						}else {
//							if(e.isThere(TL) || e.isThere(TR) || e.isThere(BL) || e.isThere(BR))retval+=200;
////							if(e.isTheres(TL,BR) ||e.isTheres(TR,BL) ||e.isTheres(TL,TR) ||e.isTheres(BL,BR)) retval+=100;
//							
//						}
//						
//						
//
//
//					}else if(e.isEnemy(S1)) {
//						retval-=400;
//						if(e.isEnemies(TL,BL,S0)||e.isEnemies(TR,BR,S2)) retval-=200;
//						else if(e.isEnemies(TL,BL)||e.isEnemies(TR,BR)) {
//							retval-=100;
//
//						}
//					}else if(e.isEnemy(TL))retval-=0;
//					else if(e.isEnemy(TR))retval-=0;
//					else if(e.isEnemy(BL))retval-=0;
//					else if(e.isEnemy(BR))retval-=0;
//					else if(e.isEnemy(S0))retval-=0;
//					else if(e.isEnemy(S1))retval-=0;
//					else if(e.isEnemy(S2))retval-=0;
//					
					
					


					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
