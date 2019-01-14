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
		
		
		StraightOneSide straightOneSide= new StraightOneSide(e);
		retval += straightOneSide.evaluate(keygroup.group); 
		
		StraightTwoSide straightTwoSide= new StraightTwoSide(e);
		retval += straightTwoSide.evaluate(keygroup.group);
		
		StraightThreeSide straightThreeSide= new StraightThreeSide(e);
		retval += straightThreeSide.evaluate(keygroup.group);
		
		StraightFourSide straightFourSide= new StraightFourSide(e);
		retval += straightFourSide.evaluate(keygroup.group);
		
		StraightFiveSide straightFiveSide= new StraightFiveSide(e);
		retval += straightFiveSide.evaluate(keygroup.group);
		
		StraightSixSide straightSixSide= new StraightSixSide(e);
		retval += straightSixSide.evaluate(keygroup.group);
		

		BentThree bentThree= new BentThree(e);
		retval += bentThree.evaluate(keygroup.group);

		
		StraightThreeCorner straightThreeCorner= new StraightThreeCorner(e);
		retval += straightThreeCorner.evaluate(keygroup.group);
		
		StraightThree straightThree= new StraightThree(e);
		retval += straightThree.evaluate(keygroup.group);
		
		
		SquareFour squareFour= new SquareFour(e);
		retval += squareFour.evaluate(keygroup.group);
		
		SquareFourCorner squareFourCorner= new SquareFourCorner(e);
		retval += squareFourCorner.evaluate(keygroup.group);
		
		SquareFourSide squareFourSide= new SquareFourSide(e);
		retval += squareFourSide.evaluate(keygroup.group);
		
		PyramidFour pyramidFour= new PyramidFour(e);
		retval += pyramidFour.evaluate(keygroup.group);
		
		PyramidFourSide pyramidFourSide= new PyramidFourSide(e);
		retval += pyramidFourSide.evaluate(keygroup.group);
		
		
		SingleEye singleEye= new SingleEye(e);
		retval += singleEye.evaluate(keygroup.group)*1;
		
		Liberties liberties= new Liberties(e);
		retval += liberties.evaluate(keystring)*0.5;
		
		
		return retval * 1;
		
	}
	
	public int runStoneHeuristics(Tuple t){

		int retval =0;
		

		
		return retval *0;
		
	}

	

}
