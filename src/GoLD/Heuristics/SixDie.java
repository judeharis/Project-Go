package GoLD.Heuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class SixDie {
	static ArrayList<Pattern> sixDiePattern = Pattern.sToPv2("xrxrxrxrxrxrozloddS");

	
	
	static public int evaluate(ArrayList<Tuple> sstring , Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
//		ArrayList<Pattern> pattern6 = Pattern.sToPv2("xrxrxrxrxrxrozloddS",e.kscolour);	
//		ArrayList<ArrayList<Tuple>>  pMatches =ps.allStringMatchv2(sstring, pattern6);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, sixDiePattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if (!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
							
					
					Tuple S1 = tlist.get(0).side(side);
					Tuple S0 = S1.side(l);
					Tuple Sn1 = S0.side(l);

					Tuple S2 = S1.side(r);
					Tuple S3 = S2.side(r);
					Tuple S6 = tlist.get((tlist.size() - 1)).side(side);
					Tuple S7 = S6.side(r);
					Tuple S8 = S7.side(r);
					Tuple S5 = S6.side(l);
					Tuple S4 = S5.side(l);
					
				
					
					if(e.isTheres(S1,S6))continue;
					
					//positive
					if (e.isThere(S1) || e.isThere(S6)) retval+=100;
					if (e.isThere(S1)) retval+=100;
					if (e.isThere(S6)) retval+=100;
					
//					if (e.isEnemy(S1) && e.isThere(S6)) retval+=100;
//					if (e.isEnemy(S6) && e.isThere(S1)) retval+=100;

					if (e.isTheres(S6,S2) && !e.isEnemies(S4)) retval+=100;
					if (e.isTheres(S1,S5) && !e.isEnemies(S3)) retval+=100;
					
					//negative
					if (e.isEnemy(S1) || e.isEnemy(S6)) retval-=100;
			
					if (e.isEnemies(S1,S7,S0)) retval-=100;
					if (e.isEnemies(S6,S0,S1))retval-=100;
			
					if (e.isEnemies(S1,S7) && (e.isThere(S6) || e.isThere(S8))  && !e.isThere(S0)) retval-=200;
					if (e.isEnemies(S6,S0) && (e.isThere(S1) || e.isThere(Sn1)) && !e.isThere(S7)) retval-=200;
						
						
				}
		
			}
		


		}
		return retval;
		
	}

	
}
