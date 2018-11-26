package aritmetica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.Individual;

public class AritmeticaGenAlgoUtil {
	
	private static final int NUM_SOLUTION = 150;
	private static final int MAX_DIST = 999-101;

	public static FitnessFunction<Integer> getFitnessFunction() {
		return new AritmeticaFitnessFunction();
	}
	
	private static int abs(int i) {
		if (i < 0) return -i;
		else return i;
	}
	
	//Los individuos con mayor fitness estan mas cerca del estado objetivo
	public static class AritmeticaFitnessFunction implements FitnessFunction<Integer>{
		public double apply(Individual<Integer> individual) {
			return MAX_DIST - abs(NUM_SOLUTION - getResultForIndividual(individual));
		}
	}
	
	
	public static GoalTest<Individual<Integer>> getGoalTest() {
		return new AritmeticaGenAlgoGoalTest();
	}
	
	public static class AritmeticaGenAlgoGoalTest implements GoalTest<Individual<Integer>> {
		public boolean test(Individual<Integer> individual) {
			return getResultForIndividual(individual) == NUM_SOLUTION;
		}
	}
	
	public static Individual<Integer> generateRandomIndividual(int numNumbers){
		List<Integer> individualRepresentation = new ArrayList<>();
		
		for (int i = 0; i < 2*numNumbers - 1; i++) {
			individualRepresentation.add(new Random().nextInt(12)+1);
		}
		return new Individual<>(individualRepresentation);
	}
	
	//Codificacion
	//numeros del 1 al 10 igual
	// 11 -> 25
	// 12 -> 50
	public static Collection<Integer> getFiniteAlphabet(){
		Collection<Integer> fab = new ArrayList<>();
		for (int i = 1; i < 13; i++) fab.add(i);
		return fab;
	}
	
	//Codificacion
	// '+' = 1 mod 4
	// '-' = 2 mod 4
	// '*' = 3 mod 4
	// '/' = 0 mod 4
	public static Integer getResultForIndividual(Individual<Integer> individual) {
		int numNumbers = individual.length();
		int partialSolution = individual.getRepresentation().get(0);
		for (int i = 1; i < numNumbers; i++) {
			int op = individual.getRepresentation().get(2*(i-1)+1);
			op = op % 4;
			int number = individual.getRepresentation().get(2*i);
			if (number == 11) number = 25;
			else if (number == 12) number = 50;
			switch (op) {
			case 0: partialSolution += number; break;
			case 1: partialSolution -= number; break;
			case 2: partialSolution *= number; break;
			case 3: partialSolution /= number; break;
			}
		}
		return partialSolution;
	}
}
