package GoLD;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {
	
	int gcsize;
	String mouse ="Nothing";
	Image bg;
	
	public Menu(int state ) {

	}


	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = new Image("Images/woodenbg3.jpg");
		mouse = "b1.0";
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		bg.draw(0, 0, (float) 0.5);


		
		g.drawString(mouse, 0 ,0);
		
		int w = gc.getWidth()/2-120;
		int h =  gc.getHeight()/2-120;
		
		SlickGo.drawButton(w,h,200,100,"Player Mode", g ,SlickGo.regionChecker(w,h,200,100,gc));
		SlickGo.drawButton(w,h+120,200,100,"Editor Mode", g,SlickGo.regionChecker(w,h+120,200,100,gc));
//		SlickGo.drawButton(w,h+240,200,100,gc.isFullscreen()?"Windowed Mode":"Full Screen", g,SlickGo.regionChecker(w,h+240,200,100,gc));
		
		SlickGo.drawButton(w,h+240,200,100,"Exit", g,SlickGo.regionChecker(w,h+240,200,100,gc));
	}


	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
//		int xpos = Mouse.getX();
//		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		
		int w = gc.getWidth()/2-120;
		int h =  gc.getHeight()/2-120;
		
//		mouse = "x : " + xpos + " y: " + ypos; 
		if(input.isMousePressed(0)) {
			if (SlickGo.regionChecker(w,h,200,100,gc)) {
//					SlickGo.playI.board = SlickGo.mainBoard;
					sbg.enterState(1);
			}
			
			if (SlickGo.regionChecker(w,h+120,200,100,gc)) sbg.enterState(2);
			
			
//			if (SlickGo.regionChecker(w,h+240,200,100,gc)) {
//				try {SlickGo.goFullScreen();
//				} catch (LWJGLException e) {e.printStackTrace();}
//			}
			
			
			if (SlickGo.regionChecker(w,h+240,200,100,gc)) System.exit(0);
		}
		if((input.isKeyPressed(Input.KEY_ESCAPE)) && gc.isFullscreen() ) {
			try {SlickGo.goFullScreen();
			} catch (LWJGLException e) {e.printStackTrace();}
		}
	}

	public int getID() {

		return 0;
	}
	



	
	
    public static void print(Object o){
        System.out.println(o);
    }
    
}
