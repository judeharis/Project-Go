package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SideOneGap  {
	Evaluator e;

	public SideOneGap (Evaluator e){
		this.e=e;
	}

	
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ArrayList<Tuple> bar3 = e.checkStringForBar(sstring, 3,1);
		if (!bar3.isEmpty()) {
			UDLR side = e.distFromSide(bar3, 1);
			if (!side.isEmpty()) {
				Tuple S1 = bar3.get(0).side(side);
				Tuple S3 = bar3.get((bar3.size() - 1)).side(side);
				Tuple S2 = S1.side(side.diag(false));
				if (e.isThere(S1) && e.isThere(S3)) {
					retval -= 200;
					if (e.isEnemy(S2))retval -= 1000;}}}
		

		return retval;
	}

	
	
}
