package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BentFourCorner {

	
	static ArrayList<Pattern> straightFourCorner1Pattern = Pattern.sToPv2("xldxdxldxrr#");
	static ArrayList<Pattern> straightFourCorner2Pattern = Pattern.sToPv2("xrxrxzdlxdrxrxr#");
	static ArrayList<Pattern> straightFourCorner3Pattern = Pattern.sToPv2("xrxddxdXzdlxdxdx");
	static ArrayList<Pattern> straightFourCorner4Pattern = Pattern.sToPv2("xrdxrxd#zdlxdx");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightFourCorner1Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR l = side.diag(!diagSide);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);		
					Tuple S3 = S2.side(l);	
					Tuple TL = tlist.get(0).side(l);
					Tuple LB = S3.side2(side.opp(),l);
					counter++;
					

//					if (e.isThere(S0) || e.isThere(S3))continue;
//					
//					if (e.isTheres(S1))retval +=800;
//					if (e.isTheres(TL,LB))retval +=1200;
					
					if (e.isThere(S0) || e.isThere(S3))continue;
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafe(e, 4,TL,LB,S0,S3);
					float zcap = States.minFinder(z1);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					patval +=650;
					float b1 = States.borderSafe(e, 1, TL);
					float b2 = States.borderSafe(e, 1, LB);
					float b3 = States.borderSafe(e, 1, S1);
					float ncap = States.minFinder(b1,b2,b3);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;

					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 1, TL);
//					float b2 = States.borderSafe(e, 1, LB);
//					float b3 = States.borderSafe(e, 1, S1);
//					float ncap = States.minFinder(b1,b2,b3);
//
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;




					
				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, straightFourCorner2Pattern,e.kscolour);
		
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
					Tuple S3 = S2.side(side);	
					Tuple TL = tlist.get(0).side(l);
					Tuple BL = TL.side2(side,side);
					counter++;

					
					if (e.isThere(S0) || e.isThere(S3))continue;
					int patval =0;	
					patval +=100;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					patval +=650;
					float b1 = States.borderSafe(e, 2, TL,BL);
					float b2 = States.borderSafe(e, 2, S1,S2);
					float ncap = States.minFinder(b1,b2);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;

					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 2, TL,BL);
//					float b2 = States.borderSafe(e, 2, S1,S2);
//					float ncap = States.minFinder(b1,b2);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;


					




					

					
				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, straightFourCorner3Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S0 = S1.side(r);
					Tuple S2 = S1.side(side);		
					Tuple S3 = S2.side(side);	
					Tuple TL = tlist.get(0).side(l);
					counter++;
					
//					if (e.isThere(S0) || e.isThere(S3))continue;
//					if (!e.isTheres(S1) && !e.isTheres(S2,TL))continue;
//
//					if (e.isTheres(S1))retval+=2000;
//					else if(e.isTheres(S2,TL)) retval+=2000;
					
					if (e.isThere(S0) || e.isThere(S3))continue;
					if (e.isTheres(S1,S2))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					patval +=650;
					float b1 = States.borderSafe(e, 2, TL,S1);
					float b2 = States.borderSafe(e, 2, S1,S2);
					float ncap = States.minFinder(b1,b2);
					if(States.oneCheck(b1,b2)) ncap = States.minFinder(ncap,0.5f);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;

					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 2, TL,S1);
//					float b2 = States.borderSafe(e, 2, S1,S2);
//					float ncap = States.minFinder(b1,b2);
//					if(States.oneCheck(b1,b2)) ncap = States.minFinder(ncap,0.5f);
//
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;


					
				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, straightFourCorner4Pattern,e.kscolour);
		
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
					Tuple S2 = S1.side(r);		
					Tuple S3 = S2.side(r);	
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple TRR = TR.side(r);
					counter++;
					

					
					if (e.isThere(S0) || e.isThere(S3))continue;
					if (e.isTheres(S1,S2,TR,TRR))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					patval +=650;
					float b1 = States.borderSafe(e, 2, TL,TR,S1,S2);
					float ncap = States.minFinder(b1);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;

					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 2, TL,TR,S1,S2);
//					float ncap = States.minFinder(b1);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;


					



					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
