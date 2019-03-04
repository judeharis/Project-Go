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
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Editor extends BasicGameState {
	Board board;
	Grouping grouping;
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


	boolean drawPattern;
	boolean blackKeyStone = true;
	ArrayList<Pattern> pattern= new ArrayList<Pattern>();

	boolean trigon =false;
	Tuple trigwhich =null;
	public static char chars;

	public static TextField txt;



	public Editor(int state) {
		this.board = Board.cloneBoard(SlickGo.mainBoard);
		grouping = new Grouping(board);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		java.awt.Font newFont = new java.awt.Font("AngelCodeFont", java.awt.Font.PLAIN, 25);
		ttfont = new TrueTypeFont(newFont, true);
		defaultFont = gc.getGraphics().getFont();
		txt = new TextField(gc, defaultFont, 0, 0, 100, 100);


	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Menu.bg.draw(0, 0, (float) 0.5);
		txt.render(gc, g);
		txt.setLocation(1000, 20);




		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		Tuple posb =  board.calulatePostionOnBoard(xpos-Board.TileSize,ypos-Board.TileSize);
		int bx = posb.a;
		int by = posb.b;



		fontStart(g);
		SlickGo.drawButton(editorx-20 ,editory+20  ,200,50,"Back", g,SlickGo.regionChecker(editorx-20 ,editory +20,200,50,gc));
		SlickGo.drawBox(editorx+330 ,editory+20  ,340,50,"Editor Mode", g, false);
		fontEnd(g);
		
		
		board.draw(g,true,100,80);
        if(withinBounds(bx,by)) {
        	board.drawoval(g,(bx+1)*Board.TileSize,(by+1)*Board.TileSize,board.placing.stoneToColor(),board.placing.isKey());
        }

        grouping.draw(g);
        if(!pattern.isEmpty() && drawPattern)pattern.get(0).draw(g,pattern,board);


        
        
        startSection(960,60);
        startSection(40,100);
		SlickGo.drawBox(editorx-20 ,editory  ,260,180,"", g,false);
		SlickGo.drawRButton(editorx,editory +20 , "Place Black Stones", g, board.placing == Stone.BLACK||board.placing == Stone.KEYBLACKSTONE);
		SlickGo.drawRButton(editorx,editory +60, "Place White Stones", g,board.placing == Stone.WHITE||board.placing == Stone.KEYWHITESTONE);
		SlickGo.drawRButton(editorx,editory +100, "Mark Valid Points", g,board.placing==Stone.VALID);
		SlickGo.drawRButton(editorx,editory +140, "Remove Stones", g,board.placing==Stone.INVALID);
		endSection();

		startSection(320,100);
		SlickGo.drawBox(editorx-20 ,editory  ,260,180,"", g,false);
		SlickGo.drawRButton(editorx ,editory +20, "Black KeyStone", g, blackKeyStone);
		SlickGo.drawRButton(editorx ,editory +60, "White KeyStone", g, !blackKeyStone);
		SlickGo.drawButton(editorx ,editory +110,200,50,"Place KeyStone", g,board.placing.isKey() || SlickGo.regionChecker(editorx ,editory +110,200,50,gc));
		endSection();
		
		startSection(40,290);
		SlickGo.drawBox(editorx-20 ,editory  ,540,60,"", g,false);
		SlickGo.drawRButton(editorx ,editory +20, "Black Plays First", g, board.blackFirst);
		SlickGo.drawRButton(editorx +280,editory +20, "White Plays First", g, !board.blackFirst);
		endSection();
		
		startSection(0,360);
		startSection(20,0);
        if(msgtimer>0) {
        	Color c = new Color(msgcr, msgcg,  msgcb, msgtimer);
        	SlickGo.drawMessageBox(editorx ,editory,540,60,msg, g ,c,ttfont);
    	}else {
    		SlickGo.drawMessageBox(editorx ,editory,540,60,"", g ,Color.black,ttfont);
    	}       
        g.setColor(Color.black);
        endSection();
		endSection(0,360);
		
		

		startSection(0,400);
		startSection(50,20);
//		SlickGo.drawBox(editorx-20 ,editory  ,540,210,"", g,false);
		SlickGo.drawButton(editorx ,editory +20,200,50,"Undo", g ,SlickGo.regionChecker(editorx  ,editory +20,200,50,gc));
		SlickGo.drawButton(editorx +280,editory +20,200,50,"Redo", g ,SlickGo.regionChecker(editorx +280 ,editory +20,200,50,gc));
		SlickGo.drawButton(editorx ,editory +80,200,50,"Flip V", g ,SlickGo.regionChecker(editorx ,editory +80,200,50,gc));
		
		SlickGo.drawButton(editorx +280,editory +80,200,50,"Flip H", g ,SlickGo.regionChecker(editorx +280 ,editory +80,200,50,gc));
		SlickGo.drawButton(editorx ,editory +140,200,50,"Rotate", g ,SlickGo.regionChecker(editorx ,editory +140,200,50,gc));
		SlickGo.drawButton(editorx +280,editory +140,200,50,"Reset", g,SlickGo.regionChecker(editorx +280 ,editory +140,200,50,gc));
		endSection();
		endSection(0,400);


		startSection(40,600);
//		SlickGo.drawBox(editorx-20 ,editory  ,540,220,"", g,false);
		fontStart(g);
		SlickGo.drawButton(editorx ,editory +20,500,50,"Switch To Play Mode", g,SlickGo.regionChecker(editorx,editory +20,500,50,gc));
		SlickGo.drawButton(editorx ,editory +80,500,50,"Load", g ,SlickGo.regionChecker(editorx ,editory +80,500,50,gc));
		SlickGo.drawButton(editorx ,editory +140,500,50,"Save", g,SlickGo.regionChecker(editorx ,editory +140,500,50,gc));
		fontEnd(g);
		endSection();
		endSection(960,60);

		





		startSection(970,50);
		SlickGo.drawButton(editorx  ,editory,20,20,"D", g,grouping.draw);
		SlickGo.drawButton(editorx +30,editory,20,20,"W", g,grouping.drawW);
		SlickGo.drawButton(editorx +60,editory,20,20,"B", g,grouping.drawB);
		SlickGo.drawButton(editorx +90,editory,20,20,"C", g,grouping.drawC);
		SlickGo.drawButton(editorx +120,editory,20,20,"P", g,drawPattern);		
		endSection();



	}



	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		pattern = Pattern.sToPv2("xrxldxdxlxdxrr#");
		
		
		
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


		if (input.isMousePressed(1)) {
			if (SlickGo.withinBounds(bx,by)) {

				print(bx+","+by);
//				Stone colour = board.stones[bx][by].getSC();
//				if(colour.isStone()) {
//					ArrayList<Tuple> sstring = board.checkForStrings(bx,by,colour.getSStrings(board));
//					print(board.checkStringSafetyv2(sstring,colour));
//				}
//				grouping = new Grouping(board,grouping.draw,grouping.drawW,grouping.drawB,grouping.drawC);
//				grouping.allocateGrouping();
//				grouping.allocateControl();
			}
		}

		
		if (input.isMousePressed(2)) {
			if (SlickGo.withinBounds(bx,by) && input.isKeyDown(Input.KEY_LSHIFT)) {
				trigon=true;
				trigwhich = new Tuple(bx,by);
//				print(bx+","+by);
//				print(board.stones[bx][by]);
//				Stone colour = board.stones[bx][by].getSC();
//				if(colour.isStone()) {
//					ArrayList<Tuple> sstring = board.checkForStrings(bx,by,colour.getSStrings(board));
//					print(board.checkStringSafetyv2(sstring,colour));
//				}
			}
		}

		if(trigon && trigwhich!=null) {
			board.chars[trigwhich.a][trigwhich.b] = txt.getText().isEmpty()?' ':txt.getText().charAt(0);
			trigon=false;
		}



		if (input.isMousePressed(0)) {
			
			if (SlickGo.withinBounds(bx,by)) {
				board.takeTurn(bx,by , true,false);
				board.distance = new int[19][19];
				if(!blackKeyStone)MoveFinder.keystonecolour = Stone.WHITE;
				else MoveFinder.keystonecolour = Stone.BLACK;			
				Evaluator evaluator = new Evaluator(board);
				print(evaluator.evaluateCurrentBoard(true));
				Evaluator evaluator2 = new Evaluator(board);
				evaluator2.moveGen(board.removeBadMovess(), MoveFinder.breathcutoff);
			}



			startSection(970,50);
			if (SlickGo.regionChecker(editorx ,editory,20,20,gc)) {grouping.draw = !grouping.draw;}
			if (SlickGo.regionChecker(editorx +30 ,editory,20,20,gc)) {grouping.drawW = !grouping.drawW;}
			if (SlickGo.regionChecker(editorx +60 ,editory,20,20,gc)) {grouping.drawB = !grouping.drawB;}
			if (SlickGo.regionChecker(editorx +90 ,editory,20,20,gc)) {grouping.drawC = !grouping.drawC;}
			if (SlickGo.regionChecker(editorx +120 ,editory,20,20,gc)) {drawPattern = !drawPattern;}
			endSection();

			
			
			
			
			//Menu
			if (SlickGo.regionChecker(editorx-20,editory +20,200,50,gc)) {
				
				if(!blackKeyStone)MoveFinder.keystonecolour = Stone.WHITE;
				else MoveFinder.keystonecolour = Stone.BLACK;
				
				board.turn = (board.blackFirst)? Stone.BLACK:Stone.WHITE;
				board.placing = board.turn;
				SlickGo.mainBoard = Board.cloneBoard(board);
				SlickGo.mainBoard.resetboard = Board.cloneBoard(board);
				sbg.enterState(0);
			}
			
			
			
		    startSection(960,60);
		    
	        startSection(40,100);
			if (SlickGo.regionChecker(editorx ,editory +20,200,40,gc)) {board.placing = Stone.BLACK;}
			if (SlickGo.regionChecker(editorx ,editory +60,200,40,gc)) {board.placing = Stone.WHITE;}
			if (SlickGo.regionChecker(editorx ,editory +100,200,40,gc)) board.placing = Stone.VALID;
			if (SlickGo.regionChecker(editorx ,editory +140,200,40,gc)) board.placing = Stone.INVALID;
			endSection();

			startSection(320,100);
			if (SlickGo.regionChecker(editorx ,editory +20,250,40,gc)) blackKeyStone=true;
			if (SlickGo.regionChecker(editorx ,editory +60,250,40,gc)) blackKeyStone=false;
			if (SlickGo.regionChecker(editorx ,editory +110,200,50,gc))board.placing = board.keystone;
			endSection();
			
			
			startSection(40,290);
			if (SlickGo.regionChecker(editorx ,editory +20,200,40,gc)) board.blackFirst=true;
			if (SlickGo.regionChecker(editorx+280 ,editory +20,200,40,gc)) board.blackFirst=false;
			endSection();


			startSection(0,400);
			startSection(50,20);
			//Undo
			if (SlickGo.regionChecker(editorx ,editory +20,200,50,gc) && board.undoBoard != null) {
				board.undoBoard.redoBoard =  Board.cloneBoard(board);
				board = Board.cloneBoard(board.undoBoard);
			}

			//Redo
			if (SlickGo.regionChecker(editorx +280 ,editory +20,200,50,gc)&& board.redoBoard != null) {
				board = Board.cloneBoard(board.redoBoard);
			}

			//FlipV
			if (SlickGo.regionChecker(editorx ,editory +80,200,50,gc)) {
				board.flip(true);
			}

			//FlipH
			if (SlickGo.regionChecker(editorx +280,editory +80,200,50,gc)) {
				board.flip(false);
			}
			
			//Rotate
			if (SlickGo.regionChecker(editorx,editory +140,200,50,gc)) {
				board.rotate();
			}

			//Reset
			if (SlickGo.regionChecker(editorx +280 ,editory+140,200,50,gc))this.board.initBoard(true);
			endSection();
			endSection(0,400);



			startSection(40,600);
			//Switch Mode
			if (SlickGo.regionChecker(editorx,editory +20,500,50,gc)) {
				
				Play.problemLoaded=true;
		    	if(board.getAllValidMovesEditorMode().isEmpty()) {
		    		Play.problemLoaded=false;
		    		Play.editorFailBoard=true;
		    		SlickGo.msgMaker("Problem Needs Valid Points",300,false,255,0,0,null, null);
		    	}
		    	
		    	if(board.keystones.isEmpty()) {
		    		Play.problemLoaded=false;
		    		Play.editorFailBoard=true;
		    		SlickGo.msgMaker("Problem Needs KeyStones",300,false,255,0,0,null, null);
		    	}
		    	
		    	if(board.bStoneStrings.isEmpty() && board.wStoneStrings.isEmpty()) {
		    		Play.problemLoaded=false;
		    		Play.editorFailBoard=true;
		    		SlickGo.msgMaker("",0,false,255,0,0,null, null);
		    	}
		    	
	    		SlickGo.playI.board = Board.cloneBoard(board);
	    		SlickGo.playI.board.placing = board.blackFirst?Stone.BLACK:Stone.WHITE;
	    		SlickGo.playI.board.turn = SlickGo.playI.board.placing;
	    		SlickGo.playI.board.refreshBoard();
	    		SlickGo.playI.board.resetboard =  Board.cloneBoard(SlickGo.playI.board);
				if(!blackKeyStone)MoveFinder.keystonecolour = Stone.WHITE;
				else MoveFinder.keystonecolour = Stone.BLACK;
	    		msgtimer=0;
	    		sbg.enterState(1);
			}
			
			//Load
			if (SlickGo.regionChecker(editorx ,editory +80,500,50,gc)) {
				Board loadBoard = SlickGo.loadFile(true);
				if(loadBoard!=null) board =loadBoard;

			}
			//Save
			if (SlickGo.regionChecker(editorx ,editory +140,500,50,gc))SlickGo.saveFile(board,true);
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

	public int getID() {

		return 2;
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
	
	
	public boolean withinBounds(int x, int y){
    	if(x <= 18 && x>=0 && y <= 18 && y>=0) return true;
    	return false;

    }



    public static void print(Object o){
        System.out.println(o);
    }

}
