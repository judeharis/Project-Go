package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BulkyFiveSide {
	
	static ArrayList<Pattern> bulkyFiveSide1Pattern = Pattern.sToPv2("xrxrdxdxdSzldxldx");
	static ArrayList<Pattern> bulkyFiveSide2Pattern = Pattern.sToPv2("xrxrdxdxdxdSzldxdxrdx");
	static ArrayList<Pattern> bulkyFiveSide3Pattern = Pattern.sToPv2("xrxrxrdxdlxdSzdlxdx");
	static ArrayList<Pattern> bulkyFiveSide4Pattern = Pattern.sToPv2("xrdxrdxdxdSzldxdxdx");


	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, bulkyFiveSide1Pattern,e.kscolour);
		
		
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
					Tuple TR = D1.side2(u,r);
					Tuple LT = S0.side2(u,l);
					
					counter++;
					
					
					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					if(e.isTheres(S1)) retval+=2000;
					if(e.isTheres(TL,LT,TR)) retval+=1000;

					


				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, bulkyFiveSide2Pattern,e.kscolour);
		
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
					Tuple TR = S0.side2(u,r);
					Tuple BL = D1.side2(side,l);
					
					counter++;

//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(!e.isTheres(S1)) continue;
//					
//					retval+=2000;
					


					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					if(e.isTheres(S1)) retval+=2000;
					if(e.isTheres(TL,BL,TR)) retval+=1000;




				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, bulkyFiveSide3Pattern,e.kscolour);
		
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
					Tuple TR = S2.side2(u,r);
					Tuple BR = S2.side2(side,r);
					

					counter++;

					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					if(e.isTheres(S1)) retval+=2000;
					if(e.isTheres(TL,BR,TR)) retval+=1000;



//					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
//					if(!e.isTheres(S1)) continue;
//					
//					retval+=2000;
			

				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, bulkyFiveSide4Pattern,e.kscolour);
		
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
					Tuple RT = S1.side2(u,r);

					counter++;

					if (e.isThere(D0) || e.isThere(D1) || e.isThere(S0) || e.isThere(S2))continue;
					if(e.isTheres(S1)) retval+=2000;
					if(e.isTheres(TL,RT,TR)) retval+=1000;


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
