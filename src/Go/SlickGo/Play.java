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
	static boolean problemLoaded = false;
	

	public static String msg="";
	public static int msgtimer = 0;
	public static int msgcr=0;
	public static int msgcg=0;
	public static int msgcb=0;



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
        
        
        
        if(problemLoaded)g.drawString("Turn: "+board.placing.toString(), board.boardSize, board.TileSize);
		g.drawString(board.desc, board.boardSize, board.TileSize+40);

		
        if(msgtimer>0) {
        	Color c = new Color(msgcr, msgcg,  msgcb, msgtimer);
        	SlickGo.drawMessageBox(board.boardSize ,board.TileSize +380,420,50,msg, g ,c);
        }else if(winMsg!="" && problemLoaded) {
			Color c = new Color(0, 0, 255, 255);
        	SlickGo.drawMessageBox(board.boardSize ,board.TileSize +380,420,50,winMsg, g ,c);
		}else if(problemLoaded) {
        	SlickGo.drawMessageBox(board.boardSize ,board.TileSize +380,420,50,gameMsg, g ,Color.black);
        }else {
        	SlickGo.drawMessageBox(board.boardSize ,board.TileSize +380,420,50,"", g ,Color.black);
        }



		
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
		if(msgtimer>0)msgtimer--;
		if(problemLoaded)board.desc = saveDesc();
		
		
		if(ai) {
			ai=false;
			gameMsg= "AI Started";
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
			msgtimer=0;
			


			if(problemLoaded) {
				//Place Stone
				if (SlickGo.withinBounds(bx,by) && !aiStarted && !gameOver) {
					board.passing = false;
					gameMsg = "";
					makeMove(bx,by);
				}
				
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
		        	gameMsg= "AI Stopped";
					
					t1 =null;
					k = null;
				}
			}
			
			
			//Reset
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +560,200,50,gc)) {
				board = board.resetboard;
				resetPlayScreen();
				board.resetboard = Board.cloneBoard(board);
			}
			
			
			//Toggle AI
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +440,200,50,gc)) turnOffComputer=!turnOffComputer;
			
			//Toggle Heuristics
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +440,200,50,gc)) heuristic=!heuristic;
			
			
			//Depth & Breadth
			if (SlickGo.regionChecker(board.boardSize,board.TileSize +240,50,50,gc)&& MoveFinder.breathcutoff >1) MoveFinder.breathcutoff--;
			if (SlickGo.regionChecker(board.boardSize+60 ,board.TileSize +240,50,50,gc)) MoveFinder.breathcutoff++;
			if (SlickGo.regionChecker(board.boardSize+180,board.TileSize +240,200,50,gc)) MoveFinder.breadthcut = !MoveFinder.breadthcut;
			if (SlickGo.regionChecker(board.boardSize,board.TileSize +300,50,50,gc)&& MoveFinder.cutoff >1) MoveFinder.cutoff--;
			if (SlickGo.regionChecker(board.boardSize+60 ,board.TileSize +300,50,50,gc)) MoveFinder.cutoff++;
			
			//IDeep
			if (SlickGo.regionChecker(board.boardSize+180,board.TileSize +300,200,50,gc)) iterativeDeepening = !iterativeDeepening;
			

			
			//Load
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +560,200,50,gc)) {
				resetPlayScreen();
				Board loadBoard = SlickGo.loadFile(false);
				if(loadBoard!=null) {
					board =loadBoard;
					problemLoaded=true;
				}
			}
			

			
			
			//Save
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +620,200,50,gc))SlickGo.saveFile(board,false);
			
			//Menu
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +680,200,50,gc)) {
				resetPlayScreen();
				problemLoaded=false;
				board.desc = "No Problem Loaded";
				msg="";
				sbg.enterState(0);
			}
		}
		
    	if (t1 != null && !t1.isAlive() && aiStarted) {
    		aiStarted=false;
        	if (k.choice != null) board.takeTurn(k.choice.a,k.choice.b , false,false);
        	else winMsg= "AI says " + board.placing.getEC()+" Wins";
			gameMsg= "AI Done";
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
	
	private String saveDesc() {
		String s = "";
		if(board.blackFirst && board.capToWin )s= "Kill White";
		if(!board.blackFirst && board.capToWin )s= "Kill Black";
		if(board.blackFirst && !board.capToWin )s= "Black To Live";
		if(!board.blackFirst && !board.capToWin )s= "White To Live";
		return s;
	}
	
	
	public int getID() {
		return 1;
	}
	





	

  

    
  
    public static void print(Object o){
        System.out.println(o);
    }
    

}
