package GoLD;






import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Editor extends BasicGameState {
	Board board;

	public static String msg="";
	public static int msgtimer = 0;
	public static int msgcr=0;
	public static int msgcg=0;
	public static int msgcb=0;
	
	private static int editorx= 0;
	private static int editory= 0;

	private static int sectionx= 0;
	private static int sectiony= 0;
	
	public static Font defaultFont;
	public static TrueTypeFont ttfont;



	boolean blackKeyStone = true;




	public static TextField txt;



	public Editor(int state) {
		this.board = Board.cloneBoard(GoLD.mainBoard);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		java.awt.Font newFont = new java.awt.Font("AngelCodeFont", java.awt.Font.PLAIN, 25);
		ttfont = new TrueTypeFont(newFont, true);
		defaultFont = gc.getGraphics().getFont();


	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Menu.bg.draw(0, 0, (float) 1);





		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		Tuple posb =  board.calulatePostionOnBoard(xpos-Board.TileSize,ypos-Board.TileSize);
		int bx = posb.a;
		int by = posb.b;
		
		board.draw(g,true,100,80);
        if(Board.withinBounds(bx,by)) {
        	board.drawoval(g,(bx+1)*Board.TileSize,(by+1)*Board.TileSize,board.placing.stoneToColor(),board.placing.isKey());
        }


		fontStart(g);
		GoLD.drawButton(editorx-20 ,editory+20  ,200,50,"Back", g,GoLD.regionChecker(editorx-20 ,editory +20,200,50,gc));
		GoLD.drawBox(editorx+330 ,editory+20  ,340,50,"Editor Mode", g, false);
		fontEnd(g);
		
		





        
        
        startSection(960,60);
        startSection(40,100);
		GoLD.drawBox(editorx-20 ,editory  ,260,180,"", g,false);
		GoLD.drawRButton(editorx,editory +20 , "Place Black Stones", g, board.placing == Stone.BLACK||board.placing == Stone.KEYBLACKSTONE);
		GoLD.drawRButton(editorx,editory +60, "Place White Stones", g,board.placing == Stone.WHITE||board.placing == Stone.KEYWHITESTONE);
		GoLD.drawRButton(editorx,editory +100, "Mark Valid Points", g,board.placing==Stone.VALID);
		GoLD.drawRButton(editorx,editory +140, "Remove Stones", g,board.placing==Stone.INVALID);
		endSection();

		startSection(320,100);
		GoLD.drawBox(editorx-20 ,editory  ,260,180,"", g,false);
		GoLD.drawRButton(editorx ,editory +20, "Black KeyStone", g, blackKeyStone);
		GoLD.drawRButton(editorx ,editory +60, "White KeyStone", g, !blackKeyStone);
		GoLD.drawButton(editorx ,editory +110,200,50,"Place KeyStone", g,board.placing.isKey() || GoLD.regionChecker(editorx ,editory +110,200,50,gc));
		endSection();
		
		startSection(40,290);
		GoLD.drawBox(editorx-20 ,editory  ,540,60,"", g,false);
		GoLD.drawRButton(editorx ,editory +20, "Black Plays First", g, board.blackFirst);
		GoLD.drawRButton(editorx +280,editory +20, "White Plays First", g, !board.blackFirst);
		endSection();
		
		startSection(0,360);
		startSection(20,0);
        if(msgtimer>0) {
        	Color c = new Color(msgcr, msgcg,  msgcb, msgtimer);
        	GoLD.drawMessageBox(editorx ,editory,540,60,msg, g ,c,ttfont);
    	}else {
    		GoLD.drawMessageBox(editorx ,editory,540,60,"", g ,Color.black,ttfont);
    	}       
        g.setColor(Color.black);
        endSection();
		endSection(0,360);
		
		

		startSection(0,400);
		startSection(50,20);
		GoLD.drawButton(editorx ,editory +20,200,50,"Undo", g ,GoLD.regionChecker(editorx  ,editory +20,200,50,gc));
		GoLD.drawButton(editorx +280,editory +20,200,50,"Redo", g ,GoLD.regionChecker(editorx +280 ,editory +20,200,50,gc));
		GoLD.drawButton(editorx ,editory +80,200,50,"Flip V", g ,GoLD.regionChecker(editorx ,editory +80,200,50,gc));
		
		GoLD.drawButton(editorx +280,editory +80,200,50,"Flip H", g ,GoLD.regionChecker(editorx +280 ,editory +80,200,50,gc));
		GoLD.drawButton(editorx ,editory +140,200,50,"Rotate", g ,GoLD.regionChecker(editorx ,editory +140,200,50,gc));
		GoLD.drawButton(editorx +280,editory +140,200,50,"Reset", g,GoLD.regionChecker(editorx +280 ,editory +140,200,50,gc));
		endSection();
		endSection(0,400);


		startSection(40,600);
		fontStart(g);
		GoLD.drawButton(editorx ,editory +20,500,50,"Switch To Play Mode", g,GoLD.regionChecker(editorx,editory +20,500,50,gc));
		GoLD.drawButton(editorx ,editory +80,500,50,"Load", g ,GoLD.regionChecker(editorx ,editory +80,500,50,gc));
		GoLD.drawButton(editorx ,editory +140,500,50,"Save", g,GoLD.regionChecker(editorx ,editory +140,500,50,gc));
		fontEnd(g);
		endSection();
		endSection(960,60);

		







	}



	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		
		
		
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		Tuple posb =  board.calulatePostionOnBoard(xpos-Board.TileSize,ypos-Board.TileSize);
		int bx = posb.a;
		int by = posb.b;
		if(msgtimer>0)msgtimer--;
		board.desc = saveDesc();

		Stone currentKeystone = board.keystone;

		if(!blackKeyStone)board.keystone = Stone.KEYWHITESTONE;
		else board.keystone = Stone.KEYBLACKSTONE;
		
		if(currentKeystone != board.keystone) {
            for (Tuple k:board.keystones){
                board.stones[k.a][k.b]=board.keystone;
            }
            board.updateStringsFull();
            board.checkForCaps(board.keystone.getSC(),true);
            board.checkForCaps(board.keystone.getEC(),true);
            board.placing= board.placing.getSC();
		}





		if (input.isMousePressed(0)) {
			
			if (Board.withinBounds(bx,by)) {
				board.takeTurn(bx,by , true,false);
				board.distance = new int[19][19];
				if(!blackKeyStone)MoveFinder.keystonecolour = Stone.WHITE;
				else MoveFinder.keystonecolour = Stone.BLACK;			
			}





			
			
			
			
			//Menu
			if (GoLD.regionChecker(editorx-20,editory +20,200,50,gc)) {
				
				if(!blackKeyStone)MoveFinder.keystonecolour = Stone.WHITE;
				else MoveFinder.keystonecolour = Stone.BLACK;
				
				board.turn = (board.blackFirst)? Stone.BLACK:Stone.WHITE;
				board.placing = board.turn;
				GoLD.mainBoard = Board.cloneBoard(board);
				GoLD.mainBoard.resetboard = Board.cloneBoard(board);
				sbg.enterState(0);
			}
			
			
			
		    startSection(960,60);
		    
		    //Radio Buttons
	        startSection(40,100);
			if (GoLD.regionChecker(editorx ,editory +20,200,40,gc)) {board.placing = Stone.BLACK;}
			if (GoLD.regionChecker(editorx ,editory +60,200,40,gc)) {board.placing = Stone.WHITE;}
			if (GoLD.regionChecker(editorx ,editory +100,200,40,gc)) board.placing = Stone.VALID;
			if (GoLD.regionChecker(editorx ,editory +140,200,40,gc)) board.placing = Stone.INVALID;
			endSection();

			startSection(320,100);
			if (GoLD.regionChecker(editorx ,editory +20,250,40,gc)) blackKeyStone=true;
			if (GoLD.regionChecker(editorx ,editory +60,250,40,gc)) blackKeyStone=false;
			if (GoLD.regionChecker(editorx ,editory +110,200,50,gc))board.placing = board.keystone;
			endSection();
			
			
			startSection(40,290);
			if (GoLD.regionChecker(editorx ,editory +20,200,40,gc)) board.blackFirst=true;
			if (GoLD.regionChecker(editorx+280 ,editory +20,200,40,gc)) board.blackFirst=false;
			endSection();

			

			startSection(0,400);
			startSection(50,20);
			//Undo
			if (GoLD.regionChecker(editorx ,editory +20,200,50,gc) && board.undoBoard != null) {
				board.undoBoard.redoBoard =  Board.cloneBoard(board);
				board = Board.cloneBoard(board.undoBoard);
			}

			//Redo
			if (GoLD.regionChecker(editorx +280 ,editory +20,200,50,gc)&& board.redoBoard != null) {
				board = Board.cloneBoard(board.redoBoard);
			}

			//FlipV
			if (GoLD.regionChecker(editorx ,editory +80,200,50,gc)) {
				board.flip(true);
			}

			//FlipH
			if (GoLD.regionChecker(editorx +280,editory +80,200,50,gc)) {
				board.flip(false);
			}
			
			//Rotate
			if (GoLD.regionChecker(editorx,editory +140,200,50,gc)) {
				board.rotate();
			}

			//Reset
			if (GoLD.regionChecker(editorx +280 ,editory+140,200,50,gc))this.board.initBoard(true);
			endSection();
			endSection(0,400);



			startSection(40,600);
			//Switch Mode
			if (GoLD.regionChecker(editorx,editory +20,500,50,gc)) {
				
				Play.problemLoaded=true;
		    	if(board.getAllValidMovesEditorMode().isEmpty()) {
		    		Play.problemLoaded=false;
		    		Play.editorFailBoard=true;
		    		GoLD.msgMaker("Problem Needs Valid Points",300,false,255,0,0,null, null);
		    	}
		    	
		    	if(board.keystones.isEmpty()) {
		    		Play.problemLoaded=false;
		    		Play.editorFailBoard=true;
		    		GoLD.msgMaker("Problem Needs KeyStones",300,false,255,0,0,null, null);
		    	}
		    	
		    	if(board.bStoneStrings.isEmpty() && board.wStoneStrings.isEmpty()) {
		    		Play.problemLoaded=false;
		    		Play.editorFailBoard=true;
		    		GoLD.msgMaker("",0,false,255,0,0,null, null);
		    	}
		    	
	    		GoLD.playI.board = Board.cloneBoard(board);
	    		GoLD.playI.board.placing = board.blackFirst?Stone.BLACK:Stone.WHITE;
	    		GoLD.playI.board.turn = GoLD.playI.board.placing;
	    		GoLD.playI.board.refreshBoard();
	    		GoLD.playI.board.resetboard =  Board.cloneBoard(GoLD.playI.board);
				if(!blackKeyStone)MoveFinder.keystonecolour = Stone.WHITE;
				else MoveFinder.keystonecolour = Stone.BLACK;
	    		msgtimer=0;
	    		sbg.enterState(1);
			}
			
			//Load
			if (GoLD.regionChecker(editorx ,editory +80,500,50,gc)) {
				Board loadBoard = GoLD.loadFile(true);
				if(loadBoard!=null) board =loadBoard;

			}
			//Save
			if (GoLD.regionChecker(editorx ,editory +140,500,50,gc))GoLD.saveFile(board,true);
			endSection();

			endSection(960,60);
			


		}
	}

	private String saveDesc() {
		String s = "";
		if(board.blackFirst && !blackKeyStone )s= "Kill White";
		if(!board.blackFirst && blackKeyStone )s= "Kill Black";
		if(board.blackFirst && blackKeyStone )s= "Black To Live";
		if(!board.blackFirst && !blackKeyStone )s= "White To Live";
		return s;
	}


	private void startSection(int x, int y) {
		editorx+=x;
		editory+=y;
		sectionx=x;
		sectiony=y;
		
	}

	private void endSection() {
		editorx-=sectionx;
		editory-=sectiony;
	}
	
	private void fontStart(Graphics g) {
		g.setFont(ttfont);
	}
	private void fontEnd(Graphics g) {
		g.setFont(defaultFont);
		
	}

	
	private void endSection(int x , int y) {
		editorx-=x;
		editory-=y;
	}
	
	

	public int getID() {
		return 2;
	}




}
