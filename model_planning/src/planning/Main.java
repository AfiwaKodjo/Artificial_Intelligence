package planning;

import java.util.*;

import modelling.*;

public class Main {
    public static void main(String[] args) {

        // Création de variables et de domaines
        Set<Object> domainVar = new HashSet<>(Arrays.asList("A", "B", "C", "E", "F"));
        Variable var = new Variable("x", domainVar);

        // Création d'un état initial
        Map<Variable, Object> initialState = new HashMap<>();
        initialState.put(var, "A"); // Commencer à A

        // Affichage de l'état initial
        System.out.println("État initial : " + initialState);

        // Création des préconditions et effets d'actions
        // Action 1
        Map<Variable, Object> precondition1 = new HashMap<>();
        precondition1.put(var, "A");
        Map<Variable, Object> effects1 = new HashMap<>();
        effects1.put(var, "B");
        BasicAction action1 = new BasicAction(precondition1, effects1, 1);

        // Action 2
        Map<Variable, Object> precondition2 = new HashMap<>();
        precondition2.put(var, "A");
        Map<Variable, Object> effects2 = new HashMap<>();
        effects2.put(var, "C");
        BasicAction action2 = new BasicAction(precondition2, effects2, 1);
        System.out.println("Action satisfaite ou non:"+action2.isApplicable(initialState));
        System.out.println("État après action 2 : " + action2.toString());

        

        // Action 3
        Map<Variable, Object> precondition3 = new HashMap<>();
        precondition3.put(var, "B");
        Map<Variable, Object> effects3 = new HashMap<>();
        effects3.put(var, "E");
        BasicAction action3 = new BasicAction(precondition3, effects3, 2);

        // Action 4
        Map<Variable, Object> precondition4 = new HashMap<>();
        precondition4.put(var, "B");
        Map<Variable, Object> effects4 = new HashMap<>();
        effects4.put(var, "F");
        BasicAction action4 = new BasicAction(precondition4, effects4, 2);

        // Création d'un ensemble d'actions
        Set<Action> actions = new HashSet<>();
        actions.add(action1);
        actions.add(action2);
        actions.add(action3);
        actions.add(action4);

        // Affichage des actions
        System.out.println("Actions disponibles :");
        System.out.println(actions);

        // Création de but
        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(var, "F"); // Objectif d'atteindre F
        BasicGoal goal = new BasicGoal(goalState);

        // Affichage de l'état du but
        System.out.println("État du but : " + goalState);

        // Démo des algorithmes
        DFSPlanner dfsPlanner = new DFSPlanner(initialState, actions, goal);
        BFSPlanner bfsPlanner = new BFSPlanner(initialState, actions, goal);
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(initialState, actions, goal);
        Heuristic heuristic = new Estimate();
        AStarPlanner aStarPlanner = new AStarPlanner(initialState, actions, goal, heuristic);

        // Activer le compteur de nœuds explorés
        dfsPlanner.activateNodeCount(true);
        bfsPlanner.activateNodeCount(true);
        dijkstraPlanner.activateNodeCount(true);
        aStarPlanner.activateNodeCount(true);

        // Trouver les plans
        List<Action> dfsPlan = dfsPlanner.plan();
        List<Action> bfsPlan = bfsPlanner.plan();
        List<Action> dijkstraPlan = dijkstraPlanner.plan();
        List<Action> aStarPlan = aStarPlanner.plan();

        // Afficher le plan
        System.out.println("Plan trouvé par DFS : " + dfsPlan);
        System.out.println("Plan trouvé par BFS : " + bfsPlan);
        System.out.println("Plan trouvé par Dijkstra : " + dijkstraPlan);
        System.out.println("Plan trouvé par AStar : " + aStarPlan);

        // Afficher le nombre de nœuds explorés
        System.out.println("Nombre de noeuds explorés par DFS : " + dfsPlanner.getExploredNodeCount());
        System.out.println("Nombre de noeuds explorés par BFS : " + bfsPlanner.getExploredNodeCount());
        System.out.println("Nombre de noeuds explorés par Dijkstra : " + dijkstraPlanner.getExploredNodeCount());
        System.out.println("Nombre de noeuds explorés par AStar : " + aStarPlanner.getExploredNodeCount());

       
    }
}
