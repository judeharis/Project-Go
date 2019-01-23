package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;
import static PatternHeuristics.States.*;

public class EyeCorner {
	Evaluator e;
	PatternSearcher ps;

	public EyeCorner (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xd#lx", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR l = side.diag(!diagSide);
					counter++;
					Tuple TL = tlist.get(0).side(l);


					Tuple C = tlist.get(0).side(side);
					
					
					if(e.isThere(C)) continue;

					ArrayList<States> states = States.addStates(e,TL,C);
					States[] k;
					if (e.isThere(TL)) {
						k = new States[]{A,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

					}else if(e.isEnemy(TL)){

					}else {
						k = new States[]{N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

					}


				}

			}
			
		}

		
		return retval;

	}

	
	
}
