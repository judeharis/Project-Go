package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class StraightTwoCorner {


	static ArrayList<Pattern> straightTwoCornerPattern = Pattern.sToPv2("xldxdxr#");

	public static int evaluate(ArrayList<Tuple> sstring , Evaluator e) {
		
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
		
		
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightTwoCornerPattern,e.kscolour);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;				

					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(side);


					
					if (e.isThere(S1) || e.isThere(S2))continue;
					
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafeRel1(e, 2, TL,S1);
					float zcap = States.minFinder(z1);
					if(e.isEnemy(S2))zcap=1;
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					
					patval +=25;
					float a = States.borderSafe(e, 1, TL);
					float ncap = States.minFinder(a);
					if(ncap>0.5) patval+=25;
					else if(ncap<0.5) patval-=25;

					
					retval+=patval;
					if(patval>=100)e.addToEye(S1,S2);
					
					
//					retval +=75;
//					float a = States.borderSafe(e, 1, TL);
//					float ncap = States.minFinder(a);
//
//					if(ncap>0.5) retval+=75;
//					else if(ncap<0.5) retval-=75;

					
					
				}
			}
		}
		
		

		
		
		return retval;
	}


	
	
}
