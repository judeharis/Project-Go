package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.HeuristicI;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SixDiesEightLives implements HeuristicI {
	Evaluator e;

	public SixDiesEightLives (Evaluator e){
		this.e=e;
	}

	@Override
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ArrayList<Tuple> bar6 = e.checkStringForBar(sstring, 6,1);
		if (!bar6.isEmpty())retval += barEval6(bar6, sstring, 6);
		
//		ArrayList<Tuple> bar7 = checkStringForBar(sstring, 7);
//		ArrayList<Tuple> bar8 = checkStringForBar(sstring, 8);
//		if (!bar7.isEmpty())
//			retval += barEval(bar7, sstring, 7);
//
//		if (!bar8.isEmpty())
//			retval += barEval(bar8, sstring, 8);
		
		return retval;
	}
	
	
	public int barEval6(ArrayList<Tuple> barString, ArrayList<Tuple> sstring, int size) {
		UDLR side = e.distFromSide(barString, 1);
		int retval = 0;
		if (!side.isEmpty()) {
			Tuple BS0 =  barString.get(0).side(side.diag(true));
			Tuple BS7 =  barString.get((barString.size() - 1)).side(side.diag(false));
			Tuple BSn1 =  BS0.side(side.diag(true));
			Tuple BSn2 =  BSn1.side(side.diag(true));
			Tuple BSn3 =  BSn2.side(side.diag(true));
			Tuple BS8 =  BS7.side(side.diag(false));
			Tuple BS9 =  BS8.side(side.diag(false));
			Tuple BS10 =  BS9.side(side.diag(false));
			
			Tuple S1 = barString.get(0).side(side);
			Tuple S0 = S1.side(side.diag(true));
			Tuple Sn1 = S0.side(side.diag(true));
			Tuple Sn2 = Sn1.side(side.diag(true));
			Tuple Sn3 = Sn2.side(side.diag(true));
			Tuple S2 = S1.side(side.diag(false));
			Tuple S3 = S2.side(side.diag(false));
			Tuple S6 = barString.get((barString.size() - 1)).side(side);
			Tuple S7 = S6.side(side.diag(false));
			Tuple S8 = S7.side(side.diag(false));
			Tuple S9 = S8.side(side.diag(false));
			Tuple S10 = S9.side(side.diag(false));
			Tuple S5 = S6.side(side.diag(true));
			Tuple S4 = S5.side(side.diag(true));
			
			Tuple OSn1 = BSn1.side(side.opp());
			Tuple OSn2 = OSn1.side(side.diag(true));
			Tuple OS8 = BS8.side(side.opp());
			Tuple OS9 = OS8.side(side.diag(false));
			
			if (e.isThere(S1)&& e.isThere(S6)) {
				retval += 2000;
				if (e.isThere(S2) && e.isThere(S5))retval -= 2000;
				else if (e.isThere(S2) || e.isThere(S5))retval -= 1000;
				if (e.isThere(S3) && e.isThere(S4))retval += 1000;
				else if (e.isThere(S3) || e.isThere(S4))retval += 1000;
				
				if (e.isEnemy(S3) || e.isEnemy(S4))retval -= 1000;
				if (e.isEnemy(S3) && e.isEnemy(S4))retval -= 2000;
			}else if(e.isEnemy(BS0) && e.isEnemy(BS7)) {
				

				
				
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
				
				
				
				if (e.isEnemies(S4) && e.isTheres(S6,S2)) retval-=200;
				if (e.isEnemies(S3) && e.isTheres(S1,S5)) retval-=200;
				
				if (e.isEnemies(S6) && e.isTheres(S2,S4)) retval-=200;
				if (e.isEnemies(S1) && e.isTheres(S5,S3)) retval-=200;
				
				if (e.isEnemies(S2) && e.isTheres(S6,S4)) retval-=200;
				if (e.isEnemies(S5) && e.isTheres(S1,S3)) retval-=200;
				

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
					if(!e.isThere(t.side(side)) && e.isEnemy(t.dubSide(side.diag(false),side))) retval-=100;
					while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(false)))) {
						retval-=100;
						t = t.side(side.diag(false));
					}
				}
				
				if (e.isEnemies(BSn1)&& e.isTheres(S1,S0)) {
					retval-=100;
					Tuple t = BSn1;
					if(!e.isThere(t.side(side)) && e.isEnemy(t.dubSide(side.diag(true),side))) retval-=100;
					while (e.isThere(t.side(side)) && e.isEnemy(t.side(side.diag(true)))) {
						retval-=100;
						t = t.side(side.diag(true));
					}
					
				}

				
				
				//------------------------------------------------------------------------------
				
				
				
					if (e.isThere(S1) || e.isThere(S6)) retval+=100;

					//if (e.isThere(S1)&& e.isThere(S6)) retval += 500; -- Changes heuristic check
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
					
					
//					//Automatically correct

//
//					//Automatically correct
//					if (e.isEnemies(S7,S4) && e.ecapped(S1) && e.isTheres(S6,S2,S0));
//					if (e.isEnemies(S0,S3) && e.ecapped(S6) && e.isTheres(S1,S5,S7));
//					if (e.isEnemies(S4,BSn1) && e.isTheres(S6,S2,S0,Sn1)) retval+=100;
//					if (e.isEnemies(S3,BS8) && e.isTheres(S1,S5,S7,S8)) retval+=100;
//
//					if (e.isEnemies(S7,S4,BSn1) && e.isTheres(S6,S2,S0,Sn1)) retval+=100;
//					if (e.isEnemies(S0,S3,BS8) && e.isTheres(S1,S5,S7,S8)) retval+=100;
//					if (e.isEnemies(S4,BSn1,Sn2) && e.isTheres(S6,S2,S0,Sn1,BSn2)) retval+=100;
//					if (e.isEnemies(S3,BS8,S9) && e.isTheres(S1,S5,S7,S8,BS9)) retval+=100;
//
//					if (e.isEnemies(S7,S4,BSn1,Sn2) && e.isTheres(S6,S2,S0,Sn1,BSn2)) retval+=100;
//					if (e.isEnemies(S0,S3,BS8,S9) && e.isTheres(S1,S5,S7,S8,BS9)) retval+=100;
//					if (e.isEnemies(S4,BSn1,Sn2) && e.ecapped(S1) && e.isTheres(S6,S2,BSn2,S0)) retval+=100;
//					if (e.isEnemies(S3,BS8,S9) && e.ecapped(S6) && e.isTheres(S1,S5,BS9,S7)) retval+=100;
				

				
			}

		}

		return retval;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
