package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BentThreeCornerv2 {
	Evaluator e;
	PatternSearcher ps;

	public BentThreeCornerv2 (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xdlxdlxrr#", e.kscolour);
		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, pattern);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if(!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR side = ps.dirNumToDir(counter);
					UDLR l = side.diag(!diagSide);
					counter++;
					Tuple TL = tlist.get(0).side(l);
					Tuple LT = TL.side2(side,l);

					
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(side);
					Tuple S0 = S1.side(l);
					
					String s = States.arrayToString(e,TL,LT,S0,S1,S2);
					if (e.isThere(TL)) {
						if("ANNNN".equals(s)){retval+=200;continue;}
						if("AENNN".equals(s)){retval+=150;continue;}
						if("AENNE".equals(s)){retval+=150;continue;}
						if("AEENE".equals(s)){retval+=100;continue;}
						if("AENEE".equals(s)){retval+=200;continue;}
						if("AEENN".equals(s)){retval+=50;continue;}
						if("AENEN".equals(s)){retval+=100;continue;}
						if("ANNNE".equals(s)){retval+=200;continue;}
						if("ANENE".equals(s)){retval+=550;continue;}
						if("ANNEE".equals(s)){retval+=200;continue;}
						if("ANENN".equals(s)){retval+=150;continue;}
						if("ANEEN".equals(s)){retval+=100;continue;}
						if("ANNEN".equals(s)){retval+=200;continue;}
						if("AANNN".equals(s)){retval+=600;continue;}
						if("AANNE".equals(s)){retval+=600;continue;}
						if("AAENE".equals(s)){retval+=1000;continue;}
						if("AANEE".equals(s)){retval+=200;continue;}
						if("AAENN".equals(s)){retval+=600;continue;}
						if("AAEEN".equals(s)){retval+=200;continue;}
						if("AANEN".equals(s)){retval+=200;continue;}
						if("AANAN".equals(s)){retval+=900;continue;}
						if("ANNAN".equals(s)){retval+=500;continue;}
						if("AENAN".equals(s)){retval+=50;continue;}
					}else if(e.isEnemy(TL)){
						if("ENNNN".equals(s)){retval+=50;continue;}
						if("EENEN".equals(s)){retval+=50;continue;}
						if("ENENE".equals(s)){retval+=50;continue;}
						if("ENENN".equals(s)){retval+=50;continue;}
						if("ENEEN".equals(s)){retval+=100;continue;}
						if("ENNEN".equals(s)){retval+=50;continue;}
						if("EANNN".equals(s)){retval+=150;continue;}
						if("EANNE".equals(s)){retval+=50;continue;}
						if("EAENE".equals(s)){retval+=100;continue;}
						if("EAENN".equals(s)){retval+=150;continue;}
						if("EAEEN".equals(s)){retval+=200;continue;}
						if("EANEN".equals(s)){retval+=100;continue;}
						if("EANAN".equals(s)){retval+=50;continue;}
						if("ENNAN".equals(s)){retval+=50;continue;}
					}else {
						if("NNNNN".equals(s)){retval+=150;continue;}
						if("NENNN".equals(s)){retval+=50;continue;}
						if("NENNE".equals(s)){retval+=50;continue;}
						if("NEENE".equals(s)){retval+=50;continue;}
						if("NENEE".equals(s)){retval+=100;continue;}
						if("NENEN".equals(s)){retval+=50;continue;}
						if("NNNNE".equals(s)){retval+=100;continue;}
						if("NNENE".equals(s)){retval+=100;continue;}
						if("NNNEE".equals(s)){retval+=100;continue;}
						if("NNENN".equals(s)){retval+=100;continue;}
						if("NNEEN".equals(s)){retval+=100;continue;}
						if("NNNEN".equals(s)){retval+=150;continue;}
						if("NANNN".equals(s)){retval+=200;continue;}
						if("NANNE".equals(s)){retval+=150;continue;}
						if("NAENE".equals(s)){retval+=550;continue;}
						if("NANEE".equals(s)){retval+=100;continue;}
						if("NAENN".equals(s)){retval+=200;continue;}
						if("NAEEN".equals(s)){retval+=200;continue;}
						if("NANEN".equals(s)){retval+=200;continue;}
						if("NANAN".equals(s)){retval+=500;continue;}
						if("NNNAN".equals(s)){retval+=100;continue;}
						if("NENAN".equals(s)){retval+=50;continue;}
					}

				}
			}
			
		}
		
		
		pattern = Pattern.sToPv2("xrxzdlxdxrrX", e.kscolour);
		pMatches =ps.allStringMatchv2(sstring, pattern);
		
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
					Tuple S1 = tlist.get(0).side(side);
					Tuple S2 = S1.side(r);
					Tuple S0 = S1.side(side);
					
					if(e.isThere(S0) || e.isThere(S2) || e.isThere(S1))continue;
					
					String s = States.arrayToString(e,TL,S0,S1,S2);
					if (e.isThere(TL)) {
						if("ANNN".equals(s)){retval+=600;continue;}
						if("ANEN".equals(s)){retval+=200;continue;}
						if("ANEE".equals(s)){retval+=200;continue;}
						if("AEEN".equals(s)){retval+=200;continue;}
						if("ANNE".equals(s)){retval+=600;continue;}
						if("AENN".equals(s)){retval+=600;continue;}
					}else if(e.isEnemy(TL)){
						if("ENNN".equals(s)){retval+=550;continue;}
						if("ENEN".equals(s)){retval+=100;continue;}
						if("ENEE".equals(s)){retval+=100;continue;}
						if("EEEN".equals(s)){retval+=100;continue;}
						if("ENNE".equals(s)){retval+=550;continue;}
						if("EENN".equals(s)){retval+=550;continue;}
					}else {
						if("NNNN".equals(s)){retval+=600;continue;}
						if("NNEN".equals(s)){retval+=150;continue;}
						if("NNEE".equals(s)){retval+=150;continue;}
						if("NEEN".equals(s)){retval+=150;continue;}
						if("NNNE".equals(s)){retval+=600;continue;}
						if("NENN".equals(s)){retval+=600;continue;}
					}
				}
			}
			
		}
		
		
		pattern = Pattern.sToPv2("xrxzdlxdrxr#", e.kscolour);
		pMatches =ps.allStringMatchv2(sstring, pattern);
		
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
					Tuple BL = TL.side2(side,side);
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(r);
					Tuple S0 = S1.side(side);
					
					
					if(e.isThere(S0) || e.isThere(S2))continue;
					String s = States.arrayToString(e,TL,BL,S0,S1,S2);
					
					if (e.isThere(TL)) {
						if("ANNNN".equals(s)){retval+=600;continue;}
						if("AENNN".equals(s)){retval+=600;continue;}
						if("AENNE".equals(s)){retval+=500;continue;}
						if("AENEE".equals(s)){retval+=50;continue;}
						if("AENEN".equals(s)){retval+=150;continue;}
						if("AEEEN".equals(s)){retval+=100;continue;}
						if("AEENN".equals(s)){retval+=500;continue;}
						if("ANNNE".equals(s)){retval+=600;continue;}
						if("ANNEE".equals(s)){retval+=150;continue;}
						if("ANENE".equals(s)){retval+=500;continue;}
						if("ANNEN".equals(s)){retval+=200;continue;}
						if("ANEEN".equals(s)){retval+=200;continue;}
						if("ANENN".equals(s)){retval+=600;continue;}
						if("AANNN".equals(s)){retval+=600;continue;}
						if("AANNE".equals(s)){retval+=600;continue;}
						if("AANEE".equals(s)){retval+=200;continue;}
						if("AAENE".equals(s)){retval+=1000;continue;}
						if("AANEN".equals(s)){retval+=200;continue;}
						if("AAEEN".equals(s)){retval+=200;continue;}
						if("AAENN".equals(s)){retval+=600;continue;}
						if("AANAN".equals(s)){retval+=425;continue;}
						if("ANNAN".equals(s)){retval+=850;continue;}
						if("AENAN".equals(s)){retval+=900;continue;}
					}else if(e.isEnemy(TL)){
						if("ENNNN".equals(s)){retval+=150;continue;}
						if("EENNN".equals(s)){retval+=150;continue;}
						if("EENNE".equals(s)){retval+=50;continue;}
						if("EENEN".equals(s)){retval+=100;continue;}
						if("EEEEN".equals(s)){retval+=100;continue;}
						if("EEENN".equals(s)){retval+=100;continue;}
						if("ENNNE".equals(s)){retval+=100;continue;}
						if("ENNEE".equals(s)){retval+=50;continue;}
						if("ENENE".equals(s)){retval+=500;continue;}
						if("ENNEN".equals(s)){retval+=150;continue;}
						if("ENEEN".equals(s)){retval+=200;continue;}
						if("ENENN".equals(s)){retval+=200;continue;}
						if("EANNN".equals(s)){retval+=600;continue;}
						if("EANNE".equals(s)){retval+=550;continue;}
						if("EANEE".equals(s)){retval+=100;continue;}
						if("EAENE".equals(s)){retval+=1000;continue;}
						if("EANEN".equals(s)){retval+=150;continue;}
						if("EAEEN".equals(s)){retval+=200;continue;}
						if("EAENN".equals(s)){retval+=600;continue;}
						if("EANAN".equals(s)){retval+=450;continue;}
						if("ENNAN".equals(s)){retval+=500;continue;}
						if("EENAN".equals(s)){retval+=50;continue;}
					}else {
						if("NNNNN".equals(s)){retval+=600;continue;}
						if("NENNN".equals(s)){retval+=150;continue;}
						if("NENNE".equals(s)){retval+=50;continue;}
						if("NENEN".equals(s)){retval+=100;continue;}
						if("NEEEN".equals(s)){retval+=100;continue;}
						if("NEENN".equals(s)){retval+=100;continue;}
						if("NNNNE".equals(s)){retval+=550;continue;}
						if("NNNEE".equals(s)){retval+=50;continue;}
						if("NNENE".equals(s)){retval+=500;continue;}
						if("NNNEN".equals(s)){retval+=150;continue;}
						if("NNEEN".equals(s)){retval+=200;continue;}
						if("NNENN".equals(s)){retval+=600;continue;}
						if("NANNN".equals(s)){retval+=600;continue;}
						if("NANNE".equals(s)){retval+=600;continue;}
						if("NANEE".equals(s)){retval+=150;continue;}
						if("NAENE".equals(s)){retval+=1000;continue;}
						if("NANEN".equals(s)){retval+=200;continue;}
						if("NAEEN".equals(s)){retval+=200;continue;}
						if("NAENN".equals(s)){retval+=600;continue;}
						if("NANAN".equals(s)){retval+=425;continue;}
						if("NNNAN".equals(s)){retval+=900;continue;}
						if("NENAN".equals(s)){retval+=500;continue;}
					}
	
				}

			}
			
		}
		
		


		
		return retval;

	}

	
	
}
