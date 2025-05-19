package blocksworld;

import planning.*;
import modelling.*;
import java.util.*;

public class MainConsole {
  // Methode pour creer un etat initial à partir d'une liste de piles
  private static Map<Variable, Object> createState(BlocksworldVariables vars, List<List<Integer>> piles) {
    Map<Variable, Object> state = new HashMap<>();

    // Initialisation par defaut
    for (int b = 0; b < vars.getNbBlock(); b++) {
      state.put(vars.getFixedB().get(b), false);
      state.put(vars.getOnB().get(b), -1); // Par defaut, met tous les blocs dans la pile 1
    }
    for (int p = 1; p <= vars.getNbPile(); p++) {
      state.put(vars.getFreeP().get(-p), true);
    }

    // Configuration des piles
    for (List<Integer> pile : piles) {
      if (pile.size() > 0) {
        // Le premier bloc est sur la table
        int firstBlock = pile.get(0);
        state.put(vars.getOnB().get(firstBlock), -1); // Met le premier bloc dans la pile 1
        state.put(vars.getFreeP().get(-1), false); // La pile n'est plus libre

        // Pour les autres blocs de la pile
        for (int i = 0; i < pile.size() - 1; i++) {
          int currentBlock = pile.get(i);
          int nextBlock = pile.get(i + 1);

          // Le bloc suivant est sur le bloc courant
          state.put(vars.getOnB().get(nextBlock), currentBlock);
          state.put(vars.getFixedB().get(currentBlock), true); // Le bloc courant est fixe
        }
      }
    }

    return state;
  }

  public static void main(String[] args) {
    // Configuration avec 5 blocs, 2 piles
    int nbBlocks = 5;
    int nbPiles = 2;
    BlocksworldVariables vars = new BlocksworldVariables(nbBlocks, nbPiles);

    // Creation de l'etat initial : deux piles
    List<List<Integer>> piles = new ArrayList<>();
    List<Integer> pile1 = new ArrayList<>();
    pile1.add(0); // bloc 0 sur la table
    pile1.add(2); // bloc 2 sur bloc 0
    pile1.add(4); // bloc 4 sur bloc 2

    List<Integer> pile2 = new ArrayList<>();
    pile2.add(1); // bloc 1 sur la table
    pile2.add(3); // bloc 3 sur bloc 1

    piles.add(pile1);
    piles.add(pile2);

    Map<Variable, Object> initialState = createState(vars, piles);
    System.out.println("Etat initial cree");

    // etat but : bloc 4 sur bloc 1
    Map<Variable, Object> goalState = new HashMap<>();
    goalState.put(vars.getOnB().get(4), 2); // On veut le bloc 4 sur le bloc 1

    Goal goal = new BasicGoal(goalState);
    System.out.println("But cree");

    // Creation des actions
    BasicActionWorld actionWorld = new BasicActionWorld(nbBlocks, nbPiles);
    Set<Action> actions = actionWorld.getActions();
    System.out.println("Actions creees: " + actions.size() + " actions");

    // Test BFS
    System.out.println("\nBFS Planner:");
    BFSPlanner bfsPlanner = new BFSPlanner(initialState, actions, goal);
    bfsPlanner.activateNodeCount(true);
    long startTime = System.currentTimeMillis();
    List<Action> bfsPlan = bfsPlanner.plan();
    System.out.println("Temps d'execution: " + (System.currentTimeMillis() - startTime) + " ms");
    System.out.println("Noeuds explores: " + bfsPlanner.getExploredNodeCount());
    System.out.println("Nombre d'actions dans le plan: " + (bfsPlan != null ? bfsPlan.size() : "Pas de solution"));

    // Test DFS
    System.out.println("\nDFS Planner:");
    DFSPlanner dfsPlanner = new DFSPlanner(initialState, actions, goal);
    dfsPlanner.activateNodeCount(true);
    startTime = System.currentTimeMillis();
    List<Action> dfsPlan = dfsPlanner.plan();
    System.out.println("Temps d'execution: " + (System.currentTimeMillis() - startTime) + " ms");
    System.out.println("Noeuds explores: " + dfsPlanner.getExploredNodeCount());
    System.out.println("Nombre d'actions dans le plan: " + (dfsPlan != null ? dfsPlan.size() : "Pas de solution"));

    // Test Dijkstra
    System.out.println("\nDijkstra Planner:");
    DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(initialState, actions, goal);
    dijkstraPlanner.activateNodeCount(true);
    startTime = System.currentTimeMillis();
    List<Action> dijkstraPlan = dijkstraPlanner.plan();
    System.out.println("Temps d'execution: " + (System.currentTimeMillis() - startTime) + " ms");
    System.out.println("Noeuds explores: " + dijkstraPlanner.getExploredNodeCount());
    System.out.println("Nombre d'actions dans le plan: " + (dijkstraPlan != null ? dijkstraPlan.size() : "Pas de solution"));
  }

}