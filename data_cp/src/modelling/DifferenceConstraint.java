package modelling;

import java.util.*;

public class DifferenceConstraint implements Constraint{
    private Variable v1;
    private Variable v2;

    public DifferenceConstraint(Variable v1, Variable v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> variables = new HashSet<> ();
	    variables.add(v1);
	    variables.add(v2);
        
        return variables;
    }

    // Vérifie si la contrainte est satisfaite
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) {
            if (!instantiation.containsKey(v1) || !instantiation.containsKey(v2)) {
                //Une exception est levée
                throw new IllegalArgumentException("Une variable n'est pas déclarée");
            }
            
             // Retourne vrai si les valeurs des deux variables sont différentes
            return (!instantiation.get(v1).equals(instantiation.get(v2)));
    }
    
}
