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

	public int runKeyStringHeuristics(Group keygroup,ArrayList<Tuple> keystring){
		int retval =0;
		

		SixDie sixDie= new SixDie(e);
		retval += sixDie.evaluate(keygroup.group);
		
		SevenUnsettled sevenUnsettled= new SevenUnsettled(e);
		retval += sevenUnsettled.evaluate(keygroup.group);
		
		EightLive eightLive= new EightLive(e);
		retval += eightLive.evaluate(keygroup.group);
		
		LineTwoSideEscape lineTwoSideEscape= new LineTwoSideEscape(e);
		retval += lineTwoSideEscape.evaluate(keygroup.group);
		
		
		SideOneGap sideOneGap= new SideOneGap(e);
		retval += sideOneGap.evaluate(keygroup.group); 
		
		SideTwoGap sideTwoGap= new SideTwoGap(e);
		retval += sideTwoGap.evaluate(keygroup.group);
		
		SideThreeGap sideThreeGap= new SideThreeGap(e);
		retval += sideThreeGap.evaluate(keygroup.group);
		
		SideFourGap sideFourGap= new SideFourGap(e);
		retval += sideFourGap.evaluate(keygroup.group);
		
		SideFiveGap sideFiveGap= new SideFiveGap(e);
		retval += sideFiveGap.evaluate(keygroup.group);
		
		SideSixGap sideSixGap= new SideSixGap(e);
		retval += sideSixGap.evaluate(keygroup.group);
		

		BentThree bentThree= new BentThree(e);
		retval += bentThree.evaluate(keygroup.group);
		
		

		
		ThreeInRowCorner threeInRowCorner= new ThreeInRowCorner(e);
		retval += threeInRowCorner.evaluate(keygroup.group);
		
		ThreeInRow threeInRow= new ThreeInRow(e);
		retval += threeInRow.evaluate(keygroup.group);
		
		
		SquareFour squareFour= new SquareFour(e);
		retval += squareFour.evaluate(keygroup.group);
		
		SquareFourCorner squareFourCorner= new SquareFourCorner(e);
		retval += squareFourCorner.evaluate(keygroup.group);
		
		SquareFourSide squareFourSide= new SquareFourSide(e);
		retval += squareFourSide.evaluate(keygroup.group);
		
	
		Liberties liberties= new Liberties(e);
		retval += liberties.evaluate(keystring)*1;
		
		
		return retval * 1;
		
	}
	
	public int runStoneHeuristics(Tuple t){

		int retval =0;
		
		ConnectStones connectStones= new ConnectStones(e);
		retval += connectStones.evaluate(t);
		
		return retval *0;
		
	}

	

}
