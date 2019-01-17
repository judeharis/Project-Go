package Go.SlickGo;

import java.util.ArrayList;

public class Evaluator {
	public Board cB;
	Board oB;
	Tuple oBCounts;
	String vB = "";
	String hB = "";
	public Stone kscolour = MoveFinder.keystonecolour.getSC();
	public Stone enemycolour = kscolour.getEC();

	public Evaluator(Board cB, Board oB, Tuple oBCounts) {
		this.cB = cB;
		this.oB = oB;
		this.oBCounts = oBCounts;
	}
	
	public Evaluator(Board cB, Board oB ) {
		this.cB = cB;
		this.oB = oB;
	}

	public Evaluator(Board cB) {
		this.cB = cB;
		this.oB = null;
		this.oBCounts = null;
	}





	public  int evaluateCurrentBoard() {
		int retval = 0;

		Grouping grouping = new Grouping(cB,false,false,false,false);
		grouping.allocateGrouping();
		
		if (cB.ko != null)retval += 50;
		HeuristicsRunner hrunner= new HeuristicsRunner(cB , this);
		for (Tuple t : cB.keystones) {
			ArrayList<Tuple> keystring = cB.checkForStrings(t.a, t.b, kscolour.getSStrings(cB));
			Group keygroup = grouping.inGroup(t, kscolour);
			if (!keystring.isEmpty()) {
//				if (cB.checkStringSafetyv2(keystring, kscolour))return Integer.MAX_VALUE;
				ArrayList<Tuple> cBneedList = cB.getNeedList(keystring, kscolour.getEC(),true);
				for (Tuple k : cBneedList)if (cB.stones[k.a][k.b] == Stone.INVALID)return Integer.MAX_VALUE;
				retval += hrunner.runKeyStringHeuristics(keygroup,keystring);
			}
		}
		
		
		for (ArrayList<Tuple>  slist : kscolour.getSStrings(cB)) {
			for (Tuple  t : slist) retval += hrunner.runStoneHeuristics(t);	
		}
		

//		grouping.allocateControl();
//		if(kscolour==Stone.BLACK)retval+= grouping.totalb;
//		else retval+= grouping.totalw;
		

		return retval;
	}
	
	public  int evaluateCurrentBoard(boolean editormode) {
		int retval = 0;

		Grouping grouping = new Grouping(cB,false,false,false,false);
		grouping.allocateGrouping();
		
		if (cB.ko != null)retval += 50;
		HeuristicsRunner hrunner= new HeuristicsRunner(cB , this);
		
		if (editormode) {
			for (Group g : grouping.allGroups) {
				if(g.colour == kscolour)
				retval += hrunner.runKeyStringHeuristics(g,g.group);
			}
		}else {
			for (Tuple t : cB.keystones) {
				ArrayList<Tuple> keystring = cB.checkForStrings(t.a, t.b, kscolour.getSStrings(cB));
				Group keygroup = grouping.inGroup(t, kscolour);
				if (!keystring.isEmpty()) {
//					if (cB.checkStringSafetyv2(keystring, kscolour))return Integer.MAX_VALUE;
					ArrayList<Tuple> cBneedList = cB.getNeedList(keystring, kscolour.getEC(),true);
					for (Tuple k : cBneedList)if (cB.stones[k.a][k.b] == Stone.INVALID)return Integer.MAX_VALUE;
					retval += hrunner.runKeyStringHeuristics(keygroup,keystring);
				}
			}
			
			
			for (ArrayList<Tuple>  slist : kscolour.getSStrings(cB)) {
				for (Tuple  t : slist) retval += hrunner.runStoneHeuristics(t);	
			}
		
		}

		return retval;
	}






//	public static Tuple countStone(Stone colour, Stone[][] stones) {
//
//		int stoneCount = 0;
//		int enemyStoneCount = 0;
//		for (int i = 0; i < stones.length; i++) {
//			for (int j = 0; j < stones[i].length; j++) {
//				if (stones[i][j].getSC() == colour)
//					stoneCount++;
//				else if (stones[i][j].getSC() == colour.getEC())
//					enemyStoneCount++;
//
//			}
//		}
//		Tuple counts = new Tuple(stoneCount, enemyStoneCount);
//		return counts;
//	}

//	public UDLR distFromSide(ArrayList<Tuple> sstring, int d) {
//		boolean vert = (barOrien(sstring)==VERT);
//		boolean hor = (barOrien(sstring)==HOR);
//		Tuple t= sstring.get(1);
//
//		if(vert) {
//			if (t.a - d == 0 && (t.b != 0 || t.b != 18))
//				return LEFT;
//			if (t.a + d == 18 && (t.b != 0 || t.b != 18))
//				return RIGHT;
//		}
//
//		if(hor) {
//			if (t.b - d == 0 && (t.a != 0 || t.a != 18))
//				return UP;
//			if (t.b + d == 18 && (t.a != 0 || t.a != 18))
//				return DOWN;
//		}
//		return NODIR;
//	}

//	public UDLR barOrien(ArrayList<Tuple> sstring) {
//		if(sstring.size()>1) {
//			if(sstring.get(0).a == sstring.get(sstring.size()-1).a) return VERT;
//			if(sstring.get(0).b == sstring.get(sstring.size()-1).b) return HOR;
//		}
//		return NODIR;
//	}

//	public ArrayList<Tuple> getLine(ArrayList<Tuple> sstring, Tuple t, boolean down) {
//		ArrayList<Tuple> ret = new ArrayList<Tuple>();
//		if (sstring.contains(t)) {
//			ret.add(t);
//			if (down)
//				ret.addAll(getLine(sstring, t.down(), down));
//			else
//				ret.addAll(getLine(sstring, t.right(), down));
//		}
//		return ret;
//	}
	
//	public int invalidInNeedList(ArrayList<ArrayList<Tuple>> colourStrings, Stone enemycolour) {
//	int retval =0;
//	for (ArrayList<Tuple> sstring :colourStrings ) {
//		if (sstring.size()>1) {
//			ArrayList<Tuple> needList = cB.getNeedList(sstring, enemycolour,true);
//			for (Tuple k : needList) {
//				if (cB.stones[k.a][k.b] == Stone.INVALID) {
//					retval += 20;
//					break;}
//			}}
//	}
//	return retval;
//}


	public boolean isThere(Tuple t){
		if(!cB.withinBounds(t)) return false;
		return cB.stones[t.a][t.b].getSC() == kscolour;
	}

	public boolean isEnemy(Tuple t) {
		if(!cB.withinBounds(t)) return false;
		return cB.stones[t.a][t.b].getSC() == kscolour.getEC();
	}


	public boolean isEnemies(Tuple...ts) {
		for (Tuple t :ts){
			if (!isEnemy(t)) return false;
		}
		return true;
	}

	public boolean isTheres(Tuple...ts) {
		for (Tuple t :ts){
			if (!isThere(t)) return false;
		}
		return true;
	}
	
	public boolean isInvalid(Tuple t) {
		if(!cB.withinBounds(t)) return false;
		return cB.stones[t.a][t.b].getSC() == Stone.INVALID;
	}


	public void print(Object o) {
		System.out.println(o);
	}


}
