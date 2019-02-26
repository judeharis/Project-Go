package GoLD;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GoLD.Board;
import GoLD.Stone;
import GoLD.Tuple;



public class Grouping {
	Stone[][] stones;
	double[][] stonesControl;

	Board b;
	
    static int gcsize =  ((SlickGo.gcheigth-50)/50)*50;
	static int boardSize = (gcsize%100==0?gcsize-50:gcsize);
	static int TileSize = ((boardSize/18)/10) *10;
	

    

//    ArrayList<Group> bGroups = new ArrayList<Group>();
//    ArrayList<Group> wGroups = new ArrayList<Group>();
    
    ArrayList<Group> allGroups = new ArrayList<Group>();
    
    
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



//	public void allocateGrouping(){
//	    bGroups = new ArrayList<Group>();
//	    wGroups = new ArrayList<Group>();
//	    
//    	for(int i=0; i<stones.length; i++) {
//            for(int j=0; j<stones[i].length; j++) {
//            	if (stones[i][j].isStone()) {
//	            	Tuple t = new Tuple(i,j);
//	            	Stone colour = stones[i][j].getSC();
//	            	ArrayList<Group> groups = colour==Stone.BLACK? bGroups:wGroups;
//	            	Group g = inGroup(t,colour);
//	            	if(g.group.isEmpty()) {
//	            		findGroupStones(t,g,colour);
//	            		groups.add(g);
//	            	}
//	            	
//            	}
//            }
//    	}
//		
//	}
	
	
	public void allocateGrouping(){

	    allGroups = new ArrayList<Group>();
	    
    	for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
            	if (stones[i][j].isStone()) {
	            	Tuple t = new Tuple(i,j);
	            	Stone colour = stones[i][j].getSC();
	            	Group g = inGroup(t,colour);
	            	if(g.group.isEmpty()) {
	            		findGroupStones(t,g);
	            		Collections.sort(g.group);
	            		allGroups.add(g);
	            	}
	            	
            	}
            }
    	}
		
	}




//	public void waveControl(Tuple t,double strength, int which){
//		int i = t.a;
//		int j = t.b;
//		Stone colour = stones[i][j];
//		Group g = inGroupv2(new Tuple(i,j),colour);
//		
//		if(colour.getSC()==Stone.BLACK && !g.group.isEmpty())strength+=g.strength;
//		if(colour.getSC()==Stone.WHITE && !g.group.isEmpty())strength-=g.strength;
//		
//		strength= strength*1/2;
//		stonesControl[i][j]=strength;
//
//		ArrayList<Tuple> wave = getNextWave(t,which);
//		
//		for(Tuple k : wave) waveControl(k,strength,which);		
//	}
//	
//	public void waveControlStart(){
//
////		stonesControl= doubleIntegerArray();
////		waveControl(new Tuple(8,8),0,1);
////		waveControl(new Tuple(10,8),0,3);
////		waveControl(new Tuple(10,10),0,5);
////		waveControl(new Tuple(8,10),0,7);
////
////		waveControl(new Tuple(8,9),0,0);
////		waveControl(new Tuple(9,8),0,2);
////		waveControl(new Tuple(10,9),0,4);
////		waveControl(new Tuple(9,10),0,6);
//		
////		waveControl(new Tuple(9,9),0,0);
////		waveControl(new Tuple(9,9),0,2);
////		waveControl(new Tuple(9,9),0,4);
////		waveControl(new Tuple(9,9),0,6);
//		
//
////		waveControl(new Tuple(9,9),0,1);
////		waveControl(new Tuple(9,9),0,3);
////		waveControl(new Tuple(9,9),0,5);
////		waveControl(new Tuple(9,9),0,7);
//		
//
//	}
//	
//	
//	public ArrayList<Tuple> getNextWave(Tuple t,int which){
//		ArrayList<Tuple> list = new  ArrayList<Tuple>();
//		switch (which) {
//		
//		case 0:
//			safeSideCheck(t.side(LEFT),list);
//			safeSideCheck(t.side2(LEFT,UP),list);
//			safeSideCheck(t.side2(LEFT,DOWN),list);
//			break;
//		case 1: 
//			safeSideCheck(t.side(LEFT),list);
//			safeSideCheck(t.side(UP),list);
//			safeSideCheck(t.side2(LEFT,UP),list);
//			break;
//		case 2:
//			safeSideCheck(t.side(UP),list);
//			safeSideCheck(t.side2(UP,LEFT),list);
//			safeSideCheck(t.side2(UP,RIGHT),list);
//			break;
//		case 3:
//			safeSideCheck(t.side(UP),list);
//			safeSideCheck(t.side(RIGHT),list);
//			safeSideCheck(t.side2(UP,RIGHT),list);
//			break;
//		case 4:
//			safeSideCheck(t.side(RIGHT),list);
//			safeSideCheck(t.side2(RIGHT,UP),list);
//			safeSideCheck(t.side2(RIGHT,DOWN),list);
//			break;
//		case 5:
//			safeSideCheck(t.side(RIGHT),list);
//			safeSideCheck(t.side(DOWN),list);
//			safeSideCheck(t.side2(RIGHT,DOWN),list);
//			break;
//		case 6:
//			safeSideCheck(t.side(DOWN),list);
//			safeSideCheck(t.side2(DOWN,RIGHT),list);
//			safeSideCheck(t.side2(DOWN,LEFT),list);
//			break;
//		case 7:
//			safeSideCheck(t.side(DOWN),list);
//			safeSideCheck(t.side(LEFT),list);
//			safeSideCheck(t.side2(DOWN,LEFT),list);
//			break;
//		}
//		return list;
//	}
//	
//	public void safeSideCheck(Tuple t, ArrayList<Tuple> list) {
//		if(b.withinBounds(t))list.add(t);
//	}
//	

	
	
	
//	public void allocateControl(){
//		stonesControl= doubleIntegerArray();
//		for (Group g : wGroups) g.updateControl(stonesControl);
//		for (Group g : bGroups) g.updateControl(stonesControl);
//		
//		
//		
//		for(int i=0; i<stonesControl.length; i++) {
//            for(int j=0; j<stonesControl[i].length; j++) {
//            	if(stonesControl[i][j] > 0)bControls.add(new Tuple(i,j));
//            	else wControls.add(new Tuple(i,j));
//            }
//    	}
//		
//		for (Group g : wGroups) wControls.removeAll(g.group);
//		for (Group g : bGroups) bControls.removeAll(g.group);
//		
//		
//		for(Tuple t: bControls) totalb+=stonesControl[t.a][t.b];
//		for(Tuple t: wControls) totalw-=stonesControl[t.a][t.b];
//		
//		totalc = totalb-totalw;
//	}
	

	
	public void allocateControl(){
		stonesControl= doubleIntegerArray();

//		for (Group g : allGroups) g.updateControl(stonesControl);
		
		for(int i=0; i<stonesControl.length; i++) {
            for(int j=0; j<stonesControl[i].length; j++) {
            	if(stonesControl[i][j] > 0)bControls.add(new Tuple(i,j));
            	else if(stonesControl[i][j] < 0) wControls.add(new Tuple(i,j));
            }
    	}		
		for (Group g : allGroups)if(g.colour==Stone.BLACK)  wControls.removeAll(g.group);else bControls.removeAll(g.group);
		
		for(Tuple t: bControls) totalb+=stonesControl[t.a][t.b];
		for(Tuple t: wControls) totalw-=stonesControl[t.a][t.b];
		
		totalc = totalb-totalw;
	}
	
	
	public Group inGroup(Tuple t, Stone colour) {
		if(colour.isStone())for (Group g : allGroups ) if (g.colour == colour && g.group.contains(t)) return g;
        return new Group(b,colour);
	}
	
	
//	public ArrayList<Group> inRegion(Tuple t, Stone colour, boolean r1) {
//		ArrayList<Group> groups = colour==Stone.BLACK? bGroups:wGroups;
//		ArrayList<Group> regions = new ArrayList<Group>();
//        for (Group g : groups ) {
//        	if (r1 && g.r1.contains(t))regions.add(g);
//        	else if(!r1 && g.r2.contains(t))regions.add(g);
//        }
//        return regions;
//	}
	
	public ArrayList<Tuple> distanceGen(ArrayList<Tuple> sstring , Stone enemy){
		
    	
    	int d = 0;
    	ArrayList<Tuple> adj = new ArrayList<>();
    	adj.addAll(sstring);
    	ArrayList<Tuple> updated = new ArrayList<>();
    	ArrayList<Tuple> updatedd = new ArrayList<>();
		
    	while(!adj.isEmpty() && d<4) {
    		updated.addAll(adj);

    		for(Tuple t :adj ) {
    			if(!updatedd.contains(t)) {
//    				b.distance[t.a][t.b] =d;
    				if (!stones[t.a][t.b].isStone())b.distance[t.a][t.b] =d;
    				updatedd.add(t);
    			}   		
    		}
    		
    		ArrayList<Tuple> tempAdj= Board.tupleArrayClone(adj);
    		for(Tuple t :adj) {
    			if(!stones[t.a][t.b].isStone() || d==0) {
	    			ArrayList<Tuple>  newAdj = b.getAdjacent(t.a, t.b);
	    			tempAdj.removeAll(newAdj);
	    			tempAdj.addAll(newAdj);
    			}
    		}
    		
    		tempAdj.removeAll(adj);
    		adj = tempAdj;
    		adj.removeAll(updated);
    		d++;
    	}
    	
    	ArrayList<Tuple> d1 = new ArrayList<>();
    	ArrayList<Tuple> d2 = new ArrayList<>();
    	ArrayList<Tuple> d3 = new ArrayList<>();

		
		for(int i=0; i<stones.length; i++) {
			for(int j=0; j<stones[i].length; j++) {
			    	if(b.distance[i][j]==1)d1.add(new Tuple(i,j));
			    	else if(b.distance[i][j]==2)d2.add(new Tuple(i,j));
			    	else if(b.distance[i][j]==3)d3.add(new Tuple(i,j));
			}
		}
		
		d1.addAll(d1);
		d1.addAll(d1);
		d1.addAll(d2);
		d1.addAll(d2);
		d1.addAll(d3);
		
		return d1;
	}


	public void findGroupStones(Tuple t, Group g ){
		ArrayList<Tuple> surrounding = Group.getStoneRegion(t,true);
		g.group.add(t);
//		g.score();
		for(Tuple k: surrounding) {
			if(stones[k.a][k.b].getSC()==g.colour && !g.group.contains(k))findGroupStones(k,g);
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
	        for(Group group : allGroups) {
	        	if(drawB && group.colour==Stone.BLACK) group.draw(g,new Color(1f,.0f,0f,.3f));
	        	if(drawW && group.colour==Stone.WHITE) group.draw(g,new Color(0f,.0f,1f,.3f));
	        }
	        
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
    	x+=Board.sPx;
    	y+=Board.sPy;
        g.setColor(c);
        g.fillRect(x-(TileSize/2), y-(TileSize/2), TileSize, TileSize);
    }

    

}
