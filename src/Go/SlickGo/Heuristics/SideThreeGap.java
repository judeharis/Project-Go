package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.HeuristicI;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SideThreeGap implements HeuristicI {
	Evaluator e;

	public SideThreeGap (Evaluator e){
		this.e=e;
	}

	@Override
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;

		ArrayList<Tuple> bar5 = e.checkStringForBar(sstring, 5,1);
		if (!bar5.isEmpty()) {
			UDLR side =e.distFromSide(bar5, 1);
			if (!side.isEmpty()) {
				Tuple S1 = bar5.get(0).side(side);
				Tuple S5= bar5.get((bar5.size() - 1)).side(side);
				Tuple S2 = S1.side(side.diag(false));
				Tuple S3 = S2.side(side.diag(false));


				if (e.isThere(S1) && e.isThere(S5)) {
					retval -= 500;
					if (e.isThere(S3))retval += 10000;
					if (e.isEnemy(S3))retval -= 10000;
				}}}
		

		return retval;
	}

	
	
}
