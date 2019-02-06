package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class FFiveSide {
	
	static ArrayList<Pattern> fFiveSide1Pattern = Pattern.sToPv2("xrdxrdxldxlxdSzldxldxdx");
	static ArrayList<Pattern> fFiveSide2Pattern = Pattern.sToPv2("xrdxdxrdxdSzldxldxdrx");
	static ArrayList<Pattern> fFiveSide3Pattern = Pattern.sToPv2("xrdxdxldxdSzldxlxldxrdx");
	static ArrayList<Pattern> fFiveSide4Pattern = Pattern.sToPv2("xrxrdxrdxldxdSzldxrdxdx");


	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, fFiveSide1Pattern,e.kscolour);
		
		
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
					Tuple S1 = D0.side(side);
					Tuple S0 = S1.side(l);		
					Tuple S2 = S1.side(r);	
					Tuple C0 = S0.side(side);	
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple RT = TR.side2(r,side);
					Tuple LT = TL.side2(l,side);
					Tuple RB = RT.side2(side, side);


					counter++;
					
					
					
					if (e.isThere(S2) || e.isThere(D0) || e.isThere(C0))continue;

					int patval =0;
					
					patval +=500;
					float a1 = States.borderSafe(e, 2, S1,S0);
					float a2 = States.borderSafe(e, 5, TL,TR,RT,RB,D0);
					float a3 = States.borderSafe(e, 5, TL,TR,RT,RB,S2);
					
					float acap = States.minFinder(a1,a2,a3);
					if(States.oneCheck(a1,a3) || States.oneCheck(a2,a3)) acap = States.minFinder(acap,0.5f);

					
					if(acap>0.5) patval+=500;
					else if(acap<0.5) patval-=500;

					
					patval +=200;
					float c1 = States.borderSafe(e, 2, S1,S0);
					float c2 = States.borderSafe(e, 2, TL,TR,S1);
					float c3 = States.borderSafe(e, 2, RT,RB,S1);
					float c4 = States.borderSafe(e, 2, LT,S0);
					
					if(!e.isEnemies(S1)) {c2+=0.5;c3+=0.5;};
					float ccap = States.minFinder(c1,c2,c3,c4);
					
					if(States.oneCheck(c1,c2) || (States.oneCheck(c1,c3))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c1,c4) || (States.oneCheck(c2,c3))) ccap = States.minFinder(ccap,0.5f);


					
					if(ccap>0.5) patval+=200;
					else if(ccap<0.5) patval-=200;
					

					retval+=patval;
					



				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, fFiveSide2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
//					UDLR u = side.opp();
					
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(side);
					Tuple S0 = S1.side(side);		
					Tuple D0 = S1.side(l);	
					Tuple C0 = S0.side(r);	
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple LT = TL.side2(l,side);
					Tuple LB = LT.side2(side,side);
					Tuple RT = S1.side2(r, r);
					counter++;

				
					
					if (e.isThere(S2) || e.isThere(D0) || e.isThere(C0))continue;
					if (e.isTheres(S0,RT))continue;

					int patval =0;
					
					patval +=500;
					float a1 = States.borderSafe(e, 2, S1,S0);
					float a2 = States.borderSafe(e, 2, S1,RT);
					float a3 = States.borderSafe(e, 4, C0,RT,TL,TR);
					float a4 = States.borderSafe(e, 4, C0,RT,LT,LB);
					float a5 = 10;// States.borderSafe(e, 4, C0,RT,TL,TR);
					float a6 = States.borderSafe(e, 5, TL,TR,LT,LB,S2);
					float a7 = States.borderSafe(e, 5, TL,TR,LT,LB,D0);
					float a8 = States.borderSafe(e, 5, TL,TR,LT,LB,S0);
					
					if(e.isEnemies(TL,TR,LT,LB,S1) && !e.isEnemy(S2)) {a6+=0.5;}
					if (e.isEnemies(TL,TR,LT,LB,S1) && !e.isEnemy(D0)) {a7+=0.5;}
					if(e.isEnemies(C0,RT) && !e.isEnemy(TL) && !e.isEnemy(TR)) {a3+=0.5;}
					if(e.isEnemies(C0,RT) && !e.isEnemy(LT) && !e.isEnemy(LB)) {a4+=0.5;}
					
					float acap = States.minFinder(a1,a2,a3,a4,a5,a6,a7,a8);
					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a8)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a2,a3) || States.oneCheck(a2,a4)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a2,a5) || States.oneCheck(a3,a4)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a3,a5) || States.oneCheck(a3,a6)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a3,a7) || States.oneCheck(a3,a8)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a4,a5) || States.oneCheck(a4,a6)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a4,a7) || States.oneCheck(a4,a8)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a5,a6) || States.oneCheck(a5,a7)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a5,a8) || States.oneCheck(a6,a7)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a6,a8) || States.oneCheck(a7,a8)) acap = States.minFinder(acap,0.5f);

					
					if(acap>0.5) patval+=500;
					else if(acap<0.5) patval-=500;

					
					patval +=200;
					float c1 = States.borderSafe(e, 2, S1,S0);
					float c2 = States.borderSafe(e, 2, S1,TL);
					float c3 = States.borderSafe(e, 2, S1,TR);
					float c4 = States.borderSafe(e, 2, S1,LT);
					float c5 = States.borderSafe(e, 2, S1,LB);
					float c6 = States.borderSafe(e, 1, RT);
					float c7 = States.borderSafe(e, 2, S0,LB);
					float c8 = States.borderSafe(e, 2, S0,LT);
					float c9 = States.borderSafe(e, 2, TL,TR);
					float c10 = States.borderSafe(e, 2, LT,LB);


					float ccap = States.minFinder(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10);
					
					if(States.oneCheck(c1,c2) || (States.oneCheck(c1,c3))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c1,c4) || (States.oneCheck(c1,c5))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c2,c3)) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c2,c4) || (States.oneCheck(c2,c5))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c3,c4)) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c3,c5)) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c4,c5)) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c1,c7) || (States.oneCheck(c1,c8))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c5,c7) || (States.oneCheck(c4,c8))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c7,c8)) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c9,c2) || (States.oneCheck(c9,c3))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c10,c4) || (States.oneCheck(c10,c5))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c10,c7) || (States.oneCheck(c10,c8))) ccap = States.minFinder(ccap,0.5f);




					
					if(ccap>0.5) patval+=200;
					else if(ccap<0.5) patval-=200;
					

					retval+=patval;


					



				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, fFiveSide3Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					
					Tuple C0 = tlist.get(0).side(side);
					Tuple S0 = C0.side(side);
					Tuple S1 = S0.side(l);		
					Tuple S2 = S1.side(l);	
					Tuple D0 = S1.side(side);	
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple LT = S2.side2(l,u);
					Tuple BL = S2.side2(l,side);
					Tuple BR = S0.side2(r, side);
					counter++;
		
					

					
					
					if (e.isThere(S2) || e.isThere(D0) || e.isThere(C0))continue;


					int patval =0;
					
//					patval +=500;
//					float a1 = States.borderSafe(e, 2, S1,S0);
//					float a2 = States.borderSafe(e, 2, S1);
//					float a3 = States.borderSafe(e, 4, C0,TL,TR);
//					float a4 = States.borderSafe(e, 4, C0,LT);
//					float a5 = States.borderSafe(e, 4, C0,TL,TR);
//					float a6 = States.borderSafe(e, 5, TL,TR,LT,S2);
//					float a7 = States.borderSafe(e, 5, TL,TR,LT,D0);
//					float a8 = States.borderSafe(e, 5, TL,TR,LT,S0);
//					
//					if(e.isEnemies(TL,TR,LT,S1) && !e.isEnemy(S2)) {a6+=0.5;}
//					if (e.isEnemies(TL,TR,LT,S1) && !e.isEnemy(D0)) {a7+=0.5;}
//					
//					float acap = States.minFinder(a1,a2,a3,a4,a5,a6,a7,a8);
//					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a8)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a2,a3) || States.oneCheck(a2,a4)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a2,a5) || States.oneCheck(a3,a4)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a3,a5) || States.oneCheck(a3,a6)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a3,a7) || States.oneCheck(a3,a8)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a4,a5) || States.oneCheck(a4,a6)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a4,a7) || States.oneCheck(a4,a8)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a5,a6) || States.oneCheck(a5,a7)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a5,a8) || States.oneCheck(a6,a7)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a6,a8) || States.oneCheck(a7,a8)) acap = States.minFinder(acap,0.5f);
//
//					
//					if(acap>0.5) patval+=500;
//					else if(acap<0.5) patval-=500;

					
					patval +=200;
					float c1 = States.borderSafe(e, 2, S1,S0);
					float c2 = States.borderSafe(e, 2, S1,TL);
					float c3 = States.borderSafe(e, 2, S1,TR);
					float c4 = States.borderSafe(e, 2, S1,LT);
					float c5 = States.borderSafe(e, 2, S1,BL);
					float c6 = States.borderSafe(e, 2, S0,BR);
					float c7 = States.borderSafe(e, 2, TL,TR);
					float c8 = States.borderSafe(e, 2, LT,BL);
//					float c10 = States.borderSafe(e, 2, LT);


					float ccap = States.minFinder(c1,c2,c3,c4,c5,c6,c7,c8);
					
					if(States.oneCheck(c1,c2) || (States.oneCheck(c1,c3))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c1,c4) || (States.oneCheck(c1,c5))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c1,c6)) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c2,c3) || (States.oneCheck(c2,c4))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c2,c5)) ccap = States.minFinder(ccap,0.5f);	
					else if(States.oneCheck(c2,c7)) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c3,c4)) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c3,c5) || (States.oneCheck(c3,c7))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c4,c5) || (States.oneCheck(c4,c8))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c5,c8)) ccap = States.minFinder(ccap,0.5f);





					
					if(ccap>0.5) patval+=200;
					else if(ccap<0.5) patval-=200;
					

					retval+=patval;






				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, fFiveSide4Pattern,e.kscolour);
		
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
