package Go.SlickGo;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Go.SlickGo.Board;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;

public class Grouping {
	Stone[][] stones;
	float[][] stonesControl;
	Board b;
	
	int gcsize = 1000;
	int boardSize = gcsize * 4 /5;
	int TileSize = boardSize/20;
	int stoneSize = TileSize;
    

    ArrayList<Group> bGroups1 = new ArrayList<Group>();
    ArrayList<Group> wGroups1 = new ArrayList<Group>();
    boolean draw = true;
    boolean drawW = false;
    boolean drawB = false;
	public Grouping (Board b){
		this.b =b;
		this.stones=b.getStones();
		this.stonesControl= floatIntegerArray();
	}


	
	public float[][] floatIntegerArray() {
		float[][] floatArray= new float[19][19];
		for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	floatArray[i][j]=0;
            	
            }
        }
		return floatArray;
		
	}



	public void allocateGrouping(){
	    bGroups1 = new ArrayList<Group>();
	    wGroups1 = new ArrayList<Group>();
		
    	for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if (stones[i][j].isStone()) {
	            	Tuple t = new Tuple(i,j);
	            	Stone colour = stones[i][j].getSC();
	            	ArrayList<Group> groups = colour==Stone.BLACK? bGroups1:wGroups1;
	            	Group g = inGroupv2(t,colour);
	            	if(g.group.isEmpty()) {
	            		findGroupStonesv2(t,g,colour);
	            		groups.add(g);
	            	}
	            	
            	}
            }
    	}
		
	}
	
	public void allocateControl(){
		stonesControl= floatIntegerArray();
		for(int i=0; i<stonesControl.length; i++) {
            for(int j=0; j<stonesControl[i].length; j++) {
	            	Tuple t = new Tuple(i,j);
	            	int control=0;
	            	Stone colour = Stone.BLACK;

	            	ArrayList<Group> regions= inRegionv2(t,colour);
	            	if(!regions.isEmpty()) for (Group g :regions)if(!g.group.isEmpty())control+=g.strength;
	            	
	            	
	            	colour = Stone.WHITE;
	            	regions= inRegionv2(t,colour);
	            	if(!regions.isEmpty())for (Group g :regions) if(!g.group.isEmpty())control-=g.strength;
	            
	            	
	            	
	            	stonesControl[i][j]=control;
            }
    	}
		
	}

	
	
	public Group inGroupv2(Tuple t, Stone colour) {
		ArrayList<Group> groups = colour==Stone.BLACK? bGroups1:wGroups1;
        for (Group g : groups ) if (g.group.contains(t)) return g;
        return new Group(b);
	}
	
	public Group inRegion(Tuple t, Stone colour) {
		ArrayList<Group> groups = colour==Stone.BLACK? bGroups1:wGroups1;
        for (Group g : groups ) if (g.region.contains(t)) return g;
        return new Group(b);
	}
	
	public ArrayList<Group> inRegionv2(Tuple t, Stone colour) {
		ArrayList<Group> groups = colour==Stone.BLACK? bGroups1:wGroups1;
		ArrayList<Group> regions = new ArrayList<Group>();
        for (Group g : groups ) if (g.region.contains(t)) regions.add(g);
        return regions;
	}
	
	public void findGroupStonesv2(Tuple t, Group g , Stone colour){
		ArrayList<Tuple> surrounding = g.getStoneRegion(t);
		g.group.add(t);
		g.score();
		for(Tuple k: surrounding) {
			if(stones[k.a][k.b].getSC()==colour && !g.group.contains(k))findGroupStonesv2(k,g,colour);
		}
	}
	

    public ArrayList<Tuple> getSurrounding(Tuple t){
    	int i = t.a;
    	int j = t.b;
        ArrayList<Tuple> sur = new ArrayList<Tuple>();
        if (i>=1) sur.add(new Tuple(i-1,j));
        if (i<=17) sur.add(new Tuple(i+1,j));
        if (j>=1)sur.add(new Tuple(i,j-1));
        if (j<=17) sur.add(new Tuple(i,j+1));
        
        if (i>=1 && j>=1) sur.add(new Tuple(i-1,j-1));
        if (i<=17 && j<=17) sur.add(new Tuple(i+1,j+1));
        if (i<=17 && j>=1)sur.add(new Tuple(i+1,j-1));
        if (i>=1 && j<=17) sur.add(new Tuple(i-1,j+1));
        
        if (i-1>=1) sur.add(new Tuple(i-2,j));
        if (i+1<=17) sur.add(new Tuple(i+2,j));
        if (j-1>=1)sur.add(new Tuple(i,j-2));
        if (j+1<=17) sur.add(new Tuple(i,j+2));
        
        return sur;
    }


    public void draw(Graphics g) {
    	

    	if(draw) {
	        if(drawB)for(Group group : bGroups1) group.draw(g,new Color(1f,0f,0f,.3f));
	        if(drawW)for(Group group : wGroups1) group.draw(g,new Color(0f,.0f,1f,.3f));
	        
	        for(int i=0; i<stones.length; i++) {
	            for(int j=0; j<stones[i].length; j++) {
	            	Tuple t = new Tuple(i,j);
	            	float c= stonesControl[i][j];
	            	if (c==0)continue;
	            	float b= (float) (.0f + (c/100.0));
	            	float w = (float) (.0f - (c/100.0));
	            	drawsquare(g,t,new Color(b,.0f,w,0.3f));	
		            	
	            	
	            }
	    	}
    	}
    	
    }

    public void drawsquare(Graphics g,  Tuple t , Color c) {
    	int x = (t.a+1) *TileSize;
    	int y = (t.b+1) *TileSize;
        g.setColor(c);
        g.fillRect(x-(stoneSize/2), y-(stoneSize/2), stoneSize, stoneSize);
    }

    

}
