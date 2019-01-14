package Go.SlickGo;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Pattern {
	int x;
	int y;
	boolean isCorner;
	boolean isNot;
	Stone colour;
	boolean wildCard;
	boolean isSide;
	

	int gcsize = 1000;
	int boardSize = gcsize * 4 /5;
	int TileSize = boardSize/20;
	int stoneSize = TileSize;



	
	Pattern(int x, int y, Stone colour, boolean isCorner,boolean isNot,boolean wildCard,boolean isSide) {
		 this.x=x;
		 this.y=y;
		 this.isCorner=isCorner;
		 this.isSide=isSide;
		 this.colour=colour;
		 this.isNot=isNot;
		 this.wildCard=wildCard;
	}
	
	
	public static ArrayList<Pattern> stringToPattern(String string,Stone colour) {
		ArrayList<Pattern> p = new ArrayList<Pattern>();
		String negatives="";
		int i=0;
		int x=0;
		int y=0;
		
		int min=Integer.MAX_VALUE;
		ArrayList<Integer> minlist = new ArrayList<Integer>();
		minlist.add(string.indexOf("x"));
		minlist.add(string.indexOf("X"));
		for (Integer o : minlist) {
			if (o>-1 && o <min) min=o;
		}

		

		if (min < string.length()) {
			negatives = string.substring(0,min);
			negatives = new StringBuffer(negatives).reverse().toString();
			string = string.substring(min,string.length());
		}

		
		for (char c : negatives.toCharArray()) {
			boolean isCorner=(c=='O'|| c=='X'|| c=='_'|| c=='E');
			boolean isNot =(c=='-' || c=='_' || c=='e' || c=='E');
			boolean wildCard =(c=='*' || c=='#');
			boolean isSide =(c=='S');
			Stone pcolour = (c=='o' || c=='O' || c=='e' || c=='E')?colour.getEC():colour.getSC();
			if(c=='/') {x=(19+x);y--; continue;} 
			else x--;
			p.add(new Pattern(x,y,pcolour,isCorner,isNot,wildCard,isSide));
			
		}
			
		
		for (char c : string.toCharArray()) {
			

			boolean isCorner=(c=='O'|| c=='X'|| c=='_'|| c=='E');
			boolean isNot =(c=='-' || c=='_' || c=='e' || c=='E');
			boolean wildCard =(c=='*' || c=='#');
			boolean isSide =(c=='S');
			Stone pcolour = (c=='o' || c=='O' || c=='e' || c=='E')?colour.getEC():colour.getSC();
			
			if (i==0) {x=0;y=0;}
			else if(c=='/') {x=(x-19);y++; continue;}
			else x++;
			i++;
			p.add(new Pattern(x,y,pcolour,isCorner,isNot,wildCard,isSide));
			
			
		}
		
		return p;
	}
	
	public static ArrayList<Pattern> sToPv2(String string,Stone colour) {
		
		ArrayList<Pattern> p = new ArrayList<Pattern>();
		int x=0;
		int y=0;

		
		for (char c : string.toCharArray()) {
			

			boolean isCorner=(c=='O'|| c=='X'|| c=='_'|| c=='E'|| c=='#');
			boolean isNot =(c=='-' || c=='_' || c=='e' || c=='E');
			boolean wildCard =(c=='*' || c=='#');
			boolean isSide =(c=='S');
			Stone pcolour = (c=='o' || c=='O' || c=='e' || c=='E')?colour.getEC():colour.getSC();

			if(c=='u') {y--; continue;}
			else if(c=='d') {y++; continue;}
			else if(c=='l') {x--; continue;}
			else if(c=='r') {x++; continue;}
			else if(c=='z') {x=0;y=0; continue;}
			
			p.add(new Pattern(x,y,pcolour,isCorner,isNot,wildCard,isSide));
			
			
		}
		
		return p;
	}
	
	
	public String toString(){
		String ret ="[";		
		if (isNot) ret+="Not "+ this.colour+ " ";
		else ret+=this.colour+ " ";
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
                		
            		if(p.isSide && !b.withinBounds(k)) okcount++;	
    				else if(!p.isSide && b.withinBounds(k)) {
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
    	int r = (stoneSize/2);
    	int k = (r/2);
    	x = (x+1)*TileSize;
    	y = (y+1)*TileSize;
    	x = x - r;
    	y = y - r;
    	
    	
    	if (p.colour == Stone.BLACK)g.setColor(new Color(Color.black));
    	else g.setColor(new Color(Color.white));
    	
    	if (p.wildCard)g.setColor(Color.green);
    	if(p.isSide)g.setColor(Color.yellow);
        g.fillOval(x, y, stoneSize, stoneSize);
        g.setColor(Color.red);
        
        if (p.isCorner)g.drawString("C",x+(k*3/2),y+k);
        else if(p.isSide)g.drawString("S",x+(k*3/2),y+k);
        
        if(p.isNot)  g.drawString("X",x+(k*3/2),y+k);
        
        g.setColor(Color.black);
        g.drawOval(x, y, stoneSize, stoneSize);
    }
	
    

}
