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
		
		SixDiesEightLives sixDiesEightLives= new SixDiesEightLives(e);
		retval += sixDiesEightLives.evaluate(sstring);
//		SixDiesEightLives2 sixDiesEightLives2= new SixDiesEightLives2(e);
//		retval += sixDiesEightLives2.evaluate(sstring);
		
		
		SideOneGap sideOneGap= new SideOneGap(e);
		retval += sideOneGap.evaluate(sstring);
		
		SideTwoGap sideTwoGap= new SideTwoGap(e);
		retval += sideTwoGap.evaluate(sstring);
		
		SideThreeGap sideThreeGap= new SideThreeGap(e);
		retval += sideThreeGap.evaluate(sstring);
		
		SideFiveGap sideFiveGap= new SideFiveGap(e);
		retval += sideFiveGap.evaluate(sstring);
		
		
		return retval;
		
	}

	

}
