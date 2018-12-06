package Go.SlickGo;


public enum UDLR {
	UP,DOWN,LEFT,RIGHT,NODIR,HOR,VERT;
	
	public boolean isEmpty() {
		if (this==NODIR) return true;
		return false;
	}
	
    public UDLR diag(boolean first) {
    	if (this==UP || this==DOWN) {
    		if(first)return LEFT;
    		else return RIGHT;
    	}
    	
    	if (this==LEFT || this==RIGHT) {
    		if(first)return UP;
    		else return DOWN;
    	}

    	return NODIR;
   }
    
    public UDLR opp() {
    	if (this==UP ) return DOWN;
    	if (this==DOWN ) return UP;
    	if (this==LEFT ) return RIGHT;
    	if (this==RIGHT ) return LEFT;

    	return NODIR;
   }
    
    
	
}
