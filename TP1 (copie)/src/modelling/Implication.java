package modelling;

import java.util.*;

public class Implication implements Constraint{
    private Variable v1;
    private Set<Object> s1;
    private Variable v2;
    private Set<Object> s2;

    public Implication(Variable v1, Set<Object>s1,Variable v2, Set<Object>s2){
        this.v1 = v1;
        this.s1 = s1;
        this.v2 = v2;
        this.s2 = s2;
    }

    public Set<Variable> getScope(){ 
        Set<Variable> variables = new HashSet<>();
        variables.add(v1);
        variables.add(v2);
        return variables;
    }

    public boolean isSatisfiedBy(Map<Variable, Object> taches) {
            if (!taches.containsKey(v1) ||!taches.containsKey(v2)) {
                throw new IllegalArgumentException("La map des tâches doit contenir toutes les variables du scope.");
            }
            Object tache1 = taches.get(v1);
            Object tache2 = taches.get(v2);
            if(s1.contains(tache1) && s2.contains(tache2)){  
                return s2.contains(tache2);
    } else {
        return true;  // Si v1 ne prend pas une valeur de S1, la contrainte est satisfaite quelle que soit la valeur de v2.
    }

    
    
}
}