package Go.SlickGo.Heuristics;

import java.util.ArrayList;


import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SideThreeGap  {
	Evaluator e;
	PatternSearcher ps;

	public SideThreeGap (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxrxdxzdxdS", e.kscolour);
		ArrayList<Tuple> bar5 =ps.stringMatch(sstring, pattern);


		if (!bar5.isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			Tuple S1 = bar5.get(0).side(side);
			Tuple S2 = S1.side(side.diag(diagSide));
			Tuple S3 = S2.side(side.diag(diagSide));
			Tuple S4 = S3.side(side.diag(diagSide));


			if (e.isEnemy(S3))retval -= 200;
			
			if (!e.isThere(S2) && !e.isThere(S4))retval += 200;

			
			return retval;
		}
		
		pattern = Pattern.sToPv2("xrxrxdxurxrxddS", e.kscolour);
		bar5 =ps.stringMatch(sstring, pattern);
		
		if (!bar5.isEmpty()) {
			UDLR side = ps.dirNumToDir();
			boolean diagSide= ps.dirSideToBool();
			Tuple S1 = bar5.get(0).side(side);
			Tuple S2 = S1.side(side.diag(diagSide));
			Tuple S3 = S2.side(side.diag(diagSide));
			Tuple S4 = S3.side(side.diag(diagSide));
			Tuple S5 = S4.side(side.diag(diagSide));

			if (e.isEnemy(S1) && e.isThere(S5))retval -= 100;
			if (e.isEnemy(S5) && e.isThere(S1))retval -= 100;
			
			if (e.isThere(S5))retval += 100;
			if (e.isThere(S1))retval += 100;
			return retval;
		}
		


		
		

		return retval;
	}


	
	
}
