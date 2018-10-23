package Go.SlickGo;


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
	Image bg;
	Font font;
	TextField desc;
	
	public Editor(int state ,int gcsize) {
		this.board = new Board();
		this.board.initBoard(true);
		
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
        	board.drawoval(g,(bx+1)*board.TileSize,(by+1)*board.TileSize,Stones.stoneToColor(board.placing),Stones.isKey(board.placing));}
        
        

		SlickGo.drawRButton(board.boardSize , board.TileSize +40 , "Place Black Stones", g, board.placing == Stones.BLACK||board.placing == Stones.KEYBLACKSTONE);
		SlickGo.drawRButton(board.boardSize , board.TileSize +80, "Place White Stones", g,board.placing == Stones.WHITE||board.placing == Stones.KEYWHITESTONE);
		SlickGo.drawRButton(board.boardSize , board.TileSize +120, "Mark Valid Spots", g,board.placing==Stones.VALID);
		SlickGo.drawRButton(board.boardSize , board.TileSize +160, "Mark Invalid Spots", g,board.placing==Stones.INVALID);
		
		SlickGo.drawRButton(board.boardSize , board.TileSize +220, "Black Plays First", g, board.blackFirst);
		SlickGo.drawRButton(board.boardSize , board.TileSize +260, "White Plays First", g, !board.blackFirst);
		
		SlickGo.drawRButton(board.boardSize , board.TileSize +320, "Keystone/s needs to die", g, board.capToWin);
		SlickGo.drawRButton(board.boardSize , board.TileSize +360, "Keystone/s needs to live", g, !board.capToWin);
		
		SlickGo.drawButton(board.boardSize ,board.TileSize +400,200,50,"Place KeyStone", g,Stones.isKey(board.placing) || SlickGo.regionChecker(board.boardSize ,board.TileSize +400,200,50,gc));
		SlickGo.drawButton(board.boardSize ,board.TileSize +560,200,50,"Load", g ,SlickGo.regionChecker(board.boardSize ,board.TileSize +560,200,50,gc));
		SlickGo.drawButton(board.boardSize +220,board.TileSize +560,200,50,"Reset", g,SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +560,200,50,gc));
		SlickGo.drawButton(board.boardSize ,board.TileSize +620,200,50,"Save", g,SlickGo.regionChecker(board.boardSize ,board.TileSize +620,200,50,gc));
		SlickGo.drawButton(board.boardSize ,board.TileSize +680,200,50,"Menu", g,SlickGo.regionChecker(board.boardSize ,board.TileSize +680,200,50,gc));
	


	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		int bx =  board.calulatePostionOnBoard(xpos-board.TileSize);
		int by =  board.calulatePostionOnBoard(ypos-board.TileSize);
		board.desc = desc.getText();
		if((board.blackFirst && board.capToWin) || (!board.blackFirst && !board.capToWin))board.keystone = Stones.KEYWHITESTONE;
		else board.keystone = Stones.KEYBLACKSTONE;
		
		if (input.isMousePressed(0)) {
			if (SlickGo.withinBounds(bx,by)) if (!board.computer) board.takeTurn(bx,by , true,false);
		

			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +40,200,40,gc)) {board.placing = Stones.BLACK;}
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +80,200,40,gc)) {board.placing = Stones.WHITE;}
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +120,200,40,gc)) board.placing = Stones.VALID;
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +160,200,40,gc)) board.placing = Stones.INVALID;
			
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
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +680,200,50,gc))sbg.enterState(0);
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
