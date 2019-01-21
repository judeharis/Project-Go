package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightTwoCornerOld {
	Evaluator e;
	PatternSearcher ps;


	public StraightTwoCornerOld (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xldxdxr#", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);

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
					
					retval += 150;	
					
					if (e.isEnemy(TL)) {
						retval-=100;
						if(e.isEnemy(S1))retval-=50;
						else if(e.isEnemy(S2))retval+=50;
					}else if (e.isEnemy(S1)) {
						retval-=50;
						if(e.isThere(TL))retval+=100;
					}else if(e.isThere(TL))retval+=50;
					
					
					
				}
			}
		}
		
		

		
		
		return retval;
	}


	
	
}
