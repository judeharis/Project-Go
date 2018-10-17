package Go.SlickGo;

import java.util.ArrayList;
import java.util.Collections;

public class Minimaxer  implements Runnable{
	 Board originalBoard ;
	 ArrayList<Tuple> keystones;
	 static Stones human;
	 Tuple choice;
	 static int max = 1000;//Integer.MAX_VALUE;
	 static int min = -1000;//Integer.MIN_VALUE;

	 Minimaxer(Board originalBoard, ArrayList<Tuple> keystones) {
		 this.originalBoard = originalBoard;
		 this.keystones = keystones;
		 this.choice = null;
	 }
	 

	 static int minimax(Board currentBoard ,ArrayList<Tuple> validMoves, ArrayList<Tuple> keystonelist , boolean isLive , int depth, Minimaxer minimaxer, int alpha,int beta) {

	 	int currentbest=0;
	 	print(currentBoard.placing+" valid moves:"+validMoves);
	 	if (depth>5) return max;
	 	
		if (keystoneLives(keystonelist)) return min+depth;
		depth++;
		if (currentBoard.placing != human && validMoves.size() == 0)return max;
		
		if(isLive) {
			int best = min;

			
			for (Tuple t : validMoves) {
				Board b = Board.cloneBoard(currentBoard);
				b.ai= true;
				b.computer = false;
				b.takeTurn(t.a,t.b,false);
				
				int returnscore =minimax(b,b.getAllValidMoves(),keyStoneRemaining(b,keystonelist),!isLive,depth,minimaxer,alpha,beta);

				if(depth==1) {
					print(depth);
					print("x: "+t.a+" y: "+t.b);
					print(returnscore);}


		        best = Math.max(best, returnscore); 
		        alpha = Math.max(alpha, best);
				if (beta <= alpha) break; }
			
			return best+depth;}
		else {
			int best = max;
	        
			for (Tuple t : validMoves) {
				Board b = Board.cloneBoard(currentBoard);
				b.ai= true;
				b.computer = false;
				b.takeTurn(t.a,t.b,false);
				
				int returnscore =minimax(b,b.getAllValidMoves(),keyStoneRemaining(b,keystonelist),!isLive,depth,minimaxer,alpha,beta);
	
				if(depth==1) {
					print(depth);
					print("x: "+t.a+" y: "+t.b);
					print(returnscore);}
				

		        best = Math.min(best, returnscore); 
		        alpha = Math.min(alpha, best);	
		        if(best < currentbest && depth==1 ) { currentbest = best;minimaxer.choice = t.clone();}
		        if (beta <= alpha) break; }
			
			return best;}

	

	} 


	@Override
	public void run() {
		print("Ai Started");
		minimax(originalBoard,originalBoard.validMoves,keystones,false,0,this,min,max);
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
