package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class StraightFourSide {


	static ArrayList<Pattern> straightFourSide1Pattern = Pattern.sToPv2("xrxrxrxrdxzdlxdS");
	static ArrayList<Pattern> straightFourSide2Pattern = Pattern.sToPv2("xrdxdxdxdxzldxdxdxdxdS");
	
	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightFourSide1Pattern,e.kscolour);
		
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
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = S3.side2(r,side.opp());
					counter++;
					
					
					if (e.isThere(S0) || e.isThere(S3))continue;
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafeRel1(e, 4,TL,TR,S0,S3);
					float zcap = States.minFinder(z1);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					patval +=650;
					float a = States.borderSafeRel2(e, 1, TL,TR);
					float b = States.borderSafeRel2(e, 2, S1,S2);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
//					retval +=700;
//					float a = States.borderSafe(e, 1, TL,TR);
//					float b = States.borderSafe(e, 2, S1,S2);
//					float ncap = States.minFinder(a,b);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;
					


					
				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, straightFourSide2Pattern,e.kscolour);
		
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
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					counter++;
					
					
					if (e.isThere(S0) || e.isThere(S3))continue;
					
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					patval +=650;
					float a = States.borderSafeRel2(e, 2, TL,TR);
					float b = States.borderSafeRel2(e, 2, S1,S2);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
//					retval +=700;
//					float a = States.borderSafe(e, 2, TL,TR);
//					float b = States.borderSafe(e, 2, S1,S2);
//					float ncap = States.minFinder(a,b);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;
					
	


				

					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
