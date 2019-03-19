package GoLD;


import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.LWJGLException;
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
	
	public static final String gamename = "GoLD";
	public static final int menu = 0;
	public static final int play = 1;
	public static final int editor = 2;
	public static final int gcwidth = 1600;
	public static final int gcheigth = 900;
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
		menuI = new Menu(menu);
		playI = new Play(play);
		editorI = new Editor(editor);

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
	
	public static void main(String[] args) throws IOException, SlickException, LWJGLException  {
//		try{ UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");}
//		catch (ClassNotFoundException e1) {e1.printStackTrace();}
//		catch (InstantiationException e1){ e1.printStackTrace();}
//		catch (IllegalAccessException e1){ e1.printStackTrace();}
//		catch (UnsupportedLookAndFeelException e1) {e1.printStackTrace();}
		
//		try{ UIManager.setLookAndFeel("com.petersoft.white.PetersoftWhiteLookAndFeel");}
//		catch (ClassNotFoundException e1) {e1.printStackTrace();}
//		catch (InstantiationException e1){ e1.printStackTrace();}
//		catch (IllegalAccessException e1){ e1.printStackTrace();}
//		catch (UnsupportedLookAndFeelException e1) {e1.printStackTrace();}

		appgc = new AppGameContainer(new SlickGo(gamename));
		appgc.setShowFPS(false);
		appgc.setDisplayMode(gcwidth, gcheigth, false);
		appgc.setTargetFrameRate(maxfps);
		appgc.setAlwaysRender(true);
		appgc.setClearEachFrame(false);
		appgc.setUpdateOnlyWhenVisible(false);
		appgc.setIcons(new String[] { "Images/GoLD32.png", "Images/GoLD16.png" });
		appgc.start();
		appgc.getGraphics().setColor(Color.black);
	
	
	
	
	}
	 


	
    public static Board loadFile(boolean editormode) {
    	
    	
 
    	final JFileChooser fc = new JFileChooser();
        fc.setPreferredSize(new Dimension(800,500));
//      File workingDirectory = new File(System.getProperty("user.dir")+"\\Computer Boards");
        File workingDirectory = new File(System.getProperty("user.dir"));

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
            int depthcut = 0;
            int breadthcut =0;
            StringBuilder desc = new StringBuilder();

            try {br = new BufferedReader(new FileReader(fc.getSelectedFile()));
                while ((st = br.readLine()) != null){
                    x =0;
                    newBoard.ko=null;

                    
                    if (y<19){
                        char firstchar = st.charAt(0);
                        if(firstchar!='|'){
                        	br.close();return  msgMaker("Invalid File",300,editormode,255,0,0,null, w);}
                        
                        for(int i = 1, n = st.length() ; i < n ; i+=1) {
                            char c = st.charAt(i);
                            if(i%4==0 && c!='|') {
                            	br.close();return  msgMaker("Invalid File",300,editormode,255,0,0,null, w);}
                            if(i%4==1 && c!=' ') {
                            	br.close();return  msgMaker("Invalid File",300,editormode,255,0,0,null, w);}
                            if(i%4==3 && c!=' ') {
                            	br.close();return  msgMaker("Invalid File",300,editormode,255,0,0,null, w);}
                
                            
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
                            		return  msgMaker("Invalid File",300,editormode,255,0,0,null, w);
                            }
                            x++;
                        }
                        if(x!=19) {br.close(); return  msgMaker("Invalid File",300,editormode,255,0,0,null, w);}
                    }

                    if(y==19) {
                    	String[] k = st.split(" ");
                    	if(k.length!=2 || !k[0].equals("Play:") || (!k[1].equals("Black") && !k[1].equals("White"))) {
                    	    br.close();
                    		return  msgMaker("Invalid File",300,editormode,255,0,0,null, w);
                    	}
                    	newBoard.turn = Stone.toStone(st.split(" ")[1]);
                    }
                    
                    
                    if(y==20) {
                    	if(!st.equals("Description: Black To Live") && !st.equals("Description: White To Live")
                    	&& !st.equals("Description: Kill Black") && !st.equals("Description: Kill White")
                    	&& !st.equals("Description: ")){
                    	    br.close();
                    		return  msgMaker("Invalid File",300,editormode,255,0,0,null, w);
                    	}
                    	desc.append(st);
                    }
                    if(y==21) {
                    	st = st.replaceAll("\\[", "").replaceAll("\\]","");
                    	String[] k = st.split(",");
                    	boolean check = true;
                    	
                    	if(k.length==2) {
                            try {depthcut= Integer.parseInt(k[0]);
                            } catch (NumberFormatException e) {check = false;}
                            if(check) {
                                try {breadthcut = Integer.parseInt(k[1]);
                                } catch (NumberFormatException e) {check = false;}
                            }
                    	}else check = false;
                    	if(!check) { br.close();return msgMaker("Invalid File",300,editormode,255,0,0,null, w);}
                    }
                    
                    
                    if(y>21) { br.close();return msgMaker("Invalid File",300,editormode,255,0,0,null, w);}
                    y++;
                }
                if(y!=22) {br.close(); return  msgMaker("Invalid File",300,editormode,255,0,0,null, w);}
                br.close();
    

            }catch (FileNotFoundException e1 ) {return msgMaker("No File Found",300,editormode,255,0,0,null, w);}
            catch (IOException e1 ) {return msgMaker("File can't be accessed right now",300,editormode,255,0,0,null, w);}

            
            if(!newBoard.validBoard()){return msgMaker("Invalid File",300,editormode,255,0,0,null, w);}
            
            String sfilename = fc.getSelectedFile().getName();
            sfilename = sfilename.substring(0, sfilename.length() - 4);
            print(sfilename);
            
            newBoard.blackFirst = (newBoard.turn==Stone.BLACK);
            newBoard.placing = newBoard.turn;
            newBoard.desc = desc.toString().replace("Description: ", "");
            newBoard.updateStringsFull();
            newBoard.checkForCaps(newBoard.turn,false);
            newBoard.checkForCaps(newBoard.turn.getEC(),false);
            newBoard.validMoves = newBoard.getAllValidMoves();
            newBoard.resetboard =Board.cloneBoard(newBoard);
            

            

        	MoveFinder.keystonecolour = newBoard.keystone.getSC();
        	if(editormode) SlickGo.editorI.blackKeyStone = newBoard.keystone.getSC()==Stone.BLACK?true:false;
        	else  SlickGo.playI.blackKeyStone = newBoard.keystone.getSC()==Stone.BLACK?true:false;
        	
        	if(sfilename.length() > 20 )sfilename= sfilename.substring(0, 20);
        	Play.problemName =sfilename+": " ;
        	if(depthcut>0) {
        		MoveFinder.cutoff = depthcut;
        		Play.heuristic = true;
        	}
        	if(breadthcut>0) {
        		MoveFinder.breathcutoff = breadthcut;
        		MoveFinder.breadthcut = true;
        	}else {
        		MoveFinder.breadthcut = false;
        	}
           

        }else return msgMaker("",0,editormode,255,0,0,null, w);
        
        w.dispose();
		return msgMaker("Problem Loaded",300,editormode,0,200,50,newBoard, w);

    }
	
    public static Board saveFile(Board board , boolean editormode)  {
    	
    	if(board.keystones.isEmpty()) return msgMaker("Problem Needs KeyStones",300,editormode,255,0,0,null, null);
    	if(board.getAllValidMovesEditorMode().isEmpty()) return msgMaker("Problem Needs Valid Points",300,editormode,255,0,0,null, null);
    	


        final JFileChooser fc = new JFileChooser();

        fc.setPreferredSize(new Dimension(800,500));
//        File workingDirectory = new File(System.getProperty("user.dir")+"\\Computer Boards");
        File workingDirectory = new File(System.getProperty("user.dir"));
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

                        //case EMPTY: break;
                    }
                }
                writer.write("|\r\n") ;

            }

   
            
            String whoplays = board.blackFirst?"Black":"White";
            writer.write("Play: "+whoplays +"\r\n") ;
            writer.write("Description: "+board.desc) ;
            writer.write("\r\n[0,0]") ;


            writer.close();
       }
  
        w.dispose();
        return null;
    }
    
    
    public static Board msgMaker(String msg , int msgtimer, boolean editormode,int r,int g,int b , Board board ,JFrame w) {
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
		if(w!=null)w.dispose();
		return board;
    }
 
    public static boolean withinBounds(int x, int y){
    	if(x <= 18 && x>=0 && y <= 18 && y>=0) return true;
    	return false;
    	
    }
	
    
	public static boolean regionChecker(int x, int y , int w , int h , GameContainer gc) {


    	float hratio = (float) ((gcheigth*1.0)/900);
    	float wratio = (float) ((gcwidth*1.0)/1600);
    	h*=hratio;
    	w*=wratio;
    	y*=hratio;
    	x*= wratio;
    	
		int xpos = Mouse.getX();
		int ypos =  Math.abs(gc.getHeight() - Mouse.getY());
		return (xpos >= x && xpos <= (x+w)  && ypos >= y && ypos <= (y+h) );
	}
	
	
    public static void drawButton(int x, int y , int w , int h, String string,Graphics g ,boolean hover) {
    	


    	float hratio = (float) ((gcheigth*1.0)/900);
    	float wratio = (float) ((gcwidth*1.0)/1600);
    	h*=hratio;
    	w*=wratio;
    	y*=hratio;
    	x*= wratio;
    	
    	

        g.setColor(Color.black);
        if(hover) g.setColor(new Color(255,225,0));
        g.drawRoundRect(x, y, w, h, 20);
        g.setColor(Color.black);
        
        g.setFont(Menu.defaultFont);
        Font oldfont = g.getFont();
        int width = oldfont.getWidth(string);
        int height = oldfont.getHeight(string);
        g.drawString(string,(x + w / 2) - (width / 2), (y + h / 2) - (height / 2));
  
    	
    }
    
    public static void drawTitle(int x, int y , int w , int h, String string,Graphics g ,boolean hover) {
    	


    	float hratio = (float) ((gcheigth*1.0)/900);
    	float wratio = (float) ((gcwidth*1.0)/1600);
    	h*=hratio;
    	w*=wratio;
    	y*=hratio;
    	x*= wratio;
    	
    	

        g.setColor(Color.black);
        g.drawRoundRect(x-1, y-1, w+2, h+2, 20);
        g.drawRoundRect(x+1, y+1, w-2, h-2, 20);
        if(hover) g.setColor(new Color(255,225,0));
        g.drawRoundRect(x, y, w, h, 20);



        g.setColor(Color.black);
        
        Font oldfont = g.getFont();
        int width = oldfont.getWidth(string);
        int height = oldfont.getHeight(string);
        g.drawString(string,(x + w / 2) - (width / 2), (y + h / 2) - (height / 2));
  
    	
    }
    
    public static void drawBox(int x, int y , int w , int h, String string,Graphics g ,boolean hover) {
    	


    	float hratio = (float) ((gcheigth*1.0)/900);
    	float wratio = (float) ((gcwidth*1.0)/1600);
    	h*=hratio;
    	w*=wratio;
    	y*=hratio;
    	x*= wratio;
    	
    	

        g.setColor(Color.black);
        if(hover) g.setColor(Color.yellow);
        g.drawRect(x, y, w, h);
        g.setColor(Color.black);
        
        
        Font oldfont = g.getFont();
        int width = oldfont.getWidth(string);
        int height = oldfont.getHeight(string);
        g.drawString(string,(x + w / 2) - (width / 2), (y + h / 2) - (height / 2));
  
    	
    }
    
    public static void drawButton(int x, int y , int w , int h, String string,Graphics g ,boolean hover , Color hc) {



    	
    	float hratio = (float) ((gcheigth*1.0)/900);
    	float wratio = (float) ((gcwidth*1.0)/1600);
    	h*=hratio;
    	w*=wratio;
    	y*=hratio;
    	x*= wratio;
    	x++;
    	y++;
    	
        g.setColor(Color.transparent);
        g.fillRect(x, y, w, h);
        g.setColor(Color.black);
        if(hover) g.setColor(hc);
        g.drawRect(x, y, w, h);
        g.setColor(Color.black);
        
        g.setFont(Menu.defaultFont);
        Font oldfont = g.getFont();
        int width = oldfont.getWidth(string);
        int height = oldfont.getHeight(string);
        g.drawString(string,(x + w / 2) - (width / 2), (y + h / 2) - (height / 2));

        
    	
    }
    public static void drawMessageBox(int x, int y , int w , int h, String string,Graphics g ,Color hc, TrueTypeFont ttfont) {
    	
    	
    	float hratio = (float) ((gcheigth*1.0)/900);
    	float wratio = (float) ((gcwidth*1.0)/1600);
    	h*=hratio;
    	w*=wratio;
    	y*=hratio;
    	x*= wratio;
//    	Font oldfont = g.getFont();
        int width = ttfont.getWidth(string);
        int height = ttfont.getHeight(string);
        
        Color oldcolour = g.getColor();
        g.setColor(Color.black);
        g.drawRect(x, y, w, h);
        g.setColor(hc);
        

		g.setFont(ttfont);
        g.drawString(string,(x + w / 2) - (width / 2), (y + h / 2) - (height / 2));
        g.setColor(oldcolour);
//        g.setFont(oldfont);
    }
    
    public static void drawRButton(int x, int y , String string,Graphics g ,boolean selected) {

    	int w = 20;
    	int h = 20;
    	

    	
    	float hratio = (float) ((gcheigth*1.0)/900);
    	float wratio = (float) ((gcwidth*1.0)/1600);
    	h*=hratio;
    	w*=wratio;
    	y*=hratio;
    	x*= wratio;
    	
    	
        g.setColor(Color.transparent);
        if(selected) g.setColor(Color.black);
        g.fillOval(x,y,w,h);
        g.setColor(Color.black);
        g.drawOval(x, y, w, h);

        Font oldfont = g.getFont();
        g.resetFont();

        g.drawString(string,x+(w*2),y+(h/10) );
    	g.setFont(oldfont);
    	
    }
    
    public static void drawString(int x, int y,String s ,Graphics g) {

    	float hratio = (float) ((gcheigth*1.0)/900);
    	float wratio = (float) ((gcwidth*1.0)/1600);
    	y*=hratio;
    	x*= wratio;

        

        g.drawString(s,x,y);
        
    	
    }
    
    public static void print(Object o){
	        System.out.println(o);
	    }
	    


}
