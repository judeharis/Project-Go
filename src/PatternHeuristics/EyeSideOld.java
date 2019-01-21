package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class EyeSideOld {
	Evaluator e;
	PatternSearcher ps;

	public EyeSideOld (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xdlxrrxldS", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
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

					

					
					Tuple C = tlist.get(0).side(side);
					
					
					if(e.isThere(C)) continue;
					if(e.isEnemy(TL) || e.isEnemy(TR) || e.isThere(C))continue;
//					if(!(e.isEnemies(T,TL,TR) || e.isEnemies(L,TL,BL) || e.isEnemies(B,BL,BR) || e.isEnemies(R,TR,BR)) && e.isThere(C))retval-=50;
//					if(e.isThere(C))retval-=50;
					
					if(e.isThere(TL) || e.isThere(TR))retval+=50;
					if(e.isTheres(TL,TR))retval+=50;



				}
				counter++;
			}
			
		}

		
		return retval;

	}

	
	
}
