package Go.SlickGo;



import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {

	Board board;
	Image bg;
	boolean gameOver=false;
	String winMsg= "";
	static String gameMsg ="";
	boolean ai = false;
	int aicounter;
	Thread t1;
	MoveFinder k;
	boolean aiStarted = false;
	boolean turnOffComputer = false;
	static boolean heuristic = true;
	static boolean iterativeDeepening = false;


	public Play(int state ,int gcsize) {
		this.board = SlickGo.mainBoard;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = new Image("Images/woodenbg3.jpg");
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		bg.draw(0, 0, (float) 0.5);
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		int bx =  board.calulatePostionOnBoard(xpos-board.TileSize);
		int by =  board.calulatePostionOnBoard(ypos-board.TileSize);
		

		board.draw(g,false);
        if(board.checkValidMove(bx,by) && board.validMoves.contains(new Tuple(bx,by))) {
        	board.drawoval(g,(bx+1)*board.TileSize,(by+1)*board.TileSize,board.placing.stoneToColor(),board.placing.isKey());}
        
        
        
        g.drawString("Turn: "+board.placing.toString(), board.boardSize, board.TileSize);
		g.drawString(board.desc, board.boardSize, board.TileSize+40);
		g.drawString(winMsg, board.boardSize, board.TileSize+80);
		SlickGo.drawButton(board.boardSize ,board.TileSize +380,420,50,gameMsg, g ,false);


		
		//SlickGo.drawButton(board.boardSize+200 ,board.TileSize,150,40,board.placing.toString(), g,false);
		
		SlickGo.drawButton(board.boardSize ,board.TileSize +240,50,50,"-", g,SlickGo.regionChecker(board.boardSize ,board.TileSize +240,50,50,gc));
		SlickGo.drawButton(board.boardSize+60 ,board.TileSize +240,50,50,"+", g,SlickGo.regionChecker(board.boardSize+60 ,board.TileSize +240,50,50,gc));
		SlickGo.drawButton(board.boardSize+120 ,board.TileSize +240,50,50,Integer.toString(MoveFinder.breathcutoff), g,false);
		SlickGo.drawButton(board.boardSize+180 ,board.TileSize +240,200,50,"Breadth Cut", g ,MoveFinder.breadthcut,Color.green);
		
		SlickGo.drawButton(board.boardSize ,board.TileSize +300,50,50,"-", g,SlickGo.regionChecker(board.boardSize ,board.TileSize +300,50,50,gc));
		SlickGo.drawButton(board.boardSize+60 ,board.TileSize +300,50,50,"+", g,SlickGo.regionChecker(board.boardSize+60 ,board.TileSize +300,50,50,gc));
		SlickGo.drawButton(board.boardSize+120 ,board.TileSize +300,50,50,Integer.toString(MoveFinder.cutoff), g,false);
		SlickGo.drawButton(board.boardSize+180 ,board.TileSize +300,200,50,"IterDeepening", g ,iterativeDeepening,Color.green);
		
		SlickGo.drawButton(board.boardSize ,board.TileSize +440,200,50,"AI " +(turnOffComputer?"Disabled":"Enabled"), g ,!turnOffComputer,Color.green);
		SlickGo.drawButton(board.boardSize +220,board.TileSize +440,200,50,"Heuristics " +(!heuristic?"Off":"On"), g ,heuristic,Color.green);
		SlickGo.drawButton(board.boardSize +220,board.TileSize +680,90,50,"Undo", g ,SlickGo.regionChecker(board.boardSize +220 ,board.TileSize +680,90,50,gc));
		SlickGo.drawButton(board.boardSize +330,board.TileSize +680,90,50,"Redo", g ,SlickGo.regionChecker(board.boardSize +330 ,board.TileSize +680,90,50,gc));
		
		SlickGo.drawButton(board.boardSize ,board.TileSize +500,200,50,"Start AI", g,SlickGo.regionChecker(board.boardSize ,board.TileSize +500,200,50,gc));
		SlickGo.drawButton(board.boardSize +220,board.TileSize +500,200,50,"Stop AI", g,SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +500,200,50,gc));
		SlickGo.drawButton(board.boardSize ,board.TileSize +560,200,50,"Load", g ,SlickGo.regionChecker(board.boardSize ,board.TileSize +560,200,50,gc));
		SlickGo.drawButton(board.boardSize +220,board.TileSize +560,200,50,"Reset", g,SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +560,200,50,gc));
		SlickGo.drawButton(board.boardSize ,board.TileSize +620,200,50,"Save", g,SlickGo.regionChecker(board.boardSize ,board.TileSize +620,200,50,gc));
		SlickGo.drawButton(board.boardSize +220,board.TileSize +620,200,50,"Pass", g,SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +620,200,50,gc));
		SlickGo.drawButton(board.boardSize ,board.TileSize +680,200,50,"Menu", g,SlickGo.regionChecker(board.boardSize ,board.TileSize +680,200,50,gc));
	

    
	}


	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		int bx =  board.calulatePostionOnBoard(xpos-board.TileSize);
		int by =  board.calulatePostionOnBoard(ypos-board.TileSize);

		
		
		if(ai) {
			ai=false;
			gameMsg= "Ai Started";
			makeComputerMove();}
		
		if (input.isMousePressed(1) && SlickGo.withinBounds(bx,by)) {
			print(bx+","+by);
			Stone colour = board.stones[bx][by].getSC();
			if(colour.isStone()) {
				ArrayList<Tuple> sstring = board.checkForStrings(bx,by,colour.getSStrings(board));
				print(board.checkStringSafetyv2(sstring,colour));
			}
		}
		
		if (input.isMousePressed(0)) {
			
			
			if (SlickGo.regionChecker(board.boardSize,board.TileSize +240,50,50,gc)&& MoveFinder.breathcutoff >1) MoveFinder.breathcutoff--;
			if (SlickGo.regionChecker(board.boardSize+60 ,board.TileSize +240,50,50,gc)) MoveFinder.breathcutoff++;
			if (SlickGo.regionChecker(board.boardSize+180,board.TileSize +240,200,50,gc)) MoveFinder.breadthcut = !MoveFinder.breadthcut;
			

			if (SlickGo.regionChecker(board.boardSize,board.TileSize +300,50,50,gc)&& MoveFinder.cutoff >1) MoveFinder.cutoff--;
			if (SlickGo.regionChecker(board.boardSize+60 ,board.TileSize +300,50,50,gc)) MoveFinder.cutoff++;
			

			if (SlickGo.regionChecker(board.boardSize+180,board.TileSize +300,200,50,gc)) iterativeDeepening = !iterativeDeepening;

			
			//Place Stone
			if (SlickGo.withinBounds(bx,by) && !aiStarted && !gameOver) {
				board.passing = false;
				gameMsg = "";
				makeMove(bx,by);}
			
			//Pass
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +620,200,50,gc) && !gameOver) {
				gameMsg = "";
				board.passing = true;
				makeMove(bx,by);}
			
//			//Undo
//			if (SlickGo.regionChecker(board.boardSize +220,board.TileSize +680,90,50,gc) && board.undoBoard != null) {
//				resetPlayScreen();
//				board.undoBoard.redoBoard =  Board.cloneBoard(board);
//				board = Board.cloneBoard(board.undoBoard);
//			}
//			
//			//Redo
//			if (SlickGo.regionChecker(board.boardSize +330,board.TileSize +680,90,50,gc)&& board.redoBoard != null) {
//				resetPlayScreen();
//				board = Board.cloneBoard(board.redoBoard);
//			}

			
			//Undo
			if (SlickGo.regionChecker(board.boardSize +220,board.TileSize +680,90,50,gc)) {
				resetPlayScreen();
				board.undoMove(true);
			}
			
			//Redo
			if (SlickGo.regionChecker(board.boardSize +330,board.TileSize +680,90,50,gc)) {
				resetPlayScreen();
				board.redoMove();
			}
			
			//Toggle AI
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +440,200,50,gc)) turnOffComputer=!turnOffComputer;
			
			//Toggle Heuristics
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +440,200,50,gc)) heuristic=!heuristic;
			
			//Start AI
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +500,200,50,gc)) ai=true;
			
			
			//Stop AI
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +500,200,50,gc) && aiStarted) {
				if (k != null)k.exit = true;
				try {t1.join();} catch (InterruptedException e) {e.printStackTrace();}
				
				if(iterativeDeepening) {
					aiStarted = false;
		        	if (k.choice != null) board.takeTurn(k.choice.a,k.choice.b , false,false);
		        	else winMsg= "AI says " + board.placing.getEC()+" Wins";
		        	afterMove();
				}
	        	gameMsg= "Ai Stopped";
				
				t1 =null;
				k = null;
			}
			
			//Load
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +560,200,50,gc)) {
				resetPlayScreen();
				SlickGo.loadFile(board,true);}
			
			//Reset
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +560,200,50,gc)) {
				board = board.resetboard;
				resetPlayScreen();
				board.resetboard = Board.cloneBoard(board);}
			


			
			
			//Save
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +620,200,50,gc))SlickGo.saveFile(board);
			
			//Menu
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +680,200,50,gc)) {
				resetPlayScreen();
				sbg.enterState(0);
			}
		}
		
    	if (t1 != null && !t1.isAlive() && aiStarted) {
    		aiStarted=false;
        	if (k.choice != null) board.takeTurn(k.choice.a,k.choice.b , false,false);
        	else winMsg= "AI says " + board.placing.getEC()+" Wins";
			gameMsg= "Ai Done";
			afterMove();
    	}

		
	}
	
	
	public void makeMove(int bx,int by) {
		boolean moveMade = board.takeTurn(bx,by,false,false);
		winMsg="";
		afterMove();
		if(!turnOffComputer && moveMade)ai=true;
	}
	
	
	public void afterMove() {
		   ArrayList<Tuple> liveList = MoveFinder.liveKeys(board,board.keystones);
	        
	        liveList = MoveFinder.liveKeys(board,board.keystones);
		    if ((liveList.isEmpty() && !board.capToWin) || (board.validMoves.isEmpty() &&board.capToWin && board.turn != MoveFinder.keystonecolour)) {
		    	winMsg= (board.blackFirst?"Black":"White") + " Lost";
		    	//gameOver=true;
		    }
		    
		    if ((liveList.isEmpty() && board.capToWin) || (board.validMoves.isEmpty() && !board.capToWin && board.turn != MoveFinder.keystonecolour)) {
		    	winMsg= (board.blackFirst?"Black":"White") + " Won";
		    	//gameOver=true;
		    }


	}
	
	public void makeComputerMove() {
		ArrayList<Tuple> liveList = MoveFinder.liveKeys(board,board.keystones);
		winMsg="";
        if (!liveList.isEmpty()) {
        	MoveFinder.editormode=false;
        	k = new MoveFinder(board,board.keystones);
        	t1 = new Thread(k,"t1");
        	t1.start(); 
        	aiStarted= true;}
	}
	
	public void resetPlayScreen() {
		if (k != null)k.exit = true;
		aiStarted = false;
		t1 =null;
		k = null;
		winMsg="";
		gameOver=false;
		gameMsg="";
	}
	
	public int getID() {
		return 1;
	}
	





	

  

    
  
    public static void print(Object o){
        System.out.println(o);
    }
    

}
