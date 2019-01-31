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
					if(!e.isTheres(TL,TR)) continue;
					
					
					String s = States.arrayToString(e,TL,TR,S1,S2);
					if("AANN".equals(s)){retval+=200;continue;}
					if("AAEN".equals(s)){retval+=200;continue;}
					if("AANE".equals(s)){retval+=200;continue;}

					
					
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
					if(!e.isTheres(TL,TR)) continue;

					String s = States.arrayToString(e,TL,TR,S1,S2);
					if("AANN".equals(s)){retval+=200;continue;}
					if("AAEN".equals(s)){retval+=200;continue;}
					if("AANE".equals(s)){retval+=200;continue;}


					
					
				}
			}
		}
		
		
		return retval;
	}


	
	
}
