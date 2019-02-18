package GoLD.Heuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class Cut {
	static ArrayList<Pattern> crossCutPattern = Pattern.sToPv2("xrdx");

	
	
	static public int evaluate(ArrayList<Tuple> sstring , Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, crossCutPattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if (!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR d = ps.dirNumToDir(counter);
					UDLR r = d.diag(diagSide);
//					UDLR u = d.opp();
//					UDLR l = d.diag(!diagSide);
					counter++;

							
					
					Tuple D = tlist.get(0).side(d);
					Tuple R = tlist.get(0).side(r);
					
					retval+=20;
					
					if(e.isEnemy(R))retval-=10;
					if(e.isEnemy(D))retval-=10;
					if(e.isEnemies(D,R))retval-=10;
					
					if(e.isThere(R) && !e.isEnemy(D))retval-=10;
					if(e.isThere(D) && !e.isEnemy(R))retval-=10;
					
					if(e.isThere(R) && e.isEnemy(D))retval+=5;
					if(e.isThere(D) && e.isEnemy(R))retval+=5;
					
						
				}
		
			}
		


		}
		return retval;
		
	}
	

}
