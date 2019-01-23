package Go.SlickGo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import PatternHeuristics.LearningValues;



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
	 public static boolean learnexit = false;
	 static PrintWriter writer = null;
	 static boolean editormode = false; 
	 
	 boolean useHashTable = false;
	 
	 public static Hashtable<String, Integer> good =  new Hashtable<String,Integer>();
	 public static Hashtable<String, Integer> bad =  new Hashtable<String,Integer>();
	 
	 
	 int result=0;
	 ArrayList<String> searched = new ArrayList<String>();


	 MoveFinder(Board originalBoard, ArrayList<Tuple> keystones) {
		 this.originalBoard = originalBoard;
		 this.keystones = keystones;
		 this.choice = null;
	 }
	 

	 int alphaBetaLearner(Board currentBoard , ArrayList<Tuple> keystonelist , boolean isLive , int depth, int alpha,int beta ) {

	 	ArrayList<Tuple> validMoves = currentBoard.getAllValidMoves();
		depth++;
		
		int currentscore = 0;
		Evaluator e1 = new Evaluator(currentBoard,originalBoard);
		currentscore= e1.evaluateCurrentBoard(true);
		
		if (!keystoneLives(keystonelist)) return currentscore;
		
		if (currentBoard.placing != keystonecolour && validMoves.size() == 0) {
			validMoves.add(new Tuple(-9,-9));
			currentBoard.passing = true;
		}else if (currentBoard.placing == keystonecolour && validMoves.size() == 0)  return currentscore;

        if(searched.contains(currentBoard.boardString) && !validMoves.isEmpty()) validMoves.add(validMoves.remove(0));
        else if(!searched.contains(currentBoard.boardString)) searched.add(currentBoard.boardString);

		if(isLive) {
			int best = min;
			for (Tuple t : validMoves) {
				Board b = Board.cloneBoard(currentBoard);
				b.takeTurn(t.a,t.b,false,true);  
				
				Evaluator e2 = new Evaluator(b);
				int worseCheck= e2.evaluateCurrentBoard(true);
				if(worseCheck < currentscore)continue;
		
				int returnscore = 0;
				if(useHashTable) {
				 	String key = b.boardToString();
					if(good.containsKey(key)) returnscore = good.get(key);
					else {
						returnscore =alphaBetaLearner(b,keyStoneRemaining(b,keystonelist),!isLive,depth,alpha,beta);
						good.put(key, returnscore);
					}
				}else returnscore =alphaBetaLearner(b,keyStoneRemaining(b,keystonelist),!isLive,depth,alpha,beta);
				
				if(returnscore > best && returnscore > currentscore && depth==1 )this.choice = t.clone();
		        best = Math.max(best, returnscore); 
		        alpha = Math.max(alpha, best);
//				if(beta <= alpha) break;
		        

			}
			if(depth==1 && this.choice==null )this.choice = new Tuple(-9,-9);
			if(best==min)best=currentscore;
			

			return best;
		}else {
			int best = max;
			for (Tuple t : validMoves) {
				Board b = Board.cloneBoard(currentBoard);
				b.takeTurn(t.a,t.b,false,true);
				
				int returnscore = 0;
				if(useHashTable) {
				 	String key = b.boardToString();
					if(bad.containsKey(key)) returnscore = bad.get(key);
					else {
						returnscore =alphaBetaLearner(b,keyStoneRemaining(b,keystonelist),!isLive,depth,alpha,beta);
						bad.put(key, returnscore);
					}
				}else returnscore =alphaBetaLearner(b,keyStoneRemaining(b,keystonelist),!isLive,depth,alpha,beta);

				
				if(returnscore < best && depth==1 )this.choice = t.clone();
				
				
				
		        best = Math.min(best, returnscore); 
		        beta = Math.min(beta, best);
//				if(beta <= alpha) break;

		    }
			


			return best;
		}

	

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
		
		if (goodMoves.size() == 0 || currentBoard.placing != keystonecolour ) {goodMoves = validMoves;}
		
		//Forbidden Third 
//		for (Tuple t : currentBoard.keystones) {
//			ArrayList<Tuple> keystring = currentBoard.checkForStrings(t.a,t.b,keystonecolour.getSStrings(currentBoard)); 
//			if (!keystring.isEmpty() && depth !=1 && currentBoard.checkStringSafetyv2(keystring, keystonecolour)) return max;	
//		}
		


		if (Play.heuristic && depth>cutoff) {
			Evaluator evaluator = new Evaluator(currentBoard,originalBoard);
			return evaluator.evaluateCurrentBoard();
		}
		
        if(searched.contains(currentBoard.boardString) && !goodMoves.isEmpty()) goodMoves.add(goodMoves.remove(0));
        else if(!searched.contains(currentBoard.boardString)) searched.add(currentBoard.boardString);
        
        	
//        Collections.shuffle(goodMoves);

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
				if(beta <= alpha) break;
			}
			return best;
						
		}else{
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
		        if(beta <= alpha)break;
		    }
			
			return best;
		}

	

	} 
	 
	public void run() {
        try {writer = new PrintWriter("tree.txt");}
        catch (FileNotFoundException e1) {e1.printStackTrace();}

		Evaluator evaluator = new Evaluator(originalBoard,originalBoard);


    	
		Board cloneBoard = Board.cloneBoard(originalBoard);
//		cloneBoard = originalBoard;
		
		useHashTable= true;
		Evaluator e1 = new Evaluator(cloneBoard,originalBoard);
		e1.evaluateCurrentBoard(true);
		LearningValues.initalboard = false;
		if (learnexit) {
			result = 0;
			return;
		}
		
		if(!editormode) {
			print("Ai Started");
			print("current board :" +evaluator.evaluateCurrentBoard(true));
			if (cloneBoard.blackFirst && cloneBoard.turn == Stone.WHITE || !cloneBoard.blackFirst && cloneBoard.turn == Stone.BLACK ) 
				result = alphaBeta(cloneBoard,keystones,cloneBoard.capToWin,0,min,max);
			else 	result = alphaBeta(cloneBoard,keystones,!cloneBoard.capToWin,0,min,max);
			print(result);
			print(choice);
			print("Ai Done");
		}else {
			if (cloneBoard.blackFirst && cloneBoard.turn == Stone.WHITE || !cloneBoard.blackFirst && cloneBoard.turn == Stone.BLACK ) 
				result = alphaBetaLearner(cloneBoard,keystones,cloneBoard.capToWin,0,min,max);
			else 	result = alphaBetaLearner(cloneBoard,keystones,!cloneBoard.capToWin,0,min,max);
		}

		writer.close();
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
	
    public static void p(String s){
    	 writer.write(s) ;
    }
    
    public String tab(int o) {
    	String s="";
    	for(int i = 0; i <o;i++)s+="\t";
    	return s;
    }
	  
    public static void print(Object o){
        System.out.println(o);
    }
   
}
