package Go.SlickGo;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Go.SlickGo.Tuple;

public class Group {
    ArrayList<Tuple> group = new ArrayList<Tuple>();
    ArrayList<Tuple> region = new ArrayList<Tuple>();
    ArrayList<Tuple> r1 = new ArrayList<Tuple>();
    ArrayList<Tuple> r2 = new ArrayList<Tuple>();
    
    
    Stone colour;
    ArrayList<Tuple> connectPoints = new ArrayList<Tuple>();
    double strength =0;
	Board b;
	
	int gcsize = 1000;
	int boardSize = gcsize * 4 /5;
	int TileSize = boardSize/20;
	int stoneSize = TileSize;
    


	public Group (Board b){
		this.group= new ArrayList<Tuple>();
		this.b=b;
	}
	

	public Group (Board b,Stone colour){
		this.group= new ArrayList<Tuple>();
		this.b=b;
		this.colour=colour;
	}
	
	public ArrayList<Tuple> getAllPoints(){
	    ArrayList<Tuple> allPoints = new ArrayList<Tuple>();
	    
        for(int i=0; i<19; i++) {
            for(int j=0; j<19; j++) {
            	allPoints.add(new Tuple(i,j));
            }
        }
        return allPoints;
	}
	
    

    public 	double[][] updateControl(double[][] stonesControl){
    	double spread = 1.0f;
    	
    	ArrayList<Tuple> updated = new ArrayList<>();
    	updated.addAll(group);
    	ArrayList<Tuple> adj = new ArrayList<>();
		for(Tuple t :group) {
    		if (colour.getSC()==Stone.BLACK)stonesControl[t.a][t.b] += strength;
    		else stonesControl[t.a][t.b] -= strength;
    			
			ArrayList<Tuple>  newAdj = getStoneRegion(t,false);
			adj.removeAll(newAdj);
			adj.addAll(newAdj);
		}
		adj.removeAll(updated);
    	while(!adj.isEmpty() && spread <4) {
    		updated.addAll(adj);
    		
    		if (colour.getSC()==Stone.BLACK)for(Tuple t: adj) stonesControl[t.a][t.b] += strength/(spread*2);
    		else for(Tuple t: adj) stonesControl[t.a][t.b] -= strength/(spread*2);
    			
    		
    		ArrayList<Tuple> tempAdj= Board.tupleArrayClone(adj);
    		for(Tuple t :adj) {
    			ArrayList<Tuple>  newAdj = getStoneRegion(t,false);
    			tempAdj.removeAll(newAdj);
    			tempAdj.addAll(newAdj);
    		}
    		adj =tempAdj;
    		adj.removeAll(updated);
    		spread++;
    	}

    	erosionControl(stonesControl,updated);
    	return stonesControl;
    }
    

    
    public double[][] erosionControl(double[][] stonesControl ,ArrayList<Tuple> updated){
    	double spread = 1.0f;
    	ArrayList<Tuple> adj = new ArrayList<>();
    	ArrayList<Tuple> allPoints = getAllPoints();
    	allPoints.removeAll(updated);
    	updated.clear();
    	updated.addAll(allPoints);
  
    	
		for(Tuple t :allPoints) {
			ArrayList<Tuple>  newAdj = getStoneRegion(t,false);
			adj.removeAll(newAdj);
			adj.addAll(newAdj);
		}

		adj.removeAll(updated);
		
    	while(!adj.isEmpty() && spread <4) {
    		updated.addAll(adj);
    		for(Tuple t: adj) stonesControl[t.a][t.b] -= stonesControl[t.a][t.b];
    		
    		ArrayList<Tuple> tempAdj= Board.tupleArrayClone(adj);
    		for(Tuple t :adj) {
    			ArrayList<Tuple>  newAdj = getStoneRegion(t,false);
    			tempAdj.removeAll(newAdj);
    			tempAdj.addAll(newAdj);
    		}
    		adj =tempAdj;
    		adj.removeAll(updated);
    		spread++;
    	}
		
    	return stonesControl;
    }
    



	public void score() {
		Stone colour = b.getStones()[group.get(0).a][group.get(0).b];
		ArrayList<Tuple> nlist = b.getNeedList(group, colour.getEC(), true);
		ArrayList<Tuple> libs = b.getLibs(group, true);

		
//		strength= (nlist.size()*10) + group.size()*10;
//		strength= nlist.size()*10 +libs.size()*10+  group.size()*5;
		strength= (nlist.size()*100/libs.size()) + group.size()*10;
		region = getRegionCovered(true);
		r1 = getRegionCovered(false);
		r2 = Board.tupleArrayClone(region);
		r2.removeAll(r1);
	}
	
	public ArrayList<Tuple> getRegionCovered(boolean all) {
		ArrayList<Tuple> regions = new ArrayList<Tuple>();
		for(Tuple t :group) {
			ArrayList<Tuple> region;
			region = getStoneRegion(t,all);
			region.add(t);
			regions.removeAll(region);
			regions.addAll(region);
		}
		return regions;
	}

	

    
    public ArrayList<Tuple> getStoneRegion(Tuple t, boolean all){
    	int i = t.a;
    	int j = t.b;
        ArrayList<Tuple> region = new ArrayList<Tuple>();
        
        if (i>=1) region.add(new Tuple(i-1,j));
        if (i<=17) region.add(new Tuple(i+1,j));
        if (j>=1)region.add(new Tuple(i,j-1));
        if (j<=17) region.add(new Tuple(i,j+1));
	    if(all) {
        
	        if (i>=1 && j>=1) region.add(new Tuple(i-1,j-1));
	        if (i<=17 && j<=17) region.add(new Tuple(i+1,j+1));
	        if (i<=17 && j>=1)region.add(new Tuple(i+1,j-1));
	        if (i>=1 && j<=17) region.add(new Tuple(i-1,j+1));
	        
	        if (i-1>=1) region.add(new Tuple(i-2,j));
	        if (i+1<=17) region.add(new Tuple(i+2,j));
	        if (j-1>=1)region.add(new Tuple(i,j-2));
	        if (j+1<=17) region.add(new Tuple(i,j+2));
	    }
        
        return region;
    }
    



	
    public static void print(Object o){
        System.out.println(o);
    }
    
    
    
    
    public void draw(Graphics g, Color color) {
    	for (Tuple t : region) drawsquare(g,t,color);	
    }

    public void drawsquare(Graphics g,  Tuple t , Color c) {
    	int x = (t.a+1) *TileSize;
    	int y = (t.b+1) *TileSize;
        g.setColor(c);
        g.fillRect(x-(stoneSize/2), y-(stoneSize/2), stoneSize, stoneSize);
    }
    
    public String toString() {
    	String s= "\n"+ this.group.toString();
//    	s+= " \nRegion : " + this.region.toString() ;
    	s+= " \nR1 : " + this.r1.toString() ;
    	s+= " \nR2 : " + this.r2.toString() ;
    	s+= " \nStrength: "+strength;
    	return s;
    }

}
