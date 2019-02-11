package PatternHeuristics;

import java.util.ArrayList;
import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightTwoSide {

	static ArrayList<Pattern> straightTwoSide1Pattern = Pattern.sToPv2("xrxrdxzdlxdS");
	static ArrayList<Pattern> straightTwoSide2Pattern = Pattern.sToPv2("xrdxdxzldxdxdS");
	
	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightTwoSide1Pattern,e.kscolour);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);	
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);
					counter++;	


					
					if (e.isThere(S1) || e.isThere(S2))continue;
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafe(e, 2, TL,S1);
					float z2 = States.borderSafe(e, 2, TR,S2);
					float z3 = States.borderSafe(e, 2, TL,TR);
					float zcap = States.minFinder(z1,z2,z3);
					if(States.oneCheck(z1,z3) || States.oneCheck(z2,z3)) zcap = States.minFinder(zcap,0.5f);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					
					
					
					patval +=25;
					float a = States.borderSafe(e, 1, TL,TR);
					float ncap = States.minFinder(a);
					if(ncap>0.5) patval+=25;
					else if(ncap<0.5) patval-=25;

					
					retval+=patval;
					if(patval>=100)e.addToEye(S1,S2);
					
					
//					retval +=75;
//					float a = States.borderSafe(e, 1, TL,TR);
//					float ncap = States.minFinder(a);
//					if(ncap>0.5) retval+=75;
//					else if(ncap<0.5) retval-=75;

					
					
				}
			}
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, straightTwoSide2Pattern,e.kscolour);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(side);
					counter++;	

					
					if (e.isThere(S1) || e.isThere(S2))continue;
					
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafe(e, 3, TL,TR,S1);
					float zcap = States.minFinder(z1);
					if(e.isEnemy(S2))zcap=1;
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					
					
					
					patval +=25;
					float a = States.borderSafe(e, 1, TL,TR);
					float ncap = States.minFinder(a);
					if(ncap>0.5) patval+=25;
					else if(ncap<0.5) patval-=25;

					
					retval+=patval;
					if(patval>=100)e.addToEye(S1,S2);
					
					
//					retval +=75;
//					float a = States.borderSafe(e, 1, TL,TR);
//					float ncap = States.minFinder(a);
//					if(ncap>0.5) retval+=75;
//					else if(ncap<0.5) retval-=75;
//					e.addToEye(S1,S2);

					
					
				}
			}
		}
		
		
		return retval;
	}


	
	
}
