package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightTwoOld {
	Evaluator e;
	PatternSearcher ps;


	public StraightTwoOld (Evaluator e){
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
					


					if(!e.isThere(TL) && !e.isThere(TR) && !e.isThere(BL) && !e.isThere(BR)) retval+=100;
					if(e.isThere(TL) || e.isThere(TR) || e.isThere(BL) || e.isThere(BR)) {

						if(e.isTheres(TL,TR,BL,BR))retval+=200;
						else if(e.isTheres(TL,TR,BL))retval+=200;
						else if(e.isTheres(TL,TR,BR))retval+=200;
						else if(e.isTheres(TL,BL,BR))retval+=200;
						else if(e.isTheres(TR,BL,BR))retval+=200;
						else if(e.isTheres(TL,TR)) {
							retval+=200;
							if(e.isEnemies(BL,BR))retval-=100;
							else if(e.isEnemy(BL) || e.isEnemy(BR)) retval-=50;
						}else if(e.isTheres(TL,BL)) {
							retval+=200;
							if(e.isEnemies(TR,BR,S2))retval-=200;
							else if(e.isEnemies(TR,BR,S1))retval-=100;
							else if(e.isEnemies(TR,BR))retval-=150;
							else if(e.isEnemies(TR,S2))retval-=100;
							else if(e.isEnemies(BR,S2))retval-=100;
							else if(e.isEnemies(TR,S1))retval-=50;
							else if(e.isEnemies(BR,S1))retval-=50;
							else if(e.isEnemies(TR) ||e.isEnemies(BR))retval-=50;
						}else if(e.isTheres(TL,BR)) {
							retval+=200;
							if(e.isEnemies(TR,BL,S1))retval-=100;
							else if(e.isEnemies(TR,BL,S2))retval-=100;
							else if(e.isEnemies(TR,BL))retval-=100;
							else if(e.isEnemies(TR,S1))retval-=50;
							else if(e.isEnemies(TR,S2))retval-=50;
							else if(e.isEnemies(BL,S1))retval-=50;
							else if(e.isEnemies(BL,S2))retval-=50;
							else if(e.isEnemies(TR) ||e.isEnemies(BL))retval-=50;
						}else if(e.isTheres(TR,BR)) {
							retval+=200;
							if(e.isEnemies(TL,BL,S1))retval-=200;
							else if(e.isEnemies(TL,BL,S2))retval-=100;
							else if(e.isEnemies(TL,BL))retval-=150;
							else if(e.isEnemies(TL,S1))retval-=100;	
							else if(e.isEnemies(TL,S2))retval-=50;
							else if(e.isEnemies(BL,S1))retval-=100;
							else if(e.isEnemies(BL,S2))retval-=50;
							else if(e.isEnemies(TL) ||e.isEnemies(BL))retval-=50;
						}else if(e.isTheres(TR,BL)) {
							retval+=200;
							if(e.isEnemies(TL,BR,S2))retval-=100;
							else if(e.isEnemies(TL,BR,S1))retval-=100;
							else if(e.isEnemies(TL,BR))retval-=100;
							else if(e.isEnemies(TL,S2))retval-=50;
							else if(e.isEnemies(TL,S1))retval-=50;
							else if(e.isEnemies(BR,S2))retval-=50;
							else if(e.isEnemies(BR,S1))retval-=50;
							else if(e.isEnemies(TL) ||e.isEnemies(BR))retval-=50;
						}else if(e.isTheres(BL,BR)) {
							retval+=200;
							if(e.isEnemies(TL,TR))retval-=100;
							else if(e.isEnemy(TL) || e.isEnemy(TR)) retval-=50;
						}else if(e.isTheres(TL)) {
							retval+=100;
							if(e.isEnemies(TR,BL,BR,S1))retval-=0;
							else if(e.isEnemies(TR,BL,BR,S2))retval-=100;
							else if(e.isEnemies(TR,S1,BL))retval-=0;
							else if(e.isEnemies(TR,S1,BR))retval-=0;
							else if(e.isEnemies(TR,S2,BL))retval-=50;
							else if(e.isEnemies(TR,S2,BR))retval-=100;
							else if(e.isEnemies(TR,BL,BR))retval-=50;
							else if(e.isEnemies(S1,BL,BR))retval-=0;
							else if(e.isEnemies(S2,BL,BR))retval-=50;
							else if(e.isEnemies(TR,S1))retval-=0;
							else if(e.isEnemies(TR,S2))retval-=50;
							else if(e.isEnemies(TR,BL))retval-=0;
							else if(e.isEnemies(TR,BR))retval-=50;
							else if(e.isEnemies(S1,BL))retval-=0;
							else if(e.isEnemies(S1,BR))retval-=0;	
							else if(e.isEnemies(S2,BL))retval-=0;
							else if(e.isEnemies(S2,BR))retval-=50;
							else if(e.isEnemies(BL,BR))retval-=0;							
							else if(e.isEnemies(TR))retval-=0;
							else if(e.isEnemies(S1))retval+=50;
							else if(e.isEnemies(S2))retval+=50;
							else if(e.isEnemies(BL))retval-=0;
							else if(e.isEnemies(BR))retval-=0;
						}else if(e.isTheres(TR)) {
							retval+=100;
							if(e.isEnemies(TL,BR,BL,S2))retval-=0;
							else if(e.isEnemies(TL,BR,BL,S1))retval-=100;
							else if(e.isEnemies(TL,S2,BR))retval-=0;
							else if(e.isEnemies(TL,S2,BL))retval-=0;
							else if(e.isEnemies(TL,S1,BR))retval-=50;
							else if(e.isEnemies(TL,S1,BL))retval-=100;
							else if(e.isEnemies(TL,BR,BL))retval-=50;
							else if(e.isEnemies(S2,BR,BL))retval-=0;
							else if(e.isEnemies(S1,BR,BL))retval-=50;
							else if(e.isEnemies(TL,S2))retval-=0;
							else if(e.isEnemies(TL,S1))retval-=50;
							else if(e.isEnemies(TL,BR))retval-=0;
							else if(e.isEnemies(TL,BL))retval-=50;
							else if(e.isEnemies(S2,BR))retval-=0;
							else if(e.isEnemies(S2,BL))retval-=0;	
							else if(e.isEnemies(S1,BR))retval-=0;
							else if(e.isEnemies(S1,BL))retval-=50;
							else if(e.isEnemies(BR,BL))retval-=0;							
							else if(e.isEnemies(TL))retval-=0;
							else if(e.isEnemies(S2))retval+=50;
							else if(e.isEnemies(S1))retval+=50;
							else if(e.isEnemies(BR))retval-=0;
							else if(e.isEnemies(BL))retval-=0;
						}else if(e.isTheres(BL)) {
							retval+=100;
							if(e.isEnemies(TR,TL,BR,S1))retval-=0;
							else if(e.isEnemies(TR,TL,BR,S2))retval-=100;
							else if(e.isEnemies(TR,S1,TL))retval-=0;
							else if(e.isEnemies(TR,S1,BR))retval-=0;
							else if(e.isEnemies(TR,S2,TL))retval-=50;
							else if(e.isEnemies(TR,S2,BR))retval-=100;
							else if(e.isEnemies(TR,TL,BR))retval-=50;
							else if(e.isEnemies(S1,TL,BR))retval-=0;
							else if(e.isEnemies(S2,TL,BR))retval-=50;
							else if(e.isEnemies(TR,S1))retval-=0;
							else if(e.isEnemies(TR,S2))retval-=50;
							else if(e.isEnemies(TR,TL))retval-=0;
							else if(e.isEnemies(TR,BR))retval-=50;
							else if(e.isEnemies(S1,TL))retval-=0;
							else if(e.isEnemies(S1,BR))retval-=0;	
							else if(e.isEnemies(S2,TL))retval-=0;
							else if(e.isEnemies(S2,BR))retval-=50;
							else if(e.isEnemies(TL,BR))retval-=0;							
							else if(e.isEnemies(TR))retval-=0;
							else if(e.isEnemies(S1))retval+=50;
							else if(e.isEnemies(S2))retval+=50;
							else if(e.isEnemies(TL))retval-=0;
							else if(e.isEnemies(BR))retval-=0;

						}else if(e.isTheres(BR)) {
							retval+=100;
							if(e.isEnemies(TL,TR,BL,S2))retval-=0;
							else if(e.isEnemies(TL,TR,BL,S1))retval-=100;
							else if(e.isEnemies(TL,S2,TR))retval-=0;
							else if(e.isEnemies(TL,S2,BL))retval-=0;
							else if(e.isEnemies(TL,S1,TR))retval-=50;
							else if(e.isEnemies(TL,S1,BL))retval-=100;
							else if(e.isEnemies(TL,TR,BL))retval-=50;
							else if(e.isEnemies(S2,TR,BL))retval-=0;
							else if(e.isEnemies(S1,TR,BL))retval-=50;
							else if(e.isEnemies(TL,S2))retval-=0;
							else if(e.isEnemies(TL,S1))retval-=50;
							else if(e.isEnemies(TL,TR))retval-=0;
							else if(e.isEnemies(TL,BL))retval-=50;
							else if(e.isEnemies(S2,TR))retval-=0;
							else if(e.isEnemies(S2,BL))retval-=0;	
							else if(e.isEnemies(S1,TR))retval-=0;
							else if(e.isEnemies(S1,BL))retval-=50;
							else if(e.isEnemies(TR,BL))retval-=0;							
							else if(e.isEnemies(TL))retval-=0;
							else if(e.isEnemies(S2))retval+=50;
							else if(e.isEnemies(S1))retval+=50;
							else if(e.isEnemies(TR))retval-=0;
							else if(e.isEnemies(BL))retval-=0;
						}


							
									
					}else if(e.isEnemy(TL) || e.isEnemy(TR) || e.isEnemy(BL) || e.isEnemy(BR) || e.isEnemy(S1) || e.isEnemy(S2)) {

						if(e.isEnemy(TL)) {
							
							
							if(e.isEnemies(TR,S1,BL,BR))retval-=100;
							else if(e.isEnemies(TR,S2,BL,BR))retval-=100;
							else if(e.isEnemies(TR,S1,BL))retval-=100;
							else if(e.isEnemies(TR,S1,BR))retval-=50;
							else if(e.isEnemies(TR,S2,BL))retval-=50;
							else if(e.isEnemies(TR,S2,BR))retval-=100;
							else if(e.isEnemies(TR,BL,BR))retval-=100;
							else if(e.isEnemies(S1,BL,BR))retval-=100;
							else if(e.isEnemies(S2,BL,BR))retval-=50;
							
							else if(e.isEnemies(TR,S1))retval-=50;
							else if(e.isEnemies(TR,S2))retval-=50;
							else if(e.isEnemies(TR,BL))retval-=100;
							else if(e.isEnemies(TR,BR))retval-=100;
							else if(e.isEnemies(S1,BL))retval-=100;
							else if(e.isEnemies(S1,BR))retval-=100;	
							else if(e.isEnemies(S2,BL))retval-=0;
							else if(e.isEnemies(S2,BR))retval-=50;
							else if(e.isEnemies(BL,BR))retval-=100;
							
							else if(e.isEnemies(TR))retval-=50;
							else if(e.isEnemies(S1))retval-=50;
							else if(e.isEnemies(S2))retval-=0;
							else if(e.isEnemies(BL))retval-=100;
							else if(e.isEnemies(BR))retval-=100;

					

						}else if(e.isEnemy(TR)) {
							
							if(e.isEnemies(S1,BL,BR))retval-=50;
							else if(e.isEnemies(S2,BL,BR))retval-=100;
							
							else if(e.isEnemies(S1,BL))retval-=50;
							else if(e.isEnemies(S1,BR))retval-=0;
							else if(e.isEnemies(S2,BL))retval-=50;
							else if(e.isEnemies(S2,BR))retval-=100;
							else if(e.isEnemies(BL,BR))retval-=100;

							
							else if(e.isEnemies(S1))retval-=0;
							else if(e.isEnemies(S2))retval-=50;
							else if(e.isEnemies(BL))retval-=50;
							else if(e.isEnemies(BR))retval-=50;


						}
						else if(e.isEnemy(S1)) {
							
							if(e.isEnemies(BL,BR))retval-=50;
							
							else if(e.isEnemies(BL))retval-=50;
							else if(e.isEnemies(BR))retval-=0;
							
						}
						else if(e.isEnemy(S2)) {
							if(e.isEnemies(BL,BR))retval-=50;
							
							else if(e.isEnemies(BL))retval-=0;
							else if(e.isEnemies(BR))retval-=50;
						}else if(e.isEnemy(BR)) {
							if(e.isEnemy(BL))retval-=50;
						}
						

						
					}
					
					
//					if(e.isEnemy(TL) || e.isEnemy(TR) || e.isEnemy(BL) || e.isEnemy(BR)) {
//						if(e.isEnemies(BL,BR,TL) || e.isEnemies(TR,BR,BL) || e.isEnemies(TL,BL,TR) || e.isEnemies(TL,TR,BR)) {
//							retval-=100;
//							if(e.isEnemies(TL,BL,S1) || e.isEnemies(TR,BR,S2))retval-=0;
//							else if(e.isEnemies(TL,BL,S2) || e.isEnemies(TR,BR,S1))retval+=100; 
//							else if(e.isThere(TL) || e.isThere(TR) || e.isThere(BL) || e.isThere(BR)) retval+=50;	
//						}else if(e.isEnemies(TL,BL) || e.isEnemies(TR,BR)) {
//							retval-=50;
//							if(e.isEnemies(TL,BL,S1) || e.isEnemies(TR,BR,S2))retval-=50; 
//							if(e.isEnemies(TL,BL,S2) || e.isEnemies(TR,BR,S1))retval+=50; 
//						}else if(e.isEnemies(BL,BR) || e.isEnemies(TL,TR)) {
//							retval-=50;
//							if(e.isThere(TL) || e.isThere(TR) || e.isThere(BL) || e.isThere(BR)) retval+=50;	
//						}else if(e.isEnemies(TL,BR) || e.isEnemies(TR,BL)) {
//							retval-=50;
//							if(e.isEnemies(TL,BR) && (e.isThere(TR) || e.isThere(BL))) retval+=75;
//							
//							if(e.isEnemies(TR,BL) && (e.isThere(TL) || e.isThere(BR))) retval+=75;
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
			}
		}
		
		
		return retval;
	}


	
	
}
