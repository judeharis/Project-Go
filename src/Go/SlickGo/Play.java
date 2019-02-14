package Go.SlickGo;



import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
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
	static boolean iterativeDeepening = true;
	static boolean problemLoaded = false;
	

	public static String msg="";
	public static String problemName="";
	public static int msgtimer = 0;
	public static int msgcr=0;
	public static int msgcg=0;
	public static int msgcb=0;


	static TrueTypeFont ttfont;

	public Play(int state) {
		this.board = SlickGo.mainBoard;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = new Image("Images/woodenbg3.jpg");
		java.awt.Font newfont = new java.awt.Font("AngelCodeFont", java.awt.Font.PLAIN, 25);
		ttfont = new TrueTypeFont(newfont, true);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		bg.draw(0, 0, (float) 0.5);
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		int bx =  board.calulatePostionOnBoard(xpos-Board.TileSize);
		int by =  board.calulatePostionOnBoard(ypos-Board.TileSize);
		

		board.draw(g,false);
        if(board.checkValidMove(bx,by) && board.validMoves.contains(new Tuple(bx,by))) {
        	board.drawoval(g,(bx+1)*Board.TileSize,(by+1)*Board.TileSize,board.placing.stoneToColor(),board.placing.isKey());}
        
        
        

        
        

    	float hratio = (float) ((SlickGo.gcheigth*1.0)/800);
    	float wratio = (float) ((SlickGo.gcwidth*1.0)/1400);
        if(problemLoaded)g.drawString("Turn: "+board.placing.toString(), (Board.boardSize+200)*wratio, Board.TileSize*hratio);
		g.drawString((problemLoaded?problemName:"") +board.desc, (Board.boardSize+200)*wratio, (Board.TileSize+40)*hratio);

		
        if(msgtimer>0) {
        	Color c = new Color(msgcr, msgcg,  msgcb, msgtimer);
        	SlickGo.drawMessageBox(Board.boardSize ,Board.TileSize +320,420,50,msg, g ,c,ttfont);
        }else if(winMsg!="" && problemLoaded) {
			Color c = new Color(0, 0, 255, 255);
        	SlickGo.drawMessageBox(Board.boardSize ,Board.TileSize +320,420,50,winMsg, g ,c,ttfont);
		}else if(problemLoaded) {
        	SlickGo.drawMessageBox(Board.boardSize ,Board.TileSize +320,420,50,gameMsg, g ,Color.black,ttfont);
        }else {
        	SlickGo.drawMessageBox(Board.boardSize ,Board.TileSize +320,420,50,"", g ,Color.black,ttfont);
        }


		//SlickGo.drawButton(board.boardSize+200 ,board.TileSize,150,40,board.placing.toString(), g,false);
		
        
		
		
		
//		//IDeep
//		SlickGo.drawButton(Board.boardSize+220 ,Board.TileSize +160,200,50,"IterDeepening", g ,iterativeDeepening,Color.green);
		
		
        //Breadth
		SlickGo.drawButton(Board.boardSize+275 ,Board.TileSize +380,50,24,"+", g,SlickGo.regionChecker(Board.boardSize+275 ,Board.TileSize +380,50,24,gc));
		SlickGo.drawButton(Board.boardSize+275 ,Board.TileSize +406,50,24,"-", g,SlickGo.regionChecker(Board.boardSize+275 ,Board.TileSize +406,50,24,gc));
		SlickGo.drawButton(Board.boardSize+220 ,Board.TileSize +380,50,50, Integer.toString(MoveFinder.breathcutoff), g,false);
		SlickGo.drawButton(Board.boardSize ,Board.TileSize +380,200,50,"Limit Breadth", g ,MoveFinder.breadthcut,Color.green);
		
		
		//Depth
		SlickGo.drawButton(Board.boardSize+275 ,Board.TileSize +440,50,24,"+", g,SlickGo.regionChecker(Board.boardSize+275 ,Board.TileSize +440,50,24,gc));
		SlickGo.drawButton(Board.boardSize+275 ,Board.TileSize +466,50,24,"-", g,SlickGo.regionChecker(Board.boardSize+275 ,Board.TileSize +466,50,24,gc));
		SlickGo.drawButton(Board.boardSize+220 ,Board.TileSize +440,50,50,Integer.toString(MoveFinder.cutoff), g,false);		
		SlickGo.drawButton(Board.boardSize,Board.TileSize +440,200,50,"Limit Depth", g ,heuristic,Color.green);
		
		
		
		

		



		
		

		

		

		

		SlickGo.drawButton(Board.boardSize,Board.TileSize +500,90,50,"Undo", g ,SlickGo.regionChecker(Board.boardSize ,Board.TileSize +500,90,50,gc));
		SlickGo.drawButton(Board.boardSize +110,Board.TileSize +500,90,50,"Redo", g ,SlickGo.regionChecker(Board.boardSize +110 ,Board.TileSize +500,90,50,gc));
		SlickGo.drawButton(Board.boardSize +220,Board.TileSize +500,200,50,"Pass", g,SlickGo.regionChecker(Board.boardSize+220 ,Board.TileSize +500,200,50,gc));
		

		
		SlickGo.drawButton(Board.boardSize ,Board.TileSize +560,200,50,"Start AI", g,SlickGo.regionChecker(Board.boardSize ,Board.TileSize +560,200,50,gc));
		SlickGo.drawButton(Board.boardSize +220,Board.TileSize +560,200,50,"Stop AI", g,SlickGo.regionChecker(Board.boardSize+220 ,Board.TileSize +560,200,50,gc));

		SlickGo.drawButton(Board.boardSize ,Board.TileSize +620,200,50,"Auto AI", g ,!turnOffComputer,Color.green);
		SlickGo.drawButton(Board.boardSize +220,Board.TileSize +620,200,50,"Reset", g,SlickGo.regionChecker(Board.boardSize +220,Board.TileSize +620,200,50,gc));
		SlickGo.drawButton(Board.boardSize ,Board.TileSize +680,200,50,"Load", g ,SlickGo.regionChecker(Board.boardSize ,Board.TileSize +680,200,50,gc));
		SlickGo.drawButton(Board.boardSize +220,Board.TileSize +680,200,50,"Menu", g,SlickGo.regionChecker(Board.boardSize +220 ,Board.TileSize +680,200,50,gc));
	

		
		//removed
//		SlickGo.drawButton(board.boardSize ,board.TileSize +620,200,50,"Save", g,SlickGo.regionChecker(board.boardSize ,board.TileSize +620,200,50,gc));
//		SlickGo.drawButton(board.boardSize +220,board.TileSize +440,200,50,"Heuristics " +(!heuristic?"Off":"On"), g ,heuristic,Color.green);

	}


	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		int bx =  board.calulatePostionOnBoard(xpos-Board.TileSize);
		int by =  board.calulatePostionOnBoard(ypos-Board.TileSize);
		if(msgtimer>0)msgtimer--;
		if(problemLoaded)board.desc = saveDesc();
		
		

		if(ai) {
			ai=false;
			gameMsg= "AI Running";
			makeComputerMove();}
		
		if (input.isMousePressed(1) && SlickGo.withinBounds(bx,by)) {
			print(bx+","+by);
			Stone colour = board.stones[bx][by].getSC();
			if(colour.isStone()) {
				ArrayList<Tuple> sstring = board.checkForStrings(bx,by,colour.getSStrings(board));
				print(board.checkStringSafetyv2(sstring,colour));
			}
		}
		
		if((input.isKeyPressed(Input.KEY_ESCAPE)) && gc.isFullscreen() ) {
			try {SlickGo.goFullScreen();
			} catch (LWJGLException e) {e.printStackTrace();}
		}
		
		if (input.isMousePressed(0)) {
			msgtimer=0;
			


			if(problemLoaded) {
				//Place Stone
				if (SlickGo.withinBounds(bx,by) && !gameOver) {
					if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
					else{
						board.passing = false;
						gameMsg = "";
						makeMove(bx,by);
						afterMove();
					}
				}

				//Undo
				if (SlickGo.regionChecker(Board.boardSize,Board.TileSize +500,90,50,gc)) {
					if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
					else{
						resetPlayScreen();
						board.undoMove(true);
						afterMove();
					}
				}
				
				//Redo
				if (SlickGo.regionChecker(Board.boardSize +110,Board.TileSize +500,90,50,gc)) {
					if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
					else{
						resetPlayScreen();
						board.redoMove();
						afterMove();
					}
				}
				
				//Pass
				if (SlickGo.regionChecker(Board.boardSize+220 ,Board.TileSize +500,200,50,gc) && !gameOver) {
					if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
					else{
						gameMsg = board.turn+" passed";
						board.passing = true;
						makeMove(bx,by);
						afterMove();
					}

				}
				

				
				
				//Start AI
				if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +560,200,50,gc) && !ai && !gameOver) {
					if(aiStarted) msgMaker("AI Is Running" , 180,250,0,0);
					else ai=true;
				}
				
				
				//Stop AI
				if (SlickGo.regionChecker(Board.boardSize+220 ,Board.TileSize +560,200,50,gc) && aiStarted) {
					if (k != null)k.exit = true;
					try {if(t1!=null)t1.join();} catch (InterruptedException e) {e.printStackTrace();}
					aiStarted = false;
					if(iterativeDeepening) {
			        	if (k.choice != null) {
			        		board.takeTurn(k.choice.a,k.choice.b , false,false);
			        		if(k.choice.a == -9 && k.choice.b==-9)gameMsg= "AI Passed";
			        		else gameMsg= "AI Placed at " + Board.coord(k.choice);
			        	}else winMsg= "AI says " + board.placing.getEC()+" Wins";
			        	afterMove();
					}else gameMsg= "AI Stopped";
		        			
					t1 =null;
					k = null;
				}
			}
			
			

			

			
			

			//Breadth
			if (SlickGo.regionChecker(Board.boardSize+275,Board.TileSize +380,50,24,gc)) {
				if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
				else MoveFinder.breathcutoff++;
			}
			if (SlickGo.regionChecker(Board.boardSize+275,Board.TileSize +406,50,24,gc)&& MoveFinder.breathcutoff >1) {
				if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
				else MoveFinder.breathcutoff--;
			}
			if (SlickGo.regionChecker(Board.boardSize,Board.TileSize +380,200,50,gc)) {
				if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
				else MoveFinder.breadthcut = !MoveFinder.breadthcut;
			}
			
			//Depth
			if (SlickGo.regionChecker(Board.boardSize+275,Board.TileSize +440,50,25,gc)) {
				if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
				else MoveFinder.cutoff++;
			}
			if (SlickGo.regionChecker(Board.boardSize+275,Board.TileSize +466,50,25,gc)&& MoveFinder.cutoff >1) {
				if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
				else MoveFinder.cutoff--;
			}
			if (SlickGo.regionChecker(Board.boardSize,Board.TileSize +440,200,50,gc)) {
				if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
				else heuristic=!heuristic;
			}
			
//			//IDeep
//			if (SlickGo.regionChecker(Board.boardSize+220,Board.TileSize +160,200,50,gc)) {
//				if(aiStarted) msgMaker("Stop AI First" , 180,250,0,0);
//				else iterativeDeepening = !iterativeDeepening;
//			}
		

			
			
			
			//Toggle AI
			if (SlickGo.regionChecker(Board.boardSize,Board.TileSize +620,200,50,gc)) turnOffComputer=!turnOffComputer;
			
			//Reset
			if (SlickGo.regionChecker(Board.boardSize +220,Board.TileSize +620,200,50,gc)) {
				board = board.resetboard;
				resetPlayScreen();
				board.resetboard = Board.cloneBoard(board);
			}
			

			
			
			//Load
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +680,200,50,gc)) {
				resetPlayScreen();
				Board loadBoard = SlickGo.loadFile(false);
				if(loadBoard!=null) {
					board =loadBoard;
					problemLoaded=true;
				}
			}
			
	
			//Menu
			if (SlickGo.regionChecker(Board.boardSize +220,Board.TileSize +680,200,50,gc)) {
//				resetPlayScreen();
//				problemLoaded=false;
//				board.desc = "No Problem Loaded";
				gameMsg="";
				deleteAI();
				sbg.enterState(0);
			}
			
			
//			//Save
//			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +620,200,50,gc))SlickGo.saveFile(board,false);
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
			
		}
		
    	if (t1 != null && !t1.isAlive() && aiStarted) {
    		aiStarted=false;
        	if (k.choice != null) {
        		board.takeTurn(k.choice.a,k.choice.b , false,false);
        		if(k.choice.a == -9 && k.choice.b==-9)gameMsg= "AI Passed";
        		else gameMsg= "AI Placed at " + Board.coord(k.choice);
        	}else winMsg= "AI says " + board.placing.getEC()+" Wins";
//			gameMsg= "AI Done";
			afterMove();
    	}

		
	}
	
	
	public void makeMove(int bx,int by) {
		boolean moveMade = board.takeTurn(bx,by,false,false);
		winMsg="";
		afterMove();
		if(!turnOffComputer && moveMade && !gameOver)ai=true;
	}
	
	
	public void afterMove() {
		   ArrayList<Tuple> liveList = MoveFinder.liveKeys(board,board.keystones);
	        
	        liveList = MoveFinder.liveKeys(board,board.keystones);
		    if ((liveList.isEmpty() && !board.capToWin) || (board.validMoves.isEmpty() &&board.capToWin && board.turn != MoveFinder.keystonecolour)) {
		    	winMsg= (board.blackFirst?"Black":"White") + " Lost";
		    	gameOver=true;
		    }
		    
		    if ((liveList.isEmpty() && board.capToWin) || (board.validMoves.isEmpty() && !board.capToWin && board.turn != MoveFinder.keystonecolour)) {
		    	winMsg= (board.blackFirst?"Black":"White") + " Won";
		    	gameOver=true;
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
		deleteAI();
		winMsg="";
		gameOver=false;
		gameMsg="";
	}
	
	
	public void deleteAI() {
		if (k != null)k.exit = true;
		aiStarted = false;
		t1 =null;
		k = null;
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
	
	
     private void msgMaker(String msg , int msgtimer,int r,int g,int b ) {

		Play.msg = msg;
		Play.msgtimer = msgtimer;
		msgcr = r;
		msgcg = g;
		msgcb = b;

    }
 






	

  

    
  
    public static void print(Object o){
        System.out.println(o);
    }
    

}
