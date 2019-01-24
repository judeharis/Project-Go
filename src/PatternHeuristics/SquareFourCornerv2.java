package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SquareFourCornerv2 {
	Evaluator e;
	PatternSearcher ps;

	public SquareFourCornerv2 (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxzdlxdxrr#", e.kscolour);
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
					
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(r);
					Tuple D0 = S0.side(side);
					Tuple D1 = D0.side(r);
					Tuple TL = tlist.get(0).side(l);
					
					
					if(e.isThere(S0)||e.isThere(S1)||e.isThere(D0)||e.isThere(D1)) continue;
					
					String s = States.arrayToString(e,TL,S0,S1,D0,D1);
					if (e.isThere(TL)) {
						if("ANNNN".equals(s)){retval+=200;continue;}
						if("AENNN".equals(s)){retval+=200;continue;}
						if("AEENN".equals(s)){retval+=200;continue;}
						if("AEEEN".equals(s)){retval+=200;continue;}
						if("AEENE".equals(s)){retval+=200;continue;}
						if("AENEN".equals(s)){retval+=200;continue;}
						if("AENEE".equals(s)){retval+=200;continue;}
						if("AENNE".equals(s)){retval+=600;continue;}
						if("ANENN".equals(s)){retval+=200;continue;}
						if("ANEEN".equals(s)){retval+=600;continue;}
						if("ANEEE".equals(s)){retval+=200;continue;}
						if("ANENE".equals(s)){retval+=200;continue;}
						if("ANNEN".equals(s)){retval+=200;continue;}
						if("ANNEE".equals(s)){retval+=200;continue;}
						if("ANNNE".equals(s)){retval+=200;continue;}
					}else if(e.isEnemy(TL)){
						if("ENNNN".equals(s)){retval+=150;continue;}
						if("EENNN".equals(s)){retval+=100;continue;}
						if("EEENN".equals(s)){retval+=100;continue;}
						if("EEEEN".equals(s)){retval+=100;continue;}
						if("EEENE".equals(s)){retval+=200;continue;}
						if("EENEN".equals(s)){retval+=100;continue;}
						if("EENEE".equals(s)){retval+=200;continue;}
						if("EENNE".equals(s)){retval+=600;continue;}
						if("ENENN".equals(s)){retval+=150;continue;}
						if("ENEEN".equals(s)){retval+=550;continue;}
						if("ENEEE".equals(s)){retval+=200;continue;}
						if("ENENE".equals(s)){retval+=200;continue;}
						if("ENNEN".equals(s)){retval+=150;continue;}
						if("ENNEE".equals(s)){retval+=200;continue;}
						if("ENNNE".equals(s)){retval+=200;continue;}
					}else {
						if("NNNNN".equals(s)){retval+=200;continue;}
						if("NENNN".equals(s)){retval+=150;continue;}
						if("NEENN".equals(s)){retval+=150;continue;}
						if("NEEEN".equals(s)){retval+=150;continue;}
						if("NEENE".equals(s)){retval+=200;continue;}
						if("NENEN".equals(s)){retval+=150;continue;}
						if("NENEE".equals(s)){retval+=200;continue;}
						if("NENNE".equals(s)){retval+=600;continue;}
						if("NNENN".equals(s)){retval+=200;continue;}
						if("NNEEN".equals(s)){retval+=600;continue;}
						if("NNEEE".equals(s)){retval+=200;continue;}
						if("NNENE".equals(s)){retval+=200;continue;}
						if("NNNEN".equals(s)){retval+=200;continue;}
						if("NNNEE".equals(s)){retval+=200;continue;}
						if("NNNNE".equals(s)){retval+=200;continue;}
					}
				}
			}
		}

		
		return retval;

	}

	
	
}
