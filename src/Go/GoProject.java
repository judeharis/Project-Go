package Go;

import java.awt.Color;
import java.awt.Component;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;



public class GoProject {
	
	static boolean editormode = false;

	static JFrame frame;
	static Board b;
	
	
    public static void main(String[] args) throws IOException  {

        frame = new JFrame("New Frame");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 900);
        frame.setLocation(1920, 0);
        frame.getContentPane().removeAll();
        frame.repaint();

        b = init_board();

        if (editormode)  {editormode_init();}
        else {playermode_init();}
        firstmenus();

    }
	
    public static void print(Object o){
        System.out.println(o);
    }
    
    public static Board init_board(){
        b = new Board();
        b.initBoard();
        b.setLocation(0, 0);
        b.setSize(b.boardTotal, b.boardTotal);

        return b;
    }
    
    public static void editormode_init() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.add(b);
        b.initBoard();


        JRadioButton radiob = (JRadioButton) initComponent(b.boardTotal,100,220,20, frame ,new JRadioButton("Place Black Stones",true) , "radiob" );
        JRadioButton radiow = (JRadioButton) initComponent(b.boardTotal,120,220,20, frame ,new JRadioButton("Place White Stones") ,"radiow");
        JRadioButton radiov = (JRadioButton) initComponent(b.boardTotal,140,220,20, frame ,new JRadioButton("Mark Valid Spots"),"radiov" );
        JRadioButton radioi = (JRadioButton) initComponent(b.boardTotal,160,220,20, frame ,new JRadioButton("Mark Invalid Spots"),"radioi");
        JRadioButton radBtoPlay = (JRadioButton) initComponent(b.boardTotal,200,220,20, frame ,new JRadioButton("Black Plays First", true),"radBtoPlay");
        JRadioButton radWtoPlay = (JRadioButton) initComponent(b.boardTotal,220,220,20, frame ,new JRadioButton("White Plays First"),"radWtoPlay");
        JRadioButton radLife = (JRadioButton) initComponent(b.boardTotal,260,220,20, frame ,new JRadioButton("Keystone/s needs to live" ),"radLife");
        JRadioButton radDeath = (JRadioButton) initComponent(b.boardTotal,280,220,20, frame ,new JRadioButton("Keystone/s needs to die" , true),"radDeath");
        JButton keyBtn = (JButton) initComponent(b.boardTotal,300,220,40, frame ,new JButton("Place Key Black Stone"),"keyBtn");
        JEditorPane probDesc = (JEditorPane) initComponent(b.boardTotal,360,220,80, frame ,new  JEditorPane(), "probDesc");
        probDesc.setBounds(b.boardTotal, 360, 220, 80);
        probDesc.setBorder(BorderFactory.createLineBorder(Color.black));
        probDesc.setText("Enter Description");
        JButton finBtn =  (JButton) initComponent(b.boardTotal,540,220,40, frame ,new JButton("Finished"),"finBtn");
        ButtonGroup radgroup1 = new ButtonGroup();{
            radgroup1.add(radiow);
            radgroup1.add(radiob);
            radgroup1.add(radiov);
            radgroup1.add(radioi);
        }
        ButtonGroup radgroup2 = new ButtonGroup();{
            radgroup2.add(radBtoPlay);
            radgroup2.add(radWtoPlay);
        }
        ButtonGroup radgroup3 = new ButtonGroup();{
            radgroup3.add(radLife);
            radgroup3.add(radDeath);
        }



        frame.setVisible(true);

        radiob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b.placing = Stones.BLACK;
                b.tempkeystone = Stones.KEYBLACKSTONE;
                keyBtn.setText("Place Key Black Stone");
                b.repaint();
                System.out.println("placing black stones");
            }
        });

        radiow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b.placing = Stones.WHITE;
                b.tempkeystone = Stones.KEYWHITESTONE;
                keyBtn.setText("Place Key White Stone");
                b.repaint();
                System.out.println("placing white stones");
            }
        });

        radiov.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b.placing = Stones.VALID;
                System.out.println("setting valid spaces");
            }
        });

        radioi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b.placing = Stones.INVALID;
                System.out.println("setting invalid spaces");
            }
        });

        keyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b.keystone = b.tempkeystone;
                b.placing = b.keystone ;
                if (b.placing== Stones.KEYBLACKSTONE) radiob.setSelected(true);
                if (b.placing== Stones.KEYWHITESTONE) radiow.setSelected(true);
            }
        });

        finBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                final JFileChooser fc = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
                fc.setFileFilter(filter);
                fc.setCurrentDirectory(workingDirectory);
                if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    String filename =  fc.getSelectedFile().getName();
                    if (filename.length() <= 2) filename += ".txt";
                    else if((filename).substring(filename.length()-3).equals(".txt")){
                        filename += ".txt";}

                    PrintWriter writer = null;
                    try {writer = new PrintWriter(filename);}
                    catch (FileNotFoundException e1) {e1.printStackTrace();}

                    for(int i=0; i<b.stones.length; i++) {
                        for(int j=0; j<b.stones[i].length; j++) {
                            switch (b.stones[j][i]) {
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

                    String whoplays = radBtoPlay.isSelected()?"Black":"White";
                    String kill  = radDeath.isSelected()?"Yes":"No";
                    String filekstone = b.keystones.toString();
                    writer.write("Play: "+whoplays +"\r\n") ;
                    writer.write("Kill: "+kill +"\r\n") ;
                    writer.write("Key Stone/s: "+filekstone + "\r\n") ;
                    writer.write("Description: "+probDesc.getText() + "\r\n") ;
                    writer.close();}


            }
        });


    }

    public static JComponent initComponent(int x, int y , int width , int height , JFrame frame , JComponent j,String jname){
        j.setSize(width, height);
        j.setLocation(x,y);
        j.setName(jname);
        frame.add(j);
        return j ;
    }

    public static void playermode_init(){

        frame.getContentPane().removeAll();
        frame.repaint();
        frame.add(b);
        b.initBoard();

        initComponent(b.boardTotal, 100 , 200 , 20 , frame ,  new JLabel("No Problem Loaded"),"desclbl");
        initComponent(b.boardTotal, 200 , 200 , 20 , frame ,  new JLabel("Turn: "+ b.turn),"turnlbl");
        frame.setVisible(true);
//        Random rand = new Random();
//        ArrayList<Tuple> all  = b.getAllValidMoves();
//        int n = rand.nextInt(b.getAllValidMoves().size());
//        b.takeTurn(all.get(n).a, all.get(n).b);
//        for(int i = 0 ; i < 200; i++) {
//        	n=  rand.nextInt(b.validMoves.size());
//        	b.takeTurn(b.validMoves.get(n).a, b.validMoves.get(n).b);
//        }

    }

    public static void firstmenus() throws IOException {

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        MenuItem editorodeMenuItem = new MenuItem("Editor Mode");
        MenuItem playermodeMenuItem = new MenuItem("Player Mode");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem exitMenuItem = new MenuItem("Exit");

        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        editMenu.add(editorodeMenuItem);
        editMenu.add(playermodeMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        frame.setMenuBar(menuBar);

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        editorodeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editormode = true;
                editormode_init();


            }
        });

        playermodeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editormode = false;
                playermode_init();


            }
        });

        loadMenuItem.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {

                final JFileChooser fc = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
                fc.setFileFilter(filter);
                fc.setCurrentDirectory(workingDirectory);

                if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    BufferedReader br ;
                    String st;
                    int x = 0;
                    int y = 0;
                    StringBuilder desc = new StringBuilder();
                    String[] lkeystones = null ;
                    try {br = new BufferedReader(new FileReader(fc.getSelectedFile()));
                        while ((st = br.readLine()) != null){
                            x =0;
                            b.ko=null;
                            if (y<19){
                                for(int i = 2, n = st.length() ; i < n ; i+=4) {
                                    char c = st.charAt(i);
                                    switch (c){
                                        case 'x': b.stones[x][y] = Stones.BLACK;
                                            break;
                                        case 'X':
                                        	b.stones[x][y] = Stones.KEYBLACKSTONE;
                                        	b.tempkeystone = Stones.KEYBLACKSTONE;
                                        	b. keystone = Stones.KEYBLACKSTONE;
                                            break;
                                        case 'o': b.stones[x][y] = Stones.WHITE;
                                            break;
                                        case 'O': b.stones[x][y] = Stones.KEYWHITESTONE;
                                        	b.tempkeystone = Stones.KEYWHITESTONE;
                                        	b.keystone = Stones.KEYWHITESTONE;
                                            break;
                                        case 'K': b.stones[x][y] = Stones.KO;
                                            break;
                                        case '+': b.stones[x][y] = Stones.VALID;
                                            break;
                                        case '-': b.stones[x][y] = Stones.INVALID;
                                            break;
                                    }
                                    x++;
                                }}

                            if(y==19) b.turn = Stones.toStone(st.split(" ")[1]);
                            if(y==20) b.capToWin = st.split(" ")[1].equals("Yes")?true:false;
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
                        b.keystones.add(new Tuple(ta,tb));
                    }

                    b.placing = b.turn;
                    b.desc = desc.toString();
                    b.updateStringsFull();
                    b.checkForCaps(Stones.getEnemyColour(b.turn));
                    b.checkForCaps(b.turn);
                    b.validMoves =b.getAllValidMoves();
                    b.repaint();
                    updateGUI();

                }


            }
        });


        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                final JFileChooser fc = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
                fc.setFileFilter(filter);
                fc.setCurrentDirectory(workingDirectory);
                if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    String filename =  fc.getSelectedFile().getName();

                    if (filename.length() <= 2) filename += ".txt";
                    else if((filename).substring(filename.length()-3).equals(".txt")){
                        filename += ".txt";}

                    PrintWriter writer = null;
                    try {writer = new PrintWriter(filename);}
                    catch (FileNotFoundException e1) {e1.printStackTrace();}

                    for(int i=0; i<b.stones.length; i++) {
                        for(int j=0; j<b.stones[i].length; j++) {
                            switch (b.stones[j][i]) {
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

                    JEditorPane l = new JEditorPane() ;
                    if (editormode){
                        Component[] components = frame.getContentPane().getComponents();
                        for (Component c : components){
                            JRadioButton k ;
                            if(c.getName()== "radBtoPlay"){
                                k =  (JRadioButton) c;
                                String whoplays = k.isSelected()?"Black":"White";
                                writer.write("Play: "+whoplays +"\r\n") ;}
                            if(c.getName()== "radDeath"){
                                k =  (JRadioButton) c;
                                String kill  = k.isSelected()?"Yes":"No";
                                writer.write("Kill: "+kill +"\r\n") ;}
                            if(c.getName()== "probDesc") l =  (JEditorPane) c;
                        }
                    }

                    String filekstone = b.keystones.toString();
                    writer.write("Key Stone/s: "+filekstone + "\r\n") ;
                    writer.write("Description: "+l.getText() + "\r\n") ;

                    writer.close();}





            }
        });




    }

	
    public static void updateGUI() {
    	for (Component c : frame.getContentPane().getComponents()) {
    		if (c.getName().equals("turnlbl")) { 
    			JLabel l = (JLabel) c;
    			l.setText("Turn: "+ b.turn);
    		}
    		
    		if (c.getName().equals("desclbl")) { 
    			JLabel l = (JLabel) c;
    			l.setText(b.desc);
    		}
    			
    		
    	}  
		
	}
    

}
