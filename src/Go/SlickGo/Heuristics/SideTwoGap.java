package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SideTwoGap {
	Evaluator e;

	public SideTwoGap (Evaluator e){
		this.e=e;
	}

	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;

		ArrayList<Tuple> bar4 = e.checkStringForBar(sstring, 4,1);
		if (!bar4.isEmpty()) {
			UDLR side = e.distFromSide(bar4, 1);
			if (!side.isEmpty()) {
				Tuple S1 = bar4.get(0).side(side);
				Tuple S4 = bar4.get((bar4.size() - 1)).side(side);
				Tuple S2 = S1.side(side.diag(false));
				Tuple S3 = S2.side(side.diag(true));

				if (e.isThere(S1) && e.isThere(S4)) {
					retval -= 200;
					if (e.isThere(S2) && e.isThere(S3))retval -= 200;
					else if (e.isThere(S2) || e.isThere(S3))retval -= 100;}}}


		return retval;
	}

	
	
}
