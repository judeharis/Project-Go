package Go.SlickGo;
import java.util.ArrayList;


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

	public int runKeyStringHeuristics(Group keygroup,ArrayList<Tuple> keystring){
		int retval =0;
		

//		SixDie sixDie= new SixDie(e);
//		retval += sixDie.evaluate(keygroup.group);
//		
//		SevenUnsettled sevenUnsettled= new SevenUnsettled(e);
//		retval += sevenUnsettled.evaluate(keygroup.group);
//		
//		EightLive eightLive= new EightLive(e);
//		retval += eightLive.evaluate(keygroup.group);
//		
//		LineTwoSideEscape lineTwoSideEscape= new LineTwoSideEscape(e);
//		retval += lineTwoSideEscape.evaluate(keygroup.group);
//		
//		
//		StraightOneSide straightOneSide= new StraightOneSide(e);
//		retval += straightOneSide.evaluate(keygroup.group); 
//		
//		StraightTwoSide straightTwoSide= new StraightTwoSide(e);
//		retval += straightTwoSide.evaluate(keygroup.group);
//		
//		StraightThreeSide straightThreeSide= new StraightThreeSide(e);
//		retval += straightThreeSide.evaluate(keygroup.group);
//		
//		StraightFourSide straightFourSide= new StraightFourSide(e);
//		retval += straightFourSide.evaluate(keygroup.group);
//		
//		StraightFiveSide straightFiveSide= new StraightFiveSide(e);
//		retval += straightFiveSide.evaluate(keygroup.group);
//		
//		StraightSixSide straightSixSide= new StraightSixSide(e);
//		retval += straightSixSide.evaluate(keygroup.group);
//		
//
//		BentThree bentThree= new BentThree(e);
//		retval += bentThree.evaluate(keygroup.group);
//		
//		
//		BentThreeSide bentThreeSide= new BentThreeSide(e);
//		retval += bentThreeSide.evaluate(keygroup.group);
//		
//		BentThreeCorner bentThreeCorner= new BentThreeCorner(e);
//		retval += bentThreeCorner.evaluate(keygroup.group);
//
//
//		
//		StraightThreeCorner straightThreeCorner= new StraightThreeCorner(e);
//		retval += straightThreeCorner.evaluate(keygroup.group);
//		
//		StraightThree straightThree= new StraightThree(e);
//		retval += straightThree.evaluate(keygroup.group);
//		
//		
//		SquareFour squareFour= new SquareFour(e);
//		retval += squareFour.evaluate(keygroup.group);
//		
//		SquareFourCorner squareFourCorner= new SquareFourCorner(e);
//		retval += squareFourCorner.evaluate(keygroup.group);
//		
//		SquareFourSide squareFourSide= new SquareFourSide(e);
//		retval += squareFourSide.evaluate(keygroup.group);
//		
//		PyramidFour pyramidFour= new PyramidFour(e);
//		retval += pyramidFour.evaluate(keygroup.group);
//		
//		PyramidFourSide pyramidFourSide= new PyramidFourSide(e);
//		retval += pyramidFourSide.evaluate(keygroup.group);
//		
//		PyramidFourCorner pyramidFourCorner= new PyramidFourCorner(e);
//		retval += pyramidFourCorner.evaluate(keygroup.group);
//		
//		
//		CrossedFive crossedFive= new CrossedFive(e);
//		retval += crossedFive.evaluate(keygroup.group);
//		
//		BulkyFive bulkyFive= new BulkyFive(e);
//		retval += bulkyFive.evaluate(keygroup.group);
//		
//		
//		SingleEye singleEye= new SingleEye(e);
//		retval += singleEye.evaluate(keygroup.group)*1;
//		
//		Liberties liberties= new Liberties(e);
//		retval += liberties.evaluate(keystring)*0.5;
		
		

		retval=0;
		

		

		
		Eye eye= new Eye(e);
		retval += eye.evaluate(keygroup.group)*1;
		

		EyeSide eyeSide= new EyeSide(e);
		retval += eyeSide.evaluate(keygroup.group)*1;
		

		
		EyeCorner eyeCorner= new EyeCorner(e);
		retval += eyeCorner.evaluate(keygroup.group)*1;
		

		
		StraightTwo straightTwo= new StraightTwo(e);
		retval += straightTwo.evaluate(keygroup.group)*1;
		

		StraightTwoSidev2 StraightTwoSidev2= new StraightTwoSidev2(e);
		retval += StraightTwoSidev2.evaluate(keygroup.group)*1;
		
		StraightTwoCorner straightTwoCorner= new StraightTwoCorner(e);
		retval += straightTwoCorner.evaluate(keygroup.group)*1;
	

		StraightThreev2 straightThreev2= new StraightThreev2(e);
		retval += straightThreev2.evaluate(keygroup.group);
		
		

		StraightThreeSidev2 straightThreeSidev2= new StraightThreeSidev2(e);
		retval += straightThreeSidev2.evaluate(keygroup.group);
		

		
		StraightThreeCornerv2 straightThreeCornerv2= new StraightThreeCornerv2(e);
		retval += straightThreeCornerv2.evaluate(keygroup.group);
		


		
		BentThreev2 bentThreev2= new BentThreev2(e);
		retval += bentThreev2.evaluate(keygroup.group);
		


		

		BentThreeSidev2 bentThreeSidev2= new BentThreeSidev2(e);
		retval += bentThreeSidev2.evaluate(keygroup.group);
		

		
		

		
		BentThreeCornerv2New bentThreeCornerv2= new BentThreeCornerv2New(e);
		retval += bentThreeCornerv2.evaluate(keygroup.group);
		
		
		LearningValues learningValues= new LearningValues(e);
		learningValues.evaluate(keygroup.group,retval);

		
//		BentThreeCornerv2 bentThreeCornerv2= new BentThreeCornerv2(e);
//		int bentval = bentThreeCornerv2.evaluate(keygroup.group);
//		if(bentval==1000)retval=bentval;
//		else retval+=bentval;

		
		int old = 0;

		int neww =  0;

		if(old!=neww) {
			print(old+":"+neww);
		}
		
		
		


		
	
		
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
