package Go.SlickGo;

import java.util.ArrayList;
import java.util.Collections;
import static Go.SlickGo.UDLR.*;

public class Evaluator {
	Board cB;
	Board oB;
	Tuple oBCounts;
	String vB = "";
	String hB = "";
	Stone kscolour = Minimaxer.keystonecolour.getSC();
	Stone enemycolour = kscolour.getEC();

	public Evaluator(Board cB, Board oB, Tuple oBCounts) {
		this.cB = cB;
		this.oB = oB;
		this.oBCounts = oBCounts;
	}

	public Evaluator(Board cB) {
		this.cB = cB;
		this.oB = null;
		this.oBCounts = null;
	}

	// Useless
	public int evaluateBoard() {
		int retval = 0;
		int mult = 0;
		Stone kscolour = Minimaxer.keystonecolour;
		Stone colour = oB.turn;
		ArrayList<Tuple> obAtariList = kscolour == Stone.WHITE ? oB.wCapStrings : oB.bCapStrings;
		ArrayList<Tuple> cbAtariList = kscolour == Stone.WHITE ? cB.wCapStrings : cB.bCapStrings;

		Tuple cbCounts = Evaluator.countStone(colour, cB.stones);
		ArrayList<Tuple> cEyes = new ArrayList<Tuple>();
		ArrayList<Tuple> oEyes = new ArrayList<Tuple>();
		int cSafeStrings = cB.getSafeStringsCount(kscolour, cEyes);
		int oSafeStrings = oB.getSafeStringsCount(kscolour, oEyes);
		int eyeDifference = (cEyes.size() - oEyes.size());

		if (colour == kscolour)
			mult = 1;
		else
			mult = -1;

		retval += (eyeDifference) * (-200) * mult;
		retval += (cSafeStrings - oSafeStrings) * 400 * mult;

		// Check if keyStone is in Safety

		// if current board stone - original board stone count
		retval += (cbCounts.a - oBCounts.a) * 100 * mult;

		// if current board stone - original board stone count
		retval += (oBCounts.b - cbCounts.b) * 80 * mult;

		// if current board stone - current board enemy stone count
		retval += (cbCounts.a - cbCounts.b) * 50 * mult;

		// if more atari's than when started
		retval += (obAtariList.size() - cbAtariList.size()) * (200 * mult);

		return retval;
	}

	public int evaluateCurrentBoard() {
		int retval = 0;

		ArrayList<Tuple> cbAtariList = kscolour.getCapList(cB);
		int numberOfStrings = kscolour.getSStrings(cB).size();
		Tuple cbCounts = countStone(kscolour, cB.stones);
		if (numberOfStrings != 0 && cbAtariList.size() != 0)
			retval += cbAtariList.size() * (cbAtariList.size() / numberOfStrings) * (-300);

//		ArrayList<Tuple> cbEAtariList =enemycolour.getCapList(cB);
//		int enumberOfStrings = enemycolour.getSStrings(cB).size();
//		if (enumberOfStrings != 0 && cbEAtariList.size() != 0)
//			retval += cbEAtariList.size() * (cbEAtariList.size() / enumberOfStrings) * (+300);
		
		
		
//		if (cB.ko != null && cB.turn == kscolour)
//			retval += -100;
//		else if (cB.ko != null)
//			retval += 100;

		retval -= invalidInNeedList(enemycolour.getSStrings(cB),kscolour);
		retval += invalidInNeedList(kscolour.getSStrings(cB),enemycolour);

		
		if (cB.turn == kscolour)
			retval += 100;


		retval += cbCounts.a * 5;
		retval += cbCounts.b * -5;
		
		for (Tuple t : cB.keystones) {
			ArrayList<Tuple> cBsstring = cB.checkForStrings(t.a, t.b, kscolour.getSStrings(cB));
			if (!cBsstring.isEmpty()) {

				ArrayList<Tuple> e = new ArrayList<Tuple>();
				if (cB.checkStringSafety(cBsstring, kscolour, e) == 1)
					retval += 1000000;
				retval += e.size() * 500;
				ArrayList<Tuple> cBneedList = cB.getNeedList(cBsstring, kscolour.getEC());
				retval += cBneedList.size() * 20;
				for (Tuple k : cBneedList) {
					if (cB.stones[k.a][k.b] == Stone.INVALID)
						retval += 100000;
				}
				
				retval += sideOneGap(cBsstring);
				retval += sideTwoGap(cBsstring);
				retval += sideThreeGap(cBsstring);
//				retval += sideFourGap(cBsstring);
				retval += sideFiveGap(cBsstring);
				retval += sixDiesEightLives(cBsstring);

			}
		}

//		for (ArrayList<Tuple>  slist : kscolour.getSStrings(cB)) {
//			if(slist.size()> 1)retval+=500;
//		}

		getStringMap(kscolour, cB.stones, true);
//		if (hB.contains("-xxxxxxx-"))retval +=100000;

		return retval;

	}

	public int sixDiesEightLives(ArrayList<Tuple> sstring) {
		int retval = 0;
		ArrayList<Tuple> bar6 = checkStringForBar(sstring, 6);
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

	public int sideOneGap(ArrayList<Tuple> sstring) {
		int retval = 0;
		ArrayList<Tuple> bar3 = checkStringForBar(sstring, 3);
		if (!bar3.isEmpty()) {
			UDLR side = distFromSide(bar3, 1);
			if (!side.isEmpty()) {
				Tuple S1 = bar3.get(0).side(side);
				Tuple S3 = bar3.get((bar3.size() - 1)).side(side);
				Tuple S2 = S1.side(side.diag(false));
				if (isThere(S1) && isThere(S3)) {
					retval -= 200;
					if (isEnemy(S2))retval -= 1000;}}}

		return retval;
	}
	
	public int sideTwoGap(ArrayList<Tuple> sstring) {
		int retval = 0;
		ArrayList<Tuple> bar4 = checkStringForBar(sstring, 4);
		if (!bar4.isEmpty()) {
			UDLR side = distFromSide(bar4, 1);
			if (!side.isEmpty()) {
				Tuple S1 = bar4.get(0).side(side);
				Tuple S4 = bar4.get((bar4.size() - 1)).side(side);
				Tuple S2 = S1.side(side.diag(false));
				Tuple S3 = S2.side(side.diag(true));

				if (isThere(S1) && isThere(S4)) {
					retval -= 200;
					if (isThere(S2) && isThere(S3))retval -= 200;
					else if (isThere(S2) || isThere(S3))retval -= 100;}}}

		return retval;
	}
	
	public int sideThreeGap(ArrayList<Tuple> sstring) {
		int retval=0;
		ArrayList<Tuple> bar5 = checkStringForBar(sstring, 5);
		if (!bar5.isEmpty()) {
			UDLR side = distFromSide(bar5, 1);
			if (!side.isEmpty()) {
				Tuple S1 = bar5.get(0).side(side);
				Tuple S5= bar5.get((bar5.size() - 1)).side(side);
				Tuple S2 = S1.side(side.diag(false));
				Tuple S3 = S2.side(side.diag(false));


				if (isThere(S1) && isThere(S5)) {
					retval -= 500;
					if (isThere(S3))retval += 10000;
					if (isEnemy(S3))retval -= 10000;
				}}}
		return retval;
	}
	
/*	public int sideFourGap(ArrayList<Tuple> sstring) {
//		int retval=0;
//		ArrayList<Tuple> bar6 = checkStringForBar(sstring, 6);
//		if (!bar6.isEmpty()) {
//			UDLR side = distFromSide(bar6.get(1), 1);
//			if (!side.isEmpty()) {
//				Tuple S1 = bar6.get(0).side(side);
//				Tuple S6= bar6.get((bar6.size() - 1)).side(side);
//				Tuple S2 = S1.side(side.diag(false));
//				Tuple S5 = S6.side(side.diag(true));
//				Tuple S3 = S2.side(side.diag(false));
//				Tuple S4 = S5.side(side.diag(true));
//
//				if (isThere(S1) && isThere(S6)) {
//					retval += 500;
//					if (isThere(S2) && isThere(S5))retval -= 2000;
//					else if (isThere(S2) || isThere(S5))retval -= 1000;
//					if (isThere(S3) && isThere(S4))retval += 1000;
//					else if (isThere(S3) || isThere(S4))retval += 1000;
//					
//					if (isEnemy(S3) || isEnemy(S4))retval -= 1000;
//					if (isEnemy(S3) && isEnemy(S4))retval -= 2000;
//				}}}
//		return retval;
	} */
	
	public int sideFiveGap(ArrayList<Tuple> sstring) {
		int retval=0;
		ArrayList<Tuple> bar7 = checkStringForBar(sstring, 7);
		if (!bar7.isEmpty()) {
			UDLR side = distFromSide(bar7, 1);
			if (!side.isEmpty()) {
				Tuple S1 = bar7.get(0).side(side);
				Tuple S2 = S1.side(side.diag(false));
				Tuple S3 = S2.side(side.diag(false));
				Tuple S4 = S3.side(side.diag(false));
				Tuple S7= bar7.get((bar7.size() - 1)).side(side);
				Tuple S6 = S7.side(side.diag(true));
				Tuple S5 = S6.side(side.diag(true));


				if (isThere(S1) && isThere(S7)) {
					retval += 500;
					if (isThere(S2))retval += 500;
					if (isThere(S3))retval += 500;
					if (isThere(S4))retval += 500;
					if (isThere(S5))retval += 500;
					if (isThere(S6))retval += 500;

				}}}
		return retval;
	}
	

	public ArrayList<Tuple> checkStringForBar(ArrayList<Tuple> sstring, int barlength) {

		Collections.sort(sstring);
		ArrayList<Tuple> barString = new ArrayList<Tuple>();
		boolean found = false;
		for (Tuple t : sstring) {
			if (cB.findAdjacentColour(t, false, true) != kscolour.getSC()) {
				ArrayList<Tuple> vert = getLine(sstring, t.down(), true);
				vert.add(0, t);

				if (vert.size() == barlength && !found) {
					barString = vert;
					found = true;
				} else if (vert.size() == barlength) {
					barString = new ArrayList<Tuple>();
					break;
				}
			}
			if (cB.findAdjacentColour(t, false, false) != kscolour.getSC()) {
				ArrayList<Tuple> hor = getLine(sstring, t.right(), false);
				hor.add(0, t);

				if (hor.size() == barlength && !found) {
					barString = hor;
					found = true;
				} else if (hor.size() == barlength) {
					barString = new ArrayList<Tuple>();
					break;
				}
			}
		}

		return barString;

	}

	public int barEval(ArrayList<Tuple> barString, ArrayList<Tuple> sstring, int size) {
		UDLR side = distFromSide(barString, 1);
		int retval = 0;
		if (!side.isEmpty()) {
			
			
			
			
			Tuple S1 = barString.get(0).side(side);
			Tuple D1 = barString.get(0).dubSide(side, side.diag(true));
			Tuple S2 = barString.get((barString.size() - 1)).side(side);
			Tuple D2 = barString.get((barString.size() - 1)).dubSide(side, side.diag(false));

			Tuple DH1 = D1.side(side.diag(true));
			Tuple SS1 = S1.side(side.diag(false));
			Tuple SSS1 = SS1.side(side.diag(false));
			Tuple DHV1 = DH1.side(side.opp());

			Tuple DH2 = D2.side(side.diag(false));
			Tuple SS2 = S2.side(side.diag(true));
			Tuple SSS2 = SS2.side(side.diag(true));
			Tuple DHV2 = DH2.side(side.opp());

			ArrayList<Tuple> sideString = Tuple.getSideArrayList(barString, side);

			boolean l = false;
			boolean r = false;
			int valueOfSide = 300;
			while (!(l && r) && sideString.size() > 1) {
				Tuple s1 = sideString.remove(0);
				Tuple s2 = sideString.remove(sideString.size() - 1);
				if (isThere(s1)) {
					retval += valueOfSide;
					l = true;
				}
				if (isThere(s2)) {
					retval += valueOfSide;
					r = true;
				}
				valueOfSide -= 50;
			}
			if (!(l || r) && sideString.size() == 1) {
				Tuple s1 = sideString.remove(0);
				if (isThere(s1))
					retval += valueOfSide;
			}

/*				
//		if(isThere(S1) && isThere(D1) && isThere(DHV1)) retval+=700;
//			if(isThere(S1) && isThere(D1) && isThere(DH1)) retval+=600;		
//			if(isThere(D1) && isThere(DH1) && isThere(SS1)) retval+=600;
//			if (isThere(DHV1) && isThere(SS1))retval+=1000;
//		    
//			if(isThere(S1) && isThere(D1)) retval+=1000;
//			if(isThere(S1) && isThere(SS2)) retval+=900;
//			if(isThere(D1) && isThere(DH1)) retval+=800;
//		    
//
//			
//			if (isThere(SS1))retval+=100;
//			if (isThere(DH1))retval+=50;
//
//
//			if(isThere(S2) && isThere(D2) && isThere(DHV2)) retval+=700;
//			if(isThere(S2) && isThere(D2) && isThere(DH2)) retval+=600;	
//			if(isThere(D2) && isThere(DH2) && isThere(SS2)) retval+=600;
//			if (isThere(DHV2) && isThere(SS2)) retval+=1000;
//			
//			if(isThere(S2) && isThere(D2)) retval+=1000;*/

			if (isThere(D1)) {
				if (isThere(DH1))
					retval += 400;
				else
					retval -= 200;
				if (isThere(DHV1))
					retval += 100;
			}

			if (isThere(D2)) {
				if (isThere(DH2))
					retval += 400;
				else
					retval -= 200;
				if (isThere(DHV2))
					retval += 100;
			}

			if (isThere(S1)) {
				retval += 400;
				if (isThere(D1))
					retval += 400;
				else if (isEnemy(D1))
					retval -= 200;
			}

			if (isThere(S2)) {
				retval += 400;
				if (isThere(D2))
					retval += 400;
				else if (isEnemy(D2))
					retval -= 200;
			}

			if (isThere(S1) && isThere(SS2))
				retval += 900;
			if (isThere(S2) && isThere(SS1))
				retval += 900;

			if (isThere(S1) && isThere(SSS1))
				retval += 900;
			if (isThere(S2) && isThere(SSS2))
				retval += 900;

			if (isThere(SS2))
				retval += 100;
			if (isThere(DH2))
				retval += 50;

			if (isEnemy(S1))
				retval -= 300;
			if (isEnemy(D1))
				retval -= 200;

			if (isEnemy(S2))
				retval -= 300;
			if (isEnemy(D2))
				retval -= 100;


			if (size == 6)
				retval += -500;
			else if (size == 8)
				retval += 500;

		}

		return retval;
	}

	
	public int barEvalp(ArrayList<Tuple> barString, ArrayList<Tuple> sstring, int size) {
		UDLR side = distFromSide(barString, 1);
		int retval = 0;
		if (!side.isEmpty()) {
			Tuple BS0 =  barString.get(0).side(side.diag(true));
			Tuple BS7 =  barString.get((barString.size() - 1)).side(side.diag(false));
			Tuple nBS1 =  BS0.side(side.diag(true));
			Tuple nBS2 =  nBS1.side(side.diag(true));
			Tuple nBS3 =  nBS2.side(side.diag(true));
			Tuple BS8 =  BS7.side(side.diag(false));
			Tuple BS9 =  BS8.side(side.diag(false));
			Tuple BS10 =  BS9.side(side.diag(false));
			
			Tuple S1 = barString.get(0).side(side);
			Tuple S0 = S1.side(side.diag(true));
			Tuple nS1 = S0.side(side.diag(true));
			Tuple nS2 = nS1.side(side.diag(true));
			Tuple nS3 = nS2.side(side.diag(true));
			Tuple S2 = S1.side(side.diag(false));
			Tuple S3 = S2.side(side.diag(false));
			Tuple S6 = barString.get((barString.size() - 1)).side(side);
			Tuple S7 = S6.side(side.diag(false));
			Tuple S8 = S7.side(side.diag(false));
			Tuple S9 = S8.side(side.diag(false));
			Tuple S10 = S9.side(side.diag(false));
			Tuple S5 = S6.side(side.diag(true));
			Tuple S4 = S5.side(side.diag(true));
			
			Tuple nOS1 = nBS1.side(side.opp());
			Tuple nOS2 = nOS1.side(side.diag(true));
			Tuple OS8 = BS8.side(side.opp());
			Tuple OS9 = OS8.side(side.diag(false));
			
			if (isThere(S1)&& isThere(S6) && isEnemy(BS0) && isEnemy(BS7)) {
				retval += 500;
				if (isThere(S2) && isThere(S5))retval -= 2000;
				else if (isThere(S2) || isThere(S5))retval -= 1000;
				if (isThere(S3) && isThere(S4))retval += 1000;
				else if (isThere(S3) || isThere(S4))retval += 1000;
				
				if (isEnemy(S3) || isEnemy(S4))retval -= 1000;
				if (isEnemy(S3) && isEnemy(S4))retval -= 2000;
			}else if(isEnemy(BS0) && isEnemy(BS7)) {
				
				

				
				if (isThere(S1) || isThere(S6))retval += 500;
				if (isEnemy(S1) || isEnemy(S6))retval -= 500;
				
				if ((isThere(S1) && isThere(S5) )||(isThere(S2) && isThere(S6))) {
					if (isThere(S7) && isThere(S0))retval += 100;}
				
				
				if (isThere(S5) && isThere(S7) &&  isThere(BS8) && isThere(S9)) {
					retval += 150;
					if(isThere(OS8) || isThere(S10))retval += 150;}
				
				if (isThere(S2) && isThere(S0) &&  isThere(nBS1) && isThere(nS2)) {
					retval += 150;
					if(isThere(nOS1) || isThere(nS3))retval += 150;}
				
				
				if ((isEnemy(OS8) || isEnemy(S10)) &&  isThere(BS8) && isThere(S9) && isEnemy(BS9)) retval -=200;
				if ((isEnemy(nOS1) || isEnemy(nS3)) &&  isThere(nBS1) && isThere(nS2) && isEnemy(nBS2)) retval -=200;
				
				if (isEnemy(S1) && isThere(S2))retval += 200;
				if (isEnemy(S6) && isThere(S5))retval += 200;
				

				if (isThere(S1) && isThere(S0))retval += 150;
				if (isThere(S6) && isThere(S7))retval += 150;
				
				
				
				if (isThere(S1) && isThere(S5) && isEnemy(S3))retval -= 400;
				if (isThere(S6) && isThere(S2) && isEnemy(S4))retval -= 400;
				
				if (isThere(S1) && isEnemy(S6) && isEnemy(S0))retval -= 200;
				if (isThere(S6) && isEnemy(S1) && isEnemy(S7))retval -= 200;
				
				if (isThere(S1) && isThere(S0) &&  isEnemy(nS1))retval -= 200;
				if (isThere(S6) && isThere(S7) &&  isEnemy(S8))retval -= 200;
				
				if (isThere(S1) && isThere(S0) &&  isEnemy(nS1))retval -= 100;
				if (isThere(S0) && isEnemy(nS1) && isThere(nBS1))retval += 100;
				
				if (isThere(S6) && isThere(S7) &&  isEnemy(S8))retval -= 100;
				if (isThere(S7) && isEnemy(S8) && isThere(BS8))retval += 100;
				
				
				if (isThere(nS2) && isEnemy(nS3))retval -= 200;
				if (isThere(nBS1) && isEnemy(nOS1))retval -= 200;
				if (isThere(S9) && isEnemy(S10))retval -= 200;
				if (isThere(BS8) && isEnemy(OS8))retval -= 200;
				
				if(isEnemy(nS1)) {
					ArrayList<Tuple> estring = cB.checkForStrings(nS1.a,nS1.b, enemycolour.getSStrings(cB));
					if (cB.getNeedList(estring, kscolour).size() < 2) retval+=150;
					else retval-=150;}
				else if(ecapped(nS1))retval+=400; 
				
				
				if(isEnemy(S8)) {
					ArrayList<Tuple> estring = cB.checkForStrings(S8.a,S8.b, enemycolour.getSStrings(cB));
					if (cB.getNeedList(estring, kscolour).size() < 2) retval+=150;
					else retval-=150;}
				else if(ecapped(S8))retval+=400; 
				
				
				
				if (isThere(S7) && isEnemy(S8))retval -= 200;
				if (isThere(S7) && isEnemy(BS8) && isEnemy(S8))retval -= 100;
				
				if (isThere(S0) && isEnemy(nS1))retval -= 200;
				if (isThere(S0) && isEnemy(nBS1) && isEnemy(nS1))retval -= 100;

				if (isEnemy(S0) && isThere(S1))retval -= 50;
				if (isEnemy(S7) && isThere(S6))retval -= 50;
				
				
				if(isThere(S9) && (isThere(S10) || isThere(OS8)) && isThere(BS8)&& isThere(S7)&& isThere(S5)&& isThere(S8)) retval += 300;
				if((isThere(S10) || isThere(OS8)) && ((capped(BS8)&& isThere(S9)) || (capped(S9)  && isThere(BS8))) && isThere(S7)&& isThere(S5)&& isEnemy(S8)) retval -= 300;
				
				if(isThere(nS2) && (isThere(nS3) || isThere(nOS1)) && isThere(nBS1)&& isThere(S0)&& isThere(S2)&& isThere(nS1)) retval += 300;
				if((isThere(nS3) || isThere(nOS1)) && ((capped(nBS1) && isThere(nS2)) || (capped(nS2) && isThere(nBS1))) && isThere(S0)&& isThere(S2)&& isEnemy(nS1)) retval -= 300;
				
				//ending 
				//side 1 
				if (isEnemy(nS3)&& isEnemy(nS1) && isEnemy(nBS2) &&(isThere(nOS2) || isThere(nBS3))) retval+=300;
				if (isEnemy(nS3)&& isEnemy(nS1) && isEnemy(nBS2) && isThere(nOS2) && isThere(nBS3)) retval+=200;
				if (isEnemy(nS3)&& isEnemy(nS1) && isEnemy(nBS2) && isThere(nOS2) && isThere(nBS3) && isEnemy(S0)) retval-=300;
				if (isEnemy(nS3)&& (isEnemy(nS1)||ecapped(nS1)) && ecapped(nBS2) &&(isThere(nOS2) && isThere(nBS3) && isThere(nS2))) retval+=100;
				
				if (isEnemy(nOS1)&& isEnemy(nS1) && isEnemy(BS0) &&(isThere(nOS2) || isThere(nBS3))) retval+=300;
				if (isEnemy(nOS1)&& isEnemy(nS1) && isEnemy(BS0) &&(isThere(nOS2) || isThere(nBS3)) && isEnemy(nBS1)) retval-=150;
				if (isEnemy(nOS1)&& isEnemy(nS1) && isEnemy(BS0) &&(isThere(nOS2) && isThere(nBS3))) retval+=200;
				if (isEnemy(nOS1)&& (isEnemy(nS1)||ecapped(nS1)) && isEnemy(BS0) && ecapped(nBS2) && isThere(nOS2) && isThere(nBS3) && isThere(nBS1)) retval+=200;
				
				//side 2
				if (isEnemy(S10)&& isEnemy(S8) && isEnemy(BS9) &&(isThere(OS9) || isThere(BS10))) retval+=300;
				if (isEnemy(S10)&& isEnemy(S8) && isEnemy(BS9) && isThere(OS9) && isThere(BS10)) retval+=200;
				if (isEnemy(S10)&& isEnemy(S8) && isEnemy(BS9) && isThere(OS9) && isThere(BS10) && isEnemy(S7)) retval-=300;
				if (isEnemy(S10)&& (isEnemy(S8)||ecapped(S8)) && ecapped(BS9) && isThere(OS9) && isThere(BS10) && isThere(S9)) retval+=100;
				
				if (isEnemy(OS8)&& isEnemy(S8) && isEnemy(BS7) &&(isThere(OS9) || isThere(BS10))) retval+=300;
				if (isEnemy(OS8)&& isEnemy(S8) && isEnemy(BS7) &&(isThere(OS9) || isThere(BS10)) && isEnemy(BS8)) retval-=150;
				if (isEnemy(OS8)&& isEnemy(S8) && isEnemy(BS7) && isThere(OS9) && isThere(BS10)) retval+=200;
				if (isEnemy(OS8)&& (isEnemy(S8)||ecapped(S8)) && isEnemy(BS7) && ecapped(BS9) && isThere(OS9) && isThere(BS10) && isThere(BS8)) retval+=200;

				
			}

		}

		return retval;
	}

	
	public int barEval6(ArrayList<Tuple> barString, ArrayList<Tuple> sstring, int size) {
		UDLR side = distFromSide(barString, 1);
		int retval = 0;
		if (!side.isEmpty()) {
			Tuple BS0 =  barString.get(0).side(side.diag(true));
			Tuple BS7 =  barString.get((barString.size() - 1)).side(side.diag(false));
			Tuple nBS1 =  BS0.side(side.diag(true));
			Tuple nBS2 =  nBS1.side(side.diag(true));
			Tuple nBS3 =  nBS2.side(side.diag(true));
			Tuple BS8 =  BS7.side(side.diag(false));
			Tuple BS9 =  BS8.side(side.diag(false));
			Tuple BS10 =  BS9.side(side.diag(false));
			
			Tuple S1 = barString.get(0).side(side);
			Tuple S0 = S1.side(side.diag(true));
			Tuple nS1 = S0.side(side.diag(true));
			Tuple nS2 = nS1.side(side.diag(true));
			Tuple nS3 = nS2.side(side.diag(true));
			Tuple S2 = S1.side(side.diag(false));
			Tuple S3 = S2.side(side.diag(false));
			Tuple S6 = barString.get((barString.size() - 1)).side(side);
			Tuple S7 = S6.side(side.diag(false));
			Tuple S8 = S7.side(side.diag(false));
			Tuple S9 = S8.side(side.diag(false));
			Tuple S10 = S9.side(side.diag(false));
			Tuple S5 = S6.side(side.diag(true));
			Tuple S4 = S5.side(side.diag(true));
			
			Tuple nOS1 = nBS1.side(side.opp());
			Tuple nOS2 = nOS1.side(side.diag(true));
			Tuple OS8 = BS8.side(side.opp());
			Tuple OS9 = OS8.side(side.diag(false));
			
			if (isThere(S1)&& isThere(S6) && isEnemy(BS0) && isEnemy(BS7)) {
				retval += 500;
				if (isThere(S2) && isThere(S5))retval -= 2000;
				else if (isThere(S2) || isThere(S5))retval -= 1000;
				if (isThere(S3) && isThere(S4))retval += 1000;
				else if (isThere(S3) || isThere(S4))retval += 1000;
				
				if (isEnemy(S3) || isEnemy(S4))retval -= 1000;
				if (isEnemy(S3) && isEnemy(S4))retval -= 2000;
			}else if(isEnemy(BS0) && isEnemy(BS7)) {
				
				
				
				if (isThere(S2) && isThere(S0) &&  isThere(nBS1) && isThere(nS2)) {
					retval += 150;
					if(isThere(nOS1) || isThere(nS3))retval += 150;}
				if ((isEnemy(nOS1) || isEnemy(nS3)) &&  isThere(nBS1) && isThere(nS2) && isEnemy(nBS2)) retval -=200;
				if (isEnemy(S1) && isThere(S2))retval += 200;
				if (isThere(S1) && isThere(S0))retval += 150;
				if (isThere(S1) && isThere(S5) && isEnemy(S3))retval -= 400;
				if (isThere(S1) && isEnemy(S6) && isEnemy(S0))retval -= 200;
				if (isThere(S1) && isThere(S0) && isEnemy(nS1))retval -= 300;
				if (isThere(S0) && isEnemy(nS1) && isThere(nBS1))retval += 100;
				
				if (isThere(nS2) && isEnemy(nS3))retval -= 200;
				if (isThere(nBS1) && isEnemy(nOS1))retval -= 200;
				if (isThere(nBS1) && isThere(nS2) && isEnemy(nBS2))retval -= 250;
				
				if (isThere(S0) && isEnemy(nS1))retval -= 200;
				if (isThere(S0) && isEnemy(nBS1) && isEnemy(nS1))retval -= 100;
				if (isEnemy(S0) && isThere(S1))retval -= 50;
				if(isThere(nS2) && (isThere(nS3) || isThere(nOS1)) && isThere(nBS1)&& isThere(S0)&& isThere(S2)&& isThere(nS1)) retval += 300;
				if((isThere(nS3) || isThere(nOS1)) && ((capped(nBS1) && isThere(nS2)) || (capped(nS2) && isThere(nBS1))) && isThere(S0)&& isThere(S2)&& isEnemy(nS1)) retval -= 600;
				
				
				if (isEnemy(nS3)&& isEnemy(nS1) && isEnemy(nBS2) &&(isThere(nOS2) || isThere(nBS3))) retval+=300;
				if (isEnemy(nS3)&& isEnemy(nS1) && isEnemy(nBS2) && isThere(nOS2) && isThere(nBS3)) retval+=200;
				if (isEnemy(nS3)&& isEnemy(nS1) && isEnemy(nBS2) && isThere(nOS2) && isThere(nBS3) && isEnemy(S0)) retval-=300;
				if (isEnemy(nS3)&& (isEnemy(nS1)||ecapped(nS1)) && ecapped(nBS2) &&(isThere(nOS2) && isThere(nBS3) && isThere(nS2))) retval+=100;
				
				if (isEnemy(nOS1)&& isEnemy(nS1) && isEnemy(BS0) &&(isThere(nOS2) || isThere(nBS3))) retval+=300;
				if (isEnemy(nOS1)&& isEnemy(nS1) && isEnemy(BS0) &&(isThere(nOS2) || isThere(nBS3)) && isEnemy(nBS1)) retval-=150;
				if (isEnemy(nOS1)&& isEnemy(nS1) && isEnemy(BS0) &&(isThere(nOS2) && isThere(nBS3))) retval+=200;
				if (isEnemy(nOS1)&& (isEnemy(nS1)||ecapped(nS1)) && isEnemy(BS0) && ecapped(nBS2) && isThere(nOS2) && isThere(nBS3) && isThere(nBS1)) retval+=200;
				
				if(isEnemy(nS1)) {
					ArrayList<Tuple> estring = cB.checkForStrings(nS1.a,nS1.b, enemycolour.getSStrings(cB));
					if (cB.getNeedList(estring, kscolour).size() < 2 ) retval+=150;
					else retval-=150;}
				else if(ecapped(nS1))retval+=400; 
				
				
				if (isEnemy(S1) && isEnemy(S6) && isEnemy(S0) && !isThere(S7))retval -= 350;
				// other side 

				if (isThere(S5) && isThere(S7) &&  isThere(BS8) && isThere(S9)) {
					retval += 150;
					if(isThere(OS8) || isThere(S10))retval += 150;}
				
				if ((isEnemy(OS8) || isEnemy(S10)) &&  isThere(BS8) && isThere(S9) && isEnemy(BS9)) retval -=200;
				if (isEnemy(S6) && isThere(S5))retval += 200;
				if (isThere(S6) && isThere(S7))retval += 150;
				if (isThere(S6) && isThere(S2) && isEnemy(S4))retval -= 400;
				if (isThere(S6) && isEnemy(S1) && isEnemy(S7))retval -= 200;
				if (isThere(S6) && isThere(S7) && isEnemy(S8))retval -= 300;
				if (isThere(S7) && isEnemy(S8) && isThere(BS8))retval += 100;
				
				
				if (isThere(S9) && isEnemy(S10))retval -= 200;
				if (isThere(BS8) && isEnemy(OS8))retval -= 200;
				if (isThere(BS8) && isThere(S9) && isEnemy(BS9))retval -= 250;
				
				if (isThere(S7) && isEnemy(S8))retval -= 200;
				if (isThere(S7) && isEnemy(BS8) && isEnemy(S8))retval -= 100;
				if (isEnemy(S7) && isThere(S6))retval -= 50;
				if(isThere(S9) && (isThere(S10) || isThere(OS8)) && isThere(BS8)&& isThere(S7)&& isThere(S5)&& isThere(S8)) retval += 300;
				if((isThere(S10) || isThere(OS8)) && ((capped(BS8)&& isThere(S9)) || (capped(S9)  && isThere(BS8))) && isThere(S7)&& isThere(S5)&& isEnemy(S8)) retval -= 600;
				
				if (isEnemy(S10)&& isEnemy(S8) && isEnemy(BS9) &&(isThere(OS9) || isThere(BS10))) retval+=300;
				if (isEnemy(S10)&& isEnemy(S8) && isEnemy(BS9) && isThere(OS9) && isThere(BS10)) retval+=200;
				if (isEnemy(S10)&& isEnemy(S8) && isEnemy(BS9) && isThere(OS9) && isThere(BS10) && isEnemy(S7)) retval-=300;
				if (isEnemy(S10)&& (isEnemy(S8)||ecapped(S8)) && ecapped(BS9) && isThere(OS9) && isThere(BS10) && isThere(S9)) retval+=100;
				
				if (isEnemy(OS8)&& isEnemy(S8) && isEnemy(BS7) &&(isThere(OS9) || isThere(BS10))) retval+=300;
				if (isEnemy(OS8)&& isEnemy(S8) && isEnemy(BS7) &&(isThere(OS9) || isThere(BS10)) && isEnemy(BS8)) retval-=150;
				if (isEnemy(OS8)&& isEnemy(S8) && isEnemy(BS7) && isThere(OS9) && isThere(BS10)) retval+=200;
				if (isEnemy(OS8)&& (isEnemy(S8)||ecapped(S8)) && isEnemy(BS7) && ecapped(BS9) && isThere(OS9) && isThere(BS10) && isThere(BS8)) retval+=200;
				
				
				if(isEnemy(S8)) {
					ArrayList<Tuple> estring = cB.checkForStrings(S8.a,S8.b, enemycolour.getSStrings(cB));
					if (cB.getNeedList(estring, kscolour).size() < 2) retval+=150;
					else retval-=150;}
				else if(ecapped(S8))retval+=400; 
				
				if (isEnemy(S1) && isEnemy(S6) && isEnemy(S7) && !isThere(S0))retval -= 350;
				//no side
				
				if (isThere(S1) || isThere(S6))retval += 500;
				if (isEnemy(S1) || isEnemy(S6))retval -= 500;
				if ((isThere(S1) && isThere(S5) )||(isThere(S2) && isThere(S6))) {
					if (isThere(S7) && isThere(S0))retval += 100;}
				
				if (isEnemy(S1) && isEnemy(S6))retval -= 200;
	
				
				
			}

		}

		return retval;
	}

	public static Tuple countStone(Stone colour, Stone[][] stones) {

		int stoneCount = 0;
		int enemyStoneCount = 0;
		for (int i = 0; i < stones.length; i++) {
			for (int j = 0; j < stones[i].length; j++) {
				if (stones[i][j].getSC() == colour)
					stoneCount++;
				else if (stones[i][j].getSC() == colour.getEC())
					enemyStoneCount++;

			}
		}
		Tuple counts = new Tuple(stoneCount, enemyStoneCount);
		return counts;
	}

	public UDLR distFromSide(ArrayList<Tuple> sstring, int d) {
		boolean vert = (barOrien(sstring)==VERT);
		boolean hor = (barOrien(sstring)==HOR);
		Tuple t= sstring.get(1);
		
		if(vert) {
			if (t.a - d == 0 && (t.b != 0 || t.b != 18))
				return LEFT;
			if (t.a + d == 18 && (t.b != 0 || t.b != 18))
				return RIGHT;
		}
		
		if(hor) {
			if (t.b - d == 0 && (t.a != 0 || t.a != 18))
				return UP;
			if (t.b + d == 18 && (t.a != 0 || t.a != 18))
				return DOWN;
		}
		return NODIR;
	}
	
	public UDLR barOrien(ArrayList<Tuple> sstring) {
		if(sstring.size()>1) {
			if(sstring.get(0).a == sstring.get(sstring.size()-1).a) return VERT;
			if(sstring.get(0).b == sstring.get(sstring.size()-1).b) return HOR;
		}
		return NODIR;
	}

	public ArrayList<Tuple> getLine(ArrayList<Tuple> sstring, Tuple t, boolean down) {
		ArrayList<Tuple> ret = new ArrayList<Tuple>();
		if (sstring.contains(t)) {
			ret.add(t);
			if (down)
				ret.addAll(getLine(sstring, t.down(), down));
			else
				ret.addAll(getLine(sstring, t.right(), down));
		}
		return ret;
	}

	public boolean isThere(Tuple t) {
		return cB.stones[t.a][t.b].getSC() == kscolour;
	}

	public boolean isEnemy(Tuple t) {
		return cB.stones[t.a][t.b].getSC() == kscolour.getEC();
	}
	
	public boolean capped(Tuple t) {
		return kscolour.getCappedList(cB).contains(t);
	}

	
	public boolean ecapped(Tuple t) {
		return enemycolour.getCappedList(cB).contains(t);
	}

	public void getStringMap(Stone colour, Stone[][] stones, boolean keepEnemy){
		for (int i = 0; i < stones.length; i++) {
			for (int j = 0; j < stones[i].length; j++) {
				if (stones[i][j].getSC() == colour)
					this.vB += "x";
				else if (stones[i][j].getSC() == colour.getEC() && keepEnemy)
					this.vB += "+";
				else
					this.vB += "-";

				if (stones[j][i].getSC() == colour)
					this.hB += "x";
				else if (stones[j][i].getSC() == colour.getEC() && keepEnemy)
					this.hB += "+";
				else
					this.hB += "-";

			}
			this.vB += "*";
			this.hB += "*";
		}
	}

	public void print(Object o) {
		System.out.println(o);
	}
	
	public int invalidInNeedList(ArrayList<ArrayList<Tuple>> colourStrings, Stone enemycolour) {
		int retval =0;
		for (ArrayList<Tuple> sstring :colourStrings ) {
			if (sstring.size()>1) {
				ArrayList<Tuple> needList = cB.getNeedList(sstring, enemycolour);
				for (Tuple k : needList) {
					if (cB.stones[k.a][k.b] == Stone.INVALID) {
						retval += 150;
						break;}
				}}
		}
		return retval;
	}

}
