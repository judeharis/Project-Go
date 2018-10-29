package Go.SlickGo;



import java.util.ArrayList;

import org.lwjgl.input.Mouse;
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
	Minimaxer k;
	boolean aiStarted = false;


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
        if(board.checkValidMove(bx,by)) {
        	board.drawoval(g,(bx+1)*board.TileSize,(by+1)*board.TileSize,board.placing.stoneToColor(),board.placing.isKey());}
        
        
        
        g.drawString("Turn: "+board.placing.toString(), board.boardSize, board.TileSize);
		g.drawString(board.desc, board.boardSize, board.TileSize+40);
		g.drawString(winMsg, board.boardSize, board.TileSize+80);
		SlickGo.drawButton(board.boardSize ,board.TileSize +440,420,50,gameMsg, g ,false);


		
		//SlickGo.drawButton(board.boardSize+200 ,board.TileSize,150,40,board.placing.toString(), g,false);
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
		
		if(ai)aicounter++;
		if(aicounter==60) {	
			aicounter=0;
			ai=false;
			makeComputerMove();

		}
		
		if (input.isMousePressed(0)) {
			if (SlickGo.withinBounds(bx,by) && !board.computer && !gameOver) {
				board.passing = false;
				board.takeTurn(bx,by , false,false);
				if(board.computer) {
					ai=true;
					gameMsg= "Ai Started";}}

			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +500,200,50,gc)) {
				board.computer = true;
				ai=true;
				gameMsg= "Ai Started";
			}
			
			
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +500,200,50,gc) && aiStarted) {
				if (k != null)k.exit = true;
				aiStarted = false;
				t1 =null;
				k = null;
				board.computer = false;
				gameMsg= "Ai Stopped";
			}
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +560,200,50,gc)) {
				resetPlayScreen();
				SlickGo.loadFile(board,true);}
			
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +560,200,50,gc)) {
				board = board.resetboard;
				resetPlayScreen();
				board.resetboard = Board.cloneBoard(board);}
			
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +620,200,50,gc) && !gameOver) {
				board.passing = true;
				board.takeTurn(bx,by,false,false);
				if(board.computer) {
					ai=true;
					gameMsg= "Ai Started";}}

			
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +620,200,50,gc))SlickGo.saveFile(board);
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +680,200,50,gc))sbg.enterState(0);
		}
		
    	if (t1 != null && !t1.isAlive() && aiStarted) {
    		aiStarted=false;
        	if (k.choice != null) board.takeTurn(k.choice.a,k.choice.b , false,false);
        	else {
        		winMsg= "AI says " + board.placing.getEnemyColour()+" Wins";
        		board.computer=false;}
        	
			gameMsg= "Ai Done";
			afterMove();
    	}

		
	}
	
	public void afterMove() {
		   ArrayList<Tuple> liveList = Minimaxer.keyStoneRemaining(board,board.keystones);
	        
	        liveList = Minimaxer.keyStoneRemaining(board,board.keystones);
		    if ((liveList.isEmpty() && !board.capToWin) || (board.validMoves.isEmpty() &&board.capToWin && board.turn != Minimaxer.keystonecolour)) {
		    	winMsg= (board.blackFirst?"Black":"White") + " Lost";
		    	//gameOver=true;
		    }
		    
		    if ((liveList.isEmpty() && board.capToWin) || (board.validMoves.isEmpty() && !board.capToWin && board.turn != Minimaxer.keystonecolour)) {
		    	winMsg= (board.blackFirst?"Black":"White") + " Won";
		    	//gameOver=true;
		    }


	}
	
	public void makeComputerMove() {
		 ArrayList<Tuple> liveList = Minimaxer.keyStoneRemaining(board,board.keystones);

	        if (!liveList.isEmpty()) {
	        	k = new Minimaxer(board,board.keystones);
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
