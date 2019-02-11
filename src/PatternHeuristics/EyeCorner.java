package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class EyeCorner {

	static ArrayList<Pattern> eyeCornerPattern = Pattern.sToPv2("xd#lx");
	
	public static int evaluate(ArrayList<Tuple> sstring,Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, eyeCornerPattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple C = tlist.get(0).side(side);
					counter++;
					

					if(e.isTheres(C)) continue;
					int patval =0;
					patval+=50;
					float a = States.borderSafe(e, 1, TL);
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
