package PatternHeuristics;


import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Tuple;

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

}
