package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SixDieOld {
	Evaluator e;
	PatternSearcher ps;

	public SixDieOld (Evaluator e){
		this.e=e;
	}


	

	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern6 = Pattern.sToPv2("xrxrxrxrxrxrozloddS", e.kscolour);
		ArrayList<Tuple> pString =ps.stringMatch(sstring, pattern6);	
		

		if (!pString .isEmpty()) {
			boolean diagSide= ps.dirSideToBool();
			UDLR side = ps.dirNumToDir();
			
//			if (!side.isEmpty()) {
//				Tuple BS0 =  pString.get(0).side(side.diag(!diagSide));
//				Tuple BS7 =  pString.get((pString.size() - 1)).side(side.diag(diagSide));
//				Tuple BSn1 =  BS0.side(side.diag(!diagSide));
//				Tuple BSn2 =  BSn1.side(side.diag(!diagSide));
//				Tuple BS8 =  BS7.side(side.diag(diagSide));
//				Tuple BS9 =  BS8.side(side.diag(diagSide));
//				
//				Tuple S1 = pString.get(0).side(side);
//				Tuple S0 = S1.side(side.diag(!diagSide));
//				Tuple Sn1 = S0.side(side.diag(!diagSide));
//				Tuple Sn2 = Sn1.side(side.diag(!diagSide));
//				Tuple Sn3 = Sn2.side(side.diag(!diagSide));
//				Tuple S2 = S1.side(side.diag(diagSide));
//				Tuple S3 = S2.side(side.diag(diagSide));
//				Tuple S6 = pString.get((pString.size() - 1)).side(side);
//				Tuple S7 = S6.side(side.diag(diagSide));
//				Tuple S8 = S7.side(side.diag(diagSide));
//				Tuple S9 = S8.side(side.diag(diagSide));
//				Tuple S10 = S9.side(side.diag(diagSide));
//				Tuple S5 = S6.side(side.diag(!diagSide));
//				Tuple S4 = S5.side(side.diag(!diagSide));
//				
//
//				
//				if(!e.isTheres(S1,S6) && e.isEnemy(BS0) && e.isEnemy(BS7)) {
//					
//					//negative
//					if (e.isEnemy(S1) || e.isEnemy(S6)) retval-=100;
//				
//					if (e.isEnemies(S1,S7) && !e.isThere(S0)) retval-=100;
//					if (e.isEnemies(S6,S0) && !e.isThere(S7)) retval-=100;
//					
//					if (e.isEnemies(S1,S7,S0)) retval-=100;
//					if (e.isEnemies(S6,S0,S1))retval-=100;
//
//					if (e.isEnemies(S1,S7) && (e.isThere(S6) || e.isThere(S8))  && !e.isThere(S0)) retval-=100;
//					if (e.isEnemies(S6,S0) && (e.isThere(S1) || e.isThere(Sn1)) && !e.isThere(S7)) retval-=100;
//
//					if (e.isEnemies(S1,S0)) retval-=10;
//					if (e.isEnemies(S6,S7)) retval-=10;
//					
//				
//					
//
//					if (e.isEnemies(S1,S7) && e.isThere(S8)) retval-=100;
//					if (e.isEnemies(S6,S0) && e.isThere(Sn1)) retval-=100;
//					
//
//					if (e.isEnemies(BS8) && e.isThere(S7)) retval-=100;
//					if (e.isEnemies(BSn1) && e.isThere(S0)) retval-=100;
//					
//					if (e.isEnemies(BS8,S8) && e.isThere(S7)) retval-=100;
//					if (e.isEnemies(BSn1,Sn1) && e.isThere(S0)) retval-=100;
//			
//					if (e.isEnemies(BS8,S9) && e.isTheres(S7,S8) &&!e.isThere(S6)) retval-=100;
//					if (e.isEnemies(BSn1,Sn2) && e.isTheres(S0,Sn1) && !e.isThere(S1)) retval-=100;
//					
//					if (e.isEnemies(S9,S8) && e.isTheres(BS9,S10)) retval-=100;
//					if (e.isEnemies(Sn2,Sn1) && e.isTheres(BSn2,Sn3)) retval-=100;
//					
//					if (e.isEnemies(S9,S6) && e.capped(S7)&& e.capped(S8) && e.isTheres(BS9,S10)) retval-=100;
//					if (e.isEnemies(Sn2,S1) && e.capped(S0)&& e.capped(Sn1) && e.isTheres(BSn2,Sn3)) retval-=100;
//					
//
//					if (e.isEnemies(BS8)&& e.isTheres(S7)) {
//						retval-=150;
//						Tuple t = BS8;
//						while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(diagSide)))) {
//							retval-=150;
//							t = t.side(side.diag(diagSide));
//						}
//						if(e.isEnemy(t.side(side))) retval-=100;
//					}
//					
//					if (e.isEnemies(BSn1)&& e.isTheres(S0)) {
//						retval-=150;
//						Tuple t = BSn1;
//						while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(!diagSide)))) {
//							retval-=150;
//							t = t.side(side.diag(!diagSide));
//						}
//						if(e.isEnemy(t.side(side))) retval-=100;
//						
//					}
//
//					
//					
//					//positive
//					
//					
//				
//					if (e.isThere(S1) || e.isThere(S6)) retval+=100;
//
//					if (e.isEnemy(S1) && e.isThere(S6)) retval+=100;
//					if (e.isEnemy(S6) && e.isThere(S1)) retval+=100;
//					
//					
//					if (e.isTheres(S6,S2) && !e.isEnemies(S4)) retval+=100;
//					if (e.isTheres(S1,S5) && !e.isEnemies(S3)) retval+=100;
//
//					
//					if (e.isEnemies(S1) && e.isTheres(S6,S2)) retval+=100;
//					if (e.isEnemies(S6) && e.isTheres(S1,S5)) retval+=100;
//
//					if (e.isEnemies(S1,S7) && e.isTheres(S6,S2)) retval+=100;
//					if (e.isEnemies(S6,S0) && e.isTheres(S1,S5)) retval+=100;
//					
//				
//					
//					if (e.isTheres(S6,S7)) retval+=100;
//					if (e.isTheres(S1,S0)) retval+=100;
//					
//
//					
//					if (e.isEnemies(BSn1) && e.isTheres(S0,Sn1)) retval+=300;
//					if (e.isEnemies(BS8) && e.isTheres(S7,S8)) retval+=300;
//					
//					if (e.isEnemies(BSn1,Sn2) && e.isTheres(S0,Sn1,BSn2)) retval+=100;
//					if (e.isEnemies(BS8,S9) && e.isTheres(S7,S8,BS9)) retval+=100;
//					
//					if (e.isEnemies(S4) && e.ecapped(S1) && e.isTheres(S2,S0)) retval+=100;
//					if (e.isEnemies(S3) && e.ecapped(S6) && e.isTheres(S5,S7)) retval+=100;
//					
//					if (e.isEnemies(BS8)&& e.isTheres(S7,S8)) {
//						retval+=110;
//						Tuple t = S8;
//						while (e.isThere(t.side(side.diag(diagSide))) && !ps.isCorner(t.side(side.diag(diagSide)))) {
//							retval+=110;
//							t = t.side(side.diag(diagSide));
//						}
//					}
//					if (e.isEnemies(BSn1)&& e.isTheres(S0,Sn1)) {
//						retval+=110;
//						Tuple t = Sn1;
//						while (e.isThere(t.side(side.diag(!diagSide))) && !ps.isCorner(t.side(side.diag(!diagSide)))) {
//							retval+=110;
//							t = t.side(side.diag(!diagSide));
//						}
//					}
//					
//						
//				}
//			}

			
			if (!side.isEmpty()) {
				Tuple BS0 =  pString.get(0).side(side.diag(!diagSide));
				Tuple BS7 =  pString.get((pString.size() - 1)).side(side.diag(diagSide));


				
				Tuple S1 = pString.get(0).side(side);
				Tuple S0 = S1.side(side.diag(!diagSide));
				Tuple Sn1 = S0.side(side.diag(!diagSide));

				Tuple S2 = S1.side(side.diag(diagSide));
				Tuple S3 = S2.side(side.diag(diagSide));
				Tuple S6 = pString.get((pString.size() - 1)).side(side);
				Tuple S7 = S6.side(side.diag(diagSide));
				Tuple S8 = S7.side(side.diag(diagSide));
				Tuple S5 = S6.side(side.diag(!diagSide));
				Tuple S4 = S5.side(side.diag(!diagSide));
				

				
				if(!e.isTheres(S1,S6) && e.isEnemy(BS0) && e.isEnemy(BS7)) {
					
					//positive
					
					
					
					if (e.isThere(S1) || e.isThere(S6)) retval+=100;
					if (e.isThere(S1)) retval+=100;
					if (e.isThere(S6)) retval+=100;
					
					if (e.isEnemy(S1) && e.isThere(S6)) retval+=100;
					if (e.isEnemy(S6) && e.isThere(S1)) retval+=100;
					
					
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
