package GoLD.Heuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class EightLive{
	static ArrayList<Pattern> eightLivePattern = Pattern.sToPv2("xrxrxrxrxrxrxrxrozloddS");

	
	
	public static int evaluate(ArrayList<Tuple> sstring , Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
//		ArrayList<Pattern> pattern6 = Pattern.sToPv2("xrxrxrxrxrxrxrxrozloddS", e.kscolour);
//		ArrayList<Tuple> pString =ps.stringMatch(sstring, pattern6);	
//		
//
//		if (!pString .isEmpty()) {
//			boolean diagSide= ps.dirSideToBool();
//			UDLR side = ps.dirNumToDir();
//	
//			if (!side.isEmpty()) {
//
//				Tuple S1 = pString.get(0).side(side);
//				Tuple S2 = S1.side(side.diag(diagSide));
//				Tuple S3 = S2.side(side.diag(diagSide));
//				Tuple S4 = S3.side(side.diag(diagSide));
//				Tuple S5 = S4.side(side.diag(diagSide));
//				Tuple S6 = S5.side(side.diag(diagSide));
//				Tuple S7 = S6.side(side.diag(diagSide));
//				Tuple S8 = S7.side(side.diag(diagSide));
//				Tuple S9 = S8.side(side.diag(diagSide));
//				
//				Tuple S0 = S1.side(side.diag(!diagSide));
//
//			
//				if(!e.isTheres(S1,S7)) {
//					
//					if (e.isEnemy(S1) || e.isEnemy(S8)) retval-=100;
//					if (e.isEnemies(S1,S8)) retval-=100;
//					if (e.isEnemies(S1,S8) && (e.isEnemy(S0) || e.isEnemy(S9))) retval-=100;
//					if (e.isEnemies(S1,S8,S0,S9)) retval-=100;
//				
//					
//					//====
//					if (e.isThere(S1) || e.isThere(S8)) retval+=100;
//					if (e.isEnemy(S1) && e.isThere(S8)) retval+=100;
//					if (e.isEnemy(S8) && e.isThere(S1)) retval+=100;
//
//				
//				}
//			}
//		
//		}
//		
//		return retval;
		
		
		
		
//		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrxrxrxrxrxrxrozloddS", e.kscolour);
//		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, eightLivePattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					counter++;

					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);
					Tuple S3 = S2.side(r);
					Tuple S4 = S3.side(r);
					Tuple S5 = S4.side(r);
					Tuple S6 = S5.side(r);
					Tuple S7 = S6.side(r);
					Tuple S8 = S7.side(r);
					Tuple S9 = S8.side(r);
					
					Tuple S0 = S1.side(l);

				
					if(e.isTheres(S1,S7)) continue;
					
					if (e.isEnemy(S1) || e.isEnemy(S8)) retval-=100;
					if (e.isEnemies(S1,S8)) retval-=100;
					if (e.isEnemies(S1,S8) && (e.isEnemy(S0) || e.isEnemy(S9))) retval-=100;
					if (e.isEnemies(S1,S8,S0,S9)) retval-=100;
				
					
					//+
					if (e.isThere(S1) || e.isThere(S8)) retval+=100;
					if (e.isEnemy(S1) && e.isThere(S8)) retval+=100;
					if (e.isEnemy(S8) && e.isThere(S1)) retval+=100;
					
					if (e.isEnemies(S1,S0) && e.isThere(S2)) retval+=100;
					if (e.isEnemies(S8,S9) && e.isThere(S7)) retval+=100;

					
					
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
