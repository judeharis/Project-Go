package Go.SlickGo.Heuristics;

import java.util.ArrayList;


import Go.SlickGo.Evaluator;
import Go.SlickGo.Tuple;

public class Liberties  {

	public static int evaluate(ArrayList<Tuple> sstring,Evaluator e) {
		int retval = 0;
		
		ArrayList<Tuple> nlist = e.cB.getNeedList(sstring, e.enemycolour,false);
		ArrayList<Tuple> liberties = e.cB.getLibs(sstring,false);
		
//		retval += ((double)nlist.size()/(double)liberties.size()) * 100;
		e.checkedPoints.addAll(nlist);
		
		retval +=((double)liberties.size() - ((double)liberties.size()-(double)nlist.size())) * 1;

		return retval;
	}


	
	
}
