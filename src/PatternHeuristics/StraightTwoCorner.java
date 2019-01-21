package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;
import static PatternHeuristics.States.*;
public class StraightTwoCorner {
	Evaluator e;
	PatternSearcher ps;


	public StraightTwoCorner (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xldxdxr#", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;				

					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(side);


					
					if (e.isThere(S1) || e.isThere(S2))continue;
					
					ArrayList<States> states = States.addStates(e,TL,S1,S2);
					
					States[] k;
					if (e.isThere(TL)) {
						k = new States[]{A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

					}else if(e.isEnemy(TL)){
						k = new States[]{E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

					}else {
						k = new States[]{N,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

					}

					
					
				}
			}
		}
		
		

		
		
		return retval;
	}


	
	
}
