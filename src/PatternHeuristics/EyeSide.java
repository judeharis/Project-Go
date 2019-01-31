package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;
public class EyeSide {


	static ArrayList<Pattern> eyeSidePattern = Pattern.sToPv2("xdlxrrxldS");

	public static int evaluate(ArrayList<Tuple> sstring , Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, eyeSidePattern,e.kscolour);
		
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
					Tuple C = tlist.get(0).side(side);
					counter++;
					
					
					if(e.isThere(C)) continue;
					String s = States.arrayToString(e,TL,TR,C);
					if("AAN".equals(s)){retval+=100;continue;}

					



				}

			}
			
		}

		
		return retval;

	}

	
	
}
