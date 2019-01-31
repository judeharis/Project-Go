package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class RabbitSixCorner {
	
	static ArrayList<Pattern> rabbitSixCorner1Pattern = Pattern.sToPv2("xrdxddXzldxldxdx");
	static ArrayList<Pattern> rabbitSixCorner2Pattern = Pattern.sToPv2("xrxrdxddXzldxdxrdx");
	static ArrayList<Pattern> rabbitSixCorner3Pattern = Pattern.sToPv2("xrdxzldxldxrdxrr#");


	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, rabbitSixCorner1Pattern,e.kscolour);
		
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple A1 = tlist.get(0).side(side);
					Tuple S1 = A1.side(side);
					Tuple S0 = S1.side(l);		
					Tuple S2 = S1.side(r);	
					Tuple B1 = S1.side(side);	
					Tuple B0 = B1.side(l);	
					
					Tuple TL = A1.side2(u,l);
					Tuple TR = A1.side2(u,r);
					Tuple LT = A1.side2(l,l);		
					counter++;
					
					
					
					
					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
					
					
					if(e.isTheres(S1)) retval+=1000;
					if(e.isTheres(TL,TR,LT))retval+=3000;
					


				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, rabbitSixCorner2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple B0 = tlist.get(0).side(side);
					Tuple B1 = B0.side(r);	
					Tuple S0 = B0.side(side);
					Tuple S1 = S0.side(r);		
					Tuple S2 = S1.side(r);	
					Tuple A1 = S1.side(side);
					
					Tuple TL = B0.side2(u,l);
					Tuple TR = B1.side2(u,r);
					Tuple BL = S0.side2(side,l);

					counter++;
	
					
					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
					
					
					if(e.isTheres(S1)) retval+=1000;
					if(e.isTheres(TL,TR,BL))retval+=3000;





				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, rabbitSixCorner3Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					UDLR u = side.opp();
					Tuple A1 = tlist.get(0).side(side);
					Tuple S1 = A1.side(side);
					Tuple S2 = S1.side(l);	
					Tuple S0 = S1.side(r);
					Tuple B1 = S1.side(side);	
					Tuple B0 = B1.side(r);	
					
					Tuple TL = A1.side2(u,l);
					Tuple TR = A1.side2(u,r);		
					Tuple LT = S2.side2(u,l);
					Tuple BL = S2.side2(side,l);
					counter++;
					
					
					
					
					if (e.isThere(A1) || e.isThere(S0) || e.isThere(S2) || e.isThere(B0) || e.isThere(B1))continue;
					
					

					if(e.isTheres(S1)) retval+=1000;
					if(e.isTheres(TL,TR,BL,LT))retval+=3000;




				}
				
			}
			
		}
		

		return retval;

	}

	
	
}
