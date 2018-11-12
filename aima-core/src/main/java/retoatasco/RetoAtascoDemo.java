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
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import retoatasco.board.ExtendableBoard;
import retoatasco.board.RetoAtascoBoard;
import retoatasco.coordinate.Coordinate;
import retoatasco.examples.BasicTrafficJam;

public class RetoAtascoDemo {
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int searchOption = 1, menuOption = 1;
		while (menuOption != 0) {
			System.out.println("\nWhich option do you prefer?");
			System.out.println("1.- Use the preset example.");
			System.out.println("2.- Create my own example.");
			System.out.println("0.- Exit.");
			System.out.print("Introduce the number of your preference: ");
			menuOption = reader.nextInt();
			ExtendableBoard b;
			if (menuOption == 2)
				b = createExample();
			else
				b = new BasicTrafficJam();
			if (menuOption != 0) {
				System.out.println("The example board is the next one:");
				System.out.println(b.toString());
				searchOption = 1;
				while (searchOption != 0) {
					printSearchOption();
					System.out.print("Introduce the number of your preference: ");
					searchOption = reader.nextInt();
					executeChosenOption(searchOption, b);
				}
			}
		}
	}
	
	private static ExtendableBoard createExample() {
		Scanner r = new Scanner(System.in);
		System.out.println("How many rows does the board have? ");
		int numRows = r.nextInt();
		System.out.println("How many columns does the board have? ");
		int numColumns = r.nextInt();
		System.out.println("Now, I am going to ask you about where the exit and the"
				+ " vehicles are. You have to ask me in coordinates (starting with 0"
				+ " and ending with " + (numRows - 1) + " in the rows case, and "
				+ "ending with " + (numColumns - 1) + " in the columns case). The first "
				+ "row is the top one and the first column the left one.");
		System.out.println("In which row is the exit? ");
		int row = r.nextInt();
		System.out.println("In which column is the exit? ");
		int column = r.nextInt();
		Coordinate escape = new Coordinate(row, column);
		System.out.println("How many cars are there (including the red one)? ");
		int num = r.nextInt();
		Coordinate cars[][] = new Coordinate[num][ExtendableBoard.CAR_SIZE];
		System.out.println("Now, you have to give me " + ExtendableBoard.CAR_SIZE +
				" coordinates of each car.");
		for (int i = 0; i < ExtendableBoard.CAR_SIZE; i++) {
			System.out.println("In which row is one of the parts of the red car? ");
			row = r.nextInt();
			System.out.println("In which column is that part of the red car? ");
			column = r.nextInt();
			cars[0][i] = new Coordinate(row, column);
		}
		for (int i = 1; i < num; i++) {
			for (int j = 0; j < ExtendableBoard.CAR_SIZE; j++) {
				System.out.println("In which row is one of the parts of the car number "
						+ i + "? ");
				row = r.nextInt();
				System.out.println("In which column is one of the parts of the car number "
						+ i + "? ");
				column = r.nextInt();
				cars[i][j] = new Coordinate(row, column);
			}
		}
		System.out.println("How many lorries are there? ");
		num = r.nextInt();
		Coordinate lorries[][] = new Coordinate[num][ExtendableBoard.LORRY_SIZE];
		System.out.println("Now, you have to give me " + ExtendableBoard.LORRY_SIZE +
				" coordinates of each lorry.");
		for (int i = 0; i < num ; i++) {
			for (int j=0; j < ExtendableBoard.LORRY_SIZE; j++) {
				System.out.println("In which row is one of the parts of the lorry number "
						+ (i+1) + "? ");
				row = r.nextInt();
				System.out.println("In which column is one of the parts of the lorry number "
						+ (i+1) + "? ");
				column = r.nextInt();
				lorries[i][j] = new Coordinate(row, column);
			}
		}
		return new ExtendableBoard(numRows, numColumns, escape, cars, lorries);
	}
	
	private static void atascoDepthFirstSearch(boolean isGraphSearch, ExtendableBoard b) {
		System.out.println("RetoAtascoDemo: DFS --> ");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
				new GeneralProblem<RetoAtascoBoard, AtascoAction> (b,
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
	
	private static void atascoBreadthFirstSearch(boolean isGraphSearch, ExtendableBoard b) {
		System.out.println("RetoAtascoDemo: BFS --> ");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
				new GeneralProblem<RetoAtascoBoard, AtascoAction> (b,
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
			useAbsoluteDistanceHeuristic, ExtendableBoard b) {
		System.out.println("RetoAtascoDemo: Greedy Best First Search "
				+ (useAbsoluteDistanceHeuristic? "(AbsoluteDistanceHeursitic)" : 
				"(Number of vehicles in each line Heuristic)") +"-->");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
					new GeneralProblem<RetoAtascoBoard, AtascoAction> (b,
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
			useAbsoluteDistanceHeuristic, ExtendableBoard b) {
		System.out.println("RetoAtascoDemo: A Star Search "
				+ (useAbsoluteDistanceHeuristic? "(AbsoluteDistanceHeursitic)" : 
				"(Number of vehicles in each line Heuristic)") +"-->");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
					new GeneralProblem<RetoAtascoBoard, AtascoAction> (b,
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
	
	private static void atascoDepthLimitedSearch(ExtendableBoard b) {
		Scanner r = new Scanner(System.in);
		System.out.println("What depth limit do you want to establish? ");
		int depth = r.nextInt();
		System.out.println("RetoAtascoDemo: recursive DLS (" + depth + ") -->");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
					new GeneralProblem<RetoAtascoBoard, AtascoAction> (b,
					RetoAtascoFunctions::getActions, RetoAtascoFunctions::getResult,
					RetoAtascoFunctions::testGoal);
			SearchForActions<RetoAtascoBoard, AtascoAction> search = 
					new DepthLimitedSearch<>(depth);
			SearchAgent<RetoAtascoBoard, AtascoAction> agent = 
					new SearchAgent<>(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void atascoIterativeDepthLimitedSearch(ExtendableBoard b) {
		System.out.println("RetoAtascoDemo: Iterative DLS -->");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
					new GeneralProblem<RetoAtascoBoard, AtascoAction> (b,
					RetoAtascoFunctions::getActions, RetoAtascoFunctions::getResult,
					RetoAtascoFunctions::testGoal);
			SearchForActions<RetoAtascoBoard, AtascoAction> search = 
					new IterativeDeepeningSearch<>();
			SearchAgent<RetoAtascoBoard, AtascoAction> agent =
					new SearchAgent<>(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void atascoHillClimbingSearch(boolean useAbsoluteDistanceHeuristic,
			ExtendableBoard b) {
		System.out.println("RetoAtascoDemo: Hill Climbing Search "
				+ (useAbsoluteDistanceHeuristic? "(AbsoluteDistanceHeursitic)" : 
				"(Number of vehicles in each line Heuristic)") +"-->");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
					new GeneralProblem<RetoAtascoBoard, AtascoAction> (b,
					RetoAtascoFunctions::getActions, RetoAtascoFunctions::getResult,
					RetoAtascoFunctions::testGoal);
			HillClimbingSearch<RetoAtascoBoard, AtascoAction> search = 
					new HillClimbingSearch<>(useAbsoluteDistanceHeuristic?
					RetoAtascoFunctions.createAbsoluteDistanceHeuristicFunction()
					: RetoAtascoFunctions.createVehiclesPerLineHeuristicFunction());
			SearchAgent<RetoAtascoBoard, AtascoAction> agent = 
					new SearchAgent<>(problem, search);

			printActions(agent.getActions());
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
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
		System.out.println("13.- Recursive Depth Limited Search.");
		System.out.println("14.- Iterative Depth Limited Search.");
		System.out.println("15.- Hill Climbing Search with the absolute distance"
				+ " heuristic function.");
		System.out.println("16.- Hill Climbing Search with the number of vehicles"
				+ " in each line heuristic function.");
		System.out.println("0.- Exit.");
	}
	
	private static void executeChosenOption(int option, ExtendableBoard b) {
		switch(option) {
		case 1: atascoDepthFirstSearch(true, b);
				break;
		case 2: atascoDepthFirstSearch(false, b);
				break;
		case 3: atascoBreadthFirstSearch(true, b);
				break;
		case 4: atascoBreadthFirstSearch(false, b);
				break;
		case 5: atascoGreedyBestFirstSearch(true, true, b);
				break;
		case 6: atascoGreedyBestFirstSearch(false, true, b);
				break;
		case 7: atascoGreedyBestFirstSearch(true, false, b);
				break;
		case 8: atascoGreedyBestFirstSearch(false, false, b);
				break;
		case 9: atascoAStarSearch(true, true, b);
			break;
		case 10: atascoAStarSearch(false, true, b);
			break;
		case 11: atascoAStarSearch(true, false, b);
			break;
		case 12: atascoAStarSearch(false, false, b);
			break;
		case 13: atascoDepthLimitedSearch(b);
			break;
		case 14: atascoIterativeDepthLimitedSearch(b);
			break;
		case 15: atascoHillClimbingSearch(true, b);
			break;
		case 16: atascoHillClimbingSearch(false, b);
			break;
		default: break;
		}
	}
}
