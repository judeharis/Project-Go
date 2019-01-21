package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BentThreev2 {
	Evaluator e;
	PatternSearcher ps;

	public BentThreev2 (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		double retval = 0;
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

					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple LT = S0.side2(side.opp(),l);
					Tuple LB = LT.side2(side,side);
					Tuple BR = S1.side2(side,r);

					
					

					if(e.isEnemies(TL,TR) || e.isEnemies(LT,LB) || e.isThere(S0) || e.isThere(S2))continue;

//					if(e.isThere(S0) || e.isThere(S2))continue;
					
					
					retval+=600;
					
					if(e.isThere(S1)) {
						retval+=300;
						if(e.isThere(TL) || e.isThere(TR) || e.isThere(LT) || e.isThere(LB)) {
							retval-=50;
							
							if(e.isThere(TL)) {
								
								if(e.isTheres(TL,TR,LT)) {
									retval-=450;
									if(e.isEnemy(LB)) retval+=12.5;
								}else if(e.isTheres(TL,TR,LB)) {
									retval-=50;
									if(e.isEnemy(LT)) retval+=50;
								}else if(e.isTheres(TL,LT,LB)) {
									retval-=450;
									if(e.isEnemy(TR)) retval+=12.5;
								}else if(e.isTheres(TL,TR)) {
									retval-=0;
									if(e.isEnemy(LB)) retval-=425;
									else if(e.isEnemy(LT)) retval-=400;
								}else if(e.isTheres(TL,LT)) {
									retval-=450;
									if(e.isEnemies(LB,TR)) retval+=25;
									else if(e.isEnemy(LB) || e.isEnemy(TR)) retval+=12.5;
								}else if(e.isTheres(TL,LB)) {
									retval-=50;
									if(e.isEnemies(TR,LT)) retval+=75;
									else if(e.isEnemy(TR)) retval+=25;
									else if(e.isEnemy(LT)) retval+=50;
								}else if(e.isEnemies(TR,LB)) retval-=400;
								else if(e.isEnemies(TR,LT)) retval-=375;
								else if(e.isEnemy(LB)) retval-=425;
								else if(e.isEnemy(LT)) retval-=400;
								else if(e.isEnemy(TR)) retval+=25;
								
								
							}else if(e.isThere(TR)) {
								
								if(e.isTheres(TR,LT,LB)) {
									retval-=50;
									if(e.isEnemy(TL)) retval+=50;
								}else if(e.isTheres(TR,LB)) {
									retval-=50;
									if(e.isEnemies(TL,LT)) retval+=100;
									else if(e.isEnemy(TL)) retval+=50;
									else if(e.isEnemy(LT)) retval+=50;
								}
								else if(e.isTheres(TR,LT)) {
									retval-=50;
									if(e.isEnemies(TL,LB)) retval+=75;
									else if(e.isEnemy(TL)) retval+=50;
									else if(e.isEnemy(LB)) retval+=25;
								}else if(e.isEnemies(TL,LB)) retval-=375;
								else if(e.isEnemies(TL,LT)) retval-=350;
								else if(e.isEnemy(LB)) retval-=425;
								else if(e.isEnemy(LT)) retval-=400;
								else if(e.isEnemy(TL)) retval+=50;
								
								
							}else if(e.isThere(LT)) {
								
								if(e.isTheres(LT,LB)) {
									retval-=0;
									if(e.isEnemy(TR)) retval-=425;
									else if(e.isEnemy(TL)) retval-=400;
								}else if(e.isEnemies(TL,LB)) retval-=375;
								else if(e.isEnemies(TR,LB)) retval-=400;
								else if(e.isEnemy(TR)) retval-=425;
								else if(e.isEnemy(TL)) retval-=400;
								else if(e.isEnemy(LB)) retval+=25;
								
								
								
							}else if(e.isThere(LB)) {
								
								if(e.isEnemies(TL,LT)) retval-=350;
								else if(e.isEnemies(TR,LT)) retval-=375;
								else if(e.isEnemy(TR)) retval-=425;
								else if(e.isEnemy(TL)) retval-=400;
								else if(e.isEnemy(LT)) retval+=50;
							}
						}else if(e.isThere(BR)) {
							if(e.isEnemies(TL,TR,LT)) retval-=0;
							else if(e.isEnemies(TL,TR,LB)) retval-=0;
							else if(e.isEnemies(TL,LT,LB)) retval-=0;
							else if(e.isEnemies(TR,LT,LB)) retval-=0;
							else if(e.isEnemies(TL,TR)) retval-=0;
							else if(e.isEnemies(TL,LT)) retval-=800;
							else if(e.isEnemies(TL,LB)) retval-=825;
							else if(e.isEnemies(TR,LT)) retval-=825;
							else if(e.isEnemies(TR,LB)) retval-=850;
							else if(e.isEnemies(LB,LT)) retval-=0;
							else if(e.isEnemy(TL)) retval-=400;
							else if(e.isEnemy(TR)) retval-=425;
							else if(e.isEnemy(LT)) retval-=400;
							else if(e.isEnemy(LB)) retval-=425;
							
						}
						
						
					}
					
					


				}
				counter++;
			}
			
		}
		


		return (int) retval;

	}

	
	
}
