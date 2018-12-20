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
	
	public boolean isStone() {
		  switch (this) {
        case BLACK: return true;
        case WHITE: return true;
        case KEYBLACKSTONE: return true;
        case KEYWHITESTONE: return true;
        case VALID: return false;
        case INVALID: return false;
        case KO: return false;
        case EMPTY: return false;
        }
		return false;

	}
	
	
    public static Stone toStone(String stone){
        if (stone.toUpperCase().equals("BLACK")) return Stone.BLACK;
        return Stone.WHITE;
    }


    public Stone getSC(){
        if( this == Stone.KEYBLACKSTONE )return Stone.BLACK;
        if( this == Stone.KEYWHITESTONE )return Stone.WHITE;
        return this;
    }

    public Stone getEC(){
        if( this.getSC() == Stone.BLACK )return Stone.WHITE;
        if( this.getSC() == Stone.WHITE )return Stone.BLACK;
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
        if( this.getSC() == Stone.BLACK )return b.bStoneStrings;
        if( this.getSC() == Stone.WHITE )return b.wStoneStrings;
        return null;
		
	}
	
	public ArrayList<Tuple> getCapList(Board b){
        if( this.getSC() == Stone.BLACK )return b.bCapStrings;
        if( this.getSC() == Stone.WHITE )return b.wCapStrings;
        return null;
		
	}
	
	public ArrayList<Tuple> getCappedList(Board b){
        if( this.getSC() == Stone.BLACK )return b.bCappedStrings;
        if( this.getSC() == Stone.WHITE )return b.wCappedStrings;
        return null;
		
	}
	
	

}
