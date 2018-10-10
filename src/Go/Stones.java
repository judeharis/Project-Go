package Go;

import java.awt.Color;

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
		if (s == Stones.BLACK || s ==  Stones.KEYBLACKSTONE)return Color.BLACK;
		if (s == Stones.WHITE || s ==  Stones.KEYWHITESTONE )return Color.WHITE;
		return Color.GRAY;
	}
	
	public static boolean isKey(Stones s) {
        if( s == Stones.KEYBLACKSTONE || s == Stones.KEYWHITESTONE )return true;
		return false;
	}


}
