package GoLD;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GoLD.Tuple;

public class Group {
    ArrayList<Tuple> group = new ArrayList<Tuple>();
    ArrayList<Tuple> region = new ArrayList<Tuple>();
    ArrayList<Tuple> r1 = new ArrayList<Tuple>();
    ArrayList<Tuple> r2 = new ArrayList<Tuple>();
    


    Stone colour;


	Board b;
	
    static int gcsize =  ((GoLD.gcheigth-50)/50)*50;
	static int boardSize = (gcsize%100==0?gcsize-50:gcsize);
	static int TileSize = ((boardSize/18)/10) *10;
    




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

	

    
	public static ArrayList<Tuple> getStoneRegion(Tuple t, boolean all){
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
    

    
    public void draw(Graphics g, Color color) {
    	for (Tuple t : region) drawsquare(g,t,color);	
    }

    public void drawsquare(Graphics g,  Tuple t , Color c) {

    	int x = (t.a+1) *TileSize;
    	int y = (t.b+1) *TileSize;
    	x+=Board.sPx;
    	y+=Board.sPy;
        g.setColor(c);
        g.fillRect(x-(TileSize/2), y-(TileSize/2), TileSize, TileSize);
    }
    
    public String toString() {
    	String s= "\n"+ this.group.toString();
    	s+= " \nR1 : " + this.r1.toString() ;
    	s+= " \nR2 : " + this.r2.toString() ;
    	return s;
    }

}
