package Go.SlickGo;

import org.lwjgl.input.Mouse;
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
	public Menu(int state , int gcsize) {
		this.gcsize = gcsize;
	}


	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = new Image("Images/woodenbg3.jpg");
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		bg.draw(0, 0, (float) 0.5);
		
		g.drawString(mouse, 0 ,0);
		
		SlickGo.drawButton(500 ,300,200,100,"Player Mode", g ,SlickGo.regionChecker(500,300,200,100,gc));
		SlickGo.drawButton(500,420,200,100,"Editor Mode", g,SlickGo.regionChecker(500,420,200,100,gc));
		SlickGo.drawButton(500,540,200,100,"Exit", g,SlickGo.regionChecker(500,540,200,100,gc));
	}


	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		mouse = "x : " + xpos + " y: " + ypos; 
		if (SlickGo.regionChecker(500,300,200,100,gc)) {
			if(input.isMousePressed(0))sbg.enterState(1);}
		
		if (SlickGo.regionChecker(500,420,200,100,gc)) {
			if(input.isMousePressed(0))sbg.enterState(2);}
		
		
		if (SlickGo.regionChecker(500,540,200,100,gc)) {
			if(input.isMousePressed(0))System.exit(0);}
	}

	public int getID() {

		return 0;
	}
	



	
	
    public static void print(Object o){
        System.out.println(o);
    }
    
}
