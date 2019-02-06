package Go.SlickGo.Heuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SingleEyeOLD {
	Evaluator e;
	PatternSearcher ps;

	public SingleEyeOLD (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
//		ArrayList<Pattern> pattern = Pattern.sToPv2("xdlxrrxldx", e.kscolour);
//		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xdlxrrxldx");
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple BL = TL.side2(side,side);
					Tuple BR = TR.side2(side,side);
					
					Tuple T = tlist.get(0).side(side.opp());
					Tuple L = TL.side2(side,l);
					Tuple R = TR.side2(side,r);
					Tuple B = BL.side2(side,r);
					
					
					Tuple C = tlist.get(0).side(side);


					retval+=100;
					
					
					if(!(e.isEnemies(T,TL,TR) || e.isEnemies(L,TL,BL) || e.isEnemies(B,BL,BR) || e.isEnemies(R,TR,BR)) && e.isThere(C))retval-=500;
//					if(e.isThere(C)) retval-=500;
					
					
					if(e.isThere(TL)) retval+=20;
					if(e.isThere(TR)) retval+=20;
					if(e.isThere(BL)) retval+=20;
					if(e.isThere(BR)) retval+=20;
					

					

					if(e.isEnemy(TL) && e.isThere(TR)) retval+=20;
					if(e.isEnemy(TL) && e.isThere(BL)) retval+=20;

					if(e.isEnemy(TR) && e.isThere(TL)) retval+=20;
					if(e.isEnemy(TR) && e.isThere(BR)) retval+=20;
					
					if(e.isEnemy(BL) && e.isThere(TL)) retval+=20;
					if(e.isEnemy(BL) && e.isThere(BR)) retval+=20;
					
					if(e.isEnemy(BR) && e.isThere(TR)) retval+=20;
					if(e.isEnemy(BR) && e.isThere(BL)) retval+=20;
					
					
					
					if(e.isEnemy(TL)) retval-=40;
					if(e.isEnemy(TR)) retval-=40;
					if(e.isEnemy(BL)) retval-=40;
					if(e.isEnemy(BR)) retval-=40;
					
					
					if(e.isEnemies(TL,TR)) retval-=20;
					if(e.isEnemies(BL,TL)) retval-=20;
					if(e.isEnemies(BR,TR)) retval-=20;
					if(e.isEnemies(BL,BR)) retval-=20;

					
					




				}
				counter++;
			}
			
		}

		
		return retval;

	}

	
	
}
