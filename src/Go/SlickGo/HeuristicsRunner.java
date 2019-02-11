package Go.SlickGo;


import Go.SlickGo.Heuristics.*;
import PatternHeuristics.*;


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
//		retval += Liberties.evaluate(group.group,e);
		
		
	

		
		return retval * 1;
		
	}
	
	public int runStoneHeuristics(Tuple t){

		int retval =0;
		

		
		return retval *0;
		
	}

	

	
    public static void print(Object o){
        System.out.println(o);
    }
	

}
