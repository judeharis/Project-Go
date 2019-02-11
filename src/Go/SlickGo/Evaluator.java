package Go.SlickGo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Evaluator {
	public Board cB;
	public Stone kscolour = MoveFinder.keystonecolour.getSC();
	public Stone enemycolour = kscolour.getEC();
	public ArrayList<Tuple> checkedPoints = new ArrayList<Tuple>();
	public ArrayList<Tuple> eyes = new ArrayList<Tuple>();
	ArrayList<ArrayList<Tuple>> connectedEyes = new ArrayList<ArrayList<Tuple>>();
	public static long timed = 0;
	public Evaluator(Board cB) {
		this.cB = cB;
	}




	
	public  int evaluateCurrentBoard(boolean editormode) {
		long start = System.currentTimeMillis();
		int retval = 0;

		Grouping grouping = new Grouping(cB,false,false,false,false);
		grouping.allocateGrouping();
		

		HeuristicsRunner hrunner= new HeuristicsRunner(cB , this);
		
		if (editormode) {
			for (Group g : grouping.allGroups) {
				if(g.colour == kscolour)retval += hrunner.runKeyStringHeuristics(g);
			}
			retval+=eyeStonesLinked();
		}else {
			for (Tuple t : cB.keystones) {
				ArrayList<Tuple> keystring = cB.checkForStrings(t, kscolour);
				Group keygroup = grouping.inGroup(t, kscolour);
				if (!keystring.isEmpty()) {
					ArrayList<Tuple> cBneedList = cB.getNeedList(keystring, kscolour.getEC(),true);
					for (Tuple k : cBneedList)if (cB.stones[k.a][k.b] == Stone.INVALID)return Integer.MAX_VALUE;
					retval += hrunner.runKeyStringHeuristics(keygroup);
				}
			}
			

			retval+=eyeStonesLinked();

//			for (ArrayList<Tuple>  slist : kscolour.getSStrings(cB)) {
//				for (Tuple  t : slist) retval += hrunner.runStoneHeuristics(t);	
//			}
		
		}
		long end = System.currentTimeMillis();
		timed += end-start;
		return retval;
	}


	public ArrayList<Tuple> moveGen(ArrayList<Tuple> goodMoves,int max) {
		
		ArrayList<Tuple> oPP = countOrder(checkedPoints);
		ArrayList<Tuple> workinglist = Board.tupleArrayClone(goodMoves);
//		float r = ((float)(workinglist.size()*4))/5;
//		if(orderedPatternPoints.size() < r) return goodMoves;
		oPP.retainAll(workinglist);
		workinglist.removeAll(oPP);
		
		int k = max-oPP.size();
		while(k<0 && !oPP.isEmpty()) {
			oPP.remove(oPP.size()-1);
			k =max-oPP.size();
		}
		for(int i =0; i< k;i++) {
			if(workinglist.isEmpty())break;
			Tuple t = workinglist.remove(new Random().nextInt(workinglist.size()));
			oPP.add(t);
		}

		return oPP;
		
	}



	
	@SuppressWarnings("unchecked")
	public ArrayList<Tuple> countOrder(ArrayList<Tuple> patternPoints){
		Map<Tuple,Integer> map = new HashMap<>();
	    List<Tuple>[] bucket = new List[patternPoints.size() + 1];
		for(Tuple t :patternPoints) {
			map.put(t, map.getOrDefault(t, 0)+1);
		}
	    for (Tuple key : map.keySet()) {
	        int frequency = map.get(key);
	        if (bucket[frequency] == null) {
	            bucket[frequency] = new ArrayList<>();
	        }
	        bucket[frequency].add(key);
	        
	    }
	    ArrayList<Tuple> res = new ArrayList<>();
	    for (int i = bucket.length - 1; i >= 0; i--) {
	        if (bucket[i] != null) {
	            res.addAll(bucket[i]);
	        }
	    }
		
		
		return res;
		
	}
	
	

	
	public ArrayList<Tuple> getEyeStoneLibs(ArrayList<Tuple> tlist){
		ArrayList<Tuple> eyeStones = new ArrayList<Tuple>();
		ArrayList<Tuple> adj = new ArrayList<Tuple>();
		
		for(Tuple t:tlist) adj.addAll(cB.getAdjacent(t.a, t.b));
		
		
		for(Tuple a:adj) {
			ArrayList<Tuple> linkedStones =cB.checkForStrings(a, kscolour);
			eyeStones.removeAll(linkedStones);
			eyeStones.addAll(linkedStones);
		}
		ArrayList<Tuple> eyeStonesLibs = cB.getLibs(eyeStones, true);
		eyeStonesLibs.removeAll(tlist);
		return eyeStonesLibs;
	}
	
	
	public int eyeStonesLinked(){
//		ArrayList<Tuple> uniqEyes = (ArrayList<Tuple>) eyes.stream().distinct().collect(Collectors.toList());
		ArrayList<ArrayList<ArrayList<Tuple>>> connectEyes = new ArrayList<ArrayList<ArrayList<Tuple>>>();
		for(ArrayList<Tuple> eyes :connectedEyes) {
			ArrayList<Tuple> eyeStonesLibs =  getEyeStoneLibs(eyes);
			boolean added = false;
			for(ArrayList<ArrayList<Tuple>> tlists : connectEyes) {
				for (ArrayList<Tuple> tlist : tlists) {
					for(Tuple e : tlist) {
						if (eyeStonesLibs.contains(e)) {
							tlists.add(eyes);
							added =true;
							break;
						}
					}
					if(added)break;
				}
				if(added)break;
			}
			
			if(added==false) {
				ArrayList<ArrayList<Tuple>> newEyesList =  new ArrayList<ArrayList<Tuple>>();
				newEyesList.add(eyes);
				connectEyes.add(newEyesList);
			}
		}
		
		int retval = 0;
		for(ArrayList<ArrayList<Tuple>> tlists : connectEyes) {
			if(tlists.size()>=2) {
				retval+=800;
				retval+=(tlists.size() - 2) * 200;
			}
		}
		return retval;
	}
	
	


	public boolean isThere(Tuple t){
		if(!cB.withinBounds(t)) return false;
		checkedPoints.add(t);
		return cB.stones[t.a][t.b].getSC() == kscolour;
	}

	public boolean isEnemy(Tuple t) {
		if(!cB.withinBounds(t)) return false;
		checkedPoints.add(t);
		return cB.stones[t.a][t.b].getSC() == kscolour.getEC();
	}
	
	public boolean isEmptySpace(Tuple t) {
		if(!cB.withinBounds(t)) return false;
		checkedPoints.add(t);
		return !cB.stones[t.a][t.b].isStone();
	}
	


	public boolean isEnemies(Tuple...ts) {
		boolean ret = true;
		for (Tuple t :ts)if(!isEnemy(t))ret=false;
		return ret;
	}
	
	

	public boolean isTheres(Tuple...ts) {
		boolean ret = true;
		for (Tuple t :ts)if(!isThere(t))ret=false;
		return ret;
	}
	
	public int countThere(Tuple...ts) {
		int truecount=0;
		for (Tuple t :ts)if(isThere(t))truecount++;
		return truecount;
	}
	
	public int countEnemy(Tuple...ts) {
		int truecount=0;
		for (Tuple t :ts)if(isEnemy(t))truecount++;
		return truecount;
	}
	
	public boolean isInvalid(Tuple t) {
		if(!cB.withinBounds(t)) return false;
		return cB.stones[t.a][t.b].getSC() == Stone.INVALID;
	}


	public void print(Object o) {
		System.out.println(o);
	}
	
	public void addToEye(Tuple...ts) {
		ArrayList<Tuple> tlist = new ArrayList<Tuple>();
		for(Tuple t:ts) {
			if(isEmptySpace(t))eyes.add(t);
			tlist.add(t);
		}
		connectedEyes.add(tlist);
		
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


}
