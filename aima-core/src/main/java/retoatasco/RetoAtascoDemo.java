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
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import retoatasco.board.RetoAtascoBoard;
import retoatasco.examples.BasicTrafficJam;

public class RetoAtascoDemo {
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int option = 1;
		while (option != 0) {
			System.out.println("What type of search do you want to apply?:");
			System.out.println("1.- DFS with Graph Search.");
			System.out.println("2.- DFS with Tree Search.");
			System.out.println("3.- BFS with Graph Search.");
			System.out.println("4.- BFS with Tree Search.");
			System.out.println("0.- Exit.");
			System.out.print("Introduce the number of your preference: ");
			option = reader.nextInt();
			switch(option) {
				case 1: atascoDepthFirstSearch(true);
						break;
				case 2: atascoDepthFirstSearch(false);
						break;
				case 3: atascoBreadthFirstSearch(true);
						break;
				case 4: atascoBreadthFirstSearch(false);
						break;
				default: break;
			}
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
}
