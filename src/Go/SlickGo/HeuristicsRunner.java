package Go.SlickGo;
import java.util.ArrayList;

import Go.SlickGo.Heuristics.*;

public class HeuristicsRunner {
	
	Board cB;
	Evaluator e;
	Stone kscolour = MoveFinder.keystonecolour.getSC();
	Stone enemycolour = kscolour.getEC();

	public HeuristicsRunner(Board cB,Evaluator e) {
		this.cB= cB;
		this.e= e;
	}

	public int runKeyStringHeuristics(ArrayList<Tuple> sstring){
		int retval =0;
		
		
		SixDie sixDie= new SixDie(e);
		retval += sixDie.evaluate(sstring);
		
		SevenUnsettled sevenUnsettled= new SevenUnsettled(e);
		retval += sevenUnsettled.evaluate(sstring);
		
		EightLive eightLive= new EightLive(e);
		retval += eightLive.evaluate(sstring);
		
		LineTwoSideEscape lineTwoSideEscape= new LineTwoSideEscape(e);
		retval += lineTwoSideEscape.evaluate(sstring);
		
		
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
		
		SideSixGap sideSixGap= new SideSixGap(e);
		retval += sideSixGap.evaluate(sstring);
		

		BentThree bentThree= new BentThree(e);
		retval += bentThree.evaluate(sstring);
		
		
		ThreeInRowCorner threeInRowCorner= new ThreeInRowCorner(e);
		retval += threeInRowCorner.evaluate(sstring);
		
		ThreeInRow threeInRow= new ThreeInRow(e);
		retval += threeInRow.evaluate(sstring);
		
		
		SquareFour squareFour= new SquareFour(e);
		retval += squareFour.evaluate(sstring);
		
			
		Liberties liberties= new Liberties(e);
		retval += liberties.evaluate(sstring)*0;
		
		
		return retval * 1;
		
	}
	
	public int runStoneHeuristics(Tuple t){

		int retval =0;
		
		ConnectStones connectStones= new ConnectStones(e);
		retval += connectStones.evaluate(t);
		
		return retval *0;
		
	}

	

}
