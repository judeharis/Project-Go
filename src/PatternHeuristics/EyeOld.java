package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class EyeOld {
	Evaluator e;
	PatternSearcher ps;

	public EyeOld (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xdlxrrxldx", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple BL = TL.side2(side,side);
					Tuple BR = TR.side2(side,side);
					
//					Tuple T = tlist.get(0).side(side.opp());
//					Tuple L = TL.side2(side,l);
//					Tuple R = TR.side2(side,r);
//					Tuple B = BL.side2(side,r);
					
					
					Tuple C = tlist.get(0).side(side);
					
					
					if(e.isThere(C)) continue;
					
					

						
					if(e.isTheres(TL,TR,BL) || e.isTheres(TL,TR,BR) || e.isTheres(TL,BL,BR) || e.isTheres(TR,BL,BR))retval+=100;


						
						
					
					
					
//					if(e.isEnemy(TL) || e.isEnemy(TR) || e.isEnemy(BL) || e.isEnemy(BR)) {
//						
//						if(e.isEnemies(TL,TR,BL,BR)) retval -=0;
//						else if(e.isEnemies(TL,TR,BL)) retval -=0;
//						else if(e.isEnemies(TL,TR,BR)) retval -=0;
//						else if(e.isEnemies(TL,BL,BR)) retval -=0;
//						else if(e.isEnemies(TR,BL,BR)) retval -=0;
//						else if(e.isEnemies(TL,TR)) retval -=0;
//						else if(e.isEnemies(TL,BL)) retval -=0;
//						else if(e.isEnemies(TL,BR)) retval -=0;
//						else if(e.isEnemies(TR,BL)) retval -=0;
//						else if(e.isEnemies(TR,BR)) retval -=0;
//						else if(e.isEnemies(BL,BR)) retval -=0;
//						else if(e.isEnemy(TL)) {
//							retval -=0;
//							if(e.isTheres(TR,BL,BR)) retval+=100;
//							else if(e.isTheres(TR,BL)) retval+=50;
//							else if(e.isTheres(BL,BR)) retval+=50;
//							else if(e.isTheres(TR,BR)) retval+=50;
//							else if(e.isTheres(TR)) retval+=0;
//							else if(e.isTheres(BL)) retval+=0;
//							else if(e.isTheres(BR)) retval+=0;
//						}else if(e.isEnemy(TR)) {
//							retval -=0;
//							if(e.isTheres(TL,BL,BR)) retval+=100;
//							else if(e.isTheres(TL,BL)) retval+=50;
//							else if(e.isTheres(BL,BR)) retval+=50;
//							else if(e.isTheres(TL,BR)) retval+=50;
//							else if(e.isTheres(TL)) retval+=0;
//							else if(e.isTheres(BL)) retval+=0;
//							else if(e.isTheres(BR)) retval+=0;
//						}else if(e.isEnemy(BL)) {
//							retval -=0;
//							if(e.isTheres(TL,TR,BR)) retval+=100;
//							else if(e.isTheres(TL,TR)) retval+=50;
//							else if(e.isTheres(TR,BR)) retval+=50;
//							else if(e.isTheres(TL,BR)) retval+=50;
//							else if(e.isTheres(TL)) retval+=0;
//							else if(e.isTheres(TR)) retval+=0;
//							else if(e.isTheres(BR)) retval+=0;
//						}else if(e.isEnemy(BR)) {
//							retval -=0;
//							if(e.isTheres(TL,BL,TR)) retval+=100;
//							else if(e.isTheres(TL,BL)) retval+=50;
//							else if(e.isTheres(BL,TR)) retval+=50;
//							else if(e.isTheres(TL,TR)) retval+=50;
//							else if(e.isTheres(TL)) retval+=0;
//							else if(e.isTheres(BL)) retval+=0;
//							else if(e.isTheres(TR)) retval+=0;
//						}							
//					}else {
//						
//						if(e.isTheres(TL,TR))retval+=100;
//						else if(e.isTheres(TL,BL))retval+=100;
//						else if(e.isTheres(TL,BR))retval+=100;
//						else if(e.isTheres(TR,BL))retval+=100;
//						else if(e.isTheres(TR,BR))retval+=100;
//						else if(e.isTheres(BL,BR))retval+=100;
//						else if(e.isThere(TL) || e.isThere(TR) || e.isThere(BL) || e.isThere(BR)) retval+=50;
//
//						
//						
//					}

//					if(e.isEnemy(TL) || e.isEnemy(TR) || e.isEnemy(BL) || e.isEnemy(BR)) {
//						if(e.isEnemies(BL,BR) || e.isEnemies(TR,BR) || e.isEnemies(TL,BL) || e.isEnemies(TL,TR)) retval-=0;
//						else if(e.isEnemies(TL,BR) || e.isEnemies(TR,BL)) {
//
//							if(e.isEnemies(TL,BR) && (e.isThere(TR) || e.isThere(BL))) retval+=25;
//							
//							if(e.isEnemies(TR,BL) && (e.isThere(TL) || e.isThere(BR))) retval+=25;
//							
//							
//							if(e.isEnemies(TL,BR) && e.isTheres(TR,BL)) retval+=25;
//							
//							if(e.isEnemies(TR,BL) && e.isTheres(TL,BR)) retval+=25;
//						}else {
//
//							if(e.isEnemy(TL) && (e.isThere(TR) || e.isThere(BL))) retval+=25;
//											
//							if(e.isEnemy(TR) && (e.isThere(TL) || e.isThere(BR))) retval+=25;
//							
//							if(e.isEnemy(BL) && (e.isThere(TL) || e.isThere(BR))) retval+=25;
//							
//							if(e.isEnemy(BR) && (e.isThere(TR) || e.isThere(BL))) retval+=25;
//							
//							
//							if(e.isEnemy(TL) && e.isTheres(TR,BL)) retval+=50;
//							else if(e.isEnemy(TL) && (e.isTheres(BR,BL) || e.isTheres(BR,TR))) retval+=25;
//											
//							if(e.isEnemy(TR) && e.isTheres(TL,BR)) retval+=50;
//							else if(e.isEnemy(TR) && (e.isTheres(BL,BR) || e.isTheres(BL,TL))) retval+=25;
//							
//							if(e.isEnemy(BL) && e.isTheres(TL,BR)) retval+=50;
//							else if(e.isEnemy(BL) && (e.isTheres(TR,BR) || e.isTheres(TR,TL))) retval+=25;
//							
//							if(e.isEnemy(BR) && e.isTheres(TR,BL)) retval+=50;
//							else if(e.isEnemy(BR) && (e.isTheres(TL,BL) || e.isTheres(TL,TR))) retval+=25;
//							
//							
//							if(e.isEnemy(TL) && e.isTheres(TR,BL,BR)) retval+=25;
//							
//							if(e.isEnemy(TR) && e.isTheres(TL,BR,BL)) retval+=25;
//							
//							if(e.isEnemy(BL) && e.isTheres(TL,BR,TR)) retval+=25;
//							
//							if(e.isEnemy(BR) && e.isTheres(TR,BL,TL)) retval+=25;
//							
//						}			
//					}else {
//						if(e.isThere(TL) || e.isThere(TR) || e.isThere(BL) || e.isThere(BR)) retval+=50;
//						
//						if(e.isTheres(BL,BR) || e.isTheres(TL,TR) || e.isTheres(TL,BL)) retval+=50;
//						else if(e.isTheres(TR,BR) || e.isTheres(TL,BR) || e.isTheres(BL,TR)) retval+=50;
//						
//						
//					}
					

					

				}
				counter++;
			}
			
		}

		
		return retval;

	}

	
	
}
