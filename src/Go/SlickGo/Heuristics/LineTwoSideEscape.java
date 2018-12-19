package Go.SlickGo.Heuristics;

import java.util.ArrayList;


import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class LineTwoSideEscape  {
	Evaluator e;
	PatternSearcher ps;

	public LineTwoSideEscape (Evaluator e){
		this.e=e;
	}

	
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		ArrayList<Pattern> pattern;

		ArrayList<ArrayList<Tuple>> pMatches;
		
		
		
		pattern = Pattern.sToPv2("xrouodddS", Stone.BLACK);
		pMatches =ps.allStringMatch(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					
					UDLR side = ps.dirNumToDir(counter);
					
					UDLR l = side.diag(!diagSide);
					UDLR r = side.diag(diagSide);
					
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple S2 = S1.side(r);
					Tuple S3 = S2.side(r);
					Tuple Sn1 = S0.side(l);

					Tuple B2 = S2.side(side.opp());
					Tuple B3 = S3.side(side.opp());
					
					
					if(e.isTheres(Sn1,B3)) {
						if(e.isEnemies(S2,S0) && e.isTheres(B2))retval-=100;
						if(e.isEnemies(S2,S0,S1) && e.isTheres(B2))retval-=100;
					} 
					

					if (e.isTheres(S1)){			
						Tuple t = B2;
						Tuple u = t.side(side);
						Tuple k = t.side(r);
						if(e.isTheres(Sn1,B3)) {
							if(e.isEnemy(S2))retval-=100;
						} 
						if(e.isEnemies(B2)) {
							retval-=100;
							while (e.isThere(u) && e.isEnemy(k) && !e.isThere(k.side(r))) {
								retval-=100;
								t = t.side(r);
								u = u.side(r);
								k = k.side(r);
							}
							if(e.isEnemy(k.side(side)) && e.isTheres(k.side(r)))retval-=100;
						}
						if(e.isEnemy(t.side(side))) retval-=50;
					}
					
					if (e.isTheres(S1)) {
						retval+=100;
						Tuple t = S1;
						Tuple k = B2;
						while (e.isThere(t.side(r)) && e.isEnemy(k) && !ps.isCorner(t.side(r))) {
							retval+=100;
							t = t.side(r);
							k = k.side(r);
						}
						if(e.isThere(k)) retval+=50;
					}

					
				}
				counter++;
			}
			
		}
		
		
		
//		
//		ArrayList<Tuple> pString;
//		pattern = Pattern.sToPv2("xrxrozdxr-dS", Stone.BLACK);
//		pString =ps.stringMatch(sstring, pattern);
//
//
//		if (!pString.isEmpty()) {
//			boolean diagSide= ps.dirSideToBool();
//			UDLR side = ps.dirNumToDir();
//			Tuple S1 = pString.get(0).side(side);
//			Tuple S2 = S1.side(side.diag(diagSide));
//			Tuple S3 = S2.side(side.diag(diagSide));
//			Tuple S4 = S3.side(side.diag(diagSide));
//			
//			Tuple B4 = S4.side(side.opp());
//
//			if (e.isEnemies(B4)&& e.isTheres(S3)) {
//				retval-=100;
//				Tuple t = B4;
//				while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(diagSide)))) {
//					retval-=100;
//					t = t.side(side.diag(diagSide));
//				}
//				if(e.isEnemy(t.side(side))) retval-=50;
//			}
//			
//			if (e.isTheres(S3)) {
//				retval+=100;
//				Tuple t = S3;
//				Tuple k = B4;
//				while (e.isThere(t.side(side.diag(diagSide))) && e.isEnemy(k) && !ps.isCorner(t.side(side.diag(diagSide)))) {
//					retval+=100;
//					t = t.side(side.diag(diagSide));
//					k = k.side(side.diag(diagSide));
//				}
//				if(e.isThere(k)) retval+=50;
//			}
//
//		}
//		
//		
//		pattern = Pattern.sToPv2("xrxrozdrxdS", Stone.BLACK);
//		pString =ps.stringMatch(sstring, pattern);
//		
//		if (!pString.isEmpty()) {
//			boolean diagSide= ps.dirSideToBool();
//			UDLR side = ps.dirNumToDir();
//			Tuple S1 = pString.get(0).side(side);
//			Tuple S2 = S1.side(side.diag(diagSide));
//			Tuple S3 = S2.side(side.diag(diagSide));
//			Tuple S4 = S3.side(side.diag(diagSide));
//			
//			Tuple B4 = S4.side(side.opp());
//
//			if (e.isEnemies(B4)&& e.isTheres(S3)) {
//				retval-=100;
//				Tuple t = B4;
//				while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(diagSide)))) {
//					retval-=100;
//					t = t.side(side.diag(diagSide));
//				}
//				if(e.isEnemy(t.side(side))) retval-=50;
//			}
//			
//			if (e.isTheres(S3)) {
//				retval+=100;
//				Tuple t = S3;
//				Tuple k = B4;
//				while (e.isThere(t.side(side.diag(diagSide))) && e.isEnemy(k) && !ps.isCorner(t.side(side.diag(diagSide)))) {
//					retval+=100;
//					t = t.side(side.diag(diagSide));
//					k = k.side(side.diag(diagSide));
//				}
//				if(e.isThere(k)) retval+=50;
//			}
//
//		}
//		
//
//

		
		

		return retval;
	}


	
	
}
