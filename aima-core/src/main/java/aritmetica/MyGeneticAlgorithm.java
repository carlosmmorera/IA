package aritmetica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import aima.core.search.local.FitnessFunction;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.Individual;

/**
 * Modification of the GeneticAlgorithm class in order to obtain two descendants instead of one
 * and to change the destructive strategy of the generation of the new population and replace it
 * for a non-destructive one.
 * 
 * @author Carlos Moreno Morera
 * @author Pablo Martín Huertas
 *
 * @param <A>
 * 				 the type of the alphabet used in the representation of the
 *            individuals in the population (this is to provide flexibility in
 *            terms of how a problem can be encoded).
 */
public class MyGeneticAlgorithm<A> extends GeneticAlgorithm<A> {
	
	private double reproduceProb;

	public MyGeneticAlgorithm(int individualLength, Collection<A> finiteAlphabet, 
			double mutationProbability, double reproduceP) {
		super(individualLength, finiteAlphabet, mutationProbability);
		reproduceProb = reproduceP;
	}
	
	/**
	 * Construct the new generation
	 */
	protected List<Individual<A>> nextGeneration(List<Individual<A>> population, FitnessFunction<A> fitnessFn) {
		// new_population <- empty set
		List<Individual<A>> newPopulation = new ArrayList<Individual<A>>(population.size());
		// for i = 1 to SIZE(population) do
		for (int i = 0; i < population.size(); i++) {
			// x <- RANDOM-SELECTION(population, FITNESS-FN)
			Individual<A> x = randomSelection(population, fitnessFn);
			// y <- RANDOM-SELECTION(population, FITNESS-FN)
			Individual<A> y = randomSelection(population, fitnessFn);
			// child <- REPRODUCE(x, y)
			List<Individual<A>> childs = reproduceTwo(x, y, fitnessFn);
			// if (small random probability) then child <- MUTATE(child)
			if (random.nextDouble() <= mutationProbability) {
				childs.set(0, mutate(childs.get(0)));
			}
			
			if (random.nextDouble() <= mutationProbability) {
				childs.set(1, mutate(childs.get(1)));
			}
			// add child to new_population
			newPopulation.addAll(childs);
		}
		return newPopulation;
	}
	
	/**
	 * Obtain two descendants from the two given individuals
	 * @param x
	 * @param y
	 * @return list of descendants
	 */
	protected List<Individual<A>> reproduceTwo(Individual<A> x, Individual<A> y, 
			FitnessFunction<A> fitnessFn) {
		List<Individual<A>> result = new ArrayList<Individual<A>>();
		if (random.nextDouble() <= reproduceProb) {
			// n <- LENGTH(x);
			// Note: this is = this.individualLength
			// c <- random number from 1 to n
			int c = randomOffset(individualLength);
			// return APPEND(SUBSTRING(x, 1, c), SUBSTRING(y, c+1, n))
			List<A> childRepresentation = new ArrayList<A>();
			List<A> childRepresent = new ArrayList<A>();
			childRepresentation.addAll(x.getRepresentation().subList(0, c));
			childRepresentation.addAll(y.getRepresentation().subList(c, individualLength));
			childRepresent.addAll(y.getRepresentation().subList(0, c));
			childRepresent.addAll(x.getRepresentation().subList(c, individualLength));
			
			Individual <A> child1 = new Individual<A> (childRepresentation);
			Individual <A> child2 = new Individual<A> (childRepresent);
			
			//If child1 is better than both parents we add it
			if (fitnessFn.apply(child1) > fitnessFn.apply(x) && 
					fitnessFn.apply(child1) > fitnessFn.apply(y)) {
				result.add(new Individual<A> (childRepresentation));
				//If child2 is better than both parents we add it
				if (fitnessFn.apply(child2) > fitnessFn.apply(x) && 
						fitnessFn.apply(child2) > fitnessFn.apply(y))
					result.add(child2);
				//In the other case, we add the best of both parents
				else
					result.add(fitnessFn.apply(x) >= fitnessFn.apply(y)? x : y);
			}
			else {
				//If child2 is better than both parents we add it with the best of both
				if (fitnessFn.apply(child2) > fitnessFn.apply(x) && 
						fitnessFn.apply(child2) > fitnessFn.apply(y)) {
					result.add(child2);
					result.add(fitnessFn.apply(x) >= fitnessFn.apply(y)? x : y);
				}
				//In the case that no one is better than the parents, we add the parents
				else {
					result.add(x);
					result.add(y);
				}
			}
		}
		else {
			result.add(x);
			result.add(y);
		}
		return result;
	}
}
