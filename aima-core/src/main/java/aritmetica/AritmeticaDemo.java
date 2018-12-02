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

		aritmeticaGeneticAlgorithmSearch(50);
	}

	/**
	 * Execute the genetic algorithm for the arithmetic problem
	 */
	private static void aritmeticaGeneticAlgorithmSearch(int initialPop) {
		int mediaIter = 0;
		int maxIter = 0;
		int minIter = 1000000;
		long mediaTime = 0;
		long maxTime = 0;
		long minTime = 1000000;
		int numBlock = 0;
		
		for (int k  = 0; k < 10; ++k) {
			System.out.println("\n" + k + ".- AritmeticaDemo GeneticAlgorithm  -->");
			try {
				FitnessFunction<Integer> fitnessFunction = AritmeticaGenAlgoUtil.getFitnessFunction();
				GoalTest<Individual<Integer>> goalTest = AritmeticaGenAlgoUtil.getGoalTest();
				// Poblacion inicial
				Set<Individual<Integer>> population = new HashSet<>();
				for (int i = 0; i < initialPop; i++) {
					population.add(AritmeticaGenAlgoUtil.generateRandomIndividual(numNumbers));
				}
				GeneticAlgorithm<Integer> ga = new MyGeneticAlgorithm<>((2*numNumbers)-1,
						AritmeticaGenAlgoUtil.getFiniteAlphabet(), 0.1, 0.7);
 				// Run for a set amount of time
				Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 15000L);
 				System.out.println("Max Time (1 second) Best Individual=\n" + AritmeticaGenAlgoUtil.getStringFromIndividual(bestIndividual));
				System.out.println("Operands      = " + numNumbers);
				//System.out.println("# Board Layouts = " + (new BigDecimal(boardSize)).pow(boardSize));
				System.out.println("Fitness         = " + fitnessFunction.apply(bestIndividual));
				System.out.println("Is Goal         = " + goalTest.test(bestIndividual));
				System.out.println("Population Size = " + ga.getPopulationSize());
				System.out.println("Iterations      = " + ga.getIterations());
				System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");

				if (ga.getTimeInMilliseconds() >14999) numBlock++;
				else{mediaIter += ga.getIterations();
				mediaTime += ga.getTimeInMilliseconds();
				if (ga.getIterations() > maxIter) maxIter = ga.getIterations();
				if (ga.getTimeInMilliseconds() > maxTime) maxTime = ga.getTimeInMilliseconds();
				if (ga.getIterations() < minIter) minIter = ga.getIterations();
				if (ga.getTimeInMilliseconds() < minTime) minTime = ga.getTimeInMilliseconds();}
				} catch (Exception e) {
				e.printStackTrace();
 			}
			
		
		}
		mediaIter = mediaIter /(10 - numBlock);
		mediaTime = mediaTime /(10 - numBlock);
		
		System.out.println("\nMedia de iteraciones por ejecucion: " +  mediaIter);
		System.out.println("Cantidad máxima iteraciones: " + maxIter);
		System.out.println("Cantidad mínima iteraciones: " + minIter);
		System.out.println("Media de tiempo por ejecucion: " +  mediaTime);
		System.out.println("Tiempo máximo: " + maxTime);
		System.out.println("Tiempo mínimo: " + minTime);
		System.out.println("Numero de bloqueos: " + numBlock);
	
	}
}
		


