package GoLD;


import java.util.ArrayList;

import GoLD.Heuristics.*;
import GoLD.PatternHeuristics.*;


public class HeuristicsRunner {
	
	Board cB;
	Evaluator e;
	Stone kscolour = MoveFinder.keystonecolour.getSC();
	Stone enemycolour = kscolour.getEC();


	public HeuristicsRunner(Board cB,Evaluator e) {
		this.cB= cB;
		this.e= e;
	}

	public int runKeyStringHeuristics(Group group){
		int retval =0;



		

		retval += Hane.evaluate(group.group,e);
		retval += Cut.evaluate(group.group,e);
		retval += OnePJump.evaluate(group.group,e);
		
		retval += Eye.evaluate(group.group,e);
		retval += EyeSide.evaluate(group.group,e);
		retval += EyeCorner.evaluate(group.group,e);	
	
		retval += StraightTwo.evaluate(group.group,e);
		retval += StraightTwoSide.evaluate(group.group,e);
		retval += StraightTwoCorner.evaluate(group.group,e);
		
		retval += StraightThree.evaluate(group.group,e);	
		retval += StraightThreeSide.evaluate(group.group,e);
		retval += StraightThreeCorner.evaluate(group.group,e);
		retval += BentThree.evaluate(group.group,e);
		retval += BentThreeSide.evaluate(group.group,e);
		retval += BentThreeCorner.evaluate(group.group,e);
		
		
		retval += StraightFour.evaluate(group.group,e);
		retval += StraightFourSide.evaluate(group.group,e);
		retval += StraightFourCorner.evaluate(group.group,e);
		retval += SquareFour.evaluate(group.group,e);
		retval += SquareFourSide.evaluate(group.group,e);
		retval += SquareFourCorner.evaluate(group.group,e);
		retval += PyramidFour.evaluate(group.group,e);
		retval += PyramidFourSide.evaluate(group.group,e);
		retval += PyramidFourCorner.evaluate(group.group,e);
		
		retval += BentFour.evaluate(group.group,e);
		retval += BentFourSide.evaluate(group.group,e);
		retval += BentFourCorner.evaluate(group.group,e);
		retval += TwistedFour.evaluate(group.group,e);
		retval += TwistedFourSide.evaluate(group.group,e);
		retval += TwistedFourCorner.evaluate(group.group,e);
	
		
		
		retval += StraightFive.evaluate(group.group,e);
		retval += StraightFiveSide.evaluate(group.group,e);
		retval += StraightFiveCorner.evaluate(group.group,e);
		retval += CrossedFive.evaluate(group.group,e);
		retval += CrossedFiveSide.evaluate(group.group,e);
		retval += CrossedFiveCorner.evaluate(group.group,e);
		
		
		retval += BulkyFive.evaluate(group.group,e);
		retval += BulkyFiveSide.evaluate(group.group,e);
		retval += BulkyFiveCorner.evaluate(group.group,e);
		
		
		retval += FFive.evaluate(group.group,e);
		retval += FFiveSide.evaluate(group.group,e);
		retval += FFiveCorner.evaluate(group.group,e);	
		
		retval += StraightSix.evaluate(group.group,e);
		retval += StraightSixSide.evaluate(group.group,e);
		retval += StraightSixCorner.evaluate(group.group,e);	
		retval += RabbitSix.evaluate(group.group,e);
		retval += RabbitSixSide.evaluate(group.group,e);
		retval += RabbitSixCorner.evaluate(group.group,e);
		
		
	
		
		retval += Liberties.evaluate(group.group,e);
		
		
	

		
		return retval * 1;
		
	}
	
	public void findUseFullMoves(ArrayList<Tuple> group){




		
		Hane.evaluate(group,e);
		Cut.evaluate(group,e);
		OnePJump.evaluate(group,e);
		
		
		Eye.evaluate(group,e);
		EyeSide.evaluate(group,e);
		EyeCorner.evaluate(group,e);	
		
		StraightTwo.evaluate(group,e);
		StraightTwoSide.evaluate(group,e);
		StraightTwoCorner.evaluate(group,e);
		
		StraightThree.evaluate(group,e);	
		StraightThreeSide.evaluate(group,e);
		StraightThreeCorner.evaluate(group,e);
		BentThree.evaluate(group,e);
		BentThreeSide.evaluate(group,e);
		BentThreeCorner.evaluate(group,e);
		
		
		StraightFour.evaluate(group,e);
		StraightFourSide.evaluate(group,e);
		StraightFourCorner.evaluate(group,e);
		SquareFour.evaluate(group,e);
		SquareFourSide.evaluate(group,e);
		SquareFourCorner.evaluate(group,e);
		PyramidFour.evaluate(group,e);
		PyramidFourSide.evaluate(group,e);
		PyramidFourCorner.evaluate(group,e);
		BentFour.evaluate(group,e);
		BentFourSide.evaluate(group,e);
		BentFourCorner.evaluate(group,e);
		
		
		
		TwistedFour.evaluate(group,e);
		TwistedFourSide.evaluate(group,e);
		TwistedFourCorner.evaluate(group,e);

		StraightFive.evaluate(group,e);
		StraightFiveSide.evaluate(group,e);
		StraightFiveCorner.evaluate(group,e);
		
		CrossedFive.evaluate(group,e);
		CrossedFiveSide.evaluate(group,e);
		CrossedFiveCorner.evaluate(group,e);
		
		
		BulkyFive.evaluate(group,e);
		BulkyFiveSide.evaluate(group,e);
		BulkyFiveCorner.evaluate(group,e);
		
		
		FFive.evaluate(group,e);
		FFiveSide.evaluate(group,e);
		FFiveCorner.evaluate(group,e);	
		
		
		
		
		
		StraightSix.evaluate(group,e);
		StraightSixSide.evaluate(group,e);
		StraightSixCorner.evaluate(group,e);	
		
		
		RabbitSix.evaluate(group,e);
		RabbitSixSide.evaluate(group,e);
		RabbitSixCorner.evaluate(group,e);
		
		
		 
		Liberties.evaluate(group,e);


		
	}
	

	

	
    public static void print(Object o){
        System.out.println(o);
    }
	

}
