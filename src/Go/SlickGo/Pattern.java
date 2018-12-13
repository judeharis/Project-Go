package Go.SlickGo;

import java.util.ArrayList;

public class Pattern {
	int x;
	int y;
	boolean isCorner;
	boolean isNot;
	Stone colour;
	boolean wildCard;
	boolean isSide;
	





	
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
			

			boolean isCorner=(c=='O'|| c=='X'|| c=='_'|| c=='E');
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

	
	
	
	

}
