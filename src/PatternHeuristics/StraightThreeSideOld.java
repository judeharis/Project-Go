package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;


import static PatternHeuristics.States.*;

public class StraightThreeSideOld {
	Evaluator e;
	PatternSearcher ps;

	public StraightThreeSideOld (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrdxzldxdS", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					counter++;
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					
					
					
					Tuple TL = tlist.get(0).side(l);

					Tuple TR = S2.side2(r,side.opp());


					if (e.isThere(S0) || e.isThere(S2))continue;

					retval +=200;
					
					if(e.isThere(TL) || e.isThere(TR) ||  e.isThere(S1)) {
						if(e.isTheres(TL,TR,S1))retval+=600;
						else if(e.isTheres(TL,TR)) {
							retval+=350;
							if(e.isEnemy(S1))retval-=350;
						}else if(e.isTheres(TR,S1)  || e.isTheres(TL,S1)) {
							retval+=200;
							if(e.isEnemy(TL) || e.isEnemy(TR)) retval-=400;
						}else if(e.isThere(TL)) {
							if(e.isEnemies(TR,S2,S1)) retval-=200;
							else if(e.isEnemies(TR,S0,S2)) retval-=100;
							else if(e.isEnemies(TR,S1,S0)) retval+=0;
							else if(e.isEnemies(TR,S2)) retval-=150;
							else if(e.isEnemies(TR,S1)) retval-=100;
							else if(e.isEnemies(TR) || e.isEnemies(S2)) retval-=50;

						}else if(e.isThere(TR)) {
							if(e.isEnemies(TL,S0,S1)) retval-=200;
							else if(e.isEnemies(TL,S0,S2)) retval-=100;
							else if(e.isEnemies(TL,S1,S2)) retval+=0;
							else if(e.isEnemies(TL,S0)) retval-=150;
							else if(e.isEnemies(TL,S1)) retval-=100;
							else if(e.isEnemies(TL) || e.isEnemies(S0)) retval-=50;
		
						}else if(e.isThere(S1)) retval-=200;
						
						
					}else if(e.isEnemy(TL) || e.isEnemy(TR) ||  e.isEnemy(S1) || e.isEnemy(S0) ||  e.isEnemy(S2)) {
						if(e.isEnemies(TL,S0,S1) || e.isEnemies(TR,S2,S1) || e.isEnemies(TR,TL,S0) || e.isEnemies(TR,TL,S2)) retval-=200;
						else if(e.isEnemies(TL,TR,S1) || e.isEnemies(TL,S0,S2) || e.isEnemies(TR,S0,S2)) retval-=150;
						else if(e.isEnemies(TL,S1,S2) || e.isEnemies(TR,S0,S1)) retval-=100;
						else if(e.isEnemies(TL,S1,S2) || e.isEnemies(TR,S0,S1)) retval-=100;
						else if(e.isEnemies(TL,TR) || e.isEnemies(TL,S0) || e.isEnemies(TR,S2)) retval-=200;
						else if(e.isEnemies(TL,S1) || e.isEnemies(TR,S1) || e.isEnemies(TL,S2) || e.isEnemies(TR,S0)) retval-=150;
						else if(e.isEnemies(S0,S1) || e.isEnemies(S1,S2) || e.isEnemies(S0,S2)) retval-=100;
					}
					
			
				}
				
			}
			
		}
		
		
		
		pattern = Pattern.sToPv2("xrdxdxdxzdlxdxdxdS", e.kscolour);
		pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					counter++;
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);
					
					
					
					Tuple TL = tlist.get(0).side(l);

					Tuple TR = tlist.get(0).side(r);

					if (e.isThere(S0) || e.isThere(S2))continue;
					
			
					ArrayList<States> states = States.addStates(e,TL,TR,S0,S1,S2);

					States[] k = new States[]{N,N,N,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{A,N,N,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{A,N,E,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{A,N,N,A,N};
					if(States.stateCheck(states,k))retval+=800;

					k = new States[]{A,E,N,A,N};
					if(States.stateCheck(states,k))retval+=800;

					k = new States[]{A,A,N,A,N};
					if(States.stateCheck(states,k))retval+=800;

					k = new States[]{A,A,E,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{A,A,E,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{A,A,E,N,E};
					if(States.stateCheck(states,k))retval+=1000;

					k = new States[]{A,N,E,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{A,E,E,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{A,N,E,N,E};
					if(States.stateCheck(states,k))retval+=1000;

					k = new States[]{A,E,E,N,E};
					if(States.stateCheck(states,k))retval+=1000;

					k = new States[]{A,E,E,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{A,N,N,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{A,A,N,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{A,A,N,E,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{A,N,N,E,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{A,E,N,E,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{A,E,N,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{A,N,N,N,E};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{A,A,N,N,E};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{A,E,N,N,E};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{A,E,N,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{A,A,N,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{N,N,N,A,N};
					if(States.stateCheck(states,k))retval+=800;

					k = new States[]{E,N,N,A,N};
					if(States.stateCheck(states,k))retval+=400;

					k = new States[]{E,A,N,A,N};
					if(States.stateCheck(states,k))retval+=800;

					k = new States[]{E,E,N,A,N};
					if(States.stateCheck(states,k))retval+=0;

					k = new States[]{N,E,N,A,N};
					if(States.stateCheck(states,k))retval+=400;

					k = new States[]{N,A,N,A,N};
					if(States.stateCheck(states,k))retval+=800;

					k = new States[]{N,A,N,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{E,A,N,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{E,A,E,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{E,A,E,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{E,A,E,N,E};
					if(States.stateCheck(states,k))retval+=1000;

					k = new States[]{E,A,N,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{E,A,N,E,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{E,A,N,N,E};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{N,A,E,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{N,A,E,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{N,A,E,N,E};
					if(States.stateCheck(states,k))retval+=1000;

					k = new States[]{N,A,N,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{N,A,N,E,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{N,A,N,N,E};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{E,N,N,N,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{E,N,E,N,N};
					if(States.stateCheck(states,k))retval+=150;

					k = new States[]{E,N,E,E,N};
					if(States.stateCheck(states,k))retval+=100;

					k = new States[]{E,E,E,E,N};
					if(States.stateCheck(states,k))retval+=0;

					k = new States[]{E,N,E,N,E};
					if(States.stateCheck(states,k))retval+=550;

					k = new States[]{E,E,E,N,E};
					if(States.stateCheck(states,k))retval+=100;

					k = new States[]{E,E,E,N,N};
					if(States.stateCheck(states,k))retval+=50;

					k = new States[]{E,N,N,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{E,N,N,E,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{E,E,N,E,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{E,E,N,E,N};
					if(States.stateCheck(states,k))retval+=100;

					k = new States[]{E,N,N,N,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{E,E,N,N,E};
					if(States.stateCheck(states,k))retval+=150;

					k = new States[]{E,E,N,N,N};
					if(States.stateCheck(states,k))retval+=150;

					k = new States[]{N,N,E,N,N};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{N,N,E,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{N,E,E,E,N};
					if(States.stateCheck(states,k))retval+=100;

					k = new States[]{N,N,E,N,E};
					if(States.stateCheck(states,k))retval+=1000;

					k = new States[]{N,E,E,N,E};
					if(States.stateCheck(states,k))retval+=550;

					k = new States[]{N,E,E,N,N};
					if(States.stateCheck(states,k))retval+=150;

					k = new States[]{N,N,N,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{N,N,N,E,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{N,E,N,E,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{N,E,N,E,N};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{N,N,N,N,E};
					if(States.stateCheck(states,k))retval+=600;

					k = new States[]{N,E,N,N,E};
					if(States.stateCheck(states,k))retval+=200;

					k = new States[]{N,E,N,N,N};
					if(States.stateCheck(states,k))retval+=200;

		
			
				}
				
			}
			
		}


		
		return retval;

	}

	
	
}
