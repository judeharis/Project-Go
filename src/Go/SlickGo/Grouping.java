package Go.SlickGo;

import java.util.ArrayList;

import Go.SlickGo.Board;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;

public class Grouping {
	Stone[][] stones;
    ArrayList<ArrayList<Tuple>> bGroups = new ArrayList<ArrayList<Tuple>>();
    ArrayList<ArrayList<Tuple>> wGroups = new ArrayList<ArrayList<Tuple>>();

	public Grouping (Board b){
		this.stones=b.getStones();
	}


	public void allocateGrouping(){
    	for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if (stones[i][j].isStone()) {
	            	Tuple t = new Tuple(i,j);
	            	Stone colour = stones[i][j].getSC();
	            	ArrayList<ArrayList<Tuple>> groups = colour==Stone.BLACK? bGroups:wGroups;
	            	ArrayList<Tuple> group = inGroup(t,colour);
	            	if(group.isEmpty()) {
	            		findGroupStones(t,group,colour);
	            		groups.add(group);
	            	}
	            	
            	}
            }
    	}
		
	}

	
	public ArrayList<Tuple> inGroup(Tuple t, Stone colour) {
		ArrayList<ArrayList<Tuple>> groups = colour==Stone.BLACK? bGroups:wGroups;
        for (ArrayList<Tuple> group : groups ) if (group.contains(t)) return Board.tupleArrayClone(group);
        return new ArrayList<Tuple>();	
	}
	
	public void findGroupStones(Tuple t, ArrayList<Tuple> group , Stone colour){
		ArrayList<Tuple> surrounding = getSurrounding(t);
		group.add(t);
		for(Tuple k: surrounding) {
			if(stones[k.a][k.b].getSC()==colour && !group.contains(k))findGroupStones(k,group,colour);
		}
	}
	
    public ArrayList<Tuple>  getDiag(int i , int j) {
        ArrayList<Tuple> diag = new ArrayList<Tuple>();
        if (i>=1 && j>=1) diag.add(new Tuple(i-1,j-1));
        if (i<=17 && j<=17) diag.add(new Tuple(i+1,j+1));
        if (i<=17 && j>=1)diag.add(new Tuple(i+1,j-1));
        if (i>=1 && j<=17) diag.add(new Tuple(i-1,j+1));
        return diag;
    }
    public ArrayList<Tuple> getAdjacent(int i , int j){
        ArrayList<Tuple> adj = new ArrayList<Tuple>();
        if (i>=1) adj.add(new Tuple(i-1,j));
        if (i<=17) adj.add(new Tuple(i+1,j));
        if (j>=1)adj.add(new Tuple(i,j-1));
        if (j<=17) adj.add(new Tuple(i,j+1));
        return adj;
    }
    
    public ArrayList<Tuple> getSurrounding(Tuple t){
        ArrayList<Tuple> sur = getAdjacent(t.a,t.b);
        sur.addAll(getDiag(t.a,t.b));
        return sur;
    }
	
    

}
