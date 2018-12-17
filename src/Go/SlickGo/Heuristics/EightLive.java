package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.HeuristicI;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class EightLive implements HeuristicI {
	Evaluator e;
	PatternSearcher ps;

	public EightLive (Evaluator e){
		this.e=e;
	}


	
	@Override
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern6 = Pattern.sToPv2("xrxrxrxrxrxrxrxrozloddS", Stone.BLACK);
		ArrayList<Tuple> pString =ps.stringMatch(sstring, pattern6);	
		

		if (!pString .isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			
			
			if (!side.isEmpty()) {


			
				Tuple S1 = pString.get(0).side(side);
				Tuple S2 = S1.side(side.diag(diagSide));
				Tuple S3 = S2.side(side.diag(diagSide));
				Tuple S4 = S3.side(side.diag(diagSide));
				Tuple S5 = S4.side(side.diag(diagSide));
				Tuple S6 = S5.side(side.diag(diagSide));
				Tuple S7 = S6.side(side.diag(diagSide));
				Tuple S8 = S7.side(side.diag(diagSide));
				Tuple S9 = S8.side(side.diag(diagSide));
				
				Tuple S0 = S1.side(side.diag(!diagSide));

			
				if(!e.isTheres(S1,S7)) {
					
					if (e.isEnemy(S1) || e.isEnemy(S8)) retval-=100;
					if (e.isEnemies(S1,S8)) retval-=100;
					if (e.isEnemies(S1,S8) && (e.isEnemy(S0) || e.isEnemy(S9))) retval-=100;
					if (e.isEnemies(S1,S8,S0,S9)) retval-=100;
				
					
					//====
					if (e.isThere(S1) || e.isThere(S8)) retval+=100;
					if (e.isEnemy(S1) && e.isThere(S8)) retval+=100;
					if (e.isEnemy(S8) && e.isThere(S1)) retval+=100;

		


						
				}
			}
		
		}
		
		return retval;

	}

	
	
}
