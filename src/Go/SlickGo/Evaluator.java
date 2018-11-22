package Go.SlickGo;

import java.util.ArrayList;
import java.util.Collections;
import static Go.SlickGo.UDLR.*;
public class Evaluator {
	Board cB;
	Board oB;
	Tuple oBCounts;
	String vB ="";
	String hB ="";
	Stone kscolour =Minimaxer.keystonecolour.getSC();
	
	public Evaluator(Board cB, Board oB,Tuple oBCounts) {
		this.cB = cB;
		this.oB = oB;
		this.oBCounts = oBCounts;
	}
	public Evaluator(Board cB ) {
		this.cB = cB;
		this.oB = null;
		this.oBCounts = null;
	}

	//Useless
	public  int evaluateBoard() {
		int retval= 0;
		int mult= 0;
		Stone kscolour = Minimaxer.keystonecolour;
		Stone colour = oB.turn;
		ArrayList<Tuple> obAtariList = kscolour == Stone.WHITE ? oB.wCapStrings:oB.bCapStrings;
		ArrayList<Tuple> cbAtariList = kscolour == Stone.WHITE ? cB.wCapStrings:cB.bCapStrings;
		

		Tuple cbCounts = Evaluator.countStone(colour,cB.stones);
		ArrayList<Tuple> cEyes = new ArrayList<Tuple>();
		ArrayList<Tuple> oEyes = new ArrayList<Tuple>();
		int cSafeStrings = cB.getSafeStringsCount(kscolour,cEyes);
		int oSafeStrings = oB.getSafeStringsCount(kscolour,oEyes);
		int eyeDifference = (cEyes.size() - oEyes.size());
		
		
		
		if (colour == kscolour) mult=1;
		else mult=-1;
		
		retval+= (eyeDifference) * (-200)*mult;
		retval+= (cSafeStrings-oSafeStrings) * 400*mult;

		//Check if keyStone is in Safety

		
		
		
		// if current board stone - original board stone count
		retval+= (cbCounts.a - oBCounts.a) * 100*mult;
		
		// if current board stone - original board stone count
		retval+= (oBCounts.b - cbCounts.b) * 80*mult;
		
		// if current board stone - current board enemy stone count
		retval+= (cbCounts.a - cbCounts.b) * 50*mult;
		

		// if more atari's than when started
		retval += (obAtariList.size() - cbAtariList.size()) * (200*mult);
	
		return retval;
	}
	
	
	public  int evaluateCurrentBoard() {
		int retval= 0;

		ArrayList<Tuple> cbAtariList = kscolour.getCapList(cB);
		int numberOfStrings = kscolour.getSStrings(cB).size();
		Tuple cbCounts = countStone(kscolour,cB.stones);
		if (numberOfStrings!=0 && cbAtariList.size() != 0 ) retval += cbAtariList.size() * (cbAtariList.size()/numberOfStrings) *(-300);
		
		if(cB.ko !=null&& cB.turn == kscolour) retval+= -100;
		else if(cB.ko !=null) retval+= 100;
		
		if(cB.turn == kscolour) retval+= 100;
		
		retval += cbCounts.a *5;
		for (Tuple t : cB.keystones) {
			ArrayList<Tuple>  cBsstring = cB.checkForStrings(t.a,t.b,kscolour.getSStrings(cB)); 
			if(!cBsstring.isEmpty()) {

				ArrayList<Tuple> e = new ArrayList<Tuple>();
				if (cB.checkStringSafety(cBsstring, kscolour,e)==1) retval+=1000000;
				retval += e.size()*500;
				ArrayList<Tuple> cBneedList=cB.getNeedList(cBsstring, kscolour.getEC());
				retval += cBneedList.size() * 20;
				for (Tuple k:cBneedList) {
					if (cB.stones[k.a][k.b] == Stone.INVALID) retval +=100000;
				}
				retval += sixDiesEightLives(cBsstring);
				
			}
		}
		
		
//		for (ArrayList<Tuple>  slist : kscolour.getSStrings(cB)) {
//			if(slist.size()> 1)retval+=500;
//		}
		
		getStringMap(kscolour ,cB.stones, true);
//		if (hB.contains("-xxxxxxx-"))retval +=100000;

		return retval;
		
		
	}
	
	public static Tuple countStone(Stone colour , Stone[][] stones) {

		int stoneCount = 0;
		int enemyStoneCount =0;
	    for(int i=0; i<stones.length; i++) {
	        for(int j=0; j<stones[i].length; j++) {
	        	if (stones[i][j].getSC() == colour) stoneCount++;
	        	else if (stones[i][j].getSC() == colour.getEC()) enemyStoneCount++;
	            
	        }
	    }
		Tuple counts = new Tuple(stoneCount,enemyStoneCount);
	    return counts;
	} 
	
	public void getStringMap(Stone colour , Stone[][] stones,boolean keepEnemy) {


	    for(int i=0; i<stones.length; i++) {
	        for(int j=0; j<stones[i].length; j++) {
	        	if (stones[i][j].getSC() == colour) this.vB +="x";
	        	else if (stones[i][j].getSC() == colour.getEC()&& keepEnemy) this.vB+="+";
	        	else  this.vB+="-";
	        	
	        	if (stones[j][i].getSC() == colour) this.hB +="x";
	        	else if (stones[j][i].getSC() == colour.getEC()&& keepEnemy) this.hB+="+";
	        	else  this.hB+="-";
	            
	        }
	        this.vB+="*";
	        this.hB+="*";
	    }

	} 
	
	public ArrayList<Tuple> find7Straight(Stone colour ,Board board) {
		ArrayList<Tuple> retval =  new ArrayList<Tuple>();
		if (colour.getSC() != Stone.BLACK && colour.getSC() != Stone.WHITE ) return retval;

		ArrayList<ArrayList<Tuple>> stringList = colour.getSC().getSStrings(board);
		for (ArrayList<Tuple> sstring :stringList){
				Collections.sort(sstring);
				boolean found = false; 
				for (Tuple t :sstring) {
					if (board.findAdjacentColour(t,false,true) != colour.getSC()) {
						ArrayList<Tuple> vert =  getLine(sstring, t.down(),true);
						vert.add(0, t);
			
						if(vert.size() == 7&& !found) {
							retval = vert;
							found = true;}
						else if(vert.size() == 7) return new ArrayList<Tuple>();
						 
					}
					if (board.findAdjacentColour(t,false,false) != colour.getSC()) {
						ArrayList<Tuple> hor =  getLine(sstring, t.right(),false);
						hor.add(0, t);
			
						if(hor.size() == 7&& !found) {
							retval = hor;
							found = true;}
						else if(hor.size() == 7) return new ArrayList<Tuple>();
						 
					}	

				}
			
		}
		
		
		return retval;
	}
	
	public ArrayList<Tuple>  checkStringForBar(ArrayList<Tuple> sstring ,Board board ,int barlength){

		Collections.sort(sstring);
		ArrayList<Tuple> barString =  new ArrayList<Tuple>();
		boolean found = false; 
		for (Tuple t :sstring) {
			if (board.findAdjacentColour(t,false,true) != kscolour.getSC()) {
				ArrayList<Tuple> vert =  getLine(sstring, t.down(),true);
				vert.add(0, t);
	
				if(vert.size() == barlength&& !found) {
					barString = vert;
					found = true;}
				else if(vert.size() == barlength) {
					barString =new ArrayList<Tuple>();
					break;}}
			if (board.findAdjacentColour(t,false,false) != kscolour.getSC()) {
				ArrayList<Tuple> hor =  getLine(sstring, t.right(),false);
				hor.add(0, t);
	
				if(hor.size() == barlength&& !found) {
					barString = hor;
					found = true;}
				else if(hor.size() == barlength) {
					barString =new ArrayList<Tuple>();
					break;}}
		}

		return barString;

		
	}
	

	public int barEvalww(ArrayList<Tuple> barString, ArrayList<Tuple> sstring, Stone[][] stones,int size) {
		UDLR side = distFromSide(barString.get(1),1);
		int retval=0;
		if (!side.isEmpty()) {
			Tuple S1 = barString.get(0).side(side);
			Tuple D1 = barString.get(0).dubSide(side, side.diag(true));
			Tuple S2 = barString.get((barString.size()-1)).side(side);
			Tuple D2 = barString.get((barString.size()-1)).dubSide(side, side.diag(false));
			Tuple DH1 = D1.side(side.diag(true));
			Tuple DHV1 = DH1.side(side.opp());
			Tuple DH2 = D2.side(side.diag(false));
			Tuple DHV2 = DH2.side(side.opp());
			
		  //  ArrayList<Tuple> sideString = Tuple.getSideArrayList(barString,side);
		
	
			
			if (stones[D1.a][D1.b].getSC() == kscolour) {

				if (stones[DH1.a][DH1.b].getSC() == kscolour) retval+=400;
				else if (stones[DH1.a][DH1.b].getSC() == kscolour.getEC())retval-=700;
				if (stones[DHV1.a][DHV1.b].getSC() == kscolour) retval+=100;
//				else if (stones[DHV1.a][DHV1.b].getSC() == kscolour.getEC())retval-=100;
				}

			if (stones[D2.a][D2.b].getSC() == kscolour) {
				if (stones[DH2.a][DH2.b].getSC() == kscolour) retval+=400;
				else if (stones[DH2.a][DH2.b].getSC() == kscolour.getEC())retval-=700;
				if (stones[DHV2.a][DHV2.b].getSC() == kscolour) retval+=100;
//				else if (stones[DHV2.a][DHV2.b].getSC() == kscolour.getEC())retval-=100;
				}
			
			if((stones[S1.a][S1.b].getSC() == kscolour)) {retval+=400;
				if (stones[D1.a][D1.b].getSC() == kscolour) retval+=400;
				else if (stones[D1.a][D1.b].getSC() == kscolour.getEC())retval-=200;
				}
			
		
			
			if((stones[S2.a][S2.b].getSC() == kscolour)) {retval+=400;
				if (stones[D2.a][D2.b].getSC() == kscolour) retval+=400;
				else if (stones[D2.a][D2.b].getSC() == kscolour.getEC())retval-=200;
				}

//			
			if (stones[S1.a][S1.b].getSC() == kscolour.getEC()) retval-=300;
			if (stones[S2.a][S2.b].getSC() == kscolour.getEC()) retval-=300;
			if (stones[D1.a][D1.b].getSC() == kscolour.getEC()) retval-=100;
			if (stones[D2.a][D2.b].getSC() == kscolour.getEC()) retval-=100;

			
			
//			for (Tuple t: sideString) {
//				if(sstring.contains(t)) retval+= 100;
//				else if (stones[t.a][t.b].getSC() == kscolour.getEC()) retval-=30;
//			}

			if (size==6)retval+= -500;
			else if (size==8)retval+= 500;
			retval+=sstring.size()*10;
		}
		
		
		return retval;
	}
	
	
	public int barEval(ArrayList<Tuple> barString, ArrayList<Tuple> sstring,int size) {
		UDLR side = distFromSide(barString.get(1),1);
		int retval=0;
		if (!side.isEmpty()) {
			Tuple S1 = barString.get(0).side(side);
			Tuple D1 = barString.get(0).dubSide(side, side.diag(true));
			Tuple S2 = barString.get((barString.size()-1)).side(side);
			Tuple D2 = barString.get((barString.size()-1)).dubSide(side, side.diag(false));
			
			
			Tuple DH1 = D1.side(side.diag(true));
			Tuple SS1 = S1.side(side.diag(false));
			Tuple SSS1 = SS1.side(side.diag(false));
			Tuple DHV1 = DH1.side(side.opp());
			
			Tuple DH2 = D2.side(side.diag(false));
			Tuple SS2 = S2.side(side.diag(true));
			Tuple SSS2 = SS2.side(side.diag(true));
			Tuple DHV2 = DH2.side(side.opp());
			
		    ArrayList<Tuple> sideString = Tuple.getSideArrayList(barString,side);
		    
		    boolean l=false;
		    boolean r=false;
		    int valueOfSide = 300;
		    while (!(l&&r)&& sideString.size()>1) {
		    	Tuple s1 =sideString.remove(0);
		    	Tuple s2 =sideString.remove(sideString.size()-1);
		    	if(isThere(s1)) {retval+=valueOfSide; l =true;}
		    	if(isThere(s2)) {retval+=valueOfSide; r = true;}
		    	valueOfSide-=50;
		    }
		    if(!(l||r) && sideString.size()==1) {
		    	Tuple s1 =sideString.remove(0);
		    	if(isThere(s1)) retval+=valueOfSide;}

	


//			if(isThere(S1) && isThere(D1) && isThere(DHV1)) retval+=700;
//			if(isThere(S1) && isThere(D1) && isThere(DH1)) retval+=600;		
//			if(isThere(D1) && isThere(DH1) && isThere(SS1)) retval+=600;
//			if (isThere(DHV1) && isThere(SS1))retval+=1000;
//		    
//			if(isThere(S1) && isThere(D1)) retval+=1000;
//			if(isThere(S1) && isThere(SS2)) retval+=900;
//			if(isThere(D1) && isThere(DH1)) retval+=800;
//		    
//
//			
//			if (isThere(SS1))retval+=100;
//			if (isThere(DH1))retval+=50;
//
//
//			if(isThere(S2) && isThere(D2) && isThere(DHV2)) retval+=700;
//			if(isThere(S2) && isThere(D2) && isThere(DH2)) retval+=600;	
//			if(isThere(D2) && isThere(DH2) && isThere(SS2)) retval+=600;
//			if (isThere(DHV2) && isThere(SS2)) retval+=1000;
//			
//			if(isThere(S2) && isThere(D2)) retval+=1000;

			
			if(isThere(D1)) {
				if(isThere(DH1)) retval+=400;
				else retval-=200;
				if( isThere(DHV1)) retval+=100;
			}
			
			
			if(isThere(D2)) {
				if(isThere(DH2)) retval+=400;
				else retval-=200;
				if( isThere(DHV2)) retval+=100;
			}
			
			if(isThere(S1)){retval+=400;
				if(isThere(D1)) retval+=400;
				else if(isEnemy(D1)) retval-=200;}			

			
			if(isThere(S2)){retval+=400;
				if(isThere(D2)) retval+=400;
				else if(isEnemy(D2)) retval-=200;}
			
			if(isThere(S1) && isThere(SS2)) retval+=900;
			if(isThere(S2) && isThere(SS1)) retval+=900;
			
			
			if(isThere(S1) && isThere(SSS1)) retval+=900;
			if(isThere(S2) && isThere(SSS2)) retval+=900;
			
			if (isThere(SS2))retval+=100;
			if (isThere(DH2)) retval+=50;

			if (isEnemy(S1))retval-=300; 
			if (isEnemy(D1))retval-=200; 
			
			if (isEnemy(S2))retval-=300;
			if (isEnemy(D2))retval-=100; 
			
//			for (Tuple t: sideString) {
//				if(sstring.contains(t)) retval+= 100;
//
//			}

			if (size==6)retval+= -500;
			else if (size==8)retval+= 500;
//			retval+=sstring.size()*10;
		}
		
		
		return retval;
	}
	
	public int sixDiesEightLives(ArrayList<Tuple> sstring) {
		int retval = 0;
		ArrayList<Tuple> bar7String = checkStringForBar(sstring ,cB,7);
		ArrayList<Tuple> bar6String = checkStringForBar(sstring ,cB,6);
		ArrayList<Tuple> bar8String = checkStringForBar(sstring ,cB,8);
		if(!bar7String.isEmpty()) retval += barEval(bar7String,sstring,7);
		if(!bar6String.isEmpty()) retval += barEval(bar6String,sstring,6);
		if(!bar8String.isEmpty()) retval += barEval(bar8String,sstring,8);
		return retval;
		
	}
	
	
	public UDLR distFromSide(Tuple t,int d) {
		if (t.a-d == 0 && (t.b != 0 || t.b !=18)) return LEFT;
		if (t.a+d == 18 && (t.b != 0 || t.b !=18)) return RIGHT;
		if (t.b-d == 0 && (t.a != 0 || t.a !=18)) return UP;
		if (t.b+d == 18 && (t.a != 0 || t.a !=18)) return DOWN;
		
		return NODIR;
	}
	
	
	public  ArrayList<Tuple> getLine(ArrayList<Tuple>sstring, Tuple t, boolean down ){
		ArrayList<Tuple> ret = new  ArrayList<Tuple>();
		if (sstring.contains(t)) {
			ret.add(t);
			if (down)ret.addAll(getLine(sstring,t.down(),down));
			else ret.addAll(getLine(sstring,t.right(),down));}
		return ret;
	}
	
	public boolean isThere(Tuple t) {
		 return cB.stones[t.a][t.b].getSC() == kscolour;
	}
	
	public boolean isEnemy(Tuple t) {
		 return cB.stones[t.a][t.b].getSC() == kscolour.getEC();
	}
	
    public void print(Object o){
    	System.out.println(o);
    }
	
	
	

}
