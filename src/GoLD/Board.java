package GoLD;


import java.util.ArrayList;
import java.util.Stack;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;




public class Board{

    boolean blackFirst;

    boolean passing;
    
	
    static int gcsize =  ((SlickGo.gcheigth-50)/50)*50;
	static int boardSize = (gcsize%100==0?gcsize-50:gcsize);
	static int TileSize = (((boardSize/18)/10) *10);
	
	public static int sPx =0;
	public static int sPy =0;
	
	static boolean showCoord = true;

	static float fullTime = 0;
	static long[] arrayTimes = new long[10];
	static float takeTurnTime = 0;
	String desc ="No Problem Loaded";
	String boardString;

	
	
    public Stone placing= Stone.BLACK;
    Stone turn = Stone.BLACK;
    Stone keystone = Stone.KEYBLACKSTONE;
    
    Stone[][] stones = new Stone[19][19];

    char[][] chars = new char[19][19];
	int[][] distance = new int[19][19];
    
    Board resetboard;
    Board undoBoard;
    Board redoBoard;
   
    Stack<String> undoString =  new Stack<String>();
    Stack<String> redoString =  new Stack<String>();
    


    Tuple ko;
    Tuple maybeko;

    ArrayList<Tuple> keystones = new ArrayList<Tuple>();
    ArrayList<Tuple> bCapStrings = new ArrayList<Tuple>();
    ArrayList<Tuple> wCapStrings = new ArrayList<Tuple>();
    
    ArrayList<Tuple> bCappedStrings = new ArrayList<Tuple>();
    ArrayList<Tuple> wCappedStrings = new ArrayList<Tuple>();
    ArrayList<Tuple> validMoves;


    ArrayList<ArrayList<Tuple>> bStoneStrings = new ArrayList<ArrayList<Tuple>>();
    ArrayList<ArrayList<Tuple>> wStoneStrings = new ArrayList<ArrayList<Tuple>>();
    
    


    
    

    
    public boolean takeTurn(int i, int j, boolean  editormode , boolean check) {

    	boolean moveMade = false;
		if(editormode)removeKo();
    	if(i==-9 && j ==-9) passing =true;
		if (withinBounds(i,j) && !passing) {
			if (stones[i][j]== Stone.KO)printSent("Can't Place On KO", check, editormode); 
			else if (selfCap(i,j,placing.getEC()))printSent("Can't Self Capture", check, editormode); 
			else if (editormode){
				
				
				
				
				undoBoard = cloneBoard(this);
				redoBoard = null;
				stones[i][j] = placing;
				removeKo();
				if (placing== Stone.KEYBLACKSTONE || placing == Stone.KEYWHITESTONE){
					keystones.add(new Tuple(i,j));
					for (Tuple k:keystones) {
						stones[k.a][k.b]=placing;
					}
					placing = placing.getSC();
				}else keystones.removeIf( new Tuple(i,j)::equals);
				
				
			         
			}else if (stones[i][j] == Stone.VALID) {     
				boardString = boardToString();
				undoString.push(boardString);    	
				stones[i][j] = turn;
				moveMade=true;
				removeKo();
				turn = turn.getEC();
			}else if(!false) printSent("Invalid Move", check, editormode);
		}else if (passing) {
			passing=false;
			moveMade=true;
			if (!check) print(turn + " passed");    
			boardString = boardToString();
			undoString.push(boardString);
			removeKo();
			turn = turn.getEC();
		}else printSent("Out of bound", check, editormode);


		updateStringsFull();
		maybeko = null;  
		checkForCaps(placing,editormode);
		checkForCaps(placing.getEC(),editormode);
		if (ko !=null) stones[ko.a][ko.b] = Stone.KO;
		if (!editormode)placing =turn;
		validMoves =getAllValidMoves();
		

		boardString = boardToString();
		if(moveMade)redoString.clear();

		return moveMade;

    }

    public Stone[][] getStones(){
    	return this.stones;
    }
    
    

    
    
    

    public boolean checkForCaps(Stone colour,boolean editormode) {
    	
        if (!colour.isStone())  {
        	checkForCaps(Stone.BLACK,editormode);
        	checkForCaps(Stone.WHITE,editormode);
        	return false;
        }
        
        ArrayList<Tuple> capString = colour.getEC().getCapList(this);
        ArrayList<Tuple> cappedStrings = colour.getEC().getCappedList(this);
        ArrayList<ArrayList<Tuple>> stoneStrings =  colour.getEC().getSStrings(this);
        capString.clear();
        cappedStrings.clear();
        boolean anyCap = false;
        for (ArrayList<Tuple> tlist : stoneStrings){
            ArrayList<Tuple> needList = getNeedList(tlist,colour,true);
            if(needList.size()==1){
                capString.add(needList.get(0));
                if (needList.get(0).equals(maybeko) && tlist.size() == 1) ko = maybeko;
            }else if(needList.size()==0){
                cappedStrings.addAll(tlist);
                removeStonesOnBoard(tlist,editormode);
                anyCap=true;
                if(tlist.size() == 1) maybeko= new Tuple(tlist.get(0).a,tlist.get(0).b);
            }
        }
        return anyCap;

    }
    
    
    public ArrayList<Tuple> getAllValidMoves() {
    	ArrayList<Tuple>  validMoves = new ArrayList<Tuple>();
    	for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if (checkValidMove(i,j)) validMoves.add(new Tuple(i,j));
            }
    	}
    	if(turn==keystone.getSC())validMoves.add(new Tuple(-9,-9));
    	else if (turn==keystone.getEC() && validMoves.isEmpty() && ko!=null) validMoves.add(new Tuple(-9,-9));
    	return validMoves;
    }

    
    public ArrayList<Tuple> getAllValidMovesEditorMode() {
    	ArrayList<Tuple>  validPoint = new ArrayList<Tuple>();
    	for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if(stones[i][j]==Stone.VALID)validPoint.add(new Tuple(i,j));
            }
    	}
    	return validPoint;
    }
    
    
    public ArrayList<Tuple> removeBadMovess() {
        ArrayList<ArrayList<Tuple>> stoneStrings = turn.getSC().getSStrings(this);
        ArrayList<Tuple> capString =  turn.getEC().getCapList(this);
        ArrayList<Tuple> goodMoves = tupleArrayClone(validMoves);
        for (ArrayList<Tuple> tlist : stoneStrings){
        	ArrayList<Tuple> needList = getNeedList(tlist,turn.getEC(),true);
        	if (needList.size() == 2 && !containsInValid(needList)) {
        		for (Tuple t : needList ) {
        			boolean toBreak = false;
        			if (capString.contains(t)) continue;
        			ArrayList<Tuple> adj=  getAdjacent(t.a, t.b);
        			for (Tuple l : adj) {
        				if (!stones[l.a][l.b].isStone() && !needList.contains(l)) {
        					toBreak = true;
        					break;
        				}else if(stones[l.a][l.b].getSC() == turn && !tlist.contains(l)){
        					ArrayList<Tuple> sstring = checkForStrings(l.a,l.b,stoneStrings);
                   		 	if (!sstring.isEmpty())  {
                   		 		ArrayList<Tuple> needList2 = getNeedList(sstring,turn.getEC(),true);
                   		 		if((needList2.size() ==2 && !needList.containsAll(needList2)) ||(needList2.size() >2) || containsInValid(needList2)) {
                   		 			toBreak = true;
                       		 		break;
                   		 		}}}
        			}
        			if(!toBreak)goodMoves.remove(t);
        			
        		}
        	}
        }
        return goodMoves;
    }
    
    
    
    public boolean checkValidMove(int x , int y) {
        if(withinBounds(x,y) && stones[x][y] == Stone.VALID && !selfCap(x,y,turn.getEC()))
    			return true;
        return false;

    }

	
	public void iTov() {
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if(stones[i][j]==Stone.INVALID) stones[i][j]=Stone.VALID;
            }
        } 
	}
    
    
	
	public boolean checkStringSafetyv2(ArrayList<Tuple> list,Stone colour) {
		
		Board checkBoard = cloneBoard(this);
		checkBoard.iTov();
		Board newBoard;
		boolean noDead = false;
		while(!noDead) {
			ArrayList<Tuple> toRemove = new ArrayList<Tuple>();
			noDead=true;
			for(ArrayList<Tuple> blist :colour.getSStrings(checkBoard)) {
				if(blist.containsAll(list))continue;
				newBoard = cloneBoard(checkBoard);
				if(!checkSingleStringForSafety(blist,colour,newBoard)) {
					toRemove.addAll(blist);
					noDead=false;
					break;
				}
			}
			checkBoard.removeStonesOnBoard(toRemove, false);
			checkBoard.updateStringsFull();
			checkBoard.checkForCaps(colour.getEC(), false);
			checkBoard.checkForCaps(colour,false);
		}
		
		return checkSingleStringForSafety(list,colour,checkBoard);
	}
	
	
	public boolean checkSingleStringForSafety(ArrayList<Tuple> list,Stone colour,Board b) {
		
		Board checkBoard = b;
		checkBoard.turn = colour.getEC();
		ArrayList<Tuple> vMoves = checkBoard.getAllValidMoves();
		ArrayList<Tuple> libs = getLibs(list,true);
		checkBoard.removeKo();
		checkBoard.boardString = checkBoard.boardToString();
		while (!vMoves.isEmpty()) {	
			ArrayList<Tuple> preValidMoves =tupleArrayClone(vMoves);
			vMoves.retainAll(libs);
			checkBoard.boardString = checkBoard.boardToString();
			if(vMoves.isEmpty()) {
				vMoves = preValidMoves;
				ArrayList<ArrayList<Tuple>> empty = getConnected(vMoves);
				ArrayList<Tuple> newVmoves = new ArrayList<Tuple>();
				for(ArrayList<Tuple> tlist : empty) {
					if(tlist.size()> 1) {
						boolean removed = false;
						for(Tuple t : tlist) {
							if (!libs.contains(t)) {
								removed = true;
								tlist.remove(t);
								break;
							}
						}
						if(!removed)tlist.remove(0);
					}
					newVmoves.addAll(tlist);
					break;
				}
				vMoves = newVmoves;
			}
			Board nCB= cloneBoard(checkBoard);
			for (Tuple t: vMoves) checkBoard.stones[t.a][t.b]= colour.getEC();
			checkBoard.updateStringsFull();
			checkBoard.checkForCaps(colour.getEC(), false);
			checkBoard.checkForCaps(colour,false);
			checkBoard.boardString = checkBoard.boardToString();	
			ArrayList<Tuple> cappedStrings = (colour == Stone.WHITE ? checkBoard.bCappedStrings: checkBoard.wCappedStrings);
			if(!cappedStrings.isEmpty()) {
				checkBoard=nCB;		
				for(Tuple t:cappedStrings) if(vMoves.remove(t))break;
				continue;
			}

			checkBoard.boardString = checkBoard.boardToString();	
			checkBoard.removeKo();
			vMoves = checkBoard.getAllValidMoves();
			if(!colour.getSStrings(checkBoard).contains(list)) return false;
		}

		
		if(colour.getSStrings(checkBoard).contains(list)) return true;
		
		return false;
	}
	
	
//    private boolean selfCap(int i , int j, Stone enemycolour ){
//
//        if (!enemycolour.isStone()) return false;
//        
//        ArrayList<Tuple> enemyCapString =  (enemycolour== Stone.WHITE ? wCapStrings:  bCapStrings);
//        ArrayList<Tuple> currentCapString =  (enemycolour== Stone.WHITE ? bCapStrings:  wCapStrings);
//        ArrayList<ArrayList<Tuple>> stoneStrings =  (enemycolour== Stone.WHITE ? bStoneStrings:  wStoneStrings);
//        
//
//        if (enemyCapString.contains(new Tuple(i, j))) return false;
//        
//        ArrayList<Tuple> adj = getAdjacent(i, j);
//        if (currentCapString.contains(new Tuple(i, j))) {
//            for (Tuple t :adj) {
//            	if (stones[t.a][t.b].getSC() == enemycolour.getEC()){
//            		 ArrayList<Tuple> sstring = checkForStrings(t.a,t.b, stoneStrings);
//            		 if (!sstring.isEmpty() && getNeedList(sstring, enemycolour,true).size() >1)  return false;
//            	}else if (!stones[t.a][t.b].isStone())return false;
////            	}else if (stones[t.a][t.b].getSC() != Stone.BLACK && stones[t.a][t.b].getSC() != Stone.WHITE)return false;
//            }
//        	return true;
//        }  
//        
//        
//        for (Tuple t :adj) {
//		      if(stones[t.a][t.b].getSC()!=enemycolour.getSC()) return false;
//		}
//
//        return true;
//    }
    
    
	
    private boolean selfCap(int i , int j, Stone enemycolour ){

        if (!enemycolour.isStone()) return false;
        
        ArrayList<Tuple> enemyCapString =  (enemycolour== Stone.WHITE ? wCapStrings:  bCapStrings);
        ArrayList<Tuple> currentCapString =  (enemycolour== Stone.WHITE ? bCapStrings:  wCapStrings);
        ArrayList<ArrayList<Tuple>> stoneStrings =  (enemycolour== Stone.WHITE ? bStoneStrings:  wStoneStrings);
        

        if (enemyCapString.contains(new Tuple(i, j))) return false;
        
        ArrayList<Tuple> adj = getAdjacent(i, j);
        if (currentCapString.contains(new Tuple(i, j))) {
            for (Tuple t :adj) {
            	if (stones[t.a][t.b].getSC() == enemycolour.getEC()){
            		 ArrayList<Tuple> sstring = checkForStrings(t.a,t.b, stoneStrings);
            		 if (!sstring.isEmpty() && getNeedList(sstring, enemycolour,true).size() >1)  {
            			 return false;
            		 }
            	}else if (!stones[t.a][t.b].isStone()) {
            		return false;
            	}
            }
        	return true;
        }else {  

	        for (Tuple t :adj) {
			      if(stones[t.a][t.b].getSC()!=enemycolour.getSC()) return false;
			}

	        return true;
        }
    }
 
	
	
	public void updateStringsFull(){
		bStoneStrings = new ArrayList<ArrayList<Tuple>>();
	    wStoneStrings = new ArrayList<ArrayList<Tuple>>();
	    
	    ArrayList<Tuple> allPoints = new ArrayList<Tuple>() ;
        for(int i=0; i<19; i++) {
            for(int j=0; j<19; j++) {
            	allPoints.add(new Tuple(i,j));
            	
            }
        }
        

	    while(!allPoints.isEmpty()) {
	    	int i= allPoints.get(0).a;
	    	int j= allPoints.get(0).b;
	    	
	     	if (stones[i][j].isStone()) {
            	Tuple t = new Tuple(i,j);
            	Stone colour = stones[i][j].getSC();
            	ArrayList<ArrayList<Tuple>> stoneStrings = colour.getSStrings(this);
            	ArrayList<Tuple> sstring = checkForStrings(t,colour);
            	if(sstring.isEmpty()) {
            		findStringStones(t,sstring,colour);
            		stoneStrings.add(sstring);
            		allPoints.removeAll(sstring);
            	}
        	}else if (stones[i][j]==Stone.KO && ko==null)ko = new Tuple(i,j);
	     	allPoints.removeIf(x -> x.a ==i && x.b==j);
	    }
	    

	}
	
	public void findStringStones(Tuple t,ArrayList<Tuple> sstring, Stone colour){
		ArrayList<Tuple> surrounding = getAdjacent(t.a,t.b);
		sstring.add(t);
		for(Tuple k: surrounding) if(stones[k.a][k.b].getSC()==colour && !sstring.contains(k))findStringStones(k,sstring,colour);
		
	}
	

    public ArrayList<Tuple> getLibs(ArrayList<Tuple> sstring,boolean unique) {

        ArrayList<Tuple> capstring = new ArrayList<Tuple>();
        for (Tuple t : sstring){
            if(unique)capstring.removeAll(getAdjacent(t.a, t.b));
            capstring.addAll(getAdjacent(t.a, t.b));
        }
        ArrayList<Tuple> l = tupleArrayClone(sstring);
        capstring.removeAll(l);
        return capstring;
    }
    
    public ArrayList<Tuple> getNeedList(ArrayList<Tuple> sstring,Stone enemycolour,boolean unique) {
    	ArrayList<Tuple> needList = new ArrayList<Tuple>();
        ArrayList<Tuple> capList = getLibs(sstring,unique);
        for (Tuple t : capList){
            if (stones[t.a][t.b].getSC()!=enemycolour.getSC()) needList.add(t);
        }
        return needList;
    }

    private void removeStonesOnBoard(ArrayList<Tuple> tlist, boolean editormode){
        for(Tuple t : tlist){
            stones[t.a][t.b] = Stone.VALID;
            if (editormode && keystones.contains(t)) keystones.remove(t);
        }
    }
    
    private boolean containsInValid(ArrayList<Tuple> tlist){
        for(Tuple t : tlist){
            if(withinBounds(t) && stones[t.a][t.b] == Stone.INVALID)return true;
        }
        return false;
    }
   
    
    public void removeKo(){
        if (ko != null){
            stones[ko.a][ko.b] =Stone.VALID;
            ko = null;}
    }
    
    public ArrayList<Tuple> checkForStrings(int i , int j , ArrayList<ArrayList<Tuple>> stoneStrings){
        Tuple k = new Tuple(i, j);
        for (ArrayList<Tuple> bStrings : stoneStrings ){
            if (bStrings.contains(k)) return tupleArrayClone(bStrings);
        }
        return   new ArrayList<Tuple>();
    }
    
	public ArrayList<Tuple> checkForStrings(Tuple t, Stone colour) {
		ArrayList<ArrayList<Tuple>> stoneStrings = colour.getSStrings(this);
        for (ArrayList<Tuple> sstring : stoneStrings ) if (sstring.contains(t)) return tupleArrayClone(sstring);
        return new ArrayList<Tuple>();
	}
	
    
	
	public ArrayList<ArrayList<Tuple>> getConnected(ArrayList<Tuple> sstring){
		ArrayList<ArrayList<Tuple>> ret = new ArrayList<ArrayList<Tuple>>();
		ArrayList<Tuple> nsstring = tupleArrayClone(sstring);

		while(!nsstring.isEmpty()) {
			Tuple t = nsstring.get(0);
			ArrayList<Tuple> connected = new ArrayList<Tuple>();
			ArrayList<Tuple> adj = getAdjacent(t.a,t.b);
			connected.add(t);
			nsstring.remove(0);
			while(!adj.isEmpty()) {
				Tuple k = adj.get(0);
				if (nsstring.contains(k)) {
					connected.add(k);
					nsstring.remove(k);
					adj.addAll(getAdjacent(k.a,k.b));
				}
				 adj.remove(0);
				
			}
			ret.add(connected);
		}
		return ret;
	}

    public ArrayList<Tuple> getAdjacent(int i , int j){
        ArrayList<Tuple> adj = new ArrayList<Tuple>();
        if (i>=1) adj.add(new Tuple(i-1,j));
        if (i<=17) adj.add(new Tuple(i+1,j));
        if (j>=1)adj.add(new Tuple(i,j-1));
        if (j<=17) adj.add(new Tuple(i,j+1));
        return adj;
    }

    public ArrayList<Tuple>  getDiag(int i , int j) {
        ArrayList<Tuple> diag = new ArrayList<Tuple>();
        if (i>=1 && j>=1) diag.add(new Tuple(i-1,j-1));
        if (i<=17 && j<=17) diag.add(new Tuple(i+1,j+1));
        if (i<=17 && j>=1)diag.add(new Tuple(i+1,j-1));
        if (i>=1 && j<=17) diag.add(new Tuple(i-1,j+1));
        return diag;
    }
    
    
    
    
    public void initBoard(boolean editormode){
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
//                stones[i][j] = Stone.EMPTY;
            	stones[i][j] = Stone.INVALID;
                if (editormode) stones[i][j] = Stone.INVALID;
            }
        }
        keystone = Stone.KEYBLACKSTONE;
        placing = Stone.BLACK;
        blackFirst = true;
//        if(editormode)capToWin = false;
//        else capToWin = true;
        
        keystones.clear();
        bStoneStrings.clear();
        wStoneStrings.clear();
        bCapStrings.clear();
        wCapStrings.clear();
        bCappedStrings.clear();
        wCappedStrings.clear();
        validMoves =getAllValidMoves();
        passing=false;
        ko = null;
        resetboard =cloneBoard(this);
    }
    
    public static Board cloneBoard(Board oB) {
    	Board nB = new Board();
//    	nB.capToWin = oB.capToWin;
    	nB.placing = oB.placing;
    	nB.turn = oB.turn;
    	nB.keystone = oB.keystone;
    	nB.stones = cloneBoardStones(oB.stones);
    	if(oB.ko!=null)nB.ko = oB.ko.clone();
    	
    	nB.keystones = tupleArrayClone(oB.keystones);
    	nB.validMoves = tupleArrayClone(oB.validMoves);
    	nB.bCapStrings = tupleArrayClone(oB.bCapStrings);
    	nB.wCapStrings = tupleArrayClone(oB.wCapStrings);
    	nB.bCappedStrings = tupleArrayClone(oB.bCappedStrings);
    	nB.wCappedStrings = tupleArrayClone(oB.wCappedStrings);
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
    

    
    
    public String boardToString() {
    	StringBuilder s = new StringBuilder();
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                switch (stones[j][i]) {
                    case BLACK: s.append("| x ") ;break;
                    case WHITE:  s.append("| o ") ;break;
                    case VALID:  s.append("| + ") ;break;
                    case INVALID:   s.append("| - ") ;break;
                    case KEYBLACKSTONE: s.append("| X ") ;  break;
                    case KEYWHITESTONE:  s.append("| O ") ; break;
                    case KO: s.append("| K ") ;break;
//                    case EMPTY: break;
                }
            }
            s.append("|\r\n") ;

        }
        return s.toString();
    	
    }
    
    public Stone[][] stringToBoard(String s){
    	 Stone[][] newstones = new Stone[19][19];
    	 int i = 0;
    	 int j = 0;

		 for(char c: s.toCharArray()) {
	         switch (c){
		         case 'x': newstones[i][j] = Stone.BLACK;i++; break;
		         case 'X': newstones[i][j] = Stone.KEYBLACKSTONE;i++; break;
		         case 'o': newstones[i][j] = Stone.WHITE;i++; break;
		         case 'O': newstones[i][j] = Stone.KEYWHITESTONE;i++; break;
		         case 'K': newstones[i][j] = Stone.KO;i++; break;
		         case '+': newstones[i][j] = Stone.VALID;i++; break;
		         case '-': newstones[i][j] = Stone.INVALID;i++; break;
		         case ' ': break;
		         case '|': break;
		         case '\r': break;
		         case '\n': j++;i=0; break;
	         }
		 }  	 
    	 return newstones;
    	 
    }
    
    public void refreshBoard() {
        updateStringsFull();
        checkForCaps(turn,false);
        checkForCaps(turn.getEC(),false);
        validMoves=getAllValidMoves();
    }
    
    public void undoMove(boolean saveUndo){
    	if(!undoString.isEmpty()) {
	    	String s = undoString.pop();
	    	if(saveUndo)redoString.push(boardToString());
	    	removeKo();
	    	stones = stringToBoard(s);
	    	turn = turn.getEC();
	    	placing = turn;
	    	refreshBoard();
    	}

    }

    public void redoMove(){
    	if(!redoString.isEmpty()) {
	    	String s = redoString.pop();
	    	undoString.push(boardToString());
	    	removeKo();
	    	stones = stringToBoard(s);
	    	turn = turn.getEC();
	    	placing = turn;
	    	refreshBoard();
    	}
    }
   
    public void flip(boolean v) {
	    undoBoard = cloneBoard(this);
	    redoBoard = null;
    	Board nB = cloneBoard(this);
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if(v)nB.stones[j][18-i]= this.stones[j][i];
            	else nB.stones[18-j][i]= this.stones[j][i];
            }
        }
        ArrayList<Tuple> nkeystones = new ArrayList<Tuple>();
        for(Tuple t:keystones) {
        	if(v)nkeystones.add(new Tuple(t.a,18-t.b));
        	else nkeystones.add(new Tuple(18-t.a,t.b));
        }
        keystones = nkeystones;
        if(ko!=null) {
        	if(v)ko= new Tuple(ko.a,18-ko.b);
        	else ko= new Tuple(18-ko.a,ko.b);
        }
        stones = nB.stones;
        updateStringsFull();
        checkForCaps(turn.getSC(),true);
        checkForCaps(turn.getEC(),true);
        validMoves =getAllValidMoves();
    }
    
    public void rotate() {
	    undoBoard = cloneBoard(this);
	    redoBoard = null;
    	Board nB = cloneBoard(this);
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	nB.stones[i][18-j]= this.stones[j][i];
            }
        }
        ArrayList<Tuple> nkeystones = new ArrayList<Tuple>();
        for(Tuple t:keystones) {
        	nkeystones.add(new Tuple(t.b,18-t.a));
        }
        keystones = nkeystones;
        if(ko!=null)ko= new Tuple(ko.b,18-ko.a);
        stones = nB.stones;
        updateStringsFull();
        checkForCaps(turn.getSC(),true);
        checkForCaps(turn.getEC(),true);
        validMoves =getAllValidMoves();
    }
  
    public boolean withinBounds(int x, int y){
    	if(x <= 18 && x>=0 && y <= 18 && y>=0) return true;
    	return false;
    	
    }
    
    public boolean withinBounds(Tuple t){
    	if(t.a <= 18 && t.a>=0 && t.b <= 18 && t.b>=0) return true;
    	return false;
    	
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
    
    private static ArrayList<ArrayList<Tuple>> twoDTupleArrayClone(ArrayList<ArrayList<Tuple>> ol){
    	ArrayList<ArrayList<Tuple>> nl = new  ArrayList<ArrayList<Tuple>>() ;
    	 for(ArrayList<Tuple> l : ol) {
             nl.add(tupleArrayClone(l));
         }
    	return nl;
    }
    

    
    public void draw(Graphics g,boolean editormode,int sPx,int sPy) {
    	int x = sPx;
    	int y = sPy;
    	
    	Board.sPx= sPx;
    	Board.sPy= sPy;

		int bS = TileSize*20;
		
//		g.setBackground(Color.lightGray);
		
		g.setColor(new Color(200,140,80));
		g.fillRect(sPx, sPy, bS, bS);
		
        for (float i =TileSize; i < bS; i+=TileSize){

            g.setColor(Color.black);
//            g.setColor(Color.red);
            g.drawLine(x+i,y+TileSize,x+i, y+bS-TileSize);
            g.drawLine(x+TileSize,y+i,x+bS-TileSize, y+i);
        }
        
        showCoord=false;
        if(showCoord) {
	        char c = 'a';
	        for (float i =TileSize; i < bS; i+=TileSize){
	            g.setColor(Color.black);
	            g.drawString(c+"", x+i-5, y+TileSize-40);
	            g.drawString((c-'a'+1)+"", x+TileSize-40,y+i-8);    
	            g.drawString(c+"",x+i-5, y+ TileSize-20 + (TileSize*19));
	            g.drawString((c-'a'+1)+"", x+TileSize-20 + (TileSize*19), y+i-8);         
	            c++;

	        }
        }
        

        for(int i = 4; i<17; i+=6) {
        	g.setColor(Color.blue);
            g.fillOval(x+(TileSize*i) - 3 ,y+(TileSize*4) -3,7,7);
            g.fillOval(x+(TileSize*i) - 3 ,y+(TileSize*10) -3,7,7);
            g.fillOval(x+(TileSize*i) - 3 ,y+(TileSize*16) -3,7,7);
      

        }
        
        g.setColor(Color.black);
        
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                switch (stones[i][j]) {
                    case BLACK:  drawoval(g,(i+1)*TileSize,(j+1)*TileSize , Color.black, false);
                        break;

                    case WHITE:  drawoval(g,(i+1)*TileSize,(j+1)*TileSize , Color.white, false);
                        break;

                    case VALID: // if (editormode)drawoval(g,(i+1)*TileSize,(j+1)*TileSize,new Color(0f,1f,0f,.0f ),false);
                    	 if (editormode)drawoval(g,(i+1)*TileSize,(j+1)*TileSize,new Color(0f,0f,0f,.0f ),false);
                        else if(!validMoves.contains(new Tuple(i,j)))drawoval( g,(i+1)*TileSize,(j+1)*TileSize ,new Color(1f,0f,0f,.2f),false);
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

//                    case EMPTY:break;
                }
                boolean idOn = true;
                if(idOn&&editormode)drawCharOnStone(g,i,j,Color.black,chars[i][j]);
                if(!idOn&&editormode)drawCharOnStone(g,(i+1)*TileSize,(j+1)*TileSize,Color.black,distance[i][j]);
            }

        }
        
        if(!editormode) {
	        for (Tuple t :this.getAllValidMoves()){
	        	drawoval(g,(t.a+1)*TileSize,(t.b+1)*TileSize,new Color(0f,1f,0f,.0f ),false);
	        }
    	}
    	
    	
    }

    public void drawoval(Graphics g,  int x, int y , Color c , boolean key) {
    	x+=sPx;
    	y+=sPy;
    	int r = (TileSize/2);
    	x = x - r;
    	y = y - r;
        g.setColor(c);
        g.fillOval(x, y, TileSize, TileSize);
        if (key){
            g.setColor(new Color(255,175,55));
            g.drawOval(x+(r/2), y+(r/2),r, r);
            g.drawOval(x, y, TileSize, TileSize);
        }else{
            g.setColor(Color.black);
            g.drawOval(x, y, TileSize, TileSize);
        }
        g.setColor(Color.black);


    }
    
    public void drawCharOnStone(Graphics g,  int i, int j , Color c ,char chars) {

    	if((int)chars==0)return;
    	int x = (i+1)*TileSize;
    	int y = (j+1)*TileSize;
    	x+=sPx;
    	y+=sPy;
    	int r = (TileSize/2);
    	x = x - r;
    	y = y - r;
    	int w = TileSize;
    	int h = TileSize-6;
    	
    	
    	String s = chars+"";

        Font oldfont = g.getFont();
        int width = oldfont.getWidth(s);
        int height = oldfont.getHeight(s);
        
		g.setColor(new Color(200,140,80));
		if(!stones[i][j].isStone())g.fillOval((x + w / 2) - (width / 2), (y + h / 2) - (height / 2),(width)+2,(height)+2);
        
        
        if(stones[i][j].isStone())g.setColor(Color.red);
        else g.setColor(c);
    
        
        g.drawString(s,(x + w / 2) - (width / 2), (y + h / 2) - (height / 2));

        g.setColor(Color.black);


    }
    
    public void drawCharOnStone(Graphics g,  int x, int y , Color c ,int chars) {
    	if (chars ==0 )return;
    	x+=sPx;
    	y+=sPy;
    	int r = (TileSize/2);
    	x = x - r;
    	y = y - r;
    	int w = TileSize;
    	int h = TileSize-6;
    	
    	
    	
    	
    	String s = chars+"";
    	boolean isInt =true;
 
        try{Integer.parseInt(s);} 
        catch (NumberFormatException nfe) {isInt=false;}
        

    
        Font oldfont = g.getFont();
        int width = oldfont.getWidth(s);
        int height = oldfont.getHeight(s);
        
		g.setColor(new Color(200,140,80));
        g.fillOval((x + w / 2) - (width / 2), (y + h / 2) - (height / 2),(width)+2,(height)+2);
        if(isInt)g.setColor(c);
        else g.setColor(c);
        g.drawString(s,(x + w / 2) - (width / 2), (y + h / 2) - (height / 2));

        g.setColor(Color.black);


    }

    public void drawsquare(Graphics g,  int x, int y , Color c) {
    	x+=sPx;
    	y+=sPy;
        g.setColor(c);
        g.fillRect(x-(TileSize/2), y-(TileSize/2), TileSize, TileSize);
    }

    public static void print(Object o){
        System.out.println(o);
        //Play.gameMsg=(String)o;
    }
    
    public static void printSent(Object o,boolean check ,boolean editormode){
        System.out.println(o);
        if(!check && !editormode)Play.gameMsg = o.toString();
        if(!check && editormode) {
        	Editor.msg = o.toString();
        	Editor.msgtimer = 300;
        	Editor.msgcb = Editor.msgcg = Editor.msgcr =0;
        	
        }

    }
   
    public Tuple calulatePostionOnBoard(int x ,int y){
    	x-=sPx;
    	y-=sPy;
    	int a = calPointOnBoard(x);
    	int b  = calPointOnBoard(y);
    	return new Tuple(a,b);
    }
    
    public int calPointOnBoard(int x){
    	if ((x % TileSize <= (TileSize*3/7))){
    		return ((x - (x % TileSize)) /TileSize);} 
    	else if ((x % TileSize >= (TileSize*4/7))) {
    		return (((x - (x % TileSize)) +  TileSize)/TileSize);}
    	else return -1;
    }

	public boolean validBoard() {
		Stone keystonecolour= Stone.KEYBLACKSTONE;
		int keystonecount = 0;
		int kocount =0;
		
		keystones.clear();
        
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if(stones[i][j].isKey()) {
                	if(keystonecount==0){
                		keystonecolour=stones[i][j];
                		keystonecount++;
                		keystones.add(new Tuple(i,j));
                	}else if(keystonecount >0) {
                		if(keystonecolour!=stones[i][j])return false;
                		keystonecount++;
                		keystones.add(new Tuple(i,j));
                	}
                }else if(stones[i][j]==Stone.KO)kocount++;

            }
        }
		if(kocount>1 || keystonecount<1)return false;
		return true;
	}
	

	public static String coord(Tuple choice) {
		String strAsciiTab = Character.toString((char) ('a' + choice.a));
        return strAsciiTab +""+ (choice.b+1) ;
	}



    
}


