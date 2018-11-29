package aritmetica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.Individual;

/**
 * @author Carlos Moreno Morera
 * @author Pablo Martín Huertas
 */
public class AritmeticaGenAlgoUtil {
	
	private static final int NUM_SOLUTION = 666;
	private static final int MAX_DIST = 999-101;

	/**
	 * Stablish the fitness function of the genetic algorithm
	 * @return the fitness function
	 */
	public static FitnessFunction<Integer> getFitnessFunction() {
		return new AritmeticaFitnessFunction();
	}
	
	/**
	 * Obtain the absolute value of the given integer
	 * @param i given number
	 * @return the absolute value of i
	 */
	private static int abs(int i) {
		if (i < 0) return -i;
		else return i;
	}
	
	//Los individuos con mayor fitness estan mas cerca del estado objetivo
	public static class AritmeticaFitnessFunction implements FitnessFunction<Integer>{
		/**
		 * Calculate the fitness function of the given individual
		 * @param individual
		 */
		public double apply(Individual<Integer> individual) {
			if (getResultForIndividual(individual)== NUM_SOLUTION) return 1;
			else return 1/abs(NUM_SOLUTION - getResultForIndividual(individual));
		}
	}
	
	/**
	 * Goal test of the genetic algorithm
	 * @return the goal test
	 */
	public static GoalTest<Individual<Integer>> getGoalTest() {
		return new AritmeticaGenAlgoGoalTest();
	}
	
	public static class AritmeticaGenAlgoGoalTest implements GoalTest<Individual<Integer>> {
		/**
		 * Test if the individual is the solution of the problem
		 * @param individual to analyze
		 * @return true if the individual is a solution and false in other case
		 */
		public boolean test(Individual<Integer> individual) {
			return getResultForIndividual(individual) == NUM_SOLUTION;
		}
	}
	
	/**
	 * Generate a random individual
	 * @param numNumbers numbers of genes
	 * @return an individual
	 */
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
	/**
	 * Obtain the finite alphabet where we are going to takes the genes
	 * @return the finite alphabet
	 */
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
	/***
	 * Obtain the result of evaluating the expression
	 * @param individual expression
	 * @return the result of the evaluation
	 */
	public static Integer getResultForIndividual(Individual<Integer> individual) {
		int numNumbers = (individual.length()/2)+1;
		int partialSolution = individual.getRepresentation().get(0);
		for (int i = 1; i < numNumbers; i++) {
			int op = individual.getRepresentation().get(2*(i-1)+1);
			op = op % 4;
			int number = individual.getRepresentation().get(2*i);
			if (number == 11) number = 25;
			else if (number == 12) number = 50;
			switch (op) {
			case 1: partialSolution += number; break;
			case 2: partialSolution -= number; break;
			case 3: partialSolution *= number; break;
			case 0: partialSolution /= number; break;
			}
		}
		return partialSolution;
	}
	
	/**
	 * Transform the given individual in a string
	 * @param individual to transform
	 * @return the String
	 */
	public static String getStringFromIndividual(Individual<Integer> individual) {
		StringBuilder builder = new StringBuilder();
		builder.append(individual.getRepresentation().get(0));
		int numNumbers = (individual.length()/2)+1;
		for (int i = 1; i < numNumbers; i++) {
			int op = individual.getRepresentation().get(2*(i-1)+1);
			op = op % 4;
			int number = individual.getRepresentation().get(2*i);
			if (number == 11) number = 25;
			else if (number == 12) number = 50;
			switch (op) {
			case 1: builder.append(" + " + number); break;
			case 2: builder.append(" - " + number); break;
			case 3: builder.append(" * " + number); break;
			case 0: builder.append(" / " + number); break;
			}
		}
		builder.append(" = " + getResultForIndividual(individual));
		return builder.toString();
		
	}
}
