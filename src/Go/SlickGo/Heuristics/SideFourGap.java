package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SideFourGap  {
	Evaluator e;
	PatternSearcher ps;

	public SideFourGap (Evaluator e){
		this.e=e;
	}

	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxrxrxdxzdxdS", e.kscolour);
		ArrayList<Tuple> bar6 =ps.stringMatch(sstring, pattern);
		

		if (!bar6.isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			Tuple S1 = bar6.get(0).side(side);
			Tuple S2 = S1.side(side.diag(diagSide));
			Tuple S3 = S2.side(side.diag(diagSide));
			Tuple S4 = S3.side(side.diag(diagSide));
			Tuple S5 = S4.side(side.diag(diagSide));



			retval += 1000;
			if(e.isThere(S2) || e.isThere(S5)) return 0;
			
			if (e.isThere(S3) && e.isThere(S4))retval += 500;
			else if (e.isThere(S3) || e.isThere(S4))retval += 500;
			
			if (e.isEnemy(S3) || e.isEnemy(S4))retval -= 400;
			if (e.isEnemy(S3) && e.isEnemy(S4))retval -= 500;
			
			

			return retval;
		}
		
		pattern = Pattern.sToPv2("xrxrxrxrxrxzdxdS",e.kscolour);
		bar6 =ps.stringMatch(sstring, pattern);
		
		if (!bar6.isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			Tuple S1 = bar6.get(0).side(side);
			Tuple S2 = S1.side(side.diag(diagSide));
			Tuple S3 = S2.side(side.diag(diagSide));
			Tuple S4 = S3.side(side.diag(diagSide));
			Tuple S5 = S4.side(side.diag(diagSide));
			Tuple S6 = S5.side(side.diag(diagSide));
			Tuple S7 = S6.side(side.diag(diagSide));

			if (e.isThere(S5)) return 0;
			if (e.isEnemy(S3) && (e.isEnemy(S6) || e.isEnemy(S7)))retval -= 100;
			if (e.isEnemy(S6))retval -= 100;
			if (e.isEnemy(S3) && e.isThere(S4))retval += 500;
			if (e.isEnemy(S4) && e.isThere(S3))retval += 1000;
			

			if (e.isEnemies(S3,S6) && e.isThere(S4))retval -= 1100;
			

			
			return retval;
		}
		
		
		pattern = Pattern.sToPv2("xrxrxdxurxrxrxddS", e.kscolour);
		bar6 =ps.stringMatch(sstring, pattern);
		
		if (!bar6.isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			Tuple S1 = bar6.get(0).side(side);
			Tuple S2 = S1.side(side.diag(diagSide));
			Tuple S3 = S2.side(side.diag(diagSide));
			Tuple S4 = S3.side(side.diag(diagSide));
			Tuple S5 = S4.side(side.diag(diagSide));
			Tuple S6 = S5.side(side.diag(diagSide));

			
			if (e.isEnemies(S1))retval -= 100;
			if (e.isEnemies(S1,S6))retval -= 100;
			
			if (e.isEnemies(S5,S6))retval -= 200;
			
			
			return retval;
		}
		
		
		return retval;

	}
	
}
