package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class FFiveCorner {
	
	static ArrayList<Pattern> fFiveCorner1Pattern = Pattern.sToPv2("xrdxddXlxzldxldxdx");
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

					Tuple D0 = tlist.get(0).side(side);
					Tuple S1 = D0.side(side);
					Tuple S0 = S1.side(l);		
					Tuple S2 = S1.side(r);	
					Tuple C0 = S0.side(side);	
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple LT = TL.side2(side,l);
					counter++;

					if (e.isThere(S2) || e.isThere(D0) || e.isThere(C0))continue;

					int patval =0;
					
					patval +=500;
					float a1 = States.borderSafe(e, 2, S1,S0);
					float a2 = States.borderSafe(e, 3, C0,S1,S2);


					
					if(States.numCheck(0.5,a1,a2) && !e.isEnemy(S0) && !e.isEnemy(S2)) a2+=0.5;
					if(States.numCheck(0.5,a1,a2) && !e.isEnemy(S1)) a2+=0.5;
					
					float acap = States.minFinder(a1,a2);
					if(States.oneCheck(a1,a2)) acap = States.minFinder(acap,0.5f);
					if(acap>0.5) patval+=500;
					else if(acap<0.5) patval-=500;

					
					
					
					patval +=400;
					float c1 = States.borderSafe(e, 1, S1);
					float c2 = States.borderSafe(e, 2, S0,LT);
					float c3 = States.borderSafe(e, 2, TL,TR);

					float ccap = States.minFinder(c1,c2,c3);

					if(ccap>0.5) patval+=400;
					else if(ccap<0.5) patval-=400;
					

					retval+=patval;



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
					UDLR l = side.diag(!diagSide);

					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(side);
					Tuple S0 = S1.side(side);		
					Tuple D0 = S1.side(l);	
					Tuple C0 = S0.side(r);	
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple LT = TL.side2(side,l);
					Tuple BL = LT.side2(side,side);
					counter++;

					if (e.isThere(S2) || e.isThere(D0) || e.isThere(C0))continue;

					int patval =0;
					
					patval +=500;
					float a1 = States.borderSafe(e, 2, S1,S0);
					float a2 = States.borderSafe(e, 5, TL,TR,LT,BL,S0);
					float a3 = States.borderSafe(e, 5, TL,TR,LT,BL,S2);
					float a4 = States.borderSafe(e, 5, TL,TR,LT,BL,D0);
					


					
					if(States.numCheck(0.5,a2) && !e.isEnemy(S0) && e.isThere(S1)) a2-=0.5;
					if(States.numCheck(0.5,a3) && !e.isEnemy(S2) && e.isThere(S1)) a3-=0.5;
					if(States.numCheck(0.5,a4) && !e.isEnemy(D0) && e.isThere(S1)) a4-=0.5;
					

					
					float acap = States.minFinder(a1,a2,a3,a4);
					if(States.oneCheck(a1,a2)) acap = States.minFinder(acap,0.5f);
					if(acap>0.5) patval+=500;
					else if(acap<0.5) patval-=500;

					
					
					
					patval +=400;
					float c1 = States.borderSafe(e, 2, S0,S1);
					float c2 = States.borderSafe(e, 2, S1,TL);
					float c3 = States.borderSafe(e, 2, S1,TR);
					float c4 = States.borderSafe(e, 2, S1,LT);
					float c5 = States.borderSafe(e, 2, S1,BL);
					float c6 = States.borderSafe(e, 2, S0,BL,LT);
					float c7 = States.borderSafe(e, 2, TL,TR);

					



					float ccap = States.minFinder(c1,c2,c3,c4,c5,c6,c7);
					if(States.oneCheck(c1,c2) || (States.oneCheck(c1,c3))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c1,c4) || (States.oneCheck(c1,c5))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c1,c6)) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c2,c3) || (States.oneCheck(c2,c4))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c2,c5) || (States.oneCheck(c2,c7))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c3,c4) || (States.oneCheck(c3,c5))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c3,c7) || (States.oneCheck(c4,c5))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c4,c6) || (States.oneCheck(c5,c6))) ccap = States.minFinder(ccap,0.5f);


					if(ccap>0.5) patval+=400;
					else if(ccap<0.5) patval-=400;
					
					if(e.isThere(S0))patval/=2;
					retval+=patval;



					



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
					UDLR l = side.diag(!diagSide);

					Tuple C0 = tlist.get(0).side(side);
					Tuple S0 = C0.side(side);
					Tuple S1 = S0.side(l);		
					Tuple S2 = S1.side(l);	
					Tuple D0 = S1.side(side);	
					
					Tuple TL = tlist.get(0).side(l);
					Tuple LT = S2.side2(side.opp(),l);
					Tuple BL = LT.side2(side,side);
					counter++;

					if (e.isThere(S2) || e.isThere(D0) || e.isThere(C0))continue;
					if (e.isTheres(TL,S0))continue;

					int patval =0;
					
					patval +=500;
					float a1 = States.borderSafe(e, 2, S1,S0);
					float a2 = States.borderSafe(e, 3, TL,LT,BL,S0);
					float a3 = States.borderSafe(e, 5, TL,LT,BL,S2);
					float a4 = States.borderSafe(e, 5, TL,LT,BL,D0);
					


					
					if(States.numCheck(0.5,a2) && !e.isEnemy(S0) && e.isThere(S1)) a2-=0.5;
					if(States.numCheck(0.5,a3) && !e.isEnemy(S2) && e.isThere(S1)) a3-=0.5;
					if(States.numCheck(0.5,a4) && !e.isEnemy(D0) && e.isThere(S1)) a4-=0.5;
					

					
					float acap = States.minFinder(a1,a2,a3,a4);
					if(States.oneCheck(a1,a2)) acap = States.minFinder(acap,0.5f);
					if(acap>0.5) patval+=500;
					else if(acap<0.5) patval-=500;

					
					
					
//					patval +=900;
//					float c1 = States.borderSafe(e, 1, S0,S1,TL);
//					float c2 = States.borderSafe(e, 2, BL,LT);
//					float ccap = States.minFinder(c1,c2);
//
//					if(ccap>0.5) patval+=900;
//					else if(ccap<0.5) patval-=900;

					
					retval+=patval;




				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, fFiveCorner4Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);

					Tuple C0 = tlist.get(0).side(side);
					Tuple S0 = C0.side(r);
					Tuple S1 = S0.side(side);		
					Tuple S2 = S1.side(side);	
					Tuple D0 = S1.side(r);	
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = S0.side2(side.opp(),r);
					Tuple LT = TL.side2(side,side);

					counter++;

					if (e.isThere(S2) || e.isThere(D0) || e.isThere(C0))continue;


					int patval =0;

					
					patval +=500;
					float a1 = States.borderSafe(e, 2, S1,S0);
					float a2 = States.borderSafe(e, 2, S2,D0);
					float a3 = States.borderSafe(e, 3, TR,S0,D0);
					float a4 = States.borderSafe(e, 3, TL,LT,S1);

					

					
					if(a1 == 0.5 || a4==0.5) {a2+=0.5;a3+=0.5;};
					float acap = States.minFinder(a1,a2,a3,a4);
					if(States.oneCheck(a1,a3) || States.oneCheck(a1,a4)) acap = States.minFinder(acap,0.5f);
					if(acap>0.5) patval+=500;
					else if(acap<0.5) patval-=500;
					
					

					
					patval +=400;
					float c1 = States.borderSafe(e, 1, S1);
					float c2 = States.borderSafe(e, 2, S0,TR);
					float c3 = States.borderSafe(e, 2, TL,LT);
					float c4 = States.borderSafe(e, 2, S2,D0);

					if(c4==0.5 && !e.isEnemies(S1))c4+=0.5;
					float ccap = States.minFinder(c1,c2,c3,c4);



					if(ccap>0.5) patval+=400;
					else if(ccap<0.5) patval-=400;
					
					if(e.isTheres(LT,S0))patval/=2;
					retval+=patval;

			

				}
				
			}
			
		}
		

		
		return retval;

	}

	
	
}
