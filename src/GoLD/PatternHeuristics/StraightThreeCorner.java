package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class StraightThreeCorner {
	
	static ArrayList<Pattern> straightThreeCornerPattern = Pattern.sToPv2("xldxdxdxr#");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightThreeCornerPattern,e.kscolour);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);
					counter++;		


					
					if (e.isThere(S0) || e.isThere(S2))continue;
					
					
					int patval =0;	
					patval+=100;
					if(e.isThere(S1))patval=0;
					

					
					patval +=450;
					float a = States.borderSafe(e, 1, TL);
					float b = States.borderSafe(e, 1, S1);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) patval+=450;
					else if(ncap<0.5) patval-=450;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2);
					
					
//					retval +=500;
//					float a = States.borderSafe(e, 1, TL);
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
