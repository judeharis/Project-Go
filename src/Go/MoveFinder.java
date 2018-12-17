package Go;

import java.util.ArrayList;
import java.util.Collections;

public class MoveFinder  implements Runnable{
	 Board originalBoard ;
	 ArrayList<Tuple> keystones;
	 static Stones human;
	 Tuple choice;

	 MoveFinder(Board originalBoard, ArrayList<Tuple> keystones) {
		 this.originalBoard = originalBoard;
		 this.keystones = keystones;
		 this.choice = null;
	 }


	 static int alphaBeta(Board currentBoard ,ArrayList<Tuple> validMoves, ArrayList<Tuple> keystonelist , boolean isLive , int depth, MoveFinder moveFinder) {


	 	ArrayList<Integer> score = new ArrayList<>();

		if (keystoneLives(keystonelist)) return Integer.MIN_VALUE;

		if (validMoves.size() == 0)return Integer.MAX_VALUE;

		depth++;
		for (Tuple t : validMoves) {
			int returnscore;
			Board b = Board.cloneBoard(currentBoard);
			b.ai= true;
			b.computer = false;
			b.takeTurn(t.a,t.b);




			returnscore =alphaBeta(b,b.getAllValidMoves(),keyStoneRemaining(b,keystonelist),!isLive,depth,moveFinder);
			score.add(returnscore);
			if(depth==1) {
				print(depth);
				print("x: "+t.a+" y: "+t.b);
				print(returnscore);}

			if(returnscore==Integer.MIN_VALUE && depth==1) moveFinder.choice = t.clone();
		}

		if(isLive) return Collections.max(score);
		else  return Collections.min(score);

	}


	@Override
	public void run() {
		alphaBeta(originalBoard,originalBoard.validMoves,keystones,false,0,this);
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

    public static void print(Object o){
        System.out.println(o);
    }

}
