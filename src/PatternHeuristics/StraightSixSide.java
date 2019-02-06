package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightSixSide {


	static ArrayList<Pattern> straightSixSide1Pattern = Pattern.sToPv2("xrxrxrxrxrxrdxzdlxdS");
	static ArrayList<Pattern> straightSixSide2Pattern = Pattern.sToPv2("xrdxdxdxdxdxdxzldxdxdxdxdxdxdS");
	
	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightSixSide1Pattern,e.kscolour);
		
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
					Tuple S2 = S1.side(r);		
					Tuple S3 = S2.side(r);	
					Tuple S4 = S3.side(r);	
					Tuple S5 = S4.side(r);	
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = S5.side2(r,side.opp());
					counter++;
					

		
					
//					if (e.isThere(S0) || e.isThere(S5))continue;
//					if(e.isTheres(S1) || e.isTheres(S2) || e.isTheres(S3) || e.isTheres(S4)) retval+=600;
//					if(e.isTheres(TL)) retval+=1700;
//					if(e.isTheres(TR)) retval+=1700;
					
					
					if (e.isThere(S0) || e.isThere(S5))continue;
					retval +=1100;
					float a = States.borderSafe(e, 1, TL,TR);
					float b = States.borderSafe(e, 4, S1,S2,S3,S4);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) retval+=1100;
					else if(ncap<0.5) retval-=1100;
				
				

					
				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, straightSixSide2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);		
					Tuple S3 = S2.side(side);	
					Tuple S4 = S3.side(side);	
					Tuple S5 = S4.side(side);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					counter++;
					

				
//					if (e.isThere(S0) || e.isThere(S5))continue;
//					if(e.isTheres(S1) || e.isTheres(S2) || e.isTheres(S3) || e.isTheres(S4)) retval+=1000;
//					if (e.isTheres(TL) || e.isThere(TR)) retval+=3000;
				
					
					if (e.isThere(S0) || e.isThere(S5))continue;
					retval +=1100;
					float a = States.borderSafe(e, 2, TL,TR);
					float b = States.borderSafe(e, 4, S1,S2,S3,S4);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) retval+=1100;
					else if(ncap<0.5) retval-=1100;
					

					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
