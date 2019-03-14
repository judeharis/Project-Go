package GoLD;


import java.util.ArrayList;
import java.util.Hashtable;




public class IterativeDeepening  implements Runnable{
	 Board originalBoard ;


	 static int max = Integer.MAX_VALUE;
	 static int min = Integer.MIN_VALUE;

	 int toDepth;
	 boolean useHashTable = false;
	 
	 Hashtable<String, Integer> good =  new Hashtable<String,Integer>();
	 Hashtable<String, Integer> bad =  new Hashtable<String,Integer>();

	 Tuple[][] killers =  new Tuple[100][2];
	 
	 ArrayList<Tuple> lastIterPath;
	 ArrayList<Tuple> thisIterPath;
	 int result;
	 MoveFinder mf;



	 IterativeDeepening(Board originalBoard, int toDepth,ArrayList<Tuple> lastIterPath,MoveFinder mf,Tuple[][]  ikillers) {
		 this.originalBoard = originalBoard;
		 this.toDepth = toDepth;
		 this.lastIterPath = lastIterPath;
		 this.mf = mf;
		 this.killers = ikillers;
	 }
	 




	
	int alphaBeta(Board cB,ArrayList<Tuple> klist, boolean isLive , int depth, int alpha,int beta, ArrayList<Tuple> soFar ) {
		if (mf.exit) return 0;
		
		depth++;
	 	ArrayList<Tuple> validMoves = cB.validMoves;
	 	ArrayList<Tuple> goodMoves = cB.removeBadMovess();


		
		if(!keystoneLives(klist)) return min;
		if(depth>50)return max;
	
		if (cB.placing != MoveFinder.keystonecolour && validMoves.size() == 0 && cB.ko==null) return max;		
		else if (cB.placing == MoveFinder.keystonecolour && goodMoves.size() == 0) goodMoves.add(new Tuple(-9,-9));
		
		
		if (cB.placing != MoveFinder.keystonecolour ) {goodMoves = validMoves;}
		
	
		if (depth>toDepth) {
			Evaluator evaluator = new Evaluator(cB);
			return evaluator.evaluateCurrentBoard(false);
		}
		
//	    if(depth>3)goodMoves = moveGen(cB,goodMoves);
	    goodMoves = moveGen(cB,goodMoves);
	    goodMoves = moveOrdering(depth,goodMoves);
	
	    if(isLive) {
			int best = min;
			ArrayList<Tuple> bestPath = new ArrayList<Tuple>(); 
			Tuple bestChoice =null;
			for (Tuple t : goodMoves) {
				ArrayList<Tuple> path = new ArrayList<Tuple>();
				path.add(t);
				Board b = Board.cloneBoard(cB);
				
				if(depth==1)print(t.clone()+ " :");
				
				b.takeTurn(t.a,t.b,false,true);  
				int returnscore = 0;
				if(useHashTable) {
				 	String key = b.boardToString();
					if(good.containsKey(key)) returnscore = good.get(key);
					else {
						returnscore = alphaBeta(b,MoveFinder.liveKeys(b,klist),!isLive,depth,alpha,beta,path);
						good.put(key, returnscore);
					}
				}else returnscore = alphaBeta(b,MoveFinder.liveKeys(b,klist),!isLive,depth,alpha,beta,path);
				if(returnscore > best) {
					bestChoice=t.clone();
					bestPath =path;
				}
				
				if(depth==1) println(returnscore);
				
				best = Math.max(best, returnscore); 
				alpha = Math.max(alpha, best);
				if(beta <= alpha) {
					if(killers[depth-1][0]!=null)killers[depth-1][1]= killers[depth-1][0];
					killers[depth-1][0]=t;
					break;
				}
			}
//			if(bestChoice!=null)soFar.add(0,bestChoice);
			if(bestChoice!=null)soFar.addAll(bestPath);
			return best;		
	    }else{
			int best = max;
			ArrayList<Tuple> bestPath = new ArrayList<Tuple>(); 
			Tuple bestChoice =null;
			for (Tuple t : goodMoves) {
				ArrayList<Tuple> path = new ArrayList<Tuple>();
				path.add(t);
				Board b = Board.cloneBoard(cB);
				
				if(depth==1)print(t.clone()+ " :");
				
				b.takeTurn(t.a,t.b,false,true);
				int returnscore = 0;
				if(useHashTable) {
				 	String key = b.boardToString();
					if(bad.containsKey(key)) returnscore = bad.get(key);
					else {
						returnscore = alphaBeta(b,MoveFinder.liveKeys(b,klist),!isLive,depth,alpha,beta,path);
						bad.put(key, returnscore);
					}
				}else returnscore = alphaBeta(b,MoveFinder.liveKeys(b,klist),!isLive,depth,alpha,beta,path);
				if(returnscore < best) {
					bestChoice=t.clone();
					bestPath =path;
				}
				
				if(depth==1) println(returnscore);
				
				best = Math.min(best, returnscore); 
				beta = Math.min(beta, best);
				if(beta <= alpha) {
					if(killers[depth-1][0]!=null)killers[depth-1][1]= killers[depth-1][0];
					killers[depth-1][0]=t;
					break;
				}
			}
//			if(bestChoice!=null)soFar.add(0,bestChoice);
			if(bestChoice!=null)soFar.addAll(bestPath);
			return best;
		}
	} 
	 





	public void run() {


		Board cB = Board.cloneBoard(originalBoard);
		
		useHashTable= false;
		Evaluator e1 = new Evaluator(cB);
		e1.evaluateCurrentBoard(false);
		boolean isLive = (cB.turn==MoveFinder.keystonecolour);


		
		ArrayList<Tuple> thisIter = new ArrayList<Tuple>();
		addToKillers();
		println("Started toDepth" + toDepth);
		
		result = alphaBeta(cB,cB.keystones, isLive,0,min,max,thisIter);

		println("Stopped toDepth" + toDepth);
		this.thisIterPath=thisIter;

	}

	
	private ArrayList<Tuple> moveGen(Board cB, ArrayList<Tuple> goodMoves) {
		if(!MoveFinder.breadthcut)return goodMoves;
		Evaluator evaluator = new Evaluator(cB);
		ArrayList<Tuple> newMoves = evaluator.moveGen(goodMoves,MoveFinder.breathcutoff);

		return newMoves;
	}
	
	private void addToKillers() {
		int kdepth = 0;
		for(Tuple t: lastIterPath) killers[kdepth++][0]=t;
		
	}
	
	
	private ArrayList<Tuple> moveOrdering(int depth, ArrayList<Tuple> goodMoves) {
		ArrayList<Tuple> newOrder = new ArrayList<Tuple>();
		Tuple k1 = killers[depth-1][0];
		Tuple k2 = killers[depth-1][1];
		if(k1!=null && goodMoves.remove(k1))newOrder.add(k1);
		if(k2!=null && goodMoves.remove(k2))newOrder.add(k2);
		newOrder.addAll(goodMoves);
		return newOrder;
	}

	



	static public boolean keystoneLives(ArrayList<Tuple> keystonelist){
	 	if(!keystonelist.isEmpty()) return true;
	 	return false;
	}

    
	  
    public static void println(Object o){
//        System.out.println(o);
    }
    public static void print(Object o){
//        System.out.print(o);
    }
   
}
