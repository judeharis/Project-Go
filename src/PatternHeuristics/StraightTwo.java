package PatternHeuristics;

import java.util.ArrayList;
import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class StraightTwo {

	static ArrayList<Pattern> straightTwoPattern = Pattern.sToPv2("xrxrdxzldxdrxrx");

	public static int evaluate(ArrayList<Tuple> sstring , Evaluator e) {
		
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, straightTwoPattern,e.kscolour);

		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR r = side.diag(diagSide);
					UDLR l = side.diag(!diagSide);
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);
					Tuple BL = TL.side2(side,side);
					Tuple BR = TR.side2(side,side);
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);
					counter++;		


					

					
					if (e.isThere(S1) || e.isThere(S2))continue;
					if(!e.isTheres(TL,TR) && !e.isTheres(TL,BR) && !e.isTheres(TR,BL) && !e.isTheres(BL,BR)) continue;
					
					String s = States.arrayToString(e,TL,TR,BL,BR,S1,S2);
					if (e.isThere(TL)) {
						if("AAANNN".equals(s)){retval+=200;continue;}
						if("AAANEN".equals(s)){retval+=200;continue;}
						if("AAAEEN".equals(s)){retval+=200;continue;}
						if("AAANNE".equals(s)){retval+=200;continue;}
						if("AAAENE".equals(s)){retval+=200;continue;}
						if("AAAENN".equals(s)){retval+=200;continue;}
						if("AAAANN".equals(s)){retval+=200;continue;}
						if("AAAAEN".equals(s)){retval+=200;continue;}
						if("AAAANE".equals(s)){retval+=200;continue;}
						if("ANAANN".equals(s)){retval+=200;continue;}
						if("ANAAEN".equals(s)){retval+=200;continue;}
						if("AEAAEN".equals(s)){retval+=200;continue;}
						if("ANAANE".equals(s)){retval+=200;continue;}
						if("AEAANE".equals(s)){retval+=200;continue;}
						if("AEAANN".equals(s)){retval+=200;continue;}
						if("AANNNN".equals(s)){retval+=200;continue;}
						if("AAENNN".equals(s)){retval+=200;continue;}
						if("AAENEN".equals(s)){retval+=200;continue;}
						if("AAEEEN".equals(s)){retval+=200;continue;}
						if("AAENNE".equals(s)){retval+=200;continue;}
						if("AAEENE".equals(s)){retval+=200;continue;}
						if("AAEENN".equals(s)){retval+=200;continue;}
						if("AANNEN".equals(s)){retval+=200;continue;}
						if("AANEEN".equals(s)){retval+=200;continue;}
						if("AANNNE".equals(s)){retval+=200;continue;}
						if("AANENE".equals(s)){retval+=200;continue;}
						if("AANENN".equals(s)){retval+=200;continue;}
						if("AANANN".equals(s)){retval+=200;continue;}
						if("AAEANN".equals(s)){retval+=200;continue;}
						if("AAEAEN".equals(s)){retval+=200;continue;}
						if("AAEANE".equals(s)){retval+=200;continue;}
						if("AANAEN".equals(s)){retval+=200;continue;}
						if("AANANE".equals(s)){retval+=200;continue;}
						if("ANNANN".equals(s)){retval+=200;continue;}
						if("ANEANN".equals(s)){retval+=200;continue;}
						if("ANEAEN".equals(s)){retval+=200;continue;}
						if("AEEAEN".equals(s)){retval+=200;continue;}
						if("ANEANE".equals(s)){retval+=200;continue;}
						if("AEEANE".equals(s)){retval+=200;continue;}
						if("AEEANN".equals(s)){retval+=200;continue;}
						if("ANNAEN".equals(s)){retval+=200;continue;}
						if("AENAEN".equals(s)){retval+=200;continue;}
						if("ANNANE".equals(s)){retval+=200;continue;}
						if("AENANE".equals(s)){retval+=200;continue;}
						if("AENANN".equals(s)){retval+=200;continue;}
					}else if(e.isEnemy(TL)){
						if("EAANNN".equals(s)){retval+=200;continue;}
						if("EAANEN".equals(s)){retval+=200;continue;}
						if("EAAEEN".equals(s)){retval+=200;continue;}
						if("EAANNE".equals(s)){retval+=200;continue;}
						if("EAAENE".equals(s)){retval+=200;continue;}
						if("EAAENN".equals(s)){retval+=200;continue;}
						if("EAAANN".equals(s)){retval+=200;continue;}
						if("EAAAEN".equals(s)){retval+=200;continue;}
						if("EAAANE".equals(s)){retval+=200;continue;}
						if("ENAANN".equals(s)){retval+=200;continue;}
						if("ENAAEN".equals(s)){retval+=200;continue;}
						if("EEAAEN".equals(s)){retval+=200;continue;}
						if("ENAANE".equals(s)){retval+=200;continue;}
						if("EEAANE".equals(s)){retval+=200;continue;}
						if("EEAANN".equals(s)){retval+=200;continue;}
					}else {
						if("NAANNN".equals(s)){retval+=200;continue;}
						if("NAANEN".equals(s)){retval+=200;continue;}
						if("NAAEEN".equals(s)){retval+=200;continue;}
						if("NAANNE".equals(s)){retval+=200;continue;}
						if("NAAENE".equals(s)){retval+=200;continue;}
						if("NAAENN".equals(s)){retval+=200;continue;}
						if("NAAANN".equals(s)){retval+=200;continue;}
						if("NAAAEN".equals(s)){retval+=200;continue;}
						if("NAAANE".equals(s)){retval+=200;continue;}
						if("NNAANN".equals(s)){retval+=200;continue;}
						if("NNAAEN".equals(s)){retval+=200;continue;}
						if("NEAAEN".equals(s)){retval+=200;continue;}
						if("NNAANE".equals(s)){retval+=200;continue;}
						if("NEAANE".equals(s)){retval+=200;continue;}
						if("NEAANN".equals(s)){retval+=200;continue;}
					}
	
				
						
					
				}
			}
		}
		
		
		return retval;
	}


	
	
}
