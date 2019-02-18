package GoLD;


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
		
		
		
//		retval += SixDie.evaluate(group.group,e);
//		retval += SevenUnsettled.evaluate(group.group,e);
//		retval += EightLive.evaluate(group.group,e);
		
		retval += Liberties.evaluate(group.group,e);
		
		
	

		
		return retval * 1;
		
	}
	
	public void findUseFullMoves(Group group){




		

		 Hane.evaluate(group.group,e);
		 Cut.evaluate(group.group,e);
		 OnePJump.evaluate(group.group,e);
		
//		 Eye.evaluate(group.group,e);
//		 EyeSide.evaluate(group.group,e);
//		 EyeCorner.evaluate(group.group,e);	
//	
//		 StraightTwo.evaluate(group.group,e);
//		 StraightTwoSide.evaluate(group.group,e);
//		 StraightTwoCorner.evaluate(group.group,e);
//		
//		 StraightThree.evaluate(group.group,e);	
//		 StraightThreeSide.evaluate(group.group,e);
//		 StraightThreeCorner.evaluate(group.group,e);
//		 BentThree.evaluate(group.group,e);
//		 BentThreeSide.evaluate(group.group,e);
//		 BentThreeCorner.evaluate(group.group,e);
//		
//		
//		 StraightFour.evaluate(group.group,e);
//		 StraightFourSide.evaluate(group.group,e);
//		 StraightFourCorner.evaluate(group.group,e);
//		 SquareFour.evaluate(group.group,e);
//		 SquareFourSide.evaluate(group.group,e);
//		 SquareFourCorner.evaluate(group.group,e);
//		 PyramidFour.evaluate(group.group,e);
//		 PyramidFourSide.evaluate(group.group,e);
//		 PyramidFourCorner.evaluate(group.group,e);
//		
//		 BentFour.evaluate(group.group,e);
//		 BentFourSide.evaluate(group.group,e);
//		 BentFourCorner.evaluate(group.group,e);
//		 TwistedFour.evaluate(group.group,e);
//		 TwistedFourSide.evaluate(group.group,e);
//		 TwistedFourCorner.evaluate(group.group,e);
//	
//		
//		
//		 StraightFive.evaluate(group.group,e);
//		 StraightFiveSide.evaluate(group.group,e);
//		 StraightFiveCorner.evaluate(group.group,e);
//		 CrossedFive.evaluate(group.group,e);
//		 CrossedFiveSide.evaluate(group.group,e);
//		 CrossedFiveCorner.evaluate(group.group,e);
//		
//		
//		 BulkyFive.evaluate(group.group,e);
//		 BulkyFiveSide.evaluate(group.group,e);
//		 BulkyFiveCorner.evaluate(group.group,e);
//		
//		
//		 FFive.evaluate(group.group,e);
//		 FFiveSide.evaluate(group.group,e);
//		 FFiveCorner.evaluate(group.group,e);	
//		
//		 StraightSix.evaluate(group.group,e);
//		 StraightSixSide.evaluate(group.group,e);
//		 StraightSixCorner.evaluate(group.group,e);	
//		 RabbitSix.evaluate(group.group,e);
//		 RabbitSixSide.evaluate(group.group,e);
//		 RabbitSixCorner.evaluate(group.group,e);
		
		 
		 Liberties.evaluate(group.group,e);
		

		
	}
	

	

	
    public static void print(Object o){
        System.out.println(o);
    }
	

}
