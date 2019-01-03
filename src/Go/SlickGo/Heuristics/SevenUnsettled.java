package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SevenUnsettled  {
	Evaluator e;
	PatternSearcher ps;

	public SevenUnsettled (Evaluator e){
		this.e=e;
	}



	
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern7 = Pattern.sToPv2("xrxrxrxrxrxrxrozloddS",e.kscolour);
		ArrayList<Tuple> pString =ps.stringMatch(sstring, pattern7);	
		ArrayList<ArrayList<Tuple>>  pMatches =ps.allStringMatch(sstring, pattern7);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if (!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);

						Tuple BS0 =  pString.get(0).side(side.diag(!diagSide));
						Tuple BS8 =  pString.get((pString.size() - 1)).side(side.diag(diagSide));
						Tuple S1 = pString.get(0).side(side);
						Tuple S0 = S1.side(side.diag(!diagSide));
						Tuple S2 = S1.side(side.diag(diagSide));
						Tuple S3 = S2.side(side.diag(diagSide));
						Tuple S4 = S3.side(side.diag(diagSide));
						Tuple S5 = S4.side(side.diag(diagSide));
						Tuple S6 = S5.side(side.diag(diagSide));
						Tuple S7 = S6.side(side.diag(diagSide));
						Tuple S8 = S7.side(side.diag(diagSide));

//						if(!e.isTheres(S1,S7) && e.isEnemy(BS0) && e.isEnemy(BS8)) {
//							//positive
//							if(e.isThere(S1) && !e.isEnemy(S7) && !e.isTheres(S2,S0))retval+=100;
//							if(e.isThere(S7) && !e.isEnemy(S1) && !e.isTheres(S6,S8))retval+=100;
//
//							if(e.isEnemy(S1) && e.isThere(S7))retval+=100;
//							if(e.isEnemy(S7) && e.isThere(S1))retval+=100;
//
//							if(e.isEnemies(S1,S7) && e.isTheres(S2,S6))retval+=100;
//
//							//negative
//							if(e.isEnemy(S1) || e.isEnemy(S7))retval-=100;
//							if(e.isEnemy(S1) && e.isEnemy(S7))retval-=100;
//							if(e.isEnemies(S1,S5) && e.isThere(S7))retval-=100;
//							if(e.isEnemies(S7,S3) && e.isThere(S1))retval-=100;
//							
//							if(e.isEnemies(S1,S8) && !e.isThere(S2) && !e.isThere(S7))retval-=100;
//							if(e.isEnemies(S7,S0) && !e.isThere(S6) && !e.isThere(S1))retval-=100;
//							
//						}
						
						if(!e.isTheres(S1,S7) && e.isEnemy(BS0) && e.isEnemy(BS8)) {
							//positive
							if(e.isThere(S1) && !e.isEnemy(S7) && !e.isTheres(S2,S0))retval+=100;
							if(e.isThere(S7) && !e.isEnemy(S1) && !e.isTheres(S6,S8))retval+=100;

							if(e.isEnemy(S1) && e.isThere(S7))retval+=100;
							if(e.isEnemy(S7) && e.isThere(S1))retval+=100;
							
							if(e.isTheres(S1,S3))retval+=900;
							if(e.isTheres(S7,S5))retval+=900;

							if(e.isEnemies(S1,S7) && e.isTheres(S2,S6))retval+=100;

							//negative
							if(e.isEnemy(S1) || e.isEnemy(S7))retval-=100;
							if(e.isEnemies(S1,S7))retval-=100;
							if(e.isEnemies(S1,S5) && e.isThere(S7))retval-=100;
							if(e.isEnemies(S7,S3) && e.isThere(S1))retval-=100;
							
							if(e.isEnemies(S1,S8) && !e.isThere(S2) && !e.isThere(S7))retval-=100;
							if(e.isEnemies(S7,S0) && !e.isThere(S6) && !e.isThere(S1))retval-=100;
							
						}
				}
				counter++;
			}
			
		}

		
		return retval;

	}

	
}
