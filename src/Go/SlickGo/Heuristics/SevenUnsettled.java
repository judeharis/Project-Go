package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SevenUnsettled  {
	Evaluator e;
	PatternSearcher ps;

	public SevenUnsettled (Evaluator e){
		this.e=e;
	}


	
	public int evaluateOld(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern7 = Pattern.sToPv2("xrxrxrxrxrxrxrozloddS", Stone.BLACK);
		ArrayList<Tuple> pString =ps.stringMatch(sstring, pattern7);	
		

		if (!pString .isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			if (!side.isEmpty()) {
				Tuple BS0 =  pString.get(0).side(side.diag(!diagSide));
				Tuple BSn1 =  BS0.side(side.diag(!diagSide));
				Tuple BSn2 =  BSn1.side(side.diag(!diagSide));

				Tuple BS8 =  pString.get((pString.size() - 1)).side(side.diag(diagSide));
				Tuple BS9 =  BS8.side(side.diag(diagSide));
				Tuple BS10 =  BS9.side(side.diag(diagSide));

				Tuple S1 = pString.get(0).side(side);
				Tuple S0 = S1.side(side.diag(!diagSide));
				Tuple Sn1 = S0.side(side.diag(!diagSide));

				
				Tuple S2 = S1.side(side.diag(diagSide));
				Tuple S3 = S2.side(side.diag(diagSide));
				Tuple S4 = S3.side(side.diag(diagSide));
				Tuple S5 = S4.side(side.diag(diagSide));
				Tuple S6 = S5.side(side.diag(diagSide));
				Tuple S7 = S6.side(side.diag(diagSide));
				Tuple S8 = S7.side(side.diag(diagSide));
				Tuple S9 = S8.side(side.diag(diagSide));



				
				if(!e.isTheres(S1,S7) && e.isEnemy(BS0) && e.isEnemy(BS8)) {
					
					//positive
					if(e.isThere(S1) && !e.isEnemy(S7) && !e.isTheres(S2,S0))retval+=100;
					if(e.isThere(S7) && !e.isEnemy(S1) && !e.isTheres(S6,S8))retval+=100;
					
					
			
					if(e.isEnemy(S1) && e.isThere(S7))retval+=100;
					if(e.isEnemy(S7) && e.isThere(S1))retval+=100;
					
					
//					if(e.isEnemies(S1,S7) && e.isTheres(S2,S6))retval+=100;
					
					if(e.ecapped(S1) && e.isTheres(S2,S6,S0))retval+=100;
					if(e.ecapped(S7) && e.isTheres(S2,S6,S8))retval+=100;
					
					
					if(e.isTheres(S1,S0) || e.isTheres(S2,S0))retval+=100;
					if(e.isTheres(S7,S8) || e.isTheres(S6,S8))retval+=100;

					

					if (e.isEnemies(BS9)&& e.isTheres(S8,S9)) {
						retval+=110;
						Tuple t = S9;
						while (e.isThere(t.side(side.diag(diagSide))) && !ps.isCorner(t.side(side.diag(diagSide)))) {
							retval+=110;
							t = t.side(side.diag(diagSide));
						}
					}
					
					if (e.isEnemies(BSn1)&& e.isTheres(S0,Sn1)) {
						retval+=110;
						Tuple t = Sn1;
						while (e.isThere(t.side(side.diag(!diagSide)))  && !ps.isCorner(t.side(side.diag(!diagSide)))) {
							retval+=110;
							t = t.side(side.diag(!diagSide));
						}
					}
					
					

					
					//negative
					if(e.isEnemy(S1) || e.isEnemy(S7))retval-=140;
					if(e.isEnemies(S1,S5) && e.isThere(S7))retval-=100;
					if(e.isEnemies(S7,S3) && e.isThere(S1))retval-=100;
					
					if(e.isEnemies(S1,S8) && !e.isThere(S2) && !e.isThere(S7))retval-=100;
					if(e.isEnemies(S7,S0) && !e.isThere(S6) && !e.isThere(S1))retval-=100;

					
					if(e.isEnemies(BSn1) && e.isTheres(S0))retval-=150;
					if(e.isEnemies(BS9) && e.isTheres(S8))retval-=150;
					
					if(e.isEnemies(S0) && e.isTheres(BSn1))retval-=150;
					if(e.isEnemies(S8) && e.isTheres(BS9))retval-=150;
					
					

					if(e.isEnemies(BSn1,Sn1) && e.isTheres(S2,S6,S0))retval-=100;
					if(e.isEnemies(BS9,S9) && e.isTheres(S2,S6,S8))retval-=100;
					
					
					
					if (e.isEnemies(BS9,BS10)&& e.isTheres(S8,S9)) {
						retval-=110;
						Tuple t = BS10;
						while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(diagSide)))) {
							retval-=110;
							t = t.side(side.diag(diagSide));
						}
						if(e.isEnemy(t.side(side))) retval-=100;
					}
					
					if (e.isEnemies(BSn1,BSn2)&& e.isTheres(S0,Sn1)) {
						retval-=110;
						Tuple t = BSn2;
						while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(!diagSide)))) {
							retval-=110;
							t = t.side(side.diag(!diagSide));
						}
						if(e.isEnemy(t.side(side))) retval-=100;
					}
						
				}

			}


		}
		
		return retval;

	}


	
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern7 = Pattern.sToPv2("xrxrxrxrxrxrxrozloddS", Stone.BLACK);
		ArrayList<Tuple> pString =ps.stringMatch(sstring, pattern7);	
		

		if (!pString .isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			if (!side.isEmpty()) {
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




				
				if(!e.isTheres(S1,S7) && e.isEnemy(BS0) && e.isEnemy(BS8)) {
					
					//positive
					if(e.isThere(S1) && !e.isEnemy(S7) && !e.isTheres(S2,S0))retval+=100;
					if(e.isThere(S7) && !e.isEnemy(S1) && !e.isTheres(S6,S8))retval+=100;
					
					
			
					if(e.isEnemy(S1) && e.isThere(S7))retval+=100;
					if(e.isEnemy(S7) && e.isThere(S1))retval+=100;
					
					
					if(e.isEnemies(S1,S7) && e.isTheres(S2,S6))retval+=100;
					



					
					//negative
					if(e.isEnemy(S1) || e.isEnemy(S7))retval-=100;
					if(e.isEnemy(S1) && e.isEnemy(S7))retval-=100;
					if(e.isEnemies(S1,S5) && e.isThere(S7))retval-=100;
					if(e.isEnemies(S7,S3) && e.isThere(S1))retval-=100;
					
					if(e.isEnemies(S1,S8) && !e.isThere(S2) && !e.isThere(S7))retval-=100;
					if(e.isEnemies(S7,S0) && !e.isThere(S6) && !e.isThere(S1))retval-=100;

					
					

				}

			}


		}
		
		return retval;

	}

	
}
