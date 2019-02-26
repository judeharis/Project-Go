package GoLD;


import java.util.ArrayList;
import java.util.Hashtable;





public class MoveFinder  implements Runnable{

	 static Stone keystonecolour =Stone.BLACK;
	 static int max = Integer.MAX_VALUE;
	 static int min = Integer.MIN_VALUE;
	 static int cutoff=1;
	 static int breathcutoff=10;
	 static boolean breadthcut = false;
	 static boolean editormode = false; 


	 boolean exit;
	 boolean useHashTable = false;
	 int result=0;
	
	 Board originalBoard ;
	 Tuple choice;
	 Hashtable<String, Integer> good =  new Hashtable<String,Integer>();
	 Hashtable<String, Integer> bad =  new Hashtable<String,Integer>();
	 Tuple[][] killers =  new Tuple[100][2];
	 ArrayList<String> searched = new ArrayList<String>();
	 ArrayList<Tuple> keystones;


	 MoveFinder(Board originalBoard, ArrayList<Tuple> keystones) {
		 this.originalBoard = originalBoard;
		 this.keystones = keystones;
		 this.choice = null;
	 }
	 



	
	
	int alphaBeta(Board cB , ArrayList<Tuple> keystonelist , boolean isLive , int depth, int alpha,int beta ) {
		if (exit) return 0;
	 	ArrayList<Tuple> validMoves = cB.validMoves;
	 	ArrayList<Tuple> goodMoves = cB.removeBadMovess();
		depth++;

		
		if(!keystoneLives(keystonelist)) return min;
		if(depth>50)return max;
	
		if (cB.placing != MoveFinder.keystonecolour && validMoves.size() == 0 && cB.ko==null) return max;	
		else if (cB.placing == keystonecolour && goodMoves.size() == 0) goodMoves.add(new Tuple(-9,-9));
		

		if (cB.placing != keystonecolour ) {goodMoves = validMoves;}
		
	
		if (Play.heuristic && depth>cutoff) {
			Evaluator evaluator = new Evaluator(cB);
			return evaluator.evaluateCurrentBoard(false);
		}
		
	    if(searched.contains(cB.boardString) && !goodMoves.isEmpty()) goodMoves.add(goodMoves.remove(0));
	    else if(!searched.contains(cB.boardString)) searched.add(cB.boardString);
	
//	    if(depth>3)goodMoves = moveGen(cB,goodMoves);
	    goodMoves = moveGen(cB,goodMoves);
	    goodMoves = moveOrdering(depth,goodMoves);
	
	    if(isLive) {
			int best = min;
			for (Tuple t : goodMoves) {
				Board b = Board.cloneBoard(cB);
				b.takeTurn(t.a,t.b,false,true);  
				if(depth==1)print(t.clone()+ " :");
				
				int returnscore = 0;
				if(useHashTable) {
				 	String key = b.boardToString();
					if(good.containsKey(key)) returnscore = good.get(key);
					else {
						returnscore =alphaBeta(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
						good.put(key, returnscore);
					}
				}else returnscore =alphaBeta(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);	
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
				Board b = Board.cloneBoard(cB);
				b.takeTurn(t.a,t.b,false,true);
				if(depth==1)print(t.clone()+ " :");
				
				int returnscore = 0;
				if(useHashTable) {
				 	String key = b.boardToString();
					if(bad.containsKey(key)) returnscore = bad.get(key);
					else {
						returnscore =alphaBeta(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
						bad.put(key, returnscore);
					}
				}else returnscore =alphaBeta(b,liveKeys(b,keystonelist),!isLive,depth,alpha,beta);
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

		Evaluator.timed =0;
		long startTime = System.currentTimeMillis();
		
		Board cB = Board.cloneBoard(originalBoard);
		boolean iterativeDeepening =Play.iterativeDeepening;
		boolean isLive = (cB.turn==keystonecolour);
		
		
		if(!iterativeDeepening || editormode) {
			useHashTable= false;
			Evaluator e1 = new Evaluator(cB);
			e1.evaluateCurrentBoard(true);
			
			if(!editormode) {
				print("Ai Started");
				print("current board :" +e1.evaluateCurrentBoard(true));
				result = alphaBeta(cB,keystones,isLive,0,min,max);
				print(result);
				print(choice);
				print("Ai Done");
			}else {
				result = alphaBeta(cB,keystones,isLive,0,min,max);
			}
		
		}else {
		
			ArrayList<Tuple> iterPath =new ArrayList<Tuple>() ;
			int iterDepth = 0;
			Tuple[][] ikillers =  new Tuple[100][2];
			while ((Play.heuristic && iterDepth < cutoff) || (!Play.heuristic && true)) {
				iterDepth++;
				IterativeDeepening iDeep = new IterativeDeepening(cB,iterDepth, iterPath,this,ikillers);
				Thread t2 = new Thread(iDeep,"t2");
				t2.start();
				try {t2.join();} catch (InterruptedException e) {e.printStackTrace();}
				if(exit) {
					print(choice + ": " + result +"\n To Depth: " + (iterDepth-1) );
					return;
				}

				iterPath = iDeep.thisIterPath;
				print(iterPath);
				ikillers = iDeep.killers;
				if(!iterPath.isEmpty())choice = iterPath.get(0);
				result = iDeep.result;
				if(result == min || result == max) break;
				
			}
			
			print(choice + ": " + result +"\n To Depth: " + iterDepth );
		}
		
		
		
		
		
		
		
		
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		
		long millis = duration % 1000;
		long second = (duration / 1000) % 60;
		long minute = (duration / (1000 * 60)) % 60;
		long hour = (duration / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
				
		print(time);
		print(Evaluator.timed );

	}

	
	private ArrayList<Tuple> moveGen(Board cB, ArrayList<Tuple> goodMoves) {
		if(!MoveFinder.breadthcut)return goodMoves;
		Evaluator evaluator = new Evaluator(cB);
		ArrayList<Tuple> newMoves = evaluator.moveGen(goodMoves,breathcutoff);
		return newMoves;
	}
	
	
	private ArrayList<Tuple> moveOrdering(int depth, ArrayList<Tuple> goodMoves) {
		ArrayList<Tuple> newOrder = new ArrayList<Tuple>();
		
		Tuple k1 = killers[depth-1][0];
		Tuple k2 = killers[depth-1][1];
		if(k2!=null && goodMoves.remove(k2))newOrder.add(k2);
		if(k1!=null && goodMoves.remove(k1))newOrder.add(k1);
		
		newOrder.addAll(goodMoves);
		return newOrder;
	}

	
	
	static public ArrayList<Tuple> liveKeys(Board b,ArrayList<Tuple> keystonelist){
		
//	 	ArrayList<Tuple> liveList = new ArrayList<Tuple>();
//		for (Tuple t : keystonelist){
//			if (b.stones[t.a][t.b].getSC() == keystonecolour) liveList.add(new Tuple(t.a,t.b));
//		}
//		return liveList;
		
		
	 	ArrayList<Tuple> liveList = new ArrayList<Tuple>();
	    for(int i=0; i<b.stones.length; i++) {
            for(int j=0; j<b.stones[i].length; j++) {
            	if (b.stones[i][j] == keystonecolour.getKeyStone()) liveList.add(new Tuple(i,j));
            }
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
