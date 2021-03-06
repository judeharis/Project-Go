package GoLD.Heuristics;

import java.util.ArrayList;

import GoLD.Evaluator;
import GoLD.Pattern;
import GoLD.PatternSearcher;
import GoLD.Tuple;
import GoLD.UDLR;

public class OnePJump {
	static ArrayList<Pattern> twoStone = Pattern.sToPv2("xrx");
	static ArrayList<Pattern> oneJumpStone = Pattern.sToPv2("xrrx");

	
	
	static public int evaluate(ArrayList<Tuple> sstring , Evaluator e) {
		
		
		int retval = 0;
		PatternSearcher ps = new PatternSearcher(e.cB,e.kscolour);
		
		

		ArrayList<ArrayList<Tuple>> pMatches =ps.allStringMatchv2(sstring, twoStone,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			for(ArrayList<Tuple> tlist: pMatches) {
				if (!tlist.isEmpty()) {
					retval+=5;	
					if(tlist.size()>1) {
						Tuple A = tlist.get(0);
						Tuple B = tlist.get(1);
						e.addToCheckedPoints(A,B);
					}
				}
			}

		}
		


		pMatches =ps.allStringMatchv2(sstring, oneJumpStone,e.kscolour);
		
		if(!pMatches.isEmpty()) {
			int counter=0;
			for(ArrayList<Tuple> tlist: pMatches) {
				if (!tlist.isEmpty()) {
					boolean diagSide= ps.dirSideToBool(counter);
					UDLR d = ps.dirNumToDir(counter);
					UDLR r = d.diag(diagSide);
					counter++;
					Tuple R = tlist.get(0).side(r);
					retval+=10;	
					if(e.isEnemy(R))retval-=10;
					e.addToCheckedPoints(R);
				}
			}

		}
		return retval;
		

	}
	

}
