package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;


public class Eye {

	static ArrayList<Pattern> eyePattern = Pattern.sToPv2("xdlxrrxldx");
	

	public static int evaluate(ArrayList<Tuple> sstring,Evaluator e) {
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, eyePattern,e.kscolour);
		
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
					Tuple C = tlist.get(0).side(side);
					counter++;
					
					
					if(e.isTheres(C) || e.isEnemies(TL,TR) || e.isEnemies(TL,BL) || e.isEnemies(TL,BR) || e.isEnemies(TR,BR)) continue;
					if(e.isEnemies(TR,BL) || e.isEnemies(TR,BR) || e.isEnemies(BL,BR)) continue;
					if(!e.isTheres(TL,TR,BL) && !e.isTheres(TL,TR,BR) && !e.isTheres(TL,BL,BR) && !e.isTheres(TR,BL,BR)) continue;

					
					String s = States.arrayToString(e,TL,TR,BL,BR,C);
					
					if (e.isThere(TL)) {
						if("AAANN".equals(s)){retval+=100;continue;}
						if("AAAEN".equals(s)){retval+=100;continue;}
						if("AAAAN".equals(s)){retval+=100;continue;}
						if("AANAN".equals(s)){retval+=100;continue;}
						if("AAEAN".equals(s)){retval+=100;continue;}
						if("ANAAN".equals(s)){retval+=100;continue;}
						if("AEAAN".equals(s)){retval+=100;continue;}
					}else if(e.isEnemy(TL)){
						if("EAAAN".equals(s)){retval+=100;continue;}
					}else {
						if("NAAAN".equals(s)){retval+=100;continue;}
					}

					
					
				}

			}
			
		}

		
		return retval;

	}

	
	
}
