package Go;

import java.util.ArrayList;
import java.util.Collections;

public class Minimaxer  implements Runnable{
	 Board originalBoard ;
	 ArrayList<Tuple> keystones;
	 static Stones human;


	 Minimaxer(Board originalBoard, ArrayList<Tuple> keystones, Stones humanColour) {
		 this.originalBoard = originalBoard;
		 this.keystones = keystones;
		 this.human = humanColour;
	 }
	 

	 static int minimax(Board currentBoard ,ArrayList<Tuple> validMoves, ArrayList<Tuple> keystonelist , boolean isLive ) {


	 	ArrayList<Integer> score = new ArrayList<>();

		if (keystoneLives(keystonelist)) return -1;

		if (validMoves.isEmpty())return 1;


	  
	    // If current move is maximizer, find the maximum attainable 
	    // value

		for (Tuple t : validMoves) {
			Board b = new Board();
			b.initBoard();
			b.stones = currentBoard.stones.clone();
			b.turn = currentBoard.turn;
			b.placing = b.turn;
			b.ko = currentBoard.ko;
			b.updateStringsFull();
			b.checkForCaps(Stones.getEnemyColour(b.turn));
			b.checkForCaps(b.turn);
			b.ai= true;
			b.computer = false;
			b.takeTurn(t.a,t.b);


			score.add((minimax(b,b.getAllValidMoves(),keyStoneRemaining(b,keystonelist),false)));
			minimax(b,b.getAllValidMoves(),keyStoneRemaining(b,keystonelist),true);
		}

		if(isLive) return Collections.max(score);
		else  return Collections.min(score);

	} 


	@Override
	public void run() {
		minimax(originalBoard,originalBoard.validMoves,keystones,false);
		
	}


	static public ArrayList<Tuple> keyStoneRemaining(Board b,ArrayList<Tuple> keystonelist){
	 	ArrayList<Tuple> liveList = new ArrayList<Tuple>();
		for (Tuple t : keystonelist){
			if (Stones.getStoneColour(b.stones[t.a][t.b]) == human) liveList.add(new Tuple(t.a,t.b));
		}
		return liveList;
	}

	static public boolean keystoneLives(ArrayList<Tuple> keystonelist){
	 	if(keystonelist.isEmpty()) return true;
	 	return false;
	}
	  
	 
}
