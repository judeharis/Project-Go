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

public class Editor extends BasicGameState {
	Board board;
	Grouping grouping;
	Image bg;
	public static String msg="";
	public static int msgtimer = 0;
	public static int msgcr=0;
	public static int msgcg=0;
	public static int msgcb=0;

	static TrueTypeFont ttfont;
	
	boolean drawPattern;
	ArrayList<Pattern> pattern= new ArrayList<Pattern>();
	

	
	
	
	public Editor(int state) {
		this.board = Board.cloneBoard(SlickGo.mainBoard);
		grouping = new Grouping(board);
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
		

		board.draw(g,true);
        if(withinBounds(bx,by)) {
        	board.drawoval(g,(bx+1)*Board.TileSize,(by+1)*Board.TileSize,board.placing.stoneToColor(),board.placing.isKey());}
        
        grouping.draw(g);
        if(!pattern.isEmpty() && drawPattern)pattern.get(0).draw(g,pattern,board);
        

        if(msgtimer>0) {
        	Color c = new Color(msgcr, msgcg,  msgcb, msgtimer);
        	SlickGo.drawMessageBox(Board.boardSize ,Board.TileSize +500,420,50,msg, g ,c,ttfont);
    	}else {
    		SlickGo.drawMessageBox(Board.boardSize ,Board.TileSize +500,420,50,"", g ,Color.black,ttfont);
    	}
        
        g.setColor(Color.black);
		SlickGo.drawRButton(Board.boardSize , Board.TileSize +40 , "Place Black Stones", g, board.placing == Stone.BLACK||board.placing == Stone.KEYBLACKSTONE);
		SlickGo.drawRButton(Board.boardSize , Board.TileSize +80, "Place White Stones", g,board.placing == Stone.WHITE||board.placing == Stone.KEYWHITESTONE);
		SlickGo.drawRButton(Board.boardSize , Board.TileSize +120, "Mark Valid Spots", g,board.placing==Stone.VALID);
		SlickGo.drawRButton(Board.boardSize , Board.TileSize +160, "Mark Invalid Spots", g,board.placing==Stone.INVALID);
		
		SlickGo.drawRButton(Board.boardSize , Board.TileSize +220, "Black Plays First", g, board.blackFirst);
		SlickGo.drawRButton(Board.boardSize , Board.TileSize +260, "White Plays First", g, !board.blackFirst);
		
		SlickGo.drawRButton(Board.boardSize , Board.TileSize +320, "Keystone/s needs to die", g, board.capToWin);
		SlickGo.drawRButton(Board.boardSize , Board.TileSize +360, "Keystone/s needs to live", g, !board.capToWin);
		
		SlickGo.drawButton(Board.boardSize ,Board.TileSize +400,200,50,"Place KeyStone", g,board.placing.isKey() || SlickGo.regionChecker(Board.boardSize ,Board.TileSize +400,200,50,gc));
		
		

		SlickGo.drawButton(Board.boardSize ,Board.TileSize +560,90,50,"Undo", g ,SlickGo.regionChecker(Board.boardSize  ,Board.TileSize +560,90,50,gc));
		SlickGo.drawButton(Board.boardSize +110,Board.TileSize +560,90,50,"Redo", g ,SlickGo.regionChecker(Board.boardSize +110 ,Board.TileSize +560,90,50,gc));
		SlickGo.drawButton(Board.boardSize +220,Board.TileSize +560,90,50,"Rotate", g ,SlickGo.regionChecker(Board.boardSize +220 ,Board.TileSize +560,90,50,gc));
		SlickGo.drawButton(Board.boardSize +330,Board.TileSize +560,90,24,"Flip V", g ,SlickGo.regionChecker(Board.boardSize +330 ,Board.TileSize +560,90,24,gc));
		SlickGo.drawButton(Board.boardSize +330,Board.TileSize +586,90,24,"Flip H", g ,SlickGo.regionChecker(Board.boardSize +330 ,Board.TileSize +586,90,24,gc));
		

		
		SlickGo.drawButton(Board.boardSize ,Board.TileSize +620,200,50,"Save", g,SlickGo.regionChecker(Board.boardSize ,Board.TileSize +620,200,50,gc));
		
		SlickGo.drawButton(Board.boardSize +220,Board.TileSize +620,200,50,"Reset", g,SlickGo.regionChecker(Board.boardSize+220 ,Board.TileSize +620,200,50,gc));
		
		SlickGo.drawButton(Board.boardSize ,Board.TileSize +680,200,50,"Load", g ,SlickGo.regionChecker(Board.boardSize ,Board.TileSize +680,200,50,gc));
		
		SlickGo.drawButton(Board.boardSize +220,Board.TileSize +680,200,50,"Menu", g,SlickGo.regionChecker(Board.boardSize +220,Board.TileSize +680,200,50,gc));

		
		
		

	
		
//		SlickGo.drawButton(Board.boardSize  ,Board.TileSize,20,20,"D", g,grouping.draw);
//		SlickGo.drawButton(Board.boardSize +30,Board.TileSize,20,20,"W", g,grouping.drawW);
//		SlickGo.drawButton(Board.boardSize +60,Board.TileSize,20,20,"B", g,grouping.drawB);
//		SlickGo.drawButton(Board.boardSize +90,Board.TileSize,20,20,"C", g,grouping.drawC);
//		SlickGo.drawButton(Board.boardSize +120,Board.TileSize,20,20,"P", g,drawPattern);



	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
 
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		int bx =  board.calulatePostionOnBoard(xpos-Board.TileSize);
		int by =  board.calulatePostionOnBoard(ypos-Board.TileSize);
		if(msgtimer>0)msgtimer--;
		board.desc = saveDesc();
		

		
		
		
		
		
		
		
		Stone currentKeystone = board.keystone;
		if((board.blackFirst && board.capToWin) || (!board.blackFirst && !board.capToWin))board.keystone = Stone.KEYWHITESTONE;
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
		
		
//		if (input.isMousePressed(1)) {
//			if (SlickGo.withinBounds(bx,by)) {
//
//				print(bx+","+by);
//				Stone colour = board.stones[bx][by].getSC();
//				if(colour.isStone()) {
//					ArrayList<Tuple> sstring = board.checkForStrings(bx,by,colour.getSStrings(board));
//					print(board.checkStringSafetyv2(sstring,colour));
//				}
//				grouping = new Grouping(board,grouping.draw,grouping.drawW,grouping.drawB,grouping.drawC);
//				grouping.allocateGrouping();
//			}
//		}
//		if (input.isMousePressed(2)) {
//			if (SlickGo.withinBounds(bx,by)) {
//				print(bx+","+by);
//				print(board.stones[bx][by]);
//				Stone colour = board.stones[bx][by].getSC();
//				if(colour.isStone()) {
//					ArrayList<Tuple> sstring = board.checkForStrings(bx,by,colour.getSStrings(board));
//					print(board.checkStringSafetyv2(sstring,colour));
//				}
//			}	
//		}
		
		
		if((input.isKeyPressed(Input.KEY_ESCAPE)) && gc.isFullscreen() ) {
			try {SlickGo.goFullScreen();
			} catch (LWJGLException e) {e.printStackTrace();}
		}


		pattern = Pattern.sToPv2("xlddr#zldxdx");
		if (input.isMousePressed(0)) {
			if (SlickGo.withinBounds(bx,by)) {		
				board.takeTurn(bx,by , true,false);
				if((board.blackFirst && board.capToWin) || (!board.blackFirst && !board.capToWin))MoveFinder.keystonecolour = Stone.WHITE;
	    		else MoveFinder.keystonecolour = Stone.BLACK;
				Evaluator evaluator = new Evaluator(board);
				print(evaluator. evaluateCurrentBoard(true));
			}
		
//			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize,20,20,gc)) {grouping.draw = !grouping.draw;}
//			if (SlickGo.regionChecker(Board.boardSize +30 ,Board.TileSize,20,20,gc)) {grouping.drawW = !grouping.drawW;}
//			if (SlickGo.regionChecker(Board.boardSize +60 ,Board.TileSize,20,20,gc)) {grouping.drawB = !grouping.drawB;}
//			if (SlickGo.regionChecker(Board.boardSize +90 ,Board.TileSize,20,20,gc)) {grouping.drawC = !grouping.drawC;}
//			if (SlickGo.regionChecker(Board.boardSize +120 ,Board.TileSize,20,20,gc)) {drawPattern = !drawPattern;}
			
			
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +40,200,40,gc)) {board.placing = Stone.BLACK;}
			
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +80,200,40,gc)) {board.placing = Stone.WHITE;}
			
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +120,200,40,gc)) board.placing = Stone.VALID;
			
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +160,200,40,gc)) board.placing = Stone.INVALID;
			
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +220,200,40,gc)) board.blackFirst=true;
				
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +260,200,40,gc)) board.blackFirst=false;
			
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +320,250,40,gc)) board.capToWin=true;
			
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +360,250,40,gc)) board.capToWin=false;
			
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +400,200,40,gc))board.placing = board.keystone;
			
			

			
			
			//Undo
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +560,90,50,gc) && board.undoBoard != null) {
				board.undoBoard.redoBoard =  Board.cloneBoard(board);
				board = Board.cloneBoard(board.undoBoard);
			}
			
			//Redo
			if (SlickGo.regionChecker(Board.boardSize +110 ,Board.TileSize +560,90,50,gc)&& board.redoBoard != null) {
				board = Board.cloneBoard(board.redoBoard);
			}
			
			
			//Rotate
			if (SlickGo.regionChecker(Board.boardSize +220,Board.TileSize +560,90,50,gc)) {
				board.rotate();
			}
			
			//FlipV
			if (SlickGo.regionChecker(Board.boardSize +330,Board.TileSize +560,90,24,gc)) {
				board.flip(true);
			}
			
			//FlipH
			if (SlickGo.regionChecker(Board.boardSize +330,Board.TileSize +586,90,24,gc)) {
				board.flip(false);
			}
			
			
			
			//Save
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +620,200,50,gc))SlickGo.saveFile(board,true);
			
			//Reset
			if (SlickGo.regionChecker(Board.boardSize+220 ,Board.TileSize +620,200,50,gc))this.board.initBoard(true);
			
			//Load
			if (SlickGo.regionChecker(Board.boardSize ,Board.TileSize +680,200,50,gc)) {
				Board loadBoard = SlickGo.loadFile(true);
				if(loadBoard!=null) board =loadBoard;

			}
			
			
			
			//Menu
			if (SlickGo.regionChecker(Board.boardSize +220,Board.TileSize +680,200,50,gc)) {
				if((board.blackFirst && board.capToWin) || (!board.blackFirst && !board.capToWin))MoveFinder.keystonecolour = Stone.WHITE;
	    		else MoveFinder.keystonecolour = Stone.BLACK;
				board.turn = (board.blackFirst)? Stone.BLACK:Stone.WHITE;
				board.placing = board.turn;					
				SlickGo.mainBoard = Board.cloneBoard(board);
				SlickGo.mainBoard.resetboard = Board.cloneBoard(board);
				sbg.enterState(0);
			}
			

			
			
		}
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

		return 2;
	}
   
	
	public boolean withinBounds(int x, int y){
    	if(x <= 18 && x>=0 && y <= 18 && y>=0) return true;
    	return false;
    	
    }
	


    public static void print(Object o){
        System.out.println(o);
    }

}
