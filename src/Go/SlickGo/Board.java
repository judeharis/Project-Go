package Go.SlickGo;


import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;




public class Board{

    boolean blackFirst;
    boolean capToWin;
    boolean passing;
    
	int gcsize = 1000;
	int boardSize = gcsize * 4 /5;
	int TileSize = boardSize/20;
	int stoneSize = TileSize;
	static float fullTime = 0;
	static long[] arrayTimes = new long[10];
	static float takeTurnTime = 0;
	String desc ="No Problem Loaded";


	
	
    Stone placing= Stone.BLACK;
    Stone turn = Stone.BLACK;
    Stone keystone = Stone.KEYBLACKSTONE;
    
    Stone[][] stones = new Stone[19][19];
    
    Board resetboard;
    Board undoBoard;
    Board redoBoard;

    Tuple ko;
    Tuple maybeko;

    ArrayList<Tuple> keystones = new ArrayList<>();
    ArrayList<Tuple> bCapStrings = new ArrayList<Tuple>();
    ArrayList<Tuple> wCapStrings = new ArrayList<Tuple>();
    ArrayList<Tuple> validMoves;

    ArrayList<ArrayList<Tuple>> bStoneStrings = new ArrayList<ArrayList<Tuple>>();
    ArrayList<ArrayList<Tuple>> wStoneStrings = new ArrayList<ArrayList<Tuple>>();

    
    public boolean takeTurn(int i, int j, boolean  editormode , boolean check) {

    	boolean moveMade = false;

    	if (withinBounds(i,j) && !passing) {
            if (stones[i][j]== Stone.KO){
                print("Can't Place On KO");}
            else if (selfCap(i,j,placing.getEnemyColour())){
                print("Can't Self Capture");}
            else{
                if (editormode){
                    stones[i][j] = placing;
                    removeKo();
                    if (placing== Stone.KEYBLACKSTONE || placing == Stone.KEYWHITESTONE){
                        keystones.add(new Tuple(i,j));
                        for (Tuple k:keystones){
                            stones[k.a][k.b]=placing;
                        }
                        placing = placing.getStoneColour();}
                    else{
                        keystones.removeIf( new Tuple(i,j)::equals);}}
                else if (stones[i][j] == Stone.EMPTY || stones[i][j] == Stone.VALID) {
                	    undoBoard = cloneBoard(this);
                	    redoBoard = null;
                        stones[i][j] = turn;
                        moveMade=true;
                        removeKo();
                        turn = turn.getEnemyColour();}
                else if(!check) print("Invalid Move");
                updateStringsSingle(i,j);}}
        else if (passing) {
        	moveMade=true;
        	removeKo();
        	if (!check) print(turn + " passed");
        	turn = turn.getEnemyColour();
        	passing=false;}
        else print("Out of bound");


        updateStringsFull();

        maybeko = null;
     
        checkForCaps(placing);
        checkForCaps(placing.getEnemyColour());



        if (ko !=null) stones[ko.a][ko.b] = Stone.KO;
        if (!editormode)placing =turn;

        validMoves =getAllValidMoves();


        return moveMade;

    }


    public void initBoard(boolean editormode){
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                stones[i][j] = Stone.EMPTY;
                if (editormode) stones[i][j] = Stone.INVALID;
            }
        }

        keystone = Stone.KEYBLACKSTONE;
        placing = Stone.BLACK;
        blackFirst = true;
        capToWin = true;
        keystones.clear();
        bStoneStrings.clear();
        wStoneStrings.clear();
        bCapStrings.clear();
        wCapStrings.clear();
        validMoves =getAllValidMoves();
        passing=false;
        ko = null;
        resetboard =cloneBoard(this);
        //undoBoard =cloneBoard(this);
    }
    
    public static Board cloneBoard(Board oB) {
    	Board nB = new Board();
    	nB.capToWin = oB.capToWin;
    	nB.boardSize = oB.boardSize;
    	nB.placing = oB.placing;
    	nB.turn = oB.turn;
    	nB.keystone = oB.keystone;
    	nB.stones = cloneBoardStones(oB.stones);
    	if(oB.ko!=null)nB.ko = oB.ko.clone();
    	
    	nB.keystones = tupleArrayClone(oB.keystones);
    	nB.validMoves = tupleArrayClone(oB.validMoves);
    	nB.bCapStrings = tupleArrayClone(oB.bCapStrings);
    	nB.wCapStrings = tupleArrayClone(oB.wCapStrings);
    	nB.bStoneStrings = twoDTupleArrayClone(oB.bStoneStrings);
    	nB.wStoneStrings = twoDTupleArrayClone(oB.wStoneStrings);
    	nB.passing = oB.passing;
    	nB.blackFirst = oB.blackFirst;
    	nB.desc = oB.desc;
    	nB.resetboard = oB.resetboard;
    	nB.undoBoard = oB.undoBoard;
      	nB.redoBoard = oB.redoBoard;
    	return nB;
    } 

    public boolean checkForCaps( Stone colour) {
    	
        if (colour.getStoneColour() != Stone.BLACK && colour.getStoneColour() != Stone.WHITE)  {
        	checkForCaps(Stone.BLACK);
        	checkForCaps(Stone.WHITE);
        	return false;
        }
        ArrayList<Tuple> capString =  (colour== Stone.WHITE ? bCapStrings:  wCapStrings);
        ArrayList<ArrayList<Tuple>> stoneStrings = (colour== Stone.WHITE ? bStoneStrings: wStoneStrings);
        capString.clear();
        boolean anyCap = false;
        for (ArrayList<Tuple> tlist : stoneStrings){
            ArrayList<Tuple> capList = getCaptureStringFor(tlist);
            ArrayList<Tuple> needList = new ArrayList<Tuple>();
            for (Tuple t : capList){
                if (stones[t.a][t.b].getStoneColour()!=colour.getStoneColour()) needList.add(t);
            }
            
            if(needList.size()==1){
                capString.add(needList.get(0));
                if (needList.get(0).equals(maybeko) && tlist.size() == 1) ko = maybeko;}
            else if(needList.size()==0){
                removeStonesOnBoard(tlist);
                anyCap=true;
                if(tlist.size() == 1) maybeko= new Tuple(tlist.get(0).a,tlist.get(0).b);}
        }
        return anyCap;

    }

    public void updateStringsFull(){
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                 updateStringsSingle(i,j);
            }
        }
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

    public ArrayList<Tuple> removeBadMoves() {
        ArrayList<ArrayList<Tuple>> stoneStrings = turn.getStoneColour()==Stone.BLACK ? bStoneStrings:wStoneStrings;
        ArrayList<Tuple> capString =  (turn.getStoneColour()== Stone.WHITE ? bCapStrings:  wCapStrings);
        ArrayList<Tuple> goodMoves = tupleArrayClone(validMoves);
        for (ArrayList<Tuple> tlist : stoneStrings){
        	ArrayList<Tuple> needList = getNeedList(tlist,turn.getEnemyColour());
        	if (needList.size() == 2) {
        		for (Tuple t : needList ) {
        			Board k = Board.cloneBoard(this);
        			if (stones[t.a][t.b]!= Stone.INVALID) {
	        			k.takeTurn(t.a, t.b, false, true);
	        			if(turn.getStoneColour()== Stone.WHITE && k.wCapStrings.size() > wCapStrings.size() && !capString.contains(t)){
	        				goodMoves.remove(t);}
	        			if(turn.getStoneColour()== Stone.BLACK && k.bCapStrings.size() > bCapStrings.size() && !capString.contains(t)){
	        				goodMoves.remove(t);}}}}
        }
        return goodMoves;
    }
    
    public ArrayList<Tuple> removeBadMovess() {
        ArrayList<ArrayList<Tuple>> stoneStrings = turn.getStoneColour()==Stone.BLACK ? bStoneStrings:wStoneStrings;
        ArrayList<Tuple> capString =  turn.getStoneColour()== Stone.BLACK ? wCapStrings: bCapStrings;
        ArrayList<Tuple> goodMoves = tupleArrayClone(validMoves);
        for (ArrayList<Tuple> tlist : stoneStrings){
        	ArrayList<Tuple> needList = getNeedList(tlist,turn.getEnemyColour());
        	if (needList.size() == 2) {
        		for (Tuple t : needList ) {
        			boolean toBreak = false;
        			if (capString.contains(t)) continue;
        			ArrayList<Tuple> libs=  getLiberties(t.a, t.b);
        			for (Tuple l : libs) {
        				if (stones[l.a][l.b].getStoneColour() != Stone.WHITE && stones[l.a][l.b].getStoneColour() != Stone.BLACK && !needList.contains(l)) {
        					toBreak = true;
        					break;
        				}else if(stones[l.a][l.b].getStoneColour() == turn ){
                   		 	StoneStringResponse libsStringres = checkForStrings( l.a ,  l.b, stoneStrings);
                   		 	if (libsStringres.state && getNeedList(libsStringres.list, turn.getEnemyColour()).size() >2)  {
                   		 		toBreak = true;
                   		 		break;}}
        			}
        			if(!toBreak)goodMoves.remove(t);
        			
        		}
        	}
        }
        return goodMoves;
    }
    
    
    public boolean checkValidMove(int x , int y) {
        if(withinBounds(x,y) && stones[x][y]!= Stone.KO && (stones[x][y] == Stone.EMPTY || stones[x][y] == Stone.VALID) && !selfCap(x,y,turn.getEnemyColour()))
    			return true;
        return false;

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
                        if (keystone==Stone.KEYBLACKSTONE) {
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
    
 
    

//OG working one
    
//    private boolean selfCap(int i , int j, Stone enemycolour ){
//        long start = System.nanoTime();
//
//        if (enemycolour != Stone.BLACK && enemycolour != Stone.WHITE) {
//            long end = System.nanoTime();
//            fullTime += (end - start );
//            return false;
//        }
//        ArrayList<Tuple> capString =  (enemycolour== Stone.WHITE ? wCapStrings:  bCapStrings);
//        ArrayList<ArrayList<Tuple>> stoneStrings =  (enemycolour== Stone.WHITE ? bStoneStrings:  wStoneStrings);
//        ArrayList<Tuple> libs = getLiberties(i, j);
//        if (capString.contains(new Tuple(i, j))) {
//            long end = System.nanoTime();
//            fullTime += (end - start );
//        	return false;
//        }
//        
//    	
//
//        ArrayList<Tuple> sstring= new ArrayList<Tuple>();
//        sstring.add(new Tuple(i,j));
//        for(Tuple t :libs ){
//            if (stones[t.a][t.b].getStoneColour() == enemycolour.getEnemyColour()){
//                StoneStringResponse libsStringres = checkForStrings( t.a ,  t.b, stoneStrings);
//                if(!libsStringres.state){
//                    sstring.add(t);}
//                else{
//                	//This tupleArrayMerger combines string of liberty stone with string of current stone
//                    sstring= tupleArrayMerger(sstring,libsStringres.list);}}
//        }
// 
//        
//        for (Tuple t :getCaptureStringFor(sstring)) {
//            if(stones[t.a][t.b].getStoneColour()!=enemycolour.getStoneColour()){
//                long end = System.nanoTime();
//                fullTime += (end - start );
//                return false;}
//        }
//        long end = System.nanoTime();
//        fullTime += (end - start );
//        return true;
//    }
    
    
    
	public int getSafeStringsCount(Stone colour) {
		int retval = 0;
		ArrayList<ArrayList<Tuple>> stoneStrings =  (colour== Stone.WHITE ? wStoneStrings:  bStoneStrings);
		for (ArrayList<Tuple> list :stoneStrings) {
			retval += checkStringSafety(list, colour);
		}
		return retval;
	}
	
	
	public int checkStringSafety(ArrayList<Tuple> list,Stone colour) {
		if (colour.getStoneColour() !=Stone.BLACK && colour.getStoneColour() !=Stone.WHITE) return 0;
		ArrayList<Tuple> needList = getNeedList(list,colour.getEnemyColour());
		if (needList.size() >= 2) {
			int eyes = 0;
    		for (Tuple t : needList ) {
    			ArrayList<Tuple> sur = getSurrounding(t.a,t.b);
    			ArrayList<Tuple> libs = getLiberties(t.a,t.b);
    			int surrondingNumber = sur.size();
    			for (Tuple l : sur) {
    				if (stones[l.a][l.b].getStoneColour() != Stone.BLACK && stones[l.a][l.b].getStoneColour() != Stone.WHITE && !isAdjacentAll(l,colour)) surrondingNumber--;
    				if (libs.contains(l) && needList.contains(l)) surrondingNumber=0;
    				else if(stones[l.a][l.b].getStoneColour() == colour.getEnemyColour()) {
    					StoneStringResponse stringRes  = checkForStrings(l.a,l.b,colour.getEnemyColour().getSStrings(this));
    					if (stringRes.state) {
    						ArrayList<Tuple> eNeedList = getNeedList(stringRes.list,colour);
    						if (eNeedList.size()!=1)surrondingNumber--;
    						else if (eNeedList.size()==1 && !eNeedList.get(0).equals(t)) surrondingNumber--; }}
    			}
    			if (sur.size() == 3 && surrondingNumber >1 ) eyes++;
    			else if(sur.size() == 5 && surrondingNumber >4 )eyes++;
    			else if(sur.size() == 8 && surrondingNumber >6 )eyes++;
    			
    		}
    		if (eyes>=2) return 1;
    	}
		return 0;
		
	}
	
	public boolean isAdjacentAll(Tuple t,Stone colour) {
		ArrayList<Tuple> libs = getLiberties(t.a,t.b);
		for(Tuple l : libs) {
			if (stones[l.a][l.b] != colour)return false;
		}
		return true;
		
	}
    
    private boolean selfCap(int i , int j, Stone enemycolour ){

        if (enemycolour != Stone.BLACK && enemycolour != Stone.WHITE) return false;
        
        ArrayList<Tuple> enemyCapString =  (enemycolour== Stone.WHITE ? wCapStrings:  bCapStrings);
        ArrayList<Tuple> currentCapString =  (enemycolour== Stone.WHITE ? bCapStrings:  wCapStrings);
        ArrayList<ArrayList<Tuple>> stoneStrings =  (enemycolour== Stone.WHITE ? bStoneStrings:  wStoneStrings);
        

        if (enemyCapString.contains(new Tuple(i, j))) return false;
        
        ArrayList<Tuple> libs = getLiberties(i, j);
        if (currentCapString.contains(new Tuple(i, j))) {
            for (Tuple t :libs) {
            	if (stones[t.a][t.b].getStoneColour() == enemycolour.getEnemyColour()){
            		 StoneStringResponse libsStringres = checkForStrings( t.a ,  t.b, stoneStrings);
            		 if (libsStringres.state && getNeedList(libsStringres.list, enemycolour).size() >1)  return false;}
            	else if (stones[t.a][t.b].getStoneColour() != Stone.BLACK && stones[t.a][t.b].getStoneColour() != Stone.WHITE)return false;
            }
        	return true;}  
        
        
        for (Tuple t :libs) {
		      if(stones[t.a][t.b].getStoneColour()!=enemycolour.getStoneColour()) return false;
		}

        return true;
    }
 
    private void removeKo(){
        if (ko != null){
            stones[ko.a][ko.b] =Stone.VALID;
            ko = null;}
    }
    
    private void updateStringsSingle(int i , int j){
        if (stones[i][j].getStoneColour()==Stone.BLACK || stones[i][j].getStoneColour() == Stone.WHITE){
            removeOldStoneFromString(i,j,stones[i][j].getStoneColour()==Stone.BLACK ? wStoneStrings:bStoneStrings);
            ArrayList<ArrayList<Tuple>> stoneStrings = stones[i][j].getStoneColour()==Stone.BLACK ? bStoneStrings:wStoneStrings;
            StoneStringResponse stringed = checkForStrings(i, j,stoneStrings);
            if (!stringed.state){
                ArrayList<Tuple> libs =  getLiberties(i,j);
                ArrayList<Tuple> sstring= new ArrayList<Tuple>();
                sstring.add(new Tuple(i,j));
                for(Tuple t :libs ){
                    if (stones[t.a][t.b].getStoneColour() == stones[i][j].getStoneColour()){
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
    
    public ArrayList<Tuple> getCaptureStringFor(ArrayList<Tuple> sstring) {

        ArrayList<Tuple> capstring = new ArrayList<Tuple>();
        for (Tuple t : sstring){
            capstring.removeAll(getLiberties(t.a, t.b));
            capstring.addAll(getLiberties(t.a, t.b));
        }
        ArrayList<Tuple> l = tupleArrayClone(sstring);
        capstring.removeAll(l);
        return capstring;
    }
    
    public ArrayList<Tuple> getNeedList(ArrayList<Tuple> sstring,Stone enemycolour) {
    	ArrayList<Tuple> needList = new ArrayList<Tuple>();
        ArrayList<Tuple> capList = getCaptureStringFor(sstring);
        for (Tuple t : capList){
            if (stones[t.a][t.b].getStoneColour()!=enemycolour.getStoneColour()) needList.add(t);
        }
        return needList;
    }
    
    
    
    private ArrayList<Tuple> tupleArrayMerger(ArrayList<Tuple> a,ArrayList<Tuple> b){
    	ArrayList<Tuple> l = new ArrayList<Tuple>();
    	l.addAll(a);
    	for (Tuple t : b ) {
    		if (!l.contains(t)) l.add(t);
    	}
		return l;
    	
    }
    
    private void removeStonesOnBoard(ArrayList<Tuple> tlist){
        for(Tuple t : tlist){
            stones[t.a][t.b] = Stone.VALID;
        }
    }
   
    private static ArrayList<ArrayList<Tuple>> twoDTupleArrayClone(ArrayList<ArrayList<Tuple>> ol){
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
    
    private static Stone[][] cloneBoardStones(Stone[][] oS){
    	Stone[][] nS = new Stone[19][19];
    	  for(int i=0; i<oS.length; i++) {
              for(int j=0; j<oS[i].length; j++) {
            	  nS[i][j] =  oS[i][j];      
              }
          }
    	return nS;
    }
    
    private void removeOldStoneFromString(int  i , int j , ArrayList<ArrayList<Tuple>> stoneStrings ){
        StoneStringResponse stringed = checkForStrings( i ,  j, stoneStrings);
        if (stringed.state){
            stoneStrings.remove(stringed.list);
            updateStringsFull();
        }

    }
    
    private ArrayList<Tuple> getLiberties(int i , int j){
        ArrayList<Tuple> libs = new ArrayList<Tuple>();
        if (i>=1) libs.add(new Tuple(i-1,j));
        if (i<=17) libs.add(new Tuple(i+1,j));
        if (j>=1)libs.add(new Tuple(i,j-1));
        if (j<=17) libs.add(new Tuple(i,j+1));
        return libs;
    }
    
    private ArrayList<Tuple> getSurrounding(int i , int j){
        ArrayList<Tuple> sur = getLiberties(i,j);
        if (i>=1 && j>=1) sur.add(new Tuple(i-1,j-1));
        if (i<=17 && j<=17) sur.add(new Tuple(i+1,j+1));
        if (i<=17 && j>=1)sur.add(new Tuple(i+1,j-1));
        if (i>=1 && j<=17) sur.add(new Tuple(i-1,j+1));
        return sur;
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
    
   
   
    
}


