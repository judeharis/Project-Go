package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BulkyFiveSide {
	
	static ArrayList<Pattern> bulkyFiveSide1Pattern = Pattern.sToPv2("xrxrdxdxdSzldxldx");
	static ArrayList<Pattern> bulkyFiveSide2Pattern = Pattern.sToPv2("xrxrdxdxdxdSzldxdxrdx");
	static ArrayList<Pattern> bulkyFiveSide3Pattern = Pattern.sToPv2("xrxrxrdxdlxdSzdlxdx");
	static ArrayList<Pattern> bulkyFiveSide4Pattern = Pattern.sToPv2("xrdxrdxdxdSzldxdxdx");


	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, bulkyFiveSide1Pattern,e.kscolour);
		
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple D0 = tlist.get(0).side(side);
					Tuple D1 = D0.side(r);
					Tuple S1 = D0.side(side);
					Tuple S0 = S1.side(l);
					Tuple S2 = S1.side(r);	
//					Tuple TL = D0.side2(u,l);
//					Tuple TR = D1.side2(u,r);
					Tuple LT = S0.side2(u,l);			
					counter++;

					
					

					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float a3 = States.borderSafe(e, 2, S1,S2);
					float a4 = States.borderSafe(e, 2, S1,D0);
					float a5 = States.borderSafe(e, 2, LT,D1);
					if(!e.isEnemy(D0) && !e.isEnemy(S0) && !e.isEnemy(S2) && !e.isThere(S1))a5+=0.5;
					if(!e.isEnemy(S1) && e.isEnemies(S2,D0)) {a3+=0.5;a4+=0.5;}
					float acap = States.minFinder(a3,a4,a5);
					if(States.oneCheck(a3,a4)) acap = States.minFinder(acap,0.5f);			
					if(acap>0.5) patval+=450;
					else if(acap<0.5) patval-=450;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2,D0,D1);
					
					
					
//					retval +=500;
//					float a3 = States.borderSafe(e, 2, S1,S2);
//					float a4 = States.borderSafe(e, 2, S1,D0);
//					float a5 = States.borderSafe(e, 2, LT,D1);
//					if(!e.isEnemy(D0) && !e.isEnemy(S0) && !e.isEnemy(S2) && !e.isThere(S1))a5+=0.5;
//					if(!e.isEnemy(S1) && e.isEnemies(S2,D0)) {a3+=0.5;a4+=0.5;}
//					float acap = States.minFinder(a3,a4,a5);
//					if(States.oneCheck(a3,a4)) acap = States.minFinder(acap,0.5f);			
//					if(acap>0.5) retval+=500;
//					else if(acap<0.5) retval-=500;


				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, bulkyFiveSide2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple D0 = tlist.get(0).side(side);
					Tuple D1 = D0.side(side);
					Tuple S0 = D0.side(r);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);
					
					Tuple TL = D0.side2(u,l);
					Tuple TR = S0.side2(u,r);
					Tuple BL = D1.side2(side,l);
					
					counter++;


//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(e.isTheres(S1)) retval+=2000;
//					if(e.isTheres(TL,BL,TR)) retval+=1000;
					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float c1 = States.borderSafe(e, 3, S2,D1,BL);
					float c2 = States.borderSafe(e, 2, S1,S0);
					float c3 = States.borderSafe(e, 2, S1,D1);
					if(!e.isEnemy(S1) && e.isEnemies(D1,D0)) {c2+=0.5;c3+=0.5;}
					float ccap = States.minFinder(c1,c2,c3);
					if(States.oneCheck(c1,c3) || States.oneCheck(c2,c3)) ccap = States.minFinder(ccap,0.5f);
					if(ccap>0.5) patval+=450;
					else if(ccap<0.5) patval-=450;
					patval +=200;
					float b1 = States.borderSafe(e, 3, BL,D1,D0);
					float b2 = States.borderSafe(e, 4, TL,TR,D0,S0);
					float b3 = States.borderSafe(e, 2, S1,S0);
					float b4 = States.borderSafe(e, 2, S1,D1);
					float b5 = States.borderSafe(e, 3, S2,D1,BL);
					float bcap = States.minFinder(b1,b2,b3,b4,b5);
					if(States.oneCheck(b1,b2) || States.oneCheck(b1,b4)) bcap = States.minFinder(bcap,0.5f);
					else if(States.oneCheck(b1,b5) || States.oneCheck(b2,b3)) bcap = States.minFinder(bcap,0.5f);
					else if(States.oneCheck(b3,b4) || States.oneCheck(b4,b5)) bcap = States.minFinder(bcap,0.5f);
					if(bcap>0.5) patval+=200;
					else if(bcap<0.5) patval-=200;
					patval +=200;
					float a1 = States.borderSafe(e, 1, TL,TR,BL);
					float a2 = States.borderSafe(e, 1, S1);
					float acap = States.minFinder(a1,a2);
					if(acap>0.5) patval+=200;
					else if(acap<0.5) patval-=200;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2,D0,D1);
					
					
//					retval +=500;
//					float c1 = States.borderSafe(e, 3, S2,D1,BL);
//					float c2 = States.borderSafe(e, 2, S1,S0);
//					float c3 = States.borderSafe(e, 2, S1,D1);
//					if(!e.isEnemy(S1) && e.isEnemies(D1,D0)) {c2+=0.5;c3+=0.5;}
//					float ccap = States.minFinder(c1,c2,c3);
//					if(States.oneCheck(c1,c3) || States.oneCheck(c2,c3)) ccap = States.minFinder(ccap,0.5f);
//					if(ccap>0.5) retval+=500;
//					else if(ccap<0.5) retval-=500;
//					retval +=200;
//					float b1 = States.borderSafe(e, 3, BL,D1,D0);
//					float b2 = States.borderSafe(e, 4, TL,TR,D0,S0);
//					float b3 = States.borderSafe(e, 2, S1,S0);
//					float b4 = States.borderSafe(e, 2, S1,D1);
//					float b5 = States.borderSafe(e, 3, S2,D1,BL);
//					float bcap = States.minFinder(b1,b2,b3,b4,b5);
//					if(States.oneCheck(b1,b2) || States.oneCheck(b1,b4)) bcap = States.minFinder(bcap,0.5f);
//					else if(States.oneCheck(b1,b5) || States.oneCheck(b2,b3)) bcap = States.minFinder(bcap,0.5f);
//					else if(States.oneCheck(b3,b4) || States.oneCheck(b4,b5)) bcap = States.minFinder(bcap,0.5f);
//					if(bcap>0.5) retval+=200;
//					else if(bcap<0.5) retval-=200;
//					retval +=200;
//					float a1 = States.borderSafe(e, 1, TL,TR,BL);
//					float a2 = States.borderSafe(e, 1, S1);
//					float acap = States.minFinder(a1,a2);
//					if(acap>0.5) retval+=200;
//					else if(acap<0.5) retval-=200;
					


				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, bulkyFiveSide3Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple D0 = S0.side(side);
					Tuple D1 = D0.side(r);
					
					Tuple TL = S0.side2(u,l);
					Tuple TR = S2.side2(u,r);
					Tuple BR = S2.side2(side,r);
					

					counter++;

					
					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float c1 = States.borderSafe(e, 2, S1,S0);
					float c2 = States.borderSafe(e, 2, S1,D1);
					float c3 = States.borderSafe(e, 3, TR,BR,D0);
					if(!e.isEnemy(D1) && !e.isEnemy(S0) && !e.isEnemy(S2) && !e.isThere(S1))c3+=0.5;
					if(!e.isEnemy(S1) && e.isEnemies(D1,S0)) {c1+=0.5;c2+=0.5;}
					float ccap = States.minFinder(c1,c2,c3);
					if(States.oneCheck(c1,c2)) ccap = States.minFinder(ccap,0.5f);
					if(ccap>0.5) patval+=450;
					else if(ccap<0.5) patval-=450;
					patval +=400;
					float a1 = States.borderSafe(e, 1, TL,TR,BR);
					float a2 = States.borderSafe(e, 1, S1);
					float acap = States.minFinder(a1,a2);
					if(acap>0.5) patval+=400;
					else if(acap<0.5) patval-=400;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2,D0,D1);
					
					
//					retval +=500;
//					float c1 = States.borderSafe(e, 2, S1,S0);
//					float c2 = States.borderSafe(e, 2, S1,D1);
//					float c3 = States.borderSafe(e, 3, TR,BR,D0);
//					if(!e.isEnemy(D1) && !e.isEnemy(S0) && !e.isEnemy(S2) && !e.isThere(S1))c3+=0.5;
//					if(!e.isEnemy(S1) && e.isEnemies(D1,S0)) {c1+=0.5;c2+=0.5;}
//					float ccap = States.minFinder(c1,c2,c3);
//					if(States.oneCheck(c1,c2)) ccap = States.minFinder(ccap,0.5f);
//					if(ccap>0.5) retval+=500;
//					else if(ccap<0.5) retval-=500;
//					retval +=400;
//					float a1 = States.borderSafe(e, 1, TL,TR,BR);
//					float a2 = States.borderSafe(e, 1, S1);
//					float acap = States.minFinder(a1,a2);
//					if(acap>0.5) retval+=400;
//					else if(acap<0.5) retval-=400;
			

				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, bulkyFiveSide4Pattern,e.kscolour);
		
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
					Tuple RT = S1.side2(u,r);

					counter++;

					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float c1 = States.borderSafe(e, 2, S1,S0);
					float c2 = States.borderSafe(e, 2, S1,D1);
					float c3 = States.borderSafe(e, 3, TR,TL,D0);
					if(!e.isEnemy(D1) && !e.isEnemy(S0) && !e.isEnemy(S2) && !e.isThere(S1))c3+=0.5;
					if(!e.isEnemy(S1) && e.isEnemies(D1,S0)) {c1+=0.5;c2+=0.5;}
					float ccap = States.minFinder(c1,c2,c3);
					if(States.oneCheck(c1,c2)) ccap = States.minFinder(ccap,0.5f);
					if(ccap>0.5) patval+=450;
					else if(ccap<0.5) patval-=450;
					patval +=400;
					float a1 = States.borderSafe(e, 1, TL,TR,RT);
					float a2 = States.borderSafe(e, 1, S1);
					float acap = States.minFinder(a1,a2);
					if(acap>0.5) patval+=400;
					else if(acap<0.5) patval-=400;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2,D0,D1);
					
					
//					retval +=500;
//					float c1 = States.borderSafe(e, 2, S1,S0);
//					float c2 = States.borderSafe(e, 2, S1,D1);
//					float c3 = States.borderSafe(e, 3, TR,TL,D0);
//					if(!e.isEnemy(D1) && !e.isEnemy(S0) && !e.isEnemy(S2) && !e.isThere(S1))c3+=0.5;
//					if(!e.isEnemy(S1) && e.isEnemies(D1,S0)) {c1+=0.5;c2+=0.5;}
//					float ccap = States.minFinder(c1,c2,c3);
//					if(States.oneCheck(c1,c2)) ccap = States.minFinder(ccap,0.5f);
//					if(ccap>0.5) retval+=500;
//					else if(ccap<0.5) retval-=500;
//					retval +=400;
//					float a1 = States.borderSafe(e, 1, TL,TR,RT);
//					float a2 = States.borderSafe(e, 1, S1);
//					float acap = States.minFinder(a1,a2);
//					if(acap>0.5) retval+=400;
//					else if(acap<0.5) retval-=400;
			
			

				}
				
			}
			
		}
		

		
		return retval;

	}

	
	
}
