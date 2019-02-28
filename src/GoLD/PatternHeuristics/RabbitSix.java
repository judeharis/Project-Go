package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class RabbitSix {


	static ArrayList<Pattern> rabbitSixPattern = Pattern.sToPv2("xrdxrdxldxldxlxzldxldxdx");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, rabbitSixPattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple A1 = tlist.get(0).side(side);
					Tuple S1 = A1.side(side);
					Tuple S0 = S1.side(l);		
					Tuple S2 = S1.side(r);	
					Tuple B1 = S1.side(side);	
					Tuple B0 = B1.side(l);	
					
					
					Tuple TL = A1.side2(u,l);
					Tuple TR = A1.side2(u,r);
					Tuple LT = A1.side2(l,l);		
					Tuple RT = A1.side2(r,r);		
					Tuple RB = S2.side2(r,side);
					Tuple BL = B0.side2(side,l);
					Tuple BR = S2.side2(side,side);	
					
					
					counter++;
					
					
//					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
//					if(e.isTheres(S1)) retval+=1000;
//					if(e.countThere(TL,TR,LT,RT,RB,BL,BR) > 5) retval+=3000;
					
					
					
					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					patval +=500;
					float a1 = States.borderSafeRel2(e, 2 ,S1,S0);
					float a2 = States.borderSafeRel2(e, 2 ,S1,B1);
					float a3 = States.borderSafeRel2(e, 4, TL,LT,S0,A1);
					float a4 = States.borderSafeRel2(e, 4, TR,RT,S2,A1);
					float a5 = States.borderSafeRel2(e, 4, B1,BR,S2,RB);
					if(!e.isEnemies(S1) && a1==0.5) a1+=0.5;
					if(!e.isEnemies(S1) && a2==0.5) a2+=0.5;
					float acap = States.minFinder(a1,a2,a3,a4,a5);
					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a3)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a2,a5) || States.oneCheck(a3,a4)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a4,a5)) acap = States.minFinder(acap,0.5f);
					if(acap>0.5) patval+=500;
					else if(acap<0.5) patval-=500;
					
					
					patval +=400;
					float b1 = States.borderSafeRel1(e, 1, S1);
					float b2 = States.borderSafeRel1(e, 2, TL,TR);
					float b3 = States.borderSafeRel1(e, 2, RT,RB);
					float b4 = States.borderSafeRel1(e, 2, LT,BL,BR);
					float bcap = States.minFinder(b1,b2,b3,b4);
					if(bcap>0.5) patval+=400;
					else if(bcap<0.5) patval-=400;
					
					patval +=200;
					float c1 = States.borderSafeRel1(e, 1, S1);
					float c2 = States.borderSafeRel1(e, 2, TL,TR,LT,RT,RB,BL,BR);
					float ccap = States.minFinder(c1,c2);
					if(ccap>0.5) patval+=200;
					else if(ccap<0.5) patval-=200;

					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(A1,S1,S0,S2,B1,B0);
					
					

//					int patval =0;
//					patval +=500;
//					float a1 = States.borderSafe(e, 2 ,S1,S0);
//					float a2 = States.borderSafe(e, 2 ,S1,B1);
//					float a3 = States.borderSafe(e, 4, TL,LT,S0,A1);
//					float a4 = States.borderSafe(e, 4, TR,RT,S2,A1);
//					float a5 = States.borderSafe(e, 4, B1,BR,S2,RB);
//					if(!e.isEnemies(S1) && a1==0.5) a1+=0.5;
//					if(!e.isEnemies(S1) && a2==0.5) a2+=0.5;
//					float acap = States.minFinder(a1,a2,a3,a4,a5);
//					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a3)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a2,a5) || States.oneCheck(a3,a4)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a4,a5)) acap = States.minFinder(acap,0.5f);
//					if(acap>0.5) patval+=500;
//					else if(acap<0.5) patval-=500;
//					patval +=400;
//					float b1 = States.borderSafe(e, 1, S1);
//					float b2 = States.borderSafe(e, 2, TL,TR);
//					float b3 = States.borderSafe(e, 2, RT,RB);
//					float b4 = States.borderSafe(e, 2, LT,BL,BR);
//					float bcap = States.minFinder(b1,b2,b3,b4);
//					if(bcap>0.5) patval+=400;
//					else if(bcap<0.5) patval-=400;
//					patval +=200;
//					float c1 = States.borderSafe(e, 1, S1);
//					float c2 = States.borderSafe(e, 2, TL,TR,LT,RT,RB,BL,BR);
//					float ccap = States.minFinder(c1,c2);
//					if(ccap>0.5) patval+=200;
//					else if(ccap<0.5) patval-=200;
//					retval+=patval;
					
					

				}
				
			}
			
		}
		


		
		return retval;

	}

	
	
}
