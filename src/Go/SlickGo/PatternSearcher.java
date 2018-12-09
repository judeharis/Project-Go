package Go.SlickGo;

import java.util.ArrayList;


public class PatternSearcher {
	Board b;
	Stone colour;
	
	PatternSearcher(Board b, Stone colour){
		this.b = b;
		this.colour = colour;
	}
	
	
	boolean stringMatch(ArrayList<Tuple> sstring, ArrayList<Pattern> pattern) {
		boolean found = false;
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
					if(b.withinBounds(k)) {
						boolean colourCheck = ((b.stones[k.a][k.b].getSC() == p.colour)!=p.isNot) || p.wildCard;
						
						
					
//						boolean colourCheck;
//						if(p.isNot) colourCheck = (b.stones[k.a][k.b].getSC() != p.colour) || p.wildCard;
//						else colourCheck = (b.stones[k.a][k.b].getSC() == p.colour) || p.wildCard;
						
						boolean cornerCheck = isCorner(k)==p.isCorner;
						if(colourCheck && cornerCheck){
							matchTries.get(counter).add(k);}
						else toSkip[counter]=true;}
					else toSkip[counter]=true;
					counter++;
				}
			}
			
			for (ArrayList<Tuple> slist :matchTries) {
				if(slist.size() == pattern.size()) {
					found=true;
					match= slist;
					break;}
			}
			if(found)break;
			
		}
		
		print(match);
		return found;
	}
	
	
	

	public static boolean areAllTrue(boolean[] barray){
	    for(boolean b : barray) if(!b) return false;
	    return true;
	}
	
	boolean distanceFromSide(Tuple t, UDLR side , int d) {
		
		return true;
	}
	
	boolean isCorner(Tuple t) {
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
