package retoatasco;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import aima.core.agent.Action;
import aima.core.search.agent.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.GeneralProblem;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.framework.qsearch.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import retoatasco.board.RetoAtascoBoard;
import retoatasco.examples.BasicTrafficJam;

public class RetoAtascoDemo {
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int option = 1;
		while (option != 0) {
			printSearchOption();
			System.out.print("Introduce the number of your preference: ");
			option = reader.nextInt();
			executeChosenOption(option);
		}
	}
	
	private static void atascoDepthFirstSearch(boolean isGraphSearch) {
		System.out.println("RetoAtascoDemo: DFS --> ");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
				new GeneralProblem<RetoAtascoBoard, AtascoAction> (new BasicTrafficJam(),
				RetoAtascoFunctions::getActions, RetoAtascoFunctions::getResult,
				RetoAtascoFunctions::testGoal);
			SearchForActions<RetoAtascoBoard, AtascoAction> search = 
					new DepthFirstSearch<>(isGraphSearch? new GraphSearch<>()
							: new TreeSearch<>());
			SearchAgent<RetoAtascoBoard, AtascoAction> agent = 
					new SearchAgent<>(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void atascoBreadthFirstSearch(boolean isGraphSearch) {
		System.out.println("RetoAtascoDemo: BFS --> ");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
				new GeneralProblem<RetoAtascoBoard, AtascoAction> (new BasicTrafficJam(),
				RetoAtascoFunctions::getActions, RetoAtascoFunctions::getResult,
				RetoAtascoFunctions::testGoal);
			SearchForActions<RetoAtascoBoard, AtascoAction> search = 
					new BreadthFirstSearch<>(isGraphSearch? new GraphSearch<>()
							: new TreeSearch<>());
			SearchAgent<RetoAtascoBoard, AtascoAction> agent = 
					new SearchAgent<>(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void atascoGreedyBestFirstSearch(boolean isGraphSearch, boolean
			useAbsoluteDistanceHeuristic) {
		System.out.println("RetoAtascoDemo: Greedy Best First Search "
				+ (useAbsoluteDistanceHeuristic? "(AbsoluteDistanceHeursitic)" : 
				"(Number of vehicles in each line Heuristic)") +"-->");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
					new GeneralProblem<RetoAtascoBoard, AtascoAction> (new BasicTrafficJam(),
					RetoAtascoFunctions::getActions, RetoAtascoFunctions::getResult,
					RetoAtascoFunctions::testGoal);
			SearchForActions<RetoAtascoBoard, AtascoAction> search = 
					new GreedyBestFirstSearch<> (isGraphSearch? new GraphSearch<>()
					: new TreeSearch<>(), useAbsoluteDistanceHeuristic?
					RetoAtascoFunctions.createAbsoluteDistanceHeuristicFunction()
					: RetoAtascoFunctions.createVehiclesPerLineHeuristicFunction());
			SearchAgent<RetoAtascoBoard, AtascoAction> agent = 
					new SearchAgent<>(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void atascoAStarSearch(boolean isGraphSearch, boolean
			useAbsoluteDistanceHeuristic) {
		System.out.println("RetoAtascoDemo: A Star Search "
				+ (useAbsoluteDistanceHeuristic? "(AbsoluteDistanceHeursitic)" : 
				"(Number of vehicles in each line Heuristic)") +"-->");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
					new GeneralProblem<RetoAtascoBoard, AtascoAction> (new BasicTrafficJam(),
					RetoAtascoFunctions::getActions, RetoAtascoFunctions::getResult,
					RetoAtascoFunctions::testGoal);
			SearchForActions<RetoAtascoBoard, AtascoAction> search = new AStarSearch<>
					(isGraphSearch? new GraphSearch<>() : new TreeSearch<>(), 
					useAbsoluteDistanceHeuristic?
					RetoAtascoFunctions.createAbsoluteDistanceHeuristicFunction()
					: RetoAtascoFunctions.createVehiclesPerLineHeuristicFunction());
			SearchAgent<RetoAtascoBoard, AtascoAction> agent = 
					new SearchAgent<>(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printActions(List<Action> actions) {
		actions.forEach(System.out::println);
	}
	
	private static void printInstrumentation(Properties properties) {
		for (Object o : properties.keySet()) {
			String key = (String) o;
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}
	}
	
	private static void printSearchOption() {
		System.out.println("\nWhat type of search do you want to apply?:");
		System.out.println("1.- DFS with Graph Search.");
		System.out.println("2.- DFS with Tree Search.");
		System.out.println("3.- BFS with Graph Search.");
		System.out.println("4.- BFS with Tree Search.");
		System.out.println("5.- Greedy Best First Search with Graph Search"
				+ " and the absolute distance heuristic function.");
		System.out.println("6.- Greedy Best First Search with Tree Search"
				+ " and the absolute distance heuristic function.");
		System.out.println("7.- Greedy Best First Search with Graph Search"
				+ " and the number of vehicles in each line heuristic function.");
		System.out.println("8.- Greedy Best First Search with Tree Search"
				+ " and the number of vehicles in each line heuristic function.");
		System.out.println("9.- A* Search with Graph Search"
				+ " and the absolute distance heuristic function.");
		System.out.println("10.- A* Search with Tree Search"
				+ " and the absolute distance heuristic function.");
		System.out.println("11.- A* Search with Graph Search"
				+ " and the number of vehicles in each line heuristic function.");
		System.out.println("12.- A* Search with Tree Search"
				+ " and the number of vehicles in each line heuristic function.");
		System.out.println("0.- Exit.");
	}
	
	private static void executeChosenOption(int option) {
		switch(option) {
		case 1: atascoDepthFirstSearch(true);
				break;
		case 2: atascoDepthFirstSearch(false);
				break;
		case 3: atascoBreadthFirstSearch(true);
				break;
		case 4: atascoBreadthFirstSearch(false);
				break;
		case 5: atascoGreedyBestFirstSearch(true, true);
				break;
		case 6: atascoGreedyBestFirstSearch(false, true);
				break;
		case 7: atascoGreedyBestFirstSearch(true, false);
				break;
		case 8: atascoGreedyBestFirstSearch(false, false);
				break;
		case 9: atascoAStarSearch(true, true);
			break;
		case 10: atascoAStarSearch(false, true);
			break;
		case 11: atascoAStarSearch(true, false);
			break;
		case 12: atascoAStarSearch(false, false);
			break;
		default: break;
		}
	}
}
