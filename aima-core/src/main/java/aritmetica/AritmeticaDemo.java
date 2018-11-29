package aritmetica;

import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Carlos Moreno Morera
 * @author Pablo Martín Huertas
 * 
 */

public class AritmeticaDemo {

	private static final int numNumbers = 6;

	public static void main(String[] args) {

		aritmeticaGeneticAlgorithmSearch();
	}

	/**
	 * Execute the genetic algorithm for the arithmetic problem
	 */
	private static void aritmeticaGeneticAlgorithmSearch() {
		System.out.println("\nAritmeticaDemo GeneticAlgorithm  -->");
		try {
			FitnessFunction<Integer> fitnessFunction = AritmeticaGenAlgoUtil.getFitnessFunction();
			GoalTest<Individual<Integer>> goalTest = AritmeticaGenAlgoUtil.getGoalTest();
			// Poblacion inicial
			Set<Individual<Integer>> population = new HashSet<>();
			for (int i = 0; i < 50; i++) {
				population.add(AritmeticaGenAlgoUtil.generateRandomIndividual(numNumbers));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<>((2*numNumbers)-1,
					AritmeticaGenAlgoUtil.getFiniteAlphabet(), 0.10);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 1000L);

			System.out.println("Max Time (1 second) Best Individual=\n" + AritmeticaGenAlgoUtil.getStringFromIndividual(bestIndividual));
			System.out.println("Operands      = " + numNumbers);
			//System.out.println("# Board Layouts = " + (new BigDecimal(boardSize)).pow(boardSize));
			System.out.println("Fitness         = " + fitnessFunction.apply(bestIndividual));
			System.out.println("Is Goal         = " + goalTest.test(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Iterations      = " + ga.getIterations());
			System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");

			// Run till goal is achieved
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 0L);

			System.out.println("");
			System.out.println("Goal Test Best Individual=\n" + AritmeticaGenAlgoUtil.getStringFromIndividual(bestIndividual));
			System.out.println("Operands      = " + numNumbers);
			//System.out.println("# Board Layouts = " + (new BigDecimal(boardSize)).pow(boardSize));
			System.out.println("Fitness         = " + fitnessFunction.apply(bestIndividual));
			System.out.println("Is Goal         = " + goalTest.test(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

