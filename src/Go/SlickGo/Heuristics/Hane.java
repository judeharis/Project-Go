package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class Hane {
	static ArrayList<Pattern> hanePattern = Pattern.sToPv2("xro");

	
	
	static public int evaluate(ArrayList<Tuple> sstring , Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, hanePattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if (!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR d = ps.dirNumToDir(counter);
					UDLR u = d.opp();
//					UDLR r = d.diag(diagSide);
					UDLR l = d.diag(!diagSide);
					counter++;

							
					
					Tuple U = tlist.get(0).side(u);
					Tuple D = tlist.get(0).side(d);
					Tuple L = tlist.get(0).side(l);
					
					if(e.isEnemy(U))retval-=10;
					if(e.isEnemy(D))retval-=10;
					if(e.isEnemy(L))retval-=5;
				
						
				}
		
			}
		


		}
		return retval;
		
	}
	

}
