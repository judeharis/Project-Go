package Go.SlickGo;

import java.util.ArrayList;

public class MoveFinder2  implements Runnable{
	 Board originalBoard ;
	 ArrayList<Tuple> keystones;
	 static Stone keystonecolour;
	 Tuple choice;
	 static int max = Integer.MAX_VALUE;
	 static int min = Integer.MIN_VALUE;
	 Tuple obCounts;
	 int line = 0;
	 boolean exit;
	 boolean def;
	 
	 ArrayList<String> searched = new ArrayList<String>();

	 MoveFinder2(Board originalBoard, ArrayList<Tuple> keystones) {
		 this.originalBoard = originalBoard;
		 this.keystones = keystones;
		 this.choice = null;
//		 this.obCounts = Evaluator.countStone(originalBoard.turn ,originalBoard.stones);
	 }
	 

	 int alphaBeta(Board currentBoard , ArrayList<Tuple> keystonelist , boolean isLive , int depth, int alpha,int beta, ArrayList<Tuple> soFar) {
		if (this.exit) return 0;
	 	ArrayList<Tuple> validMoves = currentBoard.validMoves;
	 	long s = System.nanoTime();
	 	ArrayList<Tuple> goodMoves = currentBoard.removeBadMovess();
        long e = System.nanoTime();
        Board.arrayTimes[0] += (e - s );
        
		depth++;
//	 	print(currentBoard.placing+" valid moves:"+validMoves + " good moves: " + currentBoard.goodMoves);
		if (keystoneLives(keystonelist)) {
			int retval = min;
//			print("eval: "+retval);
//			print(soFar);
			return retval;
		}

		if (currentBoard.placing != keystonecolour && validMoves.size() == 0) {
			int retval = max;
//			print("eval: "+retval);
//			print(soFar);
			return retval;
		}
		else if (currentBoard.placing == keystonecolour && goodMoves.size() == 0) {
			currentBoard.passing = true;
			goodMoves.add(new Tuple(-9,-9));}
		
		if (goodMoves.size() == 0) {goodMoves = validMoves;}
		
		//Forbidden Third 
//		for (Tuple t : currentBoard.keystones) {
//			StoneStringResponse stringRes = currentBoard.checkForStrings(t.a,t.b,keystonecolour.getSStrings(currentBoard)); 
//			if (stringRes.state&& depth !=1 && currentBoard.checkStringSafety(stringRes.list, keystonecolour,new ArrayList<Tuple>())==1) {
//				int retval =max;
////				print("safe: "+retval);
////				print(soFar);
//				return retval;
//			}
//		}

		if (Play.heuristic && depth>4) {
			Evaluator evaluator = new Evaluator(currentBoard);

			int retval =evaluator.evaluateCurrentBoard(false);
//			print("eval: "+retval);
//			print(soFar);
			return retval;
		}
		
        if(searched.contains(currentBoard.boardString) && !goodMoves.isEmpty()) goodMoves.add(goodMoves.remove(0));
        else if(!searched.contains(currentBoard.boardString)) searched.add(currentBoard.boardString);
        	
        
		
		
		if(isLive) {
			int best = min;

			
			for (Tuple t : goodMoves) {
//				print("\n\r"+line++ + "."+currentBoard.placing+" valid moves:"+validMoves + " good moves: " + goodMoves +" \ndepth: " + depth +" step taken: " + t);

				Board b = Board.cloneBoard(currentBoard);

		    	long ttstart = System.nanoTime();
				b.takeTurn(t.a,t.b,false,true);
		        long ttend = System.nanoTime();
		        Board.takeTurnTime += (ttend - ttstart );  
				if(depth==1)System.out.print(t.clone()+ " :");
		    	ArrayList<Tuple> newSofar = Board.tupleArrayClone(soFar);
		    	newSofar.add(t);
//				print(newSofar);
				int returnscore =alphaBeta(b,keyStoneRemaining(b,keystonelist),!isLive,depth,alpha,beta,newSofar);	
		        if(returnscore > best && depth==1 ) {
		        	this.choice = t.clone();
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
//		        	moveFinder.choice = t.clone();
//		        	print(returnscore);
//		        }
//		        else if(depth==1){ 
//		        	b.passing = true;
//		        	returnscore =alphaBeta(b,keyStoneRemaining(b,keystonelist),!isLive,depth,moveFinder,alpha,beta);
//			        if(returnscore == max)  {moveFinder.choice = t.clone();print(depth + " " + t.clone());}
//		        }
				if (beta <= alpha) break;
			}
			
			return best;}
		else {
			int best = max;
	        
			for (Tuple t : goodMoves) {
//				print("\n\r"+line++ + "."+currentBoard.placing+" valid moves:"+validMoves + " good moves: " + goodMoves +" \ndepth: " + depth +" step taken: " + t);
				Board b = Board.cloneBoard(currentBoard);
		    	long ttstart = System.nanoTime();
				b.takeTurn(t.a,t.b,false,true);
		        long ttend = System.nanoTime();
		        Board.takeTurnTime += (ttend - ttstart );
				if(depth==1)System.out.print(t.clone()+ " :");
		    	ArrayList<Tuple> newSofar = Board.tupleArrayClone(soFar);
		    	newSofar.add(t);
//				print(newSofar);
				int returnscore =alphaBeta(b,keyStoneRemaining(b,keystonelist),!isLive,depth,alpha,beta,newSofar);
		        if(returnscore < best && depth==1 ) {
		        	this.choice = t.clone();
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
//		        	moveFinder.choice = t.clone();
//		        }
//		        else if(depth==1){       
//		        	b.passing = true;
//		        	returnscore =alphaBeta(b,keyStoneRemaining(b,keystonelist),!isLive,depth,moveFinder,alpha,beta);
//		            if(returnscore == max)  {moveFinder.choice = t.clone();print(depth + " " + t.clone());}
//		        }
		        if (beta <= alpha)break;
		    }
			
			return best;}

	

	} 





	@Override
	public void run() {
			print("Ai Started");

			if (Play.heuristic) {
				Evaluator evaluator = new Evaluator(originalBoard);
				print("current board :" +evaluator.evaluateCurrentBoard(false));
			}
			
			int i =1000000;
	    	long start = System.nanoTime();
	    	
	    	ArrayList<Tuple> sofar = new ArrayList<Tuple>();


	    	
			if (originalBoard.blackFirst && originalBoard.turn == Stone.WHITE || !originalBoard.blackFirst && originalBoard.turn == Stone.BLACK ) 
				def=!originalBoard.capToWin;
			else 	def= originalBoard.capToWin;
	    	
			if (originalBoard.blackFirst && originalBoard.turn == Stone.WHITE || !originalBoard.blackFirst && originalBoard.turn == Stone.BLACK ) 
					alphaBeta(originalBoard,keystones,originalBoard.capToWin,0,min,max,sofar);
			else 	alphaBeta(originalBoard,keystones,!originalBoard.capToWin,0,min,max,sofar);
			
			
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
			if (b.stones[t.a][t.b].getSC() == keystonecolour) liveList.add(new Tuple(t.a,t.b));
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
