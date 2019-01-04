package Go.SlickGo;

import java.util.ArrayList;
import java.util.Collections;


public class MoveFinder  implements Runnable{
	 Board originalBoard ;
	 ArrayList<Tuple> keystones;
	 static Stone keystonecolour;
	 static int cutoff=1;
	 Tuple choice;
	 static int max = Integer.MAX_VALUE;
	 static int min = Integer.MIN_VALUE;
	 int line = 0;
	 boolean exit;

	 
	 ArrayList<String> searched = new ArrayList<String>();


	 MoveFinder(Board originalBoard, ArrayList<Tuple> keystones) {
		 this.originalBoard = originalBoard;
		 this.keystones = keystones;
		 this.choice = null;
	 }
	 

	 int alphaBeta(Board currentBoard , ArrayList<Tuple> keystonelist , boolean isLive , int depth, int alpha,int beta ) {
		if (this.exit) return 0;
	 	ArrayList<Tuple> validMoves = currentBoard.validMoves;
	 	ArrayList<Tuple> goodMoves = currentBoard.removeBadMovess();
		depth++;
		
		if (!keystoneLives(keystonelist)) return min;
		

		if (currentBoard.placing != keystonecolour && validMoves.size() == 0) return max;		
		else if (currentBoard.placing == keystonecolour && goodMoves.size() == 0) {
			currentBoard.passing = true;
			goodMoves.add(new Tuple(-9,-9));}
		
		if (goodMoves.size() == 0) {goodMoves = validMoves;}
		
		//Forbidden Third 
		for (Tuple t : currentBoard.keystones) {
			ArrayList<Tuple> keystring = currentBoard.checkForStrings(t.a,t.b,keystonecolour.getSStrings(currentBoard)); 
			if (!keystring.isEmpty() && depth !=1 && currentBoard.checkStringSafetyv2(keystring, keystonecolour)) return max;	
		}

		if (Play.heuristic && depth>cutoff) {
			Evaluator evaluator = new Evaluator(currentBoard,originalBoard);
			return evaluator.evaluateCurrentBoard();}
		
        if(searched.contains(currentBoard.boardString) && !goodMoves.isEmpty()) goodMoves.add(goodMoves.remove(0));
        else if(!searched.contains(currentBoard.boardString)) searched.add(currentBoard.boardString);
        	


//		print("\n\r"+line++ + "."+currentBoard.placing+" valid moves:"+validMoves + " good moves: " + goodMoves +" \ndepth: " + depth +" step taken: " );

		if(isLive) {
			int best = min;
			for (Tuple t : goodMoves) {
				Board b = currentBoard;
				b.takeTurn(t.a,t.b,false,true);  
				if(depth==1)System.out.print(t.clone()+ " :");
				int	returnscore =alphaBeta(b,keyStoneRemaining(b,keystonelist),!isLive,depth,alpha,beta);
				b.undoMove(false);
				if(returnscore > best && depth==1 )this.choice = t.clone();
		        best = Math.max(best, returnscore); 
		        alpha = Math.max(alpha, best);
		        if(depth==1) print(returnscore);
		        
				if (beta <= alpha) break;
			}
			return best;}
		else {
			int best = max;
 
			for (Tuple t : goodMoves) {
				Board b = currentBoard;
				b.takeTurn(t.a,t.b,false,true);
				if(depth==1)System.out.print(t.clone()+ " :");
				int returnscore =alphaBeta(b,keyStoneRemaining(b,keystonelist),!isLive,depth,alpha,beta);
				b.undoMove(false);
				if(returnscore < best && depth==1 )this.choice = t.clone();
		        best = Math.min(best, returnscore); 
		        beta = Math.min(beta, best);
		        if(depth==1) print(returnscore);
		        if (beta <= alpha)break;
		    }
			
			return best;}

	

	} 


	public void run() {
			print("Ai Started");
			if (Play.heuristic) {
				Evaluator evaluator = new Evaluator(originalBoard,originalBoard);
				print("current board :" +evaluator.evaluateCurrentBoard());
			}
	    	
			if (originalBoard.blackFirst && originalBoard.turn == Stone.WHITE || !originalBoard.blackFirst && originalBoard.turn == Stone.BLACK ) 
					alphaBeta(originalBoard,keystones,originalBoard.capToWin,0,min,max);
			else 	alphaBeta(originalBoard,keystones,!originalBoard.capToWin,0,min,max);
			
			print(choice);
			print("Ai Done");
	}


	static public ArrayList<Tuple> keyStoneRemaining(Board b,ArrayList<Tuple> keystonelist){
	 	ArrayList<Tuple> liveList = new ArrayList<Tuple>();
		for (Tuple t : keystonelist){
			if (b.stones[t.a][t.b].getSC() == keystonecolour) liveList.add(new Tuple(t.a,t.b));
		}
		return liveList;
	}

	static public boolean keystoneLives(ArrayList<Tuple> keystonelist){
	 	if(!keystonelist.isEmpty()) return true;
	 	return false;
	}
	  
    public static void print(Object o){
        System.out.println(o);
    }
   
}
