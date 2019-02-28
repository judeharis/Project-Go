package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class StraightThreeSide {
	
	static ArrayList<Pattern> straightThreeSide1Pattern = Pattern.sToPv2("xrxrxrdxzldxdS");
	static ArrayList<Pattern> straightThreeSide2Pattern = Pattern.sToPv2("xrdxdxdxzdlxdxdxdS");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightThreeSide1Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = S2.side2(r,side.opp());
					counter++;


					if (e.isThere(S0) || e.isThere(S2))continue;
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafeRel1(e, 3, TL,S0,S1);
					float z2 = States.borderSafeRel1(e, 3, TR,S1,S2);
					float z3 = States.borderSafe(e, 1,S0);
					float z4 = States.borderSafe(e, 1,S2);
					float z5 = States.borderSafeRel1(e, 2, TL,TR) + States.minFinder(z3,z4);
					float zcap = States.minFinder(z1,z2,z5);
					if(States.oneCheck(z1,z2) || States.oneCheck(z1,z5)) zcap = States.minFinder(zcap,0.5f);
					else if(States.oneCheck(z2,z5)) zcap = States.minFinder(zcap,0.5f);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1))patval=0;
					
					
					
					patval +=450;
					float a = States.borderSafeRel2(e, 1, TL,TR);
					float b = States.borderSafeRel2(e, 1, S1);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) patval+=450;
					else if(ncap<0.5) patval-=450;
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2);
					
					
//					retval +=500;
//					float a = States.borderSafe(e, 1, TL,TR);
//					float b = States.borderSafe(e, 1, S1);
//					float ncap = States.minFinder(a,b);
//					if(ncap>0.5) retval+=500;
//					else if(ncap<0.5) retval-=500;
					

					
			
				}
			}
			
		}
		
		
		
		pMatches =ps.allStringMatchv2(sstring, straightThreeSide2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					counter++;

					if (e.isThere(S0) || e.isThere(S2))continue;
					
					
					int patval =0;	
					patval+=100;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float a = States.borderSafeRel2(e, 2, TL,TR);
					float b = States.borderSafeRel2(e, 1, S1);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) patval+=450;
					else if(ncap<0.5) patval-=450;

					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2);
					
					
//					retval +=500;
//					float a = States.borderSafe(e, 2, TL,TR);
//					float b = States.borderSafe(e, 1, S1);
//					float ncap = States.minFinder(a,b);
//					if(ncap>0.5) retval+=500;
//					else if(ncap<0.5) retval-=500;


					
			
				}
				
			}
			
		}


		
		return retval;

	}

	
	
}
