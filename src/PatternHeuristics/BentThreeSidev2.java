package PatternHeuristics;

import java.util.ArrayList;

import Go.SlickGo.Evaluator;
import Go.SlickGo.Pattern;
import Go.SlickGo.PatternSearcher;
import Go.SlickGo.Tuple;
import Go.SlickGo.UDLR;

public class BentThreeSidev2 {
	Evaluator e;
	PatternSearcher ps;

	public BentThreeSidev2 (Evaluator e){
		this.e=e;
	}



	public int evaluate(ArrayList<Tuple> sstring) {
		int retval = 0;
		ps = new PatternSearcher(e.cB,e.kscolour);
		
		ArrayList<Pattern> pattern = Pattern.sToPv2("xrdxdxdSllluxurx", e.kscolour);
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
					
					Tuple S2 = tlist.get(0).side(side);
					Tuple S1 = S2.side(side);
					Tuple S0 = S1.side(l);
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side(r);
					Tuple LT = S0.side2(side.opp(),l);
					Tuple LL = S0.side2(l,l);
					
					if(e.isEnemies(TL,TR) || e.isEnemies(LT,LL)  || e.isThere(S0) || e.isThere(S2)) continue;
					
					String s = States.arrayToString(e,TL,TR,LT,LL,S0,S1,S2);
					

					if (e.isThere(TL)) {
						if("ANAANNN".equals(s)){retval+=600;continue;}
						if("ANAAENN".equals(s)){retval+=600;continue;}
						if("ANAAENE".equals(s)){retval+=1000;continue;}
						if("AEAAENE".equals(s)){retval+=1000;continue;}
						if("ANAAEEN".equals(s)){retval+=200;continue;}
						if("AEAAEEN".equals(s)){retval+=200;continue;}
						if("AEAAENN".equals(s)){retval+=600;continue;}
						if("ANAANNE".equals(s)){retval+=600;continue;}
						if("ANAANEE".equals(s)){retval+=150;continue;}
						if("AEAANEE".equals(s)){retval+=100;continue;}
						if("AEAANNE".equals(s)){retval+=550;continue;}
						if("ANAANEN".equals(s)){retval+=200;continue;}
						if("AEAANEN".equals(s)){retval+=150;continue;}
						if("AEAANNN".equals(s)){retval+=600;continue;}
						if("ANAANAN".equals(s)){retval+=850;continue;}
						if("AEAANAN".equals(s)){retval+=900;continue;}
						if("AAAANAN".equals(s)){retval+=850;continue;}
						if("AAAANNN".equals(s)){retval+=600;continue;}
						if("AAAAENN".equals(s)){retval+=600;continue;}
						if("AAAAENE".equals(s)){retval+=1000;continue;}
						if("AAAAEEN".equals(s)){retval+=200;continue;}
						if("AAAANNE".equals(s)){retval+=600;continue;}
						if("AAAANEE".equals(s)){retval+=200;continue;}
						if("AAAANEN".equals(s)){retval+=200;continue;}
						if("ANNANNN".equals(s)){retval+=150;continue;}
						if("ANEANNN".equals(s)){retval+=100;continue;}
						if("ANEAENN".equals(s)){retval+=50;continue;}
						if("ANEAENE".equals(s)){retval+=100;continue;}
						if("AEEAENE".equals(s)){retval+=100;continue;}
						if("AEEAENN".equals(s)){retval+=50;continue;}
						if("ANEANNE".equals(s)){retval+=100;continue;}
						if("ANEANEE".equals(s)){retval+=150;continue;}
						if("AEEANEE".equals(s)){retval+=100;continue;}
						if("AEEANNE".equals(s)){retval+=100;continue;}
						if("ANEANEN".equals(s)){retval+=50;continue;}
						if("AEEANEN".equals(s)){retval+=50;continue;}
						if("AEEANNN".equals(s)){retval+=100;continue;}
						if("ANNAENN".equals(s)){retval+=150;continue;}
						if("ANNAENE".equals(s)){retval+=550;continue;}
						if("AENAENE".equals(s)){retval+=550;continue;}
						if("ANNAEEN".equals(s)){retval+=100;continue;}
						if("AENAEEN".equals(s)){retval+=100;continue;}
						if("AENAENN".equals(s)){retval+=150;continue;}
						if("ANNANNE".equals(s)){retval+=150;continue;}
						if("ANNANEE".equals(s)){retval+=150;continue;}
						if("AENANEE".equals(s)){retval+=100;continue;}
						if("AENANNE".equals(s)){retval+=100;continue;}
						if("ANNANEN".equals(s)){retval+=150;continue;}
						if("AENANEN".equals(s)){retval+=100;continue;}
						if("AENANNN".equals(s)){retval+=150;continue;}
						if("ANNANAN".equals(s)){retval+=450;continue;}
						if("AEEANAN".equals(s)){retval+=50;continue;}
						if("AENANAN".equals(s)){retval+=500;continue;}
						if("AANANAN".equals(s)){retval+=450;continue;}
						if("AANANNN".equals(s)){retval+=200;continue;}
						if("AAEANNN".equals(s)){retval+=150;continue;}
						if("AAEAENN".equals(s)){retval+=50;continue;}
						if("AAEAENE".equals(s)){retval+=100;continue;}
						if("AAEANNE".equals(s)){retval+=150;continue;}
						if("AAEANEE".equals(s)){retval+=200;continue;}
						if("AAEANEN".equals(s)){retval+=100;continue;}
						if("AANAENN".equals(s)){retval+=150;continue;}
						if("AANAENE".equals(s)){retval+=550;continue;}
						if("AANAEEN".equals(s)){retval+=100;continue;}
						if("AANANNE".equals(s)){retval+=200;continue;}
						if("AANANEE".equals(s)){retval+=200;continue;}
						if("AANANEN".equals(s)){retval+=200;continue;}
						if("ANANNNN".equals(s)){retval+=600;continue;}
						if("ANAENNN".equals(s)){retval+=600;continue;}
						if("ANAEENN".equals(s)){retval+=600;continue;}
						if("ANAEENE".equals(s)){retval+=1000;continue;}
						if("AEAEENE".equals(s)){retval+=1000;continue;}
						if("ANAEEEN".equals(s)){retval+=200;continue;}
						if("AEAEEEN".equals(s)){retval+=200;continue;}
						if("AEAEENN".equals(s)){retval+=600;continue;}
						if("ANAENNE".equals(s)){retval+=600;continue;}
						if("ANAENEE".equals(s)){retval+=150;continue;}
						if("AEAENEE".equals(s)){retval+=100;continue;}
						if("AEAENNE".equals(s)){retval+=550;continue;}
						if("ANAENEN".equals(s)){retval+=200;continue;}
						if("AEAENEN".equals(s)){retval+=150;continue;}
						if("AEAENNN".equals(s)){retval+=600;continue;}
						if("ANANENN".equals(s)){retval+=600;continue;}
						if("ANANENE".equals(s)){retval+=1000;continue;}
						if("AEANENE".equals(s)){retval+=1000;continue;}
						if("ANANEEN".equals(s)){retval+=200;continue;}
						if("AEANEEN".equals(s)){retval+=200;continue;}
						if("AEANENN".equals(s)){retval+=600;continue;}
						if("ANANNNE".equals(s)){retval+=600;continue;}
						if("ANANNEE".equals(s)){retval+=150;continue;}
						if("AEANNEE".equals(s)){retval+=100;continue;}
						if("AEANNNE".equals(s)){retval+=550;continue;}
						if("ANANNEN".equals(s)){retval+=200;continue;}
						if("AEANNEN".equals(s)){retval+=150;continue;}
						if("AEANNNN".equals(s)){retval+=600;continue;}
						if("ANANNAN".equals(s)){retval+=850;continue;}
						if("ANAENAN".equals(s)){retval+=850;continue;}
						if("AEAENAN".equals(s)){retval+=900;continue;}
						if("AEANNAN".equals(s)){retval+=900;continue;}
						if("AAANNAN".equals(s)){retval+=850;continue;}
						if("AAAENAN".equals(s)){retval+=850;continue;}
						if("AAANNNN".equals(s)){retval+=600;continue;}
						if("AAAENNN".equals(s)){retval+=600;continue;}
						if("AAAEENN".equals(s)){retval+=600;continue;}
						if("AAAEENE".equals(s)){retval+=1000;continue;}
						if("AAAEEEN".equals(s)){retval+=200;continue;}
						if("AAAENNE".equals(s)){retval+=600;continue;}
						if("AAAENEE".equals(s)){retval+=200;continue;}
						if("AAAENEN".equals(s)){retval+=200;continue;}
						if("AAANENN".equals(s)){retval+=600;continue;}
						if("AAANENE".equals(s)){retval+=1000;continue;}
						if("AAANEEN".equals(s)){retval+=200;continue;}
						if("AAANNNE".equals(s)){retval+=600;continue;}
						if("AAANNEE".equals(s)){retval+=200;continue;}
						if("AAANNEN".equals(s)){retval+=200;continue;}
						if("ANNNNNN".equals(s)){retval+=150;continue;}
						if("ANNENNN".equals(s)){retval+=150;continue;}
						if("ANNEENN".equals(s)){retval+=150;continue;}
						if("ANNEENE".equals(s)){retval+=550;continue;}
						if("AENEENE".equals(s)){retval+=500;continue;}
						if("ANNEEEN".equals(s)){retval+=100;continue;}
						if("AENEEEN".equals(s)){retval+=100;continue;}
						if("AENEENN".equals(s)){retval+=100;continue;}
						if("ANNENNE".equals(s)){retval+=150;continue;}
						if("ANNENEE".equals(s)){retval+=150;continue;}
						if("AENENEE".equals(s)){retval+=100;continue;}
						if("AENENNE".equals(s)){retval+=100;continue;}
						if("ANNENEN".equals(s)){retval+=150;continue;}
						if("AENENEN".equals(s)){retval+=100;continue;}
						if("AENENNN".equals(s)){retval+=150;continue;}
						if("ANENNNN".equals(s)){retval+=100;continue;}
						if("ANENENN".equals(s)){retval+=50;continue;}
						if("ANENENE".equals(s)){retval+=100;continue;}
						if("AEENENE".equals(s)){retval+=50;continue;}
						if("AEENENN".equals(s)){retval+=50;continue;}
						if("ANENNNE".equals(s)){retval+=100;continue;}
						if("ANENNEE".equals(s)){retval+=150;continue;}
						if("AEENNEE".equals(s)){retval+=100;continue;}
						if("AEENNNE".equals(s)){retval+=100;continue;}
						if("ANENNEN".equals(s)){retval+=50;continue;}
						if("AEENNEN".equals(s)){retval+=50;continue;}
						if("AEENNNN".equals(s)){retval+=100;continue;}
						if("ANNNENN".equals(s)){retval+=150;continue;}
						if("ANNNENE".equals(s)){retval+=550;continue;}
						if("AENNENE".equals(s)){retval+=550;continue;}
						if("ANNNEEN".equals(s)){retval+=100;continue;}
						if("AENNEEN".equals(s)){retval+=100;continue;}
						if("AENNENN".equals(s)){retval+=150;continue;}
						if("ANNNNNE".equals(s)){retval+=150;continue;}
						if("ANNNNEE".equals(s)){retval+=150;continue;}
						if("AENNNEE".equals(s)){retval+=100;continue;}
						if("AENNNNE".equals(s)){retval+=100;continue;}
						if("ANNNNEN".equals(s)){retval+=150;continue;}
						if("AENNNEN".equals(s)){retval+=100;continue;}
						if("AENNNNN".equals(s)){retval+=150;continue;}
						if("ANNNNAN".equals(s)){retval+=450;continue;}
						if("ANNENAN".equals(s)){retval+=450;continue;}
						if("AENENAN".equals(s)){retval+=500;continue;}
						if("AEENNAN".equals(s)){retval+=50;continue;}
						if("AENNNAN".equals(s)){retval+=500;continue;}
						if("AANNNAN".equals(s)){retval+=450;continue;}
						if("AANENAN".equals(s)){retval+=450;continue;}
						if("AANNNNN".equals(s)){retval+=200;continue;}
						if("AANENNN".equals(s)){retval+=200;continue;}
						if("AANEENN".equals(s)){retval+=150;continue;}
						if("AANEENE".equals(s)){retval+=550;continue;}
						if("AANEEEN".equals(s)){retval+=100;continue;}
						if("AANENNE".equals(s)){retval+=200;continue;}
						if("AANENEE".equals(s)){retval+=200;continue;}
						if("AANENEN".equals(s)){retval+=200;continue;}
						if("AAENNNN".equals(s)){retval+=150;continue;}
						if("AAENENN".equals(s)){retval+=50;continue;}
						if("AAENENE".equals(s)){retval+=100;continue;}
						if("AAENNNE".equals(s)){retval+=150;continue;}
						if("AAENNEE".equals(s)){retval+=200;continue;}
						if("AAENNEN".equals(s)){retval+=100;continue;}
						if("AANNENN".equals(s)){retval+=150;continue;}
						if("AANNENE".equals(s)){retval+=550;continue;}
						if("AANNEEN".equals(s)){retval+=100;continue;}
						if("AANNNNE".equals(s)){retval+=200;continue;}
						if("AANNNEE".equals(s)){retval+=200;continue;}
						if("AANNNEN".equals(s)){retval+=200;continue;}
					}else if(e.isEnemy(TL)){
						if("ENNENNN".equals(s)){retval+=50;continue;}
						if("ENNEENN".equals(s)){retval+=100;continue;}
						if("ENNEENE".equals(s)){retval+=50;continue;}
						if("ENNEEEN".equals(s)){retval+=100;continue;}
						if("ENNENNE".equals(s)){retval+=50;continue;}
						if("ENNENEE".equals(s)){retval+=50;continue;}
						if("ENNENEN".equals(s)){retval+=50;continue;}
						if("ENENNNN".equals(s)){retval+=50;continue;}
						if("ENENNEE".equals(s)){retval+=50;continue;}
						if("ENENNEN".equals(s)){retval+=50;continue;}
						if("ENNNNNN".equals(s)){retval+=100;continue;}
						if("ENNNENN".equals(s)){retval+=100;continue;}
						if("ENNNENE".equals(s)){retval+=50;continue;}
						if("ENNNEEN".equals(s)){retval+=100;continue;}
						if("ENNNNNE".equals(s)){retval+=50;continue;}
						if("ENNNNEE".equals(s)){retval+=50;continue;}
						if("ENNNNEN".equals(s)){retval+=100;continue;}
						if("ENEANNN".equals(s)){retval+=50;continue;}
						if("ENEAEEN".equals(s)){retval+=50;continue;}
						if("ENEANEE".equals(s)){retval+=50;continue;}
						if("ENEANEN".equals(s)){retval+=100;continue;}
						if("ENNANNN".equals(s)){retval+=100;continue;}
						if("ENNAENN".equals(s)){retval+=100;continue;}
						if("ENNAENE".equals(s)){retval+=50;continue;}
						if("ENNAEEN".equals(s)){retval+=150;continue;}
						if("ENNANNE".equals(s)){retval+=50;continue;}
						if("ENNANEE".equals(s)){retval+=50;continue;}
						if("ENNANEN".equals(s)){retval+=100;continue;}
						if("ENAANNN".equals(s)){retval+=150;continue;}
						if("ENAAENN".equals(s)){retval+=200;continue;}
						if("ENAAENE".equals(s)){retval+=550;continue;}
						if("ENAAEEN".equals(s)){retval+=200;continue;}
						if("ENAANNE".equals(s)){retval+=100;continue;}
						if("ENAANEE".equals(s)){retval+=50;continue;}
						if("ENAANEN".equals(s)){retval+=150;continue;}
						if("ENAANAN".equals(s)){retval+=500;continue;}
						if("EAAANAN".equals(s)){retval+=900;continue;}
						if("EAAANNN".equals(s)){retval+=600;continue;}
						if("EAAAENN".equals(s)){retval+=600;continue;}
						if("EAAAENE".equals(s)){retval+=1000;continue;}
						if("EAAAEEN".equals(s)){retval+=200;continue;}
						if("EAAANNE".equals(s)){retval+=550;continue;}
						if("EAAANEE".equals(s)){retval+=100;continue;}
						if("EAAANEN".equals(s)){retval+=150;continue;}
						if("ENEANAN".equals(s)){retval+=50;continue;}
						if("ENNANAN".equals(s)){retval+=100;continue;}
						if("EAEANAN".equals(s)){retval+=50;continue;}
						if("EANANAN".equals(s)){retval+=500;continue;}
						if("EAEANNN".equals(s)){retval+=100;continue;}
						if("EAEAENN".equals(s)){retval+=50;continue;}
						if("EAEAEEN".equals(s)){retval+=50;continue;}
						if("EAEANNE".equals(s)){retval+=50;continue;}
						if("EAEANEE".equals(s)){retval+=100;continue;}
						if("EAEANEN".equals(s)){retval+=50;continue;}
						if("EANANNN".equals(s)){retval+=150;continue;}
						if("EANAENN".equals(s)){retval+=150;continue;}
						if("EANAENE".equals(s)){retval+=500;continue;}
						if("EANAEEN".equals(s)){retval+=100;continue;}
						if("EANANNE".equals(s)){retval+=100;continue;}
						if("EANANEE".equals(s)){retval+=100;continue;}
						if("EANANEN".equals(s)){retval+=100;continue;}
						if("ENAENNN".equals(s)){retval+=150;continue;}
						if("ENAEENN".equals(s)){retval+=200;continue;}
						if("ENAEENE".equals(s)){retval+=550;continue;}
						if("ENAEEEN".equals(s)){retval+=200;continue;}
						if("ENAENNE".equals(s)){retval+=100;continue;}
						if("ENAENEE".equals(s)){retval+=50;continue;}
						if("ENAENEN".equals(s)){retval+=150;continue;}
						if("ENANNNN".equals(s)){retval+=150;continue;}
						if("ENANENN".equals(s)){retval+=200;continue;}
						if("ENANENE".equals(s)){retval+=550;continue;}
						if("ENANEEN".equals(s)){retval+=200;continue;}
						if("ENANNNE".equals(s)){retval+=100;continue;}
						if("ENANNEE".equals(s)){retval+=50;continue;}
						if("ENANNEN".equals(s)){retval+=150;continue;}
						if("ENAENAN".equals(s)){retval+=500;continue;}
						if("ENANNAN".equals(s)){retval+=500;continue;}
						if("EAAENAN".equals(s)){retval+=900;continue;}
						if("EAANNAN".equals(s)){retval+=900;continue;}
						if("EAAENNN".equals(s)){retval+=600;continue;}
						if("EAAEENN".equals(s)){retval+=600;continue;}
						if("EAAEENE".equals(s)){retval+=1000;continue;}
						if("EAAEEEN".equals(s)){retval+=200;continue;}
						if("EAAENNE".equals(s)){retval+=550;continue;}
						if("EAAENEE".equals(s)){retval+=100;continue;}
						if("EAAENEN".equals(s)){retval+=150;continue;}
						if("EAANNNN".equals(s)){retval+=600;continue;}
						if("EAANENN".equals(s)){retval+=600;continue;}
						if("EAANENE".equals(s)){retval+=1000;continue;}
						if("EAANEEN".equals(s)){retval+=200;continue;}
						if("EAANNNE".equals(s)){retval+=550;continue;}
						if("EAANNEE".equals(s)){retval+=100;continue;}
						if("EAANNEN".equals(s)){retval+=150;continue;}
						if("ENNENAN".equals(s)){retval+=50;continue;}
						if("ENENNAN".equals(s)){retval+=50;continue;}
						if("ENNNNAN".equals(s)){retval+=100;continue;}
						if("EANENAN".equals(s)){retval+=500;continue;}
						if("EAENNAN".equals(s)){retval+=50;continue;}
						if("EANNNAN".equals(s)){retval+=500;continue;}
						if("EANENNN".equals(s)){retval+=150;continue;}
						if("EANEENN".equals(s)){retval+=100;continue;}
						if("EANEENE".equals(s)){retval+=500;continue;}
						if("EANEEEN".equals(s)){retval+=100;continue;}
						if("EANENNE".equals(s)){retval+=50;continue;}
						if("EANENEE".equals(s)){retval+=100;continue;}
						if("EANENEN".equals(s)){retval+=50;continue;}
						if("EAENNNN".equals(s)){retval+=100;continue;}
						if("EAENENN".equals(s)){retval+=50;continue;}
						if("EAENEEN".equals(s)){retval+=50;continue;}
						if("EAENNNE".equals(s)){retval+=50;continue;}
						if("EAENNEE".equals(s)){retval+=100;continue;}
						if("EAENNEN".equals(s)){retval+=50;continue;}
						if("EANNNNN".equals(s)){retval+=150;continue;}
						if("EANNENN".equals(s)){retval+=150;continue;}
						if("EANNENE".equals(s)){retval+=500;continue;}
						if("EANNEEN".equals(s)){retval+=150;continue;}
						if("EANNNNE".equals(s)){retval+=100;continue;}
						if("EANNNEE".equals(s)){retval+=100;continue;}
						if("EANNNEN".equals(s)){retval+=100;continue;}
					}else {
						if("NNNNNNN".equals(s)){retval+=150;continue;}
						if("NNNENNN".equals(s)){retval+=150;continue;}
						if("NNNEENN".equals(s)){retval+=100;continue;}
						if("NNNEENE".equals(s)){retval+=500;continue;}
						if("NENEENE".equals(s)){retval+=50;continue;}
						if("NNNEEEN".equals(s)){retval+=100;continue;}
						if("NENEEEN".equals(s)){retval+=100;continue;}
						if("NENEENN".equals(s)){retval+=100;continue;}
						if("NNNENNE".equals(s)){retval+=100;continue;}
						if("NNNENEE".equals(s)){retval+=100;continue;}
						if("NENENEE".equals(s)){retval+=50;continue;}
						if("NENENNE".equals(s)){retval+=50;continue;}
						if("NNNENEN".equals(s)){retval+=100;continue;}
						if("NENENEN".equals(s)){retval+=100;continue;}
						if("NENENNN".equals(s)){retval+=100;continue;}
						if("NNENNNN".equals(s)){retval+=100;continue;}
						if("NNENENN".equals(s)){retval+=50;continue;}
						if("NNENENE".equals(s)){retval+=50;continue;}
						if("NNENNNE".equals(s)){retval+=50;continue;}
						if("NNENNEE".equals(s)){retval+=100;continue;}
						if("NEENNEE".equals(s)){retval+=50;continue;}
						if("NEENNNE".equals(s)){retval+=50;continue;}
						if("NNENNEN".equals(s)){retval+=50;continue;}
						if("NEENNEN".equals(s)){retval+=50;continue;}
						if("NEENNNN".equals(s)){retval+=50;continue;}
						if("NNNNENN".equals(s)){retval+=150;continue;}
						if("NNNNENE".equals(s)){retval+=550;continue;}
						if("NENNENE".equals(s)){retval+=50;continue;}
						if("NNNNEEN".equals(s)){retval+=100;continue;}
						if("NENNEEN".equals(s)){retval+=100;continue;}
						if("NENNENN".equals(s)){retval+=100;continue;}
						if("NNNNNNE".equals(s)){retval+=100;continue;}
						if("NNNNNEE".equals(s)){retval+=100;continue;}
						if("NENNNEE".equals(s)){retval+=50;continue;}
						if("NENNNNE".equals(s)){retval+=50;continue;}
						if("NNNNNEN".equals(s)){retval+=100;continue;}
						if("NENNNEN".equals(s)){retval+=100;continue;}
						if("NENNNNN".equals(s)){retval+=100;continue;}
						if("NNNANNN".equals(s)){retval+=150;continue;}
						if("NNEANNN".equals(s)){retval+=100;continue;}
						if("NNEAENN".equals(s)){retval+=50;continue;}
						if("NNEAENE".equals(s)){retval+=50;continue;}
						if("NEEAENE".equals(s)){retval+=50;continue;}
						if("NNEANNE".equals(s)){retval+=50;continue;}
						if("NNEANEE".equals(s)){retval+=100;continue;}
						if("NEEANEE".equals(s)){retval+=50;continue;}
						if("NEEANNE".equals(s)){retval+=50;continue;}
						if("NNEANEN".equals(s)){retval+=50;continue;}
						if("NEEANEN".equals(s)){retval+=50;continue;}
						if("NEEANNN".equals(s)){retval+=50;continue;}
						if("NNNAENN".equals(s)){retval+=150;continue;}
						if("NNNAENE".equals(s)){retval+=550;continue;}
						if("NENAENE".equals(s)){retval+=100;continue;}
						if("NNNAEEN".equals(s)){retval+=100;continue;}
						if("NENAEEN".equals(s)){retval+=100;continue;}
						if("NENAENN".equals(s)){retval+=100;continue;}
						if("NNNANNE".equals(s)){retval+=100;continue;}
						if("NNNANEE".equals(s)){retval+=100;continue;}
						if("NENANEE".equals(s)){retval+=50;continue;}
						if("NENANNE".equals(s)){retval+=50;continue;}
						if("NNNANEN".equals(s)){retval+=100;continue;}
						if("NENANEN".equals(s)){retval+=100;continue;}
						if("NENANNN".equals(s)){retval+=100;continue;}
						if("NNAANNN".equals(s)){retval+=600;continue;}
						if("NNAAENN".equals(s)){retval+=600;continue;}
						if("NNAAENE".equals(s)){retval+=1000;continue;}
						if("NEAAENE".equals(s)){retval+=550;continue;}
						if("NNAAEEN".equals(s)){retval+=200;continue;}
						if("NEAAEEN".equals(s)){retval+=200;continue;}
						if("NEAAENN".equals(s)){retval+=200;continue;}
						if("NNAANNE".equals(s)){retval+=550;continue;}
						if("NNAANEE".equals(s)){retval+=100;continue;}
						if("NEAANEE".equals(s)){retval+=50;continue;}
						if("NEAANNE".equals(s)){retval+=100;continue;}
						if("NNAANEN".equals(s)){retval+=150;continue;}
						if("NEAANEN".equals(s)){retval+=150;continue;}
						if("NEAANNN".equals(s)){retval+=150;continue;}
						if("NNAANAN".equals(s)){retval+=900;continue;}
						if("NEAANAN".equals(s)){retval+=500;continue;}
						if("NAAANAN".equals(s)){retval+=850;continue;}
						if("NAAANNN".equals(s)){retval+=600;continue;}
						if("NAAAENN".equals(s)){retval+=600;continue;}
						if("NAAAENE".equals(s)){retval+=1000;continue;}
						if("NAAAEEN".equals(s)){retval+=200;continue;}
						if("NAAANNE".equals(s)){retval+=600;continue;}
						if("NAAANEE".equals(s)){retval+=150;continue;}
						if("NAAANEN".equals(s)){retval+=200;continue;}
						if("NNNANAN".equals(s)){retval+=500;continue;}
						if("NNEANAN".equals(s)){retval+=50;continue;}
						if("NEEANAN".equals(s)){retval+=50;continue;}
						if("NENANAN".equals(s)){retval+=100;continue;}
						if("NANANAN".equals(s)){retval+=450;continue;}
						if("NANANNN".equals(s)){retval+=150;continue;}
						if("NAEANNN".equals(s)){retval+=100;continue;}
						if("NAEAENN".equals(s)){retval+=50;continue;}
						if("NAEAENE".equals(s)){retval+=50;continue;}
						if("NAEANNE".equals(s)){retval+=100;continue;}
						if("NAEANEE".equals(s)){retval+=150;continue;}
						if("NAEANEN".equals(s)){retval+=50;continue;}
						if("NANAENN".equals(s)){retval+=150;continue;}
						if("NANAENE".equals(s)){retval+=550;continue;}
						if("NANAEEN".equals(s)){retval+=100;continue;}
						if("NANANNE".equals(s)){retval+=150;continue;}
						if("NANANEE".equals(s)){retval+=150;continue;}
						if("NANANEN".equals(s)){retval+=150;continue;}
						if("NNANNNN".equals(s)){retval+=600;continue;}
						if("NNAENNN".equals(s)){retval+=600;continue;}
						if("NNAEENN".equals(s)){retval+=600;continue;}
						if("NNAEENE".equals(s)){retval+=1000;continue;}
						if("NEAEENE".equals(s)){retval+=550;continue;}
						if("NNAEEEN".equals(s)){retval+=200;continue;}
						if("NEAEEEN".equals(s)){retval+=200;continue;}
						if("NEAEENN".equals(s)){retval+=200;continue;}
						if("NNAENNE".equals(s)){retval+=550;continue;}
						if("NNAENEE".equals(s)){retval+=100;continue;}
						if("NEAENEE".equals(s)){retval+=50;continue;}
						if("NEAENNE".equals(s)){retval+=100;continue;}
						if("NNAENEN".equals(s)){retval+=150;continue;}
						if("NEAENEN".equals(s)){retval+=150;continue;}
						if("NEAENNN".equals(s)){retval+=150;continue;}
						if("NNANENN".equals(s)){retval+=600;continue;}
						if("NNANENE".equals(s)){retval+=1000;continue;}
						if("NEANENE".equals(s)){retval+=550;continue;}
						if("NNANEEN".equals(s)){retval+=200;continue;}
						if("NEANEEN".equals(s)){retval+=200;continue;}
						if("NEANENN".equals(s)){retval+=200;continue;}
						if("NNANNNE".equals(s)){retval+=550;continue;}
						if("NNANNEE".equals(s)){retval+=100;continue;}
						if("NEANNEE".equals(s)){retval+=50;continue;}
						if("NEANNNE".equals(s)){retval+=100;continue;}
						if("NNANNEN".equals(s)){retval+=150;continue;}
						if("NEANNEN".equals(s)){retval+=150;continue;}
						if("NEANNNN".equals(s)){retval+=150;continue;}
						if("NNANNAN".equals(s)){retval+=900;continue;}
						if("NNAENAN".equals(s)){retval+=900;continue;}
						if("NEAENAN".equals(s)){retval+=500;continue;}
						if("NEANNAN".equals(s)){retval+=500;continue;}
						if("NAANNAN".equals(s)){retval+=850;continue;}
						if("NAAENAN".equals(s)){retval+=850;continue;}
						if("NAANNNN".equals(s)){retval+=600;continue;}
						if("NAAENNN".equals(s)){retval+=600;continue;}
						if("NAAEENN".equals(s)){retval+=600;continue;}
						if("NAAEENE".equals(s)){retval+=1000;continue;}
						if("NAAEEEN".equals(s)){retval+=200;continue;}
						if("NAAENNE".equals(s)){retval+=600;continue;}
						if("NAAENEE".equals(s)){retval+=150;continue;}
						if("NAAENEN".equals(s)){retval+=200;continue;}
						if("NAANENN".equals(s)){retval+=600;continue;}
						if("NAANENE".equals(s)){retval+=1000;continue;}
						if("NAANEEN".equals(s)){retval+=200;continue;}
						if("NAANNNE".equals(s)){retval+=600;continue;}
						if("NAANNEE".equals(s)){retval+=150;continue;}
						if("NAANNEN".equals(s)){retval+=200;continue;}
						if("NNNNNAN".equals(s)){retval+=500;continue;}
						if("NNNENAN".equals(s)){retval+=500;continue;}
						if("NENENAN".equals(s)){retval+=50;continue;}
						if("NNENNAN".equals(s)){retval+=50;continue;}
						if("NEENNAN".equals(s)){retval+=50;continue;}
						if("NENNNAN".equals(s)){retval+=100;continue;}
						if("NANNNAN".equals(s)){retval+=450;continue;}
						if("NANENAN".equals(s)){retval+=450;continue;}
						if("NANNNNN".equals(s)){retval+=150;continue;}
						if("NANENNN".equals(s)){retval+=150;continue;}
						if("NANEENN".equals(s)){retval+=150;continue;}
						if("NANEENE".equals(s)){retval+=550;continue;}
						if("NANEEEN".equals(s)){retval+=100;continue;}
						if("NANENNE".equals(s)){retval+=150;continue;}
						if("NANENEE".equals(s)){retval+=150;continue;}
						if("NANENEN".equals(s)){retval+=150;continue;}
						if("NAENNNN".equals(s)){retval+=100;continue;}
						if("NAENENN".equals(s)){retval+=50;continue;}
						if("NAENENE".equals(s)){retval+=50;continue;}
						if("NAENNNE".equals(s)){retval+=100;continue;}
						if("NAENNEE".equals(s)){retval+=150;continue;}
						if("NAENNEN".equals(s)){retval+=50;continue;}
						if("NANNENN".equals(s)){retval+=150;continue;}
						if("NANNENE".equals(s)){retval+=550;continue;}
						if("NANNEEN".equals(s)){retval+=100;continue;}
						if("NANNNNE".equals(s)){retval+=150;continue;}
						if("NANNNEE".equals(s)){retval+=150;continue;}
						if("NANNNEN".equals(s)){retval+=150;continue;}
					}
					
					
					


				}

			}
			
		}
		
		
		pattern = Pattern.sToPv2("xrxrdxldxzldxdxdS", e.kscolour);
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
					Tuple S1 = tlist.get(0).side(side);
					Tuple S0 = S1.side(side);
					Tuple S2 = S1.side(r);
					
					Tuple TL = tlist.get(0).side(l);
					Tuple TR = tlist.get(0).side2(r,r);
					Tuple RB = S2.side2(side,r);

					
					if(e.isEnemies(TR,RB) || e.isThere(S0) || e.isThere(S2) || e.isTheres(S1,RB))continue;


					
					String s = States.arrayToString(e,TL,TR,RB,S0,S1,S2);
					if (e.isThere(TL)) {
						if("ANNNNN".equals(s)){retval+=600;continue;}
						if("ANNNEN".equals(s)){retval+=200;continue;}
						if("ANNEEN".equals(s)){retval+=200;continue;}
						if("AENEEN".equals(s)){retval+=200;continue;}
						if("ANEEEN".equals(s)){retval+=150;continue;}
						if("ANNNEE".equals(s)){retval+=200;continue;}
						if("AENNEE".equals(s)){retval+=100;continue;}
						if("ANENEE".equals(s)){retval+=100;continue;}
						if("AENNEN".equals(s)){retval+=200;continue;}
						if("ANENEN".equals(s)){retval+=200;continue;}
						if("ANNENN".equals(s)){retval+=600;continue;}
						if("ANNENE".equals(s)){retval+=500;continue;}
						if("AENENE".equals(s)){retval+=500;continue;}
						if("AENENN".equals(s)){retval+=200;continue;}
						if("ANEENN".equals(s)){retval+=100;continue;}
						if("ANNNNE".equals(s)){retval+=550;continue;}
						if("AENNNE".equals(s)){retval+=150;continue;}
						if("ANENNE".equals(s)){retval+=50;continue;}
						if("AENNNN".equals(s)){retval+=200;continue;}
						if("ANENNN".equals(s)){retval+=150;continue;}
						if("ANNNAN".equals(s)){retval+=900;continue;}
						if("AENNAN".equals(s)){retval+=500;continue;}
						if("ANENAN".equals(s)){retval+=500;continue;}
						if("AANNAN".equals(s)){retval+=850;continue;}
						if("AAENAN".equals(s)){retval+=900;continue;}
						if("AANNNN".equals(s)){retval+=600;continue;}
						if("AANNEN".equals(s)){retval+=200;continue;}
						if("AANEEN".equals(s)){retval+=200;continue;}
						if("AAEEEN".equals(s)){retval+=200;continue;}
						if("AANNEE".equals(s)){retval+=200;continue;}
						if("AAENEE".equals(s)){retval+=200;continue;}
						if("AAENEN".equals(s)){retval+=200;continue;}
						if("AANENN".equals(s)){retval+=600;continue;}
						if("AANENE".equals(s)){retval+=500;continue;}
						if("AAEENN".equals(s)){retval+=500;continue;}
						if("AANNNE".equals(s)){retval+=600;continue;}
						if("AAENNE".equals(s)){retval+=500;continue;}
						if("AAENNN".equals(s)){retval+=600;continue;}
						if("AAANNN".equals(s)){retval+=600;continue;}
						if("AAANEN".equals(s)){retval+=200;continue;}
						if("AAAEEN".equals(s)){retval+=200;continue;}
						if("AAANEE".equals(s)){retval+=200;continue;}
						if("AAAENN".equals(s)){retval+=600;continue;}
						if("AAAENE".equals(s)){retval+=1000;continue;}
						if("AAANNE".equals(s)){retval+=600;continue;}
						if("ANANNN".equals(s)){retval+=600;continue;}
						if("ANANEN".equals(s)){retval+=200;continue;}
						if("ANAEEN".equals(s)){retval+=200;continue;}
						if("AEAEEN".equals(s)){retval+=200;continue;}
						if("ANANEE".equals(s)){retval+=200;continue;}
						if("AEANEE".equals(s)){retval+=200;continue;}
						if("AEANEN".equals(s)){retval+=200;continue;}
						if("ANAENN".equals(s)){retval+=600;continue;}
						if("ANAENE".equals(s)){retval+=1000;continue;}
						if("AEAENE".equals(s)){retval+=1000;continue;}
						if("AEAENN".equals(s)){retval+=600;continue;}
						if("ANANNE".equals(s)){retval+=600;continue;}
						if("AEANNE".equals(s)){retval+=600;continue;}
						if("AEANNN".equals(s)){retval+=600;continue;}
					}else if(e.isEnemy(TL)){
						if("ENNNNN".equals(s)){retval+=550;continue;}
						if("ENNNEN".equals(s)){retval+=100;continue;}
						if("ENNEEN".equals(s)){retval+=100;continue;}
						if("EENEEN".equals(s)){retval+=100;continue;}
						if("ENEEEN".equals(s)){retval+=100;continue;}
						if("ENNNEE".equals(s)){retval+=100;continue;}
						if("EENNEE".equals(s)){retval+=50;continue;}
						if("ENENEE".equals(s)){retval+=50;continue;}
						if("EENNEN".equals(s)){retval+=100;continue;}
						if("ENENEN".equals(s)){retval+=100;continue;}
						if("ENNENN".equals(s)){retval+=550;continue;}
						if("ENNENE".equals(s)){retval+=500;continue;}
						if("EENENE".equals(s)){retval+=500;continue;}
						if("EENENN".equals(s)){retval+=100;continue;}
						if("ENEENN".equals(s)){retval+=50;continue;}
						if("ENNNNE".equals(s)){retval+=550;continue;}
						if("EENNNE".equals(s)){retval+=100;continue;}
						if("ENENNE".equals(s)){retval+=50;continue;}
						if("EENNNN".equals(s)){retval+=100;continue;}
						if("ENENNN".equals(s)){retval+=100;continue;}
						if("ENNNAN".equals(s)){retval+=900;continue;}
						if("EENNAN".equals(s)){retval+=500;continue;}
						if("ENENAN".equals(s)){retval+=500;continue;}
						if("EANNAN".equals(s)){retval+=850;continue;}
						if("EAENAN".equals(s)){retval+=900;continue;}
						if("EANNNN".equals(s)){retval+=550;continue;}
						if("EANNEN".equals(s)){retval+=100;continue;}
						if("EANEEN".equals(s)){retval+=100;continue;}
						if("EAEEEN".equals(s)){retval+=100;continue;}
						if("EANNEE".equals(s)){retval+=150;continue;}
						if("EAENEE".equals(s)){retval+=100;continue;}
						if("EAENEN".equals(s)){retval+=100;continue;}
						if("EANENN".equals(s)){retval+=550;continue;}
						if("EANENE".equals(s)){retval+=500;continue;}
						if("EAEENN".equals(s)){retval+=500;continue;}
						if("EANNNE".equals(s)){retval+=600;continue;}
						if("EAENNE".equals(s)){retval+=500;continue;}
						if("EAENNN".equals(s)){retval+=550;continue;}
						if("EAANNN".equals(s)){retval+=600;continue;}
						if("EAANEN".equals(s)){retval+=150;continue;}
						if("EAAEEN".equals(s)){retval+=100;continue;}
						if("EAANEE".equals(s)){retval+=200;continue;}
						if("EAAENN".equals(s)){retval+=550;continue;}
						if("EAAENE".equals(s)){retval+=1000;continue;}
						if("EAANNE".equals(s)){retval+=600;continue;}
						if("ENANNN".equals(s)){retval+=550;continue;}
						if("ENANEN".equals(s)){retval+=100;continue;}
						if("ENAEEN".equals(s)){retval+=100;continue;}
						if("EEAEEN".equals(s)){retval+=100;continue;}
						if("ENANEE".equals(s)){retval+=150;continue;}
						if("EEANEE".equals(s)){retval+=100;continue;}
						if("EEANEN".equals(s)){retval+=100;continue;}
						if("ENAENN".equals(s)){retval+=550;continue;}
						if("ENAENE".equals(s)){retval+=1000;continue;}
						if("EEAENE".equals(s)){retval+=1000;continue;}
						if("EEAENN".equals(s)){retval+=550;continue;}
						if("ENANNE".equals(s)){retval+=600;continue;}
						if("EEANNE".equals(s)){retval+=550;continue;}
						if("EEANNN".equals(s)){retval+=550;continue;}
					}else {
						if("NNNNNN".equals(s)){retval+=600;continue;}
						if("NNNNEN".equals(s)){retval+=150;continue;}
						if("NNNEEN".equals(s)){retval+=150;continue;}
						if("NENEEN".equals(s)){retval+=150;continue;}
						if("NNEEEN".equals(s)){retval+=100;continue;}
						if("NNNNEE".equals(s)){retval+=150;continue;}
						if("NENNEE".equals(s)){retval+=100;continue;}
						if("NNENEE".equals(s)){retval+=100;continue;}
						if("NENNEN".equals(s)){retval+=150;continue;}
						if("NNENEN".equals(s)){retval+=150;continue;}
						if("NNNENN".equals(s)){retval+=550;continue;}
						if("NNNENE".equals(s)){retval+=500;continue;}
						if("NENENE".equals(s)){retval+=500;continue;}
						if("NENENN".equals(s)){retval+=150;continue;}
						if("NNEENN".equals(s)){retval+=50;continue;}
						if("NNNNNE".equals(s)){retval+=550;continue;}
						if("NENNNE".equals(s)){retval+=150;continue;}
						if("NNENNE".equals(s)){retval+=50;continue;}
						if("NENNNN".equals(s)){retval+=150;continue;}
						if("NNENNN".equals(s)){retval+=150;continue;}
						if("NNNNAN".equals(s)){retval+=900;continue;}
						if("NENNAN".equals(s)){retval+=500;continue;}
						if("NNENAN".equals(s)){retval+=500;continue;}
						if("NANNAN".equals(s)){retval+=850;continue;}
						if("NAENAN".equals(s)){retval+=900;continue;}
						if("NANNNN".equals(s)){retval+=600;continue;}
						if("NANNEN".equals(s)){retval+=150;continue;}
						if("NANEEN".equals(s)){retval+=150;continue;}
						if("NAEEEN".equals(s)){retval+=150;continue;}
						if("NANNEE".equals(s)){retval+=200;continue;}
						if("NAENEE".equals(s)){retval+=150;continue;}
						if("NAENEN".equals(s)){retval+=150;continue;}
						if("NANENN".equals(s)){retval+=600;continue;}
						if("NANENE".equals(s)){retval+=500;continue;}
						if("NAEENN".equals(s)){retval+=500;continue;}
						if("NANNNE".equals(s)){retval+=600;continue;}
						if("NAENNE".equals(s)){retval+=500;continue;}
						if("NAENNN".equals(s)){retval+=600;continue;}
						if("NAANNN".equals(s)){retval+=600;continue;}
						if("NAANEN".equals(s)){retval+=200;continue;}
						if("NAAEEN".equals(s)){retval+=150;continue;}
						if("NAANEE".equals(s)){retval+=200;continue;}
						if("NAAENN".equals(s)){retval+=600;continue;}
						if("NAAENE".equals(s)){retval+=1000;continue;}
						if("NAANNE".equals(s)){retval+=600;continue;}
						if("NNANNN".equals(s)){retval+=600;continue;}
						if("NNANEN".equals(s)){retval+=150;continue;}
						if("NNAEEN".equals(s)){retval+=150;continue;}
						if("NEAEEN".equals(s)){retval+=150;continue;}
						if("NNANEE".equals(s)){retval+=200;continue;}
						if("NEANEE".equals(s)){retval+=150;continue;}
						if("NEANEN".equals(s)){retval+=150;continue;}
						if("NNAENN".equals(s)){retval+=600;continue;}
						if("NNAENE".equals(s)){retval+=1000;continue;}
						if("NEAENE".equals(s)){retval+=1000;continue;}
						if("NEAENN".equals(s)){retval+=600;continue;}
						if("NNANNE".equals(s)){retval+=600;continue;}
						if("NEANNE".equals(s)){retval+=600;continue;}
						if("NEANNN".equals(s)){retval+=600;continue;}
					}


				}

			}
			
		}


		
		return retval;

	}

	
	
}
