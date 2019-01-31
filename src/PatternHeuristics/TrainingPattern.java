package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.MoveFinder;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;
import Go.SlickGo.VariationFinder;


public class TrainingPattern {


	public static int evaluate(ArrayList<Tuple> sstring,Evaluator e, int tk) {
		double retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		int counttrue  = 0;


		ArrayList<Pattern> pattern = Pattern.sToPv2("xrdxrdxldxldxluxluxrux");
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern, e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					int addToRet = 0;
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S2 = S0.side(side);
					Tuple S1 = S2.side(l);		
					Tuple S3 = S2.side(r);	
					Tuple S4 = S2.side(side);	
					Tuple A1 = tlist.get(0).side(l);
					Tuple A2 = A1.side2(r,r);
					Tuple B1 = A2.side2(r,side);
					Tuple B2 = B1.side2(side,side);
					Tuple C1 = B2.side2(side,l);
					Tuple C2 = C1.side2(l,l);
					Tuple D1 = C2.side2(l,side.opp());
					Tuple D2 = D1.side2(side.opp(),side.opp());
					counter++;
					
					
					if (e.isThere(S0) || e.isThere(S1) || e.isThere(S3) || e.isThere(S4))continue;
					if(!e.isTheres(A1) && !e.isTheres(A2)) continue;
					if(!e.isTheres(B1) && !e.isTheres(B2)) continue;
					if(!e.isTheres(C1) && !e.isTheres(C2)) continue;
					if(!e.isTheres(D1) && !e.isTheres(D2)) continue;
					if(!e.isTheres(S2)) continue;
					

					String s = States.arrayToString(e,A1,A2,B1,B1,C1,C2,D1,D2,S0,S1,S2,S3,S4);
					addToRet+=2000;
	

					if(addToRet!=0)counttrue++;
					retval +=addToRet;

					

				}

			}
			
		}

		if(retval > 0)retval = retval-tk;
		if(LearningValues.initalboard && counttrue >1) VariationFinder.repeated = counttrue;
		return (int) retval;

	}

	
	
}
