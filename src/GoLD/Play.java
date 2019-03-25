package GoLD;



import java.util.ArrayList;


import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {




	boolean ai = false;

	Thread t1;
	MoveFinder mf;
	boolean aiStarted = false;
	int aiStartedTimer = 0;
	boolean turnOffComputer = false;
	static boolean heuristic = true;
	static boolean iterativeDeepening = true;
	static boolean problemLoaded = false;
	static boolean editorFailBoard = false;
	boolean gameOver=false;

	

	String winMsg= "";
	static String gameMsg ="";
	static String msg="";
	static int msgtimer = 0;
	static int msgcr=0;
	static int msgcg=0;
	static int msgcb=0;
	static TrueTypeFont ttfont;
	static Font defaultFont;

	


	private int playx= 0;
	private int playy= 0;
	private int sectionx= 0;
	private int sectiony= 0;



	

	
	
	Board board;
	boolean blackKeyStone = true;

	public Play(int state) {
		this.board = GoLD.mainBoard;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		java.awt.Font newfont = new java.awt.Font("AngelCodeFont", java.awt.Font.PLAIN, 25);
		ttfont = new TrueTypeFont(newfont, true);
		defaultFont = gc.getGraphics().getFont();

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		Menu.bg.draw(0, 0, (float) 1);


		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		Tuple posb =  board.calulatePostionOnBoard(xpos-Board.TileSize,ypos-Board.TileSize);
		int bx = posb.a;
		int by = posb.b;


		board.draw(g,false,100,80);
        if(board.checkValidMove(bx,by) && board.validMoves.contains(new Tuple(bx,by))) {
        	board.drawoval(g,(bx+1)*Board.TileSize,(by+1)*Board.TileSize,board.placing.stoneToColor(),board.placing.isKey());}

		fontStart(g);
		GoLD.drawButton(playx-20 ,playy+20  ,200,50,"Back", g,GoLD.regionChecker(playx-20 ,playy +20,200,50,gc));
		GoLD.drawBox(playx+330 ,playy+20  ,340,50,"Play Mode", g, false);
		fontEnd(g);

		

		
		startSection(990,100);
		fontStart(g);
		GoLD.drawString(playx ,playy,(problemLoaded?("Turn: "+board.placing.toString()):""), g);
		GoLD.drawString(playx ,playy +50,board.desc, g);
//		SlickGo.drawString(playx +300 ,playy,(problemLoaded?("Valid: "+(board.validMoves.contains(new Tuple(-9,-9))?board.validMoves.size()-1:board.validMoves.size())):""), g);
//		SlickGo.drawString(playx +300 ,playy -50,(problemLoaded?problemName:""), g);
//		SlickGo.drawString(playx +300 ,playy ,times, g);
		fontEnd(g);
		endSection();
		


		//IDeep
//		startSection(990,40);
//		SlickGo.drawButton(playx ,playy,200,50,"IterDeepening", g ,iterativeDeepening,Color.green);
//		endSection();

		
		startSection(990,220);
		GoLD.drawBox(playx-10 ,playy-20  ,540,210,"", g,false);
		startSection(10,0);
		
        //Breadth
		GoLD.drawButton(playx ,playy,200,50,"Limit Breadth", g ,MoveFinder.breadthcut,Color.green);
		GoLD.drawButton(playx+280 ,playy,100,50, Integer.toString(MoveFinder.breathcutoff), g,MoveFinder.breadthcut,Color.green);
		GoLD.drawButton(playx+400 ,playy,80,24,"+", g,GoLD.regionChecker(playx+400 ,playy,80,24,gc));
		GoLD.drawButton(playx+400 ,playy +26,80,24,"-", g,GoLD.regionChecker(playx+400 ,playy +26,80,24,gc));
		endSection();
		
		startSection(10,60);
		//Depth
		GoLD.drawButton(playx,playy,200,50,"Limit Depth", g ,heuristic,Color.green);
		GoLD.drawButton(playx+280 ,playy,100,50,Integer.toString(MoveFinder.cutoff), g ,heuristic,Color.green);
		GoLD.drawButton(playx+400 ,playy,80,24,"+", g,GoLD.regionChecker(playx+400 ,playy ,80,24,gc));
		GoLD.drawButton(playx+400 ,playy +26,80,24,"-", g,GoLD.regionChecker(playx+400 ,playy +26,80,24,gc));
		endSection();
		
		startSection(10, 120);
		//AutoPlay Switch Turn
		GoLD.drawButton(playx ,playy,200,50,"Auto Play", g ,!turnOffComputer,Color.green);
		GoLD.drawButton(playx +280,playy,200,50,"Switch Turn", g,GoLD.regionChecker(playx +280,playy,200,50,gc));
		endSection();
		

		startSection(-10,200);
        if(msgtimer>0 || editorFailBoard) {
        	Color c = new Color(msgcr, msgcg,  msgcb, editorFailBoard?250:msgtimer);
        	GoLD.drawMessageBox(playx ,playy,540,60,msg, g ,c,ttfont);
        }else if(winMsg!="" && problemLoaded) {
			Color c = new Color(0, 0, 255, 255);
        	GoLD.drawMessageBox(playx ,playy,540,60,winMsg, g ,c,ttfont);
		}else if(problemLoaded) {
        	GoLD.drawMessageBox(playx ,playy,540,60,gameMsg, g ,Color.black,ttfont);
        }else {
        	GoLD.drawMessageBox(playx ,playy,540,60,"", g ,Color.black,ttfont);
        }
        endSection();
        


		startSection(20,280);
		//Start & Stop
		GoLD.drawButton(playx ,playy,200,50,"Start", g,GoLD.regionChecker(playx ,playy,200,50,gc));
		GoLD.drawButton(playx +280,playy,200,50,"Stop", g,GoLD.regionChecker(playx+280 ,playy,200,50,gc));
		endSection();
		
		startSection(20,340);
		//Undo ,Redo,Pass, Reset
		GoLD.drawButton(playx,playy,200,50,"Undo", g ,GoLD.regionChecker(playx ,playy,200,50,gc));
		GoLD.drawButton(playx +280,playy,200,50,"Redo", g ,GoLD.regionChecker(playx +280 ,playy,200,50,gc));
		GoLD.drawButton(playx,playy +60,200,50,"Pass", g,GoLD.regionChecker(playx ,playy +60,200,50,gc));
		GoLD.drawButton(playx +280,playy +60,200,50,"Reset", g,GoLD.regionChecker(playx +280,playy +60,200,50,gc));
		endSection();
		
		startSection(10, 460);
		fontStart(g);
		GoLD.drawButton(playx ,playy,500,50,"Switch To Editor Mode", g ,GoLD.regionChecker(playx ,playy,500,50,gc));
		GoLD.drawButton(playx ,playy +60,500,50,"Load", g ,GoLD.regionChecker(playx ,playy +60,500,50,gc));
		GoLD.drawButton(playx ,playy +120,500,50,"Save", g ,GoLD.regionChecker(playx ,playy +120,500,50,gc));
		fontEnd(g);
		endSection();
		
		endSection(990,220);

	}



	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		Tuple posb =  board.calulatePostionOnBoard(xpos-Board.TileSize,ypos-Board.TileSize);
		int bx = posb.a;
		int by = posb.b;
		if(msgtimer>0)msgtimer--;
		board.desc = saveDesc();

		blackKeyStone=board.keystone.getSC()==Stone.WHITE?false:true;

		if(ai) {
			ai=false;
			gameMsg= "Searching Moves";
			makeComputerMove();
		}

		
		if(aiStarted) {
			aiStartedTimer++;
			if(aiStartedTimer>180)gameMsg= "   Searching Moves...";
			else if(aiStartedTimer>120)gameMsg= "  Searching Moves..";
			else if(aiStartedTimer>60)gameMsg= " Searching Moves.";
			else if(aiStartedTimer>0)gameMsg= "Searching Moves";
			if(aiStartedTimer>240)aiStartedTimer=0;
		}

		if (input.isMousePressed(0)) {
			msgtimer=0;
			//Place Stone
			if (Board.withinBounds(bx,by) && !gameOver && problemLoaded) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else{
					board.passing = false;
					gameMsg = "";
					makeMove(bx,by);
					afterMove();
				}
			}
			
			//Menu
			if (GoLD.regionChecker(playx -20,playy +20,200,50,gc)) {
				gameMsg="";
				deleteAI();
				sbg.enterState(0);
			}
			
//			//IDeep
//			startSection(990,40);
//			if (SlickGo.regionChecker(playx,playy,200,50,gc)) {
//				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
//				else iterativeDeepening =!iterativeDeepening;
//			}
//			endSection();
			
			startSection(990,220);
			startSection(10,0);
	        //Breadth
			if (GoLD.regionChecker(playx,playy,200,50,gc)) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else MoveFinder.breadthcut = !MoveFinder.breadthcut;
			}
			if (GoLD.regionChecker(playx+400,playy ,80,24,gc)) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else MoveFinder.breathcutoff++;
			}
			if (GoLD.regionChecker(playx+400,playy +26,80,24,gc)&& MoveFinder.breathcutoff >1) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else MoveFinder.breathcutoff--;
			}
			endSection();
			
			startSection(10,60);
			//Depth
			if (GoLD.regionChecker(playx,playy ,200,50,gc)) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else heuristic=!heuristic;
			}
			if (GoLD.regionChecker(playx+400,playy ,80,25,gc)) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else MoveFinder.cutoff++;
			}
			if (GoLD.regionChecker(playx+400,playy +26,80,25,gc)&& MoveFinder.cutoff >1) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else MoveFinder.cutoff--;
			}
			endSection();
			
			startSection(10, 120);
			//AutoPlay
			if (GoLD.regionChecker(playx,playy,200,50,gc)) turnOffComputer=!turnOffComputer;
			//Switch Turn
			if (GoLD.regionChecker(playx +280 ,playy,200,50,gc) && !gameOver  && problemLoaded) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else{

					board.turn = board.turn.getEC();
					board.placing = board.turn;
					board.validMoves = board.getAllValidMoves();
					board.removeKo();
					winMsg="";
					afterMove();
				}

			}
			endSection();
			



			startSection(20,280);
			//Start AI
			if (GoLD.regionChecker(playx ,playy,200,50,gc) && !ai && !gameOver && problemLoaded) {
				if(aiStarted) msgMaker("Searching Moves..." , 180,250,0,0);
				else ai=true;
			}
			//Stop AI
			if (GoLD.regionChecker(playx +280 ,playy,200,50,gc) && aiStarted && problemLoaded) {
				if (mf != null)mf.exit = true;
				try {if(t1!=null)t1.join();} catch (InterruptedException e) {e.printStackTrace();}
				aiStarted = false;
				aiStartedTimer=0;
				if(iterativeDeepening) {
		        	if (mf.choice != null) {
		        		board.takeTurn(mf.choice.a,mf.choice.b , false,false);
		        		if(mf.choice.a == -9 && mf.choice.b==-9)gameMsg= "Computer Passed";
		        		else gameMsg= "Computer Placed at " + Board.coord(mf.choice);
		        	}else winMsg= "Computer says " + board.placing.getEC()+" Wins";
		        	afterMove();
				}else gameMsg= "Search Stopped";

				t1 =null;
				mf = null;
			}
			endSection();
			
			startSection(20,340);
			//Undo ,Redo,Pass, Reset
			//Undo
			if (GoLD.regionChecker(playx,playy,200,50,gc)  && problemLoaded) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else{
					resetPlayScreen();
					board.undoMove(true);
					afterMove();
				}
			}

			
			//Redo
			if (GoLD.regionChecker(playx +280,playy,200,50,gc) && problemLoaded) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else{
					resetPlayScreen();
					board.redoMove();
					afterMove();
				}
			}
			//Pass
			if (GoLD.regionChecker(playx ,playy +60,200,50,gc) && !gameOver  && problemLoaded ) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else if(board.turn != board.keystone.getSC() && board.ko ==null)msgMaker("Attacking Player Can't Pass Unless in Ko" , 180,250,0,0);
				else {
					gameMsg = board.turn+" passed";
					board.passing = true;
					makeMove(bx,by);
					afterMove();
				}


			}
			//Reset
			if (GoLD.regionChecker(playx +280,playy +60,200,50,gc) && problemLoaded) {
				board = board.resetboard;
				resetPlayScreen();
				board.resetboard = Board.cloneBoard(board);
			}
			endSection();
			
			startSection(10, 460);
			//Switch Mode
			if (GoLD.regionChecker(playx,playy,500,50,gc)) {
				resetPlayScreen();
				editorFailBoard=false;
				sbg.enterState(2);
			}
			
			//Load
			if (GoLD.regionChecker(playx ,playy +60,500,50,gc)) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else{
					resetPlayScreen();
					Board loadBoard = GoLD.loadFile(false);
					if(loadBoard!=null) {
						board =loadBoard;
						problemLoaded=true;
						iterativeDeepening = true; 
						editorFailBoard=false;
					}
				}
			}
			//Save
			if (GoLD.regionChecker(playx ,playy +120,500,50,gc)) {
				if(aiStarted) msgMaker("Stop Search To Do This" , 180,250,0,0);
				else{
					GoLD.saveFile(board,false);
				}
			}

			endSection();
			
			endSection(990,220);
			

		}

    	if (t1 != null && !t1.isAlive() && aiStarted) {
    		aiStarted = false;
    		aiStartedTimer=0;
        	if (mf.choice != null) {
        		board.takeTurn(mf.choice.a,mf.choice.b , false,false);
        		if(mf.choice.a == -9 && mf.choice.b==-9)gameMsg= "Computer Passed";
        		else gameMsg= "Computer Placed at " + Board.coord(mf.choice);
        	}else winMsg= "Computer says " + board.placing.getEC()+" Wins";
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

		    if ((liveList.isEmpty())){
		    	winMsg= (MoveFinder.keystonecolour.getSC()==Stone.WHITE?"Black":"White") + " Won";
		    	gameOver=true;
		    }else if (board.validMoves.isEmpty() && board.turn != MoveFinder.keystonecolour){
		    	winMsg= (MoveFinder.keystonecolour.getSC()==Stone.WHITE?"Black":"White") + " Lost";
		    	gameOver=true;
		    }


	}


	public void makeComputerMove() {
		ArrayList<Tuple> liveList = MoveFinder.liveKeys(board,board.keystones);
		winMsg="";
        if (!liveList.isEmpty()) {
        	MoveFinder.editormode=false;
        	mf = new MoveFinder(board,board.keystones);
        	t1 = new Thread(mf,"t1");
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
		if (mf != null)mf.exit = true;
		aiStarted = false;
		aiStartedTimer=0;
		t1 =null;
		mf = null;
	}
	private String saveDesc() {
		String s = "";
		if(board.blackFirst && !blackKeyStone )s= "Kill White";
		if(!board.blackFirst && blackKeyStone )s= "Kill Black";
		if(board.blackFirst && blackKeyStone )s= "Black To Live";
		if(!board.blackFirst && !blackKeyStone )s= "White To Live";
		if(!problemLoaded)s="";
		return s;
	}




     private void msgMaker(String msg , int msgtimer,int r,int g,int b ) {

		Play.msg = msg;
		Play.msgtimer = msgtimer;
		msgcr = r;
		msgcg = g;
		msgcb = b;

    }
     
 	private void startSection(int x, int y) {
		playx+=x;
		playy+=y;
		sectionx=x;
		sectiony=y;
		
	}

	private void endSection() {
		playx-=sectionx;
		playy-=sectiony;
	}
	
	private void endSection(int x , int y) {
		playx-=x;
		playy-=y;
	}
	

	private void fontStart(Graphics g) {
		g.setFont(ttfont);
	}
	private void fontEnd(Graphics g) {
		g.setFont(defaultFont);
	}




	public int getID() {
		return 1;
	}







}
