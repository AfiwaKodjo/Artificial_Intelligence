package planning;

import java.util.*;

import modelling.*;

public class DFSPlanner implements Planner {
    private Map<Variable, Object> initialState;  // Etat initial
    private Set<Action> actions; //Ensemble des actions
    private Goal goal; //Le but qu'on veut atteindre
    private boolean countNodes; // Attribut pour activer la sonde
    private int exploredNodeCount; // Attribut pour compter les nœuds explorés

    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.countNodes = false; // Par défaut, la sonde est désactivée
        this.exploredNodeCount = 0; // Initialiser le compteur
    }

    //Méthode qui lance la recherche en profondeur pour trouver un plan d'actions depuis l'état initial.
    @Override
    public List<Action> plan() {
        Stack<Action> actionsStack = new Stack<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(initialState);
        List<Action> callPlan = dfs(initialState, actionsStack, closed);
        
        // Retourne une liste vide s'il n'y a pas de plan trouvé
        return callPlan;
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

    //Méthode pour implémenter la recherche en profondeur
    public List<Action> dfs(Map<Variable, Object> instantiation, Stack<Action> plan, Set<Map<Variable, Object>> closed) {
        if (countNodes) {
             exploredNodeCount++; // Incrémenter le compteur si activé
            }
        //On vérifie si l'état courant ou actuel satifait le but
        if (goal.isSatisfiedBy(instantiation)) {
            return new ArrayList<>(plan); // Retourner le plan courant
        }

        //on parcourt chaque action puis après on vérifie si l'action est applicable dans l'état actuel
        for (Action action : actions) {
            if (action.isApplicable(instantiation)) {
                Map<Variable, Object> next = action.successor(instantiation);
                if (!closed.contains(next)) {
                    
                    plan.push(action);
                    closed.add(next);
                    List<Action> subplan = dfs(next, plan, closed);
                    if (subplan != null && !subplan.isEmpty()) {
                        return subplan; // Retourner le plan trouvé
                    } else {
                        plan.pop(); // Enlever l'action si le sous-plan est vide
                        closed.remove(next);
                    }
                }
            }
        }
        return null; // Aucun plan trouvé
    }
}
