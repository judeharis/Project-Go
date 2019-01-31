package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class TwistedFourSide {

	
	static ArrayList<Pattern> twistedFourSide1Pattern = Pattern.sToPv2("xrdxdxldxdSzdlxdlxdx");
	static ArrayList<Pattern> twistedFourSide2Pattern = Pattern.sToPv2("xrxrdxdlxdSzdlxldx");
	
	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, twistedFourSide1Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S0 = S1.side(side);
					Tuple D0 = S0.side(l);		
					Tuple D1 = D0.side(side);	
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple RB = S0.side2(side,r);
					Tuple LT = TL.side2(side,l);
					counter++;
					

//					if (!e.isThere(S0) && !e.isThere(D0))continue;
//					if (!e.isTheres(S0,LT,TR) && !e.isTheres(S0,D0,TL) && !e.isTheres(S0,D0,TR))continue;
//					
//					if (e.isThere(S1) || e.isThere(D1))continue;
//					if (!e.isThere(S0) && !e.isThere(D0))continue;
//					if (!e.isTheres(S0,LT,TR) && !e.isTheres(S0,D0,TL) && !e.isTheres(S0,D0,TR))continue;
					
					if (e.isThere(S1) || e.isThere(D1))continue;
					if (e.isTheres(S0,D0)){
						retval+=1400;
						if(e.isTheres(TL) || e.isTheres(TR)) retval+=600;

					}else if (e.isThere(S0)){
						retval+=1100;
						if(e.isTheres(LT,TL) || e.isTheres(LT,TR)) retval+=900;

					}else if (e.isThere(D0)){
						retval+=1100;
						if(e.isTheres(RB,TL) || e.isTheres(RB,TR)) retval+=900;

					}
					
				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, twistedFourSide2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple D0 = S0.side(side);		
					Tuple D1 = D0.side(l);	
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);
					Tuple RB = TR.side2(side,side);
					Tuple LT = TL.side2(side,l);
					counter++;
					
//					if (e.isThere(S1) || e.isThere(D1))continue;
//					if (!e.isTheres(LT,D0,S0))continue;
//					if (!e.isThere(TR) && !e.isThere(RB))continue;

					
					if (e.isThere(S1) || e.isThere(D1))continue;
	
					if (e.isTheres(S0,D0)){
						retval+=1200;
						if(e.isTheres(LT)) retval+=500;
						if(e.isTheres(RB) || e.isTheres(TR)) retval+=300;

					}else if (e.isThere(S0)){
						retval+=1000;
						if(e.isTheres(LT)) retval+=700;
						if(e.isTheres(RB,TR)) retval+=300;

					}else if (e.isThere(D0)){
						retval+=800;
						if(e.isTheres(LT,TL)) retval+=800;
						if(e.isTheres(RB) || e.isThere(TR)) retval+=400;

					}
					
					
				}
				
			}
			
		}
		


				

		
		return retval;

	}

	
	
}
