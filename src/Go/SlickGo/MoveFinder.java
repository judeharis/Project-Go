package Go.SlickGo;


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

	 static boolean editormode = false; 
	 
	 boolean useHashTable = false;
	 
	 public static Hashtable<String, Integer> lgood =  new Hashtable<String,Integer>();
	 public static Hashtable<String, Integer> lbad =  new Hashtable<String,Integer>();
	 
	 Hashtable<String, Integer> good =  new Hashtable<String,Integer>();
	 Hashtable<String, Integer> bad =  new Hashtable<String,Integer>();
	 
	 int hitcounter= 0;

	 
	 Tuple[][] killers =  new Tuple[100][2];
	 
	 
	 int result=0;
	 ArrayList<String> searched = new ArrayList<String>();


	 MoveFinder(Board originalBoard, ArrayList<Tuple> keystones) {
		 this.originalBoard = originalBoard;
		 this.keystones = keystones;
		 this.choice = null;
	 }
	 

	 int alphaBetaLearner(Board cB , ArrayList<Tuple> keystonelist , boolean isLive , int depth, int alpha,int beta ) {

	 	ArrayList<Tuple> validMoves = cB.getAllValidMoves();
		depth++;
		
		int currentscore = 0;
		Evaluator e1 = new Evaluator(cB);
		currentscore= e1.evaluateCurrentBoard(true);
		
		if (!keystoneLives(keystonelist)) return currentscore;
		
		if (cB.placing != keystonecolour && validMoves.size() == 0) validMoves.add(new Tuple(-9,-9));
		else if (cB.placing == keystonecolour && validMoves.size() == 0)  return currentscore;

        if(searched.contains(cB.boardString) && !validMoves.isEmpty()) validMoves.add(validMoves.remove(0));
        else if(!searched.contains(cB.boardString)) searched.add(cB.boardString);

		if(isLive) {
			int best = min;
			for (Tuple t : validMoves) {
				Board b = Board.cloneBoard(cB);
				b.takeTurn(t.a,t.b,false,true);  
				
				Evaluator e2 = new Evaluator(b);
				int worseCheck= e2.evaluateCurrentBoard(true);
				if(worseCheck < currentscore)continue;
		
				int returnscore = 0;
				if(useHashTable) {
				 	String key = b.boardToString();
					if(lgood.containsKey(key)) returnscore = lgood.get(key);
					else {
						returnscore =alphaBetaLearner(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
						lgood.put(key, returnscore);
					}
				}else returnscore =alphaBetaLearner(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
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
				Board b = Board.cloneBoard(cB);
				b.takeTurn(t.a,t.b,false,true);
				
				int returnscore = 0;
				if(useHashTable) {
				 	String key = b.boardToString();
					if(lbad.containsKey(key)) returnscore = lbad.get(key);
					else {
						returnscore =alphaBetaLearner(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
						lbad.put(key, returnscore);
					}
				}else returnscore =alphaBetaLearner(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);

				
				if(returnscore < best && depth==1 )this.choice = t.clone();
				
				
				
		        best = Math.min(best, returnscore); 
		        beta = Math.min(beta, best);
//				if(beta <= alpha) break;

		    }
			return best;
		}
	} 
	 
	

	int alphaBetaOld(Board cB , ArrayList<Tuple> keystonelist , boolean isLive , int depth, int alpha,int beta ) {
		if (exit) return 0;
	 	ArrayList<Tuple> validMoves = cB.validMoves;
	 	ArrayList<Tuple> goodMoves = cB.validMoves;
		depth++;


		cB.desc = depth+"";
		
		if (!keystoneLives(keystonelist)) return min;
		if(depth>100)return max;

		if (cB.placing != keystonecolour && validMoves.size() == 0) return max;		
		else if (cB.placing == keystonecolour && goodMoves.size() == 0) {
			goodMoves = validMoves;
//			cB.passing = true;
//			goodMoves.add(new Tuple(-9,-9));
		}
		
//		if (goodMoves.size() == 0 || cB.placing != keystonecolour ) {goodMoves = validMoves;}
		
		if (cB.placing != keystonecolour ) {goodMoves = validMoves;}
		
		//Forbidden Third 
//		for (Tuple t : cB.keystones) {
//			ArrayList<Tuple> keystring = cB.checkForStrings(t.a,t.b,keystonecolour.getSStrings(cB)); 
//			if (!keystring.isEmpty() && depth !=1 && cB.checkStringSafetyv2(keystring, keystonecolour)) return max;	
//		}
		

		if(depth==15)return 0;
		if (Play.heuristic && depth>cutoff) {
			Evaluator evaluator = new Evaluator(cB);
			return evaluator.evaluateCurrentBoard(false);
		}
		
        if(searched.contains(cB.boardString) && !goodMoves.isEmpty()) goodMoves.add(goodMoves.remove(0));
        else if(!searched.contains(cB.boardString)) searched.add(cB.boardString);
        
//      goodMoves = MoveOrdering(depth,goodMoves);
        	
//      Collections.shuffle(goodMoves);

//		print("\n\r"+line++ + "."+cB.placing+" valid moves:"+validMoves + " good moves: " + goodMoves +" \ndepth: " + depth +" step taken: " );
        

		if(isLive) {
			int best = min;
			for (Tuple t : goodMoves) {
				Board b = cB;
				b.takeTurn(t.a,t.b,false,true);  
				if(depth==1)System.out.print(t.clone()+ " :");
				int	returnscore =alphaBeta(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
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
				Board b = cB;
				b.takeTurn(t.a,t.b,false,true);
				if(depth==1)System.out.print(t.clone()+ " :");
				int returnscore =alphaBeta(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
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
	
	
	
	int alphaBeta(Board cB , ArrayList<Tuple> keystonelist , boolean isLive , int depth, int alpha,int beta ) {
		if (exit) return 0;
	 	ArrayList<Tuple> validMoves = cB.validMoves;
	 	ArrayList<Tuple> goodMoves = cB.removeBadMovess();
		depth++;
		cB.desc=depth+":" +hitcounter;
		
		if (!keystoneLives(keystonelist)) return min;
		if(depth>50)return max;
	
		if (cB.placing != keystonecolour && validMoves.size() == 0) return max;		
		else if (cB.placing == keystonecolour && goodMoves.size() == 0) goodMoves.add(new Tuple(-9,-9));
		
		
		
		if (cB.placing != keystonecolour ) {goodMoves = validMoves;}
		
	
		if (Play.heuristic && depth>cutoff) {
			Evaluator evaluator = new Evaluator(cB);
			return evaluator.evaluateCurrentBoard(false);
		}
		
	    if(searched.contains(cB.boardString) && !goodMoves.isEmpty()) goodMoves.add(goodMoves.remove(0));
	    else if(!searched.contains(cB.boardString)) searched.add(cB.boardString);
	    goodMoves = moveOrdering(depth,goodMoves);
	    goodMoves = moveGen(cB,goodMoves);
	
	    if(isLive) {
			int best = min;
			for (Tuple t : goodMoves) {
//				Board b = cB;
				Board b = Board.cloneBoard(cB);
				b.takeTurn(t.a,t.b,false,true);  
				if(depth==1)System.out.print(t.clone()+ " :");
				
				int returnscore = 0;
				if(useHashTable) {
				 	String key = b.boardToString();
					if(good.containsKey(key)) {returnscore = good.get(key);hitcounter++;}
					else {
						returnscore =alphaBeta(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
						good.put(key, returnscore);
					}
				}else returnscore =alphaBeta(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);	
//				b.undoMove(false);
				if(returnscore > best && depth==1 )this.choice = t.clone();
				best = Math.max(best, returnscore); 
				alpha = Math.max(alpha, best);
				if(depth==1) print(returnscore);
				if(beta <= alpha) {
					if(killers[depth-1][0]!=null)killers[depth-1][1]= killers[depth-1][0];
					killers[depth-1][0]=t;
					break;
				}
			}
			return best;		
	    }else{
			int best = max;
			for (Tuple t : goodMoves) {
//				Board b = cB;
				Board b = Board.cloneBoard(cB);
				b.takeTurn(t.a,t.b,false,true);
				if(depth==1)System.out.print(t.clone()+ " :");
				
				
				int returnscore = 0;
				if(useHashTable) {
				 	String key = b.boardToString();
					if(bad.containsKey(key))  {returnscore = bad.get(key);hitcounter++;}
					else {
						returnscore =alphaBeta(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
						bad.put(key, returnscore);
						
					}
				}else returnscore =alphaBeta(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
//				b.undoMove(false);
				if(returnscore < best && depth==1 )this.choice = t.clone();
				best = Math.min(best, returnscore); 
				beta = Math.min(beta, best);
				if(depth==1) print(returnscore);
				if(beta <= alpha) {
					if(killers[depth-1][0]!=null)killers[depth-1][1]= killers[depth-1][0];
					killers[depth-1][0]=t;
					break;
				}
			}
			return best;
		}
	} 


	public void run() {


		boolean iterativeDeepening =Play.iterativeDeepening;
		Board cloneBoard = Board.cloneBoard(originalBoard);
//		cloneBoard = originalBoard;
		if(!iterativeDeepening || editormode) {
			useHashTable= true;
			Evaluator e1 = new Evaluator(cloneBoard);
			e1.evaluateCurrentBoard(true);
			LearningValues.initalboard = false;
			if (learnexit) {
				result = 0;
				return;
			}
			
			if(!editormode) {
				print("Ai Started");
				print("current board :" +e1.evaluateCurrentBoard(true));
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
		
		}else {
		
			ArrayList<Tuple> iterPath =new ArrayList<Tuple>() ;
			int iterDepth = 0;
			
			while ((Play.heuristic && iterDepth < cutoff) || (!Play.heuristic && true)) {
				iterDepth++;
				IterativeDeepening iDeep = new IterativeDeepening(cloneBoard,iterDepth, iterPath,this);
				Thread t2 = new Thread(iDeep,"t2");
				t2.start();
				try {t2.join();} catch (InterruptedException e) {e.printStackTrace();}
				if(exit) {
					print(choice + ": " + result +"\n To Depth: " + (iterDepth-1) );
					return;
				}
				iterPath = iDeep.thisIterPath;
				if(!iterPath.isEmpty())choice = iterPath.get(0);
				result = iDeep.result;
				if(result == min || result == max) break;
				
			}
			
			print(choice + ": " + result +"\n To Depth: " + iterDepth );
		}

	}

	
	private ArrayList<Tuple> moveGen(Board cB, ArrayList<Tuple> goodMoves) {
		Evaluator evaluator = new Evaluator(cB);
		ArrayList<Tuple> newMoves = evaluator.moveGen(goodMoves);
		newMoves = goodMoves;
		return newMoves;
	}
	
	
	private ArrayList<Tuple> moveOrdering(int depth, ArrayList<Tuple> goodMoves) {
		ArrayList<Tuple> newOrder = new ArrayList<Tuple>();
		
		Tuple k1 = killers[depth-1][0];
		Tuple k2 = killers[depth-1][1];
		if(k1!=null && goodMoves.remove(k1)) {
			newOrder.add(k1);
		}
		
		if(k2!=null && goodMoves.remove(k2)) {
			newOrder.add(k2);
		}
		newOrder.addAll(goodMoves);
		return newOrder;
	}

	

	static public ArrayList<Tuple> liveKeys(Board b,ArrayList<Tuple> keystonelist){
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
