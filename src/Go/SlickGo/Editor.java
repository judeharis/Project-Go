package Go.SlickGo;




import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Editor extends BasicGameState {
	Board board;
	Grouping grouping;
	Image bg;
	Font font;
	TextField desc;
	boolean drawPattern;
	ArrayList<Pattern> pattern= new ArrayList<Pattern>();
	public Editor(int state ,int gcsize  ) {
		this.board = Board.cloneBoard(SlickGo.mainBoard);
		grouping = new Grouping(board);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = new Image("Images/woodenbg3.jpg");
		font = new TrueTypeFont(new java.awt.Font(java.awt.Font.MONOSPACED,java.awt.Font.BOLD , 12), false);
	    desc = new TextField(gc, font, board.boardSize , board.TileSize +460, 300, 40); 

	    desc.setBackgroundColor(Color.transparent);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		bg.draw(0, 0, (float) 0.5);
		desc.setFocus(true);
	    desc.setMaxLength(40);
		desc.render(gc, g);
		
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		int bx =  board.calulatePostionOnBoard(xpos-board.TileSize);
		int by =  board.calulatePostionOnBoard(ypos-board.TileSize);
		

		board.draw(g,true);
        if(withinBounds(bx,by)) {
        	board.drawoval(g,(bx+1)*board.TileSize,(by+1)*board.TileSize,board.placing.stoneToColor(),board.placing.isKey());}
        
        grouping.draw(g);
        if(!pattern.isEmpty() && drawPattern)pattern.get(0).draw(g,pattern,board);

		SlickGo.drawRButton(board.boardSize , board.TileSize +40 , "Place Black Stones", g, board.placing == Stone.BLACK||board.placing == Stone.KEYBLACKSTONE);
		SlickGo.drawRButton(board.boardSize , board.TileSize +80, "Place White Stones", g,board.placing == Stone.WHITE||board.placing == Stone.KEYWHITESTONE);
		SlickGo.drawRButton(board.boardSize , board.TileSize +120, "Mark Valid Spots", g,board.placing==Stone.VALID);
		SlickGo.drawRButton(board.boardSize , board.TileSize +160, "Mark Invalid Spots", g,board.placing==Stone.INVALID);
		
		SlickGo.drawRButton(board.boardSize , board.TileSize +220, "Black Plays First", g, board.blackFirst);
		SlickGo.drawRButton(board.boardSize , board.TileSize +260, "White Plays First", g, !board.blackFirst);
		
		SlickGo.drawRButton(board.boardSize , board.TileSize +320, "Keystone/s needs to die", g, board.capToWin);
		SlickGo.drawRButton(board.boardSize , board.TileSize +360, "Keystone/s needs to live", g, !board.capToWin);
		
		SlickGo.drawButton(board.boardSize ,board.TileSize +400,200,50,"Place KeyStone", g,board.placing.isKey() || SlickGo.regionChecker(board.boardSize ,board.TileSize +400,200,50,gc));
		SlickGo.drawButton(board.boardSize ,board.TileSize +560,200,50,"Load", g ,SlickGo.regionChecker(board.boardSize ,board.TileSize +560,200,50,gc));
		SlickGo.drawButton(board.boardSize +220,board.TileSize +560,200,50,"Reset", g,SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +560,200,50,gc));
		SlickGo.drawButton(board.boardSize ,board.TileSize +620,200,50,"Save", g,SlickGo.regionChecker(board.boardSize ,board.TileSize +620,200,50,gc));
		SlickGo.drawButton(board.boardSize ,board.TileSize +680,200,50,"Menu", g,SlickGo.regionChecker(board.boardSize ,board.TileSize +680,200,50,gc));
		SlickGo.drawButton(board.boardSize +220,board.TileSize +680,90,50,"Undo", g ,SlickGo.regionChecker(board.boardSize +220 ,board.TileSize +680,90,50,gc));
		SlickGo.drawButton(board.boardSize +330,board.TileSize +680,90,50,"Redo", g ,SlickGo.regionChecker(board.boardSize +330 ,board.TileSize +680,90,50,gc));
		SlickGo.drawButton(board.boardSize +220,board.TileSize +620,90,50,"Rotate", g ,SlickGo.regionChecker(board.boardSize +220 ,board.TileSize +620,90,50,gc));
		SlickGo.drawButton(board.boardSize +330,board.TileSize +620,90,50,"Flip", g ,SlickGo.regionChecker(board.boardSize +330 ,board.TileSize +620,90,50,gc));
		
		
		SlickGo.drawButton(board.boardSize  ,board.TileSize,20,20,"D", g,grouping.draw);
		SlickGo.drawButton(board.boardSize +30,board.TileSize,20,20,"W", g,grouping.drawW);
		SlickGo.drawButton(board.boardSize +60,board.TileSize,20,20,"B", g,grouping.drawB);
		SlickGo.drawButton(board.boardSize +90,board.TileSize,20,20,"C", g,grouping.drawC);
		SlickGo.drawButton(board.boardSize +120,board.TileSize,20,20,"P", g,drawPattern);



	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		int bx =  board.calulatePostionOnBoard(xpos-board.TileSize);
		int by =  board.calulatePostionOnBoard(ypos-board.TileSize);
		board.desc = desc.getText();
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
		
		if (input.isMousePressed(1)) {
			if (SlickGo.withinBounds(bx,by)) {

				print(bx+","+by);
				Stone colour = board.stones[bx][by].getSC();
				if(colour.isStone()) {
					ArrayList<Tuple> sstring = board.checkForStrings(bx,by,colour.getSStrings(board));
					print(board.checkStringSafetyv2(sstring,colour));
				}
				grouping = new Grouping(board,grouping.draw,grouping.drawW,grouping.drawB,grouping.drawC);
				grouping.allocateGrouping();

//				grouping.stonesControl= grouping.doubleIntegerArray();
//				grouping.allocateControl();
//				print(grouping.allGroups);
				
//				Board clone =  Board.cloneBoard(board);
//				VariationFinder vf = new VariationFinder(clone);
//				if(!grouping.allGroups.isEmpty()) vf.group = grouping.allGroups.get(0).group;
//				VariationFinder.searched.clear();
//		    	
//				MoveFinder.lbad.clear();
//				MoveFinder.lgood.clear();
//
//
//				vf.getAllVariationv2(clone);
//				print(vf.count);
//				print(VariationFinder.searched.size());
//				try {
//					vf.findValues(board);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				MoveFinder.lbad.clear();
//				MoveFinder.lgood.clear();


			}
			
			
		}
		if (input.isMousePressed(2)) {
			if (SlickGo.withinBounds(bx,by)) {
				print(bx+","+by);
				print(board.stones[bx][by]);
				Stone colour = board.stones[bx][by].getSC();
				if(colour.isStone()) {
					ArrayList<Tuple> sstring = board.checkForStrings(bx,by,colour.getSStrings(board));
					print(board.checkStringSafetyv2(sstring,colour));
				}
			}
			
			
		}
		
		


		pattern = Pattern.sToPv2("xrdxddXlxzldxldxdx");
		if (input.isMousePressed(0)) {
			if (SlickGo.withinBounds(bx,by)) {		
				board.takeTurn(bx,by , true,false);
				if((board.blackFirst && board.capToWin) || (!board.blackFirst && !board.capToWin))MoveFinder.keystonecolour = Stone.WHITE;
	    		else MoveFinder.keystonecolour = Stone.BLACK;
				Evaluator evaluator = new Evaluator(board);
				print(evaluator. evaluateCurrentBoard(true));
			}
		
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize,20,20,gc)) {grouping.draw = !grouping.draw;}
			if (SlickGo.regionChecker(board.boardSize +30 ,board.TileSize,20,20,gc)) {grouping.drawW = !grouping.drawW;}
			if (SlickGo.regionChecker(board.boardSize +60 ,board.TileSize,20,20,gc)) {grouping.drawB = !grouping.drawB;}
			if (SlickGo.regionChecker(board.boardSize +90 ,board.TileSize,20,20,gc)) {grouping.drawC = !grouping.drawC;}
			if (SlickGo.regionChecker(board.boardSize +120 ,board.TileSize,20,20,gc)) {drawPattern = !drawPattern;}
			
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +40,200,40,gc)) {board.placing = Stone.BLACK;}
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +80,200,40,gc)) {board.placing = Stone.WHITE;}
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +120,200,40,gc)) board.placing = Stone.VALID;
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +160,200,40,gc)) board.placing = Stone.INVALID;
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +220,200,40,gc)) board.blackFirst=true;
				
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +260,200,40,gc)) board.blackFirst=false;
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +320,250,40,gc)) board.capToWin=true;
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +360,250,40,gc)) board.capToWin=false;
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +400,200,40,gc))board.placing = board.keystone;
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +560,200,50,gc)) {
				SlickGo.loadFile(board,true);
				desc.setText(board.desc);
			}
			
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +560,200,50,gc))this.board.initBoard(true);
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +620,200,50,gc))SlickGo.saveFile(board);
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +680,200,50,gc)) {
				if((board.blackFirst && board.capToWin) || (!board.blackFirst && !board.capToWin))MoveFinder.keystonecolour = Stone.WHITE;
	    		else MoveFinder.keystonecolour = Stone.BLACK;
				board.turn = (board.blackFirst)? Stone.BLACK:Stone.WHITE;
				board.placing = board.turn;					
				SlickGo.mainBoard = Board.cloneBoard(board);
				SlickGo.mainBoard.resetboard = Board.cloneBoard(board);
				sbg.enterState(0);
			}
			
			//Undo
			if (SlickGo.regionChecker(board.boardSize +220,board.TileSize +680,90,50,gc) && board.undoBoard != null) {
				board.undoBoard.redoBoard =  Board.cloneBoard(board);
				board = Board.cloneBoard(board.undoBoard);
			}
			
			//Redo
			if (SlickGo.regionChecker(board.boardSize +330,board.TileSize +680,90,50,gc)&& board.redoBoard != null) {
				board = Board.cloneBoard(board.redoBoard);
			}
			
			
			//Rotate
			if (SlickGo.regionChecker(board.boardSize +220,board.TileSize +620,90,50,gc)) {
				board.rotate();
			}
			
			//Flip
			if (SlickGo.regionChecker(board.boardSize +330,board.TileSize +620,90,50,gc)) {
				board.flip();
			}
			
		}
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
