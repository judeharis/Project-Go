package Go.SlickGo.Heuristics;

import java.util.ArrayList;


import Go.SlickGo.Evaluator;
import Go.SlickGo.HeuristicI;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class LineTwoSideEscape implements HeuristicI {
	Evaluator e;
	PatternSearcher ps;

	public LineTwoSideEscape (Evaluator e){
		this.e=e;
	}

	
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrozdxr-dS", Stone.BLACK);
		ArrayList<Tuple> pString =ps.stringMatch(sstring, pattern);


		if (!pString.isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			Tuple S1 = pString.get(0).side(side);
			Tuple S2 = S1.side(side.diag(diagSide));
			Tuple S3 = S2.side(side.diag(diagSide));
			Tuple S4 = S3.side(side.diag(diagSide));
			
			Tuple B4 = S4.side(side.opp());

			if (e.isEnemies(B4)&& e.isTheres(S3)) {
				retval-=100;
				Tuple t = B4;
				while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(diagSide)))) {
					retval-=100;
					t = t.side(side.diag(diagSide));
				}
				if(e.isEnemy(t.side(side))) retval-=50;
			}
			
			if (e.isTheres(S3)) {
				retval+=100;
				Tuple t = S3;
				while (e.isThere(t.side(side.diag(diagSide))) && !ps.isCorner(t.side(side.diag(diagSide)))) {
					retval+=100;
					t = t.side(side.diag(diagSide));
				}
			}


			
			return retval;
		}
		
		
		pattern = Pattern.sToPv2("xrxrozdxr-dS", Stone.BLACK);
		pString =ps.stringMatch(sstring, pattern);
		
		if (!pString.isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			Tuple S1 = pString.get(0).side(side);
			Tuple S2 = S1.side(side.diag(diagSide));
			Tuple S3 = S2.side(side.diag(diagSide));
			Tuple S4 = S3.side(side.diag(diagSide));
			
			Tuple B4 = S4.side(side.opp());

			if (e.isEnemies(B4)&& e.isTheres(S3)) {
				retval-=100;
				Tuple t = B4;
				while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(diagSide)))) {
					retval-=100;
					t = t.side(side.diag(diagSide));
				}
				if(e.isEnemy(t.side(side))) retval-=50;
			}
			
			if (e.isTheres(S3)) {
				retval+=100;
				Tuple t = S3;
				while (e.isThere(t.side(side.diag(diagSide))) && !ps.isCorner(t.side(side.diag(diagSide)))) {
					retval+=100;
					t = t.side(side.diag(diagSide));
				}
			}


			
			return retval;
		}
		



		
		

		return retval;
	}


	
	
}
