package GoLD.PatternHeuristics;


import java.util.ArrayList;

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
		for(Tuple t :ts) {
			if(e.isEnemy(t))enemycount++;
			else if(e.isThere(t));
			else nothingcount++;
		}
		enemycount +=(nothingcount/2);
		if(enemycount+nothingcount <badlimit)return badlimit;
		return badlimit - enemycount ;
	}

	
	
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
