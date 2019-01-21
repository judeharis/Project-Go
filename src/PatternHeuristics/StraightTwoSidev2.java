package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

import static PatternHeuristics.States.*;

public class StraightTwoSidev2 {
	Evaluator e;
	PatternSearcher ps;


	public StraightTwoSidev2 (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrdxzdlxdS", e.kscolour);
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

					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);


					
					if (e.isThere(S1) || e.isThere(S2))continue;
					
					
					ArrayList<States> states = States.addStates(e,TL,TR,S1,S2);
					States[] k;
					if (e.isThere(TL)) {
						k = new States[]{A,N,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,N,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{A,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{A,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}

						k = new States[]{A,E,N,E};
						if(States.stateCheck(states,k)){retval+=0;continue;}

						k = new States[]{A,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{A,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}

					}else if(e.isEnemy(TL)){
						k = new States[]{E,A,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,A,E,N};
						if(States.stateCheck(states,k)){retval+=0;continue;}

						k = new States[]{E,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{E,N,N,N};
						if(States.stateCheck(states,k)){retval+=0;continue;}

						k = new States[]{E,N,E,N};
						if(States.stateCheck(states,k)){retval+=0;continue;}

						k = new States[]{E,E,E,N};
						if(States.stateCheck(states,k)){retval+=0;continue;}

						k = new States[]{E,N,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{E,E,N,E};
						if(States.stateCheck(states,k)){retval+=0;continue;}

						k = new States[]{E,E,N,N};
						if(States.stateCheck(states,k)){retval+=0;continue;}

					}else {
						k = new States[]{N,N,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,A,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,A,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}

						k = new States[]{N,A,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}

						k = new States[]{N,N,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,E,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,N,N,E};
						if(States.stateCheck(states,k)){retval+=50;continue;}

						k = new States[]{N,E,N,E};
						if(States.stateCheck(states,k)){retval+=0;continue;}

						k = new States[]{N,E,N,N};
						if(States.stateCheck(states,k)){retval+=0;continue;}

					}


					
					
				}
			}
		}
		
		
		pattern = Pattern.sToPv2("xrdxdxzldxdxdS", e.kscolour);
		pMatches =ps.allStringMatchv2(sstring, pattern);

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
					Tuple TR = tlist.get(0).side(r);

					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(side);


					
					if (e.isThere(S1) || e.isThere(S2))continue;
					
					
					
					ArrayList<States> states = States.addStates(e,TL,TR,S1,S2);
					States[] k;
					if (e.isThere(TL)) {
						k = new States[]{A,N,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}
	
						k = new States[]{A,N,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}
	
						k = new States[]{A,A,E,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}
	
						k = new States[]{A,E,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{A,N,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}
	
						k = new States[]{A,A,N,E};
						if(States.stateCheck(states,k)){retval+=200;continue;}
	
						k = new States[]{A,E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{A,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{A,A,N,N};
						if(States.stateCheck(states,k)){retval+=200;continue;}
					}else if(e.isEnemy(TL)){

						k = new States[]{E,A,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{E,A,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{E,A,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{E,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{E,N,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}
	
						k = new States[]{E,E,E,N};
						if(States.stateCheck(states,k)){retval+=0;continue;}
	
						k = new States[]{E,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{E,E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{E,E,N,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}

					}else {
						
						
						k = new States[]{N,N,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{N,A,N,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}
	
						k = new States[]{N,A,E,N};
						if(States.stateCheck(states,k)){retval+=150;continue;}
	
						k = new States[]{N,A,N,E};
						if(States.stateCheck(states,k)){retval+=150;continue;}
	
						k = new States[]{N,N,E,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{N,E,E,N};
						if(States.stateCheck(states,k)){retval+=50;continue;}
	
						k = new States[]{N,N,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{N,E,N,E};
						if(States.stateCheck(states,k)){retval+=100;continue;}
	
						k = new States[]{N,E,N,N};
						if(States.stateCheck(states,k)){retval+=100;continue;}
					}

					
					
				}
			}
		}
		
		
		return retval;
	}


	
	
}
