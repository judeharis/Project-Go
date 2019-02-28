package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class BentFourSide {

	
	static ArrayList<Pattern> straightFourSide1Pattern = Pattern.sToPv2("xrxruxrdxdxdSzdlx");
	static ArrayList<Pattern> straightFourSide2Pattern = Pattern.sToPv2("xrdxdxrdxdSzdlxdxdx");
	static ArrayList<Pattern> straightFourSide3Pattern = Pattern.sToPv2("xrxrdxdxdxdSzdlxdrxdx");
	static ArrayList<Pattern> straightFourSide4Pattern = Pattern.sToPv2("xrxrxrdxdxdSzldxdrxrx");

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
					Tuple S3 = S2.side(side.opp());	
					Tuple TL = S3.side2(side.opp(), l);
					Tuple TR = S3.side2(side.opp(), r);
					Tuple LT = S0.side2(side.opp(),l);
					counter++;
					
					




					if (e.isThere(S0) || e.isThere(S3))continue;
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafeRel1(e, 5,TL,TR,LT,S0,S3);
					float zcap = States.minFinder(z1);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					
					
					patval +=450;
					float a1 = States.borderSafeRel2(e, 1, LT);
					float a2 = States.borderSafeRel2(e, 2 ,TL,TR);
					float a3 = States.borderSafeRel2(e, 2, S1,S2);
					float acap = States.minFinder(a1,a2,a3);
					if(acap>0.5) patval+=450;
					else if(acap<0.5) patval-=450;
					
					
					patval +=200;
					float b1 = States.borderSafeRel1(e, 1, LT);
					float b2 = States.borderSafeRel1(e, 2, S1,S2,TL,TR);
					float ncap = States.minFinder(b1,b2);
					if(ncap>0.5) patval+=200;
					else if(ncap<0.5) patval-=200;
					
					
//					patval +=650;
//					float b1 = States.borderSafe(e, 1, LT);
//					float b2 = States.borderSafe(e, 2, S1,S2,TL,TR);
//					float ncap = States.minFinder(b1,b2);
//					if(ncap>0.5) patval+=650;
//					else if(ncap<0.5) patval-=650;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 1, LT);
//					float b2 = States.borderSafe(e, 2, S1,S2,TL,TR);
//					float ncap = States.minFinder(b1,b2);
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
					Tuple S3 = S2.side(r);	
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple RT = S3.side2(side.opp(),r);
					counter++;
					

					
					
					
					
					if (e.isThere(S0) || e.isThere(S3))continue;
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafeRel1(e, 5,TL,TR,RT,S0,S3);
					float zcap = States.minFinder(z1);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					patval +=650;
					float b1 = States.borderSafeRel2(e, 2, TL,TR);
					float b2 = States.borderSafeRel2(e, 1, RT);
					float b3 = States.borderSafeRel2(e, 2, S1,S2);
					float ncap = States.minFinder(b1,b2,b3);
					if(ncap>0.5) patval+=650;
					else if(ncap<0.5) patval-=650;

					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 2, TL,TR);
//					float b2 = States.borderSafe(e, 1, RT);
//					float b3 = States.borderSafe(e, 2, S1,S2);
//
//					float ncap = States.minFinder(b1,b2,b3);
//
//
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;

					

					

					
				}
				
			}
			
		}
		
		pMatches =ps.allStringMatchv2(sstring, straightFourSide3Pattern,e.kscolour);
		
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
					Tuple S2 = S1.side(side);		
					Tuple S3 = S2.side(side);	
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);
					Tuple BL = TL.side2(side,side);
					Tuple BB = BL.side(side);
					counter++;
					

					
					if (e.isThere(S0) || e.isThere(S3))continue;
					if (e.isTheres(S1,S2,BL,BB))continue;
					
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					patval +=450;
					float a4 = States.borderSafeRel1(e, 1, S0);
					float a5 = States.borderSafeRel1(e, 1, S2);
					float a6 = e.isThere(S1)?0:1;
					float a1 = States.borderSafeRel2(e, 2, TL,BL) + States.minFinder(a4,a5,a6);
					float a3 = States.borderSafeRel2(e, 2, S1,S2);
					float acap = States.minFinder(a1,a3);
					
					if(acap>0.5) patval+=450;
					else if(acap<0.5) patval-=450;
					
					patval +=200;
					float b1 = States.borderSafeRel1(e, 2, TL,BL);
					float b2 = States.borderSafeRel1(e, 2, S1,S2);
					float b3 = States.borderSafeRel1(e, 3, S1,TR,TL);
					float b4 = States.borderSafeRel1(e, 3, S1,TR,BL);
					float ncap = States.minFinder(b1,b2,b3,b4);
					if(States.oneCheck(b1,b3) || States.oneCheck(b1,b4) || States.oneCheck(b1,b2)) ncap = States.minFinder(ncap,0.5f);
					if(States.oneCheck(b2,b3) || States.oneCheck(b2,b4)) ncap = States.minFinder(ncap,0.5f);

					if(ncap>0.5) patval+=200;
					else if(ncap<0.5) patval-=200;
					
//					patval +=650;
//					float b1 = States.borderSafe(e, 2, TL,BL);
//					float b2 = States.borderSafe(e, 2, S1,S2);
//					float b3 = States.borderSafe(e, 3, S1,TR,TL);
//					float b4 = States.borderSafe(e, 3, S1,TR,BL);
//					float ncap = States.minFinder(b1,b2,b3,b4);
//					if(States.oneCheck(b1,b3) || States.oneCheck(b1,b4) || States.oneCheck(b1,b2)) ncap = States.minFinder(ncap,0.5f);
//					if(States.oneCheck(b2,b3) || States.oneCheck(b2,b4)) ncap = States.minFinder(ncap,0.5f);
//
//					if(ncap>0.5) patval+=650;
//					else if(ncap<0.5) patval-=650;

					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 2, TL,BL);
//					float b2 = States.borderSafe(e, 2, S1,S2);
//					float b3 = States.borderSafe(e, 3, S1,TR,TL);
//					float b4 = States.borderSafe(e, 3, S1,TR,BL);
//					float ncap = States.minFinder(b1,b2,b3,b4);
//					if(States.oneCheck(b1,b3) || States.oneCheck(b1,b4) || States.oneCheck(b1,b2)) ncap = States.minFinder(ncap,0.5f);
//					if(States.oneCheck(b2,b3) || States.oneCheck(b2,b4)) ncap = States.minFinder(ncap,0.5f);
//
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;




					
				}
				
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, straightFourSide4Pattern,e.kscolour);
		
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
					Tuple TR = S2.side2(side.opp(),r);
					Tuple BL = TL.side2(side,side);
					counter++;
					
					
					
					
					if (e.isThere(S0) || e.isThere(S3))continue;
					if (e.isTheres(BL,S1,S2))continue;
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1) || e.isThere(S2))patval=0;
					
					
					
					
					patval +=450;
					float a4 = States.borderSafeRel1(e, 1, S0);
					float a5 = States.borderSafeRel1(e, 1, S2);
					float a6 = e.isThere(S1)?0:1;
					float a1 = States.borderSafeRel2(e, 2, TL,BL) + States.minFinder(a4,a5,a6);
					float a3 = States.borderSafeRel2(e, 2, S1,S2);
					float acap = States.minFinder(a1,a3);
					
					if(acap>0.5) patval+=450;
					else if(acap<0.5) patval-=450;
					
					
					patval +=200;
					float b1 = States.borderSafeRel1(e, 2, TL,BL);
					float b2 = States.borderSafeRel1(e, 2, S1,S2);
					float b3 = States.borderSafeRel1(e, 2, S2,TR);
					float ncap = States.minFinder(b1,b2,b3);
					if(States.oneCheck(b2,b3)) ncap = States.minFinder(ncap,0.5f);

					if(ncap>0.5) patval+=200;
					else if(ncap<0.5) patval-=200;
					
					
					
//					patval +=650;
//					float b1 = States.borderSafe(e, 2, TL,BL);
//					float b2 = States.borderSafe(e, 2, S1,S2);
//					float b3 = States.borderSafe(e, 2, S2,TR);
//					float ncap = States.minFinder(b1,b2,b3);
//					if(States.oneCheck(b2,b3)) ncap = States.minFinder(ncap,0.5f);
//
//					if(ncap>0.5) patval+=650;
//					else if(ncap<0.5) patval-=650;

					retval+=patval;
					if(patval>=100 && !e.isThere(S1) && !e.isThere(S2))e.addToEye(S0,S1,S2,S3);
					
					
//					retval +=700;
//					float b1 = States.borderSafe(e, 2, TL,BL);
//					float b2 = States.borderSafe(e, 2, S1,S2);
//					float b3 = States.borderSafe(e, 2, S2,TR);
//					float ncap = States.minFinder(b1,b2,b3);
//					if(States.oneCheck(b2,b3)) ncap = States.minFinder(ncap,0.5f);
//
//					if(ncap>0.5) retval+=700;
//					else if(ncap<0.5) retval-=700;




					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
