package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;
import Go.SlickGo.VariationFinder;

public class BentThreeSidev2Old {
	Evaluator e;
	PatternSearcher ps;

	public BentThreeSidev2Old (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrdxdxdSllluxurx", e.kscolour);
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
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(side);
					Tuple S0 = S1.side(l);
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple LT = S0.side2(side.opp(),l);
					Tuple LL = S0.side2(l,l);


					if(e.isEnemies(TL,TR) || e.isEnemies(LT,LL)  || e.isThere(S0) || e.isThere(S2)) continue;
					
					if(e.isTheres(S1,LT,TL)) retval+=1000;
					else if(e.isTheres(S1,LT,TR))retval+=1000;
					
					



				}

			}
			
		}
		
		
		pattern = Pattern.sToPv2("xrxrdxldxzldxdxdS", e.kscolour);
		pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					counter++;
					Tuple S1 = tlist.get(0).side(side);
					Tuple S0 = S1.side(side);
					Tuple S2 = S1.side(r);
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);
					Tuple RB = S2.side2(side,r);

					
					if(e.isEnemies(TR,RB) || e.isThere(S0) || e.isThere(S2) || e.isTheres(S1,RB)) continue;
					

//					if(e.isTheres(S1,TR))retval+=1000;

				}

			}
			
		}


		
		return retval;

	}

	
	
}
