package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class BulkyFive {


	static ArrayList<Pattern> bulkyFivePattern = Pattern.sToPv2("xrxrdxrdxdlxlxlxulxux");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, bulkyFivePattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple D0 = tlist.get(0).side(side);
					Tuple D1 = D0.side(r);
					Tuple S0 = D0.side(side);		
					Tuple S1 = S0.side(r);	
					Tuple S2 = S1.side(r);	
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = D1.side2(r,side.opp());
					Tuple RT = TR.side2(r,side);
					Tuple BL = S0.side2(l,side);
					Tuple BR = S2.side2(side,r);
					Tuple BRR = BR.side2(side.opp(),r);
					counter++;
					
									
					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
				
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float a1 = States.borderSafeRel2(e, 2, S1,D1);
					float a2 = States.borderSafeRel2(e, 2, S1,S0);
					float a3 = States.borderSafeRel2(e, 3, RT,BR,D0);
					float a6 = States.borderSafeRel2(e, 4, TR,RT,D1,S2);
					float a7 = States.borderSafeRel2(e, 4, BR,BRR,RT,S2);
					if(!e.isEnemy(D1) && !e.isEnemy(S0) && !e.isEnemy(S2) && !e.isThere(S1))a3+=0.5;
					if(!e.isEnemy(S1) && e.isEnemies(D1,S0)) {a1+=0.5;a2+=0.5;}
					float acap = States.minFinder(a1,a2,a3,a6,a7);
					if(States.oneCheck(a1,a2) || States.oneCheck(a6,a7)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a1,a6) || States.oneCheck(a2,a3)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a3,a6) || States.oneCheck(a3,a7)) acap = States.minFinder(acap,0.5f);
					if(acap>0.5) patval+=450;
					else if(acap<0.5) patval-=450;
					
					
					patval +=400;
					float c1 = States.borderSafeRel1(e, 2, TL,TR,RT,BR,BL);
					float c2 = States.borderSafeRel1(e, 1, S1);
					float ccap = States.minFinder(c1,c2);
					if(ccap>0.5) patval+=400;
					else if(ccap<0.5) patval-=400;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2,D0,D1);
					

					
					

					
//					retval +=500;
//					float a1 = States.borderSafe(e, 2, S1,D1);
//					float a2 = States.borderSafe(e, 2, S1,S0);
//					float a3 = States.borderSafe(e, 3, RT,BR,D0);
//					float a6 = States.borderSafe(e, 4, TR,RT,D1,S2);
//					float a7 = States.borderSafe(e, 4, BR,BRR,RT,S2);
//					if(!e.isEnemy(D1) && !e.isEnemy(S0) && !e.isEnemy(S2) && !e.isThere(S1))a3+=0.5;
//					if(!e.isEnemy(S1) && e.isEnemies(D1,S0)) {a1+=0.5;a2+=0.5;}
//					float acap = States.minFinder(a1,a2,a3,a6,a7);
//					if(States.oneCheck(a1,a2) || States.oneCheck(a6,a7)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a1,a6) || States.oneCheck(a2,a3)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a3,a6) || States.oneCheck(a3,a7)) acap = States.minFinder(acap,0.5f);
//					if(acap>0.5) retval+=500;
//					else if(acap<0.5) retval-=500;
//					
//					
////					retval +=500;
////					float a1 = States.borderSafe(e, 2, S1,D1);
////					float a2 = States.borderSafe(e, 2, S1,S0);
////					float a3 = States.borderSafe(e, 4, RT,BR,S0,D0);
////					float a4 = States.borderSafe(e, 4, RT,BR,D0,D1);
////					float a5 = States.borderSafe(e, 4, RT,BR,D0,S2);
////					float a6 = States.borderSafe(e, 4, TR,RT,D1,S2);
////					float a7 = States.borderSafe(e, 4, BR,BRR,RT,S2);
////					
////					
////					if(!e.isEnemy(S1) && e.isEnemies(D1,S0)) {a1+=0.5;a2+=0.5;}
////					
////					float acap = States.minFinder(a1,a2,a3,a4,a5,a6,a7);
////					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a4)) acap = States.minFinder(acap,0.5f);
////					else if(States.oneCheck(a1,a6) || States.oneCheck(a2,a3)) acap = States.minFinder(acap,0.5f);
////					else if(States.oneCheck(a3,a4) || States.oneCheck(a3,a5)) acap = States.minFinder(acap,0.5f);
////					else if(States.oneCheck(a3,a6) || States.oneCheck(a3,a7)) acap = States.minFinder(acap,0.5f);
////					else if(States.oneCheck(a4,a5) || States.oneCheck(a4,a6)) acap = States.minFinder(acap,0.5f);
////					else if(States.oneCheck(a4,a7) || States.oneCheck(a5,a6)) acap = States.minFinder(acap,0.5f);
////					else if(States.oneCheck(a5,a7) || States.oneCheck(a6,a7)) acap = States.minFinder(acap,0.5f);
////					
////					
////					if(acap>0.5) retval+=500;
////					else if(acap<0.5) retval-=500;
//
//
//
//					retval +=400;
//					float c1 = States.borderSafe(e, 2, TL,TR,RT,BR,BL);
//					float c2 = States.borderSafe(e, 1, S1);
//					float ccap = States.minFinder(c1,c2);
//					if(ccap>0.5) retval+=400;
//					else if(ccap<0.5) retval-=400;
					
				}
				
			}
			
		}
		


		
		return retval;

	}

	
	
}
