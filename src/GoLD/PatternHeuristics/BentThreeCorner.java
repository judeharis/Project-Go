package GoLD.PatternHeuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class BentThreeCorner {
	
	static ArrayList<Pattern> bentThreeCorner1Pattern = Pattern.sToPv2("xdlxdlxrr#");
	static ArrayList<Pattern> bentThreeCorner2Pattern = Pattern.sToPv2("xrxzdlxdxrrX");
	static ArrayList<Pattern> bentThreeCorner3Pattern = Pattern.sToPv2("xrxzdlxdrxr#");

	public static int evaluate(ArrayList<Tuple> sstring, Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, bentThreeCorner1Pattern,e.kscolour);

		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple LT = TL.side2(side,l);
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(side);
					Tuple S0 = S1.side(l);
					counter++;

					
					if(e.isThere(S0) || e.isThere(S2)) continue;
					
					
					
					int patval =0;	
					patval +=50;
					float z1 = States.borderSafe(e, 1,S0);
					float z2 = States.borderSafe(e, 1,S2);
					float z3 = States.borderSafeRel1(e, 2, TL,LT) + States.minFinder(z1,z2);
					float zcap = States.minFinder(z3);
					if(zcap>0.5) patval+=50;
					else if(zcap<0.5) patval-=50;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float a = States.borderSafeRel2(e, 1, TL,LT);
					float b = States.borderSafeRel2(e, 1, S1);
					float ncap = States.minFinder(a,b);
					if(ncap>0.5) patval+=450;
					else if(ncap<0.5) patval-=450;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2);
					
//					retval +=500;
//					float a = States.borderSafe(e, 1, TL,LT);
//					float b = States.borderSafe(e, 1, S1);
//					float ncap = States.minFinder(a,b);
//					
//					if(ncap>0.5) retval+=500;
//					else if(ncap<0.5) retval-=500;
					




				}
			}
			
		}
		
		
		pMatches =ps.allStringMatchv2(sstring, bentThreeCorner2Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
//					UDLR l = side.diag(!diagSide);
//					Tuple TL = tlist.get(0).side(l);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);
					Tuple S0 = S1.side(side);
					counter++;
					
					if(e.isThere(S0) || e.isThere(S2) || e.isThere(S1))continue;
					
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					patval +=450;
					float a = States.borderSafeRel2(e, 1, S1);
					float ncap = States.minFinder(a);
					if(ncap>0.5) patval+=450;
					else if(ncap<0.5) patval-=450;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2);
					
					
					
//					retval +=500;
//					float a = States.borderSafe(e, 1, S1);
//					float ncap = States.minFinder(a);
//					
//					if(ncap>0.5) retval+=500;
//					else if(ncap<0.5) retval-=500;


				}
			}
			
		}
		
		

		pMatches =ps.allStringMatchv2(sstring, bentThreeCorner3Pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple BL = TL.side2(side,side);
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(r);
					Tuple S0 = S1.side(side);
					counter++;
					
					
					if(e.isThere(S0) || e.isThere(S2))continue;
					
					
					
					int patval =0;	
					patval +=100;
					if(e.isThere(S1))patval=0;
					
					
					if(e.isTheres(S1,BL)){
						patval +=450;
						float a = States.borderSafeRel1(e, 2, TL,BL);
						float b = States.borderSafeRel1(e, 1, S1);
						float ncap = States.minFinder(a,b);
						if(ncap>0.5) patval+=450;
						else if(ncap<0.5) patval-=450;
					}else {
						patval +=450;
						float a = States.borderSafeRel2(e, 2, TL,BL);
						float b = States.borderSafeRel2(e, 1, S1);
						float ncap = States.minFinder(a,b);
						if(ncap>0.5) patval+=450;
						else if(ncap<0.5) patval-=450;
					}
					
					
					if(e.isTheres(S1,BL))patval/=2;
					
					
					retval+=patval;
					if(patval>=100 && !e.isThere(S1))e.addToEye(S0,S1,S2);
					
					
//					retval +=500;
//					float a = States.borderSafe(e, 2, TL,BL);
//					float b = States.borderSafe(e, 1, S1);
//					float ncap = States.minFinder(a,b);
//					if(ncap>0.5) retval+=500;
//					else if(ncap<0.5) retval-=500;
//					if(e.isTheres(S1,BL))retval-=500;
					



					

	
				}

			}
			
		}
		
		


		
		return retval;

	}

	
	
}
