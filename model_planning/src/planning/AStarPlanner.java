package planning;

import java.util.*;

import modelling.*;

public class AStarPlanner implements Planner {
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristic;
    private boolean countNodes; // activer ou désactiver le comptage
    private int exploredNodeCount; // Compteur de nœuds explorés

    // Utilisation des paramètres pour initialiser les attributs
    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
        this.countNodes = false; // Utilisation du paramètre
        this.exploredNodeCount = 0; // Initialiser le compteur
    }

    @Override
    public List<Action> plan() {
        return astar();
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return initialState;
    }

    @Override
    public Set<Action> getActions() {
        return actions;
    }

    @Override
    public Goal getGoal() {
        return goal;
    }

    public void activateNodeCount(boolean activate) {
        this.countNodes = activate;
    }

    public int getExploredNodeCount() {
        return exploredNodeCount; // Méthode pour récupérer le nombre de nœuds explorés
    }

    public List<Action> astar() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();
        PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(Comparator.comparingDouble(value::get)); //on définit une file de priorité
        open.add(initialState);
        father.put(initialState, null);
        distance.put(initialState, 0.0f);
        value.put(initialState, distance.get(initialState)+heuristic.estimate(initialState));

        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.poll();
            if (countNodes) {
                exploredNodeCount++; // Incrémenter le compteur si activé
            }

            if (goal.isSatisfiedBy(instantiation)) {
                return getAStarPlan(father, plan, instantiation); // Retourner le plan lorsque l'objectif est atteint
            }

            //on parcourt les actions
            for (Action action : actions) {
                if (action.isApplicable(instantiation)) {
                    Map<Variable, Object> next = action.successor(instantiation);
                    if (!distance.containsKey(next)) {
                        distance.put(next, Float.POSITIVE_INFINITY);
                    }
                    float newDistance = distance.get(instantiation) + action.getCost(); //nouvelle distance
                    if (newDistance < distance.getOrDefault(next, Float.POSITIVE_INFINITY)) {
                        distance.put(next, newDistance);
                        value.put(next,newDistance+heuristic.estimate(next));
                        father.put(next, instantiation);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }

        return null; // Si aucun plan n'est trouvé
    }

    public List<Action> getAStarPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
                                      Map<Map<Variable, Object>, Action> plan,
                                      Map<Variable, Object> goal) {
        List<Action> A_STAR_plan = new LinkedList<>();

        // Reconstruire le plan en suivant les parents jusqu'à l'état initial
        while (goal != null && father.get(goal) != null) {
            Action action = plan.get(goal);
            if (action != null) {
                A_STAR_plan.add(action);
            }
            goal = father.get(goal);
        }

        Collections.reverse(A_STAR_plan);  // Inverse la liste pour obtenir l'ordre correct
        return A_STAR_plan; //retourne la liste correcte
    }
}
