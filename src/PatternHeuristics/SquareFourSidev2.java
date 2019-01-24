package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class SquareFourSidev2 {
	Evaluator e;
	PatternSearcher ps;

	public SquareFourSidev2 (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrxrdxdxzdlxdxdS", e.kscolour);
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
					Tuple TR = S1.side2(side.opp(),r);


					
					if(e.isThere(S0)||e.isThere(S1)||e.isThere(D0)||e.isThere(D1)) continue;
					String s = States.arrayToString(e,TL,TR,S0,S1,D0,D1);
					
					if (e.isThere(TL)) {
						if("ANNNNN".equals(s)){retval+=200;continue;}
						if("ANENNN".equals(s)){retval+=200;continue;}
						if("ANENEN".equals(s)){retval+=200;continue;}
						if("ANEEEN".equals(s)){retval+=200;continue;}
						if("AEEEEN".equals(s)){retval+=200;continue;}
						if("ANENEE".equals(s)){retval+=200;continue;}
						if("AEENEE".equals(s)){retval+=200;continue;}
						if("AEENEN".equals(s)){retval+=200;continue;}
						if("ANEENN".equals(s)){retval+=200;continue;}
						if("ANEENE".equals(s)){retval+=200;continue;}
						if("AEEENE".equals(s)){retval+=200;continue;}
						if("AEEENN".equals(s)){retval+=200;continue;}
						if("ANENNE".equals(s)){retval+=600;continue;}
						if("AEENNE".equals(s)){retval+=600;continue;}
						if("AEENNN".equals(s)){retval+=200;continue;}
						if("ANNNEN".equals(s)){retval+=200;continue;}
						if("ANNEEN".equals(s)){retval+=600;continue;}
						if("ANNEEE".equals(s)){retval+=200;continue;}
						if("AENEEE".equals(s)){retval+=200;continue;}
						if("AENEEN".equals(s)){retval+=600;continue;}
						if("ANNNEE".equals(s)){retval+=200;continue;}
						if("AENNEE".equals(s)){retval+=200;continue;}
						if("AENNEN".equals(s)){retval+=200;continue;}
						if("ANNENN".equals(s)){retval+=200;continue;}
						if("ANNENE".equals(s)){retval+=150;continue;}
						if("AENENE".equals(s)){retval+=150;continue;}
						if("AENENN".equals(s)){retval+=150;continue;}
						if("ANNNNE".equals(s)){retval+=200;continue;}
						if("AENNNE".equals(s)){retval+=150;continue;}
						if("AENNNN".equals(s)){retval+=200;continue;}
						if("AANNNN".equals(s)){retval+=200;continue;}
						if("AAENNN".equals(s)){retval+=200;continue;}
						if("AAENEN".equals(s)){retval+=200;continue;}
						if("AAEEEN".equals(s)){retval+=200;continue;}
						if("AAENEE".equals(s)){retval+=200;continue;}
						if("AAEENN".equals(s)){retval+=200;continue;}
						if("AAEENE".equals(s)){retval+=200;continue;}
						if("AAENNE".equals(s)){retval+=600;continue;}
						if("AANNEN".equals(s)){retval+=200;continue;}
						if("AANEEN".equals(s)){retval+=600;continue;}
						if("AANEEE".equals(s)){retval+=200;continue;}
						if("AANNEE".equals(s)){retval+=200;continue;}
						if("AANENN".equals(s)){retval+=200;continue;}
						if("AANENE".equals(s)){retval+=200;continue;}
						if("AANNNE".equals(s)){retval+=200;continue;}
					}else if(e.isEnemy(TL)){
						if("ENNNNN".equals(s)){retval+=200;continue;}
						if("ENENNN".equals(s)){retval+=150;continue;}
						if("ENENEN".equals(s)){retval+=150;continue;}
						if("ENEEEN".equals(s)){retval+=150;continue;}
						if("EEEEEN".equals(s)){retval+=100;continue;}
						if("ENENEE".equals(s)){retval+=200;continue;}
						if("EEENEE".equals(s)){retval+=200;continue;}
						if("EEENEN".equals(s)){retval+=100;continue;}
						if("ENEENN".equals(s)){retval+=150;continue;}
						if("ENEENE".equals(s)){retval+=150;continue;}
						if("EEEENE".equals(s)){retval+=100;continue;}
						if("EEEENN".equals(s)){retval+=100;continue;}
						if("ENENNE".equals(s)){retval+=600;continue;}
						if("EEENNE".equals(s)){retval+=550;continue;}
						if("EEENNN".equals(s)){retval+=100;continue;}
						if("ENNNEN".equals(s)){retval+=200;continue;}
						if("ENNEEN".equals(s)){retval+=600;continue;}
						if("ENNEEE".equals(s)){retval+=200;continue;}
						if("EENEEE".equals(s)){retval+=200;continue;}
						if("EENEEN".equals(s)){retval+=550;continue;}
						if("ENNNEE".equals(s)){retval+=200;continue;}
						if("EENNEE".equals(s)){retval+=200;continue;}
						if("EENNEN".equals(s)){retval+=150;continue;}
						if("ENNENN".equals(s)){retval+=150;continue;}
						if("ENNENE".equals(s)){retval+=150;continue;}
						if("EENENE".equals(s)){retval+=100;continue;}
						if("EENENN".equals(s)){retval+=100;continue;}
						if("ENNNNE".equals(s)){retval+=200;continue;}
						if("EENNNE".equals(s)){retval+=150;continue;}
						if("EENNNN".equals(s)){retval+=150;continue;}
						if("EANNNN".equals(s)){retval+=200;continue;}
						if("EAENNN".equals(s)){retval+=150;continue;}
						if("EAENEN".equals(s)){retval+=150;continue;}
						if("EAEEEN".equals(s)){retval+=200;continue;}
						if("EAENEE".equals(s)){retval+=200;continue;}
						if("EAEENN".equals(s)){retval+=200;continue;}
						if("EAEENE".equals(s)){retval+=200;continue;}
						if("EAENNE".equals(s)){retval+=600;continue;}
						if("EANNEN".equals(s)){retval+=150;continue;}
						if("EANEEN".equals(s)){retval+=600;continue;}
						if("EANEEE".equals(s)){retval+=200;continue;}
						if("EANNEE".equals(s)){retval+=200;continue;}
						if("EANENN".equals(s)){retval+=200;continue;}
						if("EANENE".equals(s)){retval+=200;continue;}
						if("EANNNE".equals(s)){retval+=200;continue;}
					}else {
						if("NNNNNN".equals(s)){retval+=200;continue;}
						if("NNENNN".equals(s)){retval+=200;continue;}
						if("NNENEN".equals(s)){retval+=200;continue;}
						if("NNEEEN".equals(s)){retval+=200;continue;}
						if("NEEEEN".equals(s)){retval+=150;continue;}
						if("NNENEE".equals(s)){retval+=200;continue;}
						if("NEENEE".equals(s)){retval+=200;continue;}
						if("NEENEN".equals(s)){retval+=150;continue;}
						if("NNEENN".equals(s)){retval+=200;continue;}
						if("NNEENE".equals(s)){retval+=200;continue;}
						if("NEEENE".equals(s)){retval+=150;continue;}
						if("NEEENN".equals(s)){retval+=150;continue;}
						if("NNENNE".equals(s)){retval+=600;continue;}
						if("NEENNE".equals(s)){retval+=600;continue;}
						if("NEENNN".equals(s)){retval+=150;continue;}
						if("NNNNEN".equals(s)){retval+=200;continue;}
						if("NNNEEN".equals(s)){retval+=600;continue;}
						if("NNNEEE".equals(s)){retval+=200;continue;}
						if("NENEEE".equals(s)){retval+=200;continue;}
						if("NENEEN".equals(s)){retval+=600;continue;}
						if("NNNNEE".equals(s)){retval+=200;continue;}
						if("NENNEE".equals(s)){retval+=200;continue;}
						if("NENNEN".equals(s)){retval+=200;continue;}
						if("NNNENN".equals(s)){retval+=200;continue;}
						if("NNNENE".equals(s)){retval+=200;continue;}
						if("NENENE".equals(s)){retval+=150;continue;}
						if("NENENN".equals(s)){retval+=150;continue;}
						if("NNNNNE".equals(s)){retval+=200;continue;}
						if("NENNNE".equals(s)){retval+=200;continue;}
						if("NENNNN".equals(s)){retval+=200;continue;}
						if("NANNNN".equals(s)){retval+=200;continue;}
						if("NAENNN".equals(s)){retval+=200;continue;}
						if("NAENEN".equals(s)){retval+=150;continue;}
						if("NAEEEN".equals(s)){retval+=200;continue;}
						if("NAENEE".equals(s)){retval+=200;continue;}
						if("NAEENN".equals(s)){retval+=200;continue;}
						if("NAEENE".equals(s)){retval+=200;continue;}
						if("NAENNE".equals(s)){retval+=600;continue;}
						if("NANNEN".equals(s)){retval+=200;continue;}
						if("NANEEN".equals(s)){retval+=600;continue;}
						if("NANEEE".equals(s)){retval+=200;continue;}
						if("NANNEE".equals(s)){retval+=200;continue;}
						if("NANENN".equals(s)){retval+=200;continue;}
						if("NANENE".equals(s)){retval+=200;continue;}
						if("NANNNE".equals(s)){retval+=200;continue;}
					}
				}
				
			}
			
		}

		
		return retval;

	}

	
	
}
