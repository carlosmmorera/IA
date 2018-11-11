package retoatasco;

import java.util.List;
import java.util.Properties;
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
		atascoDepthFirstSearch();
		atascoBreadthFirstSearch();
	}
	
	private static void atascoDepthFirstSearch() {
		System.out.println("RetoAtascoDemo: DFS --> ");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
				new GeneralProblem<RetoAtascoBoard, AtascoAction> (new BasicTrafficJam(),
				RetoAtascoFunctions::getActions, RetoAtascoFunctions::getResult,
				RetoAtascoFunctions::testGoal);
			SearchForActions<RetoAtascoBoard, AtascoAction> search = 
					new DepthFirstSearch<>(new GraphSearch<>());
			SearchAgent<RetoAtascoBoard, AtascoAction> agent = 
					new SearchAgent<>(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void atascoBreadthFirstSearch() {
		System.out.println("RetoAtascoDemo: BFS --> ");
		try {
			Problem<RetoAtascoBoard, AtascoAction> problem = 
				new GeneralProblem<RetoAtascoBoard, AtascoAction> (new BasicTrafficJam(),
				RetoAtascoFunctions::getActions, RetoAtascoFunctions::getResult,
				RetoAtascoFunctions::testGoal);
			SearchForActions<RetoAtascoBoard, AtascoAction> search = 
					new BreadthFirstSearch<>(new TreeSearch<>());
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
