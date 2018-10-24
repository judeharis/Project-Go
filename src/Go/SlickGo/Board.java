package Go.SlickGo;


import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;



public class Board{

	boolean ai = true;
    boolean computer = false;
    boolean blackFirst;
    boolean capToWin;
    boolean passing;
    
	int gcsize = 1000;
	int boardSize = gcsize * 4 /5;
	int TileSize = boardSize/20;
	int stoneSize = TileSize;

	
	String desc ="No Problem Loaded";


	
	
    Stones placing= Stones.BLACK;
    Stones turn = Stones.BLACK;
    Stones keystone = Stones.KEYBLACKSTONE;
    
    Stones[][] stones = new Stones[19][19];
    
    Board resetboard;

    Tuple ko;
    Tuple maybeko;

    ArrayList<Tuple> keystones = new ArrayList<>();
    ArrayList<Tuple> bCapStrings = new ArrayList<Tuple>();
    ArrayList<Tuple> wCapStrings = new ArrayList<Tuple>();
    ArrayList<Tuple> validMoves;
    ArrayList<Tuple> goodMoves;

    ArrayList<ArrayList<Tuple>> bStoneStrings = new ArrayList<ArrayList<Tuple>>();
    ArrayList<ArrayList<Tuple>> wStoneStrings = new ArrayList<ArrayList<Tuple>>();

    
    public void takeTurn(int i, int j, boolean  editormode , boolean check) {
    	
    	if (withinBounds(i,j) && !passing) {
            if (stones[i][j]== Stones.KO){
                print("Can't Place On KO");}
            else if (selfCap(i,j,Stones.getEnemyColour(placing))){
                print("Can't Self Capture");}
            else{
                if (editormode){
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
                        turn = Stones.getEnemyColour(turn);
                        if (!ai)computer = !computer;}
                else {
                	
                	if(!check) print("Invalid Move");}
                updateStringsSingle(i,j);}}
        else if (passing) {
        	removeKo();
        	if (!ai) print(turn + " passed");
        	turn = Stones.getEnemyColour(turn);
        	if (!ai)computer = !computer;
        	passing=false;
        	}
        else print("Out of bound");
        
    	
        updateStringsFull();
        maybeko = null;
        checkForCaps(placing);
        checkForCaps(Stones.getEnemyColour(placing));
        if (ko !=null) stones[ko.a][ko.b] = Stones.KO;
        if (!editormode)placing =turn;
        
        validMoves =getAllValidMoves();
	    if(!check)goodMoves = removeBadMovess();
	    

	    ArrayList<Tuple> liveList = Minimaxer.keyStoneRemaining(this,keystones);

        if (computer && !liveList.isEmpty() && !check) {
        	Minimaxer k = new Minimaxer(this,keystones);
        	k.run();
        	
    		if(k.choice == null && !this.validMoves.isEmpty()) k.choice= this.validMoves.get(0);
    		else if(k.choice == null && capToWin) {
    			this.passing=true;
    			k.choice= new Tuple(-9,-9);}
        	if (k.choice != null)takeTurn(k.choice.a,k.choice.b , editormode,false);}

        
        
//        print("goodMoves" +goodMoves);
//        print("bstrings" +bStoneStrings);
//        print("bcapStrings"+bCapStrings);

    }


    public void initBoard(boolean editormode){
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                stones[i][j] = Stones.EMPTY;
                if (editormode) stones[i][j] = Stones.INVALID;
            }
        }

        keystone = Stones.KEYBLACKSTONE;
        placing = Stones.BLACK;
        blackFirst = true;
        capToWin = true;
        keystones.clear();
        bStoneStrings.clear();
        bStoneStrings.clear();
        bCapStrings.clear();
        wCapStrings.clear();
        validMoves =getAllValidMoves();
        goodMoves =removeBadMovess();
        passing=false;
        ko = null;
        resetboard =cloneBoard(this);
    }
    
    public static Board cloneBoard(Board oB) {
    	Board nB = new Board();
    	nB.ai = oB.ai;
    	nB.computer = oB.computer;
    	nB.capToWin = oB.capToWin;
    	nB.boardSize = oB.boardSize;
    	nB.placing = oB.placing;
    	nB.turn = oB.turn;
    	nB.keystone = oB.keystone;
    	nB.stones = cloneBoardStones(oB.stones);
    	if(oB.ko!=null)nB.ko = oB.ko.clone();
    	
    	nB.keystones = tupleArrayClone(oB.keystones);
    	nB.validMoves = tupleArrayClone(oB.validMoves);
    	nB.goodMoves = tupleArrayClone(oB.goodMoves);
    	nB.bCapStrings = tupleArrayClone(oB.bCapStrings);
    	nB.wCapStrings = tupleArrayClone(oB.wCapStrings);
    	nB.bStoneStrings = twoDTupleArrayClone(oB.bStoneStrings);
    	nB.wStoneStrings = twoDTupleArrayClone(oB.wStoneStrings);
    	nB.passing = oB.passing;
    	nB.blackFirst = oB.blackFirst;
    	nB.desc = oB.desc;
    	nB.resetboard = nB;
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
            if(Stones.getStoneColour(stones[t.a][t.b])!=Stones.getStoneColour(colour)){
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
                if (needList.get(0).equals(maybeko) && tlist.size() == 1) ko = maybeko;}
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
                ArrayList<Tuple> libs =  getLiberties(i,j);
                ArrayList<Tuple> sstring= new ArrayList<Tuple>();
                sstring.add(new Tuple(i,j));
                for(Tuple t :libs ){
                    if (Stones.getStoneColour(stones[t.a][t.b]) == Stones.getStoneColour(stones[i][j])){
                        StoneStringResponse libsStringres = checkForStrings(t.a,t.b,stoneStrings);
                        if(libsStringres.state){
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
    
    
    
    
    public ArrayList<Tuple> removeBadMovess() {
        ArrayList<ArrayList<Tuple>> stoneStrings = Stones.getStoneColour(turn)==Stones.BLACK ? bStoneStrings:wStoneStrings;
        ArrayList<Tuple> capString =  (Stones.getStoneColour(turn)== Stones.WHITE ? bCapStrings:  wCapStrings);
        ArrayList<Tuple> goodMoves = tupleArrayClone(validMoves);
        for (ArrayList<Tuple> tlist : stoneStrings){
        	ArrayList<Tuple> capList = getCaptureStringFor(tlist);
        	ArrayList<Tuple> needList = new ArrayList<Tuple>();
            for (Tuple t : capList){
                if (Stones.getStoneColour(stones[t.a][t.b])!=Stones.getEnemyColour(turn)) needList.add(t);
            }
        	if (needList.size() == 2) {
        		for (Tuple t : needList ) {
        			Board k = Board.cloneBoard(this);
        			if (stones[t.a][t.b]!= Stones.INVALID) {
	        			k.takeTurn(t.a, t.b, false, true);
	        			if(Stones.getStoneColour(turn)== Stones.WHITE && k.wCapStrings.size() > wCapStrings.size() && !capString.contains(t)){
	        				goodMoves.remove(t);}
	        			if(Stones.getStoneColour(turn)== Stones.BLACK && k.bCapStrings.size() > bCapStrings.size() && !capString.contains(t)){
	        				goodMoves.remove(t);}}}}
        }
        return goodMoves;
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

    private void removeKo(){
        if (ko != null){
            stones[ko.a][ko.b] =Stones.VALID;
            ko = null;}
    }


    public void draw(Graphics g,boolean editormode) {
    	
		g.setBackground(Color.lightGray);
        for (float i =TileSize; i < boardSize; i+=TileSize){
            g.setColor(Color.red);
            g.drawLine(i,TileSize,i, boardSize-TileSize);
            g.drawLine(TileSize,i,boardSize-TileSize, i);
        }
        
        for(int i = 4; i<17; i+=6) {
        	g.setColor(Color.blue);
            g.fillOval(TileSize*i - 3 ,TileSize*4 -3,6,6);
            g.fillOval(TileSize*i - 3 ,TileSize*10 -3,6,6);
            g.fillOval(TileSize*i - 3 ,TileSize*16 -3,6,6);

        }
        
        
        
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                switch (stones[i][j]) {
                    case BLACK:  drawoval(g,(i+1)*TileSize,(j+1)*TileSize , Color.black, false);
                        break;

                    case WHITE:  drawoval(g,(i+1)*TileSize,(j+1)*TileSize , Color.white, false);
                        break;

                    case VALID:  if (editormode)drawoval(g,(i+1)*TileSize,(j+1)*TileSize,new Color(0f,1f,0f,.5f ),false);
                        break;

                    case INVALID:  //drawoval( g,(i+1)*TileSize,(j+1)*TileSize ,new Color(1f,0f,0f,.1f),false);
                        break;

                    case KEYBLACKSTONE:
                    case KEYWHITESTONE:
                        if (keystone==Stones.KEYBLACKSTONE) {
                            drawoval(g,(i+1)*TileSize,(j+1)*TileSize, Color.black, true);}
                        else{
                            drawoval(g, (i+1)*TileSize,(j+1)*TileSize, Color.white, true);}

                        break;

                    case KO:
                        drawsquare( g,(i+1)*TileSize,(j+1)*TileSize ,new Color(0f,0f,1f,.5f));
                    break;

                    case EMPTY:
                        break;
                }
                
            }
        }
        
        for (Tuple t :validMoves){
        	drawoval(g,(t.a+1)*TileSize,(t.b+1)*TileSize,new Color(0f,1f,0f,.5f ),false);
        }
    	
    	
    	
    }
    
    


    public void drawoval(Graphics g,  int x, int y , Color c , boolean key) {
    	int r = (stoneSize/2);
    	x = x - r;
    	y = y - r;
        g.setColor(c);
        g.fillOval(x, y, stoneSize, stoneSize);
        if (key){
            g.setColor(new Color(255,175,55));
            g.drawOval(x+(r/2), y+(r/2),r, r);
            }
        else{
            g.setColor(Color.black);}

        g.drawOval(x, y, stoneSize, stoneSize);
    }

    public void drawsquare(Graphics g,  int x, int y , Color c) {
        g.setColor(c);
        g.fillRect(x-(stoneSize/2), y-(stoneSize/2), stoneSize, stoneSize);
    }


    public static void print(Object o){
        System.out.println(o);
        Play.gameMsg=(String)o;
    }
   
    public int calulatePostionOnBoard(int x){
    	if ((x % TileSize <= (TileSize*3/7))){
    		return ((x - (x % TileSize)) /TileSize);} 
    	else if ((x % TileSize >= (TileSize*4/7))) {
    		return (((x - (x % TileSize)) +  TileSize)/TileSize);}
    	else return -1;
    }

    public boolean withinBounds(int x, int y){
    	if(x <= 18 && x>=0 && y <= 18 && y>=0) return true;
    	return false;
    	
    }


    
}


