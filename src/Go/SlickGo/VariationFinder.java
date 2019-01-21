package Go.SlickGo;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import PatternHeuristics.LearningValues;
import PatternHeuristics.States;

public class VariationFinder {
	
	Board board ;
	static ArrayList<String> searched = new ArrayList<String>();
	public static boolean skip = true;
	public static String states = "";
	public static String progstring = "";
	public static int tk = 0;
	int count = 0;

	VariationFinder(Board board ) {
		 this.board = board;
	 }
	
	
	public void getAllVariationv1(Board board) {
		count++;
		
		board.boardString =  board.boardToString();
        if(!searched.contains(board.boardString)) {
        	searched.add(board.boardString);
			Evaluator evaluator = new Evaluator(board);
			evaluator. evaluateCurrentBoard(true);
        }
        
		Board clone =  Board.cloneBoard(board);
		ArrayList<Tuple> validMoves = clone.getAllValidMoves();
		for (Tuple t : validMoves) {
			Board b = Board.cloneBoard(clone);
			b.takeTurn(t.a,t.b,false,true); 
			getAllVariationv1(b);
		}
		
		
		clone.placing = (clone.placing==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
		clone.turn = (clone.turn==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
		validMoves = clone.getAllValidMoves();
		for (Tuple t : validMoves) {
			Board b = Board.cloneBoard(clone);
			b.takeTurn(t.a,t.b,false,true);  


			getAllVariationv1(b);
		}

		
	}
	
	public void getAllVariationv3(Board board) {
		count++;
		
 

		
		board.placing = (board.placing==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
		board.turn = (board.turn==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
		String cstring = board.boardToString();
		cstring.length();
		getOnlyEnemyFromNowv3(board);
		
     
		
		board.placing = (board.placing==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
		board.turn = (board.turn==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
		ArrayList<Tuple> validMoves = board.getAllValidMoves();
		for (Tuple t : validMoves) {
			Board b = board;
			b.takeTurn(t.a,t.b,false,true); 
			b.placing = (b.placing==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
			b.turn = (b.turn==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
			getAllVariationv3(b);
			b.placing = (b.placing==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
			b.turn = (b.turn==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
			b.undoMove(false);
		}

		
	}
	
	public void getOnlyEnemyFromNowv3(Board board) {
		count++;
				
		
		board.boardString =  board.boardToString();
        if(!searched.contains(board.boardString)) searched.add(board.boardString);
        else return;
        
        

		ArrayList<Tuple> validMoves = board.getAllValidMoves();
		for (Tuple t : validMoves) {
			Board b = board;
			b.takeTurn(t.a,t.b,false,true); 
			b.placing = (b.placing==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
			b.turn = (b.turn==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
			getOnlyEnemyFromNowv3(b);
			b.placing = (b.placing==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
			b.turn = (b.turn==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
			b.undoMove(false);
		}

	}
	
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
	
	public void findValues(Board board) throws InterruptedException {
		PrintWriter writer = null;
	    try {writer = new PrintWriter("Woot.txt");}
	    catch (FileNotFoundException e1) {e1.printStackTrace();}
	    MoveFinder.editormode=true;
	    String efirst = "";
	    String nfirst = "";
	    String afirst = "";
	    
	    String q = "States[] k;\nif (e.isThere(TL)) {\n";
	    String w = "}else if(e.isEnemy(TL)){\n";
	    String e = "}else {\n";
	    
		for(String s :searched) {
			Board clone = Board.cloneBoard(board);
			
			clone.stones = clone.stringToBoard(s);
			clone.refreshBoard();

			skip=true;
			MoveFinder.learnexit=false;
			LearningValues.initalboard = true;
        	MoveFinder mf = new MoveFinder(clone,clone.keystones);
        	Thread t1 = new Thread(mf,"t1");
        	t1.start(); 
        	t1.join();

        	int result = mf.result;
    		clone.placing = (clone.placing==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
    		clone.turn = (clone.turn==Stone.WHITE)?Stone.BLACK:Stone.WHITE;
			
        	mf = new MoveFinder(clone,clone.keystones);
        	t1 = new Thread(mf,"t1");
        	t1.start(); 
        	t1.join();
        	

        	result+=mf.result;
        	if(!skip)print(states + ": "+result/2);
        	if(!skip) {
        		if(!states.isEmpty() && (((result/2)-tk)!=0)) {
        			if(states.startsWith("A"))afirst += progstring +((result/2)-tk) + ";continue;}\n\r" ;
        			if(states.startsWith("E"))efirst += progstring +((result/2)-tk) + ";continue;}\n\r" ;
        			if(states.startsWith("N"))nfirst += progstring +((result/2)-tk) + ";continue;}\n\r" ;
        		}
        	}
		}
		writer.write(q+ afirst + "\n"+ w+  efirst + "\n" +e+ nfirst +"}") ;
		writer.close();
	}
	
	
	
	
	
    public static void print(Object o){
        System.out.println(o);
    }
    
	 

}


