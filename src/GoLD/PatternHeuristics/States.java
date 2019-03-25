package GoLD.PatternHeuristics;


import java.util.ArrayList;

import GoLD.Board;
import GoLD.Evaluator;
import GoLD.Tuple;

public enum States {
	A,E,N;
	
	static public boolean stateCheck(ArrayList<States> states, States[] k) {
		if(states.size() == k.length) {
			for(int i=0; i<k.length;i++) {
				if (!(k[i]==states.get(i)))return false;
			}
		}
		return true;
	}
	
	static public ArrayList<States> addStates(Evaluator e,Tuple...ts) {
		ArrayList<States> states = new ArrayList<States>();
		for (Tuple t :ts){
			if (e.isTheres(t)) states.add(A);
			else if (e.isEnemies(t))  states.add(E);
			else states.add(N);
		}
		return states;
	}
	
	static public String arrayToString(Evaluator e,Tuple...ts) {
		String states = "";
		for (Tuple t :ts){
			if (e.isTheres(t)) states+="A";
			else if (e.isEnemies(t))  states+="E";
			else  states+="N";
		}
		return states;
	}
	
	static public float borderSafe(Evaluator e,int badlimit,Tuple...ts) {
		float enemycount = 0;
		float nothingcount = 0;
		ArrayList<Tuple> nothings = new ArrayList<Tuple>();
		for(Tuple t :ts) {
			if(e.isEnemy(t))enemycount++;
			else if(e.isThere(t));
			else {
				nothings.add(t);
				nothingcount++;
			}
		}	
		float retval = 0;
		if(enemycount+nothingcount <badlimit)retval = badlimit;
		else {
			enemycount +=(nothingcount/2);
			retval = badlimit - enemycount ;
		}
		return retval;
	}
	
	
	static public float borderSafeRel2(Evaluator e,int badlimit,Tuple...ts) {
		return borderSafeRelMod(2,e,badlimit,ts);
	}
	
	static public float borderSafeRel1(Evaluator e,int badlimit,Tuple...ts) {
		return borderSafeRelMod(1,e,badlimit,ts);
	}
	
	static public float borderSafeRel0(Evaluator e,int badlimit,Tuple...ts) {
		return borderSafeRelMod(0,e,badlimit,ts);
	}
	
	
	static public float borderSafeRelMod(int val ,Evaluator e,int badlimit,Tuple...ts) {
		float enemycount = 0;
		float nothingcount = 0;
		ArrayList<Tuple> nothings = new ArrayList<Tuple>();
		for(Tuple t :ts) {
			if(t==null)return badlimit;
			if(e.isEnemy(t))enemycount++;
			else if(e.isThere(t));
			else {
				nothings.add(t);
				nothingcount++;
			}
		}

		
		float retval = 0;
		if(enemycount+nothingcount <badlimit)retval = badlimit;
		else {
			enemycount +=(nothingcount/2);
			retval = badlimit - enemycount ;
		}
		
		
		int addNumber =0;
		if(retval ==0)addNumber=15*val;
		else if(retval <1) addNumber=10*val;
		else if(retval ==1) addNumber=5*val;
		for(Tuple t : nothings) {
			if(Board.withinBounds(t) && addNumber>0) {
				e.checkedMap.put(t, e.checkedMap.getOrDefault(t, 0)+addNumber);	
				e.checkedMapSize+=addNumber;
			}
		}
	
		return retval;
	}
	
	
//	static public float borderSafeOLD(Evaluator e,int badlimit,Tuple...ts) {
//		float enemycount = 0;
//		float nothingcount = 0;
//		ArrayList<Tuple> nothings = new ArrayList<Tuple>();
//		for(Tuple t :ts) {
//			if(e.isEnemy(t))enemycount++;
//			else if(e.isThere(t));
//			else {
////				e.addToCheckedPoints(t);
//				nothings.add(t);
//				nothingcount++;
//			}
//		}
//		enemycount +=(nothingcount/2);
//		
//		float retval = 0;
//		if(enemycount+nothingcount <badlimit)retval = badlimit;
//		else retval = badlimit - enemycount ;
//		
//		
//		int addNumber =0;
//		if(retval ==0)addNumber=15;
//		else if(retval <1) addNumber=10;
//		else if(retval ==1) addNumber=5;
//		for(Tuple t : nothings) {
//			if(e.cB.withinBounds(t)) {
//				e.checkedMap.put(t, e.checkedMap.getOrDefault(t, 0)+addNumber);	
//				e.checkedMapSize+=addNumber;
//			}
//		}
//		
////		if(enemycount+nothingcount <badlimit)return badlimit;
////		return badlimit - enemycount ;
//		
//		return retval;
//	}


	
	
	static public float minFinder(float ...fs) {

		float min = 10000;
		int halfcount=0;
		for(float f :fs) {
			if(f<min)min=f;
			if(f<1)halfcount ++;
			if(halfcount>1)return 0;
		}

		
		return min ;
	}
	
	
	static public boolean oneCheck(float ...fs) {
		for(float f :fs)if(f!=1)return false;
		return true ;
	}
	
	static public boolean numCheck(double i,float ...fs) {
		for(float f :fs)if(f!=i)return false;
		return true ;
	}
	
	static public void addAlly(ArrayList<Tuple> tlist,Evaluator e,Tuple ...ts) {

		for (Tuple t :ts){
			if (e.isTheres(t)) tlist.add(t);

		}
	}

}
