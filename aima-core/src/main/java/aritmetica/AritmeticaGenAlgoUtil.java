package aritmetica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.Individual;

public class AritmeticaGenAlgoUtil {

	public static FitnessFunction<Integer> getFitnessFunction() {
		return new AritmeticaFitnessFunction();
	}
	//IMPLEMENTAR
	public static class AritmeticaFitnessFunction implements FitnessFunction<Integer>{
		public double apply(Individual<Integer> individual) {
			return 0;
		}
	}
	
	
	public static GoalTest<Individual<Integer>> getGoalTest() {
		return new AritmeticaGenAlgoGoalTest();
	}
	
	//IMPLEMENTAR
	public static class AritmeticaGenAlgoGoalTest implements GoalTest<Individual<Integer>>{
		public boolean test(Individual<Integer> state) {
			return false;
		}
	}
	
	public static Individual<Integer> generateRandomIndividual(int numNumbers){
		List<Integer> individualRepresentation = new ArrayList<>();
		
		for (int i = 0; i < 2*numNumbers - 1; i++) {
			if(i%2 ==0)individualRepresentation.add(new Random().nextInt(12)+1);
			else individualRepresentation.add(new Random().nextInt(4));
		}
		return new Individual<>(individualRepresentation);
	}
	
	//IMPLEMENTAR
	public static Collection<Integer> getFiniteAlphabet(int numNumbers){
		return null;
	}
	
	//IMPLEMENTAR
	public static Integer getResultForIndividual(Individual<Integer> individual) {
		return 0;
	}
}
