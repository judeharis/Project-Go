package Go.SlickGo;

import org.newdawn.slick.Color;
import java.util.ArrayList;


public enum Stone {
    BLACK,WHITE,VALID,EMPTY,INVALID, KEYWHITESTONE,KEYBLACKSTONE,KO;
	

	public String toString() {
		  switch (this) {
          case BLACK:  return "Black";


          case WHITE: return "White";

          case VALID:  return "Valid";


          case INVALID:  return "Invalid";


          case KEYBLACKSTONE: return "Black Key Stone";
          case KEYWHITESTONE: return "White Key Stone";

          case KO: return "Black";
           
          case EMPTY: return "Empty";
          }
		  return "";

	}
	
    public static Stone toStone(String stone){
        if (stone.toUpperCase().equals("BLACK")) return Stone.BLACK;
        return Stone.WHITE;
    }


    public Stone getStoneColour(){
        if( this == Stone.KEYBLACKSTONE )return Stone.BLACK;
        if( this == Stone.KEYWHITESTONE )return Stone.WHITE;
        return this;
    }

    public Stone getEnemyColour(){
        if( this.getStoneColour() == Stone.BLACK )return Stone.WHITE;
        if( this.getStoneColour() == Stone.WHITE )return Stone.BLACK;
        return this;
    }


	public Stone getKeyStone() {
		if (this == Stone.BLACK)return Stone.KEYBLACKSTONE;
		if (this == Stone.WHITE)return Stone.KEYWHITESTONE;
		return this;
	}
	
	public Color stoneToColor() {
		if (this == Stone.BLACK || this ==  Stone.KEYBLACKSTONE)return Color.black;
		if (this == Stone.WHITE || this ==  Stone.KEYWHITESTONE )return Color.white;
		return Color.gray;
	}
	
	public boolean isKey() {
        return ( this == Stone.KEYBLACKSTONE || this == Stone.KEYWHITESTONE );
	}
	
	public ArrayList<ArrayList<Tuple>> getSStrings(Board b){
        if( this.getStoneColour() == Stone.BLACK )return b.bStoneStrings;
        if( this.getStoneColour() == Stone.WHITE )return b.wStoneStrings;
        return null;
		
	}
	
	public ArrayList<Tuple> getCapList(Board b){
        if( this.getStoneColour() == Stone.BLACK )return b.bCapStrings;
        if( this.getStoneColour() == Stone.WHITE )return b.wCapStrings;
        return null;
		
	}
	

}
