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

    // Vérifie si la contrainte d'implication est satisfaite
    public boolean isSatisfiedBy(Map<Variable, Object> tasks) {
            if (!tasks.containsKey(v1) ||!tasks.containsKey(v2)) {
                // Lance une exception si une variable est manquante
                throw new IllegalArgumentException("La map des tâches doit contenir toutes les variables du scope.");
            }
            Object task1 = tasks.get(v1);
            Object task2 = tasks.get(v2);
            if(s1.contains(task1) || s2.contains(task2)){  
                return s2.contains(task2);
    } else {
        return true;  
    }// Si v1 ne prend pas une valeur de S1, la contrainte est satisfaite quelle que soit la valeur de v2. 

    
    
}
}