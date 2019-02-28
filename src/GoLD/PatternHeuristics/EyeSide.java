package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class EyeSide{
	
	static ArrayList<Pattern> eyeSidePattern = Pattern.sToPv2("xdlxrrxldS");

	public static int evaluate(ArrayList<Tuple> sstring,Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, eyeSidePattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple C = tlist.get(0).side(side);
					
					
					if(e.isThere(C)) continue;
					
					int patval =0;
					patval+=50;
					float a = States.borderSafeRel1(e, 1, TL,TR);
					float ncap = States.minFinder(a);
	

					if(ncap>0.5) patval+=50;
					else if(ncap<0.5) patval-=50;

					retval+=patval;
					if(patval>=100)e.addToEye(C);

				}

			}
			
		}

		
		return retval;

	}

	
	
}
