package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;


public class Eye{
	
	static ArrayList<Pattern> eyePattern = Pattern.sToPv2("xdlxrrxldx");
	

	public static int evaluate(ArrayList<Tuple> sstring,Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv3(sstring, eyePattern,e.kscolour);

		
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
					Tuple BL = TL.side2(side,side);
					Tuple BR = TR.side2(side,side);
					Tuple C = tlist.get(0).side(side);
					Tuple TOL = ps.tolPoint.get(counter);
					counter++;
					
					
					
					if(e.isThere(C)) continue;
					
					float tol = States.borderSafeRel2(e, 1, TOL);
//					if(TOL!=null)continue;
					
					int patval =0;
					patval +=50;
					float bs1 = States.borderSafeRel1(e, 2, TL,TR,BL,BR);
					float ncap = States.minFinder(tol,bs1);
	
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
