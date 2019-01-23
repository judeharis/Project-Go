package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;


public class Eye {
	Evaluator e;
	PatternSearcher ps;

	public Eye (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xdlxrrxldx", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					counter++;
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple BL = TL.side2(side,side);
					Tuple BR = TR.side2(side,side);

					
					Tuple C = tlist.get(0).side(side);
					
					
					if(e.isThere(C)) continue;
					

					
					String s = States.arrayToString(e,TL,TR,BL,BR,C);
					
					
					if (e.isThere(TL)) {
						if("ANNNN".equals(s)){retval+=50;continue;}
						if("AANNN".equals(s)){retval+=100;continue;}
						if("AAENN".equals(s)){retval+=50;continue;}
						if("AANEN".equals(s)){retval+=50;continue;}
						if("AAANN".equals(s)){retval+=100;continue;}
						if("AAAEN".equals(s)){retval+=100;continue;}
						if("AAAAN".equals(s)){retval+=100;continue;}
						if("AANAN".equals(s)){retval+=100;continue;}
						if("AAEAN".equals(s)){retval+=100;continue;}
						if("ANANN".equals(s)){retval+=100;continue;}
						if("AEANN".equals(s)){retval+=50;continue;}
						if("ANAEN".equals(s)){retval+=50;continue;}
						if("ANAAN".equals(s)){retval+=100;continue;}
						if("AEAAN".equals(s)){retval+=100;continue;}
						if("ANNAN".equals(s)){retval+=100;continue;}
						if("AENAN".equals(s)){retval+=50;continue;}
						if("ANEAN".equals(s)){retval+=50;continue;}
					}else if(e.isEnemy(TL)){
						if("EAANN".equals(s)){retval+=50;continue;}
						if("EAAAN".equals(s)){retval+=100;continue;}
						if("EANAN".equals(s)){retval+=50;continue;}
						if("ENAAN".equals(s)){retval+=50;continue;}
					}else {
						if("NANNN".equals(s)){retval+=50;continue;}
						if("NAANN".equals(s)){retval+=100;continue;}
						if("NAAEN".equals(s)){retval+=50;continue;}
						if("NAAAN".equals(s)){retval+=100;continue;}
						if("NANAN".equals(s)){retval+=100;continue;}
						if("NAEAN".equals(s)){retval+=50;continue;}
						if("NNANN".equals(s)){retval+=50;continue;}
						if("NNAAN".equals(s)){retval+=100;continue;}
						if("NEAAN".equals(s)){retval+=50;continue;}
						if("NNNAN".equals(s)){retval+=50;continue;}
					}

					
					
				}

			}
			
		}

		
		return retval;

	}

	
	
}
