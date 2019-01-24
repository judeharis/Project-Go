package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;
import Go.SlickGo.VariationFinder;


public class TrainingPattern {
	Evaluator e;
	PatternSearcher ps;

	public TrainingPattern (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring,int tk) {
		double retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		int counttrue  = 0;


		ArrayList<Pattern> pattern = Pattern.sToPv2("xdrxdrxruxruxuS", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					counter++;
					Tuple S0 = tlist.get(0).side(r);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple D0 = S1.side(side);
					
					Tuple A1 =  tlist.get(0).side(l);
					Tuple A2 = A1.side2(side,r);
					Tuple B1 = A2.side2(side,r);
					Tuple B2 = B1.side2(r,r);
					Tuple C1 = B2.side2(side.opp(),r);
					Tuple C2 = S1.side2(side.opp(),r);




					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;

					
			

					if(e.isTheres(S1,A2,B1,C1)) {retval+=1500; counttrue++;}
					else if(e.isTheres(S1,A2,B2,C1)) {retval+=1500; counttrue++;}

					

					
//					if(e.isTheres(S1,LT,TL)) {retval+=500; counttrue++;}
//					else if(e.isTheres(S1,LB,TR)) {retval+=1000; counttrue++;}
//					else if(e.isTheres(S1,LT,TR)) {retval+=1000; counttrue++;}
//					else if(e.isTheres(S1,LB,TL)) {retval+=1000; counttrue++;}
					

				}

			}
			
		}

		if(retval > 0)retval = retval-tk;
		if(LearningValues.initalboard && counttrue >1) VariationFinder.repeated = counttrue;
		return (int) retval;

	}

	
	
}
