package Go.SlickGo.Heuristics;

import java.util.ArrayList;
import java.util.Collections;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SixDiesEightLives {
	Evaluator e;
	PatternSearcher ps;

	public SixDiesEightLives (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);

		
//		ArrayList<Tuple> bar6 = e.checkStringForBar(sstring, 6,1);
//		if (!bar6.isEmpty())retval += barEval6(bar6, sstring, 6);
		

//		ArrayList<Tuple> bar8 = checkStringForBar(sstring, 8);
		
		
		
		ArrayList<Pattern> pattern7 = Pattern.sToPv2("xrxrxrxrxrxrxrozloddS", Stone.BLACK);
		ArrayList<Tuple> bar7 =ps.stringMatch(sstring, pattern7);	
		if (!bar7.isEmpty())retval += barEval7(bar7, sstring, 7);
		
//
//		if (!bar8.isEmpty())
//			retval += barEval(bar8, sstring, 8);
		
		return retval;
	}
	
	
	public int barEval6(ArrayList<Tuple> pString, ArrayList<Tuple> sstring, int size) {
		Collections.sort(pString);
//		UDLR side = e.distFromSide(pString, 1);
		int retval = 0;
		boolean diagSide= ps.dirSideToBool();
		UDLR side = ps.dirNumToDir();
		Tuple BS0 =  pString.get(0).side(side.diag(!diagSide));
		Tuple BS7 =  pString.get((pString.size() - 1)).side(side.diag(diagSide));
		Tuple BSn1 =  BS0.side(side.diag(!diagSide));
		Tuple BSn2 =  BSn1.side(side.diag(!diagSide));
		Tuple BS8 =  BS7.side(side.diag(diagSide));
		Tuple BS9 =  BS8.side(side.diag(diagSide));
		
		Tuple S1 = pString.get(0).side(side);
		Tuple S0 = S1.side(side.diag(!diagSide));
		Tuple Sn1 = S0.side(side.diag(!diagSide));
		Tuple Sn2 = Sn1.side(side.diag(!diagSide));
		Tuple Sn3 = Sn2.side(side.diag(!diagSide));
		Tuple S2 = S1.side(side.diag(diagSide));
		Tuple S3 = S2.side(side.diag(diagSide));
		Tuple S6 = pString.get((pString.size() - 1)).side(side);
		Tuple S7 = S6.side(side.diag(diagSide));
		Tuple S8 = S7.side(side.diag(diagSide));
		Tuple S9 = S8.side(side.diag(diagSide));
		Tuple S10 = S9.side(side.diag(diagSide));
		Tuple S5 = S6.side(side.diag(!diagSide));
		Tuple S4 = S5.side(side.diag(!diagSide));
		

		
		if(!e.isTheres(S1,S6) && e.isEnemy(BS0) && e.isEnemy(BS7)) {
			
			//negative
			if (e.isEnemy(S1) || e.isEnemy(S6)) retval-=100;
		
			if (e.isEnemies(S1,S7) && !e.isThere(S0)) retval-=100;
			if (e.isEnemies(S6,S0) && !e.isThere(S7)) retval-=100;
			
			if (e.isEnemies(S1,S7,S0)) retval-=100;
			if (e.isEnemies(S6,S0,S1))retval-=100;

			if (e.isEnemies(S1,S7) && (e.isThere(S6) || e.isThere(S8))  && !e.isThere(S0)) retval-=100;
			if (e.isEnemies(S6,S0) && (e.isThere(S1) || e.isThere(Sn1)) && !e.isThere(S7)) retval-=100;

			if (e.isEnemies(S1,S0)) retval-=10;
			if (e.isEnemies(S6,S7)) retval-=10;
			
		
			

			if (e.isEnemies(S1,S7) && e.isThere(S8)) retval-=100;
			if (e.isEnemies(S6,S0) && e.isThere(Sn1)) retval-=100;
			

			if (e.isEnemies(BS8) && e.isThere(S7)) retval-=100;
			if (e.isEnemies(BSn1) && e.isThere(S0)) retval-=100;
			
			if (e.isEnemies(BS8,S8) && e.isThere(S7)) retval-=100;
			if (e.isEnemies(BSn1,Sn1) && e.isThere(S0)) retval-=100;
	
			if (e.isEnemies(BS8,S9) && e.isTheres(S7,S8) &&!e.isThere(S6)) retval-=100;
			if (e.isEnemies(BSn1,Sn2) && e.isTheres(S0,Sn1) && !e.isThere(S1)) retval-=100;
			
			if (e.isEnemies(S9,S8) && e.isTheres(BS9,S10)) retval-=100;
			if (e.isEnemies(Sn2,Sn1) && e.isTheres(BSn2,Sn3)) retval-=100;
			
			if (e.isEnemies(S9,S6) && e.capped(S7)&& e.capped(S8) && e.isTheres(BS9,S10)) retval-=100;
			if (e.isEnemies(Sn2,S1) && e.capped(S0)&& e.capped(Sn1) && e.isTheres(BSn2,Sn3)) retval-=100;
			

			if (e.isEnemies(BS8)&& e.isTheres(S7)) {
				retval-=150;
				Tuple t = BS8;
				while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(diagSide)))) {
					retval-=150;
					t = t.side(side.diag(diagSide));
				}
				if(e.isEnemy(t.side(side))) retval-=100;
			}
			
			if (e.isEnemies(BSn1)&& e.isTheres(S0)) {
				retval-=150;
				Tuple t = BSn1;
				while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(!diagSide)))) {
					retval-=150;
					t = t.side(side.diag(!diagSide));
				}
				if(e.isEnemy(t.side(side))) retval-=100;
				
			}

			
			
			//positive
			
			
		
			if (e.isThere(S1) || e.isThere(S6)) retval+=100;

			if (e.isEnemy(S1) && e.isThere(S6)) retval+=100;
			if (e.isEnemy(S6) && e.isThere(S1)) retval+=100;
			
			
			if (e.isTheres(S6,S2) && !e.isEnemies(S4)) retval+=100;
			if (e.isTheres(S1,S5) && !e.isEnemies(S3)) retval+=100;

			
			if (e.isEnemies(S1) && e.isTheres(S6,S2)) retval+=100;
			if (e.isEnemies(S6) && e.isTheres(S1,S5)) retval+=100;

			if (e.isEnemies(S1,S7) && e.isTheres(S6,S2)) retval+=100;
			if (e.isEnemies(S6,S0) && e.isTheres(S1,S5)) retval+=100;
			
		
			
			if (e.isTheres(S6,S7)) retval+=100;
			if (e.isTheres(S1,S0)) retval+=100;
			

			
			if (e.isEnemies(BSn1) && e.isTheres(S0,Sn1)) retval+=300;
			if (e.isEnemies(BS8) && e.isTheres(S7,S8)) retval+=300;
			
			if (e.isEnemies(BSn1,Sn2) && e.isTheres(S0,Sn1,BSn2)) retval+=100;
			if (e.isEnemies(BS8,S9) && e.isTheres(S7,S8,BS9)) retval+=100;
			
//			if (e.isEnemies(S4) && e.ecapped(S1) && e.isTheres(S2,S0)) retval+=100;
//			if (e.isEnemies(S3) && e.ecapped(S6) && e.isTheres(S5,S7)) retval+=100;
			
			if (e.isEnemies(BS8)&& e.isTheres(S7,S8)) {
				retval+=110;
				Tuple t = S8;
				while (e.isThere(t.side(side.diag(diagSide))) && !ps.isCorner(t.side(side.diag(diagSide)))) {
					retval+=110;
					t = t.side(side.diag(diagSide));
				}
			}
			if (e.isEnemies(BSn1)&& e.isTheres(S0,Sn1)) {
				retval+=110;
				Tuple t = Sn1;
				while (e.isThere(t.side(side.diag(!diagSide))) && !ps.isCorner(t.side(side.diag(!diagSide)))) {
					retval+=110;
					t = t.side(side.diag(!diagSide));
				}
			}
			
					
			

		}

		return retval;
	}

	
	
	
	public int barEval7O(ArrayList<Tuple> pString, ArrayList<Tuple> sstring, int size) {
		Collections.sort(pString);
		boolean diagSide= ps.dirSideToBool();
		UDLR side = ps.dirNumToDir();
		int retval = 0;

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
			
			
			if(e.isEnemy(S1) && e.isThere(S2) && !e.isTheres(S3,S7) && !e.isTheres(S6))retval+=100;
			if(e.isEnemy(S7) && e.isThere(S6) && !e.isTheres(S5,S1) && !e.isTheres(S2))retval+=100;
			
			if(e.isEnemies(S1,S7) && e.isTheres(S2,S6))retval+=100;
			
//			if(e.ecapped(S1) && e.isTheres(S2,S6,S0))retval+=100;
//			if(e.ecapped(S7) && e.isTheres(S2,S6,S8))retval+=100;
			
			
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
			if(e.isEnemy(S1) || e.isEnemy(S7))retval-=100;
			if(e.isEnemies(S1,S7) && e.isThere(S2))retval-=100;
			if(e.isEnemies(S7,S1) && e.isThere(S6))retval-=100;
			
			if(e.isEnemies(S1,S8) && !e.isThere(S2) && !e.isThere(S7))retval-=100;
			if(e.isEnemies(S7,S0) && !e.isThere(S6) && !e.isThere(S1))retval-=100;

			
			if(e.isEnemies(BSn1) && e.isTheres(S0))retval-=150;
			if(e.isEnemies(BS9) && e.isTheres(S8))retval-=150;
			

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

		

		return retval;
	}
	
	
	
	
	
	public int barEval7(ArrayList<Tuple> pString, ArrayList<Tuple> sstring, int size) {
		Collections.sort(pString);
		boolean diagSide= ps.dirSideToBool();
		UDLR side = ps.dirNumToDir();
		int retval = 0;

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
			
			
//				if(e.isEnemies(S1,S7) && e.isTheres(S2,S6))retval+=100;
			
//			if(e.ecapped(S1) && e.isTheres(S2,S6,S0))retval+=100;
//			if(e.ecapped(S7) && e.isTheres(S2,S6,S8))retval+=100;
			
			
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

		return retval;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
