package GoLD.Heuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

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
					UDLR r = d.diag(diagSide);
					UDLR l = d.diag(!diagSide);
					counter++;

							
					
					Tuple U = tlist.get(0).side(u);
					Tuple UR =U.side(r);
					Tuple D = tlist.get(0).side(d);
					Tuple DR = D.side(r);
					Tuple L = tlist.get(0).side(l);
					
					Tuple DL = D.side(l);
					Tuple UL = U.side(l);
					
					
					if(e.isEnemy(U) && !e.isThere(UR))retval-=10;
					if(e.isEnemy(D) && !e.isThere(DR))retval-=10;
					if(e.isEnemy(L))retval-=5;
					
					e.addToCheckedPoints(U,UR,D,DR,L);
				
					if(e.isEnemy(U) || e.isEnemy(L))e.addToCheckedPoints(UL);
					if(e.isEnemy(D) || e.isEnemy(L))e.addToCheckedPoints(DL);
				}
		
			}
		


		}
		return retval;
		
	}
	

}
