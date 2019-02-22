package GoLD;
import static GoLD.UDLR.*;

import java.util.ArrayList;

public class Tuple implements Comparable<Tuple>{
    public int a;
    public int b;
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
    
    
    public Tuple left() {
    	return new Tuple(this.a-1, this.b);
    }
    
    public Tuple right() {
    	return new Tuple(this.a+1, this.b);
   }
   
    
    public Tuple up() {
    	return new Tuple(this.a, this.b-1);
   }
   
    public Tuple down() {
    	return new Tuple(this.a, this.b+1);
   }
   
    public Tuple side(UDLR side) {
    	if (side==UP) return this.up();
    	if (side==DOWN) return this.down();
    	if (side==LEFT) return this.left();
    	if (side==RIGHT) return this.right();

    	return new Tuple(this.a, this.b);
   }
    
    public Tuple side2(UDLR side,UDLR side2) {
    	return this.side(side).side(side2);
   }
    public Tuple sides(UDLR ...side) {
    	Tuple t = this;
    	for(UDLR s :side) {
    		t = t.side(s);
    	}
    	return t;
   }
    
    
    

    
    public Tuple clone(){
        return new Tuple(this.a, this.b);
    }
    

	@Override
	public int compareTo(Tuple t) {
		if (this.a == t.a) return (this.b-t.b);
		return (this.a-t.a);
	}

	public static ArrayList<Tuple> getSideArrayList(ArrayList<Tuple> sstring, UDLR side) {
		ArrayList<Tuple> ret = new ArrayList<Tuple>();
		for(Tuple t: sstring) {
			ret.add(t.side(side));
		}
		return ret;
	}
}
