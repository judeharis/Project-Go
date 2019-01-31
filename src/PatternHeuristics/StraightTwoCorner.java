package PatternHeuristics;

import java.util.ArrayList;
import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

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


					
					if (e.isThere(S1) || e.isThere(S2) || !e.isThere(TL))continue;
					String s = States.arrayToString(e,TL,S1,S2);
					if("ANN".equals(s)){retval+=200;continue;}
					if("AEN".equals(s)){retval+=200;continue;}
					if("ANE".equals(s)){retval+=200;continue;}
					
					
				}
			}
		}
		
		

		
		
		return retval;
	}


	
	
}
