package Go.SlickGo.Heuristics;

import java.util.ArrayList;


import Go.SlickGo.Evaluator;
import Go.SlickGo.Tuple;

public class Liberties  {
	Evaluator e;

	public Liberties (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		
		ArrayList<Tuple> nlist = e.cB.getNeedList(sstring, e.enemycolour,false);
		ArrayList<Tuple> liberties = e.cB.getLibs(sstring,false);
		
		retval += ((double)nlist.size()/(double)liberties.size()) * 100;

		return retval;
	}


	
	
}
