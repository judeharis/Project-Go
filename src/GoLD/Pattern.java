package GoLD;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Pattern {
	int x;
	int y;
	boolean isCorner;
	boolean isNot;
	boolean def;
	boolean wildCard;
	boolean isSide;
	

    static int gcsize =  ((GoLD.gcheigth-50)/50)*50;
	static int boardSize = (gcsize%100==0?gcsize-50:gcsize);
	static int TileSize = (((boardSize/18)/10) *10);



	
	Pattern(int x, int y, Boolean def, boolean isCorner,boolean isNot,boolean wildCard,boolean isSide) {
		 this.x=x;
		 this.y=y;
		 this.isCorner=isCorner;
		 this.isSide=isSide;
		 this.def=def;
		 this.isNot=isNot;
		 this.wildCard=wildCard;
	}
	

	
	public static ArrayList<Pattern> sToPv2(String string) {
		
		ArrayList<Pattern> p = new ArrayList<Pattern>();
		int x=0;
		int y=0;

		
		for (char c : string.toCharArray()) {
			

			boolean isCorner=(c=='O'|| c=='X'|| c=='_'|| c=='E'|| c=='#');
			boolean isNot =(c=='-' || c=='_' || c=='e' || c=='E');
			boolean wildCard =(c=='*' || c=='#');
			boolean isSide =(c=='S');
			boolean def = (c=='o' || c=='O' || c=='e' || c=='E')?false:true;

			if(c=='u') {y--; continue;}
			else if(c=='d') {y++; continue;}
			else if(c=='l') {x--; continue;}
			else if(c=='r') {x++; continue;}
			else if(c=='z') {x=0;y=0; continue;}
			
			p.add(new Pattern(x,y,def,isCorner,isNot,wildCard,isSide));
			
			
		}
		
		return p;
	}
	
	
	
	
	public String toString(){
		String ret ="[";		
		if (isNot) ret+="Not "+ (def?"def":"atk")+ " ";
		else ret+= (def?"def":"atk")+ " ";
		ret+=this.x+ " ";
		ret+=this.y+ " ";
		ret+=this.isCorner? "C":"notC";
		ret+=this.isSide? " S":" notS"+ "]";
		
		return ret;
		
	}
	

	
    public void draw(Graphics g, ArrayList<Pattern> pattern, Board b) {

    	boolean fullDraw=false;
    	PatternSearcher ps = new PatternSearcher(b,Stone.BLACK);
    	
    	for(int i =0;i<19;i++) {
        	for(int j =0;j<19;j++) {
        		int okcount= 0;
            	for (Pattern p : pattern) {
            		int x = i+p.x;
            		int y = j+p.y;
            		Tuple k = new Tuple(x,y);
                		
            		if(p.isSide && !Board.withinBounds(k)) okcount++;	
    				else if(!p.isSide && Board.withinBounds(k)) {
    					boolean colourCheck = true;
    					boolean cornerCheck = p.isCorner? ps.isCorner(k):true;
    					if(colourCheck && cornerCheck)okcount++;
    				}		
                }
                	
                if (okcount == pattern.size()) {	
                	for (Pattern p : pattern) {
                		int x = i+p.x;
                		int y = j+p.y;
                		drawPattern(g,x,y,p);
                    }
                	fullDraw = true;
            	}
                if(fullDraw)break;
        	}
        	if(fullDraw)break;
    	}

    }

    public void drawPattern(Graphics g,  int x, int y ,Pattern p) {

    	x = (x+1)*TileSize;
    	y = (y+1)*TileSize;
    	x+=Board.sPx;
    	y+=Board.sPy;
    	int r = (TileSize/2);
    	int k = (r/2);
    	x = x - r;
    	y = y - r;
    	
    	
    	if (def)g.setColor(new Color(Color.black));
    	else g.setColor(new Color(Color.white));
    	
    	if (p.wildCard)g.setColor(Color.green);
    	if(p.isSide)g.setColor(Color.yellow);
        g.fillOval(x, y, TileSize, TileSize);
        g.setColor(Color.red);
        
        if (p.isCorner)g.drawString("C",x+(k*3/2),y+k);
        else if(p.isSide)g.drawString("S",x+(k*3/2),y+k);
        
        if(p.isNot)  g.drawString("X",x+(k*3/2),y+k);
        
        g.setColor(Color.black);
        g.drawOval(x, y, TileSize, TileSize);
    }
	
    

}
