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

	public static boolean initalboard = false;





	public static int evaluate(ArrayList<Tuple> sstring,Evaluator e ,int tk) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrdxrdxldxldxluxluxrux");
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern,e.kscolour);
		
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				boolean breakAfter = false;
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S2 = S0.side(side);
					Tuple S1 = S2.side(l);		
					Tuple S3 = S2.side(r);	
					Tuple S4 = S2.side(side);	
					Tuple A1 = tlist.get(0).side(l);
					Tuple A2 = A1.side2(r,r);
					Tuple B1 = A2.side2(r,side);
					Tuple B2 = B1.side2(side,side);
					Tuple C1 = B2.side2(side,l);
					Tuple C2 = C1.side2(l,l);
					Tuple D1 = C2.side2(l,side.opp());
					Tuple D2 = D1.side2(side.opp(),side.opp());
					counter++;
					
					
					if (e.isThere(S0) || e.isThere(S1) || e.isThere(S3) || e.isThere(S4)){if(initalboard){MoveFinder.learnexit=true;continue;}};
					if(!e.isTheres(A1) && !e.isTheres(A2)) {if(initalboard){MoveFinder.learnexit=true;continue;}};
					if(!e.isTheres(B1) && !e.isTheres(B2)) {if(initalboard){MoveFinder.learnexit=true;continue;}};
					if(!e.isTheres(C1) && !e.isTheres(C2)) {if(initalboard){MoveFinder.learnexit=true;continue;}};
					if(!e.isTheres(D1) && !e.isTheres(D2)) {if(initalboard){MoveFinder.learnexit=true;continue;}};
					if(!e.isTheres(S2)) {if(initalboard){MoveFinder.learnexit=true;continue;}};
					

					String s = States.arrayToString(e,A1,A2,B1,B1,C1,C2,D1,D2,S0,S1,S2,S3,S4);



//					{if(initalboard){MoveFinder.learnexit=true;continue;}}
//					print(s);
		
					String prog = "\n	if(\""+s+"\".equals(s)){retval+=";
					
		
					if (initalboard) {
						VariationFinder.skip=false;
						VariationFinder.states=s;
						VariationFinder.progstring=prog;
						VariationFinder.tk=tk;
						breakAfter=true;
					}


					

				
			
				}
				if(breakAfter)break;
				
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
