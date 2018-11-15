package Go.SlickGo;

import java.util.ArrayList;

public class Minimaxer  implements Runnable{
	 Board originalBoard ;
	 ArrayList<Tuple> keystones;
	 static Stone keystonecolour;
	 Tuple choice;
	 static int max = Integer.MAX_VALUE;
	 static int min = Integer.MIN_VALUE;
	 Tuple obCounts;
	 int line = 0;
	 boolean exit;

	 Minimaxer(Board originalBoard, ArrayList<Tuple> keystones) {
		 this.originalBoard = originalBoard;
		 this.keystones = keystones;
		 this.choice = null;
		 this.obCounts = Evaluator.countStone(originalBoard.turn ,originalBoard.stones);
	 }
	 

	 static int minimax(Board currentBoard , ArrayList<Tuple> keystonelist , boolean isLive , int depth, Minimaxer minimaxer, int alpha,int beta, ArrayList<Tuple> soFar) {
		if (minimaxer.exit) return 0;
	 	ArrayList<Tuple> validMoves = currentBoard.validMoves;
	 	long s = System.nanoTime();
	 	ArrayList<Tuple> goodMoves = currentBoard.removeBadMovess();
        long e = System.nanoTime();
        Board.arrayTimes[0] += (e - s );
        

//	 	print(currentBoard.placing+" valid moves:"+validMoves + " good moves: " + currentBoard.goodMoves);
		if (keystoneLives(keystonelist)) return min;
		depth++;
		if (currentBoard.placing != keystonecolour && validMoves.size() == 0) return max;
		else if (currentBoard.placing == keystonecolour && goodMoves.size() == 0) {
			currentBoard.passing = true;
			goodMoves.add(new Tuple(-9,-9));
		}
		if (goodMoves.size() == 0) {goodMoves = validMoves;}
		
//		for (Tuple t : currentBoard.keystones) {
//			StoneStringResponse stringRes = currentBoard.checkForStrings(t.a,t.b,keystonecolour.getSStrings(currentBoard)); 
//			if (stringRes.state&& depth !=1 && currentBoard.checkStringSafety(stringRes.list, keystonecolour,new ArrayList<Tuple>())==1)return max;
//		}

		if (Play.heuristic && depth>5) return Evaluator.evaluateBoard(currentBoard,minimaxer.originalBoard,minimaxer);
		
		
		
		
		
		
		if(isLive) {
			int best = min;

			
			for (Tuple t : goodMoves) {
//				print("\n\r"+minimaxer.line++ + "."+currentBoard.placing+" valid moves:"+validMoves + " good moves: " + goodMoves +" \ndepth: " + depth +" step taken: " + t);

				Board b = Board.cloneBoard(currentBoard);

		    	long ttstart = System.nanoTime();
				b.takeTurn(t.a,t.b,false,true);
		        long ttend = System.nanoTime();
		        Board.takeTurnTime += (ttend - ttstart );
		        
				if(depth==1  )print(depth + " " + t.clone());
		    	ArrayList<Tuple> newSofar = Board.tupleArrayClone(soFar);
//		    	newSofar.add(t);
//				print(newSofar);
				
				int returnscore =minimax(b,keyStoneRemaining(b,keystonelist),!isLive,depth,minimaxer,alpha,beta,newSofar);
			 	
		        if(returnscore > best && depth==1 ) {
		        	minimaxer.choice = t.clone();
		        	if (returnscore == max)print("Living");
		        	else if (returnscore == min)print("Dead");
		        	else print(returnscore);
		        }else if (depth ==1) {
		        	if (returnscore == max)print("Living");
		        	else if (returnscore == min)print("Dead");
		        	else print(returnscore);
		        }
		        
		        
		        best = Math.max(best, returnscore); 
		        alpha = Math.max(alpha, best);
		        
//		        if(best == max && depth==1 ) {
//		        	minimaxer.choice = t.clone();
//		        	print(returnscore);
//		        }
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
//				print("\n\r"+minimaxer.line++ + "."+currentBoard.placing+" valid moves:"+validMoves + " good moves: " + goodMoves +" \ndepth: " + depth +" step taken: " + t);
				Board b = Board.cloneBoard(currentBoard);
		    	long ttstart = System.nanoTime();
				b.takeTurn(t.a,t.b,false,true);
		        long ttend = System.nanoTime();
		        Board.takeTurnTime += (ttend - ttstart );
				if(depth==1  )print(depth + " " + t.clone());
		    	ArrayList<Tuple> newSofar = Board.tupleArrayClone(soFar);
//		    	newSofar.add(t);
//				print(newSofar);
				int returnscore =minimax(b,keyStoneRemaining(b,keystonelist),!isLive,depth,minimaxer,alpha,beta,newSofar);
		        if(returnscore < best && depth==1 ) {
		        	minimaxer.choice = t.clone();
		        	if (returnscore == max)print("Living");
		        	else if (returnscore == min)print("Dead");
		        	else print(returnscore);
		        }else if (depth ==1) {
		        	if (returnscore == max)print("Living");
		        	else if (returnscore == min)print("Dead");
		        	else print(returnscore);
		        }
		        
		        
		        best = Math.min(best, returnscore); 
		        beta = Math.min(beta, best);	
		        
//		        if(best == min && depth==1 ) {
//		        	minimaxer.choice = t.clone();
//		        }
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
			int i =1000000;
	    	long start = System.nanoTime();
	    	
	    	ArrayList<Tuple> sofar = new ArrayList<Tuple>();


	    	
			if (originalBoard.blackFirst && originalBoard.turn == Stone.WHITE || !originalBoard.blackFirst && originalBoard.turn == Stone.BLACK ) 
					minimax(originalBoard,keystones,originalBoard.capToWin,0,this,min,max,sofar);
			else 	minimax(originalBoard,keystones,!originalBoard.capToWin,0,this,min,max,sofar);
			
			
	        long end = System.nanoTime();
	        Board.fullTime += (end - start );
	        print(Board.arrayTimes[0]/i  + " " +Board.arrayTimes[0] );
			if ( Board.takeTurnTime !=0 &&  Board.fullTime !=0 &&	Board.arrayTimes[0] !=0 )
				print(Board.takeTurnTime/(i) +" "+Board.fullTime/(i) + " " + (Board.takeTurnTime*100)/Board.fullTime + "% "+
				Board.arrayTimes[0]/i + " "  + (Board.arrayTimes[0]*100)/Board.fullTime + "% ");

			Board.arrayTimes = new long[10];
			Board.takeTurnTime = 0;
			Board.fullTime =0;
			print(choice);
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
