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

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) {
        for (Variable variable : getScope()) {
            if (!instantiation.containsKey(variable)) {
                throw new IllegalArgumentException("Une variable n'est pas déclarée");
            }
            Object tache1 = instantiation.get(v1);
            Object tache2 = instantiation.get(v2);
            if(tache1 != tache2){  
                return true;
            }else{
                return false;
            }
            
    
        } 
        return true;
    }
    
}
