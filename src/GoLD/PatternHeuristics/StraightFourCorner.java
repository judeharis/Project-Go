package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class StraightFourCorner {


	static ArrayList<Pattern> straightFourCornerPattern = Pattern.sToPv2("xdlxdxdxdxr#");

	
	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightFourCornerPattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);		
					Tuple S3 = S2.side(side);	
					Tuple TL = tlist.get(0).side(l);
					counter++;
					
					
					
					
					if (e.isThere(S0) || e.isThere(S3))continue;
					
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					patval +=650;
					float a = States.borderSafeRel2(e, 1, TL);
					float b = States.borderSafeRel2(e, 2, S1,S2);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
//					retval +=700;
//					float a = States.borderSafe(e, 1, TL);
//					float b = States.borderSafe(e, 2, S1,S2);
//					float ncap = States.minFinder(a,b);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;

					


				

					
				}
				
			}
			
		}
		

		
		return retval;

	}

	
	
}
