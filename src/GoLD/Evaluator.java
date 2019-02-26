package GoLD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Evaluator {
	public Board cB;
	public Stone kscolour = MoveFinder.keystonecolour.getSC();
	public Stone enemycolour = kscolour.getEC();
	

	
	public Map<Tuple,Integer> checkedMap = new HashMap<>();
	public int checkedMapSize =0;
	
	public ArrayList<Tuple> eyes = new ArrayList<Tuple>();
	ArrayList<ArrayList<Tuple>> connectedEyes = new ArrayList<ArrayList<Tuple>>();
	public static long timed = 0;
	
	public Evaluator(Board cB) {
		this.cB = cB;
	}




	
	public  int evaluateCurrentBoard(boolean editormode) {
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
				if (!keystring.isEmpty()) {
					ArrayList<Tuple> cBneedList = cB.getNeedList(keystring, kscolour.getEC(),true);
					for (Tuple k : cBneedList)if (cB.stones[k.a][k.b] == Stone.INVALID)return Integer.MAX_VALUE;
				}
			}
			
			for (Group g : grouping.allGroups) {
				if(g.colour == kscolour)retval += hrunner.runKeyStringHeuristics(g);
			}
			retval+=eyeStonesLinked();

		}


		return retval;
	}

	public ArrayList<Tuple> moveGen(ArrayList<Tuple> goodMoves,int max) {
		long start = System.currentTimeMillis();
		
		cB.distance = new int[19][19];
		
		HeuristicsRunner hrunner= new HeuristicsRunner(cB , this);
		Grouping grouping = new Grouping(cB,false,false,false,false);
		ArrayList<Tuple> allKeyStringStones = new ArrayList<Tuple>();
		ArrayList<Tuple> oPP = new ArrayList<Tuple>();
		ArrayList<Tuple> workinglist = Board.tupleArrayClone(goodMoves);
		
		
		grouping.allocateGrouping();
//		grouping.allocateControl();
		for (Tuple t : cB.keystones) {
			ArrayList<Tuple> keygroup = grouping.inGroup(t, kscolour).group;
			allKeyStringStones.removeAll(keygroup);
			allKeyStringStones.addAll(keygroup);
		}
		hrunner.findUseFullMoves(allKeyStringStones);

		
		addToCheckedPoints(grouping.distanceGen(allKeyStringStones, kscolour.getEC()));
		oPP.addAll(countOrder());
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
		

//		cB.distance = new int[19][19];
//		int counter =1;
//		for(Tuple t :oPP) {
//			if(cB.withinBounds(t)) {
//				cB.distance[t.a][t.b]= counter;
//				counter++;
//			}
//		}
//		
		long end = System.currentTimeMillis();	
		timed += end-start;

		
		return oPP;
		
	}
	

	
	
	@SuppressWarnings("unchecked")
	public ArrayList<Tuple> countOrder(){
		Map<Tuple,Integer> map = checkedMap;
	    List<Tuple>[] bucket = new List[checkedMapSize + 1];

	    for (Tuple key : map.keySet()) {
	    	if(cB.withinBounds(key) && !cB.stones[key.a][key.b].isStone())cB.distance[key.a][key.b]= map.getOrDefault(key, 0);
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
		return cB.stones[t.a][t.b].getSC() == kscolour;
	}

	public boolean isEnemy(Tuple t) {
		if(!cB.withinBounds(t)) return false;
		return cB.stones[t.a][t.b].getSC() == kscolour.getEC();
	}
	
	public boolean isEmptySpace(Tuple t) {
		if(!cB.withinBounds(t)) return false;
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
	
	public void addToCheckedPoints(Tuple...ts) {
		for(Tuple t:ts) {
			checkedMap.put(t, checkedMap.getOrDefault(t, 0)+1);
			checkedMapSize++;
		}
	}
	
	public void addToCheckedPoints(ArrayList<Tuple> ts) {
		for(Tuple t:ts) {
			checkedMap.put(t, checkedMap.getOrDefault(t, 0)+1);
			checkedMapSize++;
		}
	}



}
