package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;
import static PatternHeuristics.States.*;

public class StraightTwo {
	Evaluator e;
	PatternSearcher ps;


	public StraightTwo (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrdxzldxdrxrx", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;				
					UDLR r = side.diag(diagSide);

					UDLR l = side.diag(!diagSide);
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);
					Tuple BL = TL.side2(side,side);
					Tuple BR = TR.side2(side,side);
					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);


					

					
					if (e.isThere(S1) || e.isThere(S2))continue;
					
	
					
					ArrayList<States> states = States.addStates(e,TL,TR,BL,BR,S1,S2);
					States[] k;
					if (e.isThere(TL)) {
						k = new States[]{A,N,N,N,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,N,E,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,A,E,N,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,A,E,N,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,A,E,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,E,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,A,E,N,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,A,E,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,E,E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,A,E,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,A,E,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,N,E,A,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,N,E,A,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,E,E,A,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,N,E,A,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,E,E,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,E,E,A,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,N,E,N,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,E,E,N,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,E,E,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,N,E,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,N,E,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,E,E,N,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{A,N,E,E,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{A,E,E,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,E,E,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{A,N,E,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,N,N,N,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,N,A,N,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,E,A,N,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,E,A,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,E,A,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,N,A,E,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,A,A,E,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,A,N,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,A,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,N,A,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,N,N,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,N,E,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,A,N,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,N,N,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,E,N,A,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,E,N,N,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,E,N,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,N,N,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,N,N,N,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,N,A,N,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,E,A,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,E,A,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,N,A,E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,A,A,E,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,A,N,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,A,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,N,A,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,N,N,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,N,E,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,A,N,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,N,N,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,E,N,A,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,E,N,N,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{A,N,N,E,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{A,E,N,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,E,A,N,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,E,A,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{A,E,A,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,E,N,A,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,E,N,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{A,N,N,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,N,A,E,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,A,A,E,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,N,E,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,N,A,N,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,A,N,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,A,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,N,A,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,N,N,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,A,N,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,N,N,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

					}else if(e.isEnemy(TL)){
						k = new States[]{E,N,A,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,A,N,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{E,A,A,N,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{E,A,A,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{E,A,A,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,A,N,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{E,A,A,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{E,A,A,E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,A,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,A,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{E,N,A,A,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{E,N,A,A,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{E,E,A,A,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,N,A,A,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{E,E,A,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,A,A,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,N,A,N,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,A,N,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,A,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,N,A,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,N,A,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,A,N,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,A,E,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,E,A,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,A,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,A,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,N,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,N,A,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{E,A,E,A,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,A,E,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,N,A,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,N,A,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{E,A,E,N,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,A,E,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,E,E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,E,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,A,N,N,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,A,N,E,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,A,N,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,N,E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,A,N,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,N,N,A,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,N,E,A,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,E,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,E,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,E,A,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,N,A,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,E,N,A,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,N,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,N,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,N,A,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,N,N,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,N,E,N,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,E,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,E,N,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,E,E,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,N,N,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,E,N,N,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,E,N,E,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,N,E,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,N,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,E,N,N,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,N,E,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,E,N,N,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,N,N,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

					}else {
						k = new States[]{N,N,N,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,A,N,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,N,A,N,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,A,N,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,A,A,E,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,A,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,N,A,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,E,A,A,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,E,A,N,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,E,A,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,A,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,A,N,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,A,N,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,A,A,E,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,A,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,N,A,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,E,A,A,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,E,A,N,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,A,E,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,E,A,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,E,A,A,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,E,A,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,A,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,A,A,E,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,A,N,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,A,A,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,N,A,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,A,N,N,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,E,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,A,E,A,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,E,A,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,A,E,A,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,E,N,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,A,E,E,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,A,E,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,A,E,E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,A,E,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,A,N,N,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,N,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,A,N,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,A,N,N,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,N,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,A,N,E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,A,N,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,A,N,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{N,N,N,A,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,N,E,A,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,E,A,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,E,E,A,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,E,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,E,E,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,E,E,A,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,N,A,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,E,N,A,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,N,A,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,E,N,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,E,N,A,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,E,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,E,N,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,E,E,N,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,E,E,E,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,E,E,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,E,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,E,E,N,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,E,E,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,E,E,N,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,E,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,N,N,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,E,N,N,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,E,N,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,N,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,N,N,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,E,N,N,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,N,E,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,E,N,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,E,N,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,N,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

					}
				
						
					
				}
			}
		}
		
		
		return retval;
	}


	
	
}
