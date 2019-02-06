package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class FFive {


	static ArrayList<Pattern> fFivePattern = Pattern.sToPv2("xrdxrdxldxldxlxluxruxux");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, fFivePattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(side);
					Tuple S0 = S1.side(side);		
					Tuple D0 = S1.side(r);	
					Tuple C0 = S0.side(l);	
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = S2.side2(r,u);
					Tuple RT = TR.side2(r,side);
					Tuple RB = RT.side2(side, side);
					Tuple LT = S1.side2(l,l);
					
					Tuple BL = C0.side2(l,side);
					Tuple BR = S0.side2(side,r);

					counter++;
					
	
					
					
					if (e.isThere(S2) || e.isThere(D0) || e.isThere(C0))continue;
					int patval =0;
					
					patval +=500;
					float a1 = States.borderSafe(e, 2, S1,S0);
					float a2 = States.borderSafe(e, 4, TR,S2,D0,RT);
					float a3 = States.borderSafe(e, 4, D0,S0,BR,RB);

					
					
					if(e.isEnemies(D0,BR,RB,S1) || e.isEnemies(D0,BR,S0))a3+=0.5;
					if(e.isEnemies(TR,S2,D0))a2+=0.5;

					
					float acap = States.minFinder(a1,a2,a3);
					if(States.oneCheck(a1,a3) || States.oneCheck(a2,a3)) acap = States.minFinder(acap,0.5f);


					
					
					if(acap>0.5) patval+=500;
					else if(acap<0.5) patval-=500;

					
					patval +=200;
					float c1 = States.borderSafe(e, 2, S1,S0);
					float c2 = States.borderSafe(e, 2, TL,TR);
					float c3 = States.borderSafe(e, 2, RT,RB);
					float c4 = States.borderSafe(e, 2, LT,BL);
					float c5 = States.borderSafe(e, 3, BR,BL,S0);
					float c6 = States.borderSafe(e, 3, BR,LT,S0);
					float ccap = States.minFinder(c1,c2,c3,c4,c5);
					
					if(States.oneCheck(c1,c5) || (States.oneCheck(c1,c6))) ccap = States.minFinder(ccap,0.5f);
					else if(States.oneCheck(c4,c5) || (States.oneCheck(c4,c6))) ccap = States.minFinder(ccap,0.5f);
					else if(States.numCheck(1.5,c5,c6)) ccap = States.minFinder(ccap,0.5f);

					
					if(ccap>0.5) patval+=200;
					else if(ccap<0.5) patval-=200;
					
					if(e.isTheres(S0,LT)) patval /=2;
					retval+=patval;
					
				}
				
			}
			
		}
		


		
		return retval;

	}

	
	
}
