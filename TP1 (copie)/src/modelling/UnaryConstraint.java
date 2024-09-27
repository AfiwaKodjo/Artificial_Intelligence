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

    public boolean isSatisfiedBy(Map<Variable, Object> taches){
            if (!taches.containsKey(v)) {
                throw new IllegalArgumentException("Une variable n'est pas déclarée");
            }
            Object tache = taches.get(v);
            return s.contains(tache);
               
    }



}