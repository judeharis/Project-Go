package Go.SlickGo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SlickGo extends StateBasedGame {
	
	public static final String gamename = "Go";
	public static final int menu = 0;
	public static final int play = 1;
	public static final int editor = 2;
	public static final int gcheigth = 1000;
	public static final int gcwidth = 1200;
	public static final int maxfps = 60;
	public SlickGo(String gamename) {
		super(gamename);
		this.addState(new Menu(menu,gcheigth));
		this.addState(new Play(play,gcheigth));
		this.addState(new Editor(play,gcheigth));

	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.getState(editor).init(gc, this);
		this.enterState(menu);
		 
	}
	
	public static void main(String[] args) throws IOException, SlickException  {
		 AppGameContainer appgc;

		 appgc = new AppGameContainer(new SlickGo(gamename));
		 appgc.setShowFPS(false);
		 appgc.setDisplayMode(1280, 840, false);
		 appgc.setTargetFrameRate(maxfps);
		 appgc.start();


	 }
	 
	 
	public static boolean regionChecker(int x, int y , int w , int h , GameContainer gc) {
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		return (xpos >= x && xpos <= (x+w)  && ypos >= y && ypos <= (y+h) );
	}
	
    public static void loadFile(Board board) {
    	final JFileChooser fc = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(workingDirectory);

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            BufferedReader br ;
            String st;
            int x = 0;
            int y = 0;
            StringBuilder desc = new StringBuilder();
            String[] lkeystones = null ;
            try {br = new BufferedReader(new FileReader(fc.getSelectedFile()));
                while ((st = br.readLine()) != null){
                    x =0;
                    board.ko=null;
                    if (y<19){
                        for(int i = 2, n = st.length() ; i < n ; i+=4) {
                            char c = st.charAt(i);
                            switch (c){
                                case 'x': board.stones[x][y] = Stones.BLACK;
                                    break;
                                case 'X':
                                	board.stones[x][y] = Stones.KEYBLACKSTONE;
                                	board.tempkeystone = Stones.KEYBLACKSTONE;
                                	board. keystone = Stones.KEYBLACKSTONE;
                                    break;
                                case 'o': board.stones[x][y] = Stones.WHITE;
                                    break;
                                case 'O': board.stones[x][y] = Stones.KEYWHITESTONE;
                                	board.tempkeystone = Stones.KEYWHITESTONE;
                                	board.keystone = Stones.KEYWHITESTONE;
                                    break;
                                case 'K': board.stones[x][y] = Stones.KO;
                                    break;
                                case '+': board.stones[x][y] = Stones.VALID;
                                    break;
                                case '-': board.stones[x][y] = Stones.INVALID;
                                    break;
                            }
                            x++;
                        }}

                    if(y==19) board.turn = Stones.toStone(st.split(" ")[1]);
                    if(y==20) board.capToWin = st.split(" ")[1].equals("Yes")?true:false;
                    if(y==21) lkeystones = st.replaceAll("[\\[\\]\\)\\(]","").split(" ");
                    if(y>21) desc.append(st);
                    y++;
                }

            }
            catch (FileNotFoundException e1 ) {e1.printStackTrace();}
            catch (IOException e1 ) {e1.printStackTrace();}
            
            for (int i=2; i<lkeystones.length;i++){
                int ta;
                int tb;
                String[] temp= lkeystones[i].split(",");
                ta = Integer.parseInt(temp[0]);
                tb = Integer.parseInt(temp[1]);
                board.keystones.add(new Tuple(ta,tb));
            }
            
            board.computer = false;
            board.ai = false;
            board.blackFirst = (board.turn==Stones.BLACK);
            board.placing = board.turn;
            board.desc = desc.toString();
            board.updateStringsFull();
            board.checkForCaps(board.turn);
            board.checkForCaps(Stones.getEnemyColour(board.turn));
            
            board.validMoves = board.getAllValidMoves();
            board.resetboard =Board.cloneBoard(board);
           

        }
    }
	
    public static void saveFile(Board board) {


        final JFileChooser fc = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(workingDirectory);
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            
        
            
            String filename =  fc.getSelectedFile().getAbsolutePath(); 
            String shortname = fc.getSelectedFile().getName();
            if (shortname.length() <= 3) filename += ".txt";
            else if(!(filename).substring(filename.length()-4).equals(".txt")){
                filename += ".txt";}

            PrintWriter writer = null;
            try {writer = new PrintWriter(filename);}
            catch (FileNotFoundException e1) {e1.printStackTrace();}

            for(int i=0; i<board.stones.length; i++) {
                for(int j=0; j<board.stones[i].length; j++) {
                    switch (board.stones[j][i]) {
                        case BLACK: writer.write("| x ") ;
                            break;

                        case WHITE:  writer.write("| o ") ;
                            break;

                        case VALID:  writer.write("| + ") ;
                            break;

                        case INVALID:   writer.write("| - ") ;
                            break;

                        case KEYBLACKSTONE: writer.write("| X ") ;
                            break;

                        case KEYWHITESTONE:  writer.write("| O ") ;
                            break;

                        case KO:  writer.write("| K ") ;
                            break;

                        case EMPTY: break;
                    }
                }
                writer.write("|\r\n") ;

            }

   
            
            String whoplays = board.blackFirst?"Black":"White";
            String kill = board.capToWin?"Yes":"No";
            writer.write("Play: "+whoplays +"\r\n") ;
            writer.write("Kill: "+kill +"\r\n") ;
            String filekstone = board.keystones.toString();
            writer.write("Key Stone/s: "+filekstone + "\r\n") ;
            writer.write("Description: "+board.desc + "\r\n") ;

            writer.close();}

    }
 
    public static boolean withinBounds(int x, int y){
    	if(x <= 18 && x>=0 && y <= 18 && y>=0) return true;
    	return false;
    	
    }
	
    public static void drawButton(int x, int y , int w , int h, String string,Graphics g ,boolean hover) {
        g.setColor(Color.transparent);
        g.fillRect(x, y, w, h);
        g.setColor(Color.black);
        if(hover) g.setColor(Color.yellow);
        g.drawRect(x, y, w, h);
        g.setColor(Color.black);
        g.drawString(string,x+(w/5),y+(h/5) );
        
    	
    }
    
    public static void drawRButton(int x, int y , String string,Graphics g ,boolean selected) {
    	int w = 20;
    	int h = 20;
        g.setColor(Color.white);
        if(selected) g.setColor(Color.green);
        g.fillOval(x,y,w,h);
        g.setColor(Color.black);
        g.drawOval(x, y, w, h);
        g.drawString(string,x+(w*2),y+(h/10) );
        
    	
    }
    
    public static void print(Object o){
	        System.out.println(o);
	    }
	    


}