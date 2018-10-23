package Go.SlickGo;



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


	public Play(int state ,int gcsize) {
		this.board = new Board();
		this.board.initBoard(false);
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
        	board.drawoval(g,(bx+1)*board.TileSize,(by+1)*board.TileSize,Stones.stoneToColor(board.placing),Stones.isKey(board.placing));}
        
        
        g.drawString("Turn: "+board.placing.toString(), board.boardSize, board.TileSize);
		g.drawString(board.desc, board.boardSize, board.TileSize+40);
		g.drawString(board.winMsg, board.boardSize, board.TileSize+80);
		
		//SlickGo.drawButton(board.boardSize+200 ,board.TileSize,150,40,board.placing.toString(), g,false);
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
		 
		if (input.isMousePressed(0)) {
			if (SlickGo.withinBounds(bx,by)) if (!board.computer) board.takeTurn(bx,by , false,false);
		
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +560,200,50,gc))SlickGo.loadFile(board,true);
			
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +560,200,50,gc)) {
				board = board.resetboard;
				board.resetboard = Board.cloneBoard(board);}
			
			if (SlickGo.regionChecker(board.boardSize+220 ,board.TileSize +620,200,50,gc)) {
				board.passing = true;
				board.takeTurn(bx,by,false,false);}
			
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +620,200,50,gc))SlickGo.saveFile(board);
			
			if (SlickGo.regionChecker(board.boardSize ,board.TileSize +680,200,50,gc))sbg.enterState(0);
		}
		
	}

	public int getID() {
		return 1;
	}
	





	

  

    
  
    public static void print(Object o){
        System.out.println(o);
    }
    

}
