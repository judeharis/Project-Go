package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BentThreeSide {
	
	static ArrayList<Pattern> bentThreeSide1Pattern = Pattern.sToPv2("xrdxdxdSllluxurx");
	static ArrayList<Pattern> bentThreeSide2Pattern = Pattern.sToPv2("xrxrdxldxzldxdxdS");
	
	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, bentThreeSide1Pattern,e.kscolour);
		

		
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
					counter++;
					
					if(e.isThere(S0) || e.isThere(S2)) continue;
					
					
					
					
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafe(e, 1,S0);
					float z2 = States.borderSafe(e, 1,S2);
					float z3 = States.borderSafe(e, 3, TL,TR,LT) + States.minFinder(z1,z2);
					float zcap = States.minFinder(z3);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1))patval=0;
					
					
					
					patval +=450;
					float a = States.borderSafe(e, 2, TL,TR);
					float b = States.borderSafe(e, 1, LT);
					float c = States.borderSafe(e, 1, S1);
					float ncap = States.minFinder(a,b,c);
					
					if(ncap>0.5) patval+=450;
					else if(ncap<0.5) patval-=450;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2);
					
					
					
					
					
//					retval +=500;
//					float a = States.borderSafe(e, 2, TL,TR);
//					float b = States.borderSafe(e, 1, LT);
//					float c = States.borderSafe(e, 1, S1);
//					float ncap = States.minFinder(a,b,c);
//					
//					if(ncap>0.5) retval+=500;
//					else if(ncap<0.5) retval-=500;
					


				
					
					
					


				}

			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, bentThreeSide2Pattern,e.kscolour);
		
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
//					UDLR l = side.diag(!diagSide);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S0 = S1.side(side);
					Tuple S2 = S1.side(r);
//					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);
					Tuple RB = S2.side2(side,r);
					counter++;

					
					

					
					
					
					if(e.isThere(S0) || e.isThere(S2)) continue;
					if(e.isTheres(S1,RB)) continue;
					
					int patval =0;	
					patval+=100;
					if(e.isThere(S1))patval=0;
					
					
					
					
					patval +=450;
					float a = States.borderSafe(e, 2, TR,RB);
					float b = States.borderSafe(e, 1, S1);
					float ncap = States.minFinder(a,b);
					
					if(ncap>0.5) patval+=450;
					else if(ncap<0.5) patval-=450;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2);
					
					
//					retval +=500;
//					float a = States.borderSafe(e, 2, TR,RB);
//					float b = States.borderSafe(e, 1, S1);
//					float ncap = States.minFinder(a,b);
//					
//					if(ncap>0.5) retval+=500;
//					else if(ncap<0.5) retval-=500;
					




					

				}

			}
			
		}


		
		return retval;

	}

	
	
}
