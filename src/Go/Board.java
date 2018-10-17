package Go;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;




@SuppressWarnings("serial")
public class Board extends Canvas implements MouseMotionListener{
    boolean ai = false;
    boolean computer = false;
    boolean capToWin;
    
	int boardTotal = 800;
	int boardSize = boardTotal/20;
	int posX;
	int posY;
	
	String desc ="No Problem Loaded";

	Timer timer;
	
	
    Stones placing= Stones.BLACK;
    Stones turn = Stones.BLACK;
    Stones tempkeystone  = Stones.KEYBLACKSTONE;
    Stones keystone = Stones.KEYBLACKSTONE;
    
    Stones[][] stones = new Stones[19][19];

    Tuple ko;
    Tuple maybeko;

    ArrayList<Tuple> keystones = new ArrayList<>();
    ArrayList<Tuple> bCapStrings = new ArrayList<Tuple>();
    ArrayList<Tuple> wCapStrings = new ArrayList<Tuple>();
    ArrayList<Tuple> validMoves;

    ArrayList<ArrayList<Tuple>> bStoneStrings = new ArrayList<ArrayList<Tuple>>();
    ArrayList<ArrayList<Tuple>> wStoneStrings = new ArrayList<ArrayList<Tuple>>();


	
    public Board(){
    	addMouseMotionListener(this);
        addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {
            	startTimer();
            }
            public void mouseExited(MouseEvent arg0) {
            	endTimer();
            }
            public void mousePressed(MouseEvent e) {

                int x = calulatePostionOnBoard(e.getX()-boardSize);
                int y =calulatePostionOnBoard(e.getY()-boardSize);

                if (!computer) takeTurn(x, y);

            }
            public void mouseReleased(MouseEvent e) {}
            
        });
        
       


    }
 
	

    public void initBoard(){
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                stones[i][j] = Stones.EMPTY;
                if (GoProject.editormode) stones[i][j] = Stones.INVALID;
            }
        }

        tempkeystone = Stones.KEYBLACKSTONE;
        keystone = Stones.KEYBLACKSTONE;
        placing = Stones.BLACK;
        keystones.clear();
        bStoneStrings.clear();
        bStoneStrings.clear();
        bCapStrings.clear();
        wCapStrings.clear();
        validMoves =getAllValidMoves();
        ko = null;
    }
    
    public static Board cloneBoard(Board oB) {
    	Board nB = new Board();
    	nB.ai = oB.ai;
    	nB.computer = oB.computer;
    	nB.capToWin = oB.capToWin;
    	nB.boardTotal = oB.boardTotal;
    	nB.boardSize = oB.boardSize;
    	nB.placing = oB.placing;
    	nB.turn = oB.turn;
    	nB.tempkeystone = oB.tempkeystone;
    	nB.keystone = oB.keystone;
    	nB.stones = cloneBoardStones(oB.stones);
    	if(oB.ko!=null)nB.ko = oB.ko.clone();
    	
    	nB.keystones = tupleArrayClone(oB.keystones);
    	nB.validMoves = tupleArrayClone(oB.validMoves);
    	nB.bCapStrings = tupleArrayClone(oB.bCapStrings);
    	nB.wCapStrings = tupleArrayClone(oB.wCapStrings);
    	nB.bStoneStrings = twoDTupleArrayClone(oB.bStoneStrings);
    	nB.wStoneStrings = twoDTupleArrayClone(oB.wStoneStrings);
    	
    	return nB;
    } 
    
    public static Stones[][] cloneBoardStones(Stones[][] oS){
    	  Stones[][] nS = new Stones[19][19];
    	  for(int i=0; i<oS.length; i++) {
              for(int j=0; j<oS[i].length; j++) {
            	  nS[i][j] =  oS[i][j];      
              }
          }
    	return nS;
    }
    
    public static ArrayList<ArrayList<Tuple>> twoDTupleArrayClone(ArrayList<ArrayList<Tuple>> ol){
    	ArrayList<ArrayList<Tuple>> nl = new  ArrayList<ArrayList<Tuple>>() ;
    	 for(ArrayList<Tuple> l : ol) {
             nl.add(tupleArrayClone(l));
         }
    	return nl;
    }
    
    public static ArrayList<Tuple> tupleArrayClone(ArrayList<Tuple> s){
        ArrayList<Tuple> l = new ArrayList<Tuple>();
        for(Tuple o : s) {
            l.add(new Tuple(o.a,o.b));
        }
        return l;
    }
   
    public void takeTurn(int i, int j) {
    	if (withinBounds(i,j)) {
            if (stones[i][j]== Stones.KO){
                print("Can't Place On KO");}
            else if (selfCap(i,j,Stones.getEnemyColour(placing))){
                    print("Can't Self Capture");}
            else{
                if (GoProject.editormode){
                    stones[i][j] = placing;
                    removeKo();
                    if (placing== Stones.KEYBLACKSTONE || placing == Stones.KEYWHITESTONE){
                        keystones.add(new Tuple(i,j));
                        for (Tuple k:keystones){
                            stones[k.a][k.b]=placing;
                        }
                        placing = Stones.getStoneColour(placing);}
                    else{
                        keystones.removeIf( new Tuple(i,j)::equals);}}
                else if (stones[i][j] == Stones.EMPTY || stones[i][j] == Stones.VALID) {
                        stones[i][j] = turn;
                        removeKo();
                        turn = (turn == Stones.BLACK) ? Stones.WHITE : Stones.BLACK;
                        if (!ai)computer = !computer;}
                else {
                    print("Stone already there");}
                updateStringsSingle(i,j);}}
        else{
            print("Out of bound");
        }

        updateStringsFull();
        maybeko = null;
        checkForCaps(placing);
        checkForCaps(Stones.getEnemyColour(placing));
        if (ko !=null) stones[ko.a][ko.b] = Stones.KO;
        if (!GoProject.editormode)placing =turn;
        
        
        if(!ai) {
            repaint();
            validMoves =getAllValidMoves();
            GoProject.updateGUI();}
        if (computer) {
        	Minimaxer k = new Minimaxer(this,keystones);
        	Minimaxer.human=Stones.getEnemyColour(turn);
        	k.run();
        	takeTurn(k.choice.a,k.choice.b);
        }
//        print("bstrings" +bStoneStrings);
//        print("bcapStrings"+bCapStrings);

    }

    private void removeKo(){
        if (ko != null){
            stones[ko.a][ko.b] =Stones.VALID;
            ko = null;}
        }

    private boolean selfCap(int i , int j, Stones colour ){

        if (colour != Stones.BLACK && colour != Stones.WHITE) return false;

        ArrayList<Tuple> capString =  (colour== Stones.WHITE ? wCapStrings:  bCapStrings);
        ArrayList<ArrayList<Tuple>> stoneStrings =  (colour== Stones.WHITE ? bStoneStrings:  wStoneStrings);
        ArrayList<Tuple> libs = getLiberties(i, j);
        if (capString.contains(new Tuple(i, j)))  return false;
        
        
        ArrayList<Tuple> sstring= new ArrayList<Tuple>();
        sstring.add(new Tuple(i,j));
        for(Tuple t :libs ){
            if (Stones.getStoneColour(stones[t.a][t.b]) == Stones.getEnemyColour(colour)){
                StoneStringResponse libsStringres = checkForStrings( t.a ,  t.b, stoneStrings);
                if(!libsStringres.state){
                    sstring.add(t);}
                else{
                	//This tupleArrayMerger combines string of liberty stone with string of current stone
                    sstring= tupleArrayMerger(sstring,libsStringres.list);}}
        }
        
        for (Tuple t :getCaptureStringFor(sstring)) {
            if(Stones.getStoneColour(stones[t.a][t.b])!= Stones.getStoneColour(colour)){
                return false;}
        }
        return true;
    }
    
    private ArrayList<Tuple> tupleArrayMerger(ArrayList<Tuple> a,ArrayList<Tuple> b){
    	ArrayList<Tuple> l = new ArrayList<Tuple>();
    	l.addAll(a);
    	for (Tuple t : b ) {
    		if (!l.contains(t)) l.add(t);
    	}
		return l;
    	
    }


    private ArrayList<Tuple> getCaptureStringFor(ArrayList<Tuple> sstring) {

        ArrayList<Tuple> capstring = new ArrayList<Tuple>();
        for (Tuple t : sstring){
            capstring.removeAll(getLiberties(t.a, t.b));
            capstring.addAll(getLiberties(t.a, t.b));
        }
        ArrayList<Tuple> l = tupleArrayClone(sstring);
        capstring.removeAll(l);
        return capstring;
    }

    public boolean checkForCaps( Stones colour) {
        if (Stones.getStoneColour(colour) != Stones.BLACK && Stones.getStoneColour(colour) != Stones.WHITE)  return false;
        ArrayList<Tuple> capString =  (colour== Stones.WHITE ? bCapStrings:  wCapStrings);
        ArrayList<ArrayList<Tuple>> stoneStrings = (colour== Stones.WHITE ? bStoneStrings: wStoneStrings);
        capString.clear();
        boolean anyCap = false;
        for (ArrayList<Tuple> tlist : stoneStrings){
            ArrayList<Tuple> capList = getCaptureStringFor(tlist);
            ArrayList<Tuple> needList = new ArrayList<Tuple>();
            for (Tuple t : capList){
                if (Stones.getStoneColour(stones[t.a][t.b])!=Stones.getStoneColour(colour)) needList.add(t);
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
        if (Stones.getStoneColour(stones[i][j])==Stones.BLACK || Stones.getStoneColour(stones[i][j]) == Stones.WHITE){
            removeOldStoneFromString(i,j,Stones.getStoneColour(stones[i][j])==Stones.BLACK ? wStoneStrings:bStoneStrings);
            ArrayList<ArrayList<Tuple>> stoneStrings = Stones.getStoneColour(stones[i][j])==Stones.BLACK ? bStoneStrings:wStoneStrings;
            StoneStringResponse stringed = checkForStrings(i, j,stoneStrings);
            if (!stringed.state){
                //print("Stone: " + i + ","+ j);
                ArrayList<Tuple> libs =  getLiberties(i,j);
                ArrayList<Tuple> sstring= new ArrayList<Tuple>();
                sstring.add(new Tuple(i,j));
                for(Tuple t :libs ){
                    if ( Stones.getStoneColour(stones[t.a][t.b]) == Stones.getStoneColour(stones[i][j])){
                        StoneStringResponse libsStringres = checkForStrings(t.a,t.b,stoneStrings);
                        if(!libsStringres.state){
                            sstring.add(t);
                            stoneStrings.add(sstring);}
                        else{
                        	//This part combines string of liberty stone with string of current stone
                        	if (libsStringres.list != sstring) {
	                            stoneStrings.remove(libsStringres.list);
	                            stoneStrings.remove(sstring);
	                            sstring = tupleArrayMerger(sstring,libsStringres.list);
	                            stoneStrings.add(sstring);}}}
                }
                if (sstring.size()==1){stoneStrings.add(sstring);}}
            stoneStrings.removeIf(item -> item.isEmpty());}
        else{
            removeOldStoneFromString(i,j,bStoneStrings);
            removeOldStoneFromString(i,j,wStoneStrings);}
    }

    public void updateStringsFull(){
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                 updateStringsSingle(i,j);
            }
        }
    }

    public void removeOldStoneFromString(int  i , int j , ArrayList<ArrayList<Tuple>> stoneStrings ){
        StoneStringResponse stringed = checkForStrings( i ,  j, stoneStrings);
        if (stringed.state){
            stoneStrings.remove(stringed.list);
            updateStringsFull();
        }

    }


    public ArrayList<Tuple> getLiberties(int i , int j){
        ArrayList<Tuple> libs = new ArrayList<Tuple>();
        if (i>=1) libs.add(new Tuple(i-1,j));
        if (i<=17) libs.add(new Tuple(i+1,j));
        if (j>=1)libs.add(new Tuple(i,j-1));
        if (j<=17) libs.add(new Tuple(i,j+1));
        return libs;


    }


    public StoneStringResponse checkForStrings(int i , int j , ArrayList<ArrayList<Tuple>> stoneStrings){


        Tuple k = new Tuple(i, j);
        if (stoneStrings.isEmpty())return new StoneStringResponse(false,null);
        for (ArrayList<Tuple> bStrings : stoneStrings ){
            if (bStrings.contains(k)) {
                return new StoneStringResponse(true,bStrings);};
        }

        return  new StoneStringResponse(false,null);
    }


    public ArrayList<Tuple> getAllValidMoves() {
    	ArrayList<Tuple>  validMoves = new ArrayList<Tuple>();
    	for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if (checkValidMove(i,j)) validMoves.add(new Tuple(i,j));
            }
    	}
    	return validMoves;
    }

    public boolean checkValidMove(int x , int y) {
        if(withinBounds(x,y)){
            if(stones[x][y]== Stones.KO){
                //print("Can't Place On KO");
                return false;}
            else if(selfCap(x,y,Stones.getEnemyColour(turn))){
                    //print("Can't Self Capture");
            		return false;}
            else{
                if (stones[x][y] == Stones.EMPTY || stones[x][y] == Stones.VALID) return true;
                else //print("Stone already there");
                return false;}}
        else{
            //print("Out of bound");
            return false;}

    }




    public void paint(Graphics g ){
        for (int i =boardSize; i < boardTotal; i+=boardSize){
            g.setColor(Color.RED);
            g.drawLine(i,boardSize,i, boardTotal-boardSize);
            g.drawLine(boardSize,i,boardTotal-boardSize, i);
        }


        for(int i = 4; i<17; i+=6) {
        	g.setColor(Color.BLUE);
            g.fillOval(boardSize*i - 3 ,boardSize*4 -3,6,6);
            g.fillOval(boardSize*i - 3 ,boardSize*10 -3,6,6);
            g.fillOval(boardSize*i - 3 ,boardSize*16 -3,6,6);

        }


        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                switch (stones[i][j]) {
                    case BLACK:  drawoval(g,(i+1)*boardSize,(j+1)*boardSize , Color.BLACK, false);
                        break;

                    case WHITE:  drawoval(g,(i+1)*boardSize,(j+1)*boardSize , Color.WHITE, false);
                        break;

                    case VALID:  //drawoval(g,(i+1)*boardSize,(j+1)*boardSize,new Color(0f,1f,0f,.5f ),false);
                        break;

                    case INVALID:  //drawoval( g,(i+1)*boardSize,(j+1)*boardSize ,new Color(1f,0f,0f,.1f),false);
                        break;

                    case KEYBLACKSTONE:
                    case KEYWHITESTONE:
                        if (keystone==Stones.KEYBLACKSTONE) {
                            drawoval(g,(i+1)*boardSize,(j+1)*boardSize, Color.BLACK, true);}
                        else{
                            drawoval(g, (i+1)*boardSize,(j+1)*boardSize, Color.WHITE, true);}

                        break;

                    case KO:
                        drawsquare( g,(i+1)*boardSize,(j+1)*boardSize ,new Color(0f,0f,1f,.5f));
                    break;

                    case EMPTY:
                        break;
                }
            }
        }

        for (Tuple t :validMoves){
        	drawoval(g,(t.a+1)*boardSize,(t.b+1)*boardSize,new Color(0f,1f,0f,.5f ),false);
        }

        if((GoProject.editormode && withinBounds(posX,posY))||checkValidMove(posX,posY)) {
        	drawoval(g,(posX+1)*boardSize,(posY+1)*boardSize,Stones.stoneToColor(placing),Stones.isKey(placing));}
        
    }

    public void drawoval(Graphics g,  int x, int y , Color c , boolean key) {
        g.setColor(c);
        g.fillOval(x-10, y-10, 20, 20);
        if (key){
            g.setColor(new Color(255,175,55));
            g.drawOval(x-7, y-7, 14, 14);}
        else{
            g.setColor(Color.BLACK);}

        g.drawOval(x-10, y-10, 20, 20);
    }

    public void drawsquare(Graphics g,  int x, int y , Color c) {
        g.setColor(c);
        g.fillRect(x-10, y-10, 20, 20);
    }


    public int calulatePostionOnBoard(int x){
    	if ((x % boardSize <= (boardSize*3/7))){
    		return (x - (x % boardSize)) /boardSize;} 
    	else if ((x % boardSize >= (boardSize*4/7))) {
    		return ((x - (x % boardSize)) +  boardSize)/boardSize;}
    	else return -1;
    }



    public static void print(Object o){
        System.out.println(o);
    }
   
    private void startTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            	repaint();

            }
        };

        timer = new Timer(true);
        timer.schedule(task, 0, 100);
    }
    
    private void endTimer() {
    	if (timer!=null) {
    		posX = -1;
    		posY = -1;
    		repaint();
    		timer.cancel();
    	}
    }

    public boolean withinBounds(int x, int y){
    	if(x <= 18 && x>=0 && y <= 18 && y>=0) return true;
    	return false;
    	
    }
    
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		posX = calulatePostionOnBoard(e.getX()-boardSize);
    	posY = calulatePostionOnBoard(e.getY()-boardSize);
	}


}
