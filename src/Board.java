import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.filechooser.*;
import java.io.IOException;




class Tuple{
    int a;
    int b;
    Tuple(int a, int b){
        this.a=a;
        this.b=b;
    }

    public String toString() {
        return "(" +a +"," + b+")";
    }

    @Override
    public boolean equals(Object a) {
        boolean equals = false;
        if (a != null && a instanceof Tuple){
            Tuple t = (Tuple) (a);
            equals =  this.a==t.a && this.b ==t.b;
        }
        return equals;
    }

    public int hashCode() {
      int result = 17;
      result = 31 * result + a + 31*result + b;
      return result;
    }
    public Tuple clone(Tuple t ){
        return new Tuple(t.a, t.b);
    }
}


class StoneStringResponse {
    public final boolean state;
    public final ArrayList<Tuple> list;

    public StoneStringResponse(boolean state, ArrayList<Tuple> list) {
        this.state = state;
        this.list = list;
    }
}

@SuppressWarnings("serial")
public class Board extends Canvas   {
    static Stones[][] stones = new Stones[19][19];


    static boolean editormode = true;
    static Stones placing= Stones.BLACK;
    Stones turn = Stones.BLACK;
    boolean capToWin;

    static Stones tempkeystone  = Stones.KEYBLACKSTONE;
    static Stones keystone = Stones.KEYBLACKSTONE;
    static ArrayList<Tuple> keystones = new ArrayList<>();


    ArrayList<ArrayList<Tuple>> bStoneStrings = new ArrayList<ArrayList<Tuple>>();
    ArrayList<ArrayList<Tuple>> wStoneStrings = new ArrayList<ArrayList<Tuple>>();
    ArrayList<Tuple> bCapStrings = new ArrayList<Tuple>();
    ArrayList<Tuple> wCapStrings = new ArrayList<Tuple>();

    Tuple ko;
    Tuple maybeko;
    String desc;


    public void initBoard(){
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                stones[i][j] = Stones.EMPTY;
                if (editormode) stones[i][j] = Stones.INVALID;
            }
        }
    }

    public Board(){

        addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent arg0) {}
            public void mousePressed(MouseEvent e) {

                int x = calulatePostionOnBoard(e.getX()-30);
                int y =calulatePostionOnBoard(e.getY()-30);

                if (x <= 18 && x>=0 && y <= 18 && y>=0) {
                    if (stones[x][y]== Stones.KO){
                        print("Can't Place On KO");}
                    else if (selfCap(x,y,getEnemyColour(placing))){
                            print("Can't Self Capture");}
                    else{
                        if (editormode){
                            stones[x][y] = placing;
                            removeKo();
                            if (placing== Stones.KEYBLACKSTONE || placing == Stones.KEYWHITESTONE){
                                keystones.add(new Tuple(x,y));
                                for (Tuple k:keystones){
                                    stones[k.a][k.b]=placing;
                                }
                                placing = getStoneColour(placing);}
                            else{
                                keystones.removeIf( new Tuple(x,y)::equals);}}
                        else if (stones[x][y] == Stones.EMPTY || stones[x ][y] == Stones.VALID) {
                                if (stones[x][y] == Stones.KO){
                                    print("Can't Place Due There Due To Ko");}
                                else{
                                    stones[x][y] = turn;
                                    removeKo();
                                    turn = (turn == Stones.BLACK) ? Stones.WHITE : Stones.BLACK;}}
                        else {
                            System.out.println("Stone already there");}
                        updateStringsSingle(x,y);}}
                else{
                    System.out.println("Out of bound");
                }

                updateStringsFull();
                maybeko = null;
                checkForCaps(placing);
                checkForCaps(getEnemyColour(placing));
                if (ko !=null) stones[ko.a][ko.b] = Stones.KO;
                if (!editormode)placing =turn;

                repaint();

            }
            public void mouseReleased(MouseEvent e) {}
        });


    }

    private void removeKo(){
        if (ko != null){
            stones[ko.a][ko.b] =Stones.VALID;
            ko = null;}
        }

    private boolean selfCap(int i , int j, Stones colour ){

        if (colour != Stones.BLACK && colour != Stones.WHITE) return false;

        ArrayList<Tuple> capString =  (colour== Stones.WHITE ? wCapStrings:  bCapStrings);
        ArrayList<Tuple> libs = getLiberties(i, j);
        if (capString.contains(new Tuple(i, j)))  return false;
        for(Tuple t :libs){
            if(stones[t.a][t.b] != colour ){
                return false;}
        }
        return true;
    }

    private ArrayList<Tuple> getCaptureStringFor(ArrayList<Tuple> sstring) {

        ArrayList<Tuple> capstring = new ArrayList<Tuple>();
        for (Tuple t : sstring){
            capstring.removeAll(getLiberties(t.a, t.b));
            capstring.addAll(getLiberties(t.a, t.b));
        }
        ArrayList<Tuple> l = TupleArrayClone(sstring);
        capstring.removeAll(l);
        return capstring;
    }

    private boolean checkForCaps( Stones colour) {
        if (getStoneColour(colour) != Stones.BLACK && getStoneColour(colour) != Stones.WHITE)  return false;
        ArrayList<Tuple> capString =  (colour== Stones.WHITE ? bCapStrings:  wCapStrings);
        ArrayList<ArrayList<Tuple>> stoneStrings = (colour== Stones.WHITE ? bStoneStrings: wStoneStrings);
        capString.clear();
        boolean anyCap = false;
        for (ArrayList<Tuple> tlist : stoneStrings){
            ArrayList<Tuple> capList = getCaptureStringFor(tlist);
            ArrayList<Tuple> needList = new ArrayList<Tuple>();
            for (Tuple t : capList){
                if (stones[t.a][t.b]!=colour) needList.add(t);
            }
            if (needList.size()==1){
                capString.add(needList.get(0));
                if (needList.get(0).equals(maybeko)) ko = maybeko;}
            else if(needList.size()==0){
                removeStonesOnBoard(tlist);
                anyCap=true;
                if(tlist.size() == 1) maybeko= new Tuple(tlist.get(0).a,tlist.get(0).b);}
        }
        return anyCap;

    }

    private void removeStonesOnBoard(ArrayList<Tuple> tlist){
        for(Tuple t : tlist){
            stones[t.a][t.b] = Stones.VALID;
        }
    }

    private void updateStringsSingle(int i , int j){
        if (getStoneColour(stones[i][j])==Stones.BLACK || getStoneColour(stones[i][j]) == Stones.WHITE){
            removeOldStoneFromString(i,j,getStoneColour(stones[i][j])==Stones.BLACK ? wStoneStrings:bStoneStrings);
            ArrayList<ArrayList<Tuple>> stoneStrings = getStoneColour(stones[i][j])==Stones.BLACK ? bStoneStrings:wStoneStrings;
            StoneStringResponse stringed = checkForStrings( i ,  j, stoneStrings,stones[i][j]);
            if (!stringed.state){
                //print("Stone: " + i + ","+ j);
                ArrayList<Tuple> libs =  getLiberties(i,j);
                ArrayList<Tuple> sstring= new ArrayList<Tuple>();
                sstring.add(new Tuple(i,j));
                for(Tuple t :libs ){

                    if (stones[t.a][t.b] == getStoneColour(stones[i][j])){
                        StoneStringResponse libsStringres = checkForStrings( t.a ,  t.b, stoneStrings,stones[i][j]);
                        if(!libsStringres.state){
                            sstring.add(t);
                            stoneStrings.add(sstring);}
                        else{
                            stoneStrings.remove(libsStringres.list);
                            stoneStrings.remove(sstring);
                            ArrayList<Tuple> l = TupleArrayClone(sstring) ;
                            libsStringres.list.removeAll(l);
                            sstring.addAll(libsStringres.list);
                            stoneStrings.add(sstring);}}
                }
                if (sstring.size()==1){stoneStrings.add(sstring);}}
            stoneStrings.removeIf(item -> item.isEmpty());}
        else{
            removeOldStoneFromString(i,j,bStoneStrings);
            removeOldStoneFromString(i,j,wStoneStrings);}
    }


    private void updateStringsFull(){
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                 updateStringsSingle(i,j);
            }
        }
    }

    public void removeOldStoneFromString(int  i , int j , ArrayList<ArrayList<Tuple>> stoneStrings ){
        StoneStringResponse stringed = checkForStrings( i ,  j, stoneStrings,stones[i][j]);
        if (stringed.state){
            stoneStrings.remove(stringed.list);
            updateStringsFull();
        }

    }

    public ArrayList<Tuple> TupleArrayClone(ArrayList<Tuple> s){
        ArrayList<Tuple> l = new ArrayList<Tuple>();
        for(Tuple o : s) {
            l.add(new Tuple(o.a,o.b));
        }
        return l;
    }

    public ArrayList<Tuple> getLiberties(int i , int j){
        ArrayList<Tuple> libs = new ArrayList<Tuple>();
        if (i>=1) libs.add(new Tuple(i-1,j));
        if (i<=17) libs.add(new Tuple(i+1,j));
        if (j>=1)libs.add(new Tuple(i,j-1));
        if (j<=17) libs.add(new Tuple(i,j+1));
        return libs;


    }



    public StoneStringResponse checkForStrings(int i , int j , ArrayList<ArrayList<Tuple>> stoneStrings , Stones stoneColour){

        if (getStoneColour(stones[i][j])==getStoneColour(stoneColour)){
            Tuple k = new Tuple(i, j);
            if (stoneStrings.isEmpty())return new StoneStringResponse(false,null);
            for (ArrayList<Tuple> bStrings : stoneStrings ){
                if (bStrings.contains(k)) {
                    return new StoneStringResponse(true,bStrings);};
            }
        }
        return  new StoneStringResponse(false,null);
    }

    public enum Stones {
        BLACK,WHITE,VALID,EMPTY,INVALID, KEYWHITESTONE,KEYBLACKSTONE,KO;
    }

    public int calulatePostionOnBoard(int x){
        return  (x % 30 > 15 ? (x + (30 - (x % 30))) : (x - (x % 30)))/30;
    }


    public void drawoval(Graphics g,  int x, int y , Color c , boolean key) {
        g.setColor(c);
        g.fillOval(x-5, y-5, 10, 10);
        if (key){
            g.setColor(new Color(255,175,55));
            g.drawOval(x-7, y-7, 14, 14);}
        else{
            g.setColor(Color.BLACK);}

        g.drawOval(x-5, y-5, 10, 10);
    }

    public void drawsquare(Graphics g,  int x, int y , Color c) {
        g.setColor(c);
        g.fillRect(x-10, y-10, 20, 20);
    }


    public void paint(Graphics g ){
        for (int i =30; i < 600; i+=30){
            g.setColor(Color.RED);
            g.drawLine(i,30,i, 570);
            g.drawLine(30,i,570, i);
        }


        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                switch (stones[i][j]) {
                    case BLACK:  drawoval(g,i*30+30,j*30+30 , Color.BLACK, false);
                        break;

                    case WHITE:  drawoval(g,i*30+30,j*30+30 , Color.WHITE, false);
                        break;

                    case VALID:  drawsquare(g,i*30+30,j*30+30,new Color(0f,1f,0f,.5f ));
                        break;

                    case INVALID:  drawsquare( g,i*30+30,j*30+30 ,new Color(1f,0f,0f,.5f));
                        break;

                    case KEYBLACKSTONE:
                    case KEYWHITESTONE:
                        if (keystone==Stones.KEYBLACKSTONE) {
                            drawoval(g, i * 30 + 30, j * 30 + 30, Color.BLACK, true);}
                        else{
                            drawoval(g, i * 30 + 30, j * 30 + 30, Color.WHITE, true);}

                        break;

                    case KO:
                        drawsquare( g,i*30+30,j*30+30 ,new Color(0f,0f,1f,.5f));
                    break;

                    case EMPTY:
                        break;
                }
            }
        }
    }

    public Stones getStoneColour(Stones s){
        if( s == Stones.KEYBLACKSTONE )return Stones.BLACK;
        if( s == Stones.KEYWHITESTONE )return Stones.WHITE;
        return s;
    }

    public Stones getEnemyColour(Stones s){
        if( getStoneColour(s) == Stones.BLACK )return Stones.WHITE;
        if( getStoneColour(s) == Stones.WHITE )return Stones.BLACK;
        return s;
    }





    public static void main(String[] args) throws IOException  {

        JFrame frame = new JFrame("New Frame");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocation(400, 0);
        frame.getContentPane().removeAll();
        frame.repaint();

        Board b = init_board(frame);

        if (editormode)  editormode_init(frame,b);
        firstmenus(frame,b);



    }

    public static Board init_board(JFrame frame){
        Board b = new Board();
        b.initBoard();
        b.setLocation(0, 0);
        b.setSize(600, 600);
        return b;
    }


    public static void editormode_init(JFrame frame, Board b) {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.add(b);
        b.initBoard();

        tempkeystone = Stones.KEYBLACKSTONE;
        keystone = Stones.KEYBLACKSTONE;
        placing = Stones.BLACK;
        keystones.clear();
        JRadioButton radiob = (JRadioButton) initComponent(600,100,220,20, frame ,new JRadioButton("Place Black Stones",true) , "radiob" );
        JRadioButton radiow = (JRadioButton) initComponent(600,120,220,20, frame ,new JRadioButton("Place White Stones") ,"radiow");
        JRadioButton radiov = (JRadioButton) initComponent(600,140,220,20, frame ,new JRadioButton("Mark Valid Spots"),"radiov" );
        JRadioButton radioi = (JRadioButton) initComponent(600,160,220,20, frame ,new JRadioButton("Mark Invalid Spots"),"radioi");
        JRadioButton radBtoPlay = (JRadioButton) initComponent(600,200,220,20, frame ,new JRadioButton("Black Plays First", true),"radBtoPlay");
        JRadioButton radWtoPlay = (JRadioButton) initComponent(600,220,220,20, frame ,new JRadioButton("White Plays First"),"radWtoPlay");
        JRadioButton radLife = (JRadioButton) initComponent(600,260,220,20, frame ,new JRadioButton("Keystone/s needs to live" ),"radLife");
        JRadioButton radDeath = (JRadioButton) initComponent(600,280,220,20, frame ,new JRadioButton("Keystone/s needs to die" , true),"radDeath");
        JButton keyBtn = (JButton) initComponent(600,300,220,40, frame ,new JButton("Place Key Black Stone"),"keyBtn");
        JEditorPane probDesc = (JEditorPane) initComponent(600,360,220,80, frame ,new  JEditorPane(), "probDesc");
        probDesc.setBounds(600, 360, 220, 80);
        probDesc.setBorder(BorderFactory.createLineBorder(Color.black));
        probDesc.setText("Enter Description");
        JButton finBtn =  (JButton) initComponent(600,540,220,40, frame ,new JButton("Finished"),"finBtn");
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
                placing = Stones.BLACK;
                tempkeystone = Stones.KEYBLACKSTONE;
                keyBtn.setText("Place Key Black Stone");
                System.out.println("placing black stones");
            }
        });

        radiow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placing = Stones.WHITE;
                tempkeystone = Stones.KEYWHITESTONE;
                keyBtn.setText("Place Key White Stone");
                System.out.println("placing white stones");
            }
        });

        radiov.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placing = Stones.VALID;
                System.out.println("setting valid spaces");
            }
        });

        radioi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placing = Stones.INVALID;
                System.out.println("setting invalid spaces");
            }
        });

        keyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                keystone = tempkeystone;
                placing = keystone ;
                if (placing== Stones.KEYBLACKSTONE) radiob.setSelected(true);
                if (placing== Stones.KEYWHITESTONE) radiow.setSelected(true);
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

                    for(int i=0; i<stones.length; i++) {
                        for(int j=0; j<stones[i].length; j++) {
                            switch (stones[j][i]) {
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
                    String filekstone = keystones.toString();
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

    public static void playermode_init(JFrame frame ,  Board b){

        frame.getContentPane().removeAll();
        frame.repaint();
        frame.add(b);
        b.initBoard();

        JLabel lbl1 = new JLabel("This is text");
        lbl1.setLocation(600,100);
        lbl1.setSize(200,20);
        frame.add(lbl1);

    }

    public static void firstmenus(JFrame frame , Board b) throws IOException {

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
                editormode_init(frame ,b );


            }
        });

        playermodeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editormode = false;

                playermode_init(frame , b);


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
                            if (y<19){
                                for(int i = 2, n = st.length() ; i < n ; i+=4) {
                                    char c = st.charAt(i);
                                    switch (c){
                                        case 'x': stones[x][y] = Stones.BLACK;
                                            break;
                                        case 'X':
                                            stones[x][y] = Stones.KEYBLACKSTONE;
                                            tempkeystone = Stones.KEYBLACKSTONE;
                                            keystone = Stones.KEYBLACKSTONE;
                                            break;
                                        case 'o': stones[x][y] = Stones.WHITE;
                                            break;
                                        case 'O': stones[x][y] = Stones.KEYWHITESTONE;
                                            tempkeystone = Stones.KEYWHITESTONE;
                                            keystone = Stones.KEYWHITESTONE;
                                            break;
                                        case 'K': stones[x][y] = Stones.KO;
                                            break;
                                        case '+': stones[x][y] = Stones.VALID;
                                            break;
                                        case '-': stones[x][y] = Stones.INVALID;
                                            break;
                                    }
                                    x++;
                                }}

                            if(y==19) b.turn = toStone(st.split(" ")[1]);
                            if(y==20) b.capToWin = st.split(" ")[1].equals("Yes")?true:false;
                            if(y==21) lkeystones = st.replaceAll("[\\[\\]\\)\\(]","").split(" ");
                            if(y>21) desc.append(st);
                            y++;
                        }

                    }
                    catch (FileNotFoundException e1 ) {e1.printStackTrace();}
                    catch (IOException e1 ) {e1.printStackTrace();}

                    print("Turn: " + b.turn);
                    print("Cap to win: " + b.capToWin);
                    for (int i=2; i<lkeystones.length;i++){
                        int ta;
                        int tb;
                        String[] temp= lkeystones[i].split(",");
                        ta = Integer.parseInt(temp[0]);
                        tb = Integer.parseInt(temp[1]);
                        keystones.add(new Tuple(ta,tb));
                    }
                    print("Key Stones: " + keystones);
                    b.desc = desc.toString();

                    b.repaint();

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

                    for(int i=0; i<stones.length; i++) {
                        for(int j=0; j<stones[i].length; j++) {
                            switch (stones[j][i]) {
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

                    String filekstone = keystones.toString();
                    writer.write("Key Stone/s: "+filekstone + "\r\n") ;
                    writer.write("Description: "+l.getText() + "\r\n") ;

                    writer.close();}





            }
        });




    }
    public static Stones toStone(String stone){
        if (stone.toUpperCase().equals("BLACK")) return Stones.BLACK;
        return Stones.WHITE;
    }
    public static void print(Object o){
        System.out.println(o);
    }


}
