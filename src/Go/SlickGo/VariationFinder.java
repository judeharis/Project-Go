package Go.SlickGo;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import PatternHeuristics.LearningValues;


public class VariationFinder {
	
	Board board ;
	static ArrayList<String> searched = new ArrayList<String>();
	public static boolean skip = true;
	public static String states = "";
	public static String progstring = "";
	public static int tk = 0;
	public static int repeated = 1;
	public ArrayList<Tuple> group;
	int count = 0;

	VariationFinder(Board board ) {
		 this.board = board;
	 }
	
	
//	public void getAllVariationv1(Board board) {
//		Board clone =  Board.cloneBoard(board);
//
//		ArrayList<Tuple> validMoves = clone.getAllValidMoves();
//
//		
//		clone.placing = clone.placing.getEC();
//		clone.turn = clone.turn.getEC();
//		String cstring = clone.boardToString();
//		cstring.length();
//		
//
//
//		getOnlyEnemyFromNowv2(clone);
//     
//		
//		clone.placing = clone.placing.getEC();
//		clone.turn = clone.turn.getEC();
//		validMoves = clone.getAllValidMoves();
//		for (Tuple t : validMoves) {
//			Board b = Board.cloneBoard(clone);
//			b.takeTurn(t.a,t.b,false,true); 
//			b.placing = b.placing.getEC();
//			b.turn = b.turn.getEC();
//			getAllVariationv1(b);
//		}
//
//		
//	}
	

	public void getAllVariationv2(Board board) {	

		Board clone =  Board.cloneBoard(board);

		ArrayList<Tuple> validMoves = clone.getAllValidMoves();
		clone.placing = clone.placing.getEC();
		clone.turn = clone.turn.getEC();
		String cstring = clone.boardToString();
		cstring.length();
		getOnlyEnemyFromNowv2(clone);
		
     
		
		clone.placing = clone.placing.getEC();
		clone.turn = clone.turn.getEC();
		validMoves = clone.getAllValidMoves();

		for (Tuple t : validMoves) {
			Board b = Board.cloneBoard(clone);
			b.takeTurn(t.a,t.b,false,true); 
			b.placing = b.placing.getEC();
			b.turn = b.turn.getEC();
			getAllVariationv2(b);
		}

		
	}
	
	public void getOnlyEnemyFromNowv2(Board board) {

		board.boardString =  board.boardToString();
		skip=true;
		LearningValues.initalboard=true;
		Evaluator e1 = new Evaluator(board);
		LearningValues.evaluate(group,e1,0);
		if(!skip) {
	        if(!searched.contains(board.boardString)) searched.add(board.boardString);
	        else return;
	        count++;
	        
			Board clone =  Board.cloneBoard(board);
	
			ArrayList<Tuple> validMoves = clone.getAllValidMoves();
			for (Tuple t : validMoves) {
				Board b = Board.cloneBoard(clone);
				b.takeTurn(t.a,t.b,false,true); 
				b.placing = b.placing.getEC();
				b.turn = b.turn.getEC();
				getOnlyEnemyFromNowv2(b);
			}
		}

	}
	
	public void findValues(Board board) throws InterruptedException {
		PrintWriter writer = null;
	    try {writer = new PrintWriter("Woot.txt");}
	    catch (FileNotFoundException e1) {e1.printStackTrace();}
	    MoveFinder.editormode=true;
	    String efirst = "";
	    String nfirst = "";
	    String afirst = "";
	    

	    String q = "if (e.isThere(TL)) {";
	    String w = "}else if(e.isEnemy(TL)){";
	    String e = "}else {";
	    List<Integer> possibleResults = List.of(0,100,200,1000,1500,2000);
	    ArrayList<String> allStates= new ArrayList<String>();
		for(String s :searched) {
			Board clone = Board.cloneBoard(board);
			
			clone.stones = clone.stringToBoard(s);
			clone.refreshBoard();

			skip=true;
			MoveFinder.learnexit=false;
			LearningValues.initalboard = true;
			repeated = 1;
        	MoveFinder mf = new MoveFinder(clone,clone.keystones);
        	Thread t1 = new Thread(mf,"t1");
        	t1.start(); 
        	t1.join();
        	
        	if(!possibleResults.contains(mf.result)) {
        		print("nope");}
        	

        	int result = mf.result;
    		clone.placing = clone.placing.getEC();
    		clone.turn = clone.turn.getEC();
			
        	mf = new MoveFinder(clone,clone.keystones);
        	t1 = new Thread(mf,"t1");
        	t1.start(); 
        	t1.join();
        	
        	if(!possibleResults.contains(mf.result)) {
        		print("nope");}
        	
        	result+=mf.result;
        	if(repeated >1) print("repeated");
        	if(!skip) print(states + ": "+result/2);
        	if(!skip) {
        		if(!states.isEmpty() && (((result/2)-tk)!=0)) {
        			if(allStates.contains(states)) {
        				print("wut");
        			}
        			allStates.add(states);
        			if(states.startsWith("A"))afirst += progstring +((result/2)-tk)/repeated + ";continue;}" ;
        			if(states.startsWith("E"))efirst += progstring +((result/2)-tk)/repeated + ";continue;}" ;
        			if(states.startsWith("N"))nfirst += progstring +((result/2)-tk)/repeated + ";continue;}" ;
        		}
        	}
		}
		writer.write(q+ afirst + "\n"+ w+  efirst + "\n" +e+ nfirst +"\n}") ;
		writer.close();
	}
	
	
	
	
	
    public static void print(Object o){
        System.out.println(o);
    }
    
	 

}


