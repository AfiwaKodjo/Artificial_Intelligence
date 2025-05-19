package planning;

import java.util.*;

import modelling.*;

public class DijkstraPlanner implements Planner {
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private boolean countNodes;
    private int exploredNodeCount;

    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.countNodes = false; // Par défaut, désactivé
        this.exploredNodeCount = 0; // Initialisation à 0
    }

    // Méthode qui appelle l'algorithme de Dijkstra
    @Override
    public List<Action> plan() {
        return dijkstra();
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    @Override
    public Goal getGoal() {
        return this.goal;
    }

    public void activateNodeCount(boolean activate) {
        this.countNodes = activate;
    }

    public int getExploredNodeCount() {
        return this.exploredNodeCount; // Retourne 0 si la sonde est désactivée
    }

   //Méthode qui explore les états possibles et construit un plan pour atteindre le but
    public List<Action> dijkstra() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(Comparator.comparingDouble(distance::get));
        
        father.put(initialState, null);
        distance.put(initialState, 0.0f);
        open.add(initialState);

        Set<Map<Variable, Object>> goals = new HashSet<>();

        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.poll(); // Récupérer l'état avec la distance minimale
            if (countNodes) {
                exploredNodeCount++; // Incrémenter le compteur si activé
            }

            if (goal.isSatisfiedBy(instantiation)) {
                goals.add(instantiation);
            }

            for (Action action : actions) {
                if (action.isApplicable(instantiation)) {
                    Map<Variable, Object> next = action.successor(instantiation);
                    float newDistance = distance.get(instantiation) + action.getCost();
                    
                    if (newDistance < distance.getOrDefault(next, Float.POSITIVE_INFINITY)) {
                        distance.put(next, newDistance);
                        father.put(next, instantiation);
                        plan.put(next, action);

                        if (!open.contains(next)) {
                            open.add(next); // Ajouter l'état suivant à la file d'attente
                        }
                    }
                }
            }
        }

        if (goals.isEmpty()) {
            return null; // Aucun objectif trouvé
        } else {
            return getDijkstraPlan(father, plan, goals, distance);
        }
    }

    // Méthode pour reconstruire le plan optimal en utilisant les informations qu'on a enregistrer
    public List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
                                         Map<Map<Variable, Object>, Action> plan,
                                         Set<Map<Variable, Object>> goals,
                                         Map<Map<Variable, Object>, Float> distance) {
        List<Action> DIJ_plan = new LinkedList<>();
        float minDistance = Float.POSITIVE_INFINITY;
        Map<Variable, Object> bestGoal = null;

        // Trouver l'état avec la distance minimale parmi les objectifs
        for (Map<Variable, Object> state : goals) {
            float stateDistance = distance.getOrDefault(state, Float.POSITIVE_INFINITY);
            if (stateDistance < minDistance) {
                minDistance = stateDistance;
                bestGoal = state;
            }
        }

        // Si aucun objectif valide n'a été trouvé, retourne une liste vide
        if (bestGoal == null) {
            return DIJ_plan; // Retourne une liste vide
        }

        // Reconstruire le plan en suivant les parents jusqu'à l'état initial
        while (bestGoal != null && father.get(bestGoal) != null) {
            Action action = plan.get(bestGoal);
            if (action != null) {
                DIJ_plan.add(0, action);
            }
            bestGoal = father.get(bestGoal);
        }

        Collections.reverse(DIJ_plan); // Inverser pour obtenir l'ordre correct
        return DIJ_plan; //retourner 'ordre correct'
    }
}
