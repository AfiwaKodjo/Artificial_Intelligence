package planning;

import java.util.*;

import modelling.Variable;

public class BFSPlanner implements Planner {
    private Map<Variable, Object> initialState; // Etat initial
    private Set<Action> actions; //Ensemble des actions
    private Goal goal; //le but qu'on veut atteindre
    private boolean countNodes; // Attribut pour activer la sonde
    private int exploredNodeCount; // Attribut pour compter les nœuds explorés

    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.countNodes = false; // Par défaut, la sonde est désactivée
        this.exploredNodeCount = 0; // Initialiser le compteur
    }

    //Elle fait appel à la méthode bfs pour effectuer la recherche
    @Override
    public List<Action> plan() {
        return bfs();
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

    // Méthode pour activer/désactiver la sonde
    public void activateNodeCount(boolean activate) {
        this.countNodes = activate;
        if (!activate) {
            this.exploredNodeCount = 0; // Réinitialiser le compteur si désactivé
        }
    }

    // Méthode pour récupérer le nombre de nœuds explorés
    public int getExploredNodeCount() {
        return this.exploredNodeCount;
    }

    /*Cette méthode explore les états possibles et retourne une
    liste d'actions qui représente le plan pour atteindre le but, ou null si aucun plan n'a été trouvé.*/
    public List<Action> bfs() {
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        Queue<Map<Variable, Object>> open = new LinkedList<>();
        open.add(initialState);
        father.put(initialState, null);

         // Si l'état initial satisfait déjà l'objectif, retourner un plan vide
        if (goal.isSatisfiedBy(initialState)) {
            return new ArrayList<>();
        }else{

        while (!open.isEmpty()) {
            if (countNodes) {
                exploredNodeCount++; // Incrémenter le compteur si activé
            }

            Map<Variable, Object> instantiation = open.poll();
            closed.add(instantiation);
          
            //parcourt la liste des actions
            for (Action action : actions) {
                if (action.isApplicable(instantiation)) {
                    Map<Variable, Object> next = action.successor(instantiation);
                    if (!closed.contains(next) && !open.contains(next)) {
                        father.put(next, instantiation);
                        plan.put(next, action);
                        if (goal.isSatisfiedBy(next)) {
                            return getBfsPlan(father, plan, next);
                        } else {
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null; // Aucun plan trouvé
    }}

    //Méthode pour reconstruire le plan une fois que le but est atteint.
    public List<Action> getBfsPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, 
                                     Map<Map<Variable, Object>, Action> plan, 
                                     Map<Variable, Object> goal) {
        List<Action> bfsPlan = new LinkedList<>();

        while (goal != null) {
            Action action = plan.get(goal);
            if (action != null) {
                bfsPlan.add(0, action);
            }
            goal = father.get(goal);
        }

        Collections.reverse(bfsPlan); // Inverser la liste pour obtenir le bon ordre des actions

        return bfsPlan; // On retourne le plan reconstruit
    }
}
