package Go.SlickGo.Heuristics;

import java.util.ArrayList;
import java.util.Collections;

import Go.SlickGo.Evaluator;
import Go.SlickGo.HeuristicI;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SixDiesEightLives implements HeuristicI {
	Evaluator e;
	PatternSearcher ps;

	public SixDiesEightLives (Evaluator e){
		this.e=e;
	}

	@Override
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);

		
		ArrayList<Tuple> bar6 = e.checkStringForBar(sstring, 6,1);
		if (!bar6.isEmpty())retval += barEval6(bar6, sstring, 6);
		
//		ArrayList<Tuple> bar7 = checkStringForBar(sstring, 7);
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
		UDLR side = e.distFromSide(pString, 1);
		int retval = 0;
		if (!side.isEmpty()) {
			Tuple BS0 =  pString.get(0).side(side.diag(true));
			Tuple BS7 =  pString.get((pString.size() - 1)).side(side.diag(false));
			Tuple BSn1 =  BS0.side(side.diag(true));
			Tuple BSn2 =  BSn1.side(side.diag(true));
			Tuple BS8 =  BS7.side(side.diag(false));
			Tuple BS9 =  BS8.side(side.diag(false));
			
			Tuple S1 = pString.get(0).side(side);
			Tuple S0 = S1.side(side.diag(true));
			Tuple Sn1 = S0.side(side.diag(true));
			Tuple Sn2 = Sn1.side(side.diag(true));
			Tuple Sn3 = Sn2.side(side.diag(true));
			Tuple S2 = S1.side(side.diag(false));
			Tuple S3 = S2.side(side.diag(false));
			Tuple S6 = pString.get((pString.size() - 1)).side(side);
			Tuple S7 = S6.side(side.diag(false));
			Tuple S8 = S7.side(side.diag(false));
			Tuple S9 = S8.side(side.diag(false));
			Tuple S10 = S9.side(side.diag(false));
			Tuple S5 = S6.side(side.diag(true));
			Tuple S4 = S5.side(side.diag(true));
			

			
			if(!e.isTheres(S1,S6) && e.isEnemy(BS0) && e.isEnemy(BS7)) {
				
				
				if (e.isEnemy(S1) || e.isEnemy(S6)) retval-=100;
				if (e.isEnemy(S1) && e.isThere(S6)) retval-=100;
				if (e.isEnemy(S6) && e.isThere(S1)) retval-=100;
			
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
				

				if (e.isEnemies(BS8)&& e.isTheres(S6,S7)) {
					retval-=100;
					Tuple t = BS8;
					while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(false)))) {
						retval-=200;
						t = t.side(side.diag(false));
					}
					if(e.isEnemy(t.side(side))) retval-=100;
				}
				
				if (e.isEnemies(BSn1)&& e.isTheres(S1,S0)) {
					retval-=100;
					Tuple t = BSn1;
					while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(true)))) {
						retval-=200;
						t = t.side(side.diag(true));
					}
					if(e.isEnemy(t.side(side))) retval-=100;
					
				}

				
				
				//------------------------------------------------------------------------------
				
				
				
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
					
					if (e.isEnemies(S4) && e.ecapped(S1) && e.isTheres(S2,S0)) retval+=100;
					if (e.isEnemies(S3) && e.ecapped(S6) && e.isTheres(S5,S7)) retval+=100;
					
					if (e.isEnemies(BS8)&& e.isTheres(S6,S7,S8)) {
						retval+=100;
						Tuple t = S8;
						while (e.isThere(t.side(side.diag(false)))) {
							retval+=100;
							t = t.side(side.diag(false));
						}
					}
					if (e.isEnemies(BSn1)&& e.isTheres(S1,S0,Sn1)) {
						retval+=100;
						Tuple t = Sn1;
						while (e.isThere(t.side(side.diag(true)))) {
							retval+=100;
							t = t.side(side.diag(true));
						}
					}
					
			}

		}

		return retval;
	}

	
	
	
	public int barEval7(ArrayList<Tuple> pString, ArrayList<Tuple> sstring, int size) {
		Collections.sort(pString);
		UDLR side = e.distFromSide(pString, 1);
		int retval = 0;
		if (!side.isEmpty()) {
			Tuple BS0 =  pString.get(0).side(side.diag(true));
			Tuple BSn1 =  BS0.side(side.diag(true));
			Tuple BSn2 =  BSn1.side(side.diag(true));
			Tuple BSn3 =  BSn2.side(side.diag(true));
			
			Tuple BS8 =  pString.get((pString.size() - 1)).side(side.diag(false));
			Tuple BS9 =  BS8.side(side.diag(false));
			Tuple BS10 =  BS9.side(side.diag(false));
			Tuple BS11 =  BS10.side(side.diag(false));
			
			Tuple S1 = pString.get(0).side(side);
			Tuple S0 = S1.side(side.diag(true));
			Tuple Sn1 = S0.side(side.diag(true));
			Tuple Sn2 = Sn1.side(side.diag(true));
			Tuple Sn3 = Sn2.side(side.diag(true));
			
			
			Tuple S2 = S1.side(side.diag(false));
			Tuple S3 = S2.side(side.diag(false));
			Tuple S4 = S3.side(side.diag(false));
			Tuple S5 = S4.side(side.diag(false));
			Tuple S6 = S5.side(side.diag(false));
			Tuple S7 = S6.side(side.diag(false));
			Tuple S8 = S7.side(side.diag(false));
			Tuple S9 = S8.side(side.diag(false));
			Tuple S10 = S9.side(side.diag(false));
			Tuple S11 = S10.side(side.diag(false));

			
			if(!e.isTheres(S1,S7) && e.isEnemy(BS0) && e.isEnemy(BS8)) {
				
				//positive
				if(e.isThere(S1) && !e.isEnemy(S7) && !e.isTheres(S2,S0))retval+=100;
				if(e.isThere(S7) && !e.isEnemy(S1) && !e.isTheres(S6,S8))retval+=100;
				
				
				if(e.isEnemy(S1) && e.isThere(S2))retval+=100;
				if(e.isEnemy(S7) && e.isThere(S6))retval+=100;
				
				if(e.isEnemies(S1,S7) && e.isTheres(S2,S6))retval+=100;
				
				if(e.ecapped(S1) && e.isTheres(S2,S6,S0))retval+=100;
				if(e.ecapped(S7) && e.isTheres(S2,S6,S8))retval+=100;
				

				if (e.isEnemies(BS9)&& e.isTheres(S6,S8,S9)) {
					retval+=110;
					Tuple t = S9;
					while (e.isThere(t.side(side.diag(false)))) {
						retval+=110;
						t = t.side(side.diag(false));
					}
				}
				
				if (e.isEnemies(BSn1)&& e.isTheres(S2,S0,Sn1)) {
					retval+=110;
					Tuple t = Sn1;
					while (e.isThere(t.side(side.diag(true)))) {
						retval+=110;
						t = t.side(side.diag(true));
					}
				}
				
				

				
				//negative
				if(e.isEnemy(S1) || e.isEnemy(S7))retval-=100;
				if(e.isEnemies(S1,S7) && e.isThere(S2))retval-=100;
				if(e.isEnemies(S7,S1) && e.isThere(S6))retval-=100;
				
				if(e.isEnemies(S1,S8) && !e.isThere(S2) && !e.isThere(S7))retval-=100;
				if(e.isEnemies(S7,S0) && !e.isThere(S6) && !e.isThere(S1))retval-=100;

				

				
				if(e.isEnemies(BSn1) && e.isTheres(S0))retval-=110;
				if(e.isEnemies(BS9) && e.isTheres(S8))retval-=110;
				
				if(e.isEnemies(BSn1,Sn1) && e.isTheres(S2,S6,S0))retval-=100;
				if(e.isEnemies(BS9,S9) && e.isTheres(S2,S6,S8))retval-=100;
				
				if (e.isEnemies(BS10)&& e.isTheres(S8,S9)) {
					retval-=110;
					Tuple t = BS10;
					while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(false)))) {
						retval-=110;
						t = t.side(side.diag(false));
					}
					if(e.isEnemy(t.side(side))) retval-=100;
				}
				
				if (e.isEnemies(BSn2)&& e.isTheres(S0,Sn1)) {
					retval-=110;
					Tuple t = BSn1;
					while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(true)))) {
						retval-=110;
						t = t.side(side.diag(true));
					}
					if(e.isEnemy(t.side(side))) retval-=100;
				}
				
				
				

					
			}

		}

		return retval;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
