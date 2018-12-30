package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SideOneGap  {
	Evaluator e;
	PatternSearcher ps;

	public SideOneGap (Evaluator e){
		this.e=e;
	}

	
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxdxzdxdS", e.kscolour);
		ArrayList<Tuple> bar3 =ps.stringMatch(sstring, pattern);
		

		if (!bar3.isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
				Tuple S1 = bar3.get(0).side(side);
				Tuple S2 = S1.side(side.diag(diagSide));
				retval -= 200;
				if (e.isEnemy(S2))retval -= 1000;}
		

		return retval;
	}

	
	
}
