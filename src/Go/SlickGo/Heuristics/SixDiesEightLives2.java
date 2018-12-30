package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Tuple;

public class SixDiesEightLives2 {
	Evaluator e;

	public SixDiesEightLives2 (Evaluator e){
		this.e=e;
	}

	
	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
//		ArrayList<Tuple> bar6 = e.checkStringForBar(sstring, 6,1);
//		if (!bar6.isEmpty())retval += barEval6(bar6, sstring, 6);
		
		
		return retval;
	}


//older barEval6	
//	public int barEval(ArrayList<Tuple> barString, ArrayList<Tuple> sstring, int size) {
//		UDLR side = distFromSide(barString, 1);
//		int retval = 0;
//		if (!side.isEmpty()) {
//			
//			Tuple S1 = barString.get(0).side(side);
//			Tuple D1 = barString.get(0).dubSide(side, side.diag(true));
//			Tuple S2 = barString.get((barString.size() - 1)).side(side);
//			Tuple D2 = barString.get((barString.size() - 1)).dubSide(side, side.diag(false));
//
//			Tuple DH1 = D1.side(side.diag(true));
//			Tuple SS1 = S1.side(side.diag(false));
//			Tuple SSS1 = SS1.side(side.diag(false));
//			Tuple DHV1 = DH1.side(side.opp());
//
//			Tuple DH2 = D2.side(side.diag(false));
//			Tuple SS2 = S2.side(side.diag(true));
//			Tuple SSS2 = SS2.side(side.diag(true));
//			Tuple DHV2 = DH2.side(side.opp());
//
//			ArrayList<Tuple> sideString = Tuple.getSideArrayList(barString, side);
//
//			boolean l = false;
//			boolean r = false;
//			int valueOfSide = 300;
//			while (!(l && r) && sideString.size() > 1) {
//				Tuple s1 = sideString.remove(0);
//				Tuple s2 = sideString.remove(sideString.size() - 1);
//				if (isThere(s1)) {
//					retval += valueOfSide;
//					l = true;
//				}
//				if (isThere(s2)) {
//					retval += valueOfSide;
//					r = true;
//				}
//				valueOfSide -= 50;
//			}
//			if (!(l || r) && sideString.size() == 1) {
//				Tuple s1 = sideString.remove(0);
//				if (isThere(s1))
//					retval += valueOfSide;
//			}
//
//
////		if(isThere(S1) && isThere(D1) && isThere(DHV1)) retval+=700;
////			if(isThere(S1) && isThere(D1) && isThere(DH1)) retval+=600;		
////			if(isThere(D1) && isThere(DH1) && isThere(SS1)) retval+=600;
////			if (isThere(DHV1) && isThere(SS1))retval+=1000;
////		    
////			if(isThere(S1) && isThere(D1)) retval+=1000;
////			if(isThere(S1) && isThere(SS2)) retval+=900;
////			if(isThere(D1) && isThere(DH1)) retval+=800;
////		    
////
////			
////			if (isThere(SS1))retval+=100;
////			if (isThere(DH1))retval+=50;
////
////
////			if(isThere(S2) && isThere(D2) && isThere(DHV2)) retval+=700;
////			if(isThere(S2) && isThere(D2) && isThere(DH2)) retval+=600;	
////			if(isThere(D2) && isThere(DH2) && isThere(SS2)) retval+=600;
////			if (isThere(DHV2) && isThere(SS2)) retval+=1000;
////			
////			if(isThere(S2) && isThere(D2)) retval+=1000;
//
//			if (isThere(D1)) {
//				if (isThere(DH1))
//					retval += 400;
//				else
//					retval -= 200;
//				if (isThere(DHV1))
//					retval += 100;
//			}
//
//			if (isThere(D2)) {
//				if (isThere(DH2))
//					retval += 400;
//				else
//					retval -= 200;
//				if (isThere(DHV2))
//					retval += 100;
//			}
//
//			if (isThere(S1)) {
//				retval += 400;
//				if (isThere(D1))
//					retval += 400;
//				else if (isEnemy(D1))
//					retval -= 200;
//			}
//
//			if (isThere(S2)) {
//				retval += 400;
//				if (isThere(D2))
//					retval += 400;
//				else if (isEnemy(D2))
//					retval -= 200;
//			}
//
//			if (isThere(S1) && isThere(SS2))
//				retval += 900;
//			if (isThere(S2) && isThere(SS1))
//				retval += 900;
//
//			if (isThere(S1) && isThere(SSS1))
//				retval += 900;
//			if (isThere(S2) && isThere(SSS2))
//				retval += 900;
//
//			if (isThere(SS2))
//				retval += 100;
//			if (isThere(DH2))
//				retval += 50;
//
//			if (isEnemy(S1))
//				retval -= 300;
//			if (isEnemy(D1))
//				retval -= 200;
//
//			if (isEnemy(S2))
//				retval -= 300;
//			if (isEnemy(D2))
//				retval -= 100;
//
//
//			if (size == 6)
//				retval += -500;
//			else if (size == 8)
//				retval += 500;
//
//		}
//
//		return retval;
//	}



	//An Older barEval6	
//	public int barEval6(ArrayList<Tuple> barString, ArrayList<Tuple> sstring, int size) {
//		UDLR side = e.distFromSide(barString, 1);
//		int retval = 0;
//		if (!side.isEmpty()) {
//			Tuple BS0 =  barString.get(0).side(side.diag(true));
//			Tuple BS7 =  barString.get((barString.size() - 1)).side(side.diag(false));
//			Tuple BSn1 =  BS0.side(side.diag(true));
//			Tuple BSn2 =  BSn1.side(side.diag(true));
//			Tuple BSn3 =  BSn2.side(side.diag(true));
//			Tuple BS8 =  BS7.side(side.diag(false));
//			Tuple BS9 =  BS8.side(side.diag(false));
//			Tuple BS10 =  BS9.side(side.diag(false));
//			
//			Tuple S1 = barString.get(0).side(side);
//			Tuple S0 = S1.side(side.diag(true));
//			Tuple Sn1 = S0.side(side.diag(true));
//			Tuple Sn2 = Sn1.side(side.diag(true));
//			Tuple Sn3 = Sn2.side(side.diag(true));
//			Tuple S2 = S1.side(side.diag(false));
//			Tuple S3 = S2.side(side.diag(false));
//			Tuple S6 = barString.get((barString.size() - 1)).side(side);
//			Tuple S7 = S6.side(side.diag(false));
//			Tuple S8 = S7.side(side.diag(false));
//			Tuple S9 = S8.side(side.diag(false));
//			Tuple S10 = S9.side(side.diag(false));
//			Tuple S5 = S6.side(side.diag(true));
//			Tuple S4 = S5.side(side.diag(true));
//			
//			Tuple OSn1 = BSn1.side(side.opp());
//			Tuple OSn2 = OSn1.side(side.diag(true));
//			Tuple OS8 = BS8.side(side.opp());
//			Tuple OS9 = OS8.side(side.diag(false));
//			
//			if (e.isThere(S1)&& e.isThere(S6)) {
//				retval += 500;
//				if (e.isThere(S2) && e.isThere(S5))retval -= 2000;
//				else if (e.isThere(S2) || e.isThere(S5))retval -= 1000;
//				if (e.isThere(S3) && e.isThere(S4))retval += 1000;
//				else if (e.isThere(S3) || e.isThere(S4))retval += 1000;
//				
//				if (e.isEnemy(S3) || e.isEnemy(S4))retval -= 1000;
//				if (e.isEnemy(S3) && e.isEnemy(S4))retval -= 2000;
//			}else if(e.isEnemy(BS0) && e.isEnemy(BS7)) {
//				
//				
//				
//				
//				if (e.isThere(S2) && e.isThere(S0) &&  e.isThere(BSn1) && e.isThere(Sn2)) {
//					retval += 150;
//					if(e.isThere(OSn1) || e.isThere(Sn3))retval += 150;}
//				if ((e.isEnemy(OSn1) || e.isEnemy(Sn3)) &&  e.isThere(BSn1) && e.isThere(Sn2) && e.isEnemy(BSn2)) retval -=200;
//				if (e.isEnemy(S1) && e.isThere(S2))retval += 200;
//				if (e.isThere(S1) && e.isThere(S0))retval += 150;
//				if (e.isThere(S1) && e.isThere(S5) && e.isEnemy(S3))retval -= 400;
//				if (e.isThere(S1) && e.isEnemy(S6) && e.isEnemy(S0))retval -= 200;
//				if (e.isThere(S1) && e.isThere(S0) && e.isEnemy(Sn1))retval -= 300;
//				if (e.isThere(S0) && e.isEnemy(Sn1) && e.isThere(BSn1))retval += 100;
//				
//				if (e.isThere(Sn2) && e.isEnemy(Sn3))retval -= 200;
//				if (e.isThere(BSn1) && e.isEnemy(OSn1))retval -= 200;
//
//				
//				if (e.isThere(S0) && e.isEnemy(Sn1))retval -= 200;
//				if (e.isThere(S0) && e.isEnemy(BSn1) && e.isEnemy(Sn1))retval -= 100;
//				if (e.isEnemy(S0) && e.isThere(S1))retval -= 50;
//				if(e.isThere(Sn2) && (e.isThere(Sn3) || e.isThere(OSn1)) && e.isThere(BSn1)&& e.isThere(S0)&& e.isThere(S2)&& e.isThere(Sn1)) retval += 300;
//				if((e.isThere(Sn3) || e.isThere(OSn1)) && ((e.capped(BSn1) && e.isThere(Sn2)) || (e.capped(Sn2) && e.isThere(BSn1))) && e.isThere(S0)&& e.isThere(S2)&& e.isEnemy(Sn1)) retval -= 600;
//				
//				
//				if (e.isEnemy(Sn3)&& e.isEnemy(Sn1) && e.isEnemy(BSn2) &&(e.isThere(OSn2) || e.isThere(BSn3))) retval+=300;
//				if (e.isEnemy(Sn3)&& e.isEnemy(Sn1) && e.isEnemy(BSn2) && e.isThere(OSn2) && e.isThere(BSn3)) retval+=200;
//				if (e.isEnemy(Sn3)&& e.isEnemy(Sn1) && e.isEnemy(BSn2) && e.isThere(OSn2) && e.isThere(BSn3) && e.isEnemy(S0)) retval-=300;
//				if (e.isEnemy(Sn3)&& (e.isEnemy(Sn1)||e.ecapped(Sn1)) && e.ecapped(BSn2) &&(e.isThere(OSn2) && e.isThere(BSn3) && e.isThere(Sn2))) retval+=100;
//				
//				if (e.isEnemy(OSn1)&& e.isEnemy(Sn1) && e.isEnemy(BS0) &&(e.isThere(OSn2) || e.isThere(BSn3))) retval+=300;
//				if (e.isEnemy(OSn1)&& e.isEnemy(Sn1) && e.isEnemy(BS0) &&(e.isThere(OSn2) || e.isThere(BSn3)) && e.isEnemy(BSn1)) retval-=150;
//				if (e.isEnemy(OSn1)&& e.isEnemy(Sn1) && e.isEnemy(BS0) &&(e.isThere(OSn2) && e.isThere(BSn3))) retval+=200;
//				if (e.isEnemy(OSn1)&& (e.isEnemy(Sn1)||e.ecapped(Sn1)) && e.isEnemy(BS0) && e.ecapped(BSn2) && e.isThere(OSn2) && e.isThere(BSn3) && e.isThere(BSn1)) retval+=200;
//				
//				if(e.isEnemy(Sn1)) {
//					ArrayList<Tuple> estring = e.cB.checkForStrings(Sn1.a,Sn1.b, e.enemycolour.getSStrings(e.cB));
//					if (e.cB.getNeedList(estring, e.kscolour,true).size() < 2 ) retval+=150;
//					else retval-=250
//							;}
//				else if(e.ecapped(Sn1))retval+=400; 
//				
//				
//				if (e.isEnemy(S1) && e.isEnemy(S6) && e.isEnemy(S0) && !e.isThere(S7))retval -= 350;
//				if (e.isEnemy(S1) && e.isEnemy(S0) && e.isThere(S6) && e.isThere(S7)) retval +=100;
//				if (e.isThere(Sn1) && e.isEnemy(S1) && e.isEnemy(S0)) retval -=100;
//				if (e.isThere(S2) && e.isThere(S0) && e.isEnemy(Sn1) && e.isThere(BSn1)) retval +=100;
//				// other side ----------------------------------------
//
//				if (e.isThere(S5) && e.isThere(S7) &&  e.isThere(BS8) && e.isThere(S9)) {
//					retval += 150;
//					if(e.isThere(OS8) || e.isThere(S10))retval += 150;}
//				
//				if ((e.isEnemy(OS8) || e.isEnemy(S10)) &&  e.isThere(BS8) && e.isThere(S9) && e.isEnemy(BS9)) retval -=200;
//				if (e.isEnemy(S6) && e.isThere(S5))retval += 200;
//				if (e.isThere(S6) && e.isThere(S7))retval += 150;
//				if (e.isThere(S6) && e.isThere(S2) && e.isEnemy(S4))retval -= 400;
//				if (e.isThere(S6) && e.isEnemy(S1) && e.isEnemy(S7))retval -= 200;
//				if (e.isThere(S6) && e.isThere(S7) && e.isEnemy(S8))retval -= 300;
//				if (e.isThere(S7) && e.isEnemy(S8) && e.isThere(BS8))retval += 100;
//				
//				
//				if (e.isThere(S9) && e.isEnemy(S10))retval -= 200;
//				if (e.isThere(BS8) && e.isEnemy(OS8))retval -= 200;
//
//				
//				if (e.isThere(S7) && e.isEnemy(S8))retval -= 200;
//				if (e.isThere(S7) && e.isEnemy(BS8) && e.isEnemy(S8))retval -= 100;
//				if (e.isEnemy(S7) && e.isThere(S6))retval -= 50;
//				if(e.isThere(S9) && (e.isThere(S10) || e.isThere(OS8)) && e.isThere(BS8)&& e.isThere(S7)&& e.isThere(S5)&& e.isThere(S8)) retval += 300;
//				if((e.isThere(S10) || e.isThere(OS8)) && ((e.capped(BS8)&& e.isThere(S9)) || (e.capped(S9)  && e.isThere(BS8))) && e.isThere(S7)&& e.isThere(S5)&& e.isEnemy(S8)) retval -= 600;
//				
//				if (e.isEnemy(S10)&& e.isEnemy(S8) && e.isEnemy(BS9) &&(e.isThere(OS9) || e.isThere(BS10))) retval+=300;
//				if (e.isEnemy(S10)&& e.isEnemy(S8) && e.isEnemy(BS9) && e.isThere(OS9) && e.isThere(BS10)) retval+=200;
//				if (e.isEnemy(S10)&& e.isEnemy(S8) && e.isEnemy(BS9) && e.isThere(OS9) && e.isThere(BS10) && e.isEnemy(S7)) retval-=300;
//				if (e.isEnemy(S10)&& (e.isEnemy(S8)||e.ecapped(S8)) && e.ecapped(BS9) && e.isThere(OS9) && e.isThere(BS10) && e.isThere(S9)) retval+=100;
//				
//				if (e.isEnemy(OS8)&& e.isEnemy(S8) && e.isEnemy(BS7) &&(e.isThere(OS9) || e.isThere(BS10))) retval+=300;
//				if (e.isEnemy(OS8)&& e.isEnemy(S8) && e.isEnemy(BS7) &&(e.isThere(OS9) || e.isThere(BS10)) && e.isEnemy(BS8)) retval-=150;
//				if (e.isEnemy(OS8)&& e.isEnemy(S8) && e.isEnemy(BS7) && e.isThere(OS9) && e.isThere(BS10)) retval+=200;
//				if (e.isEnemy(OS8)&& (e.isEnemy(S8)||e.ecapped(S8)) && e.isEnemy(BS7) && e.ecapped(BS9) && e.isThere(OS9) && e.isThere(BS10) && e.isThere(BS8)) retval+=200;
//				
//				
//				if(e.isEnemy(S8)) {
//					ArrayList<Tuple> estring = e.cB.checkForStrings(S8.a,S8.b, e.enemycolour.getSStrings(e.cB));
//					if (e.cB.getNeedList(estring, e.kscolour,true).size() < 2) retval+=150;
//					else retval-=150;}
//				else if(e.ecapped(S8))retval+=400; 
//				
//				if (e.isEnemy(S1) && e.isEnemy(S6) && e.isEnemy(S7) && !e.isThere(S0))retval -= 350;
//				if (e.isEnemy(S6) && e.isEnemy(S7) && e.isThere(S1) && e.isThere(S0)) retval +=100;
//				if (e.isThere(S8) && e.isEnemy(S6) && e.isEnemy(S7)) retval -=100;
//				if (e.isThere(S5) && e.isThere(S7) && e.isEnemy(S8) && e.isThere(BS8)) retval +=100;
//				//no side ----------------------------------------
//				
//				if (e.isThere(S1) || e.isThere(S6))retval += 500;
//				if (e.isEnemy(S1) || e.isEnemy(S6))retval -= 500;
//				if ((e.isThere(S1) && e.isThere(S5) )||(e.isThere(S2) && e.isThere(S6))) {
//					if (e.isThere(S7) && e.isThere(S0))retval += 100;}
//				
//				if (e.isEnemy(S1) && e.isEnemy(S6))retval -= 200;
//				
//			}
//
//		}
//
//		return retval;
//	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
