package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class PyramidFourSide {
	
	static ArrayList<Pattern> pyramidFourSide1Pattern = Pattern.sToPv2("xdrxdrxruxruxuS");
	static ArrayList<Pattern> pyramidFourSide2Pattern = Pattern.sToPv2("xrxrxrdxdlxdlSluxlux");
	static ArrayList<Pattern> pyramidFourSide3Pattern = Pattern.sToPv2("xrxrxrdSdlxdlxluxlux");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pyramidFourSide1Pattern,e.kscolour);
		
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					Tuple S0 = tlist.get(0).side(r);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple D0 = S1.side(side);
					Tuple BL= tlist.get(0).side(side);
					Tuple B1 = BL.side2(side,r);
					Tuple B2 = B1.side2(r,r);
					Tuple BR = B2.side2(side.opp(),r);
					counter++;
					



//					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
//					if(e.isTheres(S1)) retval+=1200;
//					if(e.isTheres(BL,BR))retval+=700;
//					if(e.isTheres(B1) || e.isTheres(B2))retval+=200;
					
					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
					
					int patval =0;	
					patval +=50;
					
					float z1 = States.borderSafe(e, 1,S0);
					float z2 = States.borderSafe(e, 1,S2);
					float z3 = States.borderSafe(e, 1,D0);
					float z4 = States.borderSafe(e, 4,BL,BR,B1,B2) +States.minFinder(z1,z2,z3);
					float zcap = States.minFinder(z4);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1))patval=0;
					
					
					
					
					patval +=450;
					float a4 = States.borderSafe(e, 1, BL);
					float a5 = States.borderSafe(e, 1, BR);

					
					
					float a1 = States.borderSafe(e, 2, B1,B2) + States.minFinder(a4,a5);
					float a2 = States.borderSafe(e, 2, BL,BR);
					float a3 = States.borderSafe(e, 1, S1);
					float acap = States.minFinder(a1,a2,a3);
					
					if(acap>0.5) patval+=450;
					else if(acap<0.5) patval-=450;
					
					
					
					patval +=200;
					float a = States.borderSafe(e, 1, BL,BR);
					float b = States.borderSafe(e, 2, B1,B2);
					float c = States.borderSafe(e, 1, S1);
					float ncap = States.minFinder(a,b,c);
					if(ncap>0.5) patval+=200;
					else if(ncap<0.5) patval-=200;
					
					

//					patval +=650;
//					float a = States.borderSafe(e, 1, BL,BR);
//					float b = States.borderSafe(e, 2, B1,B2);
//					float c = States.borderSafe(e, 1, S1);
//					float ncap = States.minFinder(a,b,c);
//					if(ncap>0.5) patval+=650;
//					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2,D0);
					
//					retval +=700;
//					float a = States.borderSafe(e, 1, BL,BR);
//					float b = States.borderSafe(e, 2, B1,B2);
//					float c = States.borderSafe(e, 1, S1);
//					
//					float ncap = States.minFinder(a,b,c);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;

				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, pyramidFourSide2Pattern,e.kscolour);
		
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
					Tuple D0 = S1.side(side);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = S2.side2(r,side.opp());
					Tuple BL = S0.side2(l,side);
					Tuple BR = S2.side2(r,side);
					counter++;



					

					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
					
					int patval =0;	
					patval +=50;
					
					float z1 = States.borderSafe(e, 1,S0);
					float z2 = States.borderSafe(e, 1,S2);
					float z3 = States.borderSafe(e, 1,D0);
					float z4 = States.borderSafe(e, 4,BL,BR,TL,TR) +States.minFinder(z1,z2,z3);
					float zcap = States.minFinder(z4);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1))patval=0;

					patval +=650;
					float a = States.borderSafe(e, 2, TL,BL);
					float b = States.borderSafe(e, 2, TR,BR);
					float c = States.borderSafe(e, 1, S1);
					float ncap = States.minFinder(a,b,c);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2,D0);
					
					
//					retval +=700;
//					float a = States.borderSafe(e, 2, TL,BL);
//					float b = States.borderSafe(e, 2, TR,BR);
//					float c = States.borderSafe(e, 1, S1);
//					
//					float ncap = States.minFinder(a,b,c);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;



				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, pyramidFourSide3Pattern,e.kscolour);
		
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
					Tuple D0 = S1.side(side);
					Tuple TL = tlist.get(0).side(l);
					Tuple BL = S0.side2(l,side);
					Tuple B1 = BL.side2(r,side);
					Tuple B2 = B1.side2(r,r);
					counter++;


//					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
//					if(!e.isTheres(TL,B1) && !e.isTheres(TL,B2)  
//							&& !e.isTheres(BL,B1)  && !e.isTheres(BL,B2))continue;
//					if(!e.isTheres(S1)) continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;

					
					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
					retval +=650;
					float a = States.borderSafe(e, 2, TL,BL);
					float b = States.borderSafe(e, 2, B1,B2);
					float c = States.borderSafe(e, 1, S1);
					
					float ncap = States.minFinder(a,b,c);
					if(ncap>0.5) retval+=650;
					else if(ncap<0.5) retval-=650;

					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2,D0);
					
//					if(e.isThere(S0)||e.isThere(S2)||e.isThere(D0)) continue;
//					retval +=700;
//					float a = States.borderSafe(e, 2, TL,BL);
//					float b = States.borderSafe(e, 2, B1,B2);
//					float c = States.borderSafe(e, 1, S1);
//					
//					float ncap = States.minFinder(a,b,c);
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;



					

				}
				
			}
			
		}
		

		
		return retval;

	}

	
	
}
