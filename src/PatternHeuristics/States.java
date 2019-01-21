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
			if (e.isThere(t)) states.add(A);
			else if (e.isEnemy(t))  states.add(E);
			else states.add(N);
		}
		return states;
	}

}
