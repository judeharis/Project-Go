package Go.SlickGo;

import org.newdawn.slick.Color;

public enum Stones {
    BLACK,WHITE,VALID,EMPTY,INVALID, KEYWHITESTONE,KEYBLACKSTONE,KO;
	

    public static Stones toStone(String stone){
        if (stone.toUpperCase().equals("BLACK")) return Stones.BLACK;
        return Stones.WHITE;
    }


    public static Stones getStoneColour(Stones s){
        if( s == Stones.KEYBLACKSTONE )return Stones.BLACK;
        if( s == Stones.KEYWHITESTONE )return Stones.WHITE;
        return s;
    }

    public static Stones getEnemyColour(Stones s){
        if( getStoneColour(s) == Stones.BLACK )return Stones.WHITE;
        if( getStoneColour(s) == Stones.WHITE )return Stones.BLACK;
        return s;
    }


	public static Stones getKeyStone(Stones s) {
		if (s == Stones.BLACK)return Stones.KEYBLACKSTONE;
		if (s == Stones.WHITE)return Stones.KEYWHITESTONE;
		return s;
	}
	
	public static Color stoneToColor(Stones s) {
		if (s == Stones.BLACK || s ==  Stones.KEYBLACKSTONE)return Color.black;
		if (s == Stones.WHITE || s ==  Stones.KEYWHITESTONE )return Color.white;
		if (s == Stones.VALID)return new Color(0f,1f,0f,.5f );
		return Color.gray;
	}
	
	public static boolean isKey(Stones s) {
        if( s == Stones.KEYBLACKSTONE || s == Stones.KEYWHITESTONE )return true;
		return false;
	}
	
	public String toString() {
		String stone="";
		if(this == Stones.BLACK ) stone = "Black";
		if(this == Stones.WHITE ) stone = "White";
		if(this == Stones.VALID ) stone = "Valid";
		if(this == Stones.EMPTY ) stone = "Empty";
		if(this == Stones.INVALID ) stone = "Invalid";
		if(this == Stones.KO ) stone = "Ko";
		
		return stone;
	}


}
