package Go.SlickGo;

import java.util.ArrayList;

public class Minimaxer  implements Runnable{
	 Board originalBoard ;
	 ArrayList<Tuple> keystones;
	 static Stone keystonecolour;
	 Tuple choice;
	 static int max = 1000;//Integer.MAX_VALUE;
	 static int min = -1000;//Integer.MIN_VALUE;
	 int line = 0;
	 boolean exit;

	 Minimaxer(Board originalBoard, ArrayList<Tuple> keystones) {
		 this.originalBoard = originalBoard;
		 this.keystones = keystones;
		 this.choice = null;
	 }
	 

	 static int minimax(Board currentBoard , ArrayList<Tuple> keystonelist , boolean isLive , int depth, Minimaxer minimaxer, int alpha,int beta) {

		if (minimaxer.exit) return 0;
	 	ArrayList<Tuple> validMoves = currentBoard.validMoves;
	 	ArrayList<Tuple> goodMoves = currentBoard.removeBadMovess();

//	 	print(currentBoard.placing+" valid moves:"+validMoves + " good moves: " + currentBoard.goodMoves);
		if (keystoneLives(keystonelist)) return min;
		depth++;
		if (currentBoard.placing != keystonecolour && validMoves.size() == 0) return max;
		else if (currentBoard.placing == keystonecolour && goodMoves.size() == 0) {
			currentBoard.passing = true;
			goodMoves = validMoves;
			validMoves.add(new Tuple(-9,-9));
		}
		if (goodMoves.size() == 0) {goodMoves = validMoves;}
		
		if(isLive) {
			int best = min;

			
			for (Tuple t : goodMoves) {
				//print("\n\r"+minimaxer.line++ + "."+currentBoard.placing+" valid moves:"+validMoves + " good moves: " + currentBoard.goodMoves +" \ndepth: " + depth +" step taken: " + t);

				Board b = Board.cloneBoard(currentBoard);
				b.takeTurn(t.a,t.b,false,true);
				if(depth==1  )print(depth + " " + t.clone());
				int returnscore =minimax(b,keyStoneRemaining(b,keystonelist),!isLive,depth,minimaxer,alpha,beta);
			
		        best = Math.max(best, returnscore); 
		        alpha = Math.max(alpha, best);
		        if(best == max && depth==1 ) minimaxer.choice = t.clone();
//		        else if(depth==1){ 
//		        	b.passing = true;
//		        	returnscore =minimax(b,keyStoneRemaining(b,keystonelist),!isLive,depth,minimaxer,alpha,beta);
//			        if(returnscore == max)  {minimaxer.choice = t.clone();print(depth + " " + t.clone());}
//		        }
				if (beta <= alpha) break;
			}
			
			return best;}
		else {
			int best = max;
	        
			for (Tuple t : goodMoves) {
				//print("\n\r"+minimaxer.line++ + "."+currentBoard.placing+" valid moves:"+validMoves + " good moves: " + currentBoard.goodMoves +" \ndepth: " + depth +" step taken: " + t);
				Board b = Board.cloneBoard(currentBoard);
				b.takeTurn(t.a,t.b,false,true);
				if(depth==1  )print(depth + " " + t.clone());
				int returnscore =minimax(b,keyStoneRemaining(b,keystonelist),!isLive,depth,minimaxer,alpha,beta);
		        best = Math.min(best, returnscore); 
		        beta = Math.min(beta, best);	
		        if(best == min && depth==1 ) minimaxer.choice = t.clone();
//		        else if(depth==1){       
//		        	b.passing = true;
//		        	returnscore =minimax(b,keyStoneRemaining(b,keystonelist),!isLive,depth,minimaxer,alpha,beta);
//		            if(returnscore == max)  {minimaxer.choice = t.clone();print(depth + " " + t.clone());}
//		        }
		        if (beta <= alpha)break;
		    }
			
			return best;}

	

	} 


	@Override
	public void run() {
			print("Ai Running");
			//originalBoard.goodMoves = originalBoard.removeBadMovess();
			if (originalBoard.blackFirst && originalBoard.turn == Stone.WHITE || !originalBoard.blackFirst && originalBoard.turn == Stone.BLACK ) 
					minimax(originalBoard,keystones,originalBoard.capToWin,0,this,min,max);
			else 	minimax(originalBoard,keystones,!originalBoard.capToWin,0,this,min,max);
			print("Ai Done");
	}


	static public ArrayList<Tuple> keyStoneRemaining(Board b,ArrayList<Tuple> keystonelist){
	 	ArrayList<Tuple> liveList = new ArrayList<Tuple>();
		for (Tuple t : keystonelist){
			if (b.stones[t.a][t.b].getStoneColour() == keystonecolour) liveList.add(new Tuple(t.a,t.b));
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
