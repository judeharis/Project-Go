package Go.SlickGo;
import java.util.ArrayList;

import Go.SlickGo.Heuristics.*;

public class HeuristicsRunner {
	
	Board cB;
	Evaluator e;
	Stone kscolour = Minimaxer.keystonecolour.getSC();
	Stone enemycolour = kscolour.getEC();
	ArrayList<Tuple> sstring;
	
	public HeuristicsRunner(Board cB,Evaluator e,ArrayList<Tuple> sstring) {
		this.cB= cB;
		this.e= e;
		this.sstring = sstring;
	}

	public int runHeuristics(){
		int retval =0;
		
		
		SixDie sixDie= new SixDie(e);
		retval += sixDie.evaluate(sstring);
		
		
		SevenUnsettled sevenUnsettled= new SevenUnsettled(e);
		retval += sevenUnsettled.evaluate(sstring);
		
		LineTwoSideEscape lineTwoSideEscape= new LineTwoSideEscape(e);
		retval += lineTwoSideEscape.evaluate(sstring);
		
		
//		SixDiesEightLives sixDiesEightLives= new SixDiesEightLives(e);
//		retval += sixDiesEightLives.evaluate(sstring);
		
		
		
		SideOneGap sideOneGap= new SideOneGap(e);
		retval += sideOneGap.evaluate(sstring);
		
		SideTwoGap sideTwoGap= new SideTwoGap(e);
		retval += sideTwoGap.evaluate(sstring);
		
		SideThreeGap sideThreeGap= new SideThreeGap(e);
		retval += sideThreeGap.evaluate(sstring);
		
		SideFourGap sideFourGap= new SideFourGap(e);
		retval += sideFourGap.evaluate(sstring);
		
		SideFiveGap sideFiveGap= new SideFiveGap(e);
		retval += sideFiveGap.evaluate(sstring);
		
		
		return retval;
		
	}

	

}
