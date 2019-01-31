package Go.SlickGo;



import java.util.ArrayList;
import java.util.Iterator;

import static Go.SlickGo.UDLR.*;


public class PatternSearcher {
	Board b;
	Stone colour;
	Integer foundNum;
	int foundCount;
	ArrayList<Integer> matchesDirNums;
	
	public PatternSearcher(Board b, Stone colour){
		this.b = b;
		this.colour = colour;
	}
	
	public ArrayList<Tuple> stringMatch(ArrayList<Tuple> sstring, ArrayList<Pattern> pattern,Stone def) {
		boolean found = false;
		foundNum =0;
		ArrayList<Tuple> match= new ArrayList<Tuple>(); 
		for(Tuple t : sstring) {
			Tuple u,d,l,r,nu,nd,nl,nr;
			ArrayList<ArrayList<Tuple>> matchTries = new ArrayList<ArrayList<Tuple>>();
			boolean[] toSkip = new boolean[8];
			

			for(int n=0 ; n<8;n++) matchTries.add(new ArrayList<Tuple>());
			
			for(Pattern p : pattern) {
			   if(areAllTrue(toSkip))break;
				Stone pcolour = p.def?def:def.getEC();
				ArrayList<Tuple> allRot = new ArrayList<Tuple>(); 

				u = new Tuple(t.a+p.x,t.b+p.y);
				nu = new Tuple(t.a-p.x,t.b-p.y);
				d = new Tuple(t.a-p.x,t.b+p.y);
				nd = new Tuple(t.a+p.x,t.b-p.y);
				l = new Tuple(t.a+p.y,t.b+p.x);
				nl = new Tuple(t.a-p.y,t.b-p.x);
				r = new Tuple(t.a-p.y,t.b+p.x);
				nr = new Tuple(t.a+p.y,t.b-p.x);
				
				allRot.add(u);
				allRot.add(nu);
				allRot.add(d);
				allRot.add(nd);
				allRot.add(l);
				allRot.add(nl);
				allRot.add(r);
				allRot.add(nr);
				
				int counter=0;
				for (Tuple k : allRot) {
					if(toSkip[counter]) {counter++;continue;}
					if(p.isSide && !b.withinBounds(k)) matchTries.get(counter).add(k);
					else if(!p.isSide && b.withinBounds(k)) {
						boolean colourCheck = ((b.stones[k.a][k.b].getSC() == pcolour)!=p.isNot) || p.wildCard;
						boolean cornerCheck = p.isCorner? isCorner(k):true;
						if(colourCheck && cornerCheck){
							matchTries.get(counter).add(k);}
						else toSkip[counter]=true;}
					else toSkip[counter]=true;
					counter++;
				}
			}
			
			int dirNum=0;
			for (ArrayList<Tuple> slist :matchTries) {
				if(slist.size() == pattern.size()) {
					found=true;
					foundNum=dirNum;
					match= slist;
					break;}
				dirNum++;
			}
			if(found)break;
			
		}
		

		ArrayList<Tuple> ret= new ArrayList<Tuple>(); 
		
		for(Tuple t :match)if(b.withinBounds(t)&&b.stones[t.a][t.b].getSC() == colour)ret.add(t);

		
		return ret;
	}
	
	public ArrayList<ArrayList<Tuple>> allStringMatch(ArrayList<Tuple> sstring, ArrayList<Pattern> pattern,Stone def) {

		
		ArrayList<ArrayList<Tuple>> matches = new ArrayList<ArrayList<Tuple>>();
	    matchesDirNums = new ArrayList<Integer>();

		for(Tuple t : sstring) {
			Tuple u,d,l,r,nu,nd,nl,nr;
			ArrayList<ArrayList<Tuple>> matchTries = new ArrayList<ArrayList<Tuple>>();
			boolean[] toSkip = new boolean[8];
			

			for(int n=0 ; n<8;n++) matchTries.add(new ArrayList<Tuple>());
			
			for(Pattern p : pattern) {
			   if(areAllTrue(toSkip))break;
				Stone pcolour = p.def?def:def.getEC();
				ArrayList<Tuple> allRot = new ArrayList<Tuple>(); 

				u = new Tuple(t.a+p.x,t.b+p.y);
				nu = new Tuple(t.a-p.x,t.b-p.y);
				d = new Tuple(t.a-p.x,t.b+p.y);
				nd = new Tuple(t.a+p.x,t.b-p.y);
				l = new Tuple(t.a+p.y,t.b+p.x);
				nl = new Tuple(t.a-p.y,t.b-p.x);
				r = new Tuple(t.a-p.y,t.b+p.x);
				nr = new Tuple(t.a+p.y,t.b-p.x);
				
				allRot.add(u);
				allRot.add(nu);
				allRot.add(d);
				allRot.add(nd);
				allRot.add(l);
				allRot.add(nl);
				allRot.add(r);
				allRot.add(nr);
				
				int counter=0;
				for (Tuple k : allRot) {
					if(toSkip[counter]) {counter++;continue;}
					if(p.isSide && !b.withinBounds(k)) matchTries.get(counter).add(k);
					else if(!p.isSide && b.withinBounds(k)) {
						boolean colourCheck = ((b.stones[k.a][k.b].getSC() == pcolour)!=p.isNot) || p.wildCard;
						boolean cornerCheck = p.isCorner? isCorner(k):true;
						if(colourCheck && cornerCheck)matchTries.get(counter).add(k);
						else toSkip[counter]=true;
					}else toSkip[counter]=true;
					counter++;
				}
			}
			
			int dirNum=0;
			for (ArrayList<Tuple> slist :matchTries) {
				if(slist.size() == pattern.size()) {
					matches.add(slist);
					matchesDirNums.add(dirNum);
				}
				dirNum++;
			}
			
		}
		
		
		for(ArrayList<Tuple> mlist :matches) {		
			Iterator<Tuple> itr = mlist.iterator(); 
	        while (itr.hasNext()){ 
	            Tuple t = itr.next(); 
	            if(b.withinBounds(t)&&b.stones[t.a][t.b].getSC() != colour)itr.remove();
	            else if(!b.withinBounds(t))itr.remove();} 
			}


		return matches;
	}
	
	public ArrayList<ArrayList<Tuple>> allStringMatchv2(ArrayList<Tuple> sstring, ArrayList<Pattern> pattern,Stone def) {
		ArrayList<ArrayList<Tuple>> matches = new ArrayList<ArrayList<Tuple>>();
	    matchesDirNums = new ArrayList<Integer>();

		for(Tuple t : sstring) {
			Tuple u,d,l,r,nu,nd,nl,nr;
			ArrayList<ArrayList<Tuple>> matchTries = new ArrayList<ArrayList<Tuple>>();
			boolean[] toSkip = new boolean[8];
			

			for(int n=0 ; n<8;n++) matchTries.add(new ArrayList<Tuple>());
			
			for(Pattern p : pattern) {
				if(areAllTrue(toSkip))break;
				Stone pcolour = p.def?def:def.getEC();
				ArrayList<Tuple> allRot = new ArrayList<Tuple>(); 

				u = new Tuple(t.a+p.x,t.b+p.y);
				nu = new Tuple(t.a-p.x,t.b-p.y);
				d = new Tuple(t.a-p.x,t.b+p.y);
				nd = new Tuple(t.a+p.x,t.b-p.y);
				l = new Tuple(t.a+p.y,t.b+p.x);
				nl = new Tuple(t.a-p.y,t.b-p.x);
				r = new Tuple(t.a-p.y,t.b+p.x);
				nr = new Tuple(t.a+p.y,t.b-p.x);
				
				allRot.add(u);
				allRot.add(nu);
				allRot.add(d);
				allRot.add(nd);
				allRot.add(l);
				allRot.add(nl);
				allRot.add(r);
				allRot.add(nr);
				
				int counter=0;
				for (Tuple k : allRot) {
					if(toSkip[counter]) {counter++;continue;}
					if(p.isSide && !b.withinBounds(k)) matchTries.get(counter).add(k);
					else if(!p.isSide && b.withinBounds(k)) {
						boolean colourCheck = ((b.stones[k.a][k.b].getSC() == pcolour)!=p.isNot) || p.wildCard;
						boolean cornerCheck = p.isCorner? isCorner(k):true;
						if(colourCheck && cornerCheck)matchTries.get(counter).add(k);
						else toSkip[counter]=true;
					}else toSkip[counter]=true;
					counter++;
				}
			}
			
			int dirNum=0;
			for (ArrayList<Tuple> slist :matchTries) {
				boolean skip = false;
				if(slist.size() == pattern.size()) {
					Iterator<Tuple> itr = slist.iterator(); 
			        while (itr.hasNext()){ 
			            Tuple k = itr.next(); 
			            if(b.withinBounds(k)&&b.stones[k.a][k.b].getSC() != colour)itr.remove();
			            else if(!b.withinBounds(k))itr.remove();
			        } 
			        
					for(ArrayList<Tuple> m: matches) if(m.containsAll(slist)) {skip=true;break;}
					if(!skip) {
						matches.add(slist);
						matchesDirNums.add(dirNum);
					}
				}
				dirNum++;
			}
			
		}
		
		
//		for(ArrayList<Tuple> mlist :matches) {		
//			Iterator<Tuple> itr = mlist.iterator(); 
//	        while (itr.hasNext()){ 
//	            Tuple t = itr.next(); 
//	            if(b.withinBounds(t)&&b.stones[t.a][t.b].getSC() != colour)itr.remove();
//	            else if(!b.withinBounds(t))itr.remove();
//	        } 
//			
//		}


		return matches;
	}
	
	public ArrayList<Tuple> tupleMatch(Tuple t, ArrayList<Pattern> pattern,Stone def){
		ArrayList<Tuple> match= new ArrayList<Tuple>(); 
		
		
		Tuple u,d,l,r,nu,nd,nl,nr;
		ArrayList<ArrayList<Tuple>> matchTries = new ArrayList<ArrayList<Tuple>>();
		boolean[] toSkip = new boolean[8];
		for(int n=0 ; n<8;n++) matchTries.add(new ArrayList<Tuple>());
		
		for(Pattern p : pattern) {
			if(areAllTrue(toSkip))break;
			Stone pcolour = p.def?def:def.getEC();
			ArrayList<Tuple> allRot = new ArrayList<Tuple>(); 

			u = new Tuple(t.a+p.x,t.b+p.y);
			nu = new Tuple(t.a-p.x,t.b-p.y);
			d = new Tuple(t.a-p.x,t.b+p.y);
			nd = new Tuple(t.a+p.x,t.b-p.y);
			l = new Tuple(t.a+p.y,t.b+p.x);
			nl = new Tuple(t.a-p.y,t.b-p.x);
			r = new Tuple(t.a-p.y,t.b+p.x);
			nr = new Tuple(t.a+p.y,t.b-p.x);
			
			allRot.add(u);
			allRot.add(nu);
			allRot.add(d);
			allRot.add(nd);
			allRot.add(l);
			allRot.add(nl);
			allRot.add(r);
			allRot.add(nr);
			
			int counter=0;
			for (Tuple k : allRot) {
				if(toSkip[counter]) {counter++;continue;}
				if(p.isSide && !b.withinBounds(k)) matchTries.get(counter).add(k);
				else if(!p.isSide && b.withinBounds(k)) {
					boolean colourCheck = ((b.stones[k.a][k.b].getSC() == pcolour)!=p.isNot) || p.wildCard;
					boolean cornerCheck = p.isCorner? isCorner(k):true;
					if(colourCheck && cornerCheck){
						matchTries.get(counter).add(k);}
					else toSkip[counter]=true;}
				else toSkip[counter]=true;
				counter++;
			}
		}
		
		
		int dirNum=0;
		foundCount=0;
		for (ArrayList<Tuple> slist :matchTries) {
			if(slist.size() == pattern.size()) {
				foundNum=dirNum;
				foundCount++;
				match= slist;}
			dirNum++;
		}

		ArrayList<Tuple> ret= new ArrayList<Tuple>(); 
		
		for(Tuple k :match)if(b.withinBounds(t)&&b.stones[k.a][k.b].getSC() == colour)ret.add(k);

			
		return ret;		
		
		
	}

	public int getFoundCount() {
		return foundCount;
	}
	
	public UDLR dirNumToDir() {
		if(this.foundNum ==0 || this.foundNum ==2 ) return DOWN;
		if(this.foundNum ==1 || this.foundNum ==3 ) return UP;
		if(this.foundNum ==5 || this.foundNum ==6 ) return LEFT;
		if(this.foundNum ==4 || this.foundNum ==7 ) return RIGHT;
		
		return NODIR;
	}
	
	public UDLR dirNumToDir(int dirNum) {
		int dir =matchesDirNums.get(dirNum);
		if(dir ==0 || dir ==2) return DOWN;
		if(dir ==1 || dir ==3) return UP;
		if(dir ==5 || dir ==6) return LEFT;
		if(dir ==4 || dir ==7) return RIGHT;
		
		return NODIR;
	}
	
	public boolean dirSideToBool() {
		if(this.foundNum ==0 || this.foundNum ==3 || this.foundNum ==6 || this.foundNum ==4 ) return false;
		if(this.foundNum ==2 || this.foundNum ==1 || this.foundNum ==5 || this.foundNum ==7 ) return true;
		
		return false;
	}
	
	public boolean dirSideToBool(int dirNum) {
		int dir =matchesDirNums.get(dirNum);
		if(dir ==0 || dir ==3 || dir ==6 || dir ==4 ) return false;
		if(dir ==2 || dir ==1 || dir ==5 || dir ==7 ) return true;
		
		return false;
	}

	public static boolean areAllTrue(boolean[] barray){
	    for(boolean b : barray) if(!b) return false;
	    return true;
	}
	
	public boolean isCorner(Tuple t) {
	    if(t.a == 0 && t.b==0) return true;
	    if(t.a == 18 && t.b==0) return true;
	    if(t.a == 0 && t.b==18) return true;
	    if(t.a == 18 && t.b==18) return true;
	    return false;
	}
	
	public void print(Object o) {
		System.out.println(o);
	}
	
	
}
