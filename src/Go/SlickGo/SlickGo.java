package Go.SlickGo;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;



public class SlickGo extends StateBasedGame {
	
	public static final String gamename = "Go";
	public static final int menu = 0;
	public static final int play = 1;
	public static final int editor = 2;
	public static final int gcheigth = 1080;
	public static final int gcwidth = 1600;
	public static final int maxfps = 60;
	public static Board mainBoard;
	public static Menu menuI;
	public static Play playI;
	public static Editor editorI;
	public static AppGameContainer appgc;

	
	public SlickGo(String gamename) {

		super(gamename);
		mainBoard =new Board();
		SlickGo.mainBoard.initBoard(true);
		menuI = new Menu(menu,gcheigth);
		playI = new Play(play,gcheigth);
		editorI = new Editor(play,gcheigth);

		this.addState(menuI);
		this.addState(playI);
		this.addState(editorI);

	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.getState(editor).init(gc, this);
		this.enterState(menu);
		 
	}
	
	public static void main(String[] args) throws IOException, SlickException  {
        try{ UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");}
        catch (ClassNotFoundException e1) {e1.printStackTrace();}
        catch (InstantiationException e1){ e1.printStackTrace();}
        catch (IllegalAccessException e1){ e1.printStackTrace();}
        catch (UnsupportedLookAndFeelException e1) {e1.printStackTrace();}

		 appgc = new AppGameContainer(new SlickGo(gamename));
		 appgc.setShowFPS(false);
//		 appgc.setDisplayMode(gcwidth, gcheigth, false);
		 appgc.setDisplayMode(1280, 840, false);
		 appgc.setTargetFrameRate(maxfps);
		 appgc.setAlwaysRender(true);
		 appgc.setClearEachFrame(false);
		 appgc.start();





	 }
	 
	 
	public static boolean regionChecker(int x, int y , int w , int h , GameContainer gc) {
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		return (xpos >= x && xpos <= (x+w)  && ypos >= y && ypos <= (y+h) );
	}
	

	
    public static Board loadFile(boolean editormode) {
    	
    	final JFileChooser fc = new JFileChooser();
        fc.setPreferredSize(new Dimension(800,500));
        File workingDirectory = new File(System.getProperty("user.dir")+"\\Computer Boards");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(workingDirectory);
        JFrame w = new JFrame();
        w.setAlwaysOnTop(true);
        w.setVisible(false);
        Board newBoard = new Board();

        if (fc.showOpenDialog(w) == JFileChooser.APPROVE_OPTION) {
        	
        	newBoard.initBoard(editormode);
            BufferedReader br ;
            String st;
            int x = 0;
            int y = 0;
            StringBuilder desc = new StringBuilder();

            try {br = new BufferedReader(new FileReader(fc.getSelectedFile()));
                while ((st = br.readLine()) != null){
                    x =0;
                    newBoard.ko=null;

                    
                    if (y<19){
                        char firstchar = st.charAt(0);
                        if(firstchar!='|'){
                        	br.close();return  msgMaker("Invalid File",300,editormode,255,0,0,null);}
                        
                        for(int i = 1, n = st.length() ; i < n ; i+=1) {
                            char c = st.charAt(i);
                            if(i%4==0 && c!='|') {
                            	br.close();return  msgMaker("Invalid File",300,editormode,255,0,0,null);}
                            if(i%4==1 && c!=' ') {
                            	br.close();return  msgMaker("Invalid File",300,editormode,255,0,0,null);}
                            if(i%4==3 && c!=' ') {
                            	br.close();return  msgMaker("Invalid File",300,editormode,255,0,0,null);}
                
                            
                            if(i%4==0 ) continue;
                            if(i%4==1 ) continue;
                            if(i%4==3 ) continue;

                            
                            switch (c){
                                case 'x': newBoard.stones[x][y] = Stone.BLACK;
                                    break;
                                case 'X':
                                	newBoard.stones[x][y] = Stone.KEYBLACKSTONE;
                                	newBoard. keystone = Stone.KEYBLACKSTONE;
                                    break;
                                case 'o': newBoard.stones[x][y] = Stone.WHITE;
                                    break;
                                case 'O': newBoard.stones[x][y] = Stone.KEYWHITESTONE;
                                	newBoard.keystone = Stone.KEYWHITESTONE;
                                    break;
                                case 'K': newBoard.stones[x][y] = Stone.KO;
                                    break;
                                case '+': newBoard.stones[x][y] = Stone.VALID;
                                    break;
                                case '-': newBoard.stones[x][y] = Stone.INVALID;
                                    break;
                                default :
                            	    br.close();
                            		return  msgMaker("Invalid File",300,editormode,255,0,0,null);
                            }
                            x++;
                        }
                        if(x!=19) {br.close(); return  msgMaker("Invalid File",300,editormode,255,0,0,null);}
                    }

                    if(y==19) {
                    	String[] k = st.split(" ");
                    	if(k.length!=2 || !k[0].equals("Play:") || (!k[1].equals("Black") && !k[1].equals("White"))) {
                    	    br.close();
                    		return  msgMaker("Invalid File",300,editormode,255,0,0,null);
                    	}
                    	newBoard.turn = Stone.toStone(st.split(" ")[1]);
                    }
                    
                    
                    if(y==20) {
                    	String[] k = st.split(" ");
                    	if(k.length!=2 || !k[0].equals("Kill:") || (!k[1].equals("Yes") && !k[1].equals("No"))) {
                    	    br.close();
                    		return  msgMaker("Invalid File",300,editormode,255,0,0,null);
                    	}
                    	newBoard.capToWin = k[1].equals("Yes")?true:false;
                    }
                    
                    
                    if(y==21) {
                    	if(!st.equals("Description: Black To Live") && !st.equals("Description: White To Live")
                    	&& !st.equals("Description: Kill Black") && !st.equals("Description: Kill White")
                    	&& !st.equals("Description: ")){
                    	    br.close();
                    		return  msgMaker("Invalid File",300,editormode,255,0,0,null);
                    	}
                    	desc.append(st);
                    }
                    
                    if(y>21) { br.close();return msgMaker("Invalid File",300,editormode,255,0,0,null);}
                    y++;
                }
                if(y!=22) {br.close(); return  msgMaker("Invalid File",300,editormode,255,0,0,null);}
                br.close();
    

            }catch (FileNotFoundException e1 ) {return msgMaker("No File Found",300,editormode,255,0,0,null);}
            catch (IOException e1 ) {return msgMaker("File can't be accessed right now",300,editormode,255,0,0,null);}

            
            if(!newBoard.validBoard()){return msgMaker("Invalid File",300,editormode,255,0,0,null);}
            
                      
            newBoard.blackFirst = (newBoard.turn==Stone.BLACK);
            newBoard.placing = newBoard.turn;
            newBoard.desc = desc.toString().replace("Description: ", "");
            newBoard.updateStringsFull();
            newBoard.checkForCaps(newBoard.turn,false);
            newBoard.checkForCaps(newBoard.turn.getEC(),false);
            newBoard.validMoves = newBoard.getAllValidMoves();
            newBoard.resetboard =Board.cloneBoard(newBoard);
        	if((newBoard.blackFirst && newBoard.capToWin) || (!newBoard.blackFirst && !newBoard.capToWin))MoveFinder.keystonecolour = Stone.WHITE;
    		else MoveFinder.keystonecolour = Stone.BLACK;
           

        }else return null;
        
        w.dispose();
		return msgMaker("Problem Loaded",300,editormode,0,200,50,newBoard);

    }
	
    public static Board saveFile(Board board , boolean editormode)  {
    	
    	if(board.keystones.isEmpty()) return msgMaker("Problem Needs KeyStones",300,editormode,255,0,0,null);
    	if(board.getAllValidMovesEditorMode().isEmpty()) return msgMaker("Problem Needs Valid Points",300,editormode,255,0,0,null);
    	


        final JFileChooser fc = new JFileChooser();

        fc.setPreferredSize(new Dimension(800,500));
        File workingDirectory = new File(System.getProperty("user.dir")+"\\Computer Boards");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(workingDirectory);
        
        JFrame w = new JFrame();
        w.setAlwaysOnTop(true);
        w.setVisible(false);
        if (fc.showSaveDialog(w) == JFileChooser.APPROVE_OPTION) {

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
            writer.write("Description: "+board.desc) ;


            writer.close();
       }
  
        w.dispose();
        return null;
    }
    
    
    public static Board msgMaker(String msg , int msgtimer, boolean editormode,int r,int g,int b , Board board) {
		if(editormode) {
			Editor.msg = msg;
			Editor.msgtimer = msgtimer;
			Editor.msgcr = r;
			Editor.msgcg = g;
			Editor.msgcb = b;
		}else {
			Play.msg = msg;
			Play.msgtimer = msgtimer;
			Play.msgcr = r;
			Play.msgcg = g;
			Play.msgcb = b;
		}
		return board;
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
    
    public static void drawButton(int x, int y , int w , int h, String string,Graphics g ,boolean hover , Color hc) {
        g.setColor(Color.transparent);
        g.fillRect(x, y, w, h);
        g.setColor(Color.black);
        if(hover) g.setColor(hc);
        g.drawRect(x, y, w, h);
        g.setColor(Color.black);
        g.drawString(string,x+(w/5),y+(h/5) );
        
    	
    }
    public static void drawMessageBox(int x, int y , int w , int h, String string,Graphics g ,Color hc) {
        Font oldfont = g.getFont();
        
		java.awt.Font newfont = new java.awt.Font("AngelCodeFont", java.awt.Font.PLAIN, 25);
		TrueTypeFont trueTypeFont = new TrueTypeFont(newfont, true);
        int width = trueTypeFont.getWidth(string);
        int height = trueTypeFont.getHeight(string);
        
        Color oldcolour = g.getColor();
        g.drawRect(x, y, w, h);
        g.setColor(hc);
        

		g.setFont(trueTypeFont);
        g.drawString(string,
        			(x + w / 2) - (width / 2), 
                    (y + h / 2) - (height / 2));
        g.setColor(oldcolour);
        g.setFont(oldfont);
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
