package Go.SlickGo;


import java.util.ArrayList;
import java.util.Stack;

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
	String boardString;

	
	
    Stone placing= Stone.BLACK;
    Stone turn = Stone.BLACK;
    Stone keystone = Stone.KEYBLACKSTONE;
    
    Stone[][] stones = new Stone[19][19];
    
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
    	if(i==-9 && j ==-9) passing =true;
		if (withinBounds(i,j) && !passing) {
			if (stones[i][j]== Stone.KO)print("Can't Place On KO"); 
			else if (selfCap(i,j,placing.getEC()))print("Can't Self Capture"); 
			else if (editormode){
				undoBoard = cloneBoard(this);
				redoBoard = null;
				stones[i][j] = placing;
				removeKo();
				if (placing== Stone.KEYBLACKSTONE || placing == Stone.KEYWHITESTONE){
					keystones.add(new Tuple(i,j));
					for (Tuple k:keystones) stones[k.a][k.b]=placing;
					placing = placing.getSC();
				}else keystones.removeIf( new Tuple(i,j)::equals);
			         
			}else if (stones[i][j] == Stone.EMPTY || stones[i][j] == Stone.VALID) {     
				boardString = boardToString();
				undoString.push(boardString);    	
				stones[i][j] = turn;
				moveMade=true;
				removeKo();
				turn = turn.getEC();
			}else if(!false) print("Invalid Move");
//			updateStringsSingle(i,j);
		}else if (passing) {
			passing=false;
			moveMade=true;
			if (!check) print(turn + " passed");    
			boardString = boardToString();
			undoString.push(boardString);
			removeKo();
			turn = turn.getEC();
		}else print("Out of bound");


		updateStringsFull();
		maybeko = null;  
		checkForCaps(placing,editormode);
		checkForCaps(placing.getEC(),editormode);
		if (ko !=null) stones[ko.a][ko.b] = Stone.KO;
		if (!editormode)placing =turn;
		validMoves =getAllValidMoves();
		boardString = boardToString();
		
		
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
    	return validMoves;
    }

    public ArrayList<Tuple> removeBadMovess() {
        ArrayList<ArrayList<Tuple>> stoneStrings = turn.getSC().getSStrings(this);
        ArrayList<Tuple> capString =  turn.getEC().getCapList(this);
        ArrayList<Tuple> goodMoves = tupleArrayClone(validMoves);
        for (ArrayList<Tuple> tlist : stoneStrings){
        	ArrayList<Tuple> needList = getNeedList(tlist,turn.getEC(),true);
        	if (needList.size() == 2) {
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
                   		 		if((needList2.size() ==2 && !needList.containsAll(needList2)) ||(needList2.size() >2)) {
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
    
    public ArrayList<Tuple> removeBadMovess(Stone colour) {
        ArrayList<ArrayList<Tuple>> stoneStrings = colour.getSC()==Stone.BLACK ? bStoneStrings:wStoneStrings;
        ArrayList<Tuple> capString =  colour.getSC()== Stone.BLACK ? wCapStrings: bCapStrings;
        ArrayList<Tuple> goodMoves = tupleArrayClone(validMoves);
        for (ArrayList<Tuple> tlist : stoneStrings){
        	ArrayList<Tuple> needList = getNeedList(tlist,colour.getEC(),true);
        	if (needList.size() == 2) {
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
                   		 		if((needList2.size() ==2 && !needList.containsAll(needList2)) ||(needList2.size() >2)) {
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
        if(withinBounds(x,y) && stones[x][y]!= Stone.KO && (stones[x][y] == Stone.EMPTY || stones[x][y] == Stone.VALID) && !selfCap(x,y,turn.getEC()))
    			return true;
        return false;

    }

//	public boolean checkStringSafetyv2(ArrayList<Tuple> list,Stone colour) {
//		
//		Board checkBoard = cloneBoard(this);
//		
//		checkBoard.turn = colour.getEC();
//		ArrayList<Tuple> vMoves = checkBoard.getAllValidMoves();
//		ArrayList<Tuple> libs = getLibs(list,true);
//		checkBoard.removeKo();
//		while (!vMoves.isEmpty()) {
//			checkBoard.boardString = checkBoard.boardToString();	
//			ArrayList<ArrayList<Tuple>> empty = getConnected(vMoves);
//			ArrayList<Tuple> newVmoves = new ArrayList<Tuple>();
//			for(ArrayList<Tuple> tlist : empty) {
//				if(tlist.size()> 1) {
//					boolean removed = false;
//					for(Tuple t : tlist) {
//						if (!libs.contains(t)) {
//							removed = true;
//							tlist.remove(t);
//							break;
//						}
//					}
//					if(!removed)tlist.remove(0);
//				}
//				newVmoves.addAll(tlist);
//				break;
//			}
//			vMoves = newVmoves;
//			Board nCB= cloneBoard(checkBoard);
//			for (Tuple t: vMoves) checkBoard.stones[t.a][t.b]= colour.getEC();
//			checkBoard.updateStringsFull();
//			checkBoard.checkForCaps(colour.getEC(), false);
//			checkBoard.checkForCaps(colour,false);
//			checkBoard.boardString = checkBoard.boardToString();	
//			ArrayList<Tuple> cappedStrings = (colour== Stone.WHITE ? checkBoard.bCappedStrings: checkBoard.wCappedStrings);
//			if(!cappedStrings.isEmpty()) {
//				checkBoard=nCB;	
//				checkBoard.boardString = checkBoard.boardToString();	
//				for(Tuple t:cappedStrings) if(vMoves.remove(t))break;
//				continue;
//			}
//
//			checkBoard.boardString = checkBoard.boardToString();	
//			checkBoard.removeKo();
//			vMoves = checkBoard.getAllValidMoves();
//			
//		}
//
//		
//		if(colour.getSStrings(checkBoard).contains(list)) return true;
//		
//		return false;
//	}
	
	public boolean checkStringSafetyv2(ArrayList<Tuple> list,Stone colour) {
		
		Board checkBoard = cloneBoard(this);
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
		while (!vMoves.isEmpty()) {	
			ArrayList<Tuple> preValidMoves =tupleArrayClone(vMoves);
			vMoves.retainAll(libs);
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
			ArrayList<Tuple> cappedStrings = (colour== Stone.WHITE ? checkBoard.bCappedStrings: checkBoard.wCappedStrings);
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
	
	
    private boolean selfCap(int i , int j, Stone enemycolour ){

        if (enemycolour != Stone.BLACK && enemycolour != Stone.WHITE) return false;
        
        ArrayList<Tuple> enemyCapString =  (enemycolour== Stone.WHITE ? wCapStrings:  bCapStrings);
        ArrayList<Tuple> currentCapString =  (enemycolour== Stone.WHITE ? bCapStrings:  wCapStrings);
        ArrayList<ArrayList<Tuple>> stoneStrings =  (enemycolour== Stone.WHITE ? bStoneStrings:  wStoneStrings);
        

        if (enemyCapString.contains(new Tuple(i, j))) return false;
        
        ArrayList<Tuple> adj = getAdjacent(i, j);
        if (currentCapString.contains(new Tuple(i, j))) {
            for (Tuple t :adj) {
            	if (stones[t.a][t.b].getSC() == enemycolour.getEC()){
            		 ArrayList<Tuple> sstring = checkForStrings( t.a ,  t.b, stoneStrings);
            		 if (!sstring.isEmpty() && getNeedList(sstring, enemycolour,true).size() >1)  return false;}
            	else if (stones[t.a][t.b].getSC() != Stone.BLACK && stones[t.a][t.b].getSC() != Stone.WHITE)return false;
            }
        	return true;}  
        
        
        for (Tuple t :adj) {
		      if(stones[t.a][t.b].getSC()!=enemycolour.getSC()) return false;
		}

        return true;
    }
 
//    public void updateStringsFull(){
//        for(int i=0; i<stones.length; i++) {
//            for(int j=0; j<stones[i].length; j++) {
//                 updateStringsSingle(i,j);
//            }
//        }
//    }
//   
//    private void updateStringsSingle(int i , int j){
//        if (stones[i][j].getSC()==Stone.BLACK || stones[i][j].getSC() == Stone.WHITE){
//            removeOldStoneFromString(i,j,stones[i][j].getSC()==Stone.BLACK ? wStoneStrings:bStoneStrings);
//            ArrayList<ArrayList<Tuple>> stoneStrings = stones[i][j].getSC()==Stone.BLACK ? bStoneStrings:wStoneStrings;
//            ArrayList<Tuple> stringed = checkForStrings(i, j,stoneStrings);
//            if (stringed.isEmpty()){
//                ArrayList<Tuple> adj =  getAdjacent(i,j);
//                ArrayList<Tuple> sstring= new ArrayList<Tuple>();
//                sstring.add(new Tuple(i,j));
//                for(Tuple t :adj ){
//                    if (stones[t.a][t.b].getSC() == stones[i][j].getSC()){
//                    	 ArrayList<Tuple>  libsStringres = checkForStrings(t.a,t.b,stoneStrings);
//                        if(!libsStringres.isEmpty()){
//                        	//This part combines string of liberty stone with string of current stone
//                        	if (libsStringres != sstring) {
//	                            stoneStrings.remove(libsStringres);
//	                            stoneStrings.remove(sstring);
//	                            sstring = tupleArrayMerger(sstring,libsStringres);
//	                            stoneStrings.add(sstring);}}}
//                }
//                if (sstring.size()==1){stoneStrings.add(sstring);}}
//            stoneStrings.removeIf(item -> item.isEmpty());}
//        else{
//        	if (stones[i][j]==Stone.KO && ko==null)ko = new Tuple(i,j);
//        		
//            removeOldStoneFromString(i,j,bStoneStrings);
//            removeOldStoneFromString(i,j,wStoneStrings);}
//    }

	public void updateStringsFull(){
		bStoneStrings = new ArrayList<ArrayList<Tuple>>();
	    wStoneStrings = new ArrayList<ArrayList<Tuple>>();
    	for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if (stones[i][j].isStone()) {
	            	Tuple t = new Tuple(i,j);
	            	Stone colour = stones[i][j].getSC();
	            	ArrayList<ArrayList<Tuple>> stoneStrings = colour.getSStrings(this);
	            	ArrayList<Tuple> sstring = inString(t,colour);
	            	if(sstring.isEmpty()) {
	            		findStringStones(t,sstring,colour);
	            		stoneStrings.add(sstring);
	            	}
            	}else if (stones[i][j]==Stone.KO && ko==null)ko = new Tuple(i,j);
            }
    	}
	}
	
	public void findStringStones(Tuple t,ArrayList<Tuple> sstring  , Stone colour){
		ArrayList<Tuple> surrounding = getAdjacent(t.a,t.b);
		sstring.add(t);
		for(Tuple k: surrounding) if(stones[k.a][k.b].getSC()==colour && !sstring.contains(k))findStringStones(k,sstring,colour);
		
	}
	
	public ArrayList<Tuple> inString(Tuple t, Stone colour) {
		ArrayList<ArrayList<Tuple>> stoneStrings = colour.getSStrings(this);
        for (ArrayList<Tuple> sstring : stoneStrings ) if (sstring.contains(t)) return sstring;
        return new ArrayList<Tuple>();
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
   
//    private void removeOldStoneFromString(int  i , int j , ArrayList<ArrayList<Tuple>> stoneStrings ){
//    	ArrayList<Tuple> stringed = checkForStrings( i ,  j, stoneStrings);
//        if (!stringed.isEmpty()){
//            stoneStrings.remove(stringed);
//            updateStringsFull();
//        }
//
//    }
    
    private void removeKo(){
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
        bCappedStrings.clear();
        wCappedStrings.clear();
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
                    case EMPTY: break;
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
   
    public void flip() {
	    undoBoard = cloneBoard(this);
	    redoBoard = null;
    	Board nB = cloneBoard(this);
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	nB.stones[j][18-i]= this.stones[j][i];
            }
        }
        ArrayList<Tuple> nkeystones = new ArrayList<Tuple>();
        for(Tuple t:keystones) {
        	nkeystones.add(new Tuple(t.a,18-t.b));
        }
        keystones = nkeystones;
        if(ko!=null)ko= new Tuple(ko.a,18-ko.b);
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
    
//    private ArrayList<Tuple> tupleArrayMerger(ArrayList<Tuple> a,ArrayList<Tuple> b){
//    	ArrayList<Tuple> l = new ArrayList<Tuple>();
//    	l.addAll(a);
//    	for (Tuple t : b ) {
//    		if (!l.contains(t)) l.add(t);
//    	}
//		return l;
//    	
//    }
    

    
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

                    case VALID: // if (editormode)drawoval(g,(i+1)*TileSize,(j+1)*TileSize,new Color(0f,1f,0f,.0f ),false);
                    	if(!validMoves.contains(new Tuple(i,j)))drawoval( g,(i+1)*TileSize,(j+1)*TileSize ,new Color(1f,0f,0f,.1f),false);
                    	else if (editormode)drawoval(g,(i+1)*TileSize,(j+1)*TileSize,new Color(0f,1f,0f,.0f ),false);
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
        
        if(!editormode) {
	        for (Tuple t :validMoves){
	        	drawoval(g,(t.a+1)*TileSize,(t.b+1)*TileSize,new Color(0f,1f,0f,.0f ),false);
	        }
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
        //Play.gameMsg=(String)o;
    }
   
    public int calulatePostionOnBoard(int x){
    	if ((x % TileSize <= (TileSize*3/7))){
    		return ((x - (x % TileSize)) /TileSize);} 
    	else if ((x % TileSize >= (TileSize*4/7))) {
    		return (((x - (x % TileSize)) +  TileSize)/TileSize);}
    	else return -1;
    }



    
}


