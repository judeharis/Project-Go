package Go.SlickGo;

import java.util.ArrayList;

public class Evaluator {

	public static int evaluateBoard(Board cB, Board oB,Minimaxer minimaxer) {
		int retval= 0;
		int mult= 0;
		Stone kscolour = Minimaxer.keystonecolour;
		Stone colour = oB.turn;
		ArrayList<Tuple> obAtariList = kscolour == Stone.WHITE ? oB.wCapStrings:oB.bCapStrings;
		ArrayList<Tuple> cbAtariList = kscolour == Stone.WHITE ? cB.wCapStrings:cB.bCapStrings;
		

		Tuple cbCounts = Evaluator.countStone(colour,cB.stones);
		ArrayList<Tuple> cEyes = new ArrayList<Tuple>();
		ArrayList<Tuple> oEyes = new ArrayList<Tuple>();
		int cSafeStrings = cB.getSafeStringsCount(kscolour,cEyes);
		int oSafeStrings = oB.getSafeStringsCount(kscolour,oEyes);
		int eyeDifference = (cEyes.size() - oEyes.size());
		

		
		
		if (colour == kscolour) mult=1;
		else mult=-1;
		
		retval+= (eyeDifference) * (-200)*mult;
		retval+= (cSafeStrings-oSafeStrings) * 400*mult;
		
		//Check if keyStone is in Safety
//		for (Tuple t : cB.keystones) {
//			StoneStringResponse cbStringRes = cB.checkForStrings(t.a,t.b,kscolour.getSStrings(cB)); 
//			StoneStringResponse obStringRes = oB.checkForStrings(t.a,t.b,kscolour.getSStrings(oB)); 
//			ArrayList<Tuple> eyelist = new ArrayList<Tuple>();
//			if (cbStringRes.state) {
//				retval+= cB.checkStringSafety(cbStringRes.list, kscolour,eyelist)* 100000*mult;
//				retval+= (oB.getNeedList(obStringRes.list,kscolour.getEnemyColour()).size() - cB.getNeedList(cbStringRes.list,kscolour.getEnemyColour()).size())*30*mult;
//			}else {
//				retval+= 10000*mult;
//			}
//		}
		
		
		
		
		
		// if current board stone - original board stone count
		retval+= (cbCounts.a - minimaxer.obCounts.a) * 100*mult;
		
		// if current board stone - original board stone count
		retval+= (minimaxer.obCounts.b - cbCounts.b) * 80*mult;
		
		// if current board stone - current board enemy stone count
		retval+= (cbCounts.a - cbCounts.b) * 50*mult;
		

		// if more atari's than when started
		retval += (obAtariList.size() - cbAtariList.size()) * (200*mult);
		


		
		return retval;
	}
	
	public static Tuple countStone(Stone colour , Stone[][] stones) {

		int stoneCount = 0;
		int enemyStoneCount =0;
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if (stones[i][j].getStoneColour() == colour) stoneCount++;
            	else if (stones[i][j].getStoneColour() == colour.getEnemyColour()) enemyStoneCount++;
                
            }
        }
		Tuple counts = new Tuple(stoneCount,enemyStoneCount);
        return counts;
	} 
	

}
