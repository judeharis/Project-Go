package Go.SlickGo;



import java.util.ArrayList;
import static Go.SlickGo.UDLR.*;


public class PatternSearcher {
	Board b;
	Stone colour;
	Integer foundNum;
	
	public PatternSearcher(Board b, Stone colour){
		this.b = b;
		this.colour = colour;
	}
	
	
	public ArrayList<Tuple> stringMatch(ArrayList<Tuple> sstring, ArrayList<Pattern> pattern) {
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
						boolean colourCheck = ((b.stones[k.a][k.b].getSC() == p.colour)!=p.isNot) || p.wildCard;
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
	
	
	
	public ArrayList<Tuple> tupleMatch(Tuple t, ArrayList<Pattern> pattern){
		ArrayList<Tuple> match= new ArrayList<Tuple>(); 
		
		
		Tuple u,d,l,r,nu,nd,nl,nr;
		ArrayList<ArrayList<Tuple>> matchTries = new ArrayList<ArrayList<Tuple>>();
		boolean[] toSkip = new boolean[8];
		for(int n=0 ; n<8;n++) matchTries.add(new ArrayList<Tuple>());
		
		for(Pattern p : pattern) {
		   if(areAllTrue(toSkip))break;
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
					boolean colourCheck = ((b.stones[k.a][k.b].getSC() == p.colour)!=p.isNot) || p.wildCard;
					boolean cornerCheck = p.isCorner? isCorner(k):true;
					if(colourCheck && cornerCheck){
						matchTries.get(counter).add(k);}
					else toSkip[counter]=true;}
				else toSkip[counter]=true;
				counter++;
			}
		}
		
		for (ArrayList<Tuple> slist :matchTries) {
			if(slist.size() == pattern.size()) {
				match= slist;
				break;}
		}

		ArrayList<Tuple> ret= new ArrayList<Tuple>(); 
		
		for(Tuple k :match)if(b.withinBounds(t)&&b.stones[k.a][k.b].getSC() == colour)ret.add(k);

			
		return ret;		
		
		
	}

	
	public UDLR dirNumToDir() {
		if(this.foundNum ==0 || this.foundNum ==2 ) return DOWN;
		if(this.foundNum ==1 || this.foundNum ==3 ) return UP;
		if(this.foundNum ==5 || this.foundNum ==6 ) return LEFT;
		if(this.foundNum ==4 || this.foundNum ==7 ) return RIGHT;
		
		return NODIR;
	}
	
	
	public boolean dirSideToBool() {
		if(this.foundNum ==0 || this.foundNum ==3 || this.foundNum ==6 || this.foundNum ==4 ) return false;
		if(this.foundNum ==2 || this.foundNum ==1 || this.foundNum ==5 || this.foundNum ==7 ) return true;
		
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
