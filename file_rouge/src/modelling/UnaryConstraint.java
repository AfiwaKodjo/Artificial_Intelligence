package modelling;

import java.util.*;

public class UnaryConstraint implements Constraint { 
    private Variable v;
    private Set<Object> s;

    public UnaryConstraint(Variable v, Set<Object> s){
        this.v = v;
        this.s = s;
    }

 public Set<Variable> getScope(){
     Set<Variable> variables = new HashSet<>();
     variables.add(v);
     return variables;
    }

    //Vérifie si le contrainte est satisfaite
    public boolean isSatisfiedBy(Map<Variable, Object> tasks){
            if (!tasks.containsKey(v)) {
                //Une exception est levée si une variable n'est pas présente
                throw new IllegalArgumentException("Une variable n'est pas déclarée");
            }
            Object task = tasks.get(v);
            return s.contains(task); // Retourne vrai si la valeur est dans l'ensemble des valeurs
               
    }



}