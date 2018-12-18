package Go.SlickGo.Heuristics;

import java.util.ArrayList;


import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;

public class ConnectStones {
	Evaluator e;
	PatternSearcher ps;

	public ConnectStones (Evaluator e){
		this.e=e;
	}


	public int evaluate(Tuple t) {
		int retval = 0;
		
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxzd-rx", Stone.BLACK);
		ps.tupleMatch(t, pattern);
		retval +=ps.getFoundCount() * 10;
		
		pattern = Pattern.sToPv2("xrxrxr", Stone.BLACK);
		ps.tupleMatch(t, pattern);
		retval +=ps.getFoundCount() * 5;
		


		
		

		return retval;
	}


	
	
}
