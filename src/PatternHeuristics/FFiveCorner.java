package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class FFiveCorner {
	
	static ArrayList<Pattern> fFiveCorner1Pattern = Pattern.sToPv2("xrdxddXzldxldxdx");
	static ArrayList<Pattern> fFiveCorner2Pattern = Pattern.sToPv2("xrdxdxd#zldxldxrdx");
	static ArrayList<Pattern> fFiveCorner3Pattern = Pattern.sToPv2("xldxlxldxrdxrrX");
	static ArrayList<Pattern> fFiveCorner4Pattern = Pattern.sToPv2("xrxrdxddXzldxrdxdx");


	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, fFiveCorner1Pattern,e.kscolour);
		
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
//					UDLR u = side.opp();
					Tuple D0 = tlist.get(0).side(side);
					Tuple D1 = D0.side(r);
					Tuple S1 = D0.side(side);
					Tuple S0 = S1.side(l);
					Tuple S2 = S1.side(r);
					
//					Tuple TL = D0.side2(u,l);
					Tuple LT = D0.side2(l,l);

					counter++;
					
//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(e.isTheres(S1)) retval+=2000;
//					if(e.isTheres(TL,LT)) retval+=1000;
					

					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					retval +=500;
					float c1 = States.borderSafe(e, 2, S1,S2);
					float c2 = States.borderSafe(e, 2, S1,D0);
					float c3 = States.borderSafe(e, 2, LT,D1);
					

					if(!e.isEnemy(D0) && !e.isEnemy(S0) && !e.isEnemy(S2) && !e.isThere(S1))c3+=0.5;
					if(!e.isEnemy(S1) && e.isEnemies(S2,D0)) {c1+=0.5;c2+=0.5;}

					float ccap = States.minFinder(c1,c2,c3);
					if(States.oneCheck(c1,c2)) ccap = States.minFinder(ccap,0.5f);


					if(ccap>0.5) retval+=500;
					else if(ccap<0.5) retval-=500;




				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, fFiveCorner2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
//					UDLR l = side.diag(!diagSide);
//					UDLR u = side.opp();
					Tuple D0 = tlist.get(0).side(side);
					Tuple D1 = D0.side(side);
					Tuple S0 = D0.side(r);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);
					
//					Tuple TL = D0.side2(u,l);
//					Tuple BL = D1.side2(side,l);
					counter++;

					
//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(e.isTheres(S1)) retval+=2000;
//					if(e.isTheres(TL,BL)) retval+=1000;
					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					retval +=500;
					float c1 = States.borderSafe(e, 2, S1,S0);
					float c2 = States.borderSafe(e, 2, S1,D1);
					

					if(!e.isEnemy(S1) && e.isEnemies(S0,D1)) {c1+=0.5;c2+=0.5;}
					
					float ccap = States.minFinder(c1,c2);
					if(States.oneCheck(c1,c2)) ccap = States.minFinder(ccap,0.5f);


					if(ccap>0.5) retval+=500;
					else if(ccap<0.5) retval-=500;



					



				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, fFiveCorner3Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
//					UDLR l = side.diag(!diagSide);
//					UDLR u = side.opp();
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple D0 = S0.side(side);
					Tuple D1 = D0.side(r);

//					Tuple TL = S0.side2(u,l);

					counter++;
		
					

					
//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(e.isTheres(S1)) retval+=2000;
//					if(e.isTheres(TL)) retval+=1000;
					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					retval +=500;
					float c1 = States.borderSafe(e, 2, S1,S0);
					float c2 = States.borderSafe(e, 2, S1,D1);

					if(!e.isEnemy(S1) && e.isEnemies(S0,D1)) {c1+=0.5;c2+=0.5;}

					float ccap = States.minFinder(c1,c2);
					if(States.oneCheck(c1,c2)) ccap = States.minFinder(ccap,0.5f);


					if(ccap>0.5) retval+=500;
					else if(ccap<0.5) retval-=500;





				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, fFiveCorner3Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(side);
					Tuple S0 = S1.side(side);
					Tuple D1 = S1.side(r);
					Tuple D0 = D1.side(side);
					
					Tuple TL = S2.side2(u,l);
					Tuple TR = S2.side2(u,r);
					counter++;

					

					
					
//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(e.isTheres(S1)) retval+=2000;
//					if(e.isTheres(TL,TR)) retval+=1000;

					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					retval +=500;
					float c1 = States.borderSafe(e, 2, S1,S0);
					float c2 = States.borderSafe(e, 2, S1,D1);
					float c3 = States.borderSafe(e, 3, TL,TR,D0);
					
					if(!e.isEnemy(D1) && !e.isEnemy(S0) && !e.isEnemy(S2) && !e.isThere(S1))c3+=0.5;
					if(!e.isEnemy(S1) && e.isEnemies(S0,D1)) {c1+=0.5;c2+=0.5;}

					float ccap = States.minFinder(c1,c2,c3);
					if(States.oneCheck(c1,c2)) ccap = States.minFinder(ccap,0.5f);


					if(ccap>0.5) retval+=500;
					else if(ccap<0.5) retval-=500;


			

				}
				
			}
			
		}
		

		
		return retval;

	}

	
	
}
