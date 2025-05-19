package cp;

import modelling.*;
import java.util.*;

public class BacktrackSolver extends AbstractSolver {

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    // Méthode utilisant l'algorithme de backsTracking pour proposer une solution au
    // problème
    @Override
    public Map<Variable, Object> solve() {
        // Appel à la méthode récursive
        return this.backtrack(new HashMap<>(), new LinkedList<>(variables));

    }

    // Méthode qui teste l'ensemble des affectations potentielles du problème
    public Map<Variable, Object> backtrack(Map<Variable, Object> partialInstantiation,
            LinkedList<Variable> variablesNotInstantiate) {
        if (variablesNotInstantiate.isEmpty()) {
            return partialInstantiation;
        }
        // choisir une valeur non encore instantiée
        Variable xi = variablesNotInstantiate.poll();
        for (Object vi : xi.getDomain()) {
            Map<Variable, Object> N = new HashMap<>(partialInstantiation);
            N.put(xi, vi);
            if (isConsistent(N)) {
                Map<Variable, Object> newGoal = backtrack(N, variablesNotInstantiate);

                if (newGoal != null) {
                    return newGoal;
                }

            }

        }
        variablesNotInstantiate.add(xi);
        return null;

    }

}