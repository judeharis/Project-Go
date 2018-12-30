package Go.SlickGo;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Go.SlickGo.Board;
import Go.SlickGo.Stone;
import Go.SlickGo.Tuple;

public class Grouping {
	Stone[][] stones;
	double[][] stonesControl;
	Board b;
	
	int gcsize = 1000;
	int boardSize = gcsize * 4 /5;
	int TileSize = boardSize/20;
	int stoneSize = TileSize;
    

    ArrayList<Group> bGroups = new ArrayList<Group>();
    ArrayList<Group> wGroups = new ArrayList<Group>();
    
    
    ArrayList<Tuple> bControls = new ArrayList<Tuple>();
    ArrayList<Tuple> wControls = new ArrayList<Tuple>();
    double totalw = 0;
    double totalb = 0;
    double totalc = 0;
    
    boolean draw = true;
    boolean drawW = false;
    boolean drawB = false;
    boolean drawC = false;
	public Grouping (Board b){
		this.b =b;
		this.stones=b.getStones();
		this.stonesControl= doubleIntegerArray();
	}
	
	
	public Grouping (Board b,boolean draw,boolean drawW,boolean drawB,boolean drawC){
		this.b =b;
		this.stones=b.getStones();
		this.stonesControl= doubleIntegerArray();
		this.draw= draw;
		this.drawW= drawW;
		this.drawB= drawB;
		this.drawC= drawC;
	}


	
	public double[][] doubleIntegerArray() {
		double[][] doubleArray= new double[19][19];
		for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	doubleArray[i][j]=0;
            	
            }
        }
		return doubleArray;
		
	}



	public void allocateGrouping(){
	    bGroups = new ArrayList<Group>();
	    wGroups = new ArrayList<Group>();
		
    	for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if (stones[i][j].isStone()) {
	            	Tuple t = new Tuple(i,j);
	            	Stone colour = stones[i][j].getSC();
	            	ArrayList<Group> groups = colour==Stone.BLACK? bGroups:wGroups;
	            	Group g = inGroupv2(t,colour);
	            	if(g.group.isEmpty()) {
	            		findGroupStonesv2(t,g,colour);
	            		groups.add(g);
	            	}
	            	
            	}
            }
    	}
		
	}
	
	public void allocateControlold(){
		stonesControl= doubleIntegerArray();
		for(int i=0; i<stonesControl.length; i++) {
            for(int j=0; j<stonesControl[i].length; j++) {
	            	Tuple t = new Tuple(i,j);
	            	int control=0;
	            	Stone colour = Stone.BLACK;

	            	ArrayList<Group> regions= inRegionv2(t,colour,true);            
	            	if(!regions.isEmpty()) for (Group g :regions)if(!g.group.isEmpty())control+=g.strength;
	            	regions= inRegionv2(t,colour,false);
	            	if(!regions.isEmpty()) for (Group g :regions)if(!g.group.isEmpty())control+=(g.strength)/2;
	            	
	            	
	            	colour = Stone.WHITE;
	            	regions= inRegionv2(t,colour,true);
	            	if(!regions.isEmpty())for (Group g :regions) if(!g.group.isEmpty())control-=g.strength;
	            	regions= inRegionv2(t,colour,false);
	            	if(!regions.isEmpty()) for (Group g :regions)if(!g.group.isEmpty())control-=(g.strength)/2;
	            	
	            	
	            	stonesControl[i][j]=control;
            }
    	}
		
	}
	

	public void allocateControl(){
		stonesControl= doubleIntegerArray();
		for (Group g : wGroups) g.updateControl(stonesControl, true);
		
	
		for (Group g : bGroups) g.updateControl(stonesControl, false);
		
		
		for(int i=0; i<stonesControl.length; i++) {
            for(int j=0; j<stonesControl[i].length; j++) {
            	if(stonesControl[i][j] > 0)bControls.add(new Tuple(i,j));
            	else wControls.add(new Tuple(i,j));
            }
    	}
		
//		for (Group g : wGroups) wControls.removeAll(g.group);
//		for (Group g : bGroups) bControls.removeAll(g.group);
		
		
		for(Tuple t: bControls) totalb+=stonesControl[t.a][t.b];
		for(Tuple t: wControls) totalw-=stonesControl[t.a][t.b];
		
		totalc = totalb-totalw;
		
		
		
	}
	

	
	
	public Group inGroupv2(Tuple t, Stone colour) {
		ArrayList<Group> groups = colour==Stone.BLACK? bGroups:wGroups;
        for (Group g : groups ) if (g.group.contains(t)) return g;
        return new Group(b);
	}
	
	
	public ArrayList<Group> inRegionv2(Tuple t, Stone colour, boolean r1) {
		ArrayList<Group> groups = colour==Stone.BLACK? bGroups:wGroups;
		ArrayList<Group> regions = new ArrayList<Group>();
        for (Group g : groups ) {
        	if (r1 && g.r1.contains(t))regions.add(g);
        	else if(!r1 && g.r2.contains(t))regions.add(g);
        }
        return regions;
	}
	



	public void findGroupStonesv2(Tuple t, Group g , Stone colour){
		ArrayList<Tuple> surrounding = g.getStoneRegion(t,true);
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
	        if(drawB)for(Group group : bGroups) group.draw(g,new Color(1f,0f,0f,.3f));
	        if(drawW)for(Group group : wGroups) group.draw(g,new Color(0f,.0f,1f,.3f));
	        if(drawC) {
		        for(int i=0; i<stones.length; i++) {
		            for(int j=0; j<stones[i].length; j++) {
		            	Tuple t = new Tuple(i,j);
		            	double c= stonesControl[i][j];
		            	if (c==0)continue;
		            	float b = (float) (.0f + (c/100.0));
		            	float w = (float) (.0f - (c/100.0));
		            	if(w>b) drawsquare(g,t,new Color(0,0,w*40,0.1f + (w/20)));	
		            	else drawsquare(g,t,new Color(b*40,0,0,0.1f + (b/20)));	
		            	
		            }
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
