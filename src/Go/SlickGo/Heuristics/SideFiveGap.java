package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.HeuristicI;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SideFiveGap implements HeuristicI {
	Evaluator e;

	public SideFiveGap (Evaluator e){
		this.e=e;
	}

	@Override
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ArrayList<Tuple> bar7 = e.checkStringForBar(sstring, 7,1);
		if (!bar7.isEmpty()) {
			UDLR side = e.distFromSide(bar7, 1);
			if (!side.isEmpty()) {
				Tuple S1 = bar7.get(0).side(side);
				Tuple S2 = S1.side(side.diag(false));
				Tuple S3 = S2.side(side.diag(false));
				Tuple S4 = S3.side(side.diag(false));
				Tuple S7= bar7.get((bar7.size() - 1)).side(side);
				Tuple S6 = S7.side(side.diag(true));
				Tuple S5 = S6.side(side.diag(true));


				if (e.isThere(S1) && e.isThere(S7)) {
					retval += 500;
					if (e.isThere(S2))retval += 300;
					if (e.isThere(S3))retval += 500;
					if (e.isThere(S4))retval += 1000;
					if (e.isThere(S5))retval += 500;
					if (e.isThere(S6))retval += 300;

				}}}
		
		

		return retval;
	}

	
	
}
