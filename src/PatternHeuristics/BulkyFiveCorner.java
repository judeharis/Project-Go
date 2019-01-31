package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BulkyFiveCorner {
	
	static ArrayList<Pattern> bulkyFiveCorner1Pattern = Pattern.sToPv2("xrxdd#zldxldx");
	static ArrayList<Pattern> bulkyFiveCorner2Pattern = Pattern.sToPv2("xrxzldxdxrdxr#");
	static ArrayList<Pattern> bulkyFiveCorner3Pattern = Pattern.sToPv2("xrxrxddXzldxdx");
	static ArrayList<Pattern> bulkyFiveCorner4Pattern = Pattern.sToPv2("xrdxzdlxdxdxrr#");


	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, bulkyFiveCorner1Pattern,e.kscolour);
		
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple D0 = tlist.get(0).side(side);
					Tuple D1 = D0.side(r);
					Tuple S1 = D0.side(side);
					Tuple S0 = S1.side(l);
					Tuple S2 = S1.side(r);
					
					Tuple TL = D0.side2(u,l);
					Tuple LT = D0.side2(l,l);

					counter++;
					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					if(e.isTheres(S1)) retval+=2000;
					if(e.isTheres(TL,LT)) retval+=1000;
					
					
//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(!e.isTheres(S1)) continue;
//					
//					retval+=2000;

				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, bulkyFiveCorner2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple D0 = tlist.get(0).side(side);
					Tuple D1 = D0.side(side);
					Tuple S0 = D0.side(r);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);
					
					Tuple TL = D0.side2(u,l);
					Tuple BL = D1.side2(side,l);
					counter++;

					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					if(e.isTheres(S1)) retval+=2000;
					if(e.isTheres(TL,BL)) retval+=1000;


//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(!e.isTheres(S1)) continue;
//					
//					retval+=2000;
					



				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, bulkyFiveCorner3Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple D0 = S0.side(side);
					Tuple D1 = D0.side(r);

					Tuple TL = S0.side2(u,l);

					counter++;
		
					

					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					if(e.isTheres(S1)) retval+=2000;
					if(e.isTheres(TL)) retval+=1000;

//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(!e.isTheres(S1)) continue;
//					
//					retval+=2000;
//			

				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, bulkyFiveCorner4Pattern,e.kscolour);
		
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
					Tuple D1 = S1.side(r);
					Tuple D0 = D1.side(side);
					
					Tuple TL = S2.side2(u,l);
					Tuple TR = S2.side2(u,r);
					counter++;

					

					
					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					if(e.isTheres(S1)) retval+=2000;
					if(e.isTheres(TL,TR)) retval+=1000;


//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(!e.isTheres(S1)) continue;
//					
//					retval+=2000;
			

				}
				
			}
			
		}
		

		
		return retval;

	}

	
	
}
