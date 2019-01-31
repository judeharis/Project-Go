package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

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
					
					
//					if (e.isThere(S0) || e.isThere(S3))continue;
//					if (!e.isThere(LT))continue;
//					if(!e.isTheres(TL,S2) && !e.isTheres(TR,S2)  
//							&& !e.isTheres(S1,TL,TR)) continue;


					if (e.isThere(S0) || e.isThere(S3))continue;
					if (e.isThere(S2)) {
						retval+=1400;
						if(e.isTheres(TL) || e.isTheres(TR)) retval+=400;
					}else if(e.isThere(S1)) {
						retval+=1200;
						if(e.isTheres(TL,TR)) retval+=600;
					}
					if (e.isThere(LT))retval+=200;
					
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
					
					
//					if (e.isThere(S0) || e.isThere(S3))continue;
//					if (!e.isThere(RT))continue;
//					if(!e.isThere(TL) && !e.isThere(TR)) continue;
//					if(!e.isTheres(S1) && !e.isTheres(S2))continue;


					
					if (e.isThere(S0) || e.isThere(S3))continue;
					if (e.isThere(S1)) {
						retval+=1400;
						if(e.isTheres(TL) || e.isTheres(TR)) retval+=400;
						if (e.isThere(RT))retval+=200;
					}else if(e.isThere(RT)) {
						retval+=1200;
						if(e.isTheres(TL) || e.isTheres(TR)) retval+=200;
						if (e.isThere(S2))retval+=600;
					}

					

					

					
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
					counter++;
					
					
					if (e.isThere(S0) || e.isThere(S3))continue;

					
//					if(e.isThere(S1)) {
//						if(e.isTheres(TL));
//						else if(e.isTheres(BL));
//						else continue;
//					}else if(e.isThere(S2)) {
//						if(e.isTheres(TL,TR));
//						else if(e.isTheres(TL,BL));
//						else if(e.isTheres(BL,TR));
//						else continue;
//					} else continue;
					
					
					if (e.isThere(S0) || e.isThere(S3))continue;
					if (e.isThere(S1)) {
						retval+=1400;
						if(e.isTheres(TL) || e.isTheres(BL))retval+=600;
					}else if(e.isThere(S2)) {
						retval+=1200;
						if(e.isTheres(TL,TR) || (e.isTheres(TL,BL)) || e.isTheres(BL,TR)) retval+=800;

					}




					
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
					
					
					
//					if(e.isThere(S1)) {
//						if(e.isTheres(TL));
//						else if(e.isTheres(BL));
//						else continue;
//					}else if(e.isThere(S2)) {
//						if(e.isTheres(TL,TR));
//						else if(e.isTheres(TL,BL));
//						else if(e.isTheres(BL,TR));
//						else continue;
//					} else continue;
					
					if (e.isThere(S0) || e.isThere(S3))continue;
					if (e.isThere(S2)) {
						retval+=1400;
						if(e.isTheres(TL) || e.isTheres(BL))retval+=600;
					}else if(e.isThere(S1)) {
						retval+=1200;
						if(e.isTheres(TL,TR) || (e.isTheres(TL,BL)) || e.isTheres(BL,TR)) retval+=800;

					}




					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
