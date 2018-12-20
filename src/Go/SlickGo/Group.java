package Go.SlickGo;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Go.SlickGo.Tuple;

public class Group {
    ArrayList<Tuple> group = new ArrayList<Tuple>();
    ArrayList<Tuple> region = new ArrayList<Tuple>();
    float strength =0;
	Board b;
	
	int gcsize = 1000;
	int boardSize = gcsize * 4 /5;
	int TileSize = boardSize/20;
	int stoneSize = TileSize;
    


	public Group (Board b){
		this.group= new ArrayList<Tuple>();
		this.b=b;
	}
	


	


	public void score() {
		Stone colour = b.getStones()[group.get(0).a][group.get(0).b];
		ArrayList<Tuple> libs = b.getLibs(group, true);
		ArrayList<Tuple> nlist = b.getNeedList(group, colour.getEC(), true);
		
		strength= (nlist.size()*100/libs.size()) + group.size()*10;
		this.region = getRegionCoverd();
		
	}
	
	public ArrayList<Tuple> getRegionCoverd() {
		ArrayList<Tuple> regions = new ArrayList<Tuple>();
		for(Tuple t :group) {
			ArrayList<Tuple> region = getStoneRegion(t);
			region.add(t);
			regions.removeAll(region);
			regions.addAll(region);
		}
		return regions;
	}
	

    
    public ArrayList<Tuple> getStoneRegion(Tuple t){
    	int i = t.a;
    	int j = t.b;
        ArrayList<Tuple> region = new ArrayList<Tuple>();
        if (i>=1) region.add(new Tuple(i-1,j));
        if (i<=17) region.add(new Tuple(i+1,j));
        if (j>=1)region.add(new Tuple(i,j-1));
        if (j<=17) region.add(new Tuple(i,j+1));
        
        if (i>=1 && j>=1) region.add(new Tuple(i-1,j-1));
        if (i<=17 && j<=17) region.add(new Tuple(i+1,j+1));
        if (i<=17 && j>=1)region.add(new Tuple(i+1,j-1));
        if (i>=1 && j<=17) region.add(new Tuple(i-1,j+1));
        
        if (i-1>=1) region.add(new Tuple(i-2,j));
        if (i+1<=17) region.add(new Tuple(i+2,j));
        if (j-1>=1)region.add(new Tuple(i,j-2));
        if (j+1<=17) region.add(new Tuple(i,j+2));
        
        return region;
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
    	String s=this.group.toString() + " \nRegion : " + this.region.toString() ;
    	s+= " \nStrength: "+strength;
    	return s;
    }

}
