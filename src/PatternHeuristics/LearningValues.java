package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.MoveFinder;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;
import Go.SlickGo.VariationFinder;




import static PatternHeuristics.States.*;


public class LearningValues {
	Evaluator e;
	PatternSearcher ps;
	public static boolean initalboard = false;
	public LearningValues (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring ,int tk) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxzdlxdxrrX", e.kscolour);
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
					Tuple TL = tlist.get(0).side(l);

					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);
					Tuple S0 = S1.side(side);
					
					

					
					if(e.isThere(S0) || e.isThere(S2) || e.isThere(S1)){
						if (initalboard) MoveFinder.learnexit=true;
						continue;
					}

		
					String s = States.arrayToString(e,TL,S0,S1,S2);

//					print(s);
		
					String prog = "\n	if(\""+s+"\".equals(s)){retval+=";
					
		
					if (initalboard) {
						VariationFinder.skip=false;
						VariationFinder.states=s;
						VariationFinder.progstring=prog;
						VariationFinder.tk=tk;
						initalboard=false;
					}


					

				
			
				}
				
			}
			
		}


		
		return retval;

	}


    public static void print(Object o){
        System.out.println(o);
    }


	public ArrayList<States> addStates(Evaluator e,Tuple...ts) {
		ArrayList<States> states = new ArrayList<States>();
		for (Tuple t :ts){
			if (e.isThere(t)) states.add(A);
			else if (e.isEnemy(t))  states.add(E);
			else states.add(N);
		}
		return states;
	}
	
}
