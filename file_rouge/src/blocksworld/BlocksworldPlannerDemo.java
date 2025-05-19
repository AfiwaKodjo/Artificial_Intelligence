package blocksworld;

import planning.*;
import modelling.*;
import java.util.*;
import javax.swing.JFrame;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWIntegerGUI;
import bwui.BWComponent;

public class BlocksworldPlannerDemo {
    
    private static Map<Variable, Object> createState(BlocksworldVariables vars, List<List<Integer>> piles) {
        Map<Variable, Object> state = new HashMap<>();

        // Initialisation par défaut
        for (int b = 0; b < vars.getNbBlock(); b++) {
            state.put(vars.getFixedB().get(b), false);
            state.put(vars.getOnB().get(b), -1); // Par défaut, met tous les blocs dans la pile 1
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
                    state.put(vars.getFixedB().get(currentBlock), true); // Le bloc courant est fixé
                }
            }
        }

        return state;
    }

    private static BWState<Integer> makeBWState(Map<Variable, Object> state, BlocksworldVariables vars) {

        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(vars.getNbBlock());
        Map<Integer, Integer> relations = new HashMap<>();

        // D'abord collecter toutes les relations
        for (int b = 0; b < vars.getNbBlock(); b++) {
            Variable onB = vars.getOnB().get(b);
            Object value = state.get(onB);
            if (value instanceof Integer) {
                int under = (Integer) value;
                if (under >= 0) { // Si c'est un bloc et non une pile
                    relations.put(b, under);
                }
            }
        }

        // Trouver les blocs qui sont sur la table (pas de bloc en dessous d'eux)
        Set<Integer> bottomBlocks = new HashSet<>();
        for (int b = 0; b < vars.getNbBlock(); b++) {
            Variable onB = vars.getOnB().get(b);
            Object value = state.get(onB);
            if (value instanceof Integer) {
                int under = (Integer) value;
                if (under < 0) { // Si c'est une pile
                    bottomBlocks.add(b);
                }
            }
        }

        // Pour chaque bloc sur la table, construire la pile vers le haut
        for (int bottomBlock : bottomBlocks) {
            int currentBlock = bottomBlock;
            while (true) {
                // Chercher le bloc qui est au-dessus
                Integer above = null;
                for (Map.Entry<Integer, Integer> entry : relations.entrySet()) {
                    if (entry.getValue() == currentBlock) {
                        above = entry.getKey();
                        break;
                    }
                }

                if (above != null) {
                    builder.setOn(above, currentBlock);
                    currentBlock = above;
                } else {
                    break;
                }
            }
        }

        return builder.getState();
    }

    private static void visualizePlan(String title, List<Action> plan, Map<Variable, Object> initialState,
            BlocksworldVariables vars, int nbBlocks) {
        if (plan != null) {
            BWIntegerGUI gui = new BWIntegerGUI(nbBlocks);
            JFrame frame = new JFrame(title);
            BWState<Integer> bwState = makeBWState(initialState, vars);
            BWComponent<Integer> component = gui.getComponent(bwState);
            frame.add(component);
            frame.pack();
            frame.setVisible(true);

            // Exécution du plan
            Map<Variable, Object> currentState = new HashMap<>(initialState);
            for (Action action : plan) {
                try {
                    Thread.sleep(1000); // Pause d'une seconde entre chaque action
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentState = action.successor(currentState);
                System.out.println("Etat apres action :");
                System.out.println(currentState);
                component.setState(makeBWState(currentState, vars));
            }
            System.out.println("Simulation du plan " + title + " terminee");
        }
    }

    public static void main(String[] args) {
        int nbBlocks = 5;
        int nbPiles = 2;
        BlocksworldVariables vars = new BlocksworldVariables(nbBlocks, nbPiles);

        // Création de l'état initial : deux piles
        List<List<Integer>> piles = new ArrayList<>();
        List<Integer> pile1 = new ArrayList<>();
        pile1.add(0);// bloc 0 sur la pile -1
        pile1.add(1); // bloc 1 sur bloc 0
        pile1.add(2);

        List<Integer> pile2 = new ArrayList<>();
        pile2.add(3);
        pile2.add(4);

        piles.add(pile1);
        piles.add(pile2);

        Map<Variable, Object> initialState = createState(vars, piles);
        System.out.println("Etat initial cree");
        System.out.println(initialState);

        // État but : bloc 4 sur bloc 1
        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(vars.getOnB().get(3), 2);
        System.out.println("-----Etat but:----- ");
        System.out.println(goalState);

        BasicGoal goal = new BasicGoal(goalState);
        System.out.println("----Goal----");
        System.out.println(goal);
        System.out.println("But cree");

        BasicActionWorld actionWorld = new BasicActionWorld(nbBlocks, nbPiles);
        actionWorld.getActions();
        // System.out.println("Actions creees: " + actions.size() + " actions");

        // Test BFS
        System.out.println("\nBFS Planner:");
        BFSPlanner bfsPlanner = new BFSPlanner(initialState, actionWorld.getActions(), goal);
        bfsPlanner.activateNodeCount(true);
        long startTime = System.currentTimeMillis();
        List<Action> bfsPlan = bfsPlanner.plan();
        System.out.println("Temps d'execution: " + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println("Noeuds explores: " + bfsPlanner.getExploredNodeCount());
        System.out.println("Nombre d'actions dans le plan: " + (bfsPlan != null ? bfsPlan.size() : "Pas de solution"));

        // Test AStar
        System.out.println("\nAstar Planner:");
        BlocksworldHeuristic heuristic = new BlocksworldHeuristic();
        //BlocksworldHeuristic heuristic2 = new BlocksworldHeuristic();
        AStarPlanner astarPlanner = new AStarPlanner(initialState, actionWorld.getActions(), goal, heuristic);
        //AStarPlanner astarPlanner2 = new AStarPlanner(initialState, actionWorld.getActions(), goal, heuristic2);
        astarPlanner.activateNodeCount(true);
        long startTime2 = System.currentTimeMillis();
        List<Action> astarPlan = astarPlanner.plan();
        System.out.println("Temps d'execution: " + (System.currentTimeMillis() - startTime2) + " ms");
        System.out.println("Noeuds explores: " + astarPlanner.getExploredNodeCount());
        System.out.println("Nombre d'actions dans le plan: " + (astarPlan != null ? astarPlan.size() : "Pas de solution"));

        // Test DFS avec timeout
        System.out.println("\nDFS Planner:");
        DFSPlanner dfsPlanner = new DFSPlanner(initialState, actionWorld.getActions(), goal);
        dfsPlanner.activateNodeCount(true);
        startTime = System.currentTimeMillis();

        @SuppressWarnings("unchecked")
        final List<Action>[] dfsPlan = new List[1];
        Thread dfsThread = new Thread(() -> {
            dfsPlan[0] = dfsPlanner.plan();
        });
        dfsThread.start();
        try {
            dfsThread.join(5000); // 5000ms, genre 5s
            if (dfsThread.isAlive()) {
                dfsThread.interrupt();
                System.out.println("DFS a ete interrompu apres 5 secondes");
            } else {
                System.out.println("Temps d'execution: " + (System.currentTimeMillis() - startTime) + " ms");
                System.out.println("Noeuds explores: " + dfsPlanner.getExploredNodeCount());
                System.out.println("Nombre d'actions dans le plan: " +
                        (dfsPlan[0] != null ? dfsPlan[0].size() : "Pas de solution"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Test Dijkstra
        System.out.println("\nDijkstra Planner:");
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(initialState, actionWorld.getActions(), goal);
        dijkstraPlanner.activateNodeCount(true);
        startTime = System.currentTimeMillis();
        List<Action> dijkstraPlan = dijkstraPlanner.plan();
        System.out.println("Temps d'execution: " + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println("Noeuds explores: " + dijkstraPlanner.getExploredNodeCount());
        System.out.println("Nombre d'actions dans le plan: " +
                (dijkstraPlan != null ? dijkstraPlan.size() : "Pas de solution"));

        // Visualisation des plans
        visualizePlan("Plan BFS", bfsPlan, initialState, vars, nbBlocks);
        visualizePlan("Plan Dijkstra", dijkstraPlan, initialState, vars, nbBlocks);
        visualizePlan("Plan AStar", astarPlan, initialState, vars, nbBlocks);
        if (dfsPlan[0] != null) {
            visualizePlan("Plan DFS", dfsPlan[0], initialState, vars, nbBlocks);
        }
    }
}