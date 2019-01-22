package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightThreeCornerv2 {
	Evaluator e;
	PatternSearcher ps;


	public StraightThreeCornerv2 (Evaluator e){
		this.e=e;
	}


	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xldxdxdxr#", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					counter++;				

					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple S0 = tlist.get(0).side(side);
					Tuple S1 = S0.side(side);
					Tuple S2 = S1.side(side);


					
					if (e.isThere(S0) || e.isThere(S2))continue;
					
					String s = States.arrayToString(e,TL,S0,S1,S2);
					


					
					if (e.isThere(TL)) {
						if("ANNN".equals(s)){retval+=600;continue;}
						if("AENN".equals(s)){retval+=600;continue;}
						if("AEEN".equals(s)){retval+=200;continue;}
						if("AENE".equals(s)){retval+=1000;continue;}
						if("ANEN".equals(s)){retval+=200;continue;}
						if("ANEE".equals(s)){retval+=200;continue;}
						if("ANNE".equals(s)){retval+=600;continue;}
						if("ANAN".equals(s)){retval+=800;continue;}
					}else if(e.isEnemy(TL)){
						if("ENNN".equals(s)){retval+=150;continue;}
						if("EENN".equals(s)){retval+=50;continue;}
						if("EENE".equals(s)){retval+=100;continue;}
						if("ENEN".equals(s)){retval+=100;continue;}
						if("ENEE".equals(s)){retval+=200;continue;}
						if("ENNE".equals(s)){retval+=150;continue;}
					}else {
						if("NNNN".equals(s)){retval+=200;continue;}
						if("NENN".equals(s)){retval+=150;continue;}
						if("NEEN".equals(s)){retval+=100;continue;}
						if("NENE".equals(s)){retval+=550;continue;}
						if("NNEN".equals(s)){retval+=200;continue;}
						if("NNEE".equals(s)){retval+=200;continue;}
						if("NNNE".equals(s)){retval+=200;continue;}
						if("NNAN".equals(s)){retval+=400;continue;}
					}


					
					
				}
			}
		}
		
		

		
		
		return retval;
	}


	
	
}
