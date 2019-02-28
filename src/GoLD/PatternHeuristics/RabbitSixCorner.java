package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class RabbitSixCorner {
	
	static ArrayList<Pattern> rabbitSixCorner1Pattern = Pattern.sToPv2("xrdxddXzldxldxdx");
	static ArrayList<Pattern> rabbitSixCorner2Pattern = Pattern.sToPv2("xrxrdxddXzldxdxrdx");
	static ArrayList<Pattern> rabbitSixCorner3Pattern = Pattern.sToPv2("xrdxzldxldxrdxrr#");


	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, rabbitSixCorner1Pattern,e.kscolour);
		
		
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
					counter++;
					
					
					
					
					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float a1 = States.borderSafeRel2(e, 2 ,S1,S0);
					float a2 = States.borderSafeRel2(e, 2 ,S1,B1);
					float a3 = States.borderSafeRel2(e, 3, TR,S2,A1);
					float a4 = States.borderSafeRel2(e, 4, TL,LT,A1,S0);
					if(!e.isEnemies(S1) && a1==0.5) a1+=0.5;
					if(!e.isEnemies(S1) && a2==0.5) a2+=0.5;
					float acap = States.minFinder(a1,a2,a3,a4);
					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a4)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a3,a4)) acap = States.minFinder(acap,0.5f);
					if(acap>0.5) patval+=450;
					else if(acap<0.5) patval-=450;
					
					patval +=200;
					float b1 = States.borderSafeRel1(e, 1, S1);
					float b2 = States.borderSafeRel1(e, 2, TL,TR);
					float b3 = States.borderSafeRel1(e, 3, TR,S2,A1);
					float b4 = States.borderSafeRel1(e, 2, B1,S2);
					float b5 = States.borderSafeRel1(e, 4, TL,LT,A1,S0);
					float bcap = States.minFinder(b1,b2,b3,b4);
					if(States.oneCheck(b2,b3) || States.oneCheck(b2,b5)) bcap = States.minFinder(bcap,0.5f);
					else if(States.oneCheck(b3,b4) || States.oneCheck(b3,b5)) bcap = States.minFinder(bcap,0.5f);	
					if(bcap>0.5) patval+=200;
					else if(bcap<0.5) patval-=200;

					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(A1,S1,S0,S2,B1,B0);
					
					
					
					
//					int patval =0;
//					patval +=500;
//					float a1 = States.borderSafe(e, 2 ,S1,S0);
//					float a2 = States.borderSafe(e, 2 ,S1,B1);
//					float a3 = States.borderSafe(e, 3, TR,S2,A1);
//					float a4 = States.borderSafe(e, 4, TL,LT,A1,S0);
//					if(!e.isEnemies(S1) && a1==0.5) a1+=0.5;
//					if(!e.isEnemies(S1) && a2==0.5) a2+=0.5;
//					float acap = States.minFinder(a1,a2,a3,a4);
//					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a4)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a3,a4)) acap = States.minFinder(acap,0.5f);
//					if(acap>0.5) patval+=500;
//					else if(acap<0.5) patval-=500;
//					patval +=200;
//					float b1 = States.borderSafe(e, 1, S1);
//					float b2 = States.borderSafe(e, 2, TL,TR);
//					float b3 = States.borderSafe(e, 3, TR,S2,A1);
//					float b4 = States.borderSafe(e, 2, B1,S2);
//					float b5 = States.borderSafe(e, 4, TL,LT,A1,S0);
//					float bcap = States.minFinder(b1,b2,b3,b4);
//					if(States.oneCheck(b2,b3) || States.oneCheck(b2,b5)) bcap = States.minFinder(bcap,0.5f);
//					else if(States.oneCheck(b3,b4) || States.oneCheck(b3,b5)) bcap = States.minFinder(bcap,0.5f);	
//					if(bcap>0.5) patval+=200;
//					else if(bcap<0.5) patval-=200;
//					retval+=patval;
					


				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, rabbitSixCorner2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple B0 = tlist.get(0).side(side);
					Tuple B1 = B0.side(r);	
					Tuple S0 = B0.side(side);
					Tuple S1 = S0.side(r);		
					Tuple S2 = S1.side(r);	
					Tuple A1 = S1.side(side);
					
					Tuple TL = B0.side2(u,l);
					Tuple TR = B1.side2(u,r);
					Tuple BL = S0.side2(side,l);

					counter++;
	
					
					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float a1 = States.borderSafeRel2(e, 2 ,S1,S0);
					float a2 = States.borderSafeRel2(e, 2 ,S1,B1);
					float a3 = States.borderSafeRel2(e, 3, TR,S2,B1);
					float a4 = States.borderSafeRel2(e, 3, BL,S0,A1);
					float a5 = States.borderSafeRel2(e, 2, A1,S2);
					if(a1 == 0.5 || a2==0.5) {a3+=0.5;a5+=0.5;};
					if(!e.isEnemies(S1) && a1==0.5) a1+=0.5;
					if(!e.isEnemies(S1) && a2==0.5) a2+=0.5;
					float acap = States.minFinder(a1,a2,a3,a4,a5);
					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a4)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a2,a3) || States.oneCheck(a3,a5)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a4,a5)) acap = States.minFinder(acap,0.5f);
					if(acap>0.5) patval+=450;
					else if(acap<0.5) patval-=450;
					
					patval +=400;
					float b1 = States.borderSafeRel1(e, 1, S1);
					float b2 = States.borderSafeRel1(e, 4, TL,TR,B0,B1);
					float b3 = States.borderSafeRel1(e, 4, TL,BL,B0,S0);
					float bcap = States.minFinder(b1,b2,b3);
					if(bcap>0.5) patval+=400;
					else if(bcap<0.5) patval-=400;
					
					patval +=200;
					float c1 = States.borderSafeRel1(e, 1, S1);
					float c2 = States.borderSafeRel1(e, 1, TL,TR,BL);
					float ccap = States.minFinder(c1,c2);
					if(ccap>0.5) patval+=200;
					else if(ccap<0.5) patval-=200;

					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(A1,S1,S0,S2,B1,B0);
					
					
					
					
					
//					int patval =0;
//					patval +=500;
//					float a1 = States.borderSafe(e, 2 ,S1,S0);
//					float a2 = States.borderSafe(e, 2 ,S1,B1);
//					float a3 = States.borderSafe(e, 3, TR,S2,B1);
//					float a4 = States.borderSafe(e, 3, BL,S0,A1);
//					float a5 = States.borderSafe(e, 2, A1,S2);
//					if(a1 == 0.5 || a2==0.5) {a3+=0.5;a5+=0.5;};
//					if(!e.isEnemies(S1) && a1==0.5) a1+=0.5;
//					if(!e.isEnemies(S1) && a2==0.5) a2+=0.5;
//					float acap = States.minFinder(a1,a2,a3,a4,a5);
//					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a4)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a2,a3) || States.oneCheck(a3,a5)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a4,a5)) acap = States.minFinder(acap,0.5f);
//					if(acap>0.5) patval+=500;
//					else if(acap<0.5) patval-=500;
//					patval +=400;
//					float b1 = States.borderSafe(e, 1, S1);
//					float b2 = States.borderSafe(e, 4, TL,TR,B0,B1);
//					float b3 = States.borderSafe(e, 4, TL,BL,B0,S0);
//					float bcap = States.minFinder(b1,b2,b3);
//					if(bcap>0.5) patval+=400;
//					else if(bcap<0.5) patval-=400;
//					patval +=200;
//					float c1 = States.borderSafe(e, 1, S1);
//					float c2 = States.borderSafe(e, 1, TL,TR,BL);
//					float ccap = States.minFinder(c1,c2);
//					if(ccap>0.5) patval+=200;
//					else if(ccap<0.5) patval-=200;
//					retval+=patval;
					



				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, rabbitSixCorner3Pattern,e.kscolour);
		
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
					Tuple S2 = S1.side(l);	
					Tuple S0 = S1.side(r);
					Tuple B1 = S1.side(side);	
					Tuple B0 = B1.side(r);	
					
					Tuple TL = A1.side2(u,l);
					Tuple TR = A1.side2(u,r);		
					Tuple LT = S2.side2(u,l);
					Tuple BL = S2.side2(side,l);
					counter++;
					
					
					
					
					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float a1 = States.borderSafeRel2(e, 2 ,S1,S0);
					float a2 = States.borderSafeRel2(e, 2 ,S1,B1);
					float a3 = States.borderSafeRel2(e, 3, TR,A1,S0);
					float a4 = States.borderSafeRel2(e, 3, BL,B1,S2);
					float a5 = States.borderSafeRel2(e, 4, TL,LT,S2,A1);
					if(a1 == 0.5 || a2==0.5) {a5+=0.5;};
					if(!e.isEnemies(S1) && a1==0.5) a1+=0.5;
					if(!e.isEnemies(S1) && a2==0.5) a2+=0.5;
					float acap = States.minFinder(a1,a2,a3,a4,a5);
					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a3)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a2,a4) || States.oneCheck(a3,a5)) acap = States.minFinder(acap,0.5f);
					else if(States.oneCheck(a4,a5)) acap = States.minFinder(acap,0.5f);
					if(acap>0.5) patval+=450;
					else if(acap<0.5) patval-=450;
					
					patval +=400;
					float b1 = States.borderSafeRel1(e, 1, S1);
					float b2 = States.borderSafeRel1(e, 2, TL,TR);
					float b3 = States.borderSafeRel1(e, 2, LT,BL);
					float b4 = States.borderSafeRel1(e, 3, BL,B1,S2);
					float b5 = States.borderSafeRel1(e, 3, BL,B1,B0);
					float b6 = States.borderSafeRel1(e, 3, TR,A1,S0);
					float b7 = States.borderSafeRel1(e, 3, TR,B0,S0);
					float b8 = States.borderSafeRel1(e, 4, TL,LT,S2,A1);
					if(b8<1 && States.minFinder(b1,b2,b3,b4,b5,b6,b7) < 1) b8+=0.5;
					float bcap = States.minFinder(b1,b2,b3,b4,b5,b6,b7,b8);
					if(States.oneCheck(b2,b6) || States.oneCheck(b2,b5)) bcap = States.minFinder(bcap,0.5f);
					else if(States.oneCheck(b3,b4) || States.oneCheck(b3,b5)) bcap = States.minFinder(bcap,0.5f);
					else if(States.oneCheck(b4,b5)) bcap = States.minFinder(bcap,0.5f);
					else if(States.oneCheck(b5,b7)) bcap = States.minFinder(bcap,0.5f);
					if(bcap>0.5) patval+=400;
					else if(bcap<0.5) patval-=400;
					
					patval +=200;
					float c1 = States.borderSafeRel1(e, 1, S1);
					float c2 = States.borderSafeRel1(e, 1, TL,TR,BL,LT);
					float ccap = States.minFinder(c1,c2);
					if(ccap>0.5) patval+=200;
					else if(ccap<0.5) patval-=200;

					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(A1,S1,S0,S2,B1,B0);
					
					
					
//					int patval =0;
//					patval +=500;
//					float a1 = States.borderSafe(e, 2 ,S1,S0);
//					float a2 = States.borderSafe(e, 2 ,S1,B1);
//					float a3 = States.borderSafe(e, 3, TR,A1,S0);
//					float a4 = States.borderSafe(e, 3, BL,B1,S2);
//					float a5 = States.borderSafe(e, 4, TL,LT,S2,A1);
//					if(a1 == 0.5 || a2==0.5) {a5+=0.5;};
//					if(!e.isEnemies(S1) && a1==0.5) a1+=0.5;
//					if(!e.isEnemies(S1) && a2==0.5) a2+=0.5;
//					float acap = States.minFinder(a1,a2,a3,a4,a5);
//					if(States.oneCheck(a1,a2) || States.oneCheck(a1,a3)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a2,a4) || States.oneCheck(a3,a5)) acap = States.minFinder(acap,0.5f);
//					else if(States.oneCheck(a4,a5)) acap = States.minFinder(acap,0.5f);
//					if(acap>0.5) patval+=500;
//					else if(acap<0.5) patval-=500;
//					patval +=400;
//					float b1 = States.borderSafe(e, 1, S1);
//					float b2 = States.borderSafe(e, 2, TL,TR);
//					float b3 = States.borderSafe(e, 2, LT,BL);
//					float b4 = States.borderSafe(e, 3, BL,B1,S2);
//					float b5 = States.borderSafe(e, 3, BL,B1,B0);
//					float b6 = States.borderSafe(e, 3, TR,A1,S0);
//					float b7 = States.borderSafe(e, 3, TR,B0,S0);
//					float b8 = States.borderSafe(e, 4, TL,LT,S2,A1);
//					if(b8<1 && States.minFinder(b1,b2,b3,b4,b5,b6,b7) < 1) b8+=0.5;
//					float bcap = States.minFinder(b1,b2,b3,b4,b5,b6,b7,b8);
//					if(States.oneCheck(b2,b6) || States.oneCheck(b2,b5)) bcap = States.minFinder(bcap,0.5f);
//					else if(States.oneCheck(b3,b4) || States.oneCheck(b3,b5)) bcap = States.minFinder(bcap,0.5f);
//					else if(States.oneCheck(b4,b5)) bcap = States.minFinder(bcap,0.5f);
//					else if(States.oneCheck(b5,b7)) bcap = States.minFinder(bcap,0.5f);
//					if(bcap>0.5) patval+=400;
//					else if(bcap<0.5) patval-=400;
//					patval +=200;
//					float c1 = States.borderSafe(e, 1, S1);
//					float c2 = States.borderSafe(e, 1, TL,TR,BL,LT);
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
